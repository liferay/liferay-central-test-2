/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
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
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class PollsPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	protected static void exportChoice(
			PortletDataContext context, Element questionsElement,
			PollsChoice choice)
		throws Exception {

		String path = getChoicePath(context, choice);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element choiceElement = questionsElement.addElement("choice");

		choiceElement.addAttribute("path", path);

		context.addZipEntry(path, choice);
	}

	protected static void exportQuestion(
			PortletDataContext context, Element questionsElement,
			Element choicesElement, Element votesElement,
			PollsQuestion question)
		throws Exception {

		if (!context.isWithinDateRange(question.getModifiedDate())) {
			return;
		}

		String path = getQuestionPath(context, question);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element questionElement = questionsElement.addElement("question");

		questionElement.addAttribute("path", path);

		question.setUserUuid(question.getUserUuid());

		List<PollsChoice> choices = PollsChoiceUtil.findByQuestionId(
			question.getQuestionId());

		for (PollsChoice choice : choices) {
			exportChoice(context, choicesElement, choice);
		}

		if (context.getBooleanParameter(_NAMESPACE, "votes")) {
			List<PollsVote> votes = PollsVoteUtil.findByQuestionId(
				question.getQuestionId());

			for (PollsVote vote : votes) {
				exportVote(context, votesElement, vote);
			}
		}

		context.addPermissions(PollsQuestion.class, question.getQuestionId());

		context.addZipEntry(path, question);
	}

	protected static void exportVote(
			PortletDataContext context, Element questionsElement,
			PollsVote vote)
		throws Exception {

		String path = getVotePath(context, vote);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element voteEl = questionsElement.addElement("vote");

		voteEl.addAttribute("path", path);

		context.addZipEntry(path, vote);
	}

	protected static String getChoicePath(
		PortletDataContext context, PollsChoice choice) {

		StringBundler sb = new StringBundler(6);

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

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(question.getQuestionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getVotePath(
		PortletDataContext context, PollsVote vote) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(vote.getQuestionId());
		sb.append("/votes/");
		sb.append(vote.getVoteId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static void importChoice(
			PortletDataContext context, PollsChoice choice)
		throws Exception {

		Map<Long, Long> questionPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionPKs, choice.getQuestionId(), choice.getQuestionId());

		PollsChoice importedChoice = null;

		if (context.isDataStrategyMirror()) {
			PollsChoice existingChoice = PollsChoiceFinderUtil.fetchByUUID_G(
				choice.getUuid(), context.getScopeGroupId());

			if (existingChoice == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(choice.getUuid());

				importedChoice = PollsChoiceLocalServiceUtil.addChoice(
					questionId, choice.getName(), choice.getDescription(),
					serviceContext);
			}
			else {
				importedChoice = PollsChoiceLocalServiceUtil.updateChoice(
					existingChoice.getChoiceId(), questionId,
					choice.getName(), choice.getDescription());
			}
		}
		else {
			importedChoice = PollsChoiceLocalServiceUtil.addChoice(
				questionId, choice.getName(), choice.getDescription(),
				new ServiceContext());
		}

		Map<Long, Long> choicePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(PollsChoice.class);

		choicePKs.put(choice.getChoiceId(), importedChoice.getChoiceId());

		context.importPermissions(
			PollsChoice.class, choice.getChoiceId(),
			importedChoice.getChoiceId());
	}

	protected static void importQuestion(
			PortletDataContext context, PollsQuestion question)
		throws Exception {

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
		serviceContext.setCreateDate(question.getCreateDate());
		serviceContext.setModifiedDate(question.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		PollsQuestion importedQuestion = null;

		if (context.isDataStrategyMirror()) {
			PollsQuestion existingQuestion =  PollsQuestionUtil.fetchByUUID_G(
				question.getUuid(), context.getScopeGroupId());

			if (existingQuestion == null) {
				serviceContext.setUuid(question.getUuid());

				importedQuestion = PollsQuestionLocalServiceUtil.addQuestion(
					userId, question.getTitleMap(),
					question.getDescriptionMap(), expirationMonth,
					expirationDay, expirationYear, expirationHour,
					expirationMinute, neverExpire, null, serviceContext);
			}
			else {
				importedQuestion = PollsQuestionLocalServiceUtil.updateQuestion(
					userId, existingQuestion.getQuestionId(),
					question.getTitleMap(), question.getDescriptionMap(),
					expirationMonth, expirationDay, expirationYear,
					expirationHour, expirationMinute, neverExpire, null,
					serviceContext);
			}
		}
		else {
			importedQuestion = PollsQuestionLocalServiceUtil.addQuestion(
				userId, question.getTitleMap(), question.getDescriptionMap(),
				expirationMonth, expirationDay, expirationYear, expirationHour,
				expirationMinute, neverExpire, null, serviceContext);
		}

		Map<Long, Long> questionPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(PollsQuestion.class);

		questionPKs.put(
			question.getQuestionId(), importedQuestion.getQuestionId());

		context.importPermissions(
			PollsQuestion.class, question.getQuestionId(),
			importedQuestion.getQuestionId());
	}

	protected static void importVote(PortletDataContext context, PollsVote vote)
		throws Exception {

		long userId = context.getUserId(vote.getUserUuid());

		Map<Long, Long> questionPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionPKs, vote.getQuestionId(), vote.getQuestionId());

		Map<Long, Long> choicePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(PollsChoice.class);

		long choiceId = MapUtil.getLong(
			choicePKs, vote.getChoiceId(), vote.getChoiceId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(vote.getVoteDate());

		PollsVoteLocalServiceUtil.addVote(
			userId, questionId, choiceId, serviceContext);
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				PollsPortletDataHandlerImpl.class, "deleteData")) {

			PollsQuestionLocalServiceUtil.deleteQuestions(
				context.getScopeGroupId());
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.polls", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("polls-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element questionsElement = rootElement.addElement("questions");
		Element choicesElement = rootElement.addElement("choices");
		Element votesElement = rootElement.addElement("votes");

		List<PollsQuestion> questions = PollsQuestionUtil.findByGroupId(
			context.getScopeGroupId());

		for (PollsQuestion question : questions) {
			exportQuestion(
				context, questionsElement, choicesElement, votesElement,
				question);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.polls", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element questionsElement = rootElement.element("questions");

		for (Element questionElement : questionsElement.elements("question")) {
			String path = questionElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			PollsQuestion question = (PollsQuestion)context.getZipEntryAsObject(
				path);

			importQuestion(context, question);
		}

		Element choicesElement = rootElement.element("choices");

		for (Element choiceElement : choicesElement.elements("choice")) {
			String path = choiceElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice choice = (PollsChoice)context.getZipEntryAsObject(path);

			importChoice(context, choice);
		}

		if (context.getBooleanParameter(_NAMESPACE, "votes")) {
			Element votesElement = rootElement.element("votes");

			for (Element voteElement : votesElement.elements("vote")) {
				String path = voteElement.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				PollsVote vote = (PollsVote)context.getZipEntryAsObject(path);

				importVote(context, vote);
			}
		}

		return null;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "polls";

	private static PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static PortletDataHandlerBoolean _votes =
	new PortletDataHandlerBoolean(_NAMESPACE, "votes");

}