/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.polls.DuplicateVoteException;
import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinderUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="PollsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class PollsPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportQuestion(
			PortletDataContext context, Element questionsEl, Element choicesEl,
			Element votesEl, PollsQuestion question)
		throws SystemException {

		if (!context.isWithinDateRange(question.getModifiedDate())) {
			return;
		}

		String path = getQuestionPath(context, question);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element questionEl = questionsEl.addElement("question");

		questionEl.addAttribute("path", path);

		question.setUserUuid(question.getUserUuid());

		List<PollsChoice> choices = PollsChoiceUtil.findByQuestionId(
			question.getQuestionId());

		for (PollsChoice choice : choices) {
			exportChoice(context, choicesEl, choice);
		}

		if (context.getBooleanParameter(_NAMESPACE, "votes")) {
			List<PollsVote> votes = PollsVoteUtil.findByQuestionId(
				question.getQuestionId());

			for (PollsVote vote : votes) {
				exportVote(context, votesEl, vote);
			}
		}

		context.addZipEntry(path, question);
	}

	public static void importChoice(
			PortletDataContext context, Map<Long, Long> questionPKs,
			Map<Long, Long> choicePKs, PollsChoice choice)
		throws Exception {

		long questionId = MapUtil.getLong(
			questionPKs, choice.getQuestionId(), choice.getQuestionId());

		PollsChoice existingChoice = null;

		try {
			PollsQuestionUtil.findByPrimaryKey(questionId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingChoice = PollsChoiceFinderUtil.findByUuid_G(
						choice.getUuid(), context.getGroupId());

					existingChoice = PollsChoiceLocalServiceUtil.updateChoice(
						existingChoice.getChoiceId(), questionId,
						choice.getName(), choice.getDescription());
				}
				catch (NoSuchChoiceException nsce) {
					existingChoice = PollsChoiceLocalServiceUtil.addChoice(
						choice.getUuid(), questionId, choice.getName(),
						choice.getDescription());
				}
			}
			else {
				existingChoice = PollsChoiceLocalServiceUtil.addChoice(
					null, questionId, choice.getName(),
					choice.getDescription());
			}

			choicePKs.put(choice.getChoiceId(), existingChoice.getChoiceId());
		}
		catch (NoSuchQuestionException nsqe) {
			_log.error(
				"Could not find the question for choice " +
					choice.getChoiceId());
		}
	}

	public static void importQuestion(
			PortletDataContext context, Map<Long, Long> questionPKs,
			PollsQuestion question)
		throws SystemException, PortalException {

		long userId = context.getUserId(question.getUserUuid());

		Date expirationDate = question.getExpirationDate();

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		PollsQuestion existingQuestion = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingQuestion =  PollsQuestionUtil.fetchByUUID_G(
				question.getUuid(), context.getGroupId());

			if (existingQuestion == null) {
				existingQuestion = PollsQuestionLocalServiceUtil.addQuestion(
					question.getUuid(), userId, question.getTitleMap(),
					question.getDescriptionMap(), expirationMonth,
					expirationDay, expirationYear, expirationHour,
					expirationMinute, neverExpire, null, serviceContext);
			}
			else {
				existingQuestion = PollsQuestionLocalServiceUtil.updateQuestion(
					userId, existingQuestion.getQuestionId(),
					question.getTitleMap(), question.getDescriptionMap(),
					expirationMonth, expirationDay, expirationYear,
					expirationHour, expirationMinute, neverExpire);
			}
		}
		else {
			existingQuestion = PollsQuestionLocalServiceUtil.addQuestion(
				null, userId, question.getTitleMap(),
				question.getDescriptionMap(), expirationMonth, expirationDay,
				expirationYear, expirationHour, expirationMinute, neverExpire,
				null, serviceContext);
		}

		questionPKs.put(
			question.getQuestionId(), existingQuestion.getQuestionId());
	}

	public static void importVote(
			PortletDataContext context, Map<Long, Long> questionPKs,
			Map<Long, Long> choicePKs, PollsVote vote)
		throws Exception {

		long userId = context.getUserId(vote.getUserUuid());
		long questionId = MapUtil.getLong(
			questionPKs, vote.getQuestionId(), vote.getQuestionId());
		long choiceId = MapUtil.getLong(
			choicePKs, vote.getChoiceId(), vote.getChoiceId());

		try {
			PollsQuestionUtil.findByPrimaryKey(questionId);
			PollsChoiceUtil.findByPrimaryKey(choiceId);

			PollsVoteLocalServiceUtil.addVote(
				userId, questionId, choiceId);
		}
		catch (DuplicateVoteException dve) {
		}
		catch (NoSuchQuestionException nsqe) {
			_log.error(
				"Could not find the question for vote " + vote.getVoteId());
		}
		catch (NoSuchChoiceException nsve) {
			_log.error(
				"Could not find the choice for vote " + vote.getVoteId());
		}
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					PollsPortletDataHandlerImpl.class, "deleteData")) {

				PollsQuestionLocalServiceUtil.deleteQuestions(
					context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("polls-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element questionsEl = root.addElement("questions");
			Element choicesEl = root.addElement("choices");
			Element votesEl = root.addElement("votes");

			List<PollsQuestion> questions = PollsQuestionUtil.findByGroupId(
				context.getGroupId());

			for (PollsQuestion question : questions) {
				exportQuestion(
					context, questionsEl, choicesEl, votesEl, question);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> questionEls = root.element("questions").elements(
				"question");

			Map<Long, Long> questionPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					PollsQuestion.class);

			for (Element questionEl : questionEls) {
				String path = questionEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				PollsQuestion question =
					(PollsQuestion)context.getZipEntryAsObject(path);

				importQuestion(context, questionPKs, question);
			}

			List<Element> choiceEls = root.element("choices").elements(
				"choice");

			Map<Long, Long> choicePKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					PollsChoice.class);

			for (Element choiceEl : choiceEls) {
				String path = choiceEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				PollsChoice choice = (PollsChoice)context.getZipEntryAsObject(
					path);

				importChoice(context, questionPKs, choicePKs, choice);
			}

			if (context.getBooleanParameter(_NAMESPACE, "votes")) {
				List<Element> voteEls = root.element("votes").elements("vote");

				for (Element voteEl : voteEls) {
					String path = voteEl.attributeValue("path");

					if (!context.isPathNotProcessed(path)) {
						continue;
					}

					PollsVote vote = (PollsVote)context.getZipEntryAsObject(
						path);

					importVote(context, questionPKs, choicePKs, vote);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	protected static void exportChoice(
			PortletDataContext context, Element questionsEl, PollsChoice choice)
		throws SystemException {

		String path = getChoicePath(context, choice);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element choiceEl = questionsEl.addElement("choice");

		choiceEl.addAttribute("path", path);

		context.addZipEntry(path, choice);
	}

	protected static void exportVote(
			PortletDataContext context, Element questionsEl, PollsVote vote)
		throws SystemException {

		String path = getVotePath(context, vote);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element voteEl = questionsEl.addElement("vote");

		voteEl.addAttribute("path", path);

		context.addZipEntry(path, vote);
	}

	protected static String getChoicePath(
		PortletDataContext context, PollsChoice choice) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(choice.getQuestionId());
		sb.append("/choices/");
		sb.append(choice.getChoiceId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getQuestionPath(
		PortletDataContext context, PollsQuestion question) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(question.getQuestionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getVotePath(
		PortletDataContext context, PollsVote vote) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(vote.getQuestionId());
		sb.append("/votes/");
		sb.append(vote.getVoteId());
		sb.append(".xml");

		return sb.toString();
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "polls";

	private static final PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static final PortletDataHandlerBoolean _votes =
		new PortletDataHandlerBoolean(_NAMESPACE, "votes");

	private static Log _log =
		LogFactoryUtil.getLog(PollsPortletDataHandlerImpl.class);

}