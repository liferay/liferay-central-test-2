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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksFolderFinderUtil {
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByNoResourceBlocks()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoResourceBlocks();
	}

	public static BookmarksFolderFinder getFinder() {
		if (_finder == null) {
			_finder = (BookmarksFolderFinder)PortalBeanLocatorUtil.locate(BookmarksFolderFinder.class.getName());

			ReferenceRegistry.registerReference(BookmarksFolderFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(BookmarksFolderFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(BookmarksFolderFinderUtil.class,
			"_finder");
	}

	private static BookmarksFolderFinder _finder;
}