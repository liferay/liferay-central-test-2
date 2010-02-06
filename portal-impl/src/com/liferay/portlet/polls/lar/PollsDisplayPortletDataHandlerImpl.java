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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="PollsDisplayPortletDataHandlerImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			preferences.setValue("question-id", StringPool.BLANK);

			return preferences;
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
					_log.warn(nsqe);
				}
			}

			if (question == null) {
				return StringPool.BLANK;
			}

			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("polls-display-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element questionsEl = root.addElement("questions");
			Element choicesEl = root.addElement("choices");
			Element votesEl = root.addElement("votes");

			PollsPortletDataHandlerImpl.exportQuestion(
				context, questionsEl, choicesEl, votesEl, question);

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
			if (Validator.isNull(data)) {
				return null;
			}

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> questionEls =
				root.element("questions").elements("question");

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

				PollsPortletDataHandlerImpl.importQuestion(
					context, questionPKs, question);
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

				PollsPortletDataHandlerImpl.importChoice(
					context, questionPKs, choicePKs, choice);
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

					PollsPortletDataHandlerImpl.importVote(
						context, questionPKs, choicePKs, vote);
				}
			}

			long questionId = GetterUtil.getLong(
				preferences.getValue("question-id", StringPool.BLANK));

			if (questionId > 0) {
				questionId = MapUtil.getLong(
					questionPKs, questionId, questionId);

				preferences.setValue("question-id", String.valueOf(questionId));
			}

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static final String _NAMESPACE = "polls";

	private static final PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static final PortletDataHandlerBoolean _votes =
		new PortletDataHandlerBoolean(_NAMESPACE, "votes");

	private static Log _log =
		LogFactoryUtil.getLog(PollsDisplayPortletDataHandlerImpl.class);

}