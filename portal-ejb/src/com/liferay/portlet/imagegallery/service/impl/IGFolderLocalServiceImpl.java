/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.imagegallery.FolderNameException;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.spring.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.spring.IGImageLocalServiceUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGFolderLocalServiceImpl implements IGFolderLocalService {

	public IGFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		parentFolderId = getParentFolderId(user.getCompanyId(), parentFolderId);
		Date now = new Date();

		validate(name);

		String folderId = Long.toString(CounterLocalServiceUtil.increment(
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

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);

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
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			folder.getPrimaryKey().toString());

		// Folder

		IGFolderUtil.remove(folder.getFolderId());
	}

	public void deleteFolders(String groupId)
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

	public List getFolders(String groupId) throws SystemException {
		return IGFolderUtil.findByGroupId(groupId);
	}

	public List getFolders(String groupId, String parentFolderId)
		throws SystemException {

		return IGFolderUtil.findByG_P(groupId, parentFolderId);
	}

	public List getFolders(
			String groupId, String parentFolderId, int begin, int end)
		throws SystemException {

		return IGFolderUtil.findByG_P(groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(String groupId, String parentFolderId)
		throws SystemException {

		return IGFolderUtil.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List folderIds, String groupId, String folderId)
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
			String companyId, String folderId, String parentFolderId,
			String name, String description)
		throws PortalException, SystemException {

		parentFolderId = getParentFolderId(companyId, parentFolderId);

		validate(name);

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		IGFolderUtil.update(folder);

		return folder;
	}

	protected String getParentFolderId(String companyId, String parentFolderId)
		throws PortalException, SystemException {

		if (!parentFolderId.equals(IGFolder.DEFAULT_PARENT_FOLDER_ID)) {

			// Ensure parent folder exists and belongs to the proper company

			try {
				IGFolder parentFolder =
					IGFolderUtil.findByPrimaryKey(parentFolderId);

				if (!companyId.equals(parentFolder.getCompanyId())) {
					parentFolderId = IGFolder.DEFAULT_PARENT_FOLDER_ID;
				}
			}
			catch (NoSuchFolderException nsfe) {
				parentFolderId = IGFolder.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}