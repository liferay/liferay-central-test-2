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

package com.liferay.portlet.polls.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.polls.QuestionChoiceException;
import com.liferay.portlet.polls.QuestionDescriptionException;
import com.liferay.portlet.polls.QuestionExpirationDateException;
import com.liferay.portlet.polls.QuestionTitleException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;
import com.liferay.portlet.polls.service.spring.PollsQuestionLocalService;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsQuestionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsQuestionLocalServiceImpl
	implements PollsQuestionLocalService {

	public PollsQuestion addQuestion(
			String userId, String plid, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, List choices,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Question

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new QuestionExpirationDateException());
		}

		validate(title, description, choices);

		String questionId = Long.toString(CounterLocalServiceUtil.increment(
			PollsQuestion.class.getName()));

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

		addQuestionResources(
			question, addCommunityPermissions, addGuestPermissions);

		// Choices

		Iterator itr = choices.iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();

			choice.setQuestionId(questionId);

			PollsChoiceUtil.update(choice);
		}

		return question;
	}

	public void addQuestionResources(
			String questionId, boolean addCommunityPermissions,
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
			question.getPrimaryKey().toString(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void checkQuestions() throws PortalException, SystemException {
		Date now = new Date();

		List questions = PollsQuestionUtil.findAll();

		for (int i = 0; i < questions.size(); i++) {
			PollsQuestion question = (PollsQuestion)questions.get(i);

			if (question.getExpirationDate() != null &&
				question.getExpirationDate().before(now)) {

				deleteQuestion(question.getQuestionId());
			}
		}
	}

	public void deleteQuestion(String questionId)
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
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			question.getPrimaryKey().toString());

		// Question

		PollsQuestionUtil.remove(question.getQuestionId());
	}

	public void deleteQuestions(String groupId)
		throws PortalException, SystemException {

		Iterator itr = PollsQuestionUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			PollsQuestion question = (PollsQuestion)itr.next();

			deleteQuestion(question);
		}
	}

	public PollsQuestion getQuestion(String questionId)
		throws PortalException, SystemException {

		return PollsQuestionUtil.findByPrimaryKey(questionId);
	}

	public List getQuestions(String groupId) throws SystemException {
		return PollsQuestionUtil.findByGroupId(groupId);
	}

	public List getQuestions(String groupId, int begin, int end)
		throws SystemException {

		return PollsQuestionUtil.findByGroupId(groupId, begin, end);
	}

	public int getQuestionsCount(String groupId) throws SystemException {
		return PollsQuestionUtil.countByGroupId(groupId);
	}

	public PollsQuestion updateQuestion(
			String userId, String questionId, String title, String description,
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

		PollsChoiceUtil.removeByQuestionId(questionId);

		Iterator itr = choices.iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();

			choice.setQuestionId(questionId);

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

			if (Validator.isNull(choice.getChoiceId()) ||
				Validator.isNull(choice.getDescription())) {

				throw new QuestionChoiceException();
			}
		}
	}

}