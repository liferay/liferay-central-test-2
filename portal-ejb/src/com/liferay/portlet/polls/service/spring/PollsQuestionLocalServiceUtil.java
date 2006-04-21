/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.polls.service.spring;

/**
 * <a href="PollsQuestionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsQuestionLocalServiceUtil {
	public static com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.util.List choices,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.addQuestion(userId, plid, title,
				description, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, choices, addCommunityPermissions,
				addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addQuestionResources(java.lang.String questionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.addQuestionResources(questionId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.addQuestionResources(question,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void checkQuestions()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.checkQuestions();
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteQuestion(java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.deleteQuestion(questionId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteQuestion(
		com.liferay.portlet.polls.model.PollsQuestion question)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.deleteQuestion(question);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteQuestions(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();
			pollsQuestionLocalService.deleteQuestions(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.polls.model.PollsQuestion getQuestion(
		java.lang.String questionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.getQuestion(questionId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getQuestions(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.getQuestions(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getQuestions(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.getQuestions(groupId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getQuestionsCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.getQuestionsCount(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		java.lang.String userId, java.lang.String questionId,
		java.lang.String title, java.lang.String description,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List choices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			PollsQuestionLocalService pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getService();

			return pollsQuestionLocalService.updateQuestion(userId, questionId,
				title, description, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, choices);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}