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
 * This class is a wrapper for {@link PollsChoice}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoice
 * @generated
 */
public class PollsChoiceWrapper implements PollsChoice {
	public PollsChoiceWrapper(PollsChoice pollsChoice) {
		_pollsChoice = pollsChoice;
	}

	public long getPrimaryKey() {
		return _pollsChoice.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_pollsChoice.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _pollsChoice.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_pollsChoice.setUuid(uuid);
	}

	public long getChoiceId() {
		return _pollsChoice.getChoiceId();
	}

	public void setChoiceId(long choiceId) {
		_pollsChoice.setChoiceId(choiceId);
	}

	public long getQuestionId() {
		return _pollsChoice.getQuestionId();
	}

	public void setQuestionId(long questionId) {
		_pollsChoice.setQuestionId(questionId);
	}

	public java.lang.String getName() {
		return _pollsChoice.getName();
	}

	public void setName(java.lang.String name) {
		_pollsChoice.setName(name);
	}

	public java.lang.String getDescription() {
		return _pollsChoice.getDescription();
	}

	public java.lang.String getDescription(java.util.Locale locale) {
		return _pollsChoice.getDescription(locale);
	}

	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _pollsChoice.getDescription(locale, useDefault);
	}

	public java.lang.String getDescription(java.lang.String languageId) {
		return _pollsChoice.getDescription(languageId);
	}

	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _pollsChoice.getDescription(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _pollsChoice.getDescriptionMap();
	}

	public void setDescription(java.lang.String description) {
		_pollsChoice.setDescription(description);
	}

	public void setDescription(java.util.Locale locale,
		java.lang.String description) {
		_pollsChoice.setDescription(locale, description);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_pollsChoice.setDescriptionMap(descriptionMap);
	}

	public com.liferay.portlet.polls.model.PollsChoice toEscapedModel() {
		return _pollsChoice.toEscapedModel();
	}

	public boolean isNew() {
		return _pollsChoice.isNew();
	}

	public void setNew(boolean n) {
		_pollsChoice.setNew(n);
	}

	public boolean isCachedModel() {
		return _pollsChoice.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pollsChoice.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pollsChoice.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_pollsChoice.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsChoice.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsChoice.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsChoice.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _pollsChoice.clone();
	}

	public int compareTo(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice) {
		return _pollsChoice.compareTo(pollsChoice);
	}

	public int hashCode() {
		return _pollsChoice.hashCode();
	}

	public java.lang.String toString() {
		return _pollsChoice.toString();
	}

	public java.lang.String toXmlString() {
		return _pollsChoice.toXmlString();
	}

	public int getVotesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoice.getVotesCount();
	}

	public PollsChoice getWrappedPollsChoice() {
		return _pollsChoice;
	}

	private PollsChoice _pollsChoice;
}