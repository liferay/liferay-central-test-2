/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link DLAppLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLAppLocalService
 * @generated
 */
public class DLAppLocalServiceWrapper implements DLAppLocalService {
	public DLAppLocalServiceWrapper(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFileEntry(userId, repositoryId, folderId,
			title, description, changeLog, extraSettings, bytes, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFileEntry(userId, repositoryId, folderId,
			title, description, changeLog, extraSettings, file, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFileEntry(userId, repositoryId, folderId,
			title, description, changeLog, extraSettings, is, size,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFileRank(repositoryId, companyId, userId,
			fileEntryId, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFileShortcut(userId, repositoryId,
			folderId, toFileEntryId, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.Folder addFolder(
		long userId, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.addFolder(userId, repositoryId,
			parentFolderId, name, description, serviceContext);
	}

	public void deleteAll(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteAll(repositoryId);
	}

	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileEntry(fileEntryId);
	}

	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	public void deleteFileRanksByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileRanksByUserId(userId);
	}

	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileShortcut(dlFileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFileShortcuts(toFileEntryId);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.deleteFolder(folderId);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntries(repositoryId, folderId);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntries(repositoryId, folderId, start,
			end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntries(repositoryId, folderId, start,
			end, obc);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntriesAndFileShortcuts(repositoryId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntriesAndFileShortcuts(repositoryId,
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntriesAndFileShortcutsCount(repositoryId,
			folderId, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntriesCount(repositoryId, folderId);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntry(fileEntryId);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long repositoryId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntry(repositoryId, folderId, title);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndRepositoryId(
		java.lang.String uuid, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileEntryByUuidAndRepositoryId(uuid,
			repositoryId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileRanks(repositoryId, userId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long repositoryId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileRanks(repositoryId, userId, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileShortcut(fileShortcutId);
	}

	public com.liferay.portal.kernel.repository.model.FileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFileVersion(fileVersionId);
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFolder(folderId);
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFolder(repositoryId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFolders(repositoryId, parentFolderId);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFolders(repositoryId, parentFolderId,
			start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderId, status);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersCount(repositoryId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getFoldersFileEntriesCount(repositoryId,
			folderIds, status);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntries(repositoryId, start, end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntries(repositoryId, start, end,
			obc);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntries(repositoryId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntries(repositoryId, userId,
			start, end, obc);
	}

	public int getGroupFileEntriesCount(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntriesCount(repositoryId);
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.getGroupFileEntriesCount(repositoryId, userId);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		long userId, long repositoryId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.moveFileEntry(userId, repositoryId,
			fileEntryId, newFolderId, serviceContext);
	}

	public void updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFileEntry(userId, fileEntryId,
			sourceFileName, title, description, changeLog, majorVersion,
			extraSettings, bytes, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFileEntry(userId, fileEntryId,
			sourceFileName, title, description, changeLog, majorVersion,
			extraSettings, file, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFileEntry(userId, fileEntryId,
			sourceFileName, title, description, changeLog, majorVersion,
			extraSettings, is, size, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFileRank(repositoryId, companyId,
			userId, fileEntryId, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFileShortcut(userId, fileShortcutId,
			folderId, toFileEntryId, serviceContext);
	}

	public void updateFileShortcuts(long torepositoryId, long oldToFileEntryId,
		long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppLocalService.updateFileShortcuts(torepositoryId,
			oldToFileEntryId, newToFileEntryId);
	}

	public com.liferay.portal.kernel.repository.model.Folder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppLocalService.updateFolder(folderId, parentFolderId, name,
			description, serviceContext);
	}

	public DLAppLocalService getWrappedDLAppLocalService() {
		return _dlAppLocalService;
	}

	public void setWrappedDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private DLAppLocalService _dlAppLocalService;
}