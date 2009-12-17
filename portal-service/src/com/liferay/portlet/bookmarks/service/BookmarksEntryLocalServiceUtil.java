/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="BookmarksEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link BookmarksEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksEntryLocalService
 * @generated
 */
public class BookmarksEntryLocalServiceUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		return getService().addBookmarksEntry(bookmarksEntry);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry createBookmarksEntry(
		long entryId) {
		return getService().createBookmarksEntry(entryId);
	}

	public static void deleteBookmarksEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteBookmarksEntry(entryId);
	}

	public static void deleteBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		getService().deleteBookmarksEntry(bookmarksEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getBookmarksEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getBookmarksEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getBookmarksEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getBookmarksEntries(start, end);
	}

	public static int getBookmarksEntriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getBookmarksEntriesCount();
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		return getService().updateBookmarksEntry(bookmarksEntry);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateBookmarksEntry(bookmarksEntry, merge);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addEntry(uuid, userId, groupId, folderId, name, url,
			comments, serviceContext);
	}

	public static void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public static void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entryId, communityPermissions, guestPermissions);
	}

	public static void deleteEntries(long groupId, long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntries(groupId, folderId);
	}

	public static void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getEntries(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getEntries(groupId, folderId, start, end, orderByComparator);
	}

	public static int getEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.SystemException {
		return getService().getEntriesCount(groupId, folderId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getEntry(entryId);
	}

	public static int getFoldersEntriesCount(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersEntriesCount(groupId, folderIds);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupEntries(groupId, userId, start, end);
	}

	public static int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupEntriesCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getNoAssetEntries()
		throws com.liferay.portal.SystemException {
		return getService().getNoAssetEntries();
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().openEntry(entryId);
	}

	public static void reIndex(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.SystemException {
		getService().reIndex(entry);
	}

	public static void reIndex(long entryId)
		throws com.liferay.portal.SystemException {
		getService().reIndex(entryId);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateAsset(userId, entry, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		long userId, long entryId, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateEntry(userId, entryId, groupId, folderId, name, url,
			comments, serviceContext);
	}

	public static BookmarksEntryLocalService getService() {
		if (_service == null) {
			_service = (BookmarksEntryLocalService)PortalBeanLocatorUtil.locate(BookmarksEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(BookmarksEntryLocalService service) {
		_service = service;
	}

	private static BookmarksEntryLocalService _service;
}