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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
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
public class LiferayLocalRepository extends LiferayBase
	implements LocalRepository {

	public LiferayLocalRepository(long repositoryId) {
		_groupId = repositoryId;
	}

	public FileEntry addFileEntry(
			long userId, long folderId, String title, String description,
			String changeLog, String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.addFileEntry(
			userId, _groupId, folderId, title, description, changeLog,
			extraSettings, is, size, serviceContext);

		return new LiferayFileEntry(fileEntry);
	}

	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder folder = DLRepositoryLocalServiceUtil.addFolder(
			userId, _groupId, parentFolderId, title, description,
			serviceContext);

		return new LiferayFolder(folder);
	}

	public void deleteAll() throws PortalException, SystemException {
		DLRepositoryLocalServiceUtil.deleteAll(_groupId);
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

		List<DLFileEntry> list = DLRepositoryLocalServiceUtil.getFileEntries(
			_groupId, folderId, start, end, obc);

		return convertFileEntries(list);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> list =
			DLRepositoryLocalServiceUtil.getFileEntriesAndFileShortcuts(
				_groupId, folderIds, status, start, end);

		return convertFileEntriesAndFolders(list);
	}

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getFileEntriesAndFileShortcutsCount(
				_groupId, folderIds, status);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return DLRepositoryLocalServiceUtil.getFileEntriesCount(
			_groupId, folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
			fileEntryId);

		return new LiferayFileEntry(fileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
			_groupId, folderId, title);

		return new LiferayFileEntry(fileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry fileEntry =
			DLRepositoryLocalServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, _groupId);

		return new LiferayFileEntry(fileEntry);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion fileVersion = DLRepositoryLocalServiceUtil.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(fileVersion);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder folder = DLRepositoryLocalServiceUtil.getFolder(folderId);

		return new LiferayFolder(folder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder folder = DLRepositoryLocalServiceUtil.getFolder(
			_groupId, parentFolderId, title);

		return new LiferayFolder(folder);
	}

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException {

		List<DLFolder> list = DLRepositoryLocalServiceUtil.getFolders(
			_groupId, parentFolderId, start, end);

		return convertFolders(list);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> list =
			DLRepositoryLocalServiceUtil.
				getFoldersAndFileEntriesAndFileShortcuts(
					_groupId, folderIds, status, start, end);

		return convertFileEntriesAndFolders(list);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				_groupId, folderIds, status);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return DLRepositoryLocalServiceUtil.getFoldersCount(
			_groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getFoldersFileEntriesCount(
			_groupId, folderIds, status);
	}

	public List<FileEntry> getRepositoryFileEntries(
			int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> list =
			DLRepositoryLocalServiceUtil.getGroupFileEntries(
				_groupId, start, end, obc);

		return convertFileEntries(list);
	}

	public List<FileEntry> getRepositoryFileEntries(
			long userId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> list =
			DLRepositoryLocalServiceUtil.getGroupFileEntries(
				_groupId, userId, start, end, obc);

		return convertFileEntries(list);
	}

	public int getRepositoryFileEntriesCount()
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getGroupFileEntriesCount(_groupId);
	}

	public int getRepositoryFileEntriesCount(long userId)
		throws SystemException {

		return DLRepositoryLocalServiceUtil.getGroupFileEntriesCount(
			_groupId, userId);
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);

		return new LiferayFileEntry(fileEntry);
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
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);

		return new LiferayFileEntry(fileEntry);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder folder = DLRepositoryLocalServiceUtil.updateFolder(
			folderId, parentFolderId, title, description, serviceContext);

		return new LiferayFolder(folder);
	}

	private long _groupId;

}