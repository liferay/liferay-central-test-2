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

package com.liferay.portal.search;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;

/**
 * @author Bruno Farache
 * @author Tina Tian
 * @author Raymond Augé
 */
public class IndexSearcherProxyBean
	extends BaseProxyBean implements IndexSearcher {

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public Hits search(
		long companyId, Query query, Sort[] sorts, int start, int end) {

		throw new UnsupportedOperationException();
	}

}