/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * <a href="IndexWriterFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class IndexWriterFactory {

	public void checkLuceneDir(long companyId) {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		Directory luceneDir = LuceneUtil.getLuceneDir(companyId);

		try {

			// LEP-6078

			if (luceneDir.fileExists("write.lock")) {
				luceneDir.deleteFile("write.lock");
			}
		}
		catch (IOException ioe) {
			_log.error("Unable to clear write lock", ioe);
		}

		// Lucene does not properly release its lock on the index when
		// IndexWriter throws an exception

		try {
			write(companyId, null);
		}
		catch (IOException ioe) {
			_log.error("Check Lucene directory failed for " + companyId, ioe);
		}
	}

	public void deleteDocuments(long companyId, Term term)
		throws InterruptedException, IOException {

		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized(this) {
			IndexReader reader = null;

			try {
				reader =
					IndexReader.open(LuceneUtil.getLuceneDir(companyId), false);

				reader.deleteDocuments(term);
			}
			finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

	public void write(long companyId, Document doc) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized(this) {
			IndexWriter writer = null;

			try {
				writer = new IndexWriter(
					LuceneUtil.getLuceneDir(companyId),
					LuceneUtil.getAnalyzer(),
					IndexWriter.MaxFieldLength.LIMITED);

				if (doc != null) {
					writer.setMergeFactor(_MERGE_FACTOR);
					writer.addDocument(doc);

					_optimizeCount++;

					if ((_OPTIMIZE_INTERVAL == 0) ||
						(_optimizeCount >= _OPTIMIZE_INTERVAL)) {

						writer.optimize();

						_optimizeCount = 0;
					}
				}
			}
			finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	protected IndexWriter getReadOnlyIndexWriter() {
		if (_readOnlyIndexWriter == null) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Disabling writing to index for this process");
				}

				_readOnlyIndexWriter = new ReadOnlyIndexWriter(
					getReadOnlyLuceneDir(), new SimpleAnalyzer(), true);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		return _readOnlyIndexWriter;
	}

	protected Directory getReadOnlyLuceneDir() throws IOException {
		if (_readOnlyLuceneDir == null) {
			String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

			File dir = new File(tmpDir + "/liferay/lucene/empty");

			dir.mkdir();

			_readOnlyLuceneDir = LuceneUtil.getDirectory(dir.getPath());
		}

		return _readOnlyLuceneDir;
	}

	private static final int _MERGE_FACTOR = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.LUCENE_MERGE_FACTOR));

	private static final int _OPTIMIZE_INTERVAL = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.LUCENE_OPTIMIZE_INTERVAL));

	private static Log _log = LogFactoryUtil.getLog(IndexWriterFactory.class);

	private FSDirectory _readOnlyLuceneDir = null;
	private IndexWriter _readOnlyIndexWriter = null;
	private int _optimizeCount = 0;

}