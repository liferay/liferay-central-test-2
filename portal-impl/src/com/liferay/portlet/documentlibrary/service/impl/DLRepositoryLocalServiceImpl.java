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

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.util.JCRHook;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLRepositoryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * For DLFileEntries, the naming convention for some of the variables is not
 * very informative, due to legacy code. Each DLFileEntry has a corresponding
 * name and title. The "name" is a unique identifier for a given file and
 * usually follows the format "1234" whereas the "title" is the actual name
 * specified by the user (e.g., "Budget.xls").
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Alexander Chow
 */
public class DLRepositoryLocalServiceImpl
	extends DLRepositoryLocalServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		String name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));
		String extension = FileUtil.getExtension(title);
		Date now = new Date();

		validateFile(groupId, folderId, title, is);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry dlFileEntry = dlFileEntryPersistence.create(fileEntryId);

		dlFileEntry.setUuid(serviceContext.getUuid());
		dlFileEntry.setGroupId(groupId);
		dlFileEntry.setCompanyId(user.getCompanyId());
		dlFileEntry.setUserId(user.getUserId());
		dlFileEntry.setUserName(user.getFullName());
		dlFileEntry.setVersionUserId(user.getUserId());
		dlFileEntry.setVersionUserName(user.getFullName());
		dlFileEntry.setCreateDate(serviceContext.getCreateDate(now));
		dlFileEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setName(name);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
		dlFileEntry.setExtraSettings(extraSettings);
		dlFileEntry.setVersion(DLFileEntryConstants.DEFAULT_VERSION);
		dlFileEntry.setSize(size);
		dlFileEntry.setReadCount(DLFileEntryConstants.DEFAULT_READ_COUNT);

		dlFileEntryPersistence.update(dlFileEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFileEntryResources(
				dlFileEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileEntryResources(
				dlFileEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// File version

		DLFileVersion dlFileVersion = addFileVersion(
			user, dlFileEntry, serviceContext.getModifiedDate(now), extension,
			title, description, null, extraSettings,
			DLFileEntryConstants.DEFAULT_VERSION, size,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			folder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// Asset

		updateAsset(
			userId, dlFileEntry, dlFileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// DLApp

		dlAppHelperLocalService.addFileEntry(
			new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(dlFileVersion), serviceContext);

		// File

		dlLocalService.addFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			dlFileEntry.getGroupId(), dlFileEntry.getDataRepositoryId(), name,
			false, fileEntryId, dlFileEntry.getLuceneProperties(),
			dlFileEntry.getModifiedDate(), serviceContext, is);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, DLFileEntry.class.getName(),
			fileEntryId, dlFileEntry, serviceContext);

		return dlFileEntry;
	}

	public DLFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validateFolder(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		DLFolder folder = dlFolderPersistence.create(folderId);

		folder.setUuid(serviceContext.getUuid());
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(serviceContext.getCreateDate(now));
		folder.setModifiedDate(serviceContext.getModifiedDate(now));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(folder, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFolderResources(
				folder, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFolderResources(
				folder, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentFolder = dlFolderPersistence.findByPrimaryKey(
				parentFolderId);

			parentFolder.setLastPostDate(now);

			dlFolderPersistence.update(parentFolder, false);
		}

		// DLApp

		dlAppHelperLocalService.addFolder(
			new LiferayFolder(folder), serviceContext);

		return folder;
	}

	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		List<DLFolder> folders = dlFolderPersistence.findByG_P(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFolder folder : folders) {
			deleteFolder(folder);
		}

		deleteFileEntries(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		deleteFileEntry(dlFileEntry);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
			dlFileEntry.setReadCount(dlFileEntry.getReadCount() + 1);

			dlFileEntryPersistence.update(dlFileEntry, false);
		}

		dlAppHelperLocalService.getFileAsStream(
			userId, new LiferayFileEntry(dlFileEntry));

		return dlLocalService.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	public List<DLFileEntry> getFileEntries(int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findAll(start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(
			groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.countFE_FS_ByG_F_S(groupId, folderIds, status);
	}

	public int getFileEntriesCount() throws SystemException {
		return dlFileEntryPersistence.countAll();
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.countByG_F(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByPrimaryKey(fileVersionId);
	}

	public DLFileVersion getFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByF_V(fileEntryId, version);
	}

	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return dlFileVersionPersistence.findByFileEntryId(fileEntryId);
		}
		else {
			return dlFileVersionPersistence.findByF_S(fileEntryId, status);
		}
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_S(groupId, folderIds, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return dlFileEntryFinder.countByG_F_S(groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int filesCount = dlFileEntryFinder.countByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			filesCount += getFoldersFileEntriesCount(
				groupId, folderIds, status);

			return filesCount;
		}
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.findByGroupId(
				groupId, start, end, obc);
		}
		else {
			return dlFileEntryPersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
	}

	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return dlFileEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.countByGroupId(groupId);
		}
		else {
			return dlFileEntryPersistence.countByG_U(groupId, userId);
		}
	}

	public DLFileVersion getLatestFileVersion(long fileEntryId)
		throws PortalException, SystemException {

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(fileEntryId);

		if (dlFileVersions.isEmpty()) {
			throw new NoSuchFileVersionException();
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		return dlFileVersions.get(0);
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryFinder.findByNoAssets();
	}

	public List<DLFileEntry> getOrphanedFileEntries() throws SystemException {
		return dlFileEntryFinder.findOrphanedFileEntries();
	}

	public DLFileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		long oldFileEntryId = dlFileEntry.getFileEntryId();

		if (dlLocalService.hasFile(
				user.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getGroupId(), newFolderId),
				dlFileEntry.getName(), StringPool.BLANK)) {

			throw new DuplicateFileException(dlFileEntry.getName());
		}

		Date now = new Date();

		long newFileEntryId = counterLocalService.increment();

		DLFileEntry newDLFileEntry = dlFileEntryPersistence.create(
			newFileEntryId);

		newDLFileEntry.setGroupId(dlFileEntry.getGroupId());
		newDLFileEntry.setCompanyId(dlFileEntry.getCompanyId());
		newDLFileEntry.setUserId(dlFileEntry.getUserId());
		newDLFileEntry.setUserName(dlFileEntry.getUserName());
		newDLFileEntry.setVersionUserId(dlFileEntry.getVersionUserId());
		newDLFileEntry.setVersionUserName(dlFileEntry.getVersionUserName());
		newDLFileEntry.setCreateDate(dlFileEntry.getCreateDate());
		newDLFileEntry.setModifiedDate(dlFileEntry.getModifiedDate());
		newDLFileEntry.setFolderId(newFolderId);
		newDLFileEntry.setName(dlFileEntry.getName());
		newDLFileEntry.setExtension(dlFileEntry.getExtension());
		newDLFileEntry.setTitle(dlFileEntry.getTitle());
		newDLFileEntry.setDescription(dlFileEntry.getDescription());
		newDLFileEntry.setExtraSettings(dlFileEntry.getExtraSettings());
		newDLFileEntry.setVersion(dlFileEntry.getVersion());
		newDLFileEntry.setSize(dlFileEntry.getSize());
		newDLFileEntry.setReadCount(dlFileEntry.getReadCount());

		dlFileEntryPersistence.update(newDLFileEntry, false);

		dlFileEntryPersistence.remove(dlFileEntry);

		workflowInstanceLinkLocalService.updateClassPK(
			dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
			DLFileEntry.class.getName(), oldFileEntryId, newFileEntryId);

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(oldFileEntryId);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			long newFileVersionId = counterLocalService.increment();

			DLFileVersion newDLFileVersion = dlFileVersionPersistence.create(
				newFileVersionId);

			newDLFileVersion.setGroupId(dlFileVersion.getGroupId());
			newDLFileVersion.setCompanyId(dlFileVersion.getCompanyId());
			newDLFileVersion.setUserId(dlFileVersion.getUserId());
			newDLFileVersion.setUserName(dlFileVersion.getUserName());
			newDLFileVersion.setCreateDate(dlFileVersion.getCreateDate());
			newDLFileVersion.setFileEntryId(newFileEntryId);
			newDLFileVersion.setExtension(dlFileVersion.getExtension());
			newDLFileVersion.setTitle(dlFileVersion.getTitle());
			newDLFileVersion.setDescription(dlFileVersion.getDescription());
			newDLFileVersion.setChangeLog(dlFileVersion.getChangeLog());
			newDLFileVersion.setExtraSettings(dlFileVersion.getExtraSettings());
			newDLFileVersion.setVersion(dlFileVersion.getVersion());
			newDLFileVersion.setSize(dlFileVersion.getSize());
			newDLFileVersion.setStatus(dlFileVersion.getStatus());
			newDLFileVersion.setStatusByUserId(userId);
			newDLFileVersion.setStatusByUserName(user.getFullName());
			newDLFileVersion.setStatusDate(serviceContext.getModifiedDate(now));

			dlFileVersionPersistence.update(newDLFileVersion, false);

			dlFileVersionPersistence.remove(dlFileVersion);
		}

		// Resources

		resourceLocalService.updateResources(
			dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(dlFileEntry.getFileEntryId()),
			String.valueOf(newFileEntryId));

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// DLApp

		dlAppHelperLocalService.moveFileEntry(oldFileEntryId, newFileEntryId);

		// File

		dlLocalService.updateFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			newDLFileEntry.getGroupId(), dlFileEntry.getDataRepositoryId(),
			newDLFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
			newFileEntryId);

		return newDLFileEntry;
	}

	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion = dlRepositoryLocalService.getFileVersion(
			fileEntryId, version);

		if (dlFileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		String sourceFileName = dlFileVersion.getTitle();
		String title = dlFileVersion.getTitle();
		String description = dlFileVersion.getDescription();
		String changeLog = "Reverted to " + version;
		boolean majorVersion = true;
		String extraSettings = dlFileVersion.getExtraSettings();
		InputStream is = getFileAsStream(userId, fileEntryId, version);
		long size = dlFileVersion.getSize();

		updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);
	}

	public void updateAsset(
			long userId, DLFileEntry dlFileEntry, DLFileVersion dlFileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		String mimeType = MimeTypesUtil.getContentType(dlFileEntry.getTitle());

		boolean addDraftAssetEntry = false;

		String version = dlFileVersion.getVersion();

		if ((dlFileVersion != null) && !dlFileVersion.isApproved() &&
			!version.equals(DLFileEntryConstants.DEFAULT_VERSION)) {

			int approvedArticlesCount = dlFileVersionPersistence.countByF_S(
				dlFileEntry.getFileEntryId(),
				WorkflowConstants.STATUS_APPROVED);

			if (approvedArticlesCount > 0) {
				addDraftAssetEntry = true;
			}
		}

		boolean visible = true;

		if ((dlFileVersion != null) && !dlFileVersion.isApproved()) {
			visible = false;
		}

		dlAppHelperLocalService.updateAsset(
			userId, new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(dlFileVersion), assetCategoryIds,
			assetTagNames, mimeType, addDraftAssetEntry, visible);
	}

	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (Validator.isNull(title)) {
			title = sourceFileName;

			if (Validator.isNull(title)) {
				title = dlFileEntry.getTitle();
			}
		}

		Date now = new Date();

		validateFile(
			dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
			dlFileEntry.getFileEntryId(), dlFileEntry.getExtension(), title,
			sourceFileName, is);

		// File version

		String version = getNextVersion(
			dlFileEntry, majorVersion, serviceContext.getWorkflowAction());

		DLFileVersion dlFileVersion = null;

		String extension = null;

		if (Validator.isNotNull(sourceFileName)) {
			extension = FileUtil.getExtension(sourceFileName);
		}
		else {
			extension = dlFileEntry.getExtension();
		}

		boolean updatedFileVersion = false;

		try {
			DLFileVersion latestDLFileVersion =
				dlFileEntry.getLatestFileVersion();

			if (size == 0) {
				size = latestDLFileVersion.getSize();
			}

			if (!latestDLFileVersion.isApproved()) {
				if (!PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED) {
					version = latestDLFileVersion.getVersion();
				}

				if (version.equals(latestDLFileVersion.getVersion())) {
					updatedFileVersion = true;
				}

				updateFileVersion(
					user, latestDLFileVersion, sourceFileName, extension, title,
					description, changeLog, extraSettings, version, size,
					latestDLFileVersion.getStatus(),
					serviceContext.getModifiedDate(now), serviceContext);
			}
			else {
				dlFileVersion = addFileVersion(
					user, dlFileEntry, serviceContext.getModifiedDate(now),
					extension, title, description, changeLog, extraSettings,
					version, size, WorkflowConstants.STATUS_DRAFT,
					serviceContext);
			}

			if (dlFileVersion == null) {
				dlFileVersion = latestDLFileVersion;
			}
		}
		catch (NoSuchFileVersionException nsfve) {
			dlFileVersion = addFileVersion(
				user, dlFileEntry, serviceContext.getModifiedDate(now),
				extension, title, description, changeLog, extraSettings,
				version, size, WorkflowConstants.STATUS_DRAFT, serviceContext);
		}

		if ((is == null) && !updatedFileVersion) {
			int fetchFailures = 0;

			while (is == null) {
				try {
					is = dlLocalService.getFileAsStream(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName());
				}
				catch (NoSuchFileException nsfe) {
					fetchFailures++;

					if (PropsValues.DL_HOOK_IMPL.equals(
							JCRHook.class.getName()) &&
						(fetchFailures <
							PropsValues.DL_HOOK_JCR_FETCH_MAX_FAILURES)) {

						try {
							Thread.sleep(PropsValues.DL_HOOK_JCR_FETCH_DELAY);
						}
						catch (InterruptedException ie) {
						}
					}
					else {
						throw nsfe;
					}
				}
			}
		}

		// Asset

		updateAsset(
			userId, dlFileEntry, dlFileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Folder

		if (dlFileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder folder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			folder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// File

		if (is != null) {
			try {
				dlLocalService.deleteFile(
					user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					version);
			}
			catch (NoSuchFileException nsfe) {
			}

			dlLocalService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				dlFileEntry.getGroupId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(), dlFileEntry.getExtension(), false,
				version, sourceFileName, dlFileEntry.getFileEntryId(),
				dlFileEntry.getLuceneProperties(),
				dlFileEntry.getModifiedDate(), serviceContext, is);
		}

		// Workflow

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), dlFileEntry.getGroupId(), userId,
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId(),
				dlFileEntry, serviceContext);
		}

		return dlFileEntry;
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validateFolder(
			folder.getFolderId(), folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(folder, false);

		return folder;
	}

	public DLFileEntry updateStatus(
			long userId, long fileEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		// File version

		DLFileVersion latestDLFileVersion = getLatestFileVersion(
			dlFileEntry.getFileEntryId());

		latestDLFileVersion.setStatus(status);
		latestDLFileVersion.setStatusByUserId(user.getUserId());
		latestDLFileVersion.setStatusByUserName(user.getFullName());
		latestDLFileVersion.setStatusDate(new Date());

		dlFileVersionPersistence.update(latestDLFileVersion, false);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// File entry

			if (DLUtil.compareVersions(
					dlFileEntry.getVersion(),
					latestDLFileVersion.getVersion()) <= 0) {

				dlFileEntry.setTitle(latestDLFileVersion.getTitle());
				dlFileEntry.setDescription(
					latestDLFileVersion.getDescription());
				dlFileEntry.setExtraSettings(
					latestDLFileVersion.getExtraSettings());
				dlFileEntry.setVersion(latestDLFileVersion.getVersion());
				dlFileEntry.setVersionUserId(latestDLFileVersion.getUserId());
				dlFileEntry.setVersionUserName(
					latestDLFileVersion.getUserName());
				dlFileEntry.setModifiedDate(
					latestDLFileVersion.getCreateDate());
				dlFileEntry.setSize(latestDLFileVersion.getSize());

				dlFileEntryPersistence.update(dlFileEntry, false);
			}

			// Indexer

			Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);

			indexer.reindex(dlFileEntry);
		}
		else {

			// File entry

			if (dlFileEntry.getVersion().equals(
					latestDLFileVersion.getVersion())) {

				String newVersion = DLFileEntryConstants.DEFAULT_VERSION;

				List<DLFileVersion> approvedFileVersions =
					dlFileVersionPersistence.findByF_S(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedFileVersions.isEmpty()) {
					newVersion = approvedFileVersions.get(0).getVersion();
				}

				dlFileEntry.setVersion(newVersion);

				dlFileEntryPersistence.update(dlFileEntry, false);
			}

			// Indexer

			if (latestDLFileVersion.getVersion().equals(
					DLFileEntryConstants.DEFAULT_VERSION)) {

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					DLFileEntry.class);

				indexer.delete(dlFileEntry);
			}
		}

		// DLApp

		dlAppHelperLocalService.updateStatus(
			userId, new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(latestDLFileVersion), status);

		return dlFileEntry;
	}

	protected void addFileEntryResources(
			DLFileEntry dlFileEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
			dlFileEntry.getUserId(), DLFileEntry.class.getName(),
			dlFileEntry.getFileEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	protected void addFileEntryResources(
			DLFileEntry dlFileEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
			dlFileEntry.getUserId(), DLFileEntry.class.getName(),
			dlFileEntry.getFileEntryId(), communityPermissions,
			guestPermissions);
	}

	protected void addFileEntryResources(
			long fileEntryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			dlFileEntry, addCommunityPermissions, addGuestPermissions);
	}

	protected void addFileEntryResources(
			long fileEntryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			dlFileEntry, communityPermissions, guestPermissions);
	}

	protected DLFileVersion addFileVersion(
			User user, DLFileEntry dlFileEntry, Date modifiedDate,
			String extension, String title, String description,
			String changeLog, String extraSettings, String version, long size,
			int status, ServiceContext serviceContext)
		throws SystemException {

		long fileVersionId = counterLocalService.increment();

		DLFileVersion dlFileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		long versionUserId = dlFileEntry.getVersionUserId();

		if (versionUserId <= 0) {
			versionUserId = dlFileEntry.getUserId();
		}

		String versionUserName = GetterUtil.getString(
			dlFileEntry.getVersionUserName(), dlFileEntry.getUserName());

		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());
		dlFileVersion.setUserId(versionUserId);
		dlFileVersion.setUserName(versionUserName);
		dlFileVersion.setCreateDate(modifiedDate);
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setExtension(extension);
		dlFileVersion.setTitle(title);
		dlFileVersion.setDescription(description);
		dlFileVersion.setChangeLog(changeLog);
		dlFileVersion.setExtraSettings(extraSettings);
		dlFileVersion.setVersion(version);
		dlFileVersion.setSize(size);
		dlFileVersion.setStatus(status);
		dlFileVersion.setStatusByUserId(user.getUserId());
		dlFileVersion.setStatusByUserName(user.getFullName());
		dlFileVersion.setStatusDate(dlFileEntry.getModifiedDate());
		dlFileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersionPersistence.update(dlFileVersion, false);

		return dlFileVersion;
	}

	protected void addFolderResources(
			DLFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			DLFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	protected void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	protected void deleteFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			deleteFileEntry(dlFileEntry);
		}
	}

	protected void deleteFileEntry(DLFileEntry dlFileEntry)
		throws PortalException, SystemException {

		// File entry

		dlFileEntryPersistence.remove(dlFileEntry);

		// Resources

		resourceLocalService.deleteResource(
			dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFileEntry.getFileEntryId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// File versions

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(
				dlFileEntry.getFileEntryId());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			dlFileVersionPersistence.remove(dlFileVersion);
		}

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// Lock

		String lockId = String.valueOf(dlFileEntry.getFileEntryId());

		lockLocalService.unlock(DLFileEntry.class.getName(), lockId);

		// DLApp

		dlAppHelperLocalService.deleteFileEntry(
			new LiferayFileEntry(dlFileEntry));

		// File

		try {
			dlLocalService.deleteFile(
				dlFileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				dlFileEntry.getDataRepositoryId(), dlFileEntry.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void deleteFolder(DLFolder folder)
		throws PortalException, SystemException {

		// Folders

		List<DLFolder> folders = dlFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (DLFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Folder

		dlFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder.getCompanyId(), DLFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, folder.getFolderId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFolder.class.getName(), folder.getFolderId());

		// File entries

		deleteFileEntries(folder.getGroupId(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFolder.class.getName(), folder.getFolderId());

		// DLApp

		dlAppHelperLocalService.deleteFolder(new LiferayFolder(folder));

		// Directory

		try {
			dlLocalService.deleteDirectory(
				folder.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getFolderId(), StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	protected long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder folder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((folder == null) || (companyId != folder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected String getNextVersion(
			DLFileEntry dlFileEntry, boolean majorVersion, int workflowAction)
		throws PortalException, SystemException {

		if (Validator.isNull(dlFileEntry.getVersion())) {
			return DLFileEntryConstants.DEFAULT_VERSION;
		}

		try {
			DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion();

			String version = dlFileVersion.getVersion();

			if (!dlFileVersion.isApproved() &&
				version.equals(DLFileEntryConstants.DEFAULT_VERSION)) {

				return DLFileEntryConstants.DEFAULT_VERSION;
			}
		}
		catch (NoSuchFileVersionException nsfve) {
		}

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(
			dlFileEntry.getVersion(), StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected long getParentFolderId(DLFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			DLFolder parentFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<DLFolder> folders = dlFolderPersistence.findByG_P(
			groupId, folderId);

		for (DLFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	protected void updateFileVersion(
			User user, DLFileVersion dlFileVersion, String sourceFileName,
			String extension, String title, String description,
			String changeLog, String extraSettings, String version, long size,
			int status, Date statusDate, ServiceContext serviceContext)
		throws SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			dlFileVersion.setExtension(extension);
		}

		dlFileVersion.setTitle(title);
		dlFileVersion.setDescription(description);
		dlFileVersion.setChangeLog(changeLog);
		dlFileVersion.setExtraSettings(extraSettings);
		dlFileVersion.setVersion(version);
		dlFileVersion.setSize(size);
		dlFileVersion.setStatus(status);
		dlFileVersion.setStatusByUserId(user.getUserId());
		dlFileVersion.setStatusByUserName(user.getFullName());
		dlFileVersion.setStatusDate(statusDate);
		dlFileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersionPersistence.update(dlFileVersion, false);
	}

	protected void validateFile(
			long groupId, long folderId, long fileEntryId, String title)
		throws PortalException, SystemException {

		try {
			getFolder(groupId, folderId, title);

			throw new DuplicateFolderNameException();
		}
		catch (NoSuchFolderException nsfe) {
		}

		try {
			DLFileEntry dlFileEntry =
				dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);

			if (dlFileEntry.getFileEntryId() != fileEntryId) {
				throw new DuplicateFileException(title);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
	}

	protected void validateFile(
			long groupId, long folderId, long fileEntryId, String extension,
			String title, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			dlLocalService.validate(
				sourceFileName, extension, sourceFileName, true, is);
		}

		dlLocalService.validate(title, true);

		validateFile(groupId, folderId, fileEntryId, title);
	}

	protected void validateFile(
			long groupId, long folderId, String title, InputStream is)
		throws PortalException, SystemException {

		dlLocalService.validate(title, true, is);

		validateFile(groupId, folderId, 0, title);
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}

		try {
			getFileEntry(groupId, parentFolderId, name);

			throw new DuplicateFileException();
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		DLFolder folder = dlFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException();
		}
	}

	protected void validateFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validateFolder(folderId, groupId, parentFolderId, name);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLRepositoryLocalServiceImpl.class);

}