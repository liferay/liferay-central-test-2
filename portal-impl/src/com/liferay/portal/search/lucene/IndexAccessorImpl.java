/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.dump.DumpIndexDeletionPolicy;
import com.liferay.portal.search.lucene.dump.IndexCommitSerializationUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class IndexAccessorImpl implements IndexAccessor {

	public IndexAccessorImpl(long companyId) {
		_companyId = companyId;

		_checkLuceneDir();
		_initIndexWriter();
		_initCommitScheduler();
	}

	public void addDocument(Document document) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		_write(null, document);
	}

	public void close() {
		try {
			_indexWriter.close();
		}
		catch (Exception e) {
			_log.error("Closing Lucene writer failed for " + _companyId, e);
		}
	}

	public void delete() {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		close();

		_deleteDirectory();

		_initIndexWriter();
	}

	public void deleteDocuments(Term term) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		try {
			_indexWriter.deleteDocuments(term);

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	public void dumpIndex(OutputStream outputStream) throws IOException {
		_dumpIndexDeletionPolicy.dump(outputStream, _indexWriter, _commitLock);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getLastGeneration() {
		return _dumpIndexDeletionPolicy.getLastGeneration();
	}

	public Directory getLuceneDir() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			return _getLuceneDirFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			return _getLuceneDirRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	public void loadIndex(InputStream inputStream) throws IOException {
		File tempFile = FileUtil.createTempFile();

		Directory tempDirectory = FSDirectory.open(tempFile);

		IndexCommitSerializationUtil.deserializeIndex(
			inputStream, tempDirectory);

		close();

		_deleteDirectory();

		Directory.copy(tempDirectory, getLuceneDir(), true);

		_initIndexWriter();

		tempDirectory.close();

		FileUtil.deltree(tempFile);
	}

	public void updateDocument(Term term, Document document)
		throws IOException {

		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing " + document);
		}

		_write(term, document);
	}

	private void _checkLuceneDir() {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		try {
			Directory directory = getLuceneDir();

			if (IndexWriter.isLocked(directory)) {
				IndexWriter.unlock(directory);
			}
		}
		catch (Exception e) {
			_log.error("Check Lucene directory failed for " + _companyId, e);
		}
	}

	private void _commit() throws IOException {
		if ((PropsValues.LUCENE_COMMIT_BATCH_SIZE == 0) ||
			(PropsValues.LUCENE_COMMIT_BATCH_SIZE <= _batchCount)) {

			_doCommit();
		}
	}

	private void _deleteDirectory() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			_deleteFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			_deleteRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	private void _deleteFile() {
		String path = _getPath();

		try {
			Directory directory = _getDirectory(path);

			directory.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not close directory " + path);
			}
		}

		FileUtil.deltree(path);
	}

	private void _deleteRam() {
	}

	private void _doCommit() throws IOException {
		if (_indexWriter != null) {
			_commitLock.lock();

			try {
				_indexWriter.commit();
			}
			finally {
				_commitLock.unlock();
			}
		}

		_batchCount = 0;
	}

	private FSDirectory _getDirectory(String path) throws IOException {
		if (PropsValues.LUCENE_STORE_TYPE_FILE_FORCE_MMAP) {
			return new MMapDirectory(new File(path));
		}
		else {
			return FSDirectory.open(new File(path));
		}
	}

	private Directory _getLuceneDirFile() {
		Directory directory = null;

		String path = _getPath();

		try {
			directory = _getDirectory(path);
		}
		catch (IOException ioe1) {
			if (directory != null) {
				try {
					directory.close();
				}
				catch (Exception e) {
				}
			}
		}

		return directory;
	}

	private Directory _getLuceneDirRam() {
		String path = _getPath();

		Directory directory = _ramDirectories.get(path);

		if (directory == null) {
			directory = new RAMDirectory();

			_ramDirectories.put(path, directory);
		}

		return directory;
	}

	private String _getPath() {
		return PropsValues.LUCENE_DIR.concat(String.valueOf(_companyId)).concat(
			StringPool.SLASH);
	}

	private void _initCommitScheduler() {
		if ((PropsValues.LUCENE_COMMIT_BATCH_SIZE <= 0) ||
			(PropsValues.LUCENE_COMMIT_TIME_INTERVAL <= 0)) {

			return;
		}

		ScheduledExecutorService scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		Runnable runnable = new Runnable() {

			public void run() {
				try {
					if (_batchCount > 0) {
						_doCommit();
					}
				}
				catch (IOException ioe) {
					_log.error("Could not run scheduled commit", ioe);
				}
			}

		};

		scheduledExecutorService.scheduleWithFixedDelay(
			runnable, 0, PropsValues.LUCENE_COMMIT_TIME_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	private void _initIndexWriter() {
		try {
			_indexWriter = new IndexWriter(
				getLuceneDir(), LuceneHelperUtil.getAnalyzer(),
				_dumpIndexDeletionPolicy, IndexWriter.MaxFieldLength.LIMITED);

			_indexWriter.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
			_indexWriter.setRAMBufferSizeMB(PropsValues.LUCENE_BUFFER_SIZE);
		}
		catch (Exception e) {
			_log.error(
				"Initializing Lucene writer failed for " + _companyId, e);
		}
	}

	private void _write(Term term, Document document) throws IOException {
		try {
			if (term != null) {
				_indexWriter.updateDocument(term, document);
			}
			else {
				_indexWriter.addDocument(document);
			}

			_optimizeCount++;

			if ((PropsValues.LUCENE_OPTIMIZE_INTERVAL == 0) ||
				(_optimizeCount >= PropsValues.LUCENE_OPTIMIZE_INTERVAL)) {

				_indexWriter.optimize();

				_optimizeCount = 0;
			}

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_STORE_TYPE_RAM = "ram";

	private static Log _log = LogFactoryUtil.getLog(IndexAccessorImpl.class);

	private volatile int _batchCount;
	private Lock _commitLock = new ReentrantLock();
	private long _companyId;
	private DumpIndexDeletionPolicy _dumpIndexDeletionPolicy =
		new DumpIndexDeletionPolicy();
	private IndexWriter _indexWriter;
	private int _optimizeCount;
	private Map<String, Directory> _ramDirectories =
		new ConcurrentHashMap<String, Directory>();

}