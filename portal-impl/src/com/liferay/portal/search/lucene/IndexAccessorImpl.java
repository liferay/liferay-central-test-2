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
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * <a href="IndexAccessorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class IndexAccessorImpl implements IndexAccessor {

	public void deleteDocuments(long companyId, Term term) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			IndexReader indexReader = null;

			try {
				indexReader = IndexReader.open(
					LuceneHelperUtil.getLuceneDir(companyId), false);

				indexReader.deleteDocuments(term);
			}
			finally {
				if (indexReader != null) {
					indexReader.close();
				}
			}
		}
	}

	public void write(long companyId, Document document) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			IndexWriter indexWriter = null;

			try {
				indexWriter = new IndexWriter(
					LuceneHelperUtil.getLuceneDir(companyId),
					LuceneHelperUtil.getAnalyzer(),
					IndexWriter.MaxFieldLength.LIMITED);

				if (document != null) {
					indexWriter.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
					indexWriter.addDocument(document);

					_optimizeCount++;

					if ((PropsValues.LUCENE_OPTIMIZE_INTERVAL == 0) ||
						(_optimizeCount >=
							PropsValues.LUCENE_OPTIMIZE_INTERVAL)) {

						indexWriter.optimize();

						_optimizeCount = 0;
					}
				}
			}
			finally {
				if (indexWriter != null) {
					indexWriter.close();
				}
			}
		}
	}

	private int _optimizeCount;

}