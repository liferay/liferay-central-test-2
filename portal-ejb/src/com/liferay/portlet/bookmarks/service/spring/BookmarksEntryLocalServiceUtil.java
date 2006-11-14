/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.spring;

/**
 * <a href="BookmarksEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryLocalServiceUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(userId, folderId, name, url,
			comments, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(userId, folderId, name, url,
			comments, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.addEntry(userId, folderId, name, url,
			comments, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public static void addEntryResources(java.lang.String folderId,
		java.lang.String entryId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
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

	public static void addEntryResources(java.lang.String folderId,
		java.lang.String entryId, java.lang.String[] communityPermissions,
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

	public static void deleteEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
		bookmarksEntryLocalService.deleteEntries(folderId);
	}

	public static void deleteEntry(java.lang.String entryId)
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

	public static java.util.List getEntries(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntries(folderId, begin, end);
	}

	public static int getEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntriesCount(folderId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getEntry(entryId);
	}

	public static int getFoldersEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getFoldersEntriesCount(folderIds);
	}

	public static java.util.List getGroupEntries(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntries(groupId, begin, end);
	}

	public static java.util.List getGroupEntries(java.lang.String groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntries(groupId, userId,
			begin, end);
	}

	public static int getGroupEntriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.getGroupEntriesCount(groupId, userId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.openEntry(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		java.lang.String companyId, java.lang.String entryId,
		java.lang.String folderId, java.lang.String name, java.lang.String url,
		java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

		return bookmarksEntryLocalService.updateEntry(companyId, entryId,
			folderId, name, url, comments);
	}
}