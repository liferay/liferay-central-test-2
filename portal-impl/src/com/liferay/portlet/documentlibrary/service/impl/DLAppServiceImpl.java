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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;

import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppServiceImpl extends DLAppServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, file, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, is, size, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long groupId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			groupId, folderId, toFileEntryId, serviceContext);
	}

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.addFolder(
			groupId, parentFolderId, name, description, serviceContext);
	}

	public DLFolder copyFolder(
			long groupId, long sourceFolderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		return dlRepositoryService.copyFolder(
			groupId, sourceFolderId, parentFolderId, name, description,
			serviceContext);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		dlRepositoryService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		dlRepositoryService.deleteFileEntry(groupId, folderId, name, version);
	}

	public void deleteFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		dlRepositoryService.deleteFileEntryByTitle(
			groupId, folderId, titleWithExtension);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		dlRepositoryService.deleteFolder(folderId);
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, RemoteException, SystemException {

		dlRepositoryService.deleteFolder(groupId, parentFolderId, name);
	}

	public InputStream getFileAsStream(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileAsStream(groupId, folderId, name);
	}

	public java.io.InputStream getFileAsStream(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileAsStream(
			groupId, folderId, name, version);
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntries(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntries(
			groupId, folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntries(
			groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlRepositoryService.getFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlRepositoryService.getFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryService.getFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlRepositoryService.getFileEntriesAndFileShortcutsCount(
			groupId, folderId, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntriesCount(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntry(fileEntryId);
	}

	public DLFileEntry getFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntry(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntryByTitle(
			groupId, folderId, titleWithExtension);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public Lock getFileEntryLock(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.getFileEntryLock(groupId, folderId, name);
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlRepositoryService.getFolder(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.getFolder(groupId, parentFolderId, name);
	}

	public long getFolderId(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.getFolderId(groupId, parentFolderId, name);
	}

	public long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		return dlRepositoryService.getFolderIds(groupId, folderId);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlRepositoryService.getFolders(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlRepositoryService.getFolders(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlRepositoryService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		return dlRepositoryService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return
			dlRepositoryService.getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws PortalException, SystemException {

		return
			dlRepositoryService.getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlRepositoryService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryService.getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntries(
			groupId, userId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntries(
			groupId, userId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntries(
			groupId, userId, rootFolderId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntries(
			groupId, userId, rootFolderId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntriesCount(groupId, userId);
	}

	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws SystemException {

		return dlRepositoryService.getGroupFileEntriesCount(
			groupId, userId, rootFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		dlRepositoryService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws SystemException {

		dlRepositoryService.getSubfolderIds(
			folderIds, groupId, folderId, recurse);
	}

	public boolean hasFileEntryLock(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.hasFileEntryLock(groupId, folderId, name);
	}

	public boolean hasInheritableLock(long folderId)
		throws PortalException, SystemException {

		return dlRepositoryService.hasInheritableLock(folderId);
	}

	public Lock lockFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryService.lockFileEntry(groupId, folderId, name);
	}

	public Lock lockFileEntry(
			long groupId, long folderId, String name, String owner,
			long expirationTime)
		throws PortalException, SystemException {

		return dlRepositoryService.lockFileEntry(
			groupId, folderId, name, owner, expirationTime);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		return dlRepositoryService.lockFolder(folderId);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, RemoteException, SystemException {

		return dlRepositoryService.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public DLFileEntry moveFileEntry(
			long groupId, long folderId, long newFolderId, String name,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.moveFileEntry(
			groupId, folderId, newFolderId, name, serviceContext);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlRepositoryService.refreshFileEntryLock(
			lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlRepositoryService.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		dlRepositoryService.revertFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long groupId, long folderId, String name)
		throws SystemException {

		dlRepositoryService.unlockFileEntry(groupId, folderId, name);
	}

	public void unlockFileEntry(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		dlRepositoryService.unlockFileEntry(groupId, folderId, name, lockUuid);
	}

	public void unlockFolder(long groupId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		dlRepositoryService.unlockFolder(groupId, folderId, lockUuid);
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		dlRepositoryService.unlockFolder(
			groupId, parentFolderId, name, lockUuid);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, bytes, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, file, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);

	}

	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public DLFileVersion updateFileVersionDescription(
			long fileVersionId, String description)
		throws PortalException, SystemException {

		return dlRepositoryService.updateFileVersionDescription(
			fileVersionId, description);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		return dlRepositoryService.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	public boolean verifyFileEntryLock(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		return dlRepositoryService.verifyFileEntryLock(
			groupId, folderId, name, lockUuid);
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return dlRepositoryService.verifyInheritableLock(folderId, lockUuid);
	}

}