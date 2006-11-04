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

package com.liferay.portlet.documentlibrary.service.spring;

/**
 * <a href="DLFileShortcutLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileShortcutLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String toFolderId, java.lang.String toName,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();

		return dlFileShortcutLocalService.addFileShortcut(userId, folderId,
			toFolderId, toName, addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileShortcutResources(long fileShortcutId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.addFileShortcutResources(folder,
			fileShortcut, addCommunityPermissions, addGuestPermissions);
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	public static void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	public static void deleteFileShortcuts(java.lang.String toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.deleteFileShortcuts(toFolderId, toName);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		java.lang.String userId, long fileShortcutId,
		java.lang.String folderId, java.lang.String toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();

		return dlFileShortcutLocalService.updateFileShortcut(userId,
			fileShortcutId, folderId, toFolderId, toName);
	}

	public static void updateFileShortcuts(java.lang.String oldToFolderId,
		java.lang.String oldToName, java.lang.String newToFolderId,
		java.lang.String newToName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalService dlFileShortcutLocalService = DLFileShortcutLocalServiceFactory.getService();
		dlFileShortcutLocalService.updateFileShortcuts(oldToFolderId,
			oldToName, newToFolderId, newToName);
	}
}