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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class LiferayFolder extends LiferayModel implements Folder {

	public LiferayFolder(DLFolder folder) {
		_folder = folder;
	}

	public LiferayFolder(DLFolder folder, boolean escapedModel) {
		_folder = folder;
		_escapedModel = escapedModel;
	}

	public List<Folder> getAncestors() throws PortalException, SystemException {
		return convertFolders(_folder.getAncestors());
	}

	public long getCompanyId() {
		return _folder.getCompanyId();
	}

	public Date getCreateDate() {
		return _folder.getCreateDate();
	}

	public String getDescription() {
		return _folder.getDescription();
	}

	public long getFolderId() {
		return _folder.getFolderId();
	}

	public Date getLastPostDate() {
		return _folder.getLastPostDate();
	}

	public Object getModel() {
		return _folder;
	}

	public Date getModifiedDate() {
		return _folder.getCreateDate();
	}

	public String getName() {
		return _folder.getName();
	}

	public Folder getParentFolder() throws PortalException, SystemException {
		return new LiferayFolder(_folder.getParentFolder());
	}

	public long getParentFolderId() {
		return _folder.getParentFolderId();
	}

	public String getPath() throws PortalException, SystemException {
		return _folder.getPath();
	}

	public String[] getPathArray() throws PortalException, SystemException {
		return _folder.getPathArray();
	}

	public long getPrimaryKey() {
		return _folder.getPrimaryKey();
	}

	public long getRepositoryId() {
		return _folder.getGroupId();
	}

	public long getUserId() {
		return _folder.getUserId();
	}

	public String getUserName() {
		return _folder.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _folder.getUserUuid();
	}

	public String getUuid() {
		return _folder.getUuid();
	}

	public boolean hasInheritableLock() {
		return _folder.hasInheritableLock();
	}

	public boolean hasLock() {
		return _folder.hasLock();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isLocked() {
		return _folder.isLocked();
	}

	public boolean isRoot() {
		return _folder.isRoot();
	}

	public void prepare() throws SystemException {
		_folder.setUserUuid(getUserUuid());
	}

	public Folder toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFolder(_folder.toEscapedModel(), true);
		}
	}

	private boolean _escapedModel = false;

	private DLFolder _folder;

}