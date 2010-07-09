/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;

import java.util.Collection;

/**
 * @author Bruno Farache
 */
public class SearchWriterMessageListener
	extends BaseSearchEngineMessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		Object payload = message.getPayload();

		if (!(payload instanceof SearchRequest)) {
			return;
		}

		SearchRequest searchRequest = (SearchRequest)payload;

		SearchEngineCommand searchEngineCommand =
			searchRequest.getSearchEngineCommand();

		long companyId = searchRequest.getCompanyId();
		Document document = searchRequest.getDocument();
		Collection<Document> documents = searchRequest.getDocuments();
		String id = searchRequest.getId();
		Collection<String> ids = searchRequest.getIds();

		IndexWriter indexWriter = searchEngine.getWriter();

		if (searchEngineCommand.equals(SearchEngineCommand.ADD_DOCUMENT)) {
			indexWriter.addDocument(companyId, document);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.ADD_DOCUMENTS)) {

			indexWriter.addDocuments(companyId, documents);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.DELETE_DOCUMENT)) {

			indexWriter.deleteDocument(companyId, id);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.DELETE_DOCUMENTS)) {

			indexWriter.deleteDocuments(companyId, ids);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.DELETE_PORTLET_DOCUMENTS)) {

			indexWriter.deletePortletDocuments(companyId, id);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.UPDATE_DOCUMENT)) {

			indexWriter.updateDocument(companyId, document);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.UPDATE_DOCUMENTS)) {

			indexWriter.updateDocuments(companyId, documents);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SearchWriterMessageListener.class);

}