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
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.polls.QuestionChoiceException;
import com.liferay.portlet.polls.QuestionDescriptionException;
import com.liferay.portlet.polls.QuestionExpirationDateException;
import com.liferay.portlet.polls.QuestionTitleException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.base.PollsQuestionLocalServiceBaseImpl;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
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
			int expirationDateMinute, boolean neverExpire, List choices,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, choices,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public PollsQuestion addQuestion(
			long userId, long plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, List choices,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addQuestion(
			userId, plid, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, choices, null, null,
			communityPermissions, guestPermissions);
	}

	public PollsQuestion addQuestion(
			long userId, long plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, List choices,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Question

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		validate(title, description, choices);

		long questionId = CounterLocalServiceUtil.increment();

		PollsQuestion question = PollsQuestionUtil.create(questionId);

		question.setGroupId(groupId);
		question.setCompanyId(user.getCompanyId());
		question.setUserId(user.getUserId());
		question.setUserName(user.getFullName());
		question.setCreateDate(now);
		question.setModifiedDate(now);
		question.setTitle(title);
		question.setDescription(description);
		question.setExpirationDate(expirationDate);

		PollsQuestionUtil.update(question);

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

		Iterator itr = choices.iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();

			long choiceId = CounterLocalServiceUtil.increment();

			choice.setChoiceId(choiceId);
			choice.setQuestionId(questionId);

			PollsChoiceUtil.update(choice);
		}

		return question;
	}

	public void addQuestionResources(
			long questionId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		addQuestionResources(
			question, addCommunityPermissions, addGuestPermissions);
	}

	public void addQuestionResources(
			PollsQuestion question, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addQuestionResources(
			long questionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		addQuestionResources(question, communityPermissions, guestPermissions);
	}

	public void addQuestionResources(
			PollsQuestion question, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), communityPermissions, guestPermissions);
	}

	public void deleteQuestion(long questionId)
		throws PortalException, SystemException {

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		deleteQuestion(question);
	}

	public void deleteQuestion(PollsQuestion question)
		throws PortalException, SystemException {

		// Votes

		PollsVoteUtil.removeByQuestionId(question.getQuestionId());

		// Choices

		PollsChoiceUtil.removeByQuestionId(question.getQuestionId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			question.getCompanyId(), PollsQuestion.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, question.getQuestionId());

		// Question

		PollsQuestionUtil.remove(question.getQuestionId());
	}

	public void deleteQuestions(long groupId)
		throws PortalException, SystemException {

		Iterator itr = PollsQuestionUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			PollsQuestion question = (PollsQuestion)itr.next();

			deleteQuestion(question);
		}
	}

	public PollsQuestion getQuestion(long questionId)
		throws PortalException, SystemException {

		return PollsQuestionUtil.findByPrimaryKey(questionId);
	}

	public List getQuestions(long groupId) throws SystemException {
		return PollsQuestionUtil.findByGroupId(groupId);
	}

	public List getQuestions(long groupId, int begin, int end)
		throws SystemException {

		return PollsQuestionUtil.findByGroupId(groupId, begin, end);
	}

	public int getQuestionsCount(long groupId) throws SystemException {
		return PollsQuestionUtil.countByGroupId(groupId);
	}

	public PollsQuestion updateQuestion(
			long userId, long questionId, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, List choices)
		throws PortalException, SystemException {

		// Question

		User user = UserUtil.findByPrimaryKey(userId);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		validate(title, description, choices);

		PollsQuestion question = PollsQuestionUtil.findByPrimaryKey(questionId);

		question.setModifiedDate(new Date());
		question.setTitle(title);
		question.setDescription(description);
		question.setExpirationDate(expirationDate);

		PollsQuestionUtil.update(question);

		// Choices

		int oldChoicesCount = PollsChoiceUtil.countByQuestionId(questionId);

		if (oldChoicesCount != choices.size()) {
			throw new QuestionChoiceException();
		}

		Iterator itr = choices.iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();

			String choiceName = choice.getName();
			String choiceDescription = choice.getDescription();

			choice = PollsChoiceUtil.fetchByQ_N(questionId, choiceName);

			if (choice == null) {
				long choiceId = CounterLocalServiceUtil.increment();

				choice = PollsChoiceUtil.create(choiceId);

				choice.setQuestionId(questionId);
				choice.setName(choiceName);
			}

			choice.setDescription(choiceDescription);

			PollsChoiceUtil.update(choice);
		}

		return question;
	}

	protected void validate(String title, String description, List choices)
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

		for (int i = 0; i < choices.size(); i++) {
			PollsChoice choice = (PollsChoice)choices.get(i);

			if (Validator.isNull(choice.getName()) ||
				Validator.isNull(choice.getDescription())) {

				throw new QuestionChoiceException();
			}
		}
	}

}