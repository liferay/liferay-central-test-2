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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Lock;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayFileEntry extends LiferayModel implements FileEntry {

	public LiferayFileEntry(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	public LiferayFileEntry(DLFileEntry fileEntry, boolean escapedModel) {
		_dlFileEntry = fileEntry;
		_escapedModel = escapedModel;
	}

	public Map<String, Serializable> getAttributes() {
		return _dlFileEntry.getExpandoBridge().getAttributes();
	}

	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	public InputStream getContentStream()
		throws PortalException, SystemException {

		return _dlFileEntry.getContentStream();
	}

	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		return _dlFileEntry.getContentStream(version);
	}

	public Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	public String getDescription() {
		return _dlFileEntry.getDescription();
	}

	public String getExtension() {
		return _dlFileEntry.getExtension();
	}

	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		return new LiferayFileVersion(_dlFileEntry.getFileVersion());
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		return new LiferayFileVersion(_dlFileEntry.getFileVersion(version));
	}

	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		return toFileVersions(_dlFileEntry.getFileVersions(status));
	}

	public Folder getFolder() {
		return new LiferayFolder(_dlFileEntry.getFolder());
	}

	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	public String getIcon() {
		return _dlFileEntry.getIcon();
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return new LiferayFileVersion(_dlFileEntry.getFileVersion());
	}

	public Lock getLock() {
		return _dlFileEntry.getLock();
	}

	public Object getModel() {
		return _dlFileEntry;
	}

	public Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	public long getRepositoryId() {
		return _dlFileEntry.getGroupId();
	}

	public long getSize() {
		return _dlFileEntry.getSize();
	}

	public String getTitle() {
		return _dlFileEntry.getTitle();
	}

	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	public String getUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	public String getUserUuid() throws SystemException {
		return _dlFileEntry.getUserUuid();
	}

	public String getUuid() {
		return _dlFileEntry.getUuid();
	}

	public String getVersion() {
		return _dlFileEntry.getVersion();
	}

	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	public String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	public String getVersionUserUuid() throws SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	public boolean hasLock() {
		return _dlFileEntry.hasLock();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isLocked() {
		return _dlFileEntry.isLocked();
	}

	public void prepare() throws SystemException {
		_dlFileEntry.setUserUuid(getUserUuid());
	}

	public FileEntry toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileEntry(_dlFileEntry.toEscapedModel(), true);
		}
	}

	private DLFileEntry _dlFileEntry;
	private boolean _escapedModel;

}