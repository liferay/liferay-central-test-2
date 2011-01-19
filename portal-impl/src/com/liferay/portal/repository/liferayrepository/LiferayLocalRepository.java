/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class LiferayLocalRepository
	extends LiferayRepositoryBase implements LocalRepository {

	public LiferayLocalRepository(long repositoryId) {
		super(repositoryId);
	}

	public LiferayLocalRepository(
		long folderId, long fileEntryId, long fileVersionId) {

		super(folderId, fileEntryId, fileVersionId);
	}

	public FileEntry addFileEntry(
			long userId, long folderId, String title, String description,
			String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.addFileEntry(
			userId, getGroupId(), toFolderId(folderId), title, description,
			changeLog, is, size, serviceContext);

		return toFileEntry(dlFileEntry);
	}

	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryLocalServiceUtil.addFolder(
			userId, getGroupId(), toFolderId(parentFolderId), title,
			description, serviceContext);

		return toFolder(dlFolder);
	}

	public void addRepository(
			long groupId, String name, String description, String portletKey,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {
	}

	public void deleteAll() throws PortalException, SystemException {
		DLRepositoryLocalServiceUtil.deleteAll(getGroupId());
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLRepositoryLocalServiceUtil.deleteFileEntry(fileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLRepositoryLocalServiceUtil.deleteFolder(folderId);
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			DLRepositoryLocalServiceUtil.getFileEntries(
				getGroupId(), toFolderId(folderId), start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> dlFileEntriesAndFileShortcuts =
			DLRepositoryLocalServiceUtil.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderIds(folderIds), status, start, end);

		return toFileEntriesAndFolders(dlFileEntriesAndFileShortcuts);
	}

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return DLRepositoryLocalServiceUtil.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
			fileEntryId);

		return toFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return toFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			DLRepositoryLocalServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, getGroupId());

		return toFileEntry(dlFileEntry);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion =
			DLRepositoryLocalServiceUtil.getFileVersion(fileVersionId);

		return toFileVersion(dlFileVersion);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryLocalServiceUtil.getFolder(folderId);

		return toFolder(dlFolder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryLocalServiceUtil.getFolder(
			getGroupId(), toFolderId(parentFolderId), title);

		return toFolder(dlFolder);
	}

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException {

		List<DLFolder> dlFolders = DLRepositoryLocalServiceUtil.getFolders(
			getGroupId(), toFolderId(parentFolderId), start, end);

		return toFolders(dlFolders);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			DLRepositoryLocalServiceUtil.
				getFoldersAndFileEntriesAndFileShortcuts(
					getGroupId(), toFolderIds(folderIds), status, start, end);

		return toFileEntriesAndFolders(dlFoldersAndFileEntriesAndFileShortcuts);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				getGroupId(), toFolderIds(folderIds), status);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return DLRepositoryLocalServiceUtil.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId));
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getFoldersFileEntriesCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	public List<FileEntry> getRepositoryFileEntries(
			int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			DLRepositoryLocalServiceUtil.getGroupFileEntries(
				getGroupId(), start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public List<FileEntry> getRepositoryFileEntries(
			long userId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			DLRepositoryLocalServiceUtil.getGroupFileEntries(
				getGroupId(), userId, start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public int getRepositoryFileEntriesCount()
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getGroupFileEntriesCount(
			getGroupId());
	}

	public int getRepositoryFileEntriesCount(long userId)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getGroupFileEntriesCount(
			getGroupId(), userId);
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.moveFileEntry(
			userId, fileEntryId, toFolderId(newFolderId), serviceContext);

		return toFileEntry(dlFileEntry);
	}

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();
		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		DLRepositoryLocalServiceUtil.updateAsset(
			userId, dlFileEntry, dlFileVersion, assetCategoryIds,
			assetTagNames);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, is, size, serviceContext);

		return toFileEntry(dlFileEntry);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLRepositoryLocalServiceUtil.updateFolder(
			toFolderId(folderId), toFolderId(parentFolderId), title,
			description, serviceContext);

		return toFolder(dlFolder);
	}

	public UnicodeProperties updateRepository(
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		return typeSettingsProperties;
	}

}