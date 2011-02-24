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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public interface FileEntry extends RepositoryModel<FileEntry> {

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException;

	public long getCompanyId();

	public InputStream getContentStream()
		throws PortalException, SystemException;

	public InputStream getContentStream(String version)
		throws PortalException, SystemException;

	public Date getCreateDate();

	public String getDescription();

	public String getExtension();

	public long getFileEntryId();

	public FileVersion getFileVersion()
		throws PortalException, SystemException;

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException;

	public List<FileVersion> getFileVersions(int status)
		throws SystemException;

	public Folder getFolder();

	public long getFolderId();

	public long getGroupId();

	public String getIcon();

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException;

	public Lock getLock();

	public Date getModifiedDate();

	public int getReadCount();

	public long getRepositoryId();

	public long getSize();

	public String getTitle();

	public long getUserId();

	public String getUserName();

	public String getUserUuid() throws SystemException;

	public String getUuid();

	public String getVersion();

	public long getVersionUserId();

	public String getVersionUserName();

	public String getVersionUserUuid() throws SystemException;

	public boolean hasLock();

	public boolean isDefaultRepository();

	public boolean isLocked();

	public boolean isLockSupported();

	public boolean isMetadataSupported();

	public boolean isSocialSupported();

}