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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("question-id", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		long questionId = GetterUtil.getLong(
			preferences.getValue("question-id", StringPool.BLANK));

		if (questionId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No question id found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
		}

		PollsQuestion question = null;

		try {
			question = PollsQuestionUtil.findByPrimaryKey(questionId);
		}
		catch (NoSuchQuestionException nsqe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsqe, nsqe);
			}

			return StringPool.BLANK;
		}

		context.addPermissions(
			"com.liferay.portlet.polls", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("polls-display-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element questionsElement = rootElement.addElement("questions");
		Element choicesElement = rootElement.addElement("choices");
		Element votesElement = rootElement.addElement("votes");

		PollsPortletDataHandlerImpl.exportQuestion(
			context, questionsElement, choicesElement, votesElement, question);

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.polls", context.getSourceGroupId(),
			context.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element questionsElement = rootElement.element("questions");

		for (Element questionEl : questionsElement.elements("question")) {
			String path = questionEl.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			PollsQuestion question = (PollsQuestion)context.getZipEntryAsObject(
				path);

			PollsPortletDataHandlerImpl.importQuestion(context, question);
		}

		Element choicesElement = rootElement.element("choices");

		for (Element choiceElement : choicesElement.elements("choice")) {
			String path = choiceElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice choice = (PollsChoice)context.getZipEntryAsObject(path);

			PollsPortletDataHandlerImpl.importChoice(context, choice);
		}

		if (context.getBooleanParameter(_NAMESPACE, "votes")) {
			Element votesElement = rootElement.element("votes");

			for (Element voteElement : votesElement.elements("vote")) {
				String path = voteElement.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				PollsVote vote = (PollsVote)context.getZipEntryAsObject(path);

				PollsPortletDataHandlerImpl.importVote(context, vote);
			}
		}

		long questionId = GetterUtil.getLong(
			preferences.getValue("question-id", StringPool.BLANK));

		if (questionId > 0) {
			Map<Long, Long> questionPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					PollsQuestion.class);

			questionId = MapUtil.getLong(
				questionPKs, questionId, questionId);

			preferences.setValue("question-id", String.valueOf(questionId));
		}

		return preferences;
	}

	private static final String _NAMESPACE = "polls";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static PortletDataHandlerBoolean _votes =
		new PortletDataHandlerBoolean(_NAMESPACE, "votes");

}