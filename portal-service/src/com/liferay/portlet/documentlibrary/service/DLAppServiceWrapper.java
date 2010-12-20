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
 * This class is a wrapper for {@link DLAppService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLAppService
 * @generated
 */
public class DLAppServiceWrapper implements DLAppService {
	public DLAppServiceWrapper(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.addFileEntry(repositoryId, folderId, title,
			description, changeLog, extraSettings, bytes, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.addFileEntry(repositoryId, folderId, title,
			description, changeLog, extraSettings, file, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.addFileEntry(repositoryId, folderId, title,
			description, changeLog, extraSettings, is, size, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.addFileShortcut(repositoryId, folderId,
			toFileEntryId, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.Folder addFolder(
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.addFolder(repositoryId, parentFolderId, name,
			description, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.Folder copyFolder(
		long repositoryId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return _dlAppService.copyFolder(repositoryId, sourceFolderId,
			parentFolderId, name, description, serviceContext);
	}

	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntryByTitle(long repositoryId, long folderId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.deleteFileEntryByTitle(repositoryId, folderId, title);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		_dlAppService.deleteFolder(folderId);
	}

	public void deleteFolder(long repositoryId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		_dlAppService.deleteFolder(repositoryId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntries(repositoryId, folderId);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntries(repositoryId, folderId, start, end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntries(repositoryId, folderId, start, end,
			obc);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntriesAndFileShortcuts(repositoryId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntriesAndFileShortcuts(repositoryId,
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntriesAndFileShortcutsCount(repositoryId,
			folderId, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntriesCount(repositoryId, folderId);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntry(fileEntryId);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long repositoryId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntry(repositoryId, folderId, title);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndRepositoryId(
		java.lang.String uuid, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileEntryByUuidAndRepositoryId(uuid,
			repositoryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFileShortcut(fileShortcutId);
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFolder(folderId);
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFolder(repositoryId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFolders(repositoryId, parentFolderId);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFolders(repositoryId, parentFolderId, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderId, status);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersCount(repositoryId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getFoldersFileEntriesCount(repositoryId,
			folderIds, status);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntries(repositoryId, userId, start,
			end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntries(repositoryId, userId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntries(repositoryId, userId,
			rootFolderId, start, end);
	}

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntries(repositoryId, userId,
			rootFolderId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntriesCount(repositoryId, userId);
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId,
		long rootFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.getGroupFileEntriesCount(repositoryId, userId,
			rootFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.getSubfolderIds(folderIds, repositoryId, folderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long repositoryId, long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.getSubfolderIds(folderIds, repositoryId, folderId, recurse);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.lockFileEntry(fileEntryId);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long fileEntryId,
		java.lang.String owner, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.lockFileEntry(fileEntryId, owner, expirationTime);
	}

	public com.liferay.portal.model.Lock lockFolder(long repositoryId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return _dlAppService.lockFolder(repositoryId, folderId);
	}

	public com.liferay.portal.model.Lock lockFolder(long repositoryId,
		long folderId, java.lang.String owner, boolean inheritable,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return _dlAppService.lockFolder(repositoryId, folderId, owner,
			inheritable, expirationTime);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.moveFileEntry(fileEntryId, newFolderId,
			serviceContext);
	}

	public com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public com.liferay.portal.model.Lock refreshFolderLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(long fileEntryId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.revertFileEntry(fileEntryId, version, serviceContext);
	}

	public void unlockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.unlockFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.unlockFileEntry(fileEntryId, lockUuid);
	}

	public void unlockFolder(long repositoryId, long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.unlockFolder(repositoryId, folderId, lockUuid);
	}

	public void unlockFolder(long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppService.unlockFolder(repositoryId, parentFolderId, name, lockUuid);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.updateFileEntry(fileEntryId, sourceFileName,
			title, description, changeLog, majorVersion, extraSettings, bytes,
			serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.updateFileEntry(fileEntryId, sourceFileName,
			title, description, changeLog, majorVersion, extraSettings, file,
			serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.updateFileEntry(fileEntryId, sourceFileName,
			title, description, changeLog, majorVersion, extraSettings, is,
			size, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.updateFileShortcut(fileShortcutId, folderId,
			toFileEntryId, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.Folder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return _dlAppService.updateFolder(folderId, parentFolderId, name,
			description, serviceContext);
	}

	public boolean verifyFileEntryLock(long repositoryId, long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.verifyFileEntryLock(repositoryId, fileEntryId,
			lockUuid);
	}

	public boolean verifyInheritableLock(long repositoryId, long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppService.verifyInheritableLock(repositoryId, folderId,
			lockUuid);
	}

	public DLAppService getWrappedDLAppService() {
		return _dlAppService;
	}

	public void setWrappedDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	private DLAppService _dlAppService;
}