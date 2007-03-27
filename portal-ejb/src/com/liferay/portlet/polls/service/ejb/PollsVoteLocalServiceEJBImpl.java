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

package com.liferay.portlet.polls.service.ejb;

import com.liferay.portlet.polls.service.PollsVoteLocalService;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PollsVoteLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.polls.service.PollsVoteLocalService
 * @see com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil
 * @see com.liferay.portlet.polls.service.ejb.PollsVoteLocalServiceEJB
 * @see com.liferay.portlet.polls.service.ejb.PollsVoteLocalServiceHome
 * @see com.liferay.portlet.polls.service.impl.PollsVoteLocalServiceImpl
 *
 */
public class PollsVoteLocalServiceEJBImpl implements PollsVoteLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.polls.model.PollsVote addVote(
		java.lang.String userId, java.lang.String questionId,
		java.lang.String choiceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().addVote(userId,
			questionId, choiceId);
	}

	public com.liferay.portlet.polls.model.PollsVote getVote(
		java.lang.String questionId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().getVote(questionId,
			userId);
	}

	public java.util.List getVotes(java.lang.String questionId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().getVotes(questionId,
			begin, end);
	}

	public java.util.List getVotes(java.lang.String questionId,
		java.lang.String choiceId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().getVotes(questionId,
			choiceId, begin, end);
	}

	public int getVotesCount(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().getVotesCount(questionId);
	}

	public int getVotesCount(java.lang.String questionId,
		java.lang.String choiceId) throws com.liferay.portal.SystemException {
		return PollsVoteLocalServiceFactory.getTxImpl().getVotesCount(questionId,
			choiceId);
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