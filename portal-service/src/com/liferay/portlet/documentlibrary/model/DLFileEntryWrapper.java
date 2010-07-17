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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntry
 * @generated
 */
public class DLFileEntryWrapper implements DLFileEntry {
	public DLFileEntryWrapper(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileEntry.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _dlFileEntry.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
	}

	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFileEntry.setUserName(userName);
	}

	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	public void setVersionUserId(long versionUserId) {
		_dlFileEntry.setVersionUserId(versionUserId);
	}

	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_dlFileEntry.setVersionUserUuid(versionUserUuid);
	}

	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	public void setVersionUserName(java.lang.String versionUserName) {
		_dlFileEntry.setVersionUserName(versionUserName);
	}

	public java.util.Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
	}

	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileEntry.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
	}

	public java.lang.String getExtension() {
		return _dlFileEntry.getExtension();
	}

	public void setExtension(java.lang.String extension) {
		_dlFileEntry.setExtension(extension);
	}

	public java.lang.String getTitle() {
		return _dlFileEntry.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _dlFileEntry.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
	}

	public java.lang.String getExtraSettings() {
		return _dlFileEntry.getExtraSettings();
	}

	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
	}

	public java.lang.String getVersion() {
		return _dlFileEntry.getVersion();
	}

	public void setVersion(java.lang.String version) {
		_dlFileEntry.setVersion(version);
	}

	public long getSize() {
		return _dlFileEntry.getSize();
	}

	public void setSize(long size) {
		_dlFileEntry.setSize(size);
	}

	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry toEscapedModel() {
		return _dlFileEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileEntry.isNew();
	}

	public void setNew(boolean n) {
		_dlFileEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion();
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder() {
		return _dlFileEntry.getFolder();
	}

	public java.lang.String getIcon() {
		return _dlFileEntry.getIcon();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getLatestFileVersion();
	}

	public com.liferay.portal.model.Lock getLock() {
		return _dlFileEntry.getLock();
	}

	public java.lang.String getLuceneProperties() {
		return _dlFileEntry.getLuceneProperties();
	}

	public long getRepositoryId() {
		return _dlFileEntry.getRepositoryId();
	}

	public boolean hasLock(long userId) {
		return _dlFileEntry.hasLock(userId);
	}

	public boolean isLocked() {
		return _dlFileEntry.isLocked();
	}

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);
	}

	public DLFileEntry getWrappedDLFileEntry() {
		return _dlFileEntry;
	}

	private DLFileEntry _dlFileEntry;
}