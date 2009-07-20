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

package com.liferay.portlet.documentlibrary.service;

public class DLFileEntryLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.SystemException {
		return getService().addDLFileEntry(dlFileEntry);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry createDLFileEntry(
		long fileEntryId) {
		return getService().createDLFileEntry(fileEntryId);
	}

	public static void deleteDLFileEntry(long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDLFileEntry(fileEntryId);
	}

	public static void deleteDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.SystemException {
		getService().deleteDLFileEntry(dlFileEntry);
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

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDLFileEntry(fileEntryId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getDLFileEntries(start, end);
	}

	public static int getDLFileEntriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getDLFileEntriesCount();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.SystemException {
		return getService().updateDLFileEntry(dlFileEntry);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateDLFileEntry(dlFileEntry, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(userId, folderId, name, title, description,
			extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(userId, folderId, name, title, description,
			extraSettings, file, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(uuid, userId, folderId, name, title,
			description, extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(uuid, userId, folderId, name, title,
			description, extraSettings, is, size, serviceContext);
	}

	public static void addFileEntryResources(long fileEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFileEntryResources(fileEntryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFileEntryResources(fileEntry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFileEntryResources(long fileEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFileEntryResources(fileEntryId, communityPermissions,
			guestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFileEntryResources(fileEntry, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addOrOverwriteFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String sourceName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addOrOverwriteFileEntry(userId, folderId, name, sourceName,
			title, description, extraSettings, file, serviceContext);
	}

	public static void deleteFileEntries(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntries(folderId);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name, version);
	}

	public static void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(fileEntry);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyFileEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyFileEntries(companyId, start, end, obc);
	}

	public static int getCompanyFileEntriesCount(long companyId)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyFileEntriesCount(companyId);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long userId, long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileAsStream(companyId, userId, folderId, name);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long userId, long folderId, java.lang.String name, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getFileAsStream(companyId, userId, folderId, name, version);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId) throws com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId, start, end, obc);
	}

	public static int getFileEntriesCount(long folderId)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntriesCount(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntry(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntry(folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntryByTitle(folderId, titleWithExtension);
	}

	public static int getFoldersFileEntriesCount(java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersFileEntriesCount(folderIds);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, userId, start, end, obc);
	}

	public static int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntriesCount(groupId);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntriesCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.SystemException {
		return getService().getNoAssetFileEntries();
	}

	public static void reIndex(long fileEntryId)
		throws com.liferay.portal.SystemException {
		getService().reIndex(fileEntryId);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateAsset(userId, fileEntry, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFileEntry(userId, folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, file,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFileEntry(userId, folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, bytes,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFileEntry(userId, folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, is, size,
			serviceContext);
	}

	public static DLFileEntryLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("DLFileEntryLocalService is not set");
		}

		return _service;
	}

	public void setService(DLFileEntryLocalService service) {
		_service = service;
	}

	private static DLFileEntryLocalService _service;
}