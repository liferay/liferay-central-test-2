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

package com.liferay.portlet.messaging.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messaging.model.JabberSession;
import com.liferay.portlet.messaging.model.MessageWait;
import com.liferay.portlet.messaging.util.MessagingUtil;
import com.liferay.portlet.messaging.util.comparator.NameComparator;
import com.liferay.util.ParamUtil;

/**
 * <a href="MessagingAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ming-Gih Lam
 *
 */
public class MessagingAction extends JSONAction {

	public String getJSON(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);
		JSONObject jo = new JSONObject();

		if ("getChats".equals(cmd)) {
			jo = getUpdates(req);
		}
		else if ("getRosterChats".equals(cmd)) {
			jo = getChatMessages(req);

			JSONArray jRoster = getRosterEntries(req).getJSONArray("roster");
			jo.put("roster", jRoster);
		}
		else if ("sendChat".equals(cmd)) {
			jo = sendMessage(req);
		}
		else if ("unload".equals(cmd)) {
			release(req);
		}

		return jo.toString();
	}

	protected JSONObject getChatMessages(HttpServletRequest req) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		PacketCollector collector = MessagingUtil
			.getCollector(req.getSession());
		Message message = MessagingUtil.getNextMessage(collector);

		while (message != null) {
			JSONObject jMsg = new JSONObject();
			jMsg.put("body", message.getBody());
			jMsg.put("category", message.getProperty("category"));
			jMsg.put("toId", message.getProperty("toId"));
			jMsg.put("toName", message.getProperty("toName"));
			jMsg.put("fromId", message.getProperty("fromId"));
			jMsg.put("fromName", message.getProperty("fromName"));
			ja.put(jMsg);

			message = MessagingUtil.getNextMessage(collector);
		}

		jo.put("chat", ja);
		
		return jo;
	}

	protected JSONObject getRosterEntries(HttpServletRequest req) {
		JSONObject jo = new JSONObject();
		Roster roster = MessagingUtil.getRoster(req.getSession());
		List rosterList = new ArrayList();

		Iterator rosterEntries = roster.getEntries();
		JSONArray ja = new JSONArray();

		while (rosterEntries.hasNext()) {
			rosterList.add(rosterEntries.next());
		}

		Collections.sort(rosterList, new NameComparator());

		for (int i = 0; i < rosterList.size(); i++) {

			JSONObject jEntry = new JSONObject();
			RosterEntry entry = (RosterEntry)rosterList.get(i);

			jEntry.put("user", MessagingUtil.getUserId(entry));
			jEntry.put("name", entry.getName());
			jEntry.put("status", MessagingUtil.getPresence(roster
				.getPresence(entry.getUser())));
			ja.put(jEntry);
		}

		jo.put("roster", ja);

		return jo;
	}

	protected JSONObject getUpdates(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		JabberSession jabberSes = (JabberSession) ses.getAttribute(WebKeys.JABBER_XMPP_SESSION);
		JSONObject jo = new JSONObject();
		String waitCmd = "";
		
		try {
			MessageWait msgWait = jabberSes.getMessageWait();
			
			if (msgWait != null) {
				release(req);
			}
			
			msgWait = new MessageWait();
			
			jabberSes.setMessageWait(msgWait);
			
			msgWait.waitForMessages();
			
			msgWait = jabberSes.getMessageWait();
			
			if (msgWait != null) {
				waitCmd = msgWait.getCmd();
			
				if ("getRoster".equals(waitCmd)) {
					jo = getRosterEntries(req);
				}
				else {
					jo = getChatMessages(req);
				}
			}
		}
		catch (Exception e) {
			jo.put("status", "failure");
		}
		finally {
			jabberSes.setMessageWait(null);
		}

		return jo;
	}

	protected JSONObject sendMessage(HttpServletRequest req) {
		JSONObject jo = new JSONObject();

		try {
			String bodyText = ParamUtil.getString(req, "text");
			String tempId = ParamUtil.getString(req, "tempId", null);
			String toId = ParamUtil.getString(req, "toId");
			String toAddr = ParamUtil.getString(req, "toAddr", null);
			String companyId = PortalUtil.getCompanyId(req);
			User fromUser = PortalUtil.getUser(req);
			User toUser;

			if (toAddr != null) {
				toUser = UserLocalServiceUtil.getUserByEmailAddress(companyId,
					toAddr);
				toId = toUser.getUserId();
			}
			else {
				toUser = UserLocalServiceUtil.getUserById(toId);
			}

			MessagingUtil.sendMessage(req.getSession(), PortalUtil
				.getUserId(req), fromUser.getFullName(), toId, toUser
				.getFullName(), bodyText);

			jo.put("status", "success");
			jo.put("toId", toId);
			jo.put("toName", toUser.getFullName());
			jo.put("fromId", PortalUtil.getUserId(req));
			jo.put("fromName", fromUser.getFullName());
			if (tempId != null) {
				jo.put("tempId", tempId);
			}
		}
		catch (Exception e) {
			jo.put("status", "failure");
		}

		return jo;
	}
	
	public synchronized void notifyGetMessages() {
		notify();
	}

	protected void release(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		JabberSession jabberSes = (JabberSession) ses.getAttribute(WebKeys.JABBER_XMPP_SESSION);
		MessageWait msgWait = jabberSes.getMessageWait();
		
		if (msgWait != null) {
			jabberSes.setMessageWait(null);
			msgWait.notifyWait();
		}
	}

}