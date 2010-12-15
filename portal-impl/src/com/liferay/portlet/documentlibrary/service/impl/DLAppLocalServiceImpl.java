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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
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

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			throw new FileSizeException();
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return dlRepositoryLocalService.addFileEntry(
			userId, groupId, folderId, title, description, changeLog,
			extraSettings, is, bytes.length, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			if (file == null) {
				throw new FileSizeException();
			}

			InputStream is = new UnsyncBufferedInputStream(
				new FileInputStream(file));

			return dlRepositoryLocalService.addFileEntry(
				userId, groupId, folderId, title, description, changeLog,
				extraSettings, is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.addFileEntry(
			userId, groupId, folderId, title, description, changeLog,
			extraSettings, is, size, serviceContext);
	}

	public DLFileRank addFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.addFileRank(
			groupId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long userId, long groupId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.addFileShortcut(
			userId, groupId, folderId, toFileEntryId, serviceContext);
	}

	public DLFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.addFolder(
			userId, groupId, parentFolderId, name, description, serviceContext);
	}

	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		dlRepositoryLocalService.deleteAll(groupId);
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		dlRepositoryLocalService.deleteFileEntry(fileEntryId);
	}

	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	public void deleteFileRanksByUserId(long userId) throws SystemException {
		dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
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

		dlRepositoryLocalService.deleteFolder(folderId);
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws SystemException {

		return getFileEntries(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return getFileEntries(groupId, folderId, start, end, null);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlRepositoryLocalService.getFileEntries(
			groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlRepositoryLocalService.getFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryLocalService.getFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFileEntriesAndFileShortcutsCount(groupId, folderIds, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlRepositoryLocalService.getFileEntriesCount(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFileEntry(fileEntryId);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String title)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFileEntryByTitle(
			groupId, folderId, title);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFileEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public List<DLFileRank> getFileRanks(long groupId, long userId)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(groupId, userId);
	}

	public List<DLFileRank> getFileRanks(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(groupId, userId, start, end);
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFileVersion(fileVersionId);
	}

	public DLFileVersion getFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFileVersion(
			fileEntryId, version);
	}

	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws SystemException {

		return dlRepositoryLocalService.getFileVersions(fileEntryId, status);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFolder(folderId);
	}

	public DLFolder getFolder(
		long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.getFolder(
			groupId, parentFolderId, name);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlRepositoryLocalService.getFolders(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlRepositoryLocalService.getFolders(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return
			dlRepositoryLocalService.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryLocalService.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlRepositoryLocalService.getFoldersCount(
			groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryLocalService.getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntries(
			groupId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntries(
			groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntries(
			groupId, userId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntries(
			groupId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		return dlRepositoryLocalService.getGroupFileEntriesCount(
			groupId, userId);
	}

	public DLFileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	public void updateAsset(
			long userId, DLFileEntry fileEntry, DLFileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		dlRepositoryLocalService.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames);
	}

	public DLFileEntry updateFileEntry(
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

		return dlRepositoryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFileEntry updateFileEntry(
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

			return dlRepositoryLocalService.updateFileEntry(
				userId, fileEntryId, sourceFileName, title, description,
				changeLog, majorVersion, extraSettings, is, size,
				serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.updateFileRank(
			groupId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public void updateFileShortcuts(
			long toGroupId, long oldToFileEntryId, long newToFileEntryId)
		throws SystemException {

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlRepositoryLocalService.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

}