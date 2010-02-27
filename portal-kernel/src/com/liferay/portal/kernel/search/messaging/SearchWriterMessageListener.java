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

/**
 * <a href="SearchWriterMessageListener.java.html"><b><i>View Source</i></b></a>
 *
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
		String id = searchRequest.getId();
		Document doc = searchRequest.getDocument();

		IndexWriter indexWriter = searchEngine.getWriter();

		if (searchEngineCommand.equals(SearchEngineCommand.ADD)) {
			indexWriter.addDocument(companyId, doc);
		}
		else if (searchEngineCommand.equals(SearchEngineCommand.DELETE)) {
			indexWriter.deleteDocument(companyId, id);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.DELETE_PORTLET_DOCUMENTS)) {

			indexWriter.deletePortletDocuments(companyId, id);
		}
		else if (searchEngineCommand.equals(SearchEngineCommand.UPDATE)) {
			indexWriter.updateDocument(companyId, id, doc);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SearchWriterMessageListener.class);

}