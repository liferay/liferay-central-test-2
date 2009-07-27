/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.MimeTypesUtil;
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

public class DLFileShortcutLocalServiceImpl
	extends DLFileShortcutLocalServiceBaseImpl {

	public DLFileShortcut addFileShortcut(
			long userId, long folderId, long toFolderId, String toName,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFileShortcut(
			null, userId, folderId, toFolderId, toName, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			String uuid, long userId, long folderId, long toFolderId,
			String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);
		Date now = new Date();

		validate(user, toFolderId, toName);

		long fileShortcutId = counterLocalService.increment();

		DLFileShortcut fileShortcut = dlFileShortcutPersistence.create(
			fileShortcutId);

		fileShortcut.setUuid(uuid);
		fileShortcut.setGroupId(folder.getGroupId());
		fileShortcut.setCompanyId(user.getCompanyId());
		fileShortcut.setUserId(user.getUserId());
		fileShortcut.setUserName(user.getFullName());
		fileShortcut.setCreateDate(now);
		fileShortcut.setModifiedDate(now);
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFolderId(toFolderId);
		fileShortcut.setToName(toName);

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

		// Tags

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			toFolderId, toName);

		copyTagEntries(fileEntry, serviceContext);

		updateAsset(
			userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Folder

		folder.setLastPostDate(fileShortcut.getModifiedDate());

		dlFolderPersistence.update(folder, false);

		return fileShortcut;
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
			long fileShortcutId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(
			fileShortcut, communityPermissions, guestPermissions);
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

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut = dlFileShortcutLocalService
			.getDLFileShortcut(fileShortcutId);

		deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		// Tags

		assetEntryLocalService.deleteEntry(
			DLFileShortcut.class.getName(), fileShortcut.getFileShortcutId());

		// Resources

		resourceLocalService.deleteResource(
			fileShortcut.getCompanyId(), DLFileShortcut.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fileShortcut.getFileShortcutId());

		// File shortcut

		dlFileShortcutPersistence.remove(fileShortcut);
	}

	public void deleteFileShortcuts(long toFolderId, String toName)
		throws PortalException, SystemException {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByTF_TN(toFolderId, toName);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			deleteFileShortcut(fileShortcut);
		}
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);
	}

	public void updateAsset(
			long userId, DLFileShortcut shortcut, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			shortcut.getToFolderId(), shortcut.getToName());

		String mimeType = MimeTypesUtil.getContentType(fileEntry.getName());

		assetEntryLocalService.updateEntry(
			userId, shortcut.getGroupId(), DLFileShortcut.class.getName(),
			shortcut.getFileShortcutId(), assetCategoryIds, assetTagNames, true, null,
			null, null, null, mimeType, fileEntry.getTitle(),
			fileEntry.getDescription(), null, null, 0, 0, null, false);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId,
			long toFolderId, String toName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);
		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		validate(user, toFolderId, toName);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		fileShortcut.setModifiedDate(new Date());
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFolderId(toFolderId);
		fileShortcut.setToName(toName);

		dlFileShortcutPersistence.update(fileShortcut, false);

		// Tags

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			toFolderId, toName);

		copyTagEntries(fileEntry, serviceContext);

		updateAsset(userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Folder

		folder.setLastPostDate(fileShortcut.getModifiedDate());

		dlFolderPersistence.update(folder, false);

		return fileShortcut;
	}

	public void updateFileShortcuts(
			long oldToFolderId, String oldToName, long newToFolderId,
			String newToName)
		throws SystemException {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByTF_TN(oldToFolderId, oldToName);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setToFolderId(newToFolderId);
			fileShortcut.setToName(newToName);

			dlFileShortcutPersistence.update(fileShortcut, false);
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

	protected void copyTagEntries(
			DLFileEntry fromFileEntry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntry.class.getName(), fromFileEntry.getFileEntryId());

		assetTagLocalService.checkTags(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetTagNames);

		serviceContext.setAssetTagNames(assetTagNames);
	}

	protected void validate(User user, long toFolderId, String toName)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			toFolderId, toName);

		if (user.getCompanyId() != fileEntry.getCompanyId()) {
			throw new NoSuchFileEntryException();
		}
	}

}