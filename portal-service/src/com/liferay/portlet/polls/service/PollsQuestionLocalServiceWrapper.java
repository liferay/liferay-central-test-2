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
 * <a href="PollsQuestionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PollsQuestionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestionLocalService
 * @generated
 */
public class PollsQuestionLocalServiceWrapper
	implements PollsQuestionLocalService {
	public PollsQuestionLocalServiceWrapper(
		PollsQuestionLocalService pollsQuestionLocalService) {
		_pollsQuestionLocalService = pollsQuestionLocalService;
	}

	public com.liferay.portlet.polls.model.PollsQuestion addPollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.addPollsQuestion(pollsQuestion);
	}

	public com.liferay.portlet.polls.model.PollsQuestion createPollsQuestion(
		long questionId) {
		return _pollsQuestionLocalService.createPollsQuestion(questionId);
	}

	public void deletePollsQuestion(long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.deletePollsQuestion(questionId);
	}

	public void deletePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.deletePollsQuestion(pollsQuestion);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.polls.model.PollsQuestion getPollsQuestion(
		long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getPollsQuestion(questionId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getPollsQuestions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getPollsQuestions(start, end);
	}

	public int getPollsQuestionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getPollsQuestionsCount();
	}

	public com.liferay.portlet.polls.model.PollsQuestion updatePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.updatePollsQuestion(pollsQuestion);
	}

	public com.liferay.portlet.polls.model.PollsQuestion updatePollsQuestion(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.updatePollsQuestion(pollsQuestion,
			merge);
	}

	public com.liferay.portlet.polls.model.PollsQuestion addQuestion(
		java.lang.String uuid, long userId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> choices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.addQuestion(uuid, userId, titleMap,
			descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, serviceContext);
	}

	public void addQuestionResources(long questionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.addQuestionResources(questionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(long questionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.addQuestionResources(questionId,
			communityPermissions, guestPermissions);
	}

	public void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.addQuestionResources(question,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(
		com.liferay.portlet.polls.model.PollsQuestion question,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.addQuestionResources(question,
			communityPermissions, guestPermissions);
	}

	public void deleteQuestion(long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.deleteQuestion(questionId);
	}

	public void deleteQuestion(
		com.liferay.portlet.polls.model.PollsQuestion question)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.deleteQuestion(question);
	}

	public void deleteQuestions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsQuestionLocalService.deleteQuestions(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion getQuestion(
		long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getQuestion(questionId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getQuestions(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getQuestions(groupId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsQuestion> getQuestions(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getQuestions(groupId, start, end);
	}

	public int getQuestionsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.getQuestionsCount(groupId);
	}

	public com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		long userId, long questionId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.updateQuestion(userId, questionId,
			titleMap, descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire);
	}

	public com.liferay.portlet.polls.model.PollsQuestion updateQuestion(
		long userId, long questionId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> choices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsQuestionLocalService.updateQuestion(userId, questionId,
			titleMap, descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, choices, serviceContext);
	}

	public PollsQuestionLocalService getWrappedPollsQuestionLocalService() {
		return _pollsQuestionLocalService;
	}

	private PollsQuestionLocalService _pollsQuestionLocalService;
}