/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
 * <a href="BookmarksFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
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
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

			return bookmarksFolderLocalService.addFolder(userId, plid,
				parentFolderId, name, description, addCommunityPermissions,
				addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addFolderResources(java.lang.String folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.addFolderResources(folderId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.addFolderResources(folder,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFolder(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.deleteFolder(folderId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.deleteFolder(folder);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFolders(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.deleteFolders(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

			return bookmarksFolderLocalService.getFolder(folderId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getFolders(java.lang.String groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

			return bookmarksFolderLocalService.getFolders(groupId,
				parentFolderId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getFoldersCount(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

			return bookmarksFolderLocalService.getFoldersCount(groupId,
				parentFolderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void getSubfolderIds(java.util.List folderIds,
		java.lang.String groupId, java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();
			bookmarksFolderLocalService.getSubfolderIds(folderIds, groupId,
				folderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		java.lang.String companyId, java.lang.String folderId,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksFolderLocalService bookmarksFolderLocalService = BookmarksFolderLocalServiceFactory.getService();

			return bookmarksFolderLocalService.updateFolder(companyId,
				folderId, parentFolderId, name, description);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}