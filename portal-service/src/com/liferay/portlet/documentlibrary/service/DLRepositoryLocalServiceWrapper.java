/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLRepositoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLRepositoryLocalService
 * @generated
 */
public class DLRepositoryLocalServiceWrapper implements DLRepositoryLocalService {
	public DLRepositoryLocalServiceWrapper(
		DLRepositoryLocalService dlRepositoryLocalService) {
		_dlRepositoryLocalService = dlRepositoryLocalService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long repositoryId, long folderId,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.addFileEntry(userId, groupId,
			repositoryId, folderId, title, description, changeLog, is, size,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.addFolder(userId, groupId,
			repositoryId, parentFolderId, name, description, serviceContext);
	}

	public void convertExtraSettings(java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.convertExtraSettings(keys);
	}

	public void deleteAll(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.deleteAll(groupId);
	}

	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.deleteFileEntry(fileEntryId);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.deleteFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getCompanyFolders(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getCompanyFoldersCount(companyId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getExtraSettingsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getExtraSettingsFileEntries(start, end);
	}

	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileAsStream(userId, fileEntryId,
			version);
	}

	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version, boolean count)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileAsStream(userId, fileEntryId,
			version, count);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntries(start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntries(groupId, folderId,
			start, end, obc);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public int getFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntriesCount();
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntriesCount(groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntry(fileEntryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntry(groupId, folderId, title);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileVersion(fileVersionId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileVersion(fileEntryId, version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFileVersions(fileEntryId, status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFolder(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFolder(groupId, parentFolderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolderByRepositoryId(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFolderByRepositoryId(repositoryId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFolders(groupId, parentFolderId,
			start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getFoldersFileEntriesCount(groupId,
			folderIds, status);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntries(groupId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntries(groupId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntries(groupId, userId,
			start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getGroupFileEntriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getLatestFileVersion(fileEntryId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getNoAssetFileEntries();
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getOrphanedFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.getOrphanedFileEntries();
	}

	public boolean hasExtraSettings()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.hasExtraSettings();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.moveFileEntry(userId, fileEntryId,
			newFolderId, serviceContext);
	}

	public void revertFileEntry(long userId, long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.revertFileEntry(userId, fileEntryId, version,
			serviceContext);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlRepositoryLocalService.updateAsset(userId, dlFileEntry,
			dlFileVersion, assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.updateFileEntry(userId, fileEntryId,
			sourceFileName, title, description, changeLog, majorVersion, is,
			size, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.moveFolder(folderId, parentFolderId,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.updateFolder(folderId, name,
			description, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.updateFolder(folderId, parentFolderId,
			name, description, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateStatus(
		long userId, long fileEntryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlRepositoryLocalService.updateStatus(userId, fileEntryId,
			status, serviceContext);
	}

	public DLRepositoryLocalService getWrappedDLRepositoryLocalService() {
		return _dlRepositoryLocalService;
	}

	public void setWrappedDLRepositoryLocalService(
		DLRepositoryLocalService dlRepositoryLocalService) {
		_dlRepositoryLocalService = dlRepositoryLocalService;
	}

	private DLRepositoryLocalService _dlRepositoryLocalService;
}