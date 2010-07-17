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
 * This class is a wrapper for {@link AssetTag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTag
 * @generated
 */
public class AssetTagWrapper implements AssetTag {
	public AssetTagWrapper(AssetTag assetTag) {
		_assetTag = assetTag;
	}

	public long getPrimaryKey() {
		return _assetTag.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetTag.setPrimaryKey(pk);
	}

	public long getTagId() {
		return _assetTag.getTagId();
	}

	public void setTagId(long tagId) {
		_assetTag.setTagId(tagId);
	}

	public long getGroupId() {
		return _assetTag.getGroupId();
	}

	public void setGroupId(long groupId) {
		_assetTag.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _assetTag.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetTag.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetTag.getUserId();
	}

	public void setUserId(long userId) {
		_assetTag.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTag.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetTag.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetTag.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetTag.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetTag.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetTag.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetTag.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetTag.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _assetTag.getName();
	}

	public void setName(java.lang.String name) {
		_assetTag.setName(name);
	}

	public int getAssetCount() {
		return _assetTag.getAssetCount();
	}

	public void setAssetCount(int assetCount) {
		_assetTag.setAssetCount(assetCount);
	}

	public com.liferay.portlet.asset.model.AssetTag toEscapedModel() {
		return _assetTag.toEscapedModel();
	}

	public boolean isNew() {
		return _assetTag.isNew();
	}

	public void setNew(boolean n) {
		_assetTag.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTag.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTag.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetTag.clone();
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTag.compareTo(assetTag);
	}

	public int hashCode() {
		return _assetTag.hashCode();
	}

	public java.lang.String toString() {
		return _assetTag.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTag.toXmlString();
	}

	public AssetTag getWrappedAssetTag() {
		return _assetTag;
	}

	private AssetTag _assetTag;
}