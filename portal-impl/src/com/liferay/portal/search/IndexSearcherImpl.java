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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.messaging.SearchRequest;

/**
 * @author Bruno Farache
 */
public class IndexSearcherImpl implements IndexSearcher {

	public Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		try {
			SearchRequest searchRequest = SearchRequest.search(
				companyId, query, sorts, start, end);

			Hits hits = (Hits)MessageBusUtil.sendSynchronousMessage(
				DestinationNames.SEARCH_READER, searchRequest,
				DestinationNames.SEARCH_READER_RESPONSE);

			return hits;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

}