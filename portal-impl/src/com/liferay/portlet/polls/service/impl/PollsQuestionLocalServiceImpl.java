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

package com.liferay.portlet.polls.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.polls.QuestionChoiceException;
import com.liferay.portlet.polls.QuestionDescriptionException;
import com.liferay.portlet.polls.QuestionExpirationDateException;
import com.liferay.portlet.polls.QuestionTitleException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.base.PollsQuestionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="PollsQuestionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class PollsQuestionLocalServiceImpl
	extends PollsQuestionLocalServiceBaseImpl {

	public PollsQuestion addQuestion(
			String uuid, long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, List<PollsChoice> choices,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Question

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		Date now = new Date();

		validate(titleMap, descriptionMap, choices);

		long questionId = counterLocalService.increment();

		PollsQuestion question = pollsQuestionPersistence.create(questionId);

		question.setUuid(uuid);
		question.setGroupId(groupId);
		question.setCompanyId(user.getCompanyId());
		question.setUserId(user.getUserId());
		question.setUserName(user.getFullName());
		question.setCreateDate(now);
		question.setModifiedDate(now);
		question.setTitleMap(titleMap);
		question.setDescriptionMap(descriptionMap);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addQuestionResources(
				question, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addQuestionResources(
				question, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Choices

		if (choices != null) {
			for (PollsChoice choice : choices) {
				pollsChoiceLocalService.addChoice(
					null, questionId, choice.getName(),
					choice.getDescription());
			}
		}

		return question;
	}

	public void addQuestionResources(
			long questionId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		addQuestionResources(
			question, addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(
			long questionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		addQuestionResources(question, communityPermissions, guestPermissions);
	}

	public void addQuestionResources(
			PollsQuestion question, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addQuestionResources(
			PollsQuestion question, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), communityPermissions, guestPermissions);
	}

	public void deleteQuestion(long questionId)
		throws PortalException, SystemException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		deleteQuestion(question);
	}

	public void deleteQuestion(PollsQuestion question)
		throws PortalException, SystemException {

		// Question

		pollsQuestionPersistence.remove(question);

		// Resources

		resourceLocalService.deleteResource(
			question.getCompanyId(), PollsQuestion.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, question.getQuestionId());

		// Choices

		pollsChoicePersistence.removeByQuestionId(question.getQuestionId());

		// Votes

		pollsVotePersistence.removeByQuestionId(question.getQuestionId());
	}

	public void deleteQuestions(long groupId)
		throws PortalException, SystemException {

		for (PollsQuestion question :
				pollsQuestionPersistence.findByGroupId(groupId)) {

			deleteQuestion(question);
		}
	}

	public PollsQuestion getQuestion(long questionId)
		throws PortalException, SystemException {

		return pollsQuestionPersistence.findByPrimaryKey(questionId);
	}

	public List<PollsQuestion> getQuestions(long groupId)
		throws SystemException {

		return pollsQuestionPersistence.findByGroupId(groupId);
	}

	public List<PollsQuestion> getQuestions(long groupId, int start, int end)
		throws SystemException {

		return pollsQuestionPersistence.findByGroupId(groupId, start, end);
	}

	public int getQuestionsCount(long groupId) throws SystemException {
		return pollsQuestionPersistence.countByGroupId(groupId);
	}

	public PollsQuestion updateQuestion(
			long userId, long questionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire)
		throws PortalException, SystemException {

		return updateQuestion(
			userId, questionId, titleMap, descriptionMap, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null, null);
	}

	public PollsQuestion updateQuestion(
			long userId, long questionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, List<PollsChoice> choices,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Question

		User user = userPersistence.findByPrimaryKey(userId);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		validate(titleMap, descriptionMap, choices);

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		question.setModifiedDate(new Date());
		question.setTitleMap(titleMap);
		question.setDescriptionMap(descriptionMap);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question, false);

		// Choices

		if (choices != null) {
			int oldChoicesCount = pollsChoicePersistence.countByQuestionId(
				questionId);

			if (oldChoicesCount > choices.size()) {
				throw new QuestionChoiceException();
			}

			for (PollsChoice choice : choices) {
				String choiceName = choice.getName();
				String choiceDescription = choice.getDescription();

				choice = pollsChoicePersistence.fetchByQ_N(
					questionId, choiceName);

				if (choice == null) {
					pollsChoiceLocalService.addChoice(
						null, questionId, choiceName, choiceDescription);
				}
				else {
					pollsChoiceLocalService.updateChoice(
						choice.getChoiceId(), questionId, choiceName,
						choiceDescription);
				}
			}
		}

		return question;
	}

	protected void validate(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			List<PollsChoice> choices)
		throws PortalException {

		Locale locale = LocaleUtil.getDefault();

		String title = titleMap.get(locale);

		if (Validator.isNull(title)) {
			throw new QuestionTitleException();
		}

		String description = descriptionMap.get(locale);

		if (Validator.isNull(description)) {
			throw new QuestionDescriptionException();
		}

		if ((choices != null) && (choices.size() < 2)) {
			throw new QuestionChoiceException();
		}
	}

}