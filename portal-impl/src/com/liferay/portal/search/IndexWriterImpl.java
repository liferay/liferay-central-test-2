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

package com.liferay.portal.search;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.messaging.SearchRequest;

/**
 * <a href="IndexWriterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class IndexWriterImpl implements IndexWriter {

	public void addDocument(long companyId, Document document) {
		SearchRequest searchRequest = SearchRequest.add(companyId, document);

		MessageBusUtil.sendMessage(
			DestinationNames.SEARCH_WRITER, searchRequest);
	}

	public void deleteDocument(long companyId, String uid) {
		SearchRequest searchRequest = SearchRequest.delete(companyId, uid);

		MessageBusUtil.sendMessage(
			DestinationNames.SEARCH_WRITER, searchRequest);
	}

	public void deletePortletDocuments(long companyId, String portletId) {
		SearchRequest searchRequest = SearchRequest.deletePortletDocuments(
			companyId, portletId);

		MessageBusUtil.sendMessage(
			DestinationNames.SEARCH_WRITER, searchRequest);
	}

	public void updateDocument(long companyId, String uid, Document document) {
		SearchRequest searchRequest = SearchRequest.update(
			companyId, uid, document);

		MessageBusUtil.sendMessage(
			DestinationNames.SEARCH_WRITER, searchRequest);
	}

}