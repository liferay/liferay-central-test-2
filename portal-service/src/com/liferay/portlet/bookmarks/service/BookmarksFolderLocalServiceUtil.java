/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="BookmarksFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksFolderLocalServiceUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.addFolder(userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.addFolder(userId, plid,
			parentFolderId, name, description, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.addFolder(userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addFolderResources(java.lang.String folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFolderResources(java.lang.String folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.addFolderResources(folder,
			communityPermissions, guestPermissions);
	}

	public static void deleteFolder(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.deleteFolder(folderId);
	}

	public static void deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.deleteFolder(folder);
	}

	public static void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.deleteFolders(groupId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.getFolder(folderId);
	}

	public static java.util.List getFolders(long groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.getFolders(groupId, parentFolderId,
			begin, end);
	}

	public static int getFoldersCount(long groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.getFoldersCount(groupId,
			parentFolderId);
	}

	public static void getSubfolderIds(java.util.List folderIds, long groupId,
		java.lang.String folderId) throws com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
		bookmarksFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		java.lang.String folderId, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentFolder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

		return bookmarksFolderLocalService.updateFolder(folderId,
			parentFolderId, name, description, mergeWithParentFolder);
	}
}