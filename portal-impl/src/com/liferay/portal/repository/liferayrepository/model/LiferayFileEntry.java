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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Lock;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class LiferayFileEntry extends LiferayModel implements FileEntry {

	public LiferayFileEntry(DLFileEntry fileEntry) {
		_fileEntry = fileEntry;
	}

	public LiferayFileEntry(DLFileEntry fileEntry, boolean escapedModel) {
		_fileEntry = fileEntry;
		_escapedModel = escapedModel;
	}

	public long getCompanyId() {
		return _fileEntry.getCompanyId();
	}

	public InputStream getContentStream()
		throws PortalException, SystemException {

		return _fileEntry.getContentStream();
	}

	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		return _fileEntry.getContentStream(version);
	}

	public Date getCreateDate() {
		return _fileEntry.getCreateDate();
	}

	public String getDescription() {
		return _fileEntry.getDescription();
	}

	public String getExtension() {
		return _fileEntry.getExtension();
	}

	public String getExtraSettings() {
		return _fileEntry.getExtraSettings();
	}

	public UnicodeProperties getExtraSettingsProperties() {
		return _fileEntry.getExtraSettingsProperties();
	}

	public long getFileEntryId() {
		return _fileEntry.getFileEntryId();
	}

	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		return new LiferayFileVersion(_fileEntry.getFileVersion());
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		return new LiferayFileVersion(_fileEntry.getFileVersion(version));
	}

	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		return convertFileVersions(_fileEntry.getFileVersions(status));
	}

	public Folder getFolder() {
		return new LiferayFolder(_fileEntry.getFolder());
	}

	public long getFolderId() {
		return _fileEntry.getFolderId();
	}

	public String getIcon() {
		return _fileEntry.getIcon();
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return new LiferayFileVersion(_fileEntry.getFileVersion());
	}

	public Lock getLock() {
		return _fileEntry.getLock();
	}

	public Object getModel() {
		return _fileEntry;
	}

	public Date getModifiedDate() {
		return _fileEntry.getModifiedDate();
	}

	public long getPrimaryKey() {
		return _fileEntry.getPrimaryKey();
	}

	public int getReadCount() {
		return _fileEntry.getReadCount();
	}

	public long getRepositoryId() {
		return _fileEntry.getGroupId();
	}

	public long getSize() {
		return _fileEntry.getSize();
	}

	public String getTitle() {
		return _fileEntry.getTitle();
	}

	public long getUserId() {
		return _fileEntry.getUserId();
	}

	public String getUserName() {
		return _fileEntry.getVersionUserName();
	}

	public String getUserUuid() throws SystemException {
		return _fileEntry.getUserUuid();
	}

	public String getUuid() {
		return _fileEntry.getUuid();
	}

	public String getVersion() {
		return _fileEntry.getVersion();
	}

	public long getVersionUserId() {
		return _fileEntry.getVersionUserId();
	}

	public String getVersionUserName() {
		return _fileEntry.getVersionUserName();
	}

	public String getVersionUserUuid() throws SystemException {
		return _fileEntry.getVersionUserUuid();
	}

	public boolean hasLock() {
		return _fileEntry.hasLock();
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isLocked() {
		return _fileEntry.isLocked();
	}

	public void prepare() throws SystemException {
		_fileEntry.setUserUuid(getUserUuid());
	}

	public FileEntry toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileEntry(_fileEntry.toEscapedModel(), true);
		}
	}

	private boolean _escapedModel = false;

	private DLFileEntry _fileEntry;

}