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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PollsQuestionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PollsQuestionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestionLocalService
 * @generated
 */
public class PollsQuestionLocalServiceUtil {
	public static com.liferay.portlet.polls.model.PollsQuestion addPollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPollsQuestion(pollsQuestion);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion createPollsQuestion(
		long questionId) {
		return getService().createPollsQuestion(questionId);
	}

	public static void deletePollsQuestion(long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePollsQuestion(questionId);
	}

	public static void deletePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePollsQuestion(pollsQuestion);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion getPollsQuestion(
		long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsQuestion(questionId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getPollsQuestions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsQuestions(start, end);
	}

	public static int getPollsQuestionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsQuestionsCount();
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updatePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePollsQuestion(pollsQuestion);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updatePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePollsQuestion(pollsQuestion, merge);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String uuid, long userId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> choices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addQuestion(uuid, userId, titleMap, descriptionMap,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, choices,
			serviceContext);
	}

	public static void addQuestionResources(long questionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addQuestionResources(questionId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addQuestionResources(long questionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addQuestionResources(questionId, communityPermissions,
			guestPermissions);
	}

	public static void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addQuestionResources(question, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addQuestionResources(question, communityPermissions,
			guestPermissions);
	}

	public static void deleteQuestion(long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteQuestion(questionId);
	}

	public static void deleteQuestion(
		com.liferay.portlet.polls.model.PollsQuestion question)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteQuestion(question);
	}

	public static void deleteQuestions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteQuestions(groupId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion getQuestion(
		long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestion(questionId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getQuestions(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestions(groupId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getQuestions(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestions(groupId, start, end);
	}

	public static int getQuestionsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestionsCount(groupId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		long userId, long questionId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateQuestion(userId, questionId, titleMap,
			descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		long userId, long questionId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> choices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateQuestion(userId, questionId, titleMap,
			descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, serviceContext);
	}

	public static PollsQuestionLocalService getService() {
		if (_service == null) {
			_service = (PollsQuestionLocalService)PortalBeanLocatorUtil.locate(PollsQuestionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PollsQuestionLocalService service) {
		_service = service;
	}

	private static PollsQuestionLocalService _service;
}