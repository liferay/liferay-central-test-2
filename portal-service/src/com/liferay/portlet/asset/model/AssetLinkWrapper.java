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
 * This class is a wrapper for {@link AssetLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLink
 * @generated
 */
public class AssetLinkWrapper implements AssetLink {
	public AssetLinkWrapper(AssetLink assetLink) {
		_assetLink = assetLink;
	}

	public long getPrimaryKey() {
		return _assetLink.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetLink.setPrimaryKey(pk);
	}

	public long getLinkId() {
		return _assetLink.getLinkId();
	}

	public void setLinkId(long linkId) {
		_assetLink.setLinkId(linkId);
	}

	public long getCompanyId() {
		return _assetLink.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetLink.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetLink.getUserId();
	}

	public void setUserId(long userId) {
		_assetLink.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLink.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetLink.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetLink.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetLink.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetLink.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetLink.setCreateDate(createDate);
	}

	public long getEntryId1() {
		return _assetLink.getEntryId1();
	}

	public void setEntryId1(long entryId1) {
		_assetLink.setEntryId1(entryId1);
	}

	public long getEntryId2() {
		return _assetLink.getEntryId2();
	}

	public void setEntryId2(long entryId2) {
		_assetLink.setEntryId2(entryId2);
	}

	public int getType() {
		return _assetLink.getType();
	}

	public void setType(int type) {
		_assetLink.setType(type);
	}

	public int getWeight() {
		return _assetLink.getWeight();
	}

	public void setWeight(int weight) {
		_assetLink.setWeight(weight);
	}

	public com.liferay.portlet.asset.model.AssetLink toEscapedModel() {
		return _assetLink.toEscapedModel();
	}

	public boolean isNew() {
		return _assetLink.isNew();
	}

	public void setNew(boolean n) {
		_assetLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetLink.clone();
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetLink assetLink) {
		return _assetLink.compareTo(assetLink);
	}

	public int hashCode() {
		return _assetLink.hashCode();
	}

	public java.lang.String toString() {
		return _assetLink.toString();
	}

	public java.lang.String toXmlString() {
		return _assetLink.toXmlString();
	}

	public AssetLink getWrappedAssetLink() {
		return _assetLink;
	}

	private AssetLink _assetLink;
}