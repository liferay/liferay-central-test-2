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

import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * <a href="IndexWriterFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class IndexWriterFactoryImpl implements IndexWriterFactory {

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

	private static final int _MERGE_FACTOR = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.LUCENE_MERGE_FACTOR));

	private static final int _OPTIMIZE_INTERVAL = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.LUCENE_OPTIMIZE_INTERVAL));

	private int _optimizeCount = 0;

}