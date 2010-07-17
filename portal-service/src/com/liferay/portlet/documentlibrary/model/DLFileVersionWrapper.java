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
 * This class is a wrapper for {@link DLFileVersion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersion
 * @generated
 */
public class DLFileVersionWrapper implements DLFileVersion {
	public DLFileVersionWrapper(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileVersion.setPrimaryKey(pk);
	}

	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	public void setFileVersionId(long fileVersionId) {
		_dlFileVersion.setFileVersionId(fileVersionId);
	}

	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFileVersion.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFileVersion.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileVersion.setCreateDate(createDate);
	}

	public long getFolderId() {
		return _dlFileVersion.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileVersion.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileVersion.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileVersion.setName(name);
	}

	public java.lang.String getExtension() {
		return _dlFileVersion.getExtension();
	}

	public void setExtension(java.lang.String extension) {
		_dlFileVersion.setExtension(extension);
	}

	public java.lang.String getTitle() {
		return _dlFileVersion.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_dlFileVersion.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _dlFileVersion.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_dlFileVersion.setDescription(description);
	}

	public java.lang.String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	public void setChangeLog(java.lang.String changeLog) {
		_dlFileVersion.setChangeLog(changeLog);
	}

	public java.lang.String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileVersion.setExtraSettings(extraSettings);
	}

	public java.lang.String getVersion() {
		return _dlFileVersion.getVersion();
	}

	public void setVersion(java.lang.String version) {
		_dlFileVersion.setVersion(version);
	}

	public long getSize() {
		return _dlFileVersion.getSize();
	}

	public void setSize(long size) {
		_dlFileVersion.setSize(size);
	}

	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	public void setStatus(int status) {
		_dlFileVersion.setStatus(status);
	}

	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_dlFileVersion.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileVersion.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileVersion.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_dlFileVersion.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion toEscapedModel() {
		return _dlFileVersion.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileVersion.isNew();
	}

	public void setNew(boolean n) {
		_dlFileVersion.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileVersion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileVersion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileVersion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileVersion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileVersion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileVersion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileVersion.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return _dlFileVersion.compareTo(dlFileVersion);
	}

	public int hashCode() {
		return _dlFileVersion.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileVersion.toXmlString();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getFileEntry();
	}

	public java.lang.String getIcon() {
		return _dlFileVersion.getIcon();
	}

	public DLFileVersion getWrappedDLFileVersion() {
		return _dlFileVersion;
	}

	private DLFileVersion _dlFileVersion;
}