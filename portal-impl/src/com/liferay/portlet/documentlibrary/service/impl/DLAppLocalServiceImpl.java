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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppLocalServiceImpl extends DLAppLocalServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.addFileEntry(
			userId, groupId, folderId, name, title, description, changeLog,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.addFileEntry(
			userId, groupId, folderId, name, title, description, changeLog,
			extraSettings, file, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.addFileEntry(
			userId, groupId, folderId, name, title, description, changeLog,
			extraSettings, is, size, serviceContext);
	}

	public DLFileRank addFileRank(
			long groupId, long companyId, long userId, long folderId,
			String name, ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.addFileRank(
			groupId, companyId, userId, folderId, name, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long userId, long groupId, long folderId, long toFolderId,
			String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.addFileShortcut(
			userId, groupId, folderId, toFolderId, toName, serviceContext);
	}

	public DLFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFolderLocalService.addFolder(
			userId, groupId, parentFolderId, name, description, serviceContext);
	}

	public DLFileEntry addOrOverwriteFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceName, String title, String description,
			String changeLog, String extraSettings, java.io.File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.addOrOverwriteFileEntry(
			userId, groupId, folderId, name, sourceName, title, description,
			changeLog, extraSettings, file, serviceContext);
	}

	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		dlFileEntryLocalService.deleteFileEntries(groupId, folderId);
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		dlFileEntryLocalService.deleteFileEntry(fileEntry);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		dlFileEntryLocalService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		dlFileEntryLocalService.deleteFileEntry(
			groupId, folderId, name, version);
	}

	public void deleteFileRanks(long userId) throws SystemException {
		dlFileRankLocalService.deleteFileRanks(userId);
	}

	public void deleteFileRanks(long folderId, String name)
		throws SystemException {

		dlFileRankLocalService.deleteFileRanks(folderId, name);
	}

	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(
			long groupId, long toFolderId, String toName)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcuts(
			groupId, toFolderId, toName);
	}

	public void deleteFolder(DLFolder folder)
		throws PortalException, SystemException {

		dlFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		dlFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		dlFolderLocalService.deleteFolders(groupId);
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end)
		throws SystemException {

		return dlFileEntryLocalService.getCompanyFileEntries(
			companyId, start, end);
	}

	public java.util.List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryLocalService.getCompanyFileEntries(
			companyId, start, end, obc);
	}

	public int getCompanyFileEntriesCount(long companyId)
		throws SystemException {

		return dlFileEntryLocalService.getCompanyFileEntriesCount(companyId);
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getCompanyFolders(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws SystemException {

		return dlFolderLocalService.getCompanyFoldersCount(companyId);
	}

	public List<DLFileEntry> getDLFileEntries(int start, int end)
		throws SystemException {

		return dlFileEntryLocalService.getDLFileEntries(start, end);
	}

	public int getDLFileEntriesCount() throws SystemException {
		return dlFileEntryLocalService.getDLFileEntriesCount();
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileAsStream(
			companyId, userId, groupId, folderId, name);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name, String version)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileAsStream(
			companyId, userId, groupId, folderId, name, version);
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryLocalService.getFileEntries(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return dlFileEntryLocalService.getFileEntries(
			groupId, folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {

		return dlFileEntryLocalService.getFileEntries(
				groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderLocalService.getFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderLocalService.getFileEntriesAndFileShortcutsCount(
			groupId, folderId, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryLocalService.getFileEntriesCount(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileEntry(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String title)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileEntryByTitle(
			groupId, folderId, title);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileEntryByUuidAndGroupId(
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

		return dlFileEntryLocalService.getFileVersion(fileVersionId);
	}

	public DLFileVersion getFileVersion(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getFileVersion(
			groupId, folderId, name, version);
	}

	public List<DLFileVersion> getFileVersions(
		long groupId, long folderId, String name, int status)
		throws SystemException {

		return dlFileEntryLocalService.getFileVersions(
			groupId, folderId, name, status);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderLocalService.getFolder(folderId);
	}

	public DLFolder getFolder(
		long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public List<DLFolder> getFolders(long companyId) throws SystemException {
		return dlFolderLocalService.getFolders(companyId);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getFolders(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return
			dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return
			dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(
				groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {

		return dlFileEntryLocalService.getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntries(groupId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntries(
			groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntries(
			groupId, userId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntries(
			groupId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		return dlFileEntryLocalService.getGroupFileEntriesCount(
			groupId, userId);
	}

	public DLFileVersion getLatestFileVersion(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.getLatestFileVersion(
			groupId, folderId, name);
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryLocalService.getNoAssetFileEntries();
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		dlFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public DLFileEntry moveFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.moveFileEntry(
			userId, groupId, folderId, newFolderId, name, serviceContext);
	}

	public void updateAsset(
			long userId, DLFileEntry fileEntry, DLFileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		dlFileEntryLocalService.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames);
	}

	public void updateAsset(
			long userId, DLFileShortcut fileShortcut, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.updateAsset(
			userId, fileShortcut, assetCategoryIds, assetTagNames);
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.updateFileEntry(
			userId, groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, bytes, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.updateFileEntry(
			userId, groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, file, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.updateFileEntry(
			userId, groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long folderId,
			String name, ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.updateFileRank(
			groupId, companyId, userId, folderId, name, serviceContext);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFolderId,
			String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.updateFileShortcut(
			userId, fileShortcutId, folderId, toFolderId, toName,
			serviceContext);
	}

	public void updateFileShortcuts(
			long groupId, long oldToFolderId, String oldToName,
			long newToFolderId, String newToName)
		throws SystemException {

		dlFileShortcutLocalService.updateFileShortcuts(
			groupId, oldToFolderId, oldToName, newToFolderId, newToName);
	}

	public DLFileVersion updateFileVersionDescription(
			long fileVersionId, String description)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.updateFileVersionDescription(
			fileVersionId, description);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	public DLFileEntry updateStatus(
			long userId, long fileEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.updateStatus(
			userId, fileEntryId, status, serviceContext);
	}

}