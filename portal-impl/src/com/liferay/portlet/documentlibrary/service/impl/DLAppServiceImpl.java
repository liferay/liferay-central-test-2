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

		return dlFileEntryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, file, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.addFileEntry(
			groupId, folderId, name, title, description, changeLog,
			extraSettings, is, size, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long groupId, long folderId, long toFolderId, String toName,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			groupId, folderId, toFolderId, toName, serviceContext);
	}

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFolderService.addFolder(
			groupId, parentFolderId, name, description, serviceContext);
	}

	public DLFolder copyFolder(
			long groupId, long sourceFolderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		return dlFolderService.copyFolder(
			groupId, sourceFolderId, parentFolderId, name, description,
			serviceContext);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(groupId, folderId, name, version);
	}

	public void deleteFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntryByTitle(
			groupId, folderId, titleWithExtension);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		dlFolderService.deleteFolder(folderId);
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, RemoteException, SystemException {

		dlFolderService.deleteFolder(groupId, parentFolderId, name);
	}

	public InputStream getFileAsStream(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileAsStream(groupId, folderId, name);
	}

	public java.io.InputStream getFileAsStream(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileAsStream(
			groupId, folderId, name, version);
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntries(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntries(groupId, folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntries(
			groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderService.getFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderService.getFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			groupId, folderId, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntriesCount(groupId, folderId);
	}

	public DLFileEntry getFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntry(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntryByTitle(
			groupId, folderId, titleWithExtension);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public Lock getFileEntryLock(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryService.getFileEntryLock(groupId, folderId, name);
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderService.getFolder(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderService.getFolder(groupId, parentFolderId, name);
	}

	public long getFolderId(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderService.getFolderId(groupId, parentFolderId, name);
	}

	public long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		return dlFolderService.getFolderIds(groupId, folderId);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderService.getFolders(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlFolderService.getFolders(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws PortalException, SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFileEntryService.getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntries(
			groupId, userId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntries(
			groupId, userId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntries(
			groupId, userId, rootFolderId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntries(
			groupId, userId, rootFolderId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntriesCount(groupId, userId);
	}

	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntriesCount(
			groupId, userId, rootFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		dlFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws SystemException {

		dlFolderService.getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public boolean hasFileEntryLock(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryService.hasFileEntryLock(groupId, folderId, name);
	}

	public boolean hasInheritableLock(long folderId)
		throws PortalException, SystemException {

		return dlFolderService.hasInheritableLock(folderId);
	}

	public Lock lockFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryService.lockFileEntry(groupId, folderId, name);
	}

	public Lock lockFileEntry(
			long groupId, long folderId, String name, String owner,
			long expirationTime)
		throws PortalException, SystemException {

		return dlFileEntryService.lockFileEntry(
			groupId, folderId, name, owner, expirationTime);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		return dlFolderService.lockFolder(folderId);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, RemoteException, SystemException {

		return dlFolderService.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public DLFileEntry moveFileEntry(
			long groupId, long folderId, long newFolderId, String name,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.moveFileEntry(
			groupId, folderId, newFolderId, name, serviceContext);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlFileEntryService.refreshFileEntryLock(
			lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlFolderService.refreshFolderLock(lockUuid, expirationTime);
	}

	public void unlockFileEntry(long groupId, long folderId, String name)
		throws SystemException {

		dlFileEntryService.unlockFileEntry(groupId, folderId, name);
	}

	public void unlockFileEntry(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		dlFileEntryService.unlockFileEntry(groupId, folderId, name, lockUuid);
	}

	public void unlockFolder(long groupId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(groupId, folderId, lockUuid);
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, bytes, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, file, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryService.updateFileEntry(
			groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);

	}

	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFolderId, String toName,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFolderId, toName,
			serviceContext);
	}

	public DLFileVersion updateFileVersionDescription(
			long fileVersionId, String description)
		throws PortalException, SystemException {

		return dlFileVersionService.updateDescription(
			fileVersionId, description);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		return dlFolderService.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	public boolean verifyFileEntryLock(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryService.verifyFileEntryLock(
			groupId, folderId, name, lockUuid);
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return dlFolderService.verifyInheritableLock(folderId, lockUuid);
	}

}