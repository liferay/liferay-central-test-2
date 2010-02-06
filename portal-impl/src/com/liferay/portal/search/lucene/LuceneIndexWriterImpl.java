/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Collection;

import org.apache.lucene.index.Term;

/**
 * <a href="LuceneIndexWriterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Allen Chiang
 * @author Alex Wallace
 */
public class LuceneIndexWriterImpl implements IndexWriter {

	public void addDocument(long companyId, Document document)
		throws SearchException {

		try {
			LuceneHelperUtil.addDocument(
				companyId, _getLuceneDocument(document));

			if (_log.isDebugEnabled()) {
				_log.debug("Added document " + document.get(Field.UID));
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	public void deleteDocument(long companyId, String uid)
		throws SearchException {

		try {
			LuceneHelperUtil.deleteDocuments(
				companyId, new Term(Field.UID, uid));

			if (_log.isDebugEnabled()) {
				_log.debug("Deleted document " + uid);
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	public void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		try {
			LuceneHelperUtil.deleteDocuments(
				companyId, new Term(Field.PORTLET_ID, portletId));
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	public void updateDocument(long companyId, String uid, Document document)
		throws SearchException {

		try {
			LuceneHelperUtil.updateDocument(
				companyId, new Term(Field.UID, uid),
				_getLuceneDocument(document));

			if (_log.isDebugEnabled()) {
				_log.debug("Updated document " + document.get(Field.UID));
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	private org.apache.lucene.document.Document _getLuceneDocument(
		Document document) {

		org.apache.lucene.document.Document luceneDocument =
			new org.apache.lucene.document.Document();

		Collection<Field> fields = document.getFields().values();

		for (Field field : fields) {
			String name = field.getName();
			boolean tokenized = field.isTokenized();
			float boost = field.getBoost();

			for (String value : field.getValues()) {
				if (Validator.isNull(value)) {
					continue;
				}

				org.apache.lucene.document.Field luceneField = null;

				if (tokenized) {
					luceneField = LuceneFields.getText(name, value);
				}
				else {
					luceneField = LuceneFields.getKeyword(name, value);
				}

				luceneField.setBoost(boost);

				luceneDocument.add(luceneField);
			}
		}

		return luceneDocument;
	}

	private static Log _log =
		LogFactoryUtil.getLog(LuceneIndexWriterImpl.class);

}