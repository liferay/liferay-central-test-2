/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.imagegallery.DuplicateFolderNameException;
import com.liferay.portlet.imagegallery.FolderNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGFolderLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="IGFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class IGFolderLocalServiceImpl extends IGFolderLocalServiceBaseImpl {

	public IGFolder addFolder(
			String uuid, long userId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		IGFolder folder = igFolderPersistence.create(folderId);

		folder.setUuid(uuid);
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		igFolderPersistence.update(folder, false);

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

		return folder;
	}

	public void addFolderResources(
			IGFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void deleteFolder(IGFolder folder)
		throws PortalException, SystemException {

		// Folder

		igFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder.getCompanyId(), IGFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, folder.getFolderId());

		// Folders

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (IGFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Images

		igImageLocalService.deleteImages(
			folder.getGroupId(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			IGFolder.class.getName(), folder.getFolderId());
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (IGFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public List<IGFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return igFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return igFolderPersistence.countByCompanyId(companyId);
	}

	public IGFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return igFolderPersistence.findByPrimaryKey(folderId);
	}

	public IGFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return igFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public List<IGFolder> getFolders(long groupId) throws SystemException {
		return igFolderPersistence.findByGroupId(groupId);
	}

	public List<IGFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<IGFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return igFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, folderId);

		for (IGFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public IGFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Merge folders

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		validate(
			folder.getFolderId(), folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		igFolderPersistence.update(folder, false);

		return folder;
	}

	protected long getParentFolderId(IGFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
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

		if (parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void mergeFolders(IGFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (IGFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<IGImage> images = igImagePersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (IGImage image : images) {
			image.setFolderId(toFolderId);

			igImagePersistence.update(image, false);

			Indexer indexer = IndexerRegistryUtil.getIndexer(IGImage.class);

			indexer.reindex(image);
		}

		deleteFolder(fromFolder);
	}

	protected void validate(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}

		IGFolder folder = igFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException();
		}

		if (name.indexOf(StringPool.PERIOD) != -1) {
			String nameWithExtension = name;

			name = FileUtil.stripExtension(nameWithExtension);

			List<IGImage> images = igImagePersistence.findByG_F_N(
				groupId, parentFolderId, name);

			for (IGImage image : images) {
				if (nameWithExtension.equals(image.getNameWithExtension())) {
					throw new DuplicateFolderNameException();
				}
			}
		}
	}

	protected void validate(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validate(folderId, groupId, parentFolderId, name);
	}

}