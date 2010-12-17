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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLRepositoryServiceUtil;

import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class LiferayRepository extends LiferayBase implements Repository {

	public LiferayRepository(long repositoryId) {
		_groupId = repositoryId;
	}

	public FileEntry addFileEntry(
			long folderId, String title, String description, String changeLog,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryServiceUtil.addFileEntry(
			_groupId, folderId, title, description, changeLog, extraSettings,
			is, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryServiceUtil.addFolder(
			_groupId, parentFolderId, title, description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public Folder copyFolder(
			long sourceFolderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws RemoteException, PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryServiceUtil.copyFolder(
			_groupId, sourceFolderId, parentFolderId, title, description,
			serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.deleteFileEntry(_groupId, folderId, title);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException, RemoteException {

		DLRepositoryServiceUtil.deleteFolder(folderId);
	}

	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException, RemoteException {

		DLRepositoryServiceUtil.deleteFolder(_groupId, parentFolderId, title);
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			DLRepositoryServiceUtil.getFileEntries(
				_groupId, folderId, start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> dlFileEntriesAndFileShortcuts =
			DLRepositoryServiceUtil.getFileEntriesAndFileShortcuts(
				_groupId, folderIds, status, start, end);

		return toFileEntriesAndFolders(dlFileEntriesAndFileShortcuts);
	}

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryServiceUtil.getFileEntriesAndFileShortcutsCount(
			_groupId, folderIds, status);
	}

	public int getFileEntriesCount(long folderId)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.getFileEntriesCount(_groupId, folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryServiceUtil.getFileEntry(
			fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryServiceUtil.getFileEntry(
			_groupId, folderId, title);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			DLRepositoryServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, _groupId);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Lock getFileEntryLock(long fileEntryId) {
		return DLRepositoryServiceUtil.getFileEntryLock(fileEntryId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryServiceUtil.getFolder(folderId);

		return new LiferayFolder(dlFolder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryServiceUtil.getFolder(
			_groupId, parentFolderId, title);

		return new LiferayFolder(dlFolder);
	}

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException {

		List<DLFolder> dlFolders = DLRepositoryServiceUtil.getFolders(
			_groupId, parentFolderId, start, end);

		return toFolders(dlFolders);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			DLRepositoryServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_groupId, folderIds, status, start, end);

		return toFileEntriesAndFolders(dlFoldersAndFileEntriesAndFileShortcuts);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryServiceUtil.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				_groupId, folderIds, status);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return DLRepositoryServiceUtil.getFoldersCount(
			_groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryServiceUtil.getFoldersFileEntriesCount(
			_groupId, folderIds, status);
	}

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			DLRepositoryServiceUtil.getGroupFileEntries(
				_groupId, userId, rootFolderId, start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws SystemException {

		return DLRepositoryServiceUtil.getGroupFileEntriesCount(
			_groupId, userId, rootFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long folderId, boolean recurse)
		throws SystemException {

		DLRepositoryServiceUtil.getSubfolderIds(
			folderIds, _groupId, folderId, recurse);
	}

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.lockFileEntry(fileEntryId);
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.lockFileEntry(
			fileEntryId, owner, expirationTime);
	}

	public Lock lockFolder(long folderId)
		throws RemoteException, PortalException, SystemException {

		return DLRepositoryServiceUtil.lockFolder(folderId);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws RemoteException, PortalException, SystemException {

		return DLRepositoryServiceUtil.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryServiceUtil.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.refreshFileEntryLock(
			lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.refreshFolderLock(
			lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	public void unlockFileEntry(long fileEntryId) throws SystemException {
		DLRepositoryServiceUtil.unlockFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.unlockFileEntry(fileEntryId, lockUuid);
	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.unlockFolder(_groupId, folderId, lockUuid);
	}

	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException {

		DLRepositoryServiceUtil.unlockFolder(
			_groupId, parentFolderId, title, lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws RemoteException, PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryServiceUtil.updateFolder(
			folderId, parentFolderId, title, description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.verifyFileEntryLock(
			fileEntryId, lockUuid);
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return DLRepositoryServiceUtil.verifyInheritableLock(
			folderId, lockUuid);
	}

	private long _groupId;

}