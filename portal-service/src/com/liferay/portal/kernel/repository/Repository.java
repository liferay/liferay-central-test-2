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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.List;

/**
 * @author Alexander Chow
 */
public interface Repository {

	public FileEntry addFileEntry(
			long folderId, String title, String description, String changeLog,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder copyFolder(
			long sourceFolderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException;

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException;

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException;

	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, RemoteException, SystemException;

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException;

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException;

	public int getFileEntriesCount(long folderId)
		throws PortalException, SystemException;

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException;

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException;

	public Folder getFolder(long folderId)
		throws PortalException, SystemException;

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException;

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException;

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException;

	public int getFoldersCount(long parentFolderId) throws SystemException;

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException;

	public long getRepositoryId();

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException;

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws SystemException;

	public void getSubfolderIds(
			List<Long> folderIds, long folderId, boolean recurse)
		throws SystemException;

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException;

	public Lock lockFolder(long folderId)
		throws PortalException, RemoteException, SystemException;

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, RemoteException, SystemException;

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException;

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException;

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void unlockFileEntry(long fileEntryId) throws SystemException;

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException;

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException;

	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException;

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException;

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException;

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException;

}