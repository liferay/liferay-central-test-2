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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticle}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticle
 * @generated
 */
public class JournalArticleWrapper implements JournalArticle {
	public JournalArticleWrapper(JournalArticle journalArticle) {
		_journalArticle = journalArticle;
	}

	public long getPrimaryKey() {
		return _journalArticle.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalArticle.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _journalArticle.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_journalArticle.setUuid(uuid);
	}

	public long getId() {
		return _journalArticle.getId();
	}

	public void setId(long id) {
		_journalArticle.setId(id);
	}

	public long getResourcePrimKey() {
		return _journalArticle.getResourcePrimKey();
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticle.setResourcePrimKey(resourcePrimKey);
	}

	public long getGroupId() {
		return _journalArticle.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalArticle.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _journalArticle.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_journalArticle.setCompanyId(companyId);
	}

	public long getUserId() {
		return _journalArticle.getUserId();
	}

	public void setUserId(long userId) {
		_journalArticle.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_journalArticle.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _journalArticle.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_journalArticle.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_journalArticle.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalArticle.setModifiedDate(modifiedDate);
	}

	public java.lang.String getArticleId() {
		return _journalArticle.getArticleId();
	}

	public void setArticleId(java.lang.String articleId) {
		_journalArticle.setArticleId(articleId);
	}

	public double getVersion() {
		return _journalArticle.getVersion();
	}

	public void setVersion(double version) {
		_journalArticle.setVersion(version);
	}

	public java.lang.String getTitle() {
		return _journalArticle.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_journalArticle.setTitle(title);
	}

	public java.lang.String getUrlTitle() {
		return _journalArticle.getUrlTitle();
	}

	public void setUrlTitle(java.lang.String urlTitle) {
		_journalArticle.setUrlTitle(urlTitle);
	}

	public java.lang.String getDescription() {
		return _journalArticle.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_journalArticle.setDescription(description);
	}

	public java.lang.String getContent() {
		return _journalArticle.getContent();
	}

	public void setContent(java.lang.String content) {
		_journalArticle.setContent(content);
	}

	public java.lang.String getType() {
		return _journalArticle.getType();
	}

	public void setType(java.lang.String type) {
		_journalArticle.setType(type);
	}

	public java.lang.String getStructureId() {
		return _journalArticle.getStructureId();
	}

	public void setStructureId(java.lang.String structureId) {
		_journalArticle.setStructureId(structureId);
	}

	public java.lang.String getTemplateId() {
		return _journalArticle.getTemplateId();
	}

	public void setTemplateId(java.lang.String templateId) {
		_journalArticle.setTemplateId(templateId);
	}

	public java.util.Date getDisplayDate() {
		return _journalArticle.getDisplayDate();
	}

	public void setDisplayDate(java.util.Date displayDate) {
		_journalArticle.setDisplayDate(displayDate);
	}

	public java.util.Date getExpirationDate() {
		return _journalArticle.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_journalArticle.setExpirationDate(expirationDate);
	}

	public java.util.Date getReviewDate() {
		return _journalArticle.getReviewDate();
	}

	public void setReviewDate(java.util.Date reviewDate) {
		_journalArticle.setReviewDate(reviewDate);
	}

	public boolean getIndexable() {
		return _journalArticle.getIndexable();
	}

	public boolean isIndexable() {
		return _journalArticle.isIndexable();
	}

	public void setIndexable(boolean indexable) {
		_journalArticle.setIndexable(indexable);
	}

	public boolean getSmallImage() {
		return _journalArticle.getSmallImage();
	}

	public boolean isSmallImage() {
		return _journalArticle.isSmallImage();
	}

	public void setSmallImage(boolean smallImage) {
		_journalArticle.setSmallImage(smallImage);
	}

	public long getSmallImageId() {
		return _journalArticle.getSmallImageId();
	}

	public void setSmallImageId(long smallImageId) {
		_journalArticle.setSmallImageId(smallImageId);
	}

	public java.lang.String getSmallImageURL() {
		return _journalArticle.getSmallImageURL();
	}

	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalArticle.setSmallImageURL(smallImageURL);
	}

	public int getStatus() {
		return _journalArticle.getStatus();
	}

	public void setStatus(int status) {
		_journalArticle.setStatus(status);
	}

	public long getStatusByUserId() {
		return _journalArticle.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_journalArticle.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_journalArticle.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _journalArticle.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_journalArticle.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _journalArticle.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_journalArticle.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _journalArticle.isApproved();
	}

	public boolean isDraft() {
		return _journalArticle.isDraft();
	}

	public boolean isExpired() {
		return _journalArticle.isExpired();
	}

	public boolean isPending() {
		return _journalArticle.isPending();
	}

	public com.liferay.portlet.journal.model.JournalArticle toEscapedModel() {
		return _journalArticle.toEscapedModel();
	}

	public boolean isNew() {
		return _journalArticle.isNew();
	}

	public void setNew(boolean n) {
		_journalArticle.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalArticle.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalArticle.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalArticle.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalArticle.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticle.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticle.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticle.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalArticle.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		return _journalArticle.compareTo(journalArticle);
	}

	public int hashCode() {
		return _journalArticle.hashCode();
	}

	public java.lang.String toString() {
		return _journalArticle.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticle.toXmlString();
	}

	public java.lang.String[] getAvailableLocales() {
		return _journalArticle.getAvailableLocales();
	}

	public java.lang.String getContentByLocale(java.lang.String languageId) {
		return _journalArticle.getContentByLocale(languageId);
	}

	public java.lang.String getDefaultLocale() {
		return _journalArticle.getDefaultLocale();
	}

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getSmallImageType();
	}

	public boolean isTemplateDriven() {
		return _journalArticle.isTemplateDriven();
	}

	public void setSmallImageType(java.lang.String smallImageType) {
		_journalArticle.setSmallImageType(smallImageType);
	}

	public JournalArticle getWrappedJournalArticle() {
		return _journalArticle;
	}

	private JournalArticle _journalArticle;
}