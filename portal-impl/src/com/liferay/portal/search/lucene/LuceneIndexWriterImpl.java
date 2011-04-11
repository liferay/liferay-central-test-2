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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.index.Term;

/**
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

	public void addDocuments(long companyId, Collection<Document> documents)
		throws SearchException {

		for (Document document : documents) {
			addDocument(companyId, document);
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

	public void deleteDocuments(long companyId, Collection<String> uids)
		throws SearchException {

		for (String uid : uids) {
			deleteDocument(companyId, uid);
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

	public void updateDocument(long companyId, Document document)
		throws SearchException {

		try {
			LuceneHelperUtil.updateDocument(
				companyId, new Term(Field.UID, document.getUID()),
				_getLuceneDocument(document));

			if (_log.isDebugEnabled()) {
				_log.debug("Updated document " + document.get(Field.UID));
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	public void updateDocuments(long companyId, Collection<Document> documents)
		throws SearchException {

		for (Document document : documents) {
			updateDocument(companyId, document);
		}
	}

	private void _addLuceneFieldable(
		org.apache.lucene.document.Document luceneDocument, String name,
		boolean numeric, boolean tokenized, float boost, String value) {

		org.apache.lucene.document.Fieldable luceneFieldable = null;

		if (numeric) {
			luceneFieldable = LuceneFields.getNumber(name, value);
		}
		else {
			if (tokenized) {
				luceneFieldable = LuceneFields.getText(name, value);
			}
			else {
				luceneFieldable = LuceneFields.getKeyword(name, value);
			}
		}

		luceneFieldable.setBoost(boost);

		luceneDocument.add(luceneFieldable);
	}

	private org.apache.lucene.document.Document _getLuceneDocument(
		Document document) {

		org.apache.lucene.document.Document luceneDocument =
			new org.apache.lucene.document.Document();

		Collection<Field> fields = document.getFields().values();

		for (Field field : fields) {
			String name = field.getName();
			boolean numeric = field.isNumeric();
			boolean tokenized = field.isTokenized();
			float boost = field.getBoost();

			if (!field.isLocalized()) {
				for (String value : field.getValues()) {
					if (Validator.isNull(value)) {
						continue;
					}

					_addLuceneFieldable(
						luceneDocument, name, numeric, tokenized, boost, value);
				}
			}
			else {
				Map<Locale, String> localizedValues =
					field.getLocalizedValues();

				for (Map.Entry<Locale, String> entry :
						localizedValues.entrySet()) {

					Locale locale = entry.getKey();
					String value = entry.getValue();

					if (Validator.isNull(value)) {
						continue;
					}

					name = name.concat(StringPool.UNDERLINE).concat(
						locale.getDisplayName());

					_addLuceneFieldable(
						luceneDocument, name, numeric, tokenized, boost, value);

				}
			}

		}

		return luceneDocument;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LuceneIndexWriterImpl.class);

}