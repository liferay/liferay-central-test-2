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

	public IndexAccessorImpl(long companyId) {
		_companyId = companyId;
	}

	public void addDocument(Document document) throws IOException {
		write(null, document);
	}

	public void cleanUp() throws IOException {
		synchronized (this) {
			if (_indexWriter != null) {
				_indexWriter.close();
			}

			_indexWriter = null;
		}
	}

	public void deleteDocuments(Term term) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			initIndexWriter();

			try {
				_indexWriter.deleteDocuments(term);
			}
			finally {
				cleanUp();
			}
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void updateDocument(Term term, Document document)
		throws IOException {

		write(term, document);
	}

	protected void initIndexWriter() throws IOException {
		if (_indexWriter == null) {
			_indexWriter = new IndexWriter(
				LuceneHelperUtil.getLuceneDir(_companyId),
				LuceneHelperUtil.getAnalyzer(),
				IndexWriter.MaxFieldLength.LIMITED);

			_indexWriter.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
		}
	}

	protected void write(Term term, Document document) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			initIndexWriter();

			try {
				if (document != null) {
					if (term != null) {
						_indexWriter.updateDocument(term, document);
					}
					else {
						_indexWriter.addDocument(document);
					}

					_optimizeCount++;

					if ((PropsValues.LUCENE_OPTIMIZE_INTERVAL == 0) ||
						(_optimizeCount >=
							PropsValues.LUCENE_OPTIMIZE_INTERVAL)) {

						_indexWriter.optimize();

						_optimizeCount = 0;
					}
				}
			}
			finally {
				cleanUp();
			}
		}
	}

	private long _companyId;
	private IndexWriter _indexWriter;
	private int _optimizeCount;

}