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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayFileVersion extends LiferayModel implements FileVersion {

	public LiferayFileVersion(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	public LiferayFileVersion(
		DLFileVersion dlFileVersion, boolean escapedModel) {

		_dlFileVersion = dlFileVersion;
		_escapedModel = escapedModel;
	}

	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = _dlFileVersion.getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	public String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	public Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	public String getDescription() {
		return _dlFileVersion.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	public String getExtension() {
		return _dlFileVersion.getExtension();
	}

	public String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		return new LiferayFileEntry(_dlFileVersion.getFileEntry());
	}

	public long getFileEntryId() {
		return _dlFileVersion.getFileEntryId();
	}

	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	public String getIcon() {
		return _dlFileVersion.getIcon();
	}

	public Object getModel() {
		return _dlFileVersion;
	}

	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	public long getRepositoryId() {
		return _dlFileVersion.getGroupId();
	}

	public long getSize() {
		return _dlFileVersion.getSize();
	}

	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	public String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	public String getStatusByUserUuid() throws SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	public Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	public String getTitle() {
		return _dlFileVersion.getTitle();
	}

	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	public String getUserName() {
		return _dlFileVersion.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _dlFileVersion.getUserUuid();
	}

	public String getVersion() {
		return _dlFileVersion.getVersion();
	}

	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	public void prepare() throws SystemException {
		_dlFileVersion.setUserUuid(getUserUuid());
	}

	public FileVersion toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileVersion(
				_dlFileVersion.toEscapedModel(), true);
		}
	}

	private DLFileVersion _dlFileVersion;
	private boolean _escapedModel;

}