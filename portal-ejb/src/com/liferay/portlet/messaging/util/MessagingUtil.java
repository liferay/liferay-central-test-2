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

package com.liferay.portlet.messaging.util;

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;

/**
 * <a href="MessagingUtil.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Ming-Gih Lam
 * 
 */
public class MessagingUtil {

	public static void addRosterEntry(HttpSession ses, String companyId,
		String email) throws PortalException, SystemException, XMPPException {

		Roster roster = getRoster(ses);

		User toUser = UserLocalServiceUtil.getUserByEmailAddress(companyId,
			email);
		String name = toUser.getFirstName() + " " + toUser.getLastName();
		String smackId = getXmppId(toUser);
		roster.createEntry(smackId, name, null);
	}

	public static void closeXMPPConnection(HttpSession ses) {
		if (isJabberEnabled()) {
			XMPPConnection connection = getConnection(ses);

			if (connection != null) {
				connection.close();
			}

			ses.removeAttribute(WebKeys.JABBER_XMPP_CONNECTION);
			ses.removeAttribute(WebKeys.JABBER_XMPP_COLLECTOR);
			ses.removeAttribute(WebKeys.JABBER_XMPP_ROSTER);
		}
	}

	public static void createXMPPConnection(HttpSession ses, String userId)
		throws XMPPException {

		if (isJabberEnabled()) {
			XMPPConnection connection;

			String serverIp = GetterUtil.getString(PropsUtil
				.get(PropsUtil.JABBER_XMPP_SERVER_ADDRESS), "localhost");

			int serverPort = GetterUtil.getInteger(PropsUtil
				.get(PropsUtil.JABBER_XMPP_SERVER_PORT), 5222);

			String userPassword = GetterUtil.getString(PropsUtil
				.get(PropsUtil.JABBER_XMPP_USER_PASSWORD), "liferayllc");

			try {
				connection = new XMPPConnection(serverIp, serverPort);
			}
			catch (XMPPException e) {
				return;
			}

			try {
				connection.login(userId, userPassword, ses.getId());
			}
			catch (XMPPException e) {
				AccountManager account = connection.getAccountManager();
				account.createAccount(userId, userPassword);
				connection.close();

				connection = new XMPPConnection(serverIp, serverPort);
				connection.login(userId, userPassword, ses.getId());
			}

			PacketFilter filter = new PacketTypeFilter(Message.class);
			PacketCollector collector = connection
				.createPacketCollector(filter);

			ses.setAttribute(WebKeys.JABBER_XMPP_CONNECTION, connection);
			ses.setAttribute(WebKeys.JABBER_XMPP_COLLECTOR, collector);
			ses
				.setAttribute(WebKeys.JABBER_XMPP_ROSTER, connection
					.getRoster());
		}
	}

	public static void deleteRosterEntries(HttpSession ses, String[] userId)
		throws XMPPException {

		Roster roster = MessagingUtil.getRoster(ses);

		for (int i = 0; i < userId.length; i++) {
			RosterEntry entry = roster.getEntry(getXmppId(userId[i]));
			roster.removeEntry(entry);
		}
	}

	public static PacketCollector getCollector(HttpSession ses) {
		PacketCollector collector = (PacketCollector) ses
			.getAttribute(WebKeys.JABBER_XMPP_COLLECTOR);

		return collector;
	}

	public static XMPPConnection getConnection(HttpSession ses) {
		return ((XMPPConnection) ses
			.getAttribute(WebKeys.JABBER_XMPP_CONNECTION));
	}

	public static Message getNextMessage(PacketCollector collector) {
		Message message = (Message) collector.pollResult();

		return message;
	}

	public static String getPresence(Presence presence) {
		String status = "unavailable";

		if (presence != null) {
			status = presence.getType().toString();
		}
		return status;
	}

	public static Roster getRoster(HttpSession ses) {
		Roster roster = (Roster) ses.getAttribute(WebKeys.JABBER_XMPP_ROSTER);

		return roster;
	}

	public static String getUserId(RosterEntry entry) {
		String userId = entry.getUser();
		String serverName = GetterUtil.getString(PropsUtil
			.get(PropsUtil.JABBER_XMPP_SERVER_NAME), "localhost");

		userId = StringUtil.replace(userId, "@" + serverName, "");

		return userId;
	}

	public static String getXmppId(String userId) {
		String serverName = GetterUtil.getString(PropsUtil
			.get(PropsUtil.JABBER_XMPP_SERVER_NAME), "localhost");

		String xmppId = userId + "@" + serverName;

		return xmppId;
	}

	public static String getXmppId(User user) {

		String xmppId = getXmppId(user.getUserId());

		return xmppId;
	}

	public static boolean isJabberEnabled() {
		boolean jabberEnabled = GetterUtil.getBoolean(PropsUtil
			.get(PropsUtil.JABBER_XMPP_SERVER_ENABLED), false);

		return jabberEnabled;
	}

	public static void sendMessage(HttpSession ses, String fromId,
		String fromName, String toId, String toName, String bodyText)
		throws XMPPException {

		XMPPConnection connection = MessagingUtil.getConnection(ses);
		Chat newChat = connection.createChat(MessagingUtil.getXmppId(toId));

		Message message = newChat.createMessage();

		message.setBody(bodyText);
		message.setProperty("category", "private");
		message.setProperty("fromId", fromId);
		message.setProperty("fromName", fromName);
		message.setProperty("toId", toId);
		message.setProperty("toName", toName);

		newChat.sendMessage(message);
	}

}