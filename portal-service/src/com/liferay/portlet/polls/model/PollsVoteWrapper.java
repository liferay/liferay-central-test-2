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
 * This class is a wrapper for {@link PollsVote}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVote
 * @generated
 */
public class PollsVoteWrapper implements PollsVote {
	public PollsVoteWrapper(PollsVote pollsVote) {
		_pollsVote = pollsVote;
	}

	public long getPrimaryKey() {
		return _pollsVote.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_pollsVote.setPrimaryKey(pk);
	}

	public long getVoteId() {
		return _pollsVote.getVoteId();
	}

	public void setVoteId(long voteId) {
		_pollsVote.setVoteId(voteId);
	}

	public long getUserId() {
		return _pollsVote.getUserId();
	}

	public void setUserId(long userId) {
		_pollsVote.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsVote.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_pollsVote.setUserUuid(userUuid);
	}

	public long getQuestionId() {
		return _pollsVote.getQuestionId();
	}

	public void setQuestionId(long questionId) {
		_pollsVote.setQuestionId(questionId);
	}

	public long getChoiceId() {
		return _pollsVote.getChoiceId();
	}

	public void setChoiceId(long choiceId) {
		_pollsVote.setChoiceId(choiceId);
	}

	public java.util.Date getVoteDate() {
		return _pollsVote.getVoteDate();
	}

	public void setVoteDate(java.util.Date voteDate) {
		_pollsVote.setVoteDate(voteDate);
	}

	public com.liferay.portlet.polls.model.PollsVote toEscapedModel() {
		return _pollsVote.toEscapedModel();
	}

	public boolean isNew() {
		return _pollsVote.isNew();
	}

	public void setNew(boolean n) {
		_pollsVote.setNew(n);
	}

	public boolean isCachedModel() {
		return _pollsVote.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pollsVote.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pollsVote.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_pollsVote.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsVote.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsVote.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsVote.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _pollsVote.clone();
	}

	public int compareTo(com.liferay.portlet.polls.model.PollsVote pollsVote) {
		return _pollsVote.compareTo(pollsVote);
	}

	public int hashCode() {
		return _pollsVote.hashCode();
	}

	public java.lang.String toString() {
		return _pollsVote.toString();
	}

	public java.lang.String toXmlString() {
		return _pollsVote.toXmlString();
	}

	public com.liferay.portlet.polls.model.PollsChoice getChoice()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsVote.getChoice();
	}

	public PollsVote getWrappedPollsVote() {
		return _pollsVote;
	}

	private PollsVote _pollsVote;
}