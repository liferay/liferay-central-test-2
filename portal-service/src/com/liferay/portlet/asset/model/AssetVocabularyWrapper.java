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
 * This class is a wrapper for {@link AssetVocabulary}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetVocabulary
 * @generated
 */
public class AssetVocabularyWrapper implements AssetVocabulary {
	public AssetVocabularyWrapper(AssetVocabulary assetVocabulary) {
		_assetVocabulary = assetVocabulary;
	}

	public long getPrimaryKey() {
		return _assetVocabulary.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetVocabulary.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _assetVocabulary.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_assetVocabulary.setUuid(uuid);
	}

	public long getVocabularyId() {
		return _assetVocabulary.getVocabularyId();
	}

	public void setVocabularyId(long vocabularyId) {
		_assetVocabulary.setVocabularyId(vocabularyId);
	}

	public long getGroupId() {
		return _assetVocabulary.getGroupId();
	}

	public void setGroupId(long groupId) {
		_assetVocabulary.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _assetVocabulary.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetVocabulary.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetVocabulary.getUserId();
	}

	public void setUserId(long userId) {
		_assetVocabulary.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetVocabulary.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetVocabulary.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetVocabulary.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetVocabulary.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetVocabulary.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetVocabulary.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetVocabulary.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetVocabulary.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _assetVocabulary.getName();
	}

	public void setName(java.lang.String name) {
		_assetVocabulary.setName(name);
	}

	public java.lang.String getTitle() {
		return _assetVocabulary.getTitle();
	}

	public java.lang.String getTitle(java.util.Locale locale) {
		return _assetVocabulary.getTitle(locale);
	}

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetVocabulary.getTitle(locale, useDefault);
	}

	public java.lang.String getTitle(java.lang.String languageId) {
		return _assetVocabulary.getTitle(languageId);
	}

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetVocabulary.getTitle(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetVocabulary.getTitleMap();
	}

	public void setTitle(java.lang.String title) {
		_assetVocabulary.setTitle(title);
	}

	public void setTitle(java.util.Locale locale, java.lang.String title) {
		_assetVocabulary.setTitle(locale, title);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_assetVocabulary.setTitleMap(titleMap);
	}

	public java.lang.String getDescription() {
		return _assetVocabulary.getDescription();
	}

	public java.lang.String getDescription(java.util.Locale locale) {
		return _assetVocabulary.getDescription(locale);
	}

	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _assetVocabulary.getDescription(locale, useDefault);
	}

	public java.lang.String getDescription(java.lang.String languageId) {
		return _assetVocabulary.getDescription(languageId);
	}

	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _assetVocabulary.getDescription(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _assetVocabulary.getDescriptionMap();
	}

	public void setDescription(java.lang.String description) {
		_assetVocabulary.setDescription(description);
	}

	public void setDescription(java.util.Locale locale,
		java.lang.String description) {
		_assetVocabulary.setDescription(locale, description);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_assetVocabulary.setDescriptionMap(descriptionMap);
	}

	public java.lang.String getSettings() {
		return _assetVocabulary.getSettings();
	}

	public void setSettings(java.lang.String settings) {
		_assetVocabulary.setSettings(settings);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary toEscapedModel() {
		return _assetVocabulary.toEscapedModel();
	}

	public boolean isNew() {
		return _assetVocabulary.isNew();
	}

	public void setNew(boolean n) {
		_assetVocabulary.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetVocabulary.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetVocabulary.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetVocabulary.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetVocabulary.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetVocabulary.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetVocabulary.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetVocabulary.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetVocabulary.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary) {
		return _assetVocabulary.compareTo(assetVocabulary);
	}

	public int hashCode() {
		return _assetVocabulary.hashCode();
	}

	public java.lang.String toString() {
		return _assetVocabulary.toString();
	}

	public java.lang.String toXmlString() {
		return _assetVocabulary.toXmlString();
	}

	public AssetVocabulary getWrappedAssetVocabulary() {
		return _assetVocabulary;
	}

	private AssetVocabulary _assetVocabulary;
}