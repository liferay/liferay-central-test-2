/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.imagegallery.FolderNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGFolderLocalServiceImpl implements IGFolderLocalService {

	public IGFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public IGFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description, null, null,
			communityPermissions, guestPermissions);
	}

	public IGFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		String folderId = String.valueOf(CounterLocalServiceUtil.increment(
			IGFolder.class.getName()));

		IGFolder folder = IGFolderUtil.create(folderId);

		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		IGFolderUtil.update(folder);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFolderResources(
				folder, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFolderResources(folder, communityPermissions, guestPermissions);
		}

		return folder;
	}

	public void addFolderResources(
			String folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			String folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(String folderId)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolder(IGFolder folder)
		throws PortalException, SystemException {

		// Folders

		Iterator itr = IGFolderUtil.findByG_P(
			folder.getGroupId(), folder.getFolderId()).iterator();

		while (itr.hasNext()) {
			IGFolder curFolder = (IGFolder)itr.next();

			deleteFolder(curFolder);
		}

		// Images

		IGImageLocalServiceUtil.deleteImages(folder.getFolderId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			folder.getCompanyId(), IGFolder.class.getName(),
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
			folder.getPrimaryKey().toString());

		// Folder

		IGFolderUtil.remove(folder.getFolderId());
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		Iterator itr = IGFolderUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			IGFolder folder = (IGFolder)itr.next();

			deleteFolder(folder);
		}
	}

	public IGFolder getFolder(String folderId)
		throws PortalException, SystemException {

		return IGFolderUtil.findByPrimaryKey(folderId);
	}

	public List getFolders(long groupId) throws SystemException {
		return IGFolderUtil.findByGroupId(groupId);
	}

	public List getFolders(long groupId, String parentFolderId)
		throws SystemException {

		return IGFolderUtil.findByG_P(groupId, parentFolderId);
	}

	public List getFolders(
			long groupId, String parentFolderId, int begin, int end)
		throws SystemException {

		return IGFolderUtil.findByG_P(groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(long groupId, String parentFolderId)
		throws SystemException {

		return IGFolderUtil.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List folderIds, long groupId, String folderId)
		throws SystemException {

		Iterator itr = IGFolderUtil.findByG_P(groupId, folderId).iterator();

		while (itr.hasNext()) {
			IGFolder folder = (IGFolder)itr.next();

			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public IGFolder updateFolder(
			String folderId, String parentFolderId, String name,
			String description, boolean mergeWithParentFolder)
		throws PortalException, SystemException {

		// Folder

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		String oldFolderId = folder.getParentFolderId();
		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		IGFolderUtil.update(folder);

		// Merge folders

		if (mergeWithParentFolder && !oldFolderId.equals(parentFolderId) &&
			!parentFolderId.equals(IGFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {

			mergeFolders(folder, parentFolderId);
		}

		return folder;
	}

	protected String getParentFolderId(long groupId, String parentFolderId)
		throws SystemException {

		if (!parentFolderId.equals(IGFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {
			IGFolder parentFolder =
				IGFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId = IGFolderImpl.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected String getParentFolderId(IGFolder folder, String parentFolderId)
		throws SystemException {

		if (parentFolderId.equals(IGFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {
			return parentFolderId;
		}

		if (folder.getFolderId().equals(parentFolderId)) {
			return folder.getParentFolderId();
		}
		else {
			IGFolder parentFolder =
				IGFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List subfolderIds = new ArrayList();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected void mergeFolders(IGFolder fromFolder, String toFolderId)
		throws PortalException, SystemException {

		Iterator itr = IGFolderUtil.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId()).iterator();

		while (itr.hasNext()) {
			IGFolder folder = (IGFolder)itr.next();

			mergeFolders(folder, toFolderId);
		}

		itr = IGImageUtil.findByFolderId(fromFolder.getFolderId()).iterator();

		while (itr.hasNext()) {

			// Image

			IGImage image = (IGImage)itr.next();

			image.setFolderId(toFolderId);

			IGImageUtil.update(image);
		}

		IGFolderUtil.remove(fromFolder.getFolderId());
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}