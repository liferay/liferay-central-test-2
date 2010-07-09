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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.search.HitsOpenSearchImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/bookmarks/open_search";

	public static final String TITLE = "Liferay Bookmarks Search: ";

	public String getPortletId() {
		return BookmarksIndexer.PORTLET_ID;
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

}