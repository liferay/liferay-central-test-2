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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.util.JCRHook;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Group;
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
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

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
			long userId, long groupId, long repositoryId, long folderId,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		String name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));
		String extension = (String)serviceContext.getAttribute("extension");
		Date now = new Date();

		validateFile(groupId, folderId, title, extension, is);

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
		dlFileEntry.setRepositoryId(repositoryId);
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setName(name);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
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
			title, description, null, StringPool.BLANK,
			DLFileEntryConstants.DEFAULT_VERSION, size,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			dlFolder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(dlFolder, false);
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
			long userId, long groupId, long repositoryId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validateFolder(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		DLFolder dlFolder = dlFolderPersistence.create(folderId);

		dlFolder.setUuid(serviceContext.getUuid());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(user.getCompanyId());
		dlFolder.setUserId(user.getUserId());
		dlFolder.setCreateDate(serviceContext.getCreateDate(now));
		dlFolder.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setMountPoint(
			GetterUtil.getBoolean(serviceContext.getAttribute("mountPoint")));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFolderResources(
				dlFolder, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFolderResources(
				dlFolder, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.findByPrimaryKey(
				parentFolderId);

			parentDLFolder.setLastPostDate(now);

			dlFolderPersistence.update(parentDLFolder, false);
		}

		// DLApp

		dlAppHelperLocalService.addFolder(
			new LiferayFolder(dlFolder), serviceContext);

		return dlFolder;
	}

	public void convertExtraSettings(String[] keys)
		throws PortalException, SystemException {

		int count = dlFileEntryFinder.countByExtraSettings();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<DLFileEntry> dlFileEntries =
				dlFileEntryFinder.findByExtraSettings(start, end);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				convertExtraSettings(dlFileEntry, keys);
			}
		}
	}

	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFolder dlFolder : dlFolders) {
			deleteFolder(dlFolder);
		}

		deleteFileEntries(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		try {
			dlLocalService.deleteDirectory(
				group.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY, groupId,
				StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		deleteFileEntry(dlFileEntry);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(dlFolder);
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	public List<DLFileEntry> getExtraSettingsFileEntries(int start, int end)
		throws SystemException {

		return dlFileEntryFinder.findByExtraSettings(start, end);
	}

	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version)
		throws PortalException, SystemException {

		return getFileAsStream(userId, fileEntryId, version, true);
	}

	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version, boolean count)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED && count) {
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
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.countFE_FS_ByG_F_S(groupId, folderId, status);
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

	public DLFolder getFolderByRepositoryId(long repositoryId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByRepositoryId(repositoryId);
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_S(groupId, folderId, status);
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

	public boolean hasExtraSettings() throws SystemException {
		if (dlFileEntryFinder.countByExtraSettings() > 0) {
			return true;
		}
		else {
			return false;
		}
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

		newDLFileEntry.setUuid(dlFileEntry.getUuid());
		newDLFileEntry.setGroupId(dlFileEntry.getGroupId());
		newDLFileEntry.setCompanyId(dlFileEntry.getCompanyId());
		newDLFileEntry.setUserId(dlFileEntry.getUserId());
		newDLFileEntry.setUserName(dlFileEntry.getUserName());
		newDLFileEntry.setVersionUserId(dlFileEntry.getVersionUserId());
		newDLFileEntry.setVersionUserName(dlFileEntry.getVersionUserName());
		newDLFileEntry.setCreateDate(dlFileEntry.getCreateDate());
		newDLFileEntry.setModifiedDate(dlFileEntry.getModifiedDate());
		newDLFileEntry.setRepositoryId(dlFileEntry.getRepositoryId());
		newDLFileEntry.setFolderId(newFolderId);
		newDLFileEntry.setName(dlFileEntry.getName());
		newDLFileEntry.setExtension(dlFileEntry.getExtension());
		newDLFileEntry.setTitle(dlFileEntry.getTitle());
		newDLFileEntry.setDescription(dlFileEntry.getDescription());
		newDLFileEntry.setExtraSettings(dlFileEntry.getExtraSettings());
		newDLFileEntry.setVersion(dlFileEntry.getVersion());
		newDLFileEntry.setSize(dlFileEntry.getSize());
		newDLFileEntry.setReadCount(dlFileEntry.getReadCount());
		newDLFileEntry.setExpandoBridgeAttributes(serviceContext);

		dlFileEntryPersistence.remove(dlFileEntry);

		dlFileEntryPersistence.update(newDLFileEntry, false);

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

			ExpandoBridge newDLFileVersionExpandoBridge =
				newDLFileVersion.getExpandoBridge();

			ExpandoBridge dlFileVersionExpandoBridge =
				dlFileVersion.getExpandoBridge();

			newDLFileVersionExpandoBridge.setAttributes(
				dlFileVersionExpandoBridge.getAttributes());

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

	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
			dlFolder.getName());

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
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

		if ((dlFileVersion != null) && !dlFileVersion.isApproved()) {
			String version = dlFileVersion.getVersion();

			if (!version.equals(DLFileEntryConstants.DEFAULT_VERSION)) {
				int approvedArticlesCount = dlFileVersionPersistence.countByF_S(
					dlFileEntry.getFileEntryId(),
					WorkflowConstants.STATUS_APPROVED);

				if (approvedArticlesCount > 0) {
					addDraftAssetEntry = true;
				}
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
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String extraSettings = StringPool.BLANK;

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
			name);

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
	}

	public DLFolder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		validateFolderName(name);

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
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
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
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
			DLFolder dlFolder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	protected void addFolderResources(
			DLFolder dlFolder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), communityPermissions, guestPermissions);
	}

	protected void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			dlFolder, addCommunityPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, communityPermissions, guestPermissions);
	}

	protected void convertExtraSettings(
			DLFileEntry dlFileEntry, DLFileVersion dlFileVersion, String[] keys)
		throws PortalException, SystemException {

		UnicodeProperties extraSettingsProperties =
			dlFileVersion.getExtraSettingsProperties();

		ExpandoBridge expandoBridge = dlFileVersion.getExpandoBridge();

		convertExtraSettings(extraSettingsProperties, expandoBridge, keys);

		dlFileVersion.setExtraSettingsProperties(extraSettingsProperties);

		dlFileVersionPersistence.update(dlFileVersion, false);

		int status = dlFileVersion.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(DLUtil.compareVersions(
				dlFileEntry.getVersion(), dlFileVersion.getVersion()) <= 0)) {

			Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);

			indexer.reindex(dlFileEntry);
		}
	}

	protected void convertExtraSettings(DLFileEntry dlFileEntry, String[] keys)
		throws PortalException, SystemException {

		UnicodeProperties extraSettingsProperties =
			dlFileEntry.getExtraSettingsProperties();

		ExpandoBridge expandoBridge = dlFileEntry.getExpandoBridge();

		convertExtraSettings(extraSettingsProperties, expandoBridge, keys);

		dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);

		dlFileEntryPersistence.update(dlFileEntry, false);

		List<DLFileVersion> dlFileVersions = getFileVersions(
			dlFileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			convertExtraSettings(dlFileEntry, dlFileVersion, keys);
		}
	}

	protected void convertExtraSettings(
		UnicodeProperties extraSettingsProperties, ExpandoBridge expandoBridge,
		String[] keys) {

		for (String key : keys) {
			String value = extraSettingsProperties.remove(key);

			if (Validator.isNull(value)) {
				continue;
			}

			int type = expandoBridge.getAttributeType(key);

			Serializable serializable = ExpandoColumnConstants.getSerializable(
				type, value);

			expandoBridge.setAttribute(key, serializable);
		}
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

			expandoValueLocalService.deleteValues(
				DLFileVersion.class.getName(),
				dlFileVersion.getFileVersionId());
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

	protected void deleteFolder(DLFolder dlFolder)
		throws PortalException, SystemException {

		// Folders

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		for (DLFolder curDLFolder : dlFolders) {
			deleteFolder(curDLFolder);
		}

		// Folder

		dlFolderPersistence.remove(dlFolder);

		// Resources

		resourceLocalService.deleteResource(
			dlFolder.getCompanyId(), DLFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFolder.getFolderId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// File entries

		deleteFileEntries(dlFolder.getGroupId(), dlFolder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// DLApp

		dlAppHelperLocalService.deleteFolder(new LiferayFolder(dlFolder));

		// Directory

		try {
			dlLocalService.deleteDirectory(
				dlFolder.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				dlFolder.getFolderId(), StringPool.BLANK);
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

			DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
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

	protected long getParentFolderId(DLFolder dlFolder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (dlFolder.getFolderId() == parentFolderId) {
			return dlFolder.getParentFolderId();
		}
		else {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(dlFolder.getGroupId() != parentDLFolder.getGroupId())) {

				return dlFolder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, dlFolder.getGroupId(), dlFolder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return dlFolder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(groupId != parentDLFolder.getGroupId())) {

				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	protected DLFileEntry updateFileEntry(
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

		String extension = (String)serviceContext.getAttribute("extension");

		if (Validator.isNull(extension)) {
			extension = dlFileEntry.getExtension();
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
				if (latestDLFileVersion.getSize() == 0) {
					version = latestDLFileVersion.getVersion();

					updateFileVersion(
						user, latestDLFileVersion, sourceFileName, extension,
						title, description, changeLog, extraSettings, version,
						size, latestDLFileVersion.getStatus(),
						serviceContext.getModifiedDate(now), serviceContext);
				}
				else {
					dlFileVersion = addFileVersion(
						user, dlFileEntry, serviceContext.getModifiedDate(now),
						extension, title, description, changeLog, extraSettings,
						version, size, WorkflowConstants.STATUS_DRAFT,
						serviceContext);
				}
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

		File file = null;

		if ((is == null) && !updatedFileVersion) {
			int fetchFailures = 0;

			while (is == null) {
				try {
					is = dlLocalService.getFileAsStream(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName());

					file = FileUtil.createTempFile(
						FileUtil.getExtension(title));

					FileUtil.write(file, is);
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
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

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			dlFolder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(dlFolder, false);
		}

		// File

		if ((is != null) || (file != null)) {
			try {
				dlLocalService.deleteFile(
					user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					version);
			}
			catch (NoSuchFileException nsfe) {
			}

			if (file != null) {
				try {
					is = new UnsyncBufferedInputStream(
						new FileInputStream(file));
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
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

			throw new DuplicateFolderNameException(title);
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

		validateFileName(title);

		dlLocalService.validate(title, false);

		validateFile(groupId, folderId, fileEntryId, title);
	}

	protected void validateFile(
			long groupId, long folderId, String title, String extension,
			InputStream is)
		throws PortalException, SystemException {

		String fileName = title + StringPool.PERIOD + extension;

		validateFileName(fileName);

		dlLocalService.validate(fileName, true, is);

		validateFile(groupId, folderId, 0, title);
	}

	protected void validateFileName(String fileName) throws PortalException {
		if (fileName.contains(StringPool.SLASH)) {
			throw new FileNameException(fileName);
		}
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		validateFolderName(name);

		try {
			getFileEntry(groupId, parentFolderId, name);

			throw new DuplicateFileException(name);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		DLFolder dlFolder = dlFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((dlFolder != null) && (dlFolder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException(name);
		}
	}

	protected void validateFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validateFolder(folderId, groupId, parentFolderId, name);
	}

	protected void validateFolderName(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLRepositoryLocalServiceImpl.class);

}