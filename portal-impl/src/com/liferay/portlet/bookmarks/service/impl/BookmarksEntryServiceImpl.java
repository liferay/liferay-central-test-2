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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryServiceBaseImpl;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;

/**
 * <a href="BookmarksEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryServiceImpl extends BookmarksEntryServiceBaseImpl {

	public BookmarksEntry addEntry(
			long groupId, long folderId, String name, String url,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_ENTRY);

		return bookmarksEntryLocalService.addEntry(
			null, getUserId(), groupId, folderId, name, url, comments,
			serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		bookmarksEntryLocalService.deleteEntry(entryId);
	}

	public BookmarksEntry getEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return bookmarksEntryLocalService.getEntry(entryId);
	}

	public BookmarksEntry openEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return bookmarksEntryLocalService.openEntry(entryId);
	}

	public BookmarksEntry updateEntry(
			long entryId, long groupId, long folderId, String name, String url,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return bookmarksEntryLocalService.updateEntry(
			getUserId(), entryId, groupId, folderId, name, url, comments,
			serviceContext);
	}

}