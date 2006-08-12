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

package com.liferay.portlet.mail.action;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.DateFormats;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mail.model.MailEnvelope;
import com.liferay.portlet.mail.model.MailFolder;
import com.liferay.portlet.mail.model.MailMessage;
import com.liferay.portlet.mail.util.MailUtil;
import com.liferay.portlet.mail.util.comparator.DateComparator;
import com.liferay.portlet.mail.util.comparator.RecipientComparator;
import com.liferay.portlet.mail.util.comparator.SizeComparator;
import com.liferay.portlet.mail.util.comparator.StateComparator;
import com.liferay.portlet.mail.util.comparator.SubjectComparator;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.TextFormatter;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="MailAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ming-Gih Lam
 *
 */
public class MailAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("deleteMessages")) {
				deleteMessages(req);
			}
			else if (cmd.equals("getFolders")) {
				return getFolders(req);
			}
			else if (cmd.equals("getMessage")) {
				return getMessage(req, res);
			}
			else if (cmd.equals("getPreview")) {
				return getPreview(req);
			}
			else if (cmd.equals("moveMessages")) {
				moveMessages(req);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}

		return StringPool.BLANK;
	}

	protected void deleteMessages(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		long[] messages = StringUtil.split(
			ParamUtil.getString(req, "messages"), ",", -1L);

		MailUtil.deleteMessages(ses, messages);
	}

	protected Set getEnvelopes(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		String folderId = ParamUtil.getString(req, "folderId");
		String sortBy = ParamUtil.getString(req, "sortBy");
		boolean asc = ParamUtil.getBoolean(req, "asc");

		MailUtil.setFolder(ses, folderId);

		Set envelopes = null;

		if (sortBy.equals("state")) {
			envelopes = MailUtil.getEnvelopes(ses, new StateComparator(asc));
		}
		else if (sortBy.equals("name")) {
			envelopes = MailUtil.getEnvelopes(
				ses, new RecipientComparator(asc));
		}
		else if (sortBy.equals("subject")) {
			envelopes = MailUtil.getEnvelopes(ses, new SubjectComparator(asc));
		}
		else if (sortBy.equals("size")) {
			envelopes = MailUtil.getEnvelopes(ses, new SizeComparator(asc));
		}
		else {
			envelopes = MailUtil.getEnvelopes(ses, new DateComparator(asc));
		}

		return envelopes;
	}

	protected String getFolders(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		JSONObject jsonObj = new JSONObject();

		JSONArray jsonFolders = new JSONArray();

		int count = 1;

		Iterator itr = MailUtil.getFolders(ses).iterator();

		while (itr.hasNext()) {
			MailFolder folder = (MailFolder)itr.next();

			JSONObject jsonFolder = new JSONObject();

			String name = folder.getName();

			jsonFolder.put("name", name);
			jsonFolder.put("id", name);
			jsonFolder.put("newCount", folder.getUnreadMessageCount());
			jsonFolder.put("totalCount", folder.getMessageCount());

			if (name.equals(MailUtil.MAIL_INBOX_NAME)) {
				jsonFolders.put(0, jsonFolder);
			}
			else {
				jsonFolders.put(count++, jsonFolder);
			}
		}

		jsonObj.put("folders", jsonFolders);

		return jsonObj.toString();
	}

	protected String getMessage(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");
		long messageId = ParamUtil.getLong(req, "messageId");

		MailUtil.setFolder(req.getSession(), folderId);

		MailMessage mailMessage = MailUtil.getMessage(req, messageId);

		req.setAttribute("mailMessage", mailMessage);

		StringBuffer sb = new StringBuffer();

		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		PortalUtil.renderPage(
			sb, ctx, req, res, "/html/portlet/mail/message_details.jsp");

		jsonObj.put("id", messageId);
		jsonObj.put("body", mailMessage.getHtmlBody());
		jsonObj.put("header", sb.toString());

		return jsonObj.toString();
	}

	protected String getPreview(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();
		Locale locale = themeDisplay.getLocale();
		TimeZone timeZone = themeDisplay.getTimeZone();

		Date today = new Date();

		Calendar cal = Calendar.getInstance(timeZone, locale);

		cal.setTime(today);
		cal.add(Calendar.DATE, -1);

		Date yesterday = cal.getTime();

		DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);
		DateFormat dateFormatDateTime = DateFormats.getDateTime(
			locale, timeZone);
		DateFormat dateFormatTime = DateFormats.getTime(locale, timeZone);

		String todayString = dateFormatDate.format(today);
		String yesterdayString = dateFormatDate.format(yesterday);

		JSONArray jsonEnvelopes = new JSONArray();

		Iterator itr = getEnvelopes(req).iterator();

		while (itr.hasNext()) {
			MailEnvelope mailEnvelope = (MailEnvelope)itr.next();

			JSONObject jsonEnvelope = new JSONObject();

			String recipient = GetterUtil.getString(
				mailEnvelope.getRecipient(), StringPool.NBSP);

			String subject = GetterUtil.getString(
				mailEnvelope.getSubject(), StringPool.NBSP);

			String dateString = StringPool.NBSP;

			if (mailEnvelope.getDate() != null) {
				dateString = dateFormatDate.format(mailEnvelope.getDate());

				if (dateString.equals(todayString)) {
					dateString =
						LanguageUtil.get(user, "today") + StringPool.SPACE +
							dateFormatTime.format(mailEnvelope.getDate());
				}
				else if (dateString.equals(yesterdayString)) {
					dateString =
						LanguageUtil.get(user, "yesterday") + StringPool.SPACE +
							dateFormatTime.format(mailEnvelope.getDate());
				}
				else {
					dateString =
						dateFormatDateTime.format(mailEnvelope.getDate());
				}
			}

			jsonEnvelope.put("id", mailEnvelope.getMessageId());
			jsonEnvelope.put("email", recipient);
			jsonEnvelope.put("subject", subject);
			jsonEnvelope.put("date", dateString);
			jsonEnvelope.put(
				"size",
				TextFormatter.formatKB(mailEnvelope.getSize(), locale) + "k");
			jsonEnvelope.put("read", mailEnvelope.isRead());
			jsonEnvelope.put("replied", mailEnvelope.isAnswered());
			jsonEnvelope.put("flagged", mailEnvelope.isFlagged());

			jsonEnvelopes.put(jsonEnvelope);
		}

		jsonObj.put("folderId", folderId);
		jsonObj.put("headers", jsonEnvelopes);

		return jsonObj.toString();
	}

	protected void moveMessages(HttpServletRequest req) throws Exception {
		long[] messages = StringUtil.split(
			ParamUtil.getString(req, "messages"), ",", -1L);
		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.moveMessages(req.getSession(), messages, folderId);
	}

	private static Log _log = LogFactory.getLog(MailAction.class);

}