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
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrettyDateFormat;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mail.model.MailEnvelope;
import com.liferay.portlet.mail.model.MailFolder;
import com.liferay.portlet.mail.model.MailMessage;
import com.liferay.portlet.mail.search.MailDisplayTerms;
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MultiHashMap;
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
			if (cmd.equals("addFolder")) {
				addFolder(req);
			}
			else if (cmd.equals("deleteFolder")) {
				deleteFolder(req);
			}
			else if (cmd.equals("deleteMessages")) {
				deleteMessages(req);
			}
			else if (cmd.equals("emptyFolder")) {
				return emptyFolder(req);
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
			else if (cmd.equals("getSearch")) {
				return getSearch(req);
			}
			else if (cmd.equals("getSearchCached")) {
				return getSearchCached(req);
			}
			else if (cmd.equals("moveMessages")) {
				moveMessages(req);
			}
			else if (cmd.equals("renameFolder")) {
				renameFolder(req);
			}
			else if (cmd.equals("updatePreferences")) {
				updatePreferences(req);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}

		return null;
	}

	protected void addFolder(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.createFolder(ses, folderId);
	}

	protected void deleteFolder(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.removeFolder(ses, folderId);
	}

	protected void deleteMessages(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		MultiHashMap messages = _convertMessages(req);

		MailUtil.deleteMessages(ses, messages);
	}

	protected String emptyFolder(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.emptyFolder(ses, folderId);

		jsonObj.put("folderId", folderId);

		return jsonObj.toString();
	}

	protected Comparator getComparator(HttpServletRequest req) throws Exception {
		String sortBy = ParamUtil.getString(req, "sortBy");
		boolean asc = ParamUtil.getBoolean(req, "asc");

		Comparator comparator;

		if (sortBy.equals("state")) {
			comparator = new StateComparator(asc);
		}
		else if (sortBy.equals("name")) {
			comparator = new RecipientComparator(asc);
		}
		else if (sortBy.equals("subject")) {
			comparator = new SubjectComparator(asc);
		}
		else if (sortBy.equals("size")) {
			comparator = new SizeComparator(asc);
		}
		else {
			comparator = new DateComparator(asc);
		}

		return comparator;
	}

	protected String getFolders(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		JSONObject jsonObj = new JSONObject();

		_getFolders(ses, jsonObj);

		return jsonObj.toString();
	}

	protected String getMessage(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		HttpSession ses = req.getSession();

		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");
		long messageId = ParamUtil.getLong(req, "messageId");

		MailUtil.setFolder(ses, folderId);

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

		MailUtil.setFolder(req.getSession(), folderId);

		Set envelopes =
			MailUtil.getEnvelopes(req.getSession(), getComparator(req));

		JSONArray jsonEnvelopes = _convertEnvelopes(envelopes, themeDisplay);

		jsonObj.put("folderId", folderId);
		jsonObj.put("headers", jsonEnvelopes);

		return jsonObj.toString();
	}

	protected String getSearch(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		HttpSession ses = req.getSession();

		MailDisplayTerms displayTerms = new MailDisplayTerms(req);

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
				WebKeys.THEME_DISPLAY);

		Set envelopes = MailUtil.search(ses, displayTerms, getComparator(req));

		ses.setAttribute(WebKeys.MAIL_SEARCH_RESULTS, envelopes);

		JSONArray jsonEnvelopes = _convertEnvelopes(envelopes, themeDisplay);

		jsonObj.put("headers", jsonEnvelopes);

		return jsonObj.toString();
	}

	protected String getSearchCached(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		HttpSession ses = req.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set envelopes = new TreeSet(getComparator(req));

		envelopes.addAll((Set)ses.getAttribute(WebKeys.MAIL_SEARCH_RESULTS));

		ses.setAttribute(WebKeys.MAIL_SEARCH_RESULTS, envelopes);

		JSONArray jsonEnvelopes = _convertEnvelopes(envelopes, themeDisplay);

		jsonObj.put("headers", jsonEnvelopes);

		return jsonObj.toString();
	}

	protected void moveMessages(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		MultiHashMap messages = _convertMessages(req);

		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.moveMessages(ses, messages, folderId);
	}

	protected void renameFolder(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		String folderId = ParamUtil.getString(req, "folderId");
		String newFolderId = ParamUtil.getString(req, "newFolderId");

		MailUtil.renameFolder(ses, folderId, newFolderId);
	}

	protected void updatePreferences(HttpServletRequest req) throws Exception {
		PortletPreferences prefs = PortalUtil.getPreferences(req);

		String[] keys = StringUtil.split(ParamUtil.getString(req, "key"));
		String[] values = StringUtil.split(ParamUtil.getString(req, "value"));

		for (int i = 0; i < keys.length && i < values.length; i++) {
			prefs.setValue(keys[i], values[i]);
		}

		prefs.store();
	}

	private JSONArray _convertEnvelopes(
			Set envelopes, ThemeDisplay themeDisplay)
		throws LanguageException {

		PrettyDateFormat dateFormat =
			new PrettyDateFormat(themeDisplay.getCompanyId(),
				themeDisplay.getLocale(), themeDisplay.getTimeZone());

		JSONArray jsonEnvelopes = new JSONArray();

		Iterator itr = envelopes.iterator();

		while (itr.hasNext()) {
			MailEnvelope mailEnvelope = (MailEnvelope)itr.next();

			JSONObject jsonEnvelope = new JSONObject();

			String recipient = GetterUtil.getString(
				mailEnvelope.getRecipient(), StringPool.NBSP);

			String subject = GetterUtil.getString(
				mailEnvelope.getSubject(), StringPool.NBSP);

			jsonEnvelope.put("id", mailEnvelope.getMessageId());
			jsonEnvelope.put("folderId", mailEnvelope.getFolderName());
			jsonEnvelope.put("email", recipient);
			jsonEnvelope.put("subject", subject);
			jsonEnvelope.put("date", dateFormat.format(mailEnvelope.getDate()));
			jsonEnvelope.put(
				"size",
				TextFormatter.formatKB(
					mailEnvelope.getSize(), themeDisplay.getLocale()) + "k");
			jsonEnvelope.put("read", mailEnvelope.isRead());
			jsonEnvelope.put("replied", mailEnvelope.isAnswered());
			jsonEnvelope.put("flagged", mailEnvelope.isFlagged());

			jsonEnvelopes.put(jsonEnvelope);
		}

		return jsonEnvelopes;
	}

	private MultiHashMap _convertMessages(HttpServletRequest req) {
		String[] messagesArray = StringUtil.split(
			ParamUtil.getString(req, "messages"), ",");

		MultiHashMap messages = new MultiHashMap();

		for (int i = 0; i < messagesArray.length; i += 2) {
			messages.put(messagesArray[i], messagesArray[i+1]);
		}
		return messages;
	}

	private void _getFolders(HttpSession ses, JSONObject jsonObj)
		throws Exception {

		long start = System.currentTimeMillis();

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

		_log.info(
			"Total ms to get folders: " + (System.currentTimeMillis() - start));
	}

	private static Log _log = LogFactory.getLog(MailAction.class);

}