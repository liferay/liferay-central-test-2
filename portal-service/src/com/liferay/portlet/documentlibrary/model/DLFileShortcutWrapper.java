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
 * This class is a wrapper for {@link DLFileShortcut}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcut
 * @generated
 */
public class DLFileShortcutWrapper implements DLFileShortcut {
	public DLFileShortcutWrapper(DLFileShortcut dlFileShortcut) {
		_dlFileShortcut = dlFileShortcut;
	}

	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileShortcut.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	public long getFileShortcutId() {
		return _dlFileShortcut.getFileShortcutId();
	}

	public void setFileShortcutId(long fileShortcutId) {
		_dlFileShortcut.setFileShortcutId(fileShortcutId);
	}

	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileShortcut.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileShortcut.setModifiedDate(modifiedDate);
	}

	public long getFolderId() {
		return _dlFileShortcut.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileShortcut.setFolderId(folderId);
	}

	public long getToFolderId() {
		return _dlFileShortcut.getToFolderId();
	}

	public void setToFolderId(long toFolderId) {
		_dlFileShortcut.setToFolderId(toFolderId);
	}

	public java.lang.String getToName() {
		return _dlFileShortcut.getToName();
	}

	public void setToName(java.lang.String toName) {
		_dlFileShortcut.setToName(toName);
	}

	public int getStatus() {
		return _dlFileShortcut.getStatus();
	}

	public void setStatus(int status) {
		_dlFileShortcut.setStatus(status);
	}

	public long getStatusByUserId() {
		return _dlFileShortcut.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_dlFileShortcut.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileShortcut.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _dlFileShortcut.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileShortcut.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _dlFileShortcut.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_dlFileShortcut.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _dlFileShortcut.isApproved();
	}

	public boolean isDraft() {
		return _dlFileShortcut.isDraft();
	}

	public boolean isExpired() {
		return _dlFileShortcut.isExpired();
	}

	public boolean isPending() {
		return _dlFileShortcut.isPending();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut toEscapedModel() {
		return _dlFileShortcut.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileShortcut.isNew();
	}

	public void setNew(boolean n) {
		_dlFileShortcut.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileShortcut.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileShortcut.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileShortcut.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileShortcut.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileShortcut.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileShortcut.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileShortcut.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcut.compareTo(dlFileShortcut);
	}

	public int hashCode() {
		return _dlFileShortcut.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileShortcut.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileShortcut.toXmlString();
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder() {
		return _dlFileShortcut.getFolder();
	}

	public java.lang.String getToTitle() {
		return _dlFileShortcut.getToTitle();
	}

	public DLFileShortcut getWrappedDLFileShortcut() {
		return _dlFileShortcut;
	}

	private DLFileShortcut _dlFileShortcut;
}