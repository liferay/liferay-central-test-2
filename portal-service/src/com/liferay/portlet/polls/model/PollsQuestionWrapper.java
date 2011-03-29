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

package com.liferay.portlet.polls.model;

/**
 * <p>
 * This class is a wrapper for {@link PollsQuestion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestion
 * @generated
 */
public class PollsQuestionWrapper implements PollsQuestion {
	public PollsQuestionWrapper(PollsQuestion pollsQuestion) {
		_pollsQuestion = pollsQuestion;
	}

	public Class<?> getModelClass() {
		return PollsQuestion.class;
	}

	public String getModelClassName() {
		return PollsQuestion.class.getName();
	}

	/**
	* Gets the primary key of this polls question.
	*
	* @return the primary key of this polls question
	*/
	public long getPrimaryKey() {
		return _pollsQuestion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this polls question
	*
	* @param pk the primary key of this polls question
	*/
	public void setPrimaryKey(long pk) {
		_pollsQuestion.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this polls question.
	*
	* @return the uuid of this polls question
	*/
	public java.lang.String getUuid() {
		return _pollsQuestion.getUuid();
	}

	/**
	* Sets the uuid of this polls question.
	*
	* @param uuid the uuid of this polls question
	*/
	public void setUuid(java.lang.String uuid) {
		_pollsQuestion.setUuid(uuid);
	}

	/**
	* Gets the question ID of this polls question.
	*
	* @return the question ID of this polls question
	*/
	public long getQuestionId() {
		return _pollsQuestion.getQuestionId();
	}

	/**
	* Sets the question ID of this polls question.
	*
	* @param questionId the question ID of this polls question
	*/
	public void setQuestionId(long questionId) {
		_pollsQuestion.setQuestionId(questionId);
	}

	/**
	* Gets the group ID of this polls question.
	*
	* @return the group ID of this polls question
	*/
	public long getGroupId() {
		return _pollsQuestion.getGroupId();
	}

	/**
	* Sets the group ID of this polls question.
	*
	* @param groupId the group ID of this polls question
	*/
	public void setGroupId(long groupId) {
		_pollsQuestion.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this polls question.
	*
	* @return the company ID of this polls question
	*/
	public long getCompanyId() {
		return _pollsQuestion.getCompanyId();
	}

	/**
	* Sets the company ID of this polls question.
	*
	* @param companyId the company ID of this polls question
	*/
	public void setCompanyId(long companyId) {
		_pollsQuestion.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this polls question.
	*
	* @return the user ID of this polls question
	*/
	public long getUserId() {
		return _pollsQuestion.getUserId();
	}

	/**
	* Sets the user ID of this polls question.
	*
	* @param userId the user ID of this polls question
	*/
	public void setUserId(long userId) {
		_pollsQuestion.setUserId(userId);
	}

	/**
	* Gets the user uuid of this polls question.
	*
	* @return the user uuid of this polls question
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestion.getUserUuid();
	}

	/**
	* Sets the user uuid of this polls question.
	*
	* @param userUuid the user uuid of this polls question
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_pollsQuestion.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this polls question.
	*
	* @return the user name of this polls question
	*/
	public java.lang.String getUserName() {
		return _pollsQuestion.getUserName();
	}

	/**
	* Sets the user name of this polls question.
	*
	* @param userName the user name of this polls question
	*/
	public void setUserName(java.lang.String userName) {
		_pollsQuestion.setUserName(userName);
	}

	/**
	* Gets the create date of this polls question.
	*
	* @return the create date of this polls question
	*/
	public java.util.Date getCreateDate() {
		return _pollsQuestion.getCreateDate();
	}

	/**
	* Sets the create date of this polls question.
	*
	* @param createDate the create date of this polls question
	*/
	public void setCreateDate(java.util.Date createDate) {
		_pollsQuestion.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this polls question.
	*
	* @return the modified date of this polls question
	*/
	public java.util.Date getModifiedDate() {
		return _pollsQuestion.getModifiedDate();
	}

	/**
	* Sets the modified date of this polls question.
	*
	* @param modifiedDate the modified date of this polls question
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_pollsQuestion.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the title of this polls question.
	*
	* @return the title of this polls question
	*/
	public java.lang.String getTitle() {
		return _pollsQuestion.getTitle();
	}

	/**
	* Gets the localized title of this polls question. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized title for
	* @return the localized title of this polls question
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _pollsQuestion.getTitle(locale);
	}

	/**
	* Gets the localized title of this polls question, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this polls question. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _pollsQuestion.getTitle(locale, useDefault);
	}

	/**
	* Gets the localized title of this polls question. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @return the localized title of this polls question
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _pollsQuestion.getTitle(languageId);
	}

	/**
	* Gets the localized title of this polls question, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this polls question
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getTitle(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized title of this polls question.
	*
	* @return the locales and localized title
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _pollsQuestion.getTitleMap();
	}

	/**
	* Sets the title of this polls question.
	*
	* @param title the title of this polls question
	*/
	public void setTitle(java.lang.String title) {
		_pollsQuestion.setTitle(title);
	}

	/**
	* Sets the localized title of this polls question.
	*
	* @param title the localized title of this polls question
	* @param locale the locale to set the localized title for
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_pollsQuestion.setTitle(title, locale);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setTitle(title, locale, defaultLocale);
	}

	/**
	* Sets the localized titles of this polls question from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this polls question
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_pollsQuestion.setTitleMap(titleMap);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Gets the description of this polls question.
	*
	* @return the description of this polls question
	*/
	public java.lang.String getDescription() {
		return _pollsQuestion.getDescription();
	}

	/**
	* Gets the localized description of this polls question. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized description for
	* @return the localized description of this polls question
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _pollsQuestion.getDescription(locale);
	}

	/**
	* Gets the localized description of this polls question, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this polls question. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _pollsQuestion.getDescription(locale, useDefault);
	}

	/**
	* Gets the localized description of this polls question. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @return the localized description of this polls question
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _pollsQuestion.getDescription(languageId);
	}

	/**
	* Gets the localized description of this polls question, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this polls question
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getDescription(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized description of this polls question.
	*
	* @return the locales and localized description
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _pollsQuestion.getDescriptionMap();
	}

	/**
	* Sets the description of this polls question.
	*
	* @param description the description of this polls question
	*/
	public void setDescription(java.lang.String description) {
		_pollsQuestion.setDescription(description);
	}

	/**
	* Sets the localized description of this polls question.
	*
	* @param description the localized description of this polls question
	* @param locale the locale to set the localized description for
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_pollsQuestion.setDescription(description, locale);
	}

	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_pollsQuestion.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this polls question from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this polls question
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_pollsQuestion.setDescriptionMap(descriptionMap);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Gets the expiration date of this polls question.
	*
	* @return the expiration date of this polls question
	*/
	public java.util.Date getExpirationDate() {
		return _pollsQuestion.getExpirationDate();
	}

	/**
	* Sets the expiration date of this polls question.
	*
	* @param expirationDate the expiration date of this polls question
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_pollsQuestion.setExpirationDate(expirationDate);
	}

	/**
	* Gets the last vote date of this polls question.
	*
	* @return the last vote date of this polls question
	*/
	public java.util.Date getLastVoteDate() {
		return _pollsQuestion.getLastVoteDate();
	}

	/**
	* Sets the last vote date of this polls question.
	*
	* @param lastVoteDate the last vote date of this polls question
	*/
	public void setLastVoteDate(java.util.Date lastVoteDate) {
		_pollsQuestion.setLastVoteDate(lastVoteDate);
	}

	public boolean isNew() {
		return _pollsQuestion.isNew();
	}

	public void setNew(boolean n) {
		_pollsQuestion.setNew(n);
	}

	public boolean isCachedModel() {
		return _pollsQuestion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pollsQuestion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pollsQuestion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_pollsQuestion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsQuestion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsQuestion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsQuestion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new PollsQuestionWrapper((PollsQuestion)_pollsQuestion.clone());
	}

	public int compareTo(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion) {
		return _pollsQuestion.compareTo(pollsQuestion);
	}

	public int hashCode() {
		return _pollsQuestion.hashCode();
	}

	public com.liferay.portlet.polls.model.PollsQuestion toEscapedModel() {
		return new PollsQuestionWrapper(_pollsQuestion.toEscapedModel());
	}

	public java.lang.String toString() {
		return _pollsQuestion.toString();
	}

	public java.lang.String toXmlString() {
		return _pollsQuestion.toXmlString();
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getChoices()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestion.getChoices();
	}

	public int getVotesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestion.getVotesCount();
	}

	public boolean isExpired() {
		return _pollsQuestion.isExpired();
	}

	public PollsQuestion getWrappedPollsQuestion() {
		return _pollsQuestion;
	}

	public void resetOriginalValues() {
		_pollsQuestion.resetOriginalValues();
	}

	private PollsQuestion _pollsQuestion;
}