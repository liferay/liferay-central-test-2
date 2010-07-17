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

package com.liferay.portlet.asset.model;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagProperty}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagProperty
 * @generated
 */
public class AssetTagPropertyWrapper implements AssetTagProperty {
	public AssetTagPropertyWrapper(AssetTagProperty assetTagProperty) {
		_assetTagProperty = assetTagProperty;
	}

	public long getPrimaryKey() {
		return _assetTagProperty.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetTagProperty.setPrimaryKey(pk);
	}

	public long getTagPropertyId() {
		return _assetTagProperty.getTagPropertyId();
	}

	public void setTagPropertyId(long tagPropertyId) {
		_assetTagProperty.setTagPropertyId(tagPropertyId);
	}

	public long getCompanyId() {
		return _assetTagProperty.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetTagProperty.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetTagProperty.getUserId();
	}

	public void setUserId(long userId) {
		_assetTagProperty.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagProperty.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetTagProperty.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetTagProperty.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetTagProperty.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetTagProperty.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetTagProperty.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetTagProperty.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetTagProperty.setModifiedDate(modifiedDate);
	}

	public long getTagId() {
		return _assetTagProperty.getTagId();
	}

	public void setTagId(long tagId) {
		_assetTagProperty.setTagId(tagId);
	}

	public java.lang.String getKey() {
		return _assetTagProperty.getKey();
	}

	public void setKey(java.lang.String key) {
		_assetTagProperty.setKey(key);
	}

	public java.lang.String getValue() {
		return _assetTagProperty.getValue();
	}

	public void setValue(java.lang.String value) {
		_assetTagProperty.setValue(value);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty toEscapedModel() {
		return _assetTagProperty.toEscapedModel();
	}

	public boolean isNew() {
		return _assetTagProperty.isNew();
	}

	public void setNew(boolean n) {
		_assetTagProperty.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagProperty.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagProperty.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagProperty.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTagProperty.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagProperty.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagProperty.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagProperty.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetTagProperty.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return _assetTagProperty.compareTo(assetTagProperty);
	}

	public int hashCode() {
		return _assetTagProperty.hashCode();
	}

	public java.lang.String toString() {
		return _assetTagProperty.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagProperty.toXmlString();
	}

	public AssetTagProperty getWrappedAssetTagProperty() {
		return _assetTagProperty;
	}

	private AssetTagProperty _assetTagProperty;
}