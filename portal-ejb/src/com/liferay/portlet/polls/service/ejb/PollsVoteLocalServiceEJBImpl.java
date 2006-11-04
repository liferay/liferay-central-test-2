/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.polls.service.spring.PollsVoteLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PollsVoteLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsVoteLocalServiceEJBImpl implements PollsVoteLocalService,
	SessionBean {
	public static final String CLASS_NAME = PollsVoteLocalService.class.getName() +
		".transaction";

	public static PollsVoteLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PollsVoteLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.polls.model.PollsVote addVote(
		java.lang.String userId, java.lang.String questionId,
		java.lang.String choiceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addVote(userId, questionId, choiceId);
	}

	public com.liferay.portlet.polls.model.PollsVote getVote(
		java.lang.String questionId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getVote(questionId, userId);
	}

	public int getVotesCount(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		return getService().getVotesCount(questionId);
	}

	public int getVotesCount(java.lang.String questionId,
		java.lang.String choiceId) throws com.liferay.portal.SystemException {
		return getService().getVotesCount(questionId, choiceId);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}