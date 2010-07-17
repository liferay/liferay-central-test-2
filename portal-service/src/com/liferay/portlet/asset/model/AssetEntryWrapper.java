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
 * This class is a wrapper for {@link AssetEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntry
 * @generated
 */
public class AssetEntryWrapper implements AssetEntry {
	public AssetEntryWrapper(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public long getPrimaryKey() {
		return _assetEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetEntry.setPrimaryKey(pk);
	}

	public long getEntryId() {
		return _assetEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_assetEntry.setEntryId(entryId);
	}

	public long getGroupId() {
		return _assetEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_assetEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _assetEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetEntry.getUserId();
	}

	public void setUserId(long userId) {
		_assetEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _assetEntry.getClassName();
	}

	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_assetEntry.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _assetEntry.getClassPK();
	}

	public void setClassPK(long classPK) {
		_assetEntry.setClassPK(classPK);
	}

	public java.lang.String getClassUuid() {
		return _assetEntry.getClassUuid();
	}

	public void setClassUuid(java.lang.String classUuid) {
		_assetEntry.setClassUuid(classUuid);
	}

	public boolean getVisible() {
		return _assetEntry.getVisible();
	}

	public boolean isVisible() {
		return _assetEntry.isVisible();
	}

	public void setVisible(boolean visible) {
		_assetEntry.setVisible(visible);
	}

	public java.util.Date getStartDate() {
		return _assetEntry.getStartDate();
	}

	public void setStartDate(java.util.Date startDate) {
		_assetEntry.setStartDate(startDate);
	}

	public java.util.Date getEndDate() {
		return _assetEntry.getEndDate();
	}

	public void setEndDate(java.util.Date endDate) {
		_assetEntry.setEndDate(endDate);
	}

	public java.util.Date getPublishDate() {
		return _assetEntry.getPublishDate();
	}

	public void setPublishDate(java.util.Date publishDate) {
		_assetEntry.setPublishDate(publishDate);
	}

	public java.util.Date getExpirationDate() {
		return _assetEntry.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_assetEntry.setExpirationDate(expirationDate);
	}

	public java.lang.String getMimeType() {
		return _assetEntry.getMimeType();
	}

	public void setMimeType(java.lang.String mimeType) {
		_assetEntry.setMimeType(mimeType);
	}

	public java.lang.String getTitle() {
		return _assetEntry.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_assetEntry.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _assetEntry.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_assetEntry.setDescription(description);
	}

	public java.lang.String getSummary() {
		return _assetEntry.getSummary();
	}

	public void setSummary(java.lang.String summary) {
		_assetEntry.setSummary(summary);
	}

	public java.lang.String getUrl() {
		return _assetEntry.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_assetEntry.setUrl(url);
	}

	public int getHeight() {
		return _assetEntry.getHeight();
	}

	public void setHeight(int height) {
		_assetEntry.setHeight(height);
	}

	public int getWidth() {
		return _assetEntry.getWidth();
	}

	public void setWidth(int width) {
		_assetEntry.setWidth(width);
	}

	public double getPriority() {
		return _assetEntry.getPriority();
	}

	public void setPriority(double priority) {
		_assetEntry.setPriority(priority);
	}

	public int getViewCount() {
		return _assetEntry.getViewCount();
	}

	public void setViewCount(int viewCount) {
		_assetEntry.setViewCount(viewCount);
	}

	public com.liferay.portlet.asset.model.AssetEntry toEscapedModel() {
		return _assetEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _assetEntry.isNew();
	}

	public void setNew(boolean n) {
		_assetEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetEntry.clone();
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		return _assetEntry.compareTo(assetEntry);
	}

	public int hashCode() {
		return _assetEntry.hashCode();
	}

	public java.lang.String toString() {
		return _assetEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _assetEntry.toXmlString();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategories();
	}

	public long[] getCategoryIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategoryIds();
	}

	public double getSocialInformationEquity() {
		return _assetEntry.getSocialInformationEquity();
	}

	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTagNames();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTags();
	}

	public void updateSocialInformationEquity(double value) {
		_assetEntry.updateSocialInformationEquity(value);
	}

	public AssetEntry getWrappedAssetEntry() {
		return _assetEntry;
	}

	private AssetEntry _assetEntry;
}