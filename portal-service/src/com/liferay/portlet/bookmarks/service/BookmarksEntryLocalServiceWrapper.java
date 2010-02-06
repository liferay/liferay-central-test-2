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

package com.liferay.portlet.bookmarks.service;


/**
 * <a href="BookmarksEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BookmarksEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksEntryLocalService
 * @generated
 */
public class BookmarksEntryLocalServiceWrapper
	implements BookmarksEntryLocalService {
	public BookmarksEntryLocalServiceWrapper(
		BookmarksEntryLocalService bookmarksEntryLocalService) {
		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.addBookmarksEntry(bookmarksEntry);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry createBookmarksEntry(
		long entryId) {
		return _bookmarksEntryLocalService.createBookmarksEntry(entryId);
	}

	public void deleteBookmarksEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.deleteBookmarksEntry(entryId);
	}

	public void deleteBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.deleteBookmarksEntry(bookmarksEntry);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry getBookmarksEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getBookmarksEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntries(start, end);
	}

	public int getBookmarksEntriesCount()
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntriesCount();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry,
			merge);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.addEntry(uuid, userId, groupId,
			folderId, name, url, comments, serviceContext);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entry,
			communityPermissions, guestPermissions);
	}

	public void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entryId,
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId, long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.deleteEntries(groupId, folderId);
	}

	public void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getEntries(groupId, folderId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getEntries(groupId, folderId, start,
			end, orderByComparator);
	}

	public int getEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getEntriesCount(groupId, folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getEntry(entryId);
	}

	public int getFoldersEntriesCount(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getFoldersEntriesCount(groupId,
			folderIds);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getGroupEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getGroupEntries(groupId, userId,
			start, end);
	}

	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getGroupEntriesCount(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getGroupEntriesCount(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getNoAssetEntries()
		throws com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.getNoAssetEntries();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.openEntry(entryId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksEntryLocalService.updateAsset(userId, entry,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		long userId, long entryId, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksEntryLocalService.updateEntry(userId, entryId,
			groupId, folderId, name, url, comments, serviceContext);
	}

	public BookmarksEntryLocalService getWrappedBookmarksEntryLocalService() {
		return _bookmarksEntryLocalService;
	}

	private BookmarksEntryLocalService _bookmarksEntryLocalService;
}