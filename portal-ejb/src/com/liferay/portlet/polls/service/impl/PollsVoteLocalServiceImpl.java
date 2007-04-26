/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.polls.DuplicateVoteException;
import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.base.PollsVoteLocalServiceBaseImpl;
import com.liferay.portlet.polls.service.persistence.PollsChoicePK;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVotePK;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="PollsVoteLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsVoteLocalServiceImpl extends PollsVoteLocalServiceBaseImpl {

	public PollsVote addVote(long userId, String questionId, String choiceId)
		throws PortalException, SystemException {

		// Question

		Date now = new Date();

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		question.setLastVoteDate(now);

		PollsQuestionUtil.update(question);

		// Vote

		PollsVotePK votePK = new PollsVotePK(questionId, userId);

		PollsVote vote = null;

		try {
			vote = PollsVoteUtil.findByPrimaryKey(votePK);

			throw new DuplicateVoteException();
		}
		catch (NoSuchVoteException nsve) {
			vote = PollsVoteUtil.create(votePK);

			PollsChoicePK choicePK =
				new PollsChoicePK(questionId, choiceId);

			PollsChoiceUtil.findByPrimaryKey(choicePK);

			vote.setChoiceId(choiceId);
			vote.setVoteDate(now);

			PollsVoteUtil.update(vote);
		}

		return vote;
	}

	public PollsVote getVote(String questionId, long userId)
		throws PortalException, SystemException {

		PollsVotePK pk = new PollsVotePK(questionId, userId);

		return PollsVoteUtil.findByPrimaryKey(pk);
	}

	public List getVotes(String questionId, int begin, int end)
		throws SystemException {

		return PollsVoteUtil.findByQuestionId(questionId, begin, end);
	}

	public List getVotes(String questionId, String choiceId, int begin, int end)
		throws SystemException {

		return PollsVoteUtil.findByQ_C(questionId, choiceId,  begin, end);
	}

	public int getVotesCount(String questionId) throws SystemException {
		return PollsVoteUtil.countByQuestionId(questionId);
	}

	public int getVotesCount(String questionId, String choiceId)
		throws SystemException {

		return PollsVoteUtil.countByQ_C(questionId, choiceId);
	}

}