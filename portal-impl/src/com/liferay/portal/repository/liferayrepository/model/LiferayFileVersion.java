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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public class LiferayFileVersion extends LiferayModel implements FileVersion {

	public LiferayFileVersion(DLFileVersion fileVersion) {
		_fileVersion = fileVersion;
	}

	public LiferayFileVersion(DLFileVersion fileVersion, boolean escapedModel) {
		_fileVersion = fileVersion;
		_escapedModel = escapedModel;
	}

	public String getChangeLog() {
		return _fileVersion.getChangeLog();
	}

	public long getCompanyId() {
		return _fileVersion.getCompanyId();
	}

	public Date getCreateDate() {
		return _fileVersion.getCreateDate();
	}

	public String getDescription() {
		return _fileVersion.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		return _fileVersion.getExpandoBridge();
	}

	public String getExtension() {
		return _fileVersion.getExtension();
	}

	public String getExtraSettings() {
		return _fileVersion.getExtraSettings();
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		return new LiferayFileEntry(_fileVersion.getFileEntry());
	}

	public long getFileEntryId() {
		return _fileVersion.getFileEntryId();
	}

	public long getFileVersionId() {
		return _fileVersion.getFileVersionId();
	}

	public String getIcon() {
		return _fileVersion.getIcon();
	}

	public Object getModel() {
		return _fileVersion;
	}

	public long getPrimaryKey() {
		return _fileVersion.getPrimaryKey();
	}

	public long getRepositoryId() {
		return _fileVersion.getGroupId();
	}

	public long getSize() {
		return _fileVersion.getSize();
	}

	public int getStatus() {
		return _fileVersion.getStatus();
	}

	public long getStatusByUserId() {
		return _fileVersion.getStatusByUserId();
	}

	public String getStatusByUserName() {
		return _fileVersion.getStatusByUserName();
	}

	public String getStatusByUserUuid() throws SystemException {
		return _fileVersion.getStatusByUserUuid();
	}

	public Date getStatusDate() {
		return _fileVersion.getStatusDate();
	}

	public String getTitle() {
		return _fileVersion.getTitle();
	}

	public long getUserId() {
		return _fileVersion.getUserId();
	}

	public String getUserName() {
		return _fileVersion.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _fileVersion.getUserUuid();
	}

	public String getVersion() {
		return _fileVersion.getVersion();
	}

	public boolean isApproved() {
		return _fileVersion.isApproved();
	}

	public boolean isDraft() {
		return _fileVersion.isDraft();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isExpired() {
		return _fileVersion.isExpired();
	}

	public boolean isPending() {
		return _fileVersion.isPending();
	}

	public void prepare() throws SystemException {
		_fileVersion.setUserUuid(getUserUuid());
	}
	public FileVersion toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileVersion(_fileVersion.toEscapedModel(), true);
		}
	}

	private boolean _escapedModel = false;

	private DLFileVersion _fileVersion;

}