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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class CMISFolder extends CMISModel implements Folder {

	public CMISFolder(
		CMISRepository cmisRepository, String uuid, long folderId,
		org.apache.chemistry.opencmis.client.api.Folder folder) {

		_cmisRepository = cmisRepository;
		_uuid = uuid;
		_folderId = folderId;
		_folder = folder;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return containsPermission(_folder, actionId);
	}

	public List<Folder> getAncestors()
		throws PortalException, SystemException {

		List<Folder> ancestors = new ArrayList<Folder>();

		Folder folder = this;

		while (!folder.isRoot()) {
			folder = folder.getParentFolder();

			ancestors.add(folder);
		}

		return ancestors;
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	public Date getCreateDate() {
		GregorianCalendar calendar = _folder.getCreationDate();

		if (calendar != null) {
			return calendar.getTime();
		}
		else {
			return new Date();
		}
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	public Date getLastPostDate() {
		return getModifiedDate();
	}

	public Object getModel() {
		return _folder;
	}

	public Date getModifiedDate() {
		GregorianCalendar calendar = _folder.getLastModificationDate();

		if (calendar != null) {
			return calendar.getTime();
		}
		else {
			return new Date();
		}
	}

	public String getName() {
		List<org.apache.chemistry.opencmis.client.api.Folder> parentFolders =
			_folder.getParents();

		if (parentFolders.size() == 0) {
			try {
				DLFolder dlFolder =
					DLRepositoryLocalServiceUtil.getFolderByRepositoryId(
						getRepositoryId());

				return dlFolder.getName();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _folder.getName();
	}

	public Folder getParentFolder() throws PortalException, SystemException {
		List<org.apache.chemistry.opencmis.client.api.Folder> parentFolders =
			_folder.getParents();

		if (parentFolders.size() == 0) {
			DLFolder dlFolder =
				DLRepositoryLocalServiceUtil.getFolderByRepositoryId(
					getRepositoryId());

			DLFolder parentDLFolder = dlFolder.getParentFolder();

			if (parentDLFolder != null) {
				return new LiferayFolder(parentDLFolder);
			}
			else {
				return null;
			}
		}
		else {
			return CMISRepositoryLocalServiceUtil.toFolder(
				getRepositoryId(), parentFolders.get(0));
		}
	}

	public long getParentFolderId() {
		try {
			Folder parentFolder = getParentFolder();

			if (parentFolder != null) {
				return parentFolder.getFolderId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	public String getPath() {
		StringBuilder sb = new StringBuilder();

		try {
			Folder folder = this;

			while (folder != null) {
				sb.insert(0, folder.getName());
				sb.insert(0, StringPool.SLASH);

				folder = folder.getParentFolder();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return sb.toString();
	}

	public String[] getPathArray() {
		String path = getPath();

		// Remove leading /

		path = path.substring(1, path.length());

		return StringUtil.split(path, StringPool.SLASH);
	}

	public long getPrimaryKey() {
		return _folderId;
	}

	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	public long getUserId() {
		try {
			return UserLocalServiceUtil.getDefaultUserId(getCompanyId());
		}
		catch (Exception e) {
			return 0;
		}
	}

	public String getUserName() {
		return _folder.getCreatedBy();
	}

	public String getUserUuid() {
		try {
			return UserLocalServiceUtil.getDefaultUser(
				getCompanyId()).getUserUuid();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean hasInheritableLock() {
		return false;
	}

	public boolean hasLock() {
		return false;
	}

	public boolean isDefaultRepository() {
		return false;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isLocked() {
		return false;
	}

	public boolean isMountPoint() {
		return false;
	}

	public boolean isRoot() {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}
		else {
			return false;
		}
	}

	public void prepare() {
	}

	public Folder toEscapedModel() {
		return this;
	}

	private org.apache.chemistry.opencmis.client.api.Folder _folder;
	private CMISRepository _cmisRepository;
	private String _uuid;
	private long _folderId;

	private static Log _log = LogFactoryUtil.getLog(CMISFolder.class);

}