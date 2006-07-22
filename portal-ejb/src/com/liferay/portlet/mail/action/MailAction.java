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

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.DateFormats;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mail.util.MailEnvelope;
import com.liferay.portlet.mail.util.MailFolder;
import com.liferay.portlet.mail.util.MailMessage;
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
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String rtString = "";

		try {
			if ("deleteMessages".equals(cmd)) {
				_deleteMessages(req);
			}
			else if ("getFolders".equals(cmd)) {
				rtString = _getFolders(req);
			}
			else if ("getMessage".equals(cmd)) {
				rtString = _getMessage(req, res);
			}
			else if ("getPreview".equals(cmd)) {
				rtString = _getPreviewHeaders(req);
			}
			else if ("moveMessages".equals(cmd)) {
				_moveMessages(req);
			}
/*
			else if ("saveDraft".equals(cmd)) {
				_saveDraft(req);
			}
*/
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}

		return rtString;
	}

	private String _deleteMessages(HttpServletRequest req) throws Exception {
		String messages = ParamUtil.getString(req, "messages");
		long msgList[] = StringUtil.split(messages, ",", -1L);

		MailUtil.deleteMessages(req.getSession(), msgList);

		return null;
	}

	private String _getFolders(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();
		JSONArray jFolders = new JSONArray();

		int count = 1;

		List folders = MailUtil.getAllFolders(req.getSession());

		for (int i = 0; i < folders.size(); i++) {
			MailFolder folderObj = (MailFolder)folders.get(i);
			JSONObject jFolderObj = new JSONObject();

			String folderName = folderObj.getName();

			jFolderObj.put("name", folderName);
			jFolderObj.put("id", folderName);
			jFolderObj.put("newCount", folderObj.getNewMessageCount());
			jFolderObj.put("totalCount", folderObj.getMessageCount());

			if (MailUtil.MAIL_INBOX_NAME.equals(folderName)) {
				jFolders.put(0, jFolderObj);
			}
			else {
				jFolders.put(count++, jFolderObj);
			}
		}

		jsonObj.put("folders", jFolders);

		return jsonObj.toString();
	}

	private String _getMessage(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		JSONObject jsonObj = new JSONObject();

		long messageId = ParamUtil.getLong(req, "messageId");
		String folderId = ParamUtil.getString(req, "folderId");
		StringBuffer header = new StringBuffer();
		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		MailUtil.setCurrentFolder(req.getSession(), folderId);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String url = themeDisplay.getPathMain() + "/mail/get_attachment?";

		MailMessage mm = MailUtil.getMessage(req.getSession(), messageId, url);

		req.setAttribute("mailMessage", mm);

		PortalUtil.renderPage(header, ctx, req, res,
			"/html/portlet/mail/message_details.jsp");

		jsonObj.put("body", mm.getHtmlBody());
		jsonObj.put("header", header.toString());
		jsonObj.put("id", messageId);

		return jsonObj.toString();
	}

	private String _getPreviewHeaders(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");
		String sortBy = ParamUtil.getString(req, "sortBy");
		boolean asc = ParamUtil.getBoolean(req, "asc");
		SortedSet set;

		MailUtil.setCurrentFolder(req.getSession(), folderId);

		if ("name".equals(sortBy)) {
			set = MailUtil.getEnvelopes(req.getSession(),
				new RecipientComparator(asc));
		}
		else if ("subject".equals(sortBy)) {
			set = MailUtil.getEnvelopes(req.getSession(),
				new SubjectComparator(asc));
		}
		else {
			set = MailUtil.getEnvelopes(req.getSession(),
				new DateComparator(asc));
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

		JSONArray meArray = new JSONArray();

		for (Iterator itr = set.iterator(); itr.hasNext(); ) {
			MailEnvelope me = (MailEnvelope)itr.next();
			JSONObject jMe = new JSONObject();

			String formattedDate = null;

			String day = df.format(me.getDate());
			if (td.equals(day)) {
				formattedDate = LanguageUtil.get(user, "today") +
					StringPool.SPACE + tf.format(me.getDate());
			}
			else if (yd.equals(day)) {
				formattedDate = LanguageUtil.get(user, "yesterday") +
					StringPool.SPACE + tf.format(me.getDate());
			}
			else {
				formattedDate = dtf.format(me.getDate());
			}

			jMe.put("date", formattedDate);
			jMe.put("id", me.getMsgUID());
			jMe.put("email",
				GetterUtil.getString(me.getRecipient(), StringPool.NBSP));
			jMe.put("subject",
				GetterUtil.getString(me.getSubject(), StringPool.NBSP));
			jMe.put("recent", me.isRecent());
			meArray.put(jMe);
		}

		jsonObj.put("headers", meArray);

		return jsonObj.toString();
	}

	private String _moveMessages(HttpServletRequest req) throws Exception {
		String messages = ParamUtil.getString(req, "messages");
		String toFolder = ParamUtil.getString(req, "folderId");
		long msgList[] = StringUtil.split(messages, ",", -1L);

		MailUtil.moveMessages(req.getSession(), msgList, toFolder);

		return null;
	}

/*
	private String _saveDraft(HttpServletRequest req) throws Exception {
		User user = PortalUtil.getUser(req);
		Address from = new InternetAddress(
			user.getEmailAddress(), user.getFullName());

		String tos = ParamUtil.getString(req, "tos");
		String ccs = ParamUtil.getString(req, "ccs");
		String bccs = ParamUtil.getString(req, "bccs");
		String subject = ParamUtil.getString(req, "subject");
		String body = ParamUtil.getString(req, "body");
		long messageId = ParamUtil.getLong(req, "messageId", -1L);
		Map attachments = ActionUtil.getAttachments(
				PortalUtil.getUploadPortletRequest(req));

		HttpSession ses =
			((ActionRequestImpl)req).getHttpServletRequest().getSession();
		long newMessageId =
			ActionUtil.completeMessage(from, tos, ccs, bccs, subject, body,
				attachments, ses, false, messageId);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", newMessageId);

		return jsonObj.toString();
	}
*/
	private static Log _log = LogFactory.getLog(MailAction.class);

}