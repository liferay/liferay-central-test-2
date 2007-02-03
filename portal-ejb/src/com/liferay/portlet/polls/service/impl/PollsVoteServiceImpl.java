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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.polls.QuestionExpiredException;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteService;
import com.liferay.portlet.polls.service.permission.PollsQuestionPermission;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

/**
 * <a href="PollsVoteServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsVoteServiceImpl extends PrincipalBean
	implements PollsVoteService {

	public PollsVote addVote(String questionId, String choiceId)
		throws PortalException, SystemException {

		String userId = null;

		try {
			userId = getUserId();
		}
		catch (PrincipalException pe) {
			userId = String.valueOf(CounterLocalServiceUtil.increment(
				PollsQuestion.class.getName() + ".anonymous"));
		}

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		if (question.isExpired()) {
			throw new QuestionExpiredException();
		}

		PollsQuestionPermission.check(
			getPermissionChecker(), question, ActionKeys.ADD_VOTE);

		return PollsVoteLocalServiceUtil.addVote(userId, questionId, choiceId);
	}

}