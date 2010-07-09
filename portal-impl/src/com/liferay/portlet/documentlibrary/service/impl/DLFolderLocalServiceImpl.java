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
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderLocalServiceImpl extends DLFolderLocalServiceBaseImpl {

	public DLFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(groupId, parentFolderId, name);

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

		// Layout

		if (PropsValues.DL_LAYOUTS_SYNC_ENABLED &&
			(parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			String[] pathArray = folder.getPathArray();

			String layoutsSyncPrivateFolder = GetterUtil.getString(
				PropsUtil.get(PropsKeys.DL_LAYOUTS_SYNC_PRIVATE_FOLDER));
			String layoutsSyncPublicFolder = GetterUtil.getString(
				PropsUtil.get(PropsKeys.DL_LAYOUTS_SYNC_PUBLIC_FOLDER));

			if (pathArray[0].equals(layoutsSyncPrivateFolder) ||
				pathArray[0].equals(layoutsSyncPublicFolder)) {

				boolean privateLayout = true;

				if (pathArray[0].equals(layoutsSyncPublicFolder)) {
					privateLayout = false;
				}

				long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
				String title = StringPool.BLANK;
				String layoutDescription = StringPool.BLANK;
				String type = LayoutConstants.TYPE_PORTLET;
				boolean hidden = false;
				String friendlyURL = StringPool.BLANK;

				Layout dlFolderLayout = null;

				try {
					dlFolderLayout = layoutLocalService.getDLFolderLayout(
						folder.getParentFolderId());

					parentLayoutId = dlFolderLayout.getLayoutId();
				}
				catch (NoSuchLayoutException nsle) {
				}

				layoutLocalService.addLayout(
					userId, groupId, privateLayout, parentLayoutId, name, title,
					layoutDescription, type, hidden, friendlyURL,
					folder.getFolderId(), new ServiceContext());
			}
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentFolder = dlFolderPersistence.findByPrimaryKey(
				parentFolderId);

			parentFolder.setLastPostDate(now);

			dlFolderPersistence.update(parentFolder, false);
		}

		return folder;
	}

	public void addFolderResources(
			DLFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			DLFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void deleteFolder(DLFolder folder)
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

		dlFileEntryLocalService.deleteFileEntries(
			folder.getGroupId(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFolder.class.getName(), folder.getFolderId());

		// Directory

		try {
			dlService.deleteDirectory(
				folder.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getFolderId(), StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<DLFolder> folders = dlFolderPersistence.findByG_P(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.countFE_FS_ByG_F_S(groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFolderFinder.countFE_FS_ByG_F_S(groupId, folderIds, status);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public List<DLFolder> getFolders(long companyId) throws SystemException {
		return dlFolderPersistence.findByCompanyId(companyId);
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

		return dlFolderFinder.countF_FE_FS_ByG_F_S(groupId, folderIds, status);
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

		return dlFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
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

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(
			folder.getFolderId(), folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(folder, false);

		// Layout

		if (PropsValues.DL_LAYOUTS_SYNC_ENABLED) {
			String privateFolder = GetterUtil.getString(PropsUtil.get(
				PropsKeys.DL_LAYOUTS_SYNC_PRIVATE_FOLDER));

			boolean privateLayout = false;

			String[] path = folder.getPathArray();

			if (path[0].equals(privateFolder)) {
				privateLayout = true;
			}

			Layout layout = layoutLocalService.getDLFolderLayout(
				folder.getFolderId());

			layout.setName(folder.getName());

			layoutLocalService.updateName(
				folder.getGroupId(), privateLayout, layout.getLayoutId(),
				folder.getName(),
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		}

		return folder;
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

	protected void validate(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}

		try {
			dlFileEntryLocalService.getFileEntryByTitle(
				groupId, parentFolderId, name);

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

	protected void validate(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validate(folderId, groupId, parentFolderId, name);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFolderLocalServiceImpl.class);

}