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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public interface Folder extends RepositoryModel<Folder> {

	public java.util.List<Folder> getAncestors()
		throws PortalException, SystemException;

	public long getCompanyId();

	public Date getCreateDate();

	public String getDescription();

	public long getFolderId();

	public Date getLastPostDate();

	public Date getModifiedDate();

	public String getName();

	public Folder getParentFolder() throws PortalException, SystemException;

	public long getParentFolderId();

	public java.lang.String getPath() throws PortalException, SystemException;

	public java.lang.String[] getPathArray()
		throws PortalException, SystemException;

	public long getRepositoryId();

	public long getUserId();

	public String getUserName();

	public String getUserUuid() throws SystemException;

	public String getUuid();

	public boolean hasInheritableLock();

	public boolean hasLock();

	public boolean isLocked();

	public boolean isRoot();

}