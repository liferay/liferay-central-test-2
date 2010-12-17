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

	/**
	* Gets the primary key of this d l file entry.
	*
	* @return the primary key of this d l file entry
	*/
	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l file entry
	*
	* @param pk the primary key of this d l file entry
	*/
	public void setPrimaryKey(long pk) {
		_dlFileEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d l file entry.
	*
	* @return the uuid of this d l file entry
	*/
	public java.lang.String getUuid() {
		return _dlFileEntry.getUuid();
	}

	/**
	* Sets the uuid of this d l file entry.
	*
	* @param uuid the uuid of this d l file entry
	*/
	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	/**
	* Gets the file entry id of this d l file entry.
	*
	* @return the file entry id of this d l file entry
	*/
	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	/**
	* Sets the file entry id of this d l file entry.
	*
	* @param fileEntryId the file entry id of this d l file entry
	*/
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Gets the group id of this d l file entry.
	*
	* @return the group id of this d l file entry
	*/
	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	/**
	* Sets the group id of this d l file entry.
	*
	* @param groupId the group id of this d l file entry
	*/
	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	/**
	* Gets the company id of this d l file entry.
	*
	* @return the company id of this d l file entry
	*/
	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	/**
	* Sets the company id of this d l file entry.
	*
	* @param companyId the company id of this d l file entry
	*/
	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this d l file entry.
	*
	* @return the user id of this d l file entry
	*/
	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	/**
	* Sets the user id of this d l file entry.
	*
	* @param userId the user id of this d l file entry
	*/
	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d l file entry.
	*
	* @return the user uuid of this d l file entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this d l file entry.
	*
	* @param userUuid the user uuid of this d l file entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d l file entry.
	*
	* @return the user name of this d l file entry
	*/
	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
	}

	/**
	* Sets the user name of this d l file entry.
	*
	* @param userName the user name of this d l file entry
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileEntry.setUserName(userName);
	}

	/**
	* Gets the version user id of this d l file entry.
	*
	* @return the version user id of this d l file entry
	*/
	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	/**
	* Sets the version user id of this d l file entry.
	*
	* @param versionUserId the version user id of this d l file entry
	*/
	public void setVersionUserId(long versionUserId) {
		_dlFileEntry.setVersionUserId(versionUserId);
	}

	/**
	* Gets the version user uuid of this d l file entry.
	*
	* @return the version user uuid of this d l file entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	/**
	* Sets the version user uuid of this d l file entry.
	*
	* @param versionUserUuid the version user uuid of this d l file entry
	*/
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_dlFileEntry.setVersionUserUuid(versionUserUuid);
	}

	/**
	* Gets the version user name of this d l file entry.
	*
	* @return the version user name of this d l file entry
	*/
	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	/**
	* Sets the version user name of this d l file entry.
	*
	* @param versionUserName the version user name of this d l file entry
	*/
	public void setVersionUserName(java.lang.String versionUserName) {
		_dlFileEntry.setVersionUserName(versionUserName);
	}

	/**
	* Gets the create date of this d l file entry.
	*
	* @return the create date of this d l file entry
	*/
	public java.util.Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	/**
	* Sets the create date of this d l file entry.
	*
	* @param createDate the create date of this d l file entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d l file entry.
	*
	* @return the modified date of this d l file entry
	*/
	public java.util.Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this d l file entry.
	*
	* @param modifiedDate the modified date of this d l file entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the folder id of this d l file entry.
	*
	* @return the folder id of this d l file entry
	*/
	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	/**
	* Sets the folder id of this d l file entry.
	*
	* @param folderId the folder id of this d l file entry
	*/
	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
	}

	/**
	* Gets the name of this d l file entry.
	*
	* @return the name of this d l file entry
	*/
	public java.lang.String getName() {
		return _dlFileEntry.getName();
	}

	/**
	* Sets the name of this d l file entry.
	*
	* @param name the name of this d l file entry
	*/
	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
	}

	/**
	* Gets the extension of this d l file entry.
	*
	* @return the extension of this d l file entry
	*/
	public java.lang.String getExtension() {
		return _dlFileEntry.getExtension();
	}

	/**
	* Sets the extension of this d l file entry.
	*
	* @param extension the extension of this d l file entry
	*/
	public void setExtension(java.lang.String extension) {
		_dlFileEntry.setExtension(extension);
	}

	/**
	* Gets the title of this d l file entry.
	*
	* @return the title of this d l file entry
	*/
	public java.lang.String getTitle() {
		return _dlFileEntry.getTitle();
	}

	/**
	* Sets the title of this d l file entry.
	*
	* @param title the title of this d l file entry
	*/
	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
	}

	/**
	* Gets the description of this d l file entry.
	*
	* @return the description of this d l file entry
	*/
	public java.lang.String getDescription() {
		return _dlFileEntry.getDescription();
	}

	/**
	* Sets the description of this d l file entry.
	*
	* @param description the description of this d l file entry
	*/
	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
	}

	/**
	* Gets the extra settings of this d l file entry.
	*
	* @return the extra settings of this d l file entry
	*/
	public java.lang.String getExtraSettings() {
		return _dlFileEntry.getExtraSettings();
	}

	/**
	* Sets the extra settings of this d l file entry.
	*
	* @param extraSettings the extra settings of this d l file entry
	*/
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
	}

	/**
	* Gets the version of this d l file entry.
	*
	* @return the version of this d l file entry
	*/
	public java.lang.String getVersion() {
		return _dlFileEntry.getVersion();
	}

	/**
	* Sets the version of this d l file entry.
	*
	* @param version the version of this d l file entry
	*/
	public void setVersion(java.lang.String version) {
		_dlFileEntry.setVersion(version);
	}

	/**
	* Gets the size of this d l file entry.
	*
	* @return the size of this d l file entry
	*/
	public long getSize() {
		return _dlFileEntry.getSize();
	}

	/**
	* Sets the size of this d l file entry.
	*
	* @param size the size of this d l file entry
	*/
	public void setSize(long size) {
		_dlFileEntry.setSize(size);
	}

	/**
	* Gets the read count of this d l file entry.
	*
	* @return the read count of this d l file entry
	*/
	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	/**
	* Sets the read count of this d l file entry.
	*
	* @param readCount the read count of this d l file entry
	*/
	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
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
		return new DLFileEntryWrapper((DLFileEntry)_dlFileEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry toEscapedModel() {
		return new DLFileEntryWrapper(_dlFileEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream();
	}

	public java.io.InputStream getContentStream(java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream(version);
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion(version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersions(status);
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

	public long getDataRepositoryId() {
		return _dlFileEntry.getDataRepositoryId();
	}

	public boolean hasLock() {
		return _dlFileEntry.hasLock();
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