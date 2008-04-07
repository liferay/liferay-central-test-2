/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
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

/**
 * <a href="PollsQuestionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsQuestionLocalServiceImpl
	extends PollsQuestionLocalServiceBaseImpl {

	public PollsQuestion addQuestion(
			long userId, long plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			null, userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public PollsQuestion addQuestion(
			String uuid, long userId, long plid, String title,
			String description, int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			uuid, userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public PollsQuestion addQuestion(
			long userId, long plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			List<PollsChoice> choices, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			null, userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, choices,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public PollsQuestion addQuestion(
			long userId, long plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			List<PollsChoice> choices, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			null, userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, choices, null, null,
			communityPermissions, guestPermissions);
	}

	public PollsQuestion addQuestion(
			String uuid, long userId, long plid, String title,
			String description, int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			List<PollsChoice> choices, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Question

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		Date now = new Date();

		validate(title, description, choices);

		long questionId = counterLocalService.increment();

		PollsQuestion question = pollsQuestionPersistence.create(questionId);

		question.setUuid(uuid);
		question.setGroupId(groupId);
		question.setCompanyId(user.getCompanyId());
		question.setUserId(user.getUserId());
		question.setUserName(user.getFullName());
		question.setCreateDate(now);
		question.setModifiedDate(now);
		question.setTitle(title);
		question.setDescription(description);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question, false);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addQuestionResources(
				question, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addQuestionResources(
				question, communityPermissions, guestPermissions);
		}

		// Choices

		for (PollsChoice choice : choices) {
			pollsChoiceLocalService.addChoice(
				questionId, choice.getName(), choice.getDescription());
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
			long questionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		addQuestionResources(question, communityPermissions, guestPermissions);
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

		// Votes

		pollsVotePersistence.removeByQuestionId(question.getQuestionId());

		// Choices

		pollsChoicePersistence.removeByQuestionId(question.getQuestionId());

		// Resources

		resourceLocalService.deleteResource(
			question.getCompanyId(), PollsQuestion.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, question.getQuestionId());

		// Question

		pollsQuestionPersistence.remove(question.getQuestionId());
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

	public List<PollsQuestion> getQuestions(long groupId, int begin, int end)
		throws SystemException {

		return pollsQuestionPersistence.findByGroupId(groupId, begin, end);
	}

	public int getQuestionsCount(long groupId) throws SystemException {
		return pollsQuestionPersistence.countByGroupId(groupId);
	}

	public PollsQuestion updateQuestion(
			long userId, long questionId, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire)
		throws PortalException, SystemException {

		return updateQuestion(
			userId, questionId, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null);
	}

	public PollsQuestion updateQuestion(
			long userId, long questionId, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			List<PollsChoice> choices)
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

		validate(title, description, choices);

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		question.setModifiedDate(new Date());
		question.setTitle(title);
		question.setDescription(description);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question, false);

		// Choices

		int oldChoicesCount = pollsChoicePersistence.countByQuestionId(
			questionId);

		if (oldChoicesCount > choices.size()) {
			throw new QuestionChoiceException();
		}

		for (PollsChoice choice : choices) {
			String choiceName = choice.getName();
			String choiceDescription = choice.getDescription();

			choice = pollsChoicePersistence.fetchByQ_N(questionId, choiceName);

			if (choice == null) {
				pollsChoiceLocalService.addChoice(
					questionId, choiceName, choiceDescription);
			}
			else {
				pollsChoiceLocalService.updateChoice(
					choice.getChoiceId(), questionId, choiceName,
					choiceDescription);
			}
		}

		return question;
	}

	protected void validate(
			String title, String description, List<PollsChoice> choices)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new QuestionTitleException();
		}
		else if (Validator.isNull(description)) {
			throw new QuestionDescriptionException();
		}

		if (choices.size() < 2) {
			throw new QuestionChoiceException();
		}
	}

}