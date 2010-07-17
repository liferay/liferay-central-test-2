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

package com.liferay.portlet.imagegallery.model;

/**
 * <p>
 * This class is a wrapper for {@link IGFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolder
 * @generated
 */
public class IGFolderWrapper implements IGFolder {
	public IGFolderWrapper(IGFolder igFolder) {
		_igFolder = igFolder;
	}

	public long getPrimaryKey() {
		return _igFolder.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_igFolder.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _igFolder.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_igFolder.setUuid(uuid);
	}

	public long getFolderId() {
		return _igFolder.getFolderId();
	}

	public void setFolderId(long folderId) {
		_igFolder.setFolderId(folderId);
	}

	public long getGroupId() {
		return _igFolder.getGroupId();
	}

	public void setGroupId(long groupId) {
		_igFolder.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _igFolder.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_igFolder.setCompanyId(companyId);
	}

	public long getUserId() {
		return _igFolder.getUserId();
	}

	public void setUserId(long userId) {
		_igFolder.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_igFolder.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _igFolder.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_igFolder.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _igFolder.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_igFolder.setModifiedDate(modifiedDate);
	}

	public long getParentFolderId() {
		return _igFolder.getParentFolderId();
	}

	public void setParentFolderId(long parentFolderId) {
		_igFolder.setParentFolderId(parentFolderId);
	}

	public java.lang.String getName() {
		return _igFolder.getName();
	}

	public void setName(java.lang.String name) {
		_igFolder.setName(name);
	}

	public java.lang.String getDescription() {
		return _igFolder.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_igFolder.setDescription(description);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder toEscapedModel() {
		return _igFolder.toEscapedModel();
	}

	public boolean isNew() {
		return _igFolder.isNew();
	}

	public void setNew(boolean n) {
		_igFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _igFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_igFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _igFolder.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_igFolder.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _igFolder.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _igFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_igFolder.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _igFolder.clone();
	}

	public int compareTo(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder) {
		return _igFolder.compareTo(igFolder);
	}

	public int hashCode() {
		return _igFolder.hashCode();
	}

	public java.lang.String toString() {
		return _igFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _igFolder.toXmlString();
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getAncestors();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getParentFolder();
	}

	public boolean isRoot() {
		return _igFolder.isRoot();
	}

	public IGFolder getWrappedIGFolder() {
		return _igFolder;
	}

	private IGFolder _igFolder;
}