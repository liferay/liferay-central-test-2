/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

/**
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
public class BookmarksSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		BookmarksEntry.class.getName(), BookmarksFolder.class.getName()
	};

	public static Indexer getInstance() {
		return new BookmarksSearcher();
	}

	public BookmarksSearcher() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

}