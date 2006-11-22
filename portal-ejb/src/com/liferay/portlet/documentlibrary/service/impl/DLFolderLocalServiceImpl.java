/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.documentlibrary.service.spring.DLLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalService;
import com.liferay.util.Validator;
import com.liferay.util.lucene.Hits;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFolderLocalServiceImpl implements DLFolderLocalService {

	public DLFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public DLFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description, null, null,
			communityPermissions, guestPermissions);
	}

	public DLFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		String folderId = Long.toString(CounterLocalServiceUtil.increment(
			DLFolder.class.getName()));

		DLFolder folder = DLFolderUtil.create(folderId);

		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		DLFolderUtil.update(folder);

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

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			DLFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			String folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			DLFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			DLFolder.class.getName(), folder.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(String folderId)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolder(DLFolder folder)
		throws PortalException, SystemException {

		// Folders

		Iterator itr = DLFolderUtil.findByG_P(
			folder.getGroupId(), folder.getFolderId()).iterator();

		while (itr.hasNext()) {
			DLFolder curFolder = (DLFolder)itr.next();

			deleteFolder(curFolder);
		}

		// File entries

		DLFileEntryLocalServiceUtil.deleteFileEntries(folder.getFolderId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			folder.getCompanyId(), DLFolder.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			folder.getPrimaryKey().toString());

		// Folder

		DLFolderUtil.remove(folder.getFolderId());
	}

	public void deleteFolders(String groupId)
		throws PortalException, SystemException {

		Iterator itr = DLFolderUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			DLFolder folder = (DLFolder)itr.next();

			deleteFolder(folder);
		}
	}

	public DLFolder getFolder(String folderId)
		throws PortalException, SystemException {

		return DLFolderUtil.findByPrimaryKey(folderId);
	}

	public DLFolder getFolder(String parentFolderId, String name)
		throws PortalException, SystemException {

		return DLFolderUtil.findByP_N(parentFolderId, name);
	}

	public List getFolders(String companyId) throws SystemException {
		return DLFolderUtil.findByCompanyId(companyId);
	}

	public List getFolders(String groupId, String parentFolderId)
		throws SystemException {

		return DLFolderUtil.findByG_P(groupId, parentFolderId);
	}

	public List getFolders(
			String groupId, String parentFolderId, int begin, int end)
		throws SystemException {

		return DLFolderUtil.findByG_P(groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(String groupId, String parentFolderId)
		throws SystemException {

		return DLFolderUtil.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List folderIds, String groupId, String folderId)
		throws SystemException {

		Iterator itr = DLFolderUtil.findByG_P(groupId, folderId).iterator();

		while (itr.hasNext()) {
			DLFolder folder = (DLFolder)itr.next();

			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public Hits search(
			String companyId, String groupId, String[] folderIds,
			String keywords)
		throws PortalException, SystemException {

		return DLLocalServiceUtil.search(
			companyId, PortletKeys.DOCUMENT_LIBRARY, groupId, folderIds,
			keywords);
	}

	public DLFolder updateFolder(
			String companyId, String folderId, String parentFolderId,
			String name, String description)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		DLFolderUtil.update(folder);

		return folder;
	}

	protected String getParentFolderId(String groupId, String parentFolderId)
		throws SystemException {

		if (!parentFolderId.equals(DLFolder.DEFAULT_PARENT_FOLDER_ID)) {
			DLFolder parentFolder =
				DLFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(!groupId.equals(parentFolder.getGroupId()))) {

				parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected String getParentFolderId(DLFolder folder, String parentFolderId)
		throws SystemException {

		if (parentFolderId.equals(DLFolder.DEFAULT_PARENT_FOLDER_ID)) {
			return parentFolderId;
		}

		if (folder.getFolderId().equals(parentFolderId)) {
			return folder.getParentFolderId();
		}
		else {
			DLFolder parentFolder =
				DLFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(!folder.getGroupId().equals(parentFolder.getGroupId()))) {

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

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}