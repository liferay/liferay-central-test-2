/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	public Class<?> getModelClass() {
		return JournalArticle.class;
	}

	public String getModelClassName() {
		return JournalArticle.class.getName();
	}

	/**
	* Gets the primary key of this journal article.
	*
	* @return the primary key of this journal article
	*/
	public long getPrimaryKey() {
		return _journalArticle.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal article
	*
	* @param pk the primary key of this journal article
	*/
	public void setPrimaryKey(long pk) {
		_journalArticle.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this journal article.
	*
	* @return the uuid of this journal article
	*/
	public java.lang.String getUuid() {
		return _journalArticle.getUuid();
	}

	/**
	* Sets the uuid of this journal article.
	*
	* @param uuid the uuid of this journal article
	*/
	public void setUuid(java.lang.String uuid) {
		_journalArticle.setUuid(uuid);
	}

	/**
	* Gets the ID of this journal article.
	*
	* @return the ID of this journal article
	*/
	public long getId() {
		return _journalArticle.getId();
	}

	/**
	* Sets the ID of this journal article.
	*
	* @param id the ID of this journal article
	*/
	public void setId(long id) {
		_journalArticle.setId(id);
	}

	/**
	* Gets the resource prim key of this journal article.
	*
	* @return the resource prim key of this journal article
	*/
	public long getResourcePrimKey() {
		return _journalArticle.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this journal article.
	*
	* @param resourcePrimKey the resource prim key of this journal article
	*/
	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticle.setResourcePrimKey(resourcePrimKey);
	}

	public boolean isResourceMain() {
		return _journalArticle.isResourceMain();
	}

	/**
	* Gets the group ID of this journal article.
	*
	* @return the group ID of this journal article
	*/
	public long getGroupId() {
		return _journalArticle.getGroupId();
	}

	/**
	* Sets the group ID of this journal article.
	*
	* @param groupId the group ID of this journal article
	*/
	public void setGroupId(long groupId) {
		_journalArticle.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this journal article.
	*
	* @return the company ID of this journal article
	*/
	public long getCompanyId() {
		return _journalArticle.getCompanyId();
	}

	/**
	* Sets the company ID of this journal article.
	*
	* @param companyId the company ID of this journal article
	*/
	public void setCompanyId(long companyId) {
		_journalArticle.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this journal article.
	*
	* @return the user ID of this journal article
	*/
	public long getUserId() {
		return _journalArticle.getUserId();
	}

	/**
	* Sets the user ID of this journal article.
	*
	* @param userId the user ID of this journal article
	*/
	public void setUserId(long userId) {
		_journalArticle.setUserId(userId);
	}

	/**
	* Gets the user uuid of this journal article.
	*
	* @return the user uuid of this journal article
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal article.
	*
	* @param userUuid the user uuid of this journal article
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalArticle.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this journal article.
	*
	* @return the user name of this journal article
	*/
	public java.lang.String getUserName() {
		return _journalArticle.getUserName();
	}

	/**
	* Sets the user name of this journal article.
	*
	* @param userName the user name of this journal article
	*/
	public void setUserName(java.lang.String userName) {
		_journalArticle.setUserName(userName);
	}

	/**
	* Gets the create date of this journal article.
	*
	* @return the create date of this journal article
	*/
	public java.util.Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	/**
	* Sets the create date of this journal article.
	*
	* @param createDate the create date of this journal article
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalArticle.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this journal article.
	*
	* @return the modified date of this journal article
	*/
	public java.util.Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal article.
	*
	* @param modifiedDate the modified date of this journal article
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalArticle.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the article ID of this journal article.
	*
	* @return the article ID of this journal article
	*/
	public java.lang.String getArticleId() {
		return _journalArticle.getArticleId();
	}

	/**
	* Sets the article ID of this journal article.
	*
	* @param articleId the article ID of this journal article
	*/
	public void setArticleId(java.lang.String articleId) {
		_journalArticle.setArticleId(articleId);
	}

	/**
	* Gets the version of this journal article.
	*
	* @return the version of this journal article
	*/
	public double getVersion() {
		return _journalArticle.getVersion();
	}

	/**
	* Sets the version of this journal article.
	*
	* @param version the version of this journal article
	*/
	public void setVersion(double version) {
		_journalArticle.setVersion(version);
	}

	/**
	* Gets the title of this journal article.
	*
	* @return the title of this journal article
	*/
	public java.lang.String getTitle() {
		return _journalArticle.getTitle();
	}

	/**
	* Gets the localized title of this journal article. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized title for
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _journalArticle.getTitle(locale);
	}

	/**
	* Gets the localized title of this journal article, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this journal article. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _journalArticle.getTitle(locale, useDefault);
	}

	/**
	* Gets the localized title of this journal article. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _journalArticle.getTitle(languageId);
	}

	/**
	* Gets the localized title of this journal article, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getTitle(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized title of this journal article.
	*
	* @return the locales and localized title
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _journalArticle.getTitleMap();
	}

	/**
	* Sets the title of this journal article.
	*
	* @param title the title of this journal article
	*/
	public void setTitle(java.lang.String title) {
		_journalArticle.setTitle(title);
	}

	/**
	* Sets the localized title of this journal article.
	*
	* @param title the localized title of this journal article
	* @param locale the locale to set the localized title for
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_journalArticle.setTitle(title, locale);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitle(title, locale, defaultLocale);
	}

	/**
	* Sets the localized titles of this journal article from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this journal article
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_journalArticle.setTitleMap(titleMap);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Gets the url title of this journal article.
	*
	* @return the url title of this journal article
	*/
	public java.lang.String getUrlTitle() {
		return _journalArticle.getUrlTitle();
	}

	/**
	* Sets the url title of this journal article.
	*
	* @param urlTitle the url title of this journal article
	*/
	public void setUrlTitle(java.lang.String urlTitle) {
		_journalArticle.setUrlTitle(urlTitle);
	}

	/**
	* Gets the description of this journal article.
	*
	* @return the description of this journal article
	*/
	public java.lang.String getDescription() {
		return _journalArticle.getDescription();
	}

	/**
	* Gets the localized description of this journal article. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized description for
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _journalArticle.getDescription(locale);
	}

	/**
	* Gets the localized description of this journal article, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal article. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _journalArticle.getDescription(locale, useDefault);
	}

	/**
	* Gets the localized description of this journal article. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _journalArticle.getDescription(languageId);
	}

	/**
	* Gets the localized description of this journal article, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getDescription(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized description of this journal article.
	*
	* @return the locales and localized description
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _journalArticle.getDescriptionMap();
	}

	/**
	* Sets the description of this journal article.
	*
	* @param description the description of this journal article
	*/
	public void setDescription(java.lang.String description) {
		_journalArticle.setDescription(description);
	}

	/**
	* Sets the localized description of this journal article.
	*
	* @param description the localized description of this journal article
	* @param locale the locale to set the localized description for
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_journalArticle.setDescription(description, locale);
	}

	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_journalArticle.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this journal article from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this journal article
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_journalArticle.setDescriptionMap(descriptionMap);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Gets the content of this journal article.
	*
	* @return the content of this journal article
	*/
	public java.lang.String getContent() {
		return _journalArticle.getContent();
	}

	/**
	* Sets the content of this journal article.
	*
	* @param content the content of this journal article
	*/
	public void setContent(java.lang.String content) {
		_journalArticle.setContent(content);
	}

	/**
	* Gets the type of this journal article.
	*
	* @return the type of this journal article
	*/
	public java.lang.String getType() {
		return _journalArticle.getType();
	}

	/**
	* Sets the type of this journal article.
	*
	* @param type the type of this journal article
	*/
	public void setType(java.lang.String type) {
		_journalArticle.setType(type);
	}

	/**
	* Gets the structure ID of this journal article.
	*
	* @return the structure ID of this journal article
	*/
	public java.lang.String getStructureId() {
		return _journalArticle.getStructureId();
	}

	/**
	* Sets the structure ID of this journal article.
	*
	* @param structureId the structure ID of this journal article
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalArticle.setStructureId(structureId);
	}

	/**
	* Gets the template ID of this journal article.
	*
	* @return the template ID of this journal article
	*/
	public java.lang.String getTemplateId() {
		return _journalArticle.getTemplateId();
	}

	/**
	* Sets the template ID of this journal article.
	*
	* @param templateId the template ID of this journal article
	*/
	public void setTemplateId(java.lang.String templateId) {
		_journalArticle.setTemplateId(templateId);
	}

	/**
	* Gets the layout uuid of this journal article.
	*
	* @return the layout uuid of this journal article
	*/
	public java.lang.String getLayoutUuid() {
		return _journalArticle.getLayoutUuid();
	}

	/**
	* Sets the layout uuid of this journal article.
	*
	* @param layoutUuid the layout uuid of this journal article
	*/
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_journalArticle.setLayoutUuid(layoutUuid);
	}

	/**
	* Gets the display date of this journal article.
	*
	* @return the display date of this journal article
	*/
	public java.util.Date getDisplayDate() {
		return _journalArticle.getDisplayDate();
	}

	/**
	* Sets the display date of this journal article.
	*
	* @param displayDate the display date of this journal article
	*/
	public void setDisplayDate(java.util.Date displayDate) {
		_journalArticle.setDisplayDate(displayDate);
	}

	/**
	* Gets the expiration date of this journal article.
	*
	* @return the expiration date of this journal article
	*/
	public java.util.Date getExpirationDate() {
		return _journalArticle.getExpirationDate();
	}

	/**
	* Sets the expiration date of this journal article.
	*
	* @param expirationDate the expiration date of this journal article
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_journalArticle.setExpirationDate(expirationDate);
	}

	/**
	* Gets the review date of this journal article.
	*
	* @return the review date of this journal article
	*/
	public java.util.Date getReviewDate() {
		return _journalArticle.getReviewDate();
	}

	/**
	* Sets the review date of this journal article.
	*
	* @param reviewDate the review date of this journal article
	*/
	public void setReviewDate(java.util.Date reviewDate) {
		_journalArticle.setReviewDate(reviewDate);
	}

	/**
	* Gets the indexable of this journal article.
	*
	* @return the indexable of this journal article
	*/
	public boolean getIndexable() {
		return _journalArticle.getIndexable();
	}

	/**
	* Determines if this journal article is indexable.
	*
	* @return <code>true</code> if this journal article is indexable; <code>false</code> otherwise
	*/
	public boolean isIndexable() {
		return _journalArticle.isIndexable();
	}

	/**
	* Sets whether this journal article is indexable.
	*
	* @param indexable the indexable of this journal article
	*/
	public void setIndexable(boolean indexable) {
		_journalArticle.setIndexable(indexable);
	}

	/**
	* Gets the small image of this journal article.
	*
	* @return the small image of this journal article
	*/
	public boolean getSmallImage() {
		return _journalArticle.getSmallImage();
	}

	/**
	* Determines if this journal article is small image.
	*
	* @return <code>true</code> if this journal article is small image; <code>false</code> otherwise
	*/
	public boolean isSmallImage() {
		return _journalArticle.isSmallImage();
	}

	/**
	* Sets whether this journal article is small image.
	*
	* @param smallImage the small image of this journal article
	*/
	public void setSmallImage(boolean smallImage) {
		_journalArticle.setSmallImage(smallImage);
	}

	/**
	* Gets the small image ID of this journal article.
	*
	* @return the small image ID of this journal article
	*/
	public long getSmallImageId() {
		return _journalArticle.getSmallImageId();
	}

	/**
	* Sets the small image ID of this journal article.
	*
	* @param smallImageId the small image ID of this journal article
	*/
	public void setSmallImageId(long smallImageId) {
		_journalArticle.setSmallImageId(smallImageId);
	}

	/**
	* Gets the small image u r l of this journal article.
	*
	* @return the small image u r l of this journal article
	*/
	public java.lang.String getSmallImageURL() {
		return _journalArticle.getSmallImageURL();
	}

	/**
	* Sets the small image u r l of this journal article.
	*
	* @param smallImageURL the small image u r l of this journal article
	*/
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalArticle.setSmallImageURL(smallImageURL);
	}

	/**
	* Gets the status of this journal article.
	*
	* @return the status of this journal article
	*/
	public int getStatus() {
		return _journalArticle.getStatus();
	}

	/**
	* Sets the status of this journal article.
	*
	* @param status the status of this journal article
	*/
	public void setStatus(int status) {
		_journalArticle.setStatus(status);
	}

	/**
	* Gets the status by user ID of this journal article.
	*
	* @return the status by user ID of this journal article
	*/
	public long getStatusByUserId() {
		return _journalArticle.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this journal article.
	*
	* @param statusByUserId the status by user ID of this journal article
	*/
	public void setStatusByUserId(long statusByUserId) {
		_journalArticle.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this journal article.
	*
	* @return the status by user uuid of this journal article
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this journal article.
	*
	* @param statusByUserUuid the status by user uuid of this journal article
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_journalArticle.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this journal article.
	*
	* @return the status by user name of this journal article
	*/
	public java.lang.String getStatusByUserName() {
		return _journalArticle.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this journal article.
	*
	* @param statusByUserName the status by user name of this journal article
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_journalArticle.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this journal article.
	*
	* @return the status date of this journal article
	*/
	public java.util.Date getStatusDate() {
		return _journalArticle.getStatusDate();
	}

	/**
	* Sets the status date of this journal article.
	*
	* @param statusDate the status date of this journal article
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_journalArticle.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _journalArticle.getApproved();
	}

	/**
	* Determines if this journal article is approved.
	*
	* @return <code>true</code> if this journal article is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _journalArticle.isApproved();
	}

	/**
	* Determines if this journal article is a draft.
	*
	* @return <code>true</code> if this journal article is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _journalArticle.isDraft();
	}

	/**
	* Determines if this journal article is expired.
	*
	* @return <code>true</code> if this journal article is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _journalArticle.isExpired();
	}

	/**
	* Determines if this journal article is pending.
	*
	* @return <code>true</code> if this journal article is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _journalArticle.isPending();
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
		return new JournalArticleWrapper((JournalArticle)_journalArticle.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		return _journalArticle.compareTo(journalArticle);
	}

	public int hashCode() {
		return _journalArticle.hashCode();
	}

	public com.liferay.portlet.journal.model.JournalArticle toEscapedModel() {
		return new JournalArticleWrapper(_journalArticle.toEscapedModel());
	}

	public java.lang.String toString() {
		return _journalArticle.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticle.toXmlString();
	}

	public com.liferay.portlet.journal.model.JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getArticleResource();
	}

	public java.lang.String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getArticleResourceUuid();
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