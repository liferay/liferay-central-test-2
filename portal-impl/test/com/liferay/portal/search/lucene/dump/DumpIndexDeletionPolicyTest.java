/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.lucene.dump;

import com.liferay.portal.kernel.test.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * @author Shuyang Zhou
 */
public class DumpIndexDeletionPolicyTest extends TestCase {

	public void setUp() throws IOException {
		_directory = new RAMDirectory();
		_dumpIndexDeletionPolicy = new DumpIndexDeletionPolicy();
		_indexWriter = new IndexWriter(_directory,
			new StandardAnalyzer(Version.LUCENE_30),
			_dumpIndexDeletionPolicy, IndexWriter.MaxFieldLength.UNLIMITED);
	}

	public void testEmptyDump() throws IOException {
		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_indexWriter.close();
	}

	public void testOneCommitDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 0);

		_indexWriter.close();
	}

	public void testTwoCommitsDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 1);
		_assertHit(_directory, "name", "test3", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 1);
		_assertHit(newDirectory, "name", "test3", 0);

		_indexWriter.close();
	}

	public void testThreeCommitsDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test3", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 1);
		_assertHit(_directory, "name", "test3", 1);
		_assertHit(_directory, "name", "test4", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 1);
		_assertHit(newDirectory, "name", "test3", 1);
		_assertHit(newDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	public void testThreeCommitsWithOptimizationDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test3", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_indexWriter.optimize();
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 1);
		_assertHit(_directory, "name", "test3", 1);
		_assertHit(_directory, "name", "test4", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 1);
		_assertHit(newDirectory, "name", "test3", 1);
		_assertHit(newDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	public void testThreeCommitsOneDeletionDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test3", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_indexWriter.deleteDocuments(new Term("name", "test2"));
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 0);
		_assertHit(_directory, "name", "test3", 1);
		_assertHit(_directory, "name", "test4", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 0);
		_assertHit(newDirectory, "name", "test3", 1);
		_assertHit(newDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	public void testThreeCommitsTwoDeletionsDump() throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test3", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_indexWriter.deleteDocuments(new Term("name", "test2"));
		_indexWriter.commit();

		_indexWriter.deleteDocuments(new Term("name", "test3"));
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 0);
		_assertHit(_directory, "name", "test3", 0);
		_assertHit(_directory, "name", "test4", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 0);
		_assertHit(newDirectory, "name", "test3", 0);
		_assertHit(newDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	public void testThreeCommitsTwoDeletionsWithOptimizationDump()
		throws IOException {
		Document document = new Document();
		document.add(new Field("name", "test1", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test2", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		document = new Document();
		document.add(new Field("name", "test3", Field.Store.YES,
			Field.Index.ANALYZED));

		_indexWriter.addDocument(document);
		_indexWriter.commit();

		_indexWriter.deleteDocuments(new Term("name", "test2"));
		_indexWriter.commit();

		_indexWriter.deleteDocuments(new Term("name", "test3"));
		_indexWriter.commit();

		_indexWriter.optimize();
		_indexWriter.commit();

		_assertHit(_directory, "name", "test1", 1);
		_assertHit(_directory, "name", "test2", 0);
		_assertHit(_directory, "name", "test3", 0);
		_assertHit(_directory, "name", "test4", 0);

		Directory newDirectory = _dumpToNewDirectory(_indexWriter);

		_assertSameDirectory(_directory, newDirectory);

		_assertHit(newDirectory, "name", "test1", 1);
		_assertHit(newDirectory, "name", "test2", 0);
		_assertHit(newDirectory, "name", "test3", 0);
		_assertHit(newDirectory, "name", "test4", 0);

		_indexWriter.close();
	}

	private void _assertSameDirectory(
		Directory sourceDirectory, Directory newDirectory) throws IOException {
		String[] sourceFileNames = sourceDirectory.listAll();
		Arrays.sort(sourceFileNames);

		String[] newFileNames = newDirectory.listAll();
		Arrays.sort(newFileNames);

		if (sourceFileNames.length != newFileNames.length) {
			fail("Source and new directory file names do not match, "
				+ "source directory : " + Arrays.toString(sourceFileNames) +
				", new directory : " + Arrays.toString(newFileNames));
		}

		for (String fileName : sourceFileNames) {
			long sourceLength = sourceDirectory.fileLength(fileName);
			long newLength = newDirectory.fileLength(fileName);
			if (sourceLength != newLength) {
				fail("Files length do not match, name : " + fileName +
					", source length : " + sourceLength + ", new length : " +
					newLength);
			}

			_assertSameContent(fileName,
				sourceDirectory.openInput(fileName),
				newDirectory.openInput(fileName));
		}
	}

	private void _assertSameContent(
			String fileName, IndexInput sourceIndexInput,
			IndexInput newIndexInput)
		throws IOException {
		for (long i = 0; i < sourceIndexInput.length(); i++) {
			if (sourceIndexInput.readByte() != newIndexInput.readByte()) {
				fail("Files content do not match, name : " + fileName +
					", position : " + i);
			}
		}

		sourceIndexInput.close();
		newIndexInput.close();
	}

	private void _assertHit(
			Directory directory, String name, String value, int hitTimes)
		throws IOException {
		IndexSearcher indexSearcher = new IndexSearcher(directory);
		Term term = new Term(name, value);
		TermQuery termQuery = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(termQuery, 1);
		assertEquals(hitTimes, topDocs.totalHits);
	}

	private Directory _dumpToNewDirectory(IndexWriter indexWriter)
		throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		_dumpIndexDeletionPolicy.dump(baos, indexWriter, new ReentrantLock());

		byte[] data = baos.toByteArray();

		Directory newDirectory = new RAMDirectory();

		IndexCommitSerializationUtil.deserializeIndex(
			new ByteArrayInputStream(data), newDirectory);

		return newDirectory;
	}

	private Directory _directory;
	private DumpIndexDeletionPolicy _dumpIndexDeletionPolicy;
	private IndexWriter _indexWriter;

}