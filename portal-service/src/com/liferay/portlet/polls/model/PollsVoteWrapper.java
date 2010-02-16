/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.polls.model;


/**
 * <a href="PollsVoteSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _pollsVote.setNew(n);
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