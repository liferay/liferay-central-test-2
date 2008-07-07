/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService
 * @see com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceFactory
 *
 */
public class BookmarksEntryLocalServiceUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addBookmarksEntry(bookmarksEntry);
	}

	public static void deleteBookmarksEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.deleteBookmarksEntry(entryId);
	}

	public static void deleteBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.deleteBookmarksEntry(bookmarksEntry);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getBookmarksEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getBookmarksEntry(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(userId, folderId, name, url,
			comments, tagsEntries, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(uuid, userId, folderId,
			name, url, comments, tagsEntries, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(userId, folderId, name, url,
			comments, tagsEntries, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(uuid, userId, folderId,
			name, url, comments, tagsEntries, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addEntryResources(long folderId, long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.addEntryResources(folderId, entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.addEntryResources(folder, entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addEntryResources(long folderId, long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.addEntryResources(folderId, entryId,
			communityPermissions, guestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.addEntryResources(folder, entry,
			communityPermissions, guestPermissions);
	}

	public static void deleteEntries(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.deleteEntries(folderId);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.deleteEntry(entry);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntries(folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntries(folderId, start, end,
			orderByComparator);
	}

	public static int getEntriesCount(long folderId)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntriesCount(folderId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntry(entryId);
	}

	public static int getFoldersEntriesCount(java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getFoldersEntriesCount(folderIds);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntries(groupId, userId,
			start, end);
	}

	public static int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntriesCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getNoAssetEntries()
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getNoAssetEntries();
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.openEntry(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		long userId, long entryId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.updateEntry(userId, entryId,
			folderId, name, url, comments, tagsEntries);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		bookmarksEntryLocalService.updateTagsAsset(userId, entry, tagsEntries);
	}
}