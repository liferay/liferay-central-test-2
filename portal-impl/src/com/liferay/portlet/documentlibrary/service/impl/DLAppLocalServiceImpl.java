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

import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.RepositoryFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppLocalServiceImpl extends DLAppLocalServiceBaseImpl {

	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			throw new FileSizeException();
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return addFileEntry(
			userId, repositoryId, folderId, title, description, changeLog,
			extraSettings, is, bytes.length, serviceContext);
	}

	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			if (file == null) {
				throw new FileSizeException();
			}

			InputStream is = new UnsyncBufferedInputStream(
				new FileInputStream(file));

			return addFileEntry(
				userId, repositoryId, folderId, title, description, changeLog,
				extraSettings, is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFileEntry(
			userId, folderId, title, description, changeLog, extraSettings, is,
			size, serviceContext);
	}

	public DLFileRank addFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.addFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long userId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.addFileShortcut(
			userId, repositoryId, folderId, toFileEntryId, serviceContext);
	}

	public Folder addFolder(
			long userId, long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);
	}

	public void deleteAll(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		localRepository.deleteAll();
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		localRepository.deleteFileEntry(fileEntryId);
	}

	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	public void deleteFileRanksByUserId(long userId) throws SystemException {
		dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	public void deleteFileShortcut(DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(dlFileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(long toFileEntryId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcuts(toFileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		localRepository.deleteFolder(folderId);
	}

	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws SystemException {

		return getFileEntries(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws SystemException {

		return getFileEntries(repositoryId, folderId, start, end, null);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntries(folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, List<Long> folderIds, int status, int start,
			int end)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesAndFileShortcuts(
			folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFileEntriesAndFileShortcuts(
			repositoryId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, List<Long> folderIds, int status)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesAndFileShortcutsCount(
			folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFileEntriesAndFileShortcutsCount(
			repositoryId, folderIds, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		return localRepository.getFileEntry(fileEntryId);
	}

	public FileEntry getFileEntry(
			long repositoryId, long folderId, String title)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntry(folderId, title);
	}

	public FileEntry getFileEntryByUuidAndRepositoryId(
			String uuid, long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntryByUuid(uuid);
	}

	public List<DLFileRank> getFileRanks(long repositoryId, long userId)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(repositoryId, userId);
	}

	public List<DLFileRank> getFileRanks(
			long repositoryId, long userId, int start, int end)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(
			repositoryId, userId, start, end);
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(
			0, 0, fileVersionId);

		return localRepository.getFileVersion(fileVersionId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		return localRepository.getFolder(folderId);
	}

	public Folder getFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, name);
	}

	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws SystemException {

		return getFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolders(parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, List<Long> folderIds, int status, int start,
			int end)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersAndFileEntriesAndFileShortcuts(
			folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderIds, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, List<Long> folderIds, int status)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			repositoryId, folderIds, status);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersCount(parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(repositoryId, start, end, null);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, int start, int end, OrderByComparator obc)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getRepositoryFileEntries(start, end, obc);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(repositoryId, userId, start, end, null);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getRepositoryFileEntries(start, end, obc);
	}

	public int getGroupFileEntriesCount(long repositoryId)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getRepositoryFileEntriesCount();
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId)
		throws SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getRepositoryFileEntriesCount(
			userId);
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		return localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(
			0, fileEntry.getFileEntryId(), 0);

		localRepository.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if (bytes != null) {
			is = new UnsyncByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new UnsyncBufferedInputStream(new FileInputStream(file));
				size = file.length();
			}

			return updateFileEntry(
				userId, fileEntryId, sourceFileName, title, description,
				changeLog, majorVersion, extraSettings, is, size,
				serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		return localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFileRank updateFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.updateFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public void updateFileShortcuts(
			long torepositoryId, long oldToFileEntryId, long newToFileEntryId)
		throws SystemException {

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		return localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	protected LocalRepository getLocalRepository(long repositoryId)
		throws RepositoryException {

		return RepositoryFactoryUtil.getLocalRepository(repositoryId);
	}

	protected LocalRepository getLocalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		return RepositoryFactoryUtil.getLocalRepository(
			folderId, fileEntryId, fileVersionId);
	}

}