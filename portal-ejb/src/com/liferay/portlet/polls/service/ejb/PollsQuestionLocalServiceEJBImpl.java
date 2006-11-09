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

import com.liferay.portlet.polls.service.spring.PollsQuestionLocalService;

import org.springframework.context.ApplicationContext;

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
	public static final String CLASS_NAME = PollsQuestionLocalService.class.getName() +
		".transaction";

	public static PollsQuestionLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PollsQuestionLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.util.List choices,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addQuestion(userId, plid, title, description,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, choices,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(java.lang.String questionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addQuestionResources(questionId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addQuestionResources(question, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteQuestion(java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteQuestion(questionId);
	}

	public void deleteQuestion(
		com.liferay.portlet.polls.model.PollsQuestion question)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteQuestion(question);
	}

	public void deleteQuestions(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteQuestions(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion getQuestion(
		java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getQuestion(questionId);
	}

	public java.util.List getQuestions(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getQuestions(groupId);
	}

	public java.util.List getQuestions(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getQuestions(groupId, begin, end);
	}

	public int getQuestionsCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getQuestionsCount(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		java.lang.String userId, java.lang.String questionId,
		java.lang.String title, java.lang.String description,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List choices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateQuestion(userId, questionId, title,
			description, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices);
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