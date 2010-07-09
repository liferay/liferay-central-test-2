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
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutLocalServiceImpl
	extends DLFileShortcutLocalServiceBaseImpl {

	public DLFileShortcut addFileShortcut(
			long userId, long groupId, long folderId, long toFolderId,
			String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		Date now = new Date();

		validate(user, groupId, toFolderId, toName);

		long fileShortcutId = counterLocalService.increment();

		DLFileShortcut fileShortcut = dlFileShortcutPersistence.create(
			fileShortcutId);

		fileShortcut.setUuid(serviceContext.getUuid());
		fileShortcut.setGroupId(groupId);
		fileShortcut.setCompanyId(user.getCompanyId());
		fileShortcut.setUserId(user.getUserId());
		fileShortcut.setUserName(user.getFullName());
		fileShortcut.setCreateDate(serviceContext.getCreateDate(now));
		fileShortcut.setModifiedDate(serviceContext.getModifiedDate(now));
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFolderId(toFolderId);
		fileShortcut.setToName(toName);
		fileShortcut.setStatus(WorkflowConstants.STATUS_APPROVED);
		fileShortcut.setStatusByUserId(userId);
		fileShortcut.setStatusByUserName(user.getFullName());
		fileShortcut.setStatusDate(now);

		dlFileShortcutPersistence.update(fileShortcut, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFileShortcutResources(
				fileShortcut, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileShortcutResources(
				fileShortcut, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			folder.setLastPostDate(fileShortcut.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// Asset

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			groupId, toFolderId, toName);

		copyAssetTags(fileEntry, serviceContext);

		updateAsset(
			userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return fileShortcut;
	}

	public void addFileShortcutResources(
			DLFileShortcut fileShortcut, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFileShortcutResources(
			DLFileShortcut fileShortcut, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), communityPermissions,
			guestPermissions);
	}

	public void addFileShortcutResources(
			long fileShortcutId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(
			fileShortcut, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(
			long fileShortcutId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(
			fileShortcut, communityPermissions, guestPermissions);
	}

	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		// File shortcut

		dlFileShortcutPersistence.remove(fileShortcut);

		// Resources

		resourceLocalService.deleteResource(
			fileShortcut.getCompanyId(), DLFileShortcut.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fileShortcut.getFileShortcutId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileShortcut.class.getName(), fileShortcut.getFileShortcutId());
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);

		deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcuts(
			long groupId, long toFolderId, String toName)
		throws PortalException, SystemException {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_TF_TN(
				groupId, toFolderId, toName);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			deleteFileShortcut(fileShortcut);
		}
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);
	}

	public void updateAsset(
			long userId, DLFileShortcut fileShortcut, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			fileShortcut.getGroupId(), fileShortcut.getToFolderId(),
			fileShortcut.getToName());

		String mimeType = MimeTypesUtil.getContentType(fileEntry.getTitle());

		assetEntryLocalService.updateEntry(
			userId, fileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), fileShortcut.getUuid(),
			assetCategoryIds, assetTagNames, false, null, null, null, null,
			mimeType, fileEntry.getTitle(), fileEntry.getDescription(), null,
			null, 0, 0, null, false);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId,
			long toFolderId, String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		validate(user, fileShortcut.getGroupId(), toFolderId, toName);

		fileShortcut.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFolderId(toFolderId);
		fileShortcut.setToName(toName);

		dlFileShortcutPersistence.update(fileShortcut, false);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			folder.setLastPostDate(fileShortcut.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// Asset

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			fileShortcut.getGroupId(), toFolderId, toName);

		copyAssetTags(fileEntry, serviceContext);

		updateAsset(
			userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return fileShortcut;
	}

	public void updateFileShortcuts(
			long groupId, long oldToFolderId, String oldToName,
			long newToFolderId, String newToName)
		throws SystemException {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_TF_TN(
				groupId, oldToFolderId, oldToName);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setToFolderId(newToFolderId);
			fileShortcut.setToName(newToName);

			dlFileShortcutPersistence.update(fileShortcut, false);
		}
	}

	protected void copyAssetTags(
			DLFileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		assetTagLocalService.checkTags(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetTagNames);

		serviceContext.setAssetTagNames(assetTagNames);
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

	protected void validate(
			User user, long groupId, long toFolderId, String toName)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			groupId, toFolderId, toName);

		if (user.getCompanyId() != fileEntry.getCompanyId()) {
			throw new NoSuchFileEntryException();
		}
	}

}