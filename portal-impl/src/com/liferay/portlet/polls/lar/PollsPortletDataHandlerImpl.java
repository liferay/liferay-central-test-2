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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="PollsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class PollsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_POLLS_DATA, _enableExport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("polls-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Questions

			List questions = PollsQuestionUtil.findByGroupId(
				context.getGroupId());

			List choices = new ArrayList();

			List votes = new ArrayList();

			Iterator itr = questions.iterator();

			while (itr.hasNext()) {
				PollsQuestion question = (PollsQuestion)itr.next();

				if (context.addPrimaryKey(
						PollsQuestion.class, question.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					List questionChoices = PollsChoiceUtil.findByQuestionId(
						question.getQuestionId());

					choices.addAll(questionChoices);

					List questionVotes = PollsVoteUtil.findByQuestionId(
						question.getQuestionId());

					votes.addAll(questionVotes);
				}
			}

			String xml = xStream.toXML(questions);

			Element el = root.addElement("poll-questions");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Choices

			itr = choices.iterator();

			while (itr.hasNext()) {
				PollsChoice choice = (PollsChoice)itr.next();

				if (context.addPrimaryKey(
						PollsChoice.class, choice.getPrimaryKeyObj())) {

					itr.remove();
				}
			}

			xml = xStream.toXML(choices);

			el = root.addElement("poll-choices");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Votes

			itr = votes.iterator();

			while (itr.hasNext()) {
				PollsVote vote = (PollsVote)itr.next();

				if (context.addPrimaryKey(
						PollsVote.class, vote.getPrimaryKeyObj())) {

					itr.remove();
				}
			}

			xml = xStream.toXML(votes);

			el = root.addElement("poll-votes");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_POLLS_DATA, _enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Questions

			Element el = root.element("poll-questions").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map questionPKs = CollectionFactory.getHashMap();

			List questions = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = questions.iterator();

			while (itr.hasNext()) {
				PollsQuestion question = (PollsQuestion)itr.next();

				importQuestion(context, questionPKs, question);
			}

			// Choices

			el = root.element("poll-choices").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map choicePKs = CollectionFactory.getHashMap();

			List choices = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = choices.iterator();

			while (itr.hasNext()) {
				PollsChoice choice = (PollsChoice)itr.next();

				importChoice(context, questionPKs, choicePKs, choice);
			}

			// Votes

			el = root.element("poll-votes").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List votes = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = votes.iterator();

			while (itr.hasNext()) {
				PollsVote vote = (PollsVote)itr.next();

				importVote(context, questionPKs, choicePKs, vote);
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importChoice(
			PortletDataContext context, Map questionPKs, Map choicePKs,
			PollsChoice choice)
		throws Exception {

		Long questionId = (Long)questionPKs.get(
			new Long(choice.getQuestionId()));

		boolean newParentQuestion = false;

		if (questionId == null) {
			questionId = new Long(choice.getQuestionId());
		}
		else {
			newParentQuestion = true;
		}

		try {
			PollsQuestionUtil.findByPrimaryKey(questionId.longValue());

			if ((PollsChoiceUtil.fetchByPrimaryKey(
					choice.getPrimaryKey()) == null) ||
				newParentQuestion) {

				PollsChoice newChoice = PollsChoiceLocalServiceUtil.addChoice(
					questionId.longValue(), choice.getName(),
					choice.getDescription());

				choicePKs.put(
					choice.getPrimaryKeyObj(), newChoice.getPrimaryKeyObj());
			}
			else {
				choice.setQuestionId(questionId.longValue());

				PollsChoiceUtil.update(choice, true);
			}
		}
		catch (NoSuchQuestionException nsqe) {
			_log.error(
				"Could not find the question for choice " +
					choice.getChoiceId());
		}
	}

	protected void importQuestion(
			PortletDataContext context, Map questionPKs, PollsQuestion question)
		throws SystemException, PortalException {

		PollsQuestion existingQuestion =
			PollsQuestionUtil.fetchByPrimaryKey(question.getPrimaryKey());

		if ((existingQuestion == null) ||
			(existingQuestion.getGroupId() != context.getGroupId())) {

			long plid = context.getPlid();

			Date expirationDate = question.getExpirationDate();
			int expirationMonth = 0;
			int expirationDay = 0;
			int expirationYear = 0;
			int expirationHours = 0;
			int expirationMinutes = 0;

			boolean neverExpire = true;

			if (expirationDate != null) {
				expirationMonth = expirationDate.getMonth();
				expirationDay = expirationDate.getDay();;
				expirationYear = expirationDate.getYear();
				expirationHours = expirationDate.getHours();
				expirationMinutes = expirationDate.getMinutes();
				neverExpire = false;
			}

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			PollsQuestion newQuestion =
				PollsQuestionLocalServiceUtil.addQuestion(
					question.getUserId(), plid, question.getTitle(),
					question.getDescription(), expirationMonth, expirationDay,
					expirationYear, expirationHours, expirationMinutes,
					neverExpire, addCommunityPermissions, addGuestPermissions);

			questionPKs.put(
				question.getPrimaryKeyObj(), newQuestion.getPrimaryKeyObj());
		}
		else {
			PollsQuestionUtil.update(question, true);
		}
	}

	protected void importVote(
			PortletDataContext context, Map questionPKs, Map choicePKs,
			PollsVote vote)
		throws Exception {

		Long questionId = (Long)questionPKs.get(
			new Long(vote.getQuestionId()));

		boolean newParentQuestion = false;

		if (questionId == null) {
			questionId = new Long(vote.getQuestionId());
		}
		else {
			newParentQuestion = true;
		}

		Long choiceId = (Long)choicePKs.get(new Long(vote.getChoiceId()));

		boolean newParentChoice = false;

		if (choiceId == null) {
			choiceId = new Long(vote.getChoiceId());
		}
		else {
			newParentChoice = true;
		}

		try {
			PollsQuestionUtil.findByPrimaryKey(questionId.longValue());
			PollsChoiceUtil.findByPrimaryKey(choiceId.longValue());

			if ((PollsChoiceUtil.fetchByPrimaryKey(
					vote.getPrimaryKey()) == null) ||
				newParentChoice || newParentQuestion) {

				PollsVoteLocalServiceUtil.addVote(
					vote.getPrimaryKey(), questionId.longValue(),
					choiceId.longValue());
			}
			else {
				vote.setQuestionId(questionId.longValue());
				vote.setChoiceId(choiceId.longValue());

				PollsVoteUtil.update(vote, true);
			}
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

	private static final String _EXPORT_POLLS_DATA =
		"export-" + PortletKeys.POLLS + "-data";

	private static final String _IMPORT_POLLS_DATA =
		"import-" + PortletKeys.POLLS + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_POLLS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_POLLS_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(PollsPortletDataHandlerImpl.class);

}