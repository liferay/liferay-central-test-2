/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public interface Repository extends DocumentRepository {

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, File, ServiceContext)}
	 */
	@Deprecated
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addFolder(long, long,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	public Folder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #checkInFileEntry(long, long,
	 *             boolean, String, ServiceContext)}
	 */
	@Deprecated
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #checkInFileEntry(long, long,
	 *             String, com.liferay.portal.service.ServiceContext)}
	 */
	@Deprecated
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyFileEntry(long, long,
	 *             long, long, ServiceContext)}
	 */
	@Deprecated
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteFileEntry(long folderId, String title)
		throws PortalException;

	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException;

	public void deleteFolder(long parentFolderId, String name)
		throws PortalException;

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException;

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException;

	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException;

	public int getFileEntriesCount(long folderId) throws PortalException;

	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException;

	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException;

	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException;

	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException;

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException;

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders)
		throws PortalException;

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException;

	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException;

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException;

	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException;

	public int getMountFoldersCount(long parentFolderId) throws PortalException;

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException;

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException;

	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException;

	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException;

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	public Lock lockFileEntry(long fileEntryId) throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Deprecated
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException;

	public Lock lockFolder(long folderId) throws PortalException;

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #moveFileEntry(long, long,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #moveFolder(long, long, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException;

	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #revertFileEntry(long, long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException;

	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException;

	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException;

	public Hits search(SearchContext searchContext) throws SearchException;

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException;

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException;

	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean, File,
	 *             ServiceContext)}
	 */
	@Deprecated
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean, InputStream,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException;

	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException;

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException;

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException;

}