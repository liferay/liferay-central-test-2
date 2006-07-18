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

package com.liferay.portlet.mailbox.action;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.mailbox.util.MailEnvelope;
import com.liferay.portlet.mailbox.util.MailFolder;
import com.liferay.portlet.mailbox.util.MailMessage;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.portlet.mailbox.util.comparator.DateComparator;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

/**
 * <a href="MailboxAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ming-Gih Lam
 *
 */
public class MailboxAction extends JSONAction {

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
				rtString = _getMessage(req);
			}
			else if ("getPreview".equals(cmd)) {
				rtString = _getPreviewHeaders(req);
			}
			else if ("moveMessages".equals(cmd)) {
				_moveMessages(req);
			}
			else if ("saveDraft".equals(cmd)) {
				_saveDraft(req);
			}
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
			JSONObject jFolderObj = new JSONObject();;

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

	private String _getMessage(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");
		long messageId = ParamUtil.getLong(req, "messageId");

		MailUtil.setCurrentFolder(req.getSession(), folderId);

		MailMessage mm = MailUtil.getMessage(req.getSession(), messageId);
		jsonObj.put("body", mm.getHtmlBody());
		jsonObj.put("id", messageId);

		return jsonObj.toString();
	}

	private String _getPreviewHeaders(HttpServletRequest req) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String folderId = ParamUtil.getString(req, "folderId");

		MailUtil.setCurrentFolder(req.getSession(), folderId);

		SortedSet set = MailUtil.getEnvelopes(req.getSession(),
			new DateComparator(false));

		JSONArray meArray = new JSONArray();

		for (Iterator itr = set.iterator(); itr.hasNext(); ) {
			MailEnvelope me = (MailEnvelope)itr.next();
			JSONObject jMe = new JSONObject();

			jMe.put("id", me.getMsgUID());
			jMe.put("email", me.getRecipient());
			jMe.put("subject", me.getSubject());
			jMe.put("date", me.getDate().toString());
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
	
	private String _saveDraft(HttpServletRequest req) throws Exception {
		String tos = ParamUtil.getString(req, "tos");
		String ccs = ParamUtil.getString(req, "ccs");
		String bccs = ParamUtil.getString(req, "bccs");
		String subject = ParamUtil.getString(req, "subject");
		String body = ParamUtil.getString(req, "body");
		long messageId = ParamUtil.getLong(req, "messageId", -1L);
		
		JSONObject jsonObj = new JSONObject();
		
		if (messageId > 0) {
			// MailUtil.updateDraft(messageId);
		}
		else {
			// messageId = MailUtil.saveNewDraft();
		}
		
		jsonObj.put("id", messageId);
		
		return jsonObj.toString();
	}

	private static Log _log = LogFactory.getLog(MailboxAction.class);

}