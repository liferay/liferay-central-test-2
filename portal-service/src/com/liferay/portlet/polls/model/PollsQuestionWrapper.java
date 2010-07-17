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

	public long getPrimaryKey() {
		return _pollsQuestion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_pollsQuestion.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _pollsQuestion.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_pollsQuestion.setUuid(uuid);
	}

	public long getQuestionId() {
		return _pollsQuestion.getQuestionId();
	}

	public void setQuestionId(long questionId) {
		_pollsQuestion.setQuestionId(questionId);
	}

	public long getGroupId() {
		return _pollsQuestion.getGroupId();
	}

	public void setGroupId(long groupId) {
		_pollsQuestion.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _pollsQuestion.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_pollsQuestion.setCompanyId(companyId);
	}

	public long getUserId() {
		return _pollsQuestion.getUserId();
	}

	public void setUserId(long userId) {
		_pollsQuestion.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestion.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_pollsQuestion.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _pollsQuestion.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_pollsQuestion.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _pollsQuestion.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_pollsQuestion.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _pollsQuestion.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_pollsQuestion.setModifiedDate(modifiedDate);
	}

	public java.lang.String getTitle() {
		return _pollsQuestion.getTitle();
	}

	public java.lang.String getTitle(java.util.Locale locale) {
		return _pollsQuestion.getTitle(locale);
	}

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _pollsQuestion.getTitle(locale, useDefault);
	}

	public java.lang.String getTitle(java.lang.String languageId) {
		return _pollsQuestion.getTitle(languageId);
	}

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getTitle(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _pollsQuestion.getTitleMap();
	}

	public void setTitle(java.lang.String title) {
		_pollsQuestion.setTitle(title);
	}

	public void setTitle(java.util.Locale locale, java.lang.String title) {
		_pollsQuestion.setTitle(locale, title);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_pollsQuestion.setTitleMap(titleMap);
	}

	public java.lang.String getDescription() {
		return _pollsQuestion.getDescription();
	}

	public java.lang.String getDescription(java.util.Locale locale) {
		return _pollsQuestion.getDescription(locale);
	}

	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _pollsQuestion.getDescription(locale, useDefault);
	}

	public java.lang.String getDescription(java.lang.String languageId) {
		return _pollsQuestion.getDescription(languageId);
	}

	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getDescription(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _pollsQuestion.getDescriptionMap();
	}

	public void setDescription(java.lang.String description) {
		_pollsQuestion.setDescription(description);
	}

	public void setDescription(java.util.Locale locale,
		java.lang.String description) {
		_pollsQuestion.setDescription(locale, description);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_pollsQuestion.setDescriptionMap(descriptionMap);
	}

	public java.util.Date getExpirationDate() {
		return _pollsQuestion.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_pollsQuestion.setExpirationDate(expirationDate);
	}

	public java.util.Date getLastVoteDate() {
		return _pollsQuestion.getLastVoteDate();
	}

	public void setLastVoteDate(java.util.Date lastVoteDate) {
		_pollsQuestion.setLastVoteDate(lastVoteDate);
	}

	public com.liferay.portlet.polls.model.PollsQuestion toEscapedModel() {
		return _pollsQuestion.toEscapedModel();
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
		return _pollsQuestion.clone();
	}

	public int compareTo(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion) {
		return _pollsQuestion.compareTo(pollsQuestion);
	}

	public int hashCode() {
		return _pollsQuestion.hashCode();
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

	private PollsQuestion _pollsQuestion;
}