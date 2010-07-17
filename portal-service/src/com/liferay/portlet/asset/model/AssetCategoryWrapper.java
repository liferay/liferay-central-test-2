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
 * This class is a wrapper for {@link AssetCategory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategory
 * @generated
 */
public class AssetCategoryWrapper implements AssetCategory {
	public AssetCategoryWrapper(AssetCategory assetCategory) {
		_assetCategory = assetCategory;
	}

	public long getPrimaryKey() {
		return _assetCategory.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetCategory.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _assetCategory.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_assetCategory.setUuid(uuid);
	}

	public long getCategoryId() {
		return _assetCategory.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_assetCategory.setCategoryId(categoryId);
	}

	public long getGroupId() {
		return _assetCategory.getGroupId();
	}

	public void setGroupId(long groupId) {
		_assetCategory.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _assetCategory.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetCategory.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetCategory.getUserId();
	}

	public void setUserId(long userId) {
		_assetCategory.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategory.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetCategory.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetCategory.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetCategory.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetCategory.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetCategory.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetCategory.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetCategory.setModifiedDate(modifiedDate);
	}

	public long getParentCategoryId() {
		return _assetCategory.getParentCategoryId();
	}

	public void setParentCategoryId(long parentCategoryId) {
		_assetCategory.setParentCategoryId(parentCategoryId);
	}

	public long getLeftCategoryId() {
		return _assetCategory.getLeftCategoryId();
	}

	public void setLeftCategoryId(long leftCategoryId) {
		_assetCategory.setLeftCategoryId(leftCategoryId);
	}

	public long getRightCategoryId() {
		return _assetCategory.getRightCategoryId();
	}

	public void setRightCategoryId(long rightCategoryId) {
		_assetCategory.setRightCategoryId(rightCategoryId);
	}

	public java.lang.String getName() {
		return _assetCategory.getName();
	}

	public void setName(java.lang.String name) {
		_assetCategory.setName(name);
	}

	public java.lang.String getTitle() {
		return _assetCategory.getTitle();
	}

	public java.lang.String getTitle(java.util.Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetCategory.getTitle(locale, useDefault);
	}

	public java.lang.String getTitle(java.lang.String languageId) {
		return _assetCategory.getTitle(languageId);
	}

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetCategory.getTitle(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetCategory.getTitleMap();
	}

	public void setTitle(java.lang.String title) {
		_assetCategory.setTitle(title);
	}

	public void setTitle(java.util.Locale locale, java.lang.String title) {
		_assetCategory.setTitle(locale, title);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_assetCategory.setTitleMap(titleMap);
	}

	public long getVocabularyId() {
		return _assetCategory.getVocabularyId();
	}

	public void setVocabularyId(long vocabularyId) {
		_assetCategory.setVocabularyId(vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetCategory toEscapedModel() {
		return _assetCategory.toEscapedModel();
	}

	public boolean isNew() {
		return _assetCategory.isNew();
	}

	public void setNew(boolean n) {
		_assetCategory.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetCategory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetCategory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetCategory.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetCategory.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetCategory.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetCategory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetCategory.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetCategory.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return _assetCategory.compareTo(assetCategory);
	}

	public int hashCode() {
		return _assetCategory.hashCode();
	}

	public java.lang.String toString() {
		return _assetCategory.toString();
	}

	public java.lang.String toXmlString() {
		return _assetCategory.toXmlString();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategory.getAncestors();
	}

	public boolean isRootCategory() {
		return _assetCategory.isRootCategory();
	}

	public AssetCategory getWrappedAssetCategory() {
		return _assetCategory;
	}

	private AssetCategory _assetCategory;
}