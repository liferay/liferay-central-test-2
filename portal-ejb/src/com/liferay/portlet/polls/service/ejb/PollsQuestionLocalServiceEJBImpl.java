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

import com.liferay.portlet.polls.service.PollsQuestionLocalService;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PollsQuestionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsQuestionLocalServiceEJBImpl
	implements PollsQuestionLocalService, SessionBean {
	public com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.util.List choices,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().addQuestion(userId,
			plid, title, description, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.util.List choices,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().addQuestion(userId,
			plid, title, description, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.util.List choices,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().addQuestion(userId,
			plid, title, description, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public void addQuestionResources(java.lang.String questionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().addQuestionResources(questionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().addQuestionResources(question,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(java.lang.String questionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().addQuestionResources(questionId,
			communityPermissions, guestPermissions);
	}

	public void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().addQuestionResources(question,
			communityPermissions, guestPermissions);
	}

	public void deleteQuestion(java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().deleteQuestion(questionId);
	}

	public void deleteQuestion(
		com.liferay.portlet.polls.model.PollsQuestion question)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().deleteQuestion(question);
	}

	public void deleteQuestions(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PollsQuestionLocalServiceFactory.getTxImpl().deleteQuestions(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion getQuestion(
		java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().getQuestion(questionId);
	}

	public java.util.List getQuestions(long groupId)
		throws com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().getQuestions(groupId);
	}

	public java.util.List getQuestions(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().getQuestions(groupId,
			begin, end);
	}

	public int getQuestionsCount(long groupId)
		throws com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().getQuestionsCount(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		java.lang.String userId, java.lang.String questionId,
		java.lang.String title, java.lang.String description,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List choices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PollsQuestionLocalServiceFactory.getTxImpl().updateQuestion(userId,
			questionId, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, choices);
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