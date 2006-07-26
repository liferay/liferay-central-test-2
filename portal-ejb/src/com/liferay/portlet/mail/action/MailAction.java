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
import com.liferay.portlet.mail.util.comparator.SubjectComparator;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
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
				_deleteMessages(req);
			}
			else if (cmd.equals("getFolders")) {
				return _getFolders(req);
			}
			else if (cmd.equals("getMessage")) {
				return _getMessage(req, res);
			}
			else if (cmd.equals("getPreview")) {
				return _getPreview(req);
			}
			else if (cmd.equals("moveMessages")) {
				_moveMessages(req);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}

		return StringPool.BLANK;
	}

	private void _deleteMessages(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		long[] messages = StringUtil.split(
			ParamUtil.getString(req, "messages"), ",", -1L);

		MailUtil.deleteMessages(ses, messages);
	}

	private String _getFolders(HttpServletRequest req) throws Exception {
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
			jsonFolder.put("newCount", folder.getNewMessageCount());
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

	private String _getMessage(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		JSONObject jsonObj = new JSONObject();

		long messageId = ParamUtil.getLong(req, "messageId");
		String folderId = ParamUtil.getString(req, "folderId");
		StringBuffer header = new StringBuffer();
		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		MailUtil.getFolder(req.getSession(), folderId);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String url = themeDisplay.getPathMain() + "/mail/get_attachment?";

		MailMessage mm = MailUtil.getMessage(req.getSession(), messageId, url);

		req.setAttribute("mailMessage", mm);

		PortalUtil.renderPage(
			header, ctx, req, res, "/html/portlet/mail/message_details.jsp");

		jsonObj.put("body", mm.getHtmlBody());
		jsonObj.put("header", header.toString());
		jsonObj.put("id", messageId);

		return jsonObj.toString();
	}

	private String _getPreview(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		HttpSession ses = req.getSession();

		String folderId = ParamUtil.getString(req, "folderId");
		String sortBy = ParamUtil.getString(req, "sortBy");
		boolean asc = ParamUtil.getBoolean(req, "asc");

		MailUtil.getFolder(ses, folderId);

		SortedSet set = null;

		if (sortBy.equals("name")) {
			set = MailUtil.getEnvelopes(ses, new RecipientComparator(asc));
		}
		else if (sortBy.equals("subject")) {
			set = MailUtil.getEnvelopes(ses, new SubjectComparator(asc));
		}
		else {
			set = MailUtil.getEnvelopes(ses, new DateComparator(asc));
		}

		User user = PortalUtil.getUser(req);
		Locale locale = user.getLocale();
		TimeZone tz = user.getTimeZone();

		DateFormat dtf = DateFormats.getDateTime(locale, tz);
		DateFormat tf = DateFormats.getTime(locale, tz);
		DateFormat df = DateFormats.getDate(locale, tz);

		Date today = new Date();
		Calendar cal = Calendar.getInstance(tz, locale);
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();

		String td = df.format(today);
		String yd = df.format(yesterday);

		JSONArray jsonEnvelopes = new JSONArray();

		Iterator itr = set.iterator();

		while (itr.hasNext()) {
			MailEnvelope me = (MailEnvelope)itr.next();
			JSONObject jEnvelope = new JSONObject();

			String formattedDate = null;

			String day = df.format(me.getDate());

			if (td.equals(day)) {
				formattedDate =
					LanguageUtil.get(user, "today") + StringPool.SPACE + tf.format(me.getDate());
			}
			else if (yd.equals(day)) {
				formattedDate =
					LanguageUtil.get(user, "yesterday") + StringPool.SPACE + tf.format(me.getDate());
			}
			else {
				formattedDate = dtf.format(me.getDate());
			}

			jEnvelope.put("date", formattedDate);
			jEnvelope.put("id", me.getMessageId());
			jEnvelope.put("email", GetterUtil.getString(me.getRecipient(), StringPool.NBSP));
			jEnvelope.put("subject", GetterUtil.getString(me.getSubject(), StringPool.NBSP));
			jEnvelope.put("recent", me.isRecent());

			jsonEnvelopes.put(jEnvelope);
		}

		jsonObj.put("headers", jsonEnvelopes);

		return jsonObj.toString();
	}

	private void _moveMessages(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		long[] messages = StringUtil.split(
			ParamUtil.getString(req, "messages"), ",", -1L);
		String folderName = ParamUtil.getString(req, "folderName");

		MailUtil.moveMessages(req.getSession(), messages, folderName);
	}

	private static Log _log = LogFactory.getLog(MailAction.class);

}