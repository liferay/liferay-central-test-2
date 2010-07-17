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
 * This class is a wrapper for {@link AssetCategoryProperty}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryProperty
 * @generated
 */
public class AssetCategoryPropertyWrapper implements AssetCategoryProperty {
	public AssetCategoryPropertyWrapper(
		AssetCategoryProperty assetCategoryProperty) {
		_assetCategoryProperty = assetCategoryProperty;
	}

	public long getPrimaryKey() {
		return _assetCategoryProperty.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetCategoryProperty.setPrimaryKey(pk);
	}

	public long getCategoryPropertyId() {
		return _assetCategoryProperty.getCategoryPropertyId();
	}

	public void setCategoryPropertyId(long categoryPropertyId) {
		_assetCategoryProperty.setCategoryPropertyId(categoryPropertyId);
	}

	public long getCompanyId() {
		return _assetCategoryProperty.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetCategoryProperty.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetCategoryProperty.getUserId();
	}

	public void setUserId(long userId) {
		_assetCategoryProperty.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryProperty.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetCategoryProperty.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetCategoryProperty.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetCategoryProperty.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetCategoryProperty.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetCategoryProperty.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetCategoryProperty.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetCategoryProperty.setModifiedDate(modifiedDate);
	}

	public long getCategoryId() {
		return _assetCategoryProperty.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_assetCategoryProperty.setCategoryId(categoryId);
	}

	public java.lang.String getKey() {
		return _assetCategoryProperty.getKey();
	}

	public void setKey(java.lang.String key) {
		_assetCategoryProperty.setKey(key);
	}

	public java.lang.String getValue() {
		return _assetCategoryProperty.getValue();
	}

	public void setValue(java.lang.String value) {
		_assetCategoryProperty.setValue(value);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty toEscapedModel() {
		return _assetCategoryProperty.toEscapedModel();
	}

	public boolean isNew() {
		return _assetCategoryProperty.isNew();
	}

	public void setNew(boolean n) {
		_assetCategoryProperty.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetCategoryProperty.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetCategoryProperty.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetCategoryProperty.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetCategoryProperty.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetCategoryProperty.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetCategoryProperty.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetCategoryProperty.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetCategoryProperty.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty) {
		return _assetCategoryProperty.compareTo(assetCategoryProperty);
	}

	public int hashCode() {
		return _assetCategoryProperty.hashCode();
	}

	public java.lang.String toString() {
		return _assetCategoryProperty.toString();
	}

	public java.lang.String toXmlString() {
		return _assetCategoryProperty.toXmlString();
	}

	public AssetCategoryProperty getWrappedAssetCategoryProperty() {
		return _assetCategoryProperty;
	}

	private AssetCategoryProperty _assetCategoryProperty;
}