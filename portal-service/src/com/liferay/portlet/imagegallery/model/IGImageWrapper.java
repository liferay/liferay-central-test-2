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
 * This class is a wrapper for {@link IGImage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImage
 * @generated
 */
public class IGImageWrapper implements IGImage {
	public IGImageWrapper(IGImage igImage) {
		_igImage = igImage;
	}

	public long getPrimaryKey() {
		return _igImage.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_igImage.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _igImage.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_igImage.setUuid(uuid);
	}

	public long getImageId() {
		return _igImage.getImageId();
	}

	public void setImageId(long imageId) {
		_igImage.setImageId(imageId);
	}

	public long getGroupId() {
		return _igImage.getGroupId();
	}

	public void setGroupId(long groupId) {
		_igImage.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _igImage.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_igImage.setCompanyId(companyId);
	}

	public long getUserId() {
		return _igImage.getUserId();
	}

	public void setUserId(long userId) {
		_igImage.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImage.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_igImage.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _igImage.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_igImage.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _igImage.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_igImage.setModifiedDate(modifiedDate);
	}

	public long getFolderId() {
		return _igImage.getFolderId();
	}

	public void setFolderId(long folderId) {
		_igImage.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _igImage.getName();
	}

	public void setName(java.lang.String name) {
		_igImage.setName(name);
	}

	public java.lang.String getDescription() {
		return _igImage.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_igImage.setDescription(description);
	}

	public long getSmallImageId() {
		return _igImage.getSmallImageId();
	}

	public void setSmallImageId(long smallImageId) {
		_igImage.setSmallImageId(smallImageId);
	}

	public long getLargeImageId() {
		return _igImage.getLargeImageId();
	}

	public void setLargeImageId(long largeImageId) {
		_igImage.setLargeImageId(largeImageId);
	}

	public long getCustom1ImageId() {
		return _igImage.getCustom1ImageId();
	}

	public void setCustom1ImageId(long custom1ImageId) {
		_igImage.setCustom1ImageId(custom1ImageId);
	}

	public long getCustom2ImageId() {
		return _igImage.getCustom2ImageId();
	}

	public void setCustom2ImageId(long custom2ImageId) {
		_igImage.setCustom2ImageId(custom2ImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage toEscapedModel() {
		return _igImage.toEscapedModel();
	}

	public boolean isNew() {
		return _igImage.isNew();
	}

	public void setNew(boolean n) {
		_igImage.setNew(n);
	}

	public boolean isCachedModel() {
		return _igImage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_igImage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _igImage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_igImage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _igImage.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _igImage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_igImage.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _igImage.clone();
	}

	public int compareTo(com.liferay.portlet.imagegallery.model.IGImage igImage) {
		return _igImage.compareTo(igImage);
	}

	public int hashCode() {
		return _igImage.hashCode();
	}

	public java.lang.String toString() {
		return _igImage.toString();
	}

	public java.lang.String toXmlString() {
		return _igImage.toXmlString();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder() {
		return _igImage.getFolder();
	}

	public int getImageSize() {
		return _igImage.getImageSize();
	}

	public java.lang.String getImageType() {
		return _igImage.getImageType();
	}

	public java.lang.String getNameWithExtension() {
		return _igImage.getNameWithExtension();
	}

	public void setImageType(java.lang.String imageType) {
		_igImage.setImageType(imageType);
	}

	public IGImage getWrappedIGImage() {
		return _igImage;
	}

	private IGImage _igImage;
}