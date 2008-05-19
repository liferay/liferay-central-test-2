/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;

import java.io.IOException;

import java.util.Collection;

import org.apache.lucene.index.Term;

/**
 * <a href="LuceneIndexWriterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneIndexWriterImpl implements IndexWriter {

	public void addDocument(long companyId, Document doc)
		throws SearchException {

		org.apache.lucene.index.IndexWriter writer = null;

		try {
			writer = LuceneUtil.getWriter(companyId);

			writer.addDocument(_getLuceneDocument(doc));
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
		finally {
			if (writer != null) {
				try {
					LuceneUtil.write(companyId);
				}
				catch (IOException ioe) {
					throw new SearchException(ioe);
				}
			}
		}
	}

	public void deleteDocument(long companyId, String uid)
		throws SearchException {

		try {
			LuceneUtil.deleteDocuments(companyId, new Term(Field.UID, uid));
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	public void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		deleteDocument(companyId, uid);

		addDocument(companyId, doc);
	}

	private org.apache.lucene.document.Document _getLuceneDocument(
		Document doc) {

		org.apache.lucene.document.Document luceneDoc =
			new org.apache.lucene.document.Document();

		Collection<Field> values = doc.getFields().values();

		for (Field field : values) {
			if (field.isTokenized()) {
				LuceneUtil.addText(
					luceneDoc, field.getName(), field.getValue());
			}
			else {
				LuceneUtil.addKeyword(
					luceneDoc, field.getName(), field.getValue());
			}
		}

		return luceneDoc;
	}

}