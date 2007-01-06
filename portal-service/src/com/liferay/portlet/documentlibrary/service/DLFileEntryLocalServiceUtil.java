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

package com.liferay.portlet.documentlibrary.service;

/**
 * <a href="DLFileEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, extraSettings, byteArray,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, extraSettings, byteArray, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, extraSettings, byteArray,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.addFileEntryResources(folderId, name,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.addFileEntryResources(folder, fileEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.addFileEntryResources(folderId, name,
			communityPermissions, guestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.addFileEntryResources(folder, fileEntry,
			communityPermissions, guestPermissions);
	}

	public static void deleteFileEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.deleteFileEntries(folderId);
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.deleteFileEntry(folderId, name, version);
	}

	public static void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
		dlFileEntryLocalService.deleteFileEntry(fileEntry);
	}

	public static java.io.InputStream getFileAsStream(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileAsStream(companyId, userId,
			folderId, name);
	}

	public static java.io.InputStream getFileAsStream(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String folderId, java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileAsStream(companyId, userId,
			folderId, name, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntry(folderId, name);
	}

	public static java.util.List getFileEntries(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntries(folderId);
	}

	public static java.util.List getFileEntries(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntries(folderId, begin, end);
	}

	public static java.util.List getFileEntriesAndShortcuts(
		java.lang.String folderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcuts(folderId,
			begin, end);
	}

	public static java.util.List getFileEntriesAndShortcuts(
		java.util.List folderIds, int begin, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcuts(folderIds,
			begin, end);
	}

	public static int getFileEntriesAndShortcutsCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcutsCount(folderId);
	}

	public static int getFileEntriesAndShortcutsCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcutsCount(folderIds);
	}

	public static int getFileEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesCount(folderId);
	}

	public static int getFoldersFileEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFoldersFileEntriesCount(folderIds);
	}

	public static java.util.List getGroupFileEntries(long groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, begin, end);
	}

	public static java.util.List getGroupFileEntries(long groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			begin, end);
	}

	public static int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
	}

	public static int getGroupFileEntriesCount(long groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntriesCount(groupId, userId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.updateFileEntry(userId, folderId,
			newFolderId, name, sourceFileName, title, description,
			extraSettings, byteArray);
	}
}