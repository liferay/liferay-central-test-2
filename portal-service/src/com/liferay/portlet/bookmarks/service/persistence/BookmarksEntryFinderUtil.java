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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="BookmarksEntryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryFinderUtil {
	public static int countByG_F(long groupId, java.util.List<Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_F(groupId, folderIds);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoAssets();
	}

	public static BookmarksEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (BookmarksEntryFinder)PortalBeanLocatorUtil.locate(BookmarksEntryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(BookmarksEntryFinder finder) {
		_finder = finder;
	}

	private static BookmarksEntryFinder _finder;
}