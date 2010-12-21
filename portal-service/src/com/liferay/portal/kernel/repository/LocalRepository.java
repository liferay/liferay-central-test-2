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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public interface LocalRepository {

	public FileEntry addFileEntry(
			long userId, long folderId, String title, String description,
			String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void addRepository(
			long groupId, String name, String description, String portletKey,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException;

	public void deleteAll() throws PortalException, SystemException;

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public void deleteFolder(long folderId)
		throws PortalException, SystemException;

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException;

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException;

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException;

	public int getFileEntriesCount(long folderId)
		throws SystemException;

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException;

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException;

	public FileVersion getFileVersion(long fileVersionId)
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

	public int getFoldersCount(long parentFolderId)
		throws SystemException;

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException;

	public List<FileEntry> getRepositoryFileEntries(
			int start, int end, OrderByComparator obc)
		throws SystemException;

	public List<FileEntry> getRepositoryFileEntries(
			long userId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public int getRepositoryFileEntriesCount() throws SystemException;

	public int getRepositoryFileEntriesCount(long userId)
		throws SystemException;

	public long getRepositoryId();

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException;

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public UnicodeProperties updateRepository(
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException;

}