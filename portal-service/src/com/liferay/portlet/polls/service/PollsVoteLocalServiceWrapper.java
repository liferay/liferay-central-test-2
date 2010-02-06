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

package com.liferay.portlet.polls.service;


/**
 * <a href="PollsVoteLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PollsVoteLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVoteLocalService
 * @generated
 */
public class PollsVoteLocalServiceWrapper implements PollsVoteLocalService {
	public PollsVoteLocalServiceWrapper(
		PollsVoteLocalService pollsVoteLocalService) {
		_pollsVoteLocalService = pollsVoteLocalService;
	}

	public com.liferay.portlet.polls.model.PollsVote addPollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.addPollsVote(pollsVote);
	}

	public com.liferay.portlet.polls.model.PollsVote createPollsVote(
		long voteId) {
		return _pollsVoteLocalService.createPollsVote(voteId);
	}

	public void deletePollsVote(long voteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_pollsVoteLocalService.deletePollsVote(voteId);
	}

	public void deletePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		_pollsVoteLocalService.deletePollsVote(pollsVote);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.polls.model.PollsVote getPollsVote(long voteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getPollsVote(voteId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> getPollsVotes(
		int start, int end) throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getPollsVotes(start, end);
	}

	public int getPollsVotesCount() throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getPollsVotesCount();
	}

	public com.liferay.portlet.polls.model.PollsVote updatePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.updatePollsVote(pollsVote);
	}

	public com.liferay.portlet.polls.model.PollsVote updatePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.updatePollsVote(pollsVote, merge);
	}

	public com.liferay.portlet.polls.model.PollsVote addVote(long userId,
		long questionId, long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsVoteLocalService.addVote(userId, questionId, choiceId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> getChoiceVotes(
		long choiceId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getChoiceVotes(choiceId, start, end);
	}

	public int getChoiceVotesCount(long choiceId)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getChoiceVotesCount(choiceId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> getQuestionVotes(
		long questionId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getQuestionVotes(questionId, start, end);
	}

	public int getQuestionVotesCount(long questionId)
		throws com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getQuestionVotesCount(questionId);
	}

	public com.liferay.portlet.polls.model.PollsVote getVote(long questionId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsVoteLocalService.getVote(questionId, userId);
	}

	public PollsVoteLocalService getWrappedPollsVoteLocalService() {
		return _pollsVoteLocalService;
	}

	private PollsVoteLocalService _pollsVoteLocalService;
}