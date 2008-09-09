/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.messaging;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.util.MBMailMessage;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.MailMessageStrategy;
import com.liferay.portlet.messageboards.util.MailMessageStrategyFactory;
import com.liferay.portlet.messageboards.util.MailingListThreadLocal;
import com.liferay.util.mail.MailEngine;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MailingListMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 *
 */
public class MailingListMessageListener implements MessageListener {

	public void receive(com.liferay.portal.kernel.messaging.Message message) {
		MailingListRequest mailingListRequest =
			(MailingListRequest)JSONFactoryUtil.deserialize(
				(String)message.getPayload());

		Folder folder = null;

		Message[] messages = null;

		try {
			folder = getFolder(mailingListRequest);

			messages = folder.getMessages();

			processMessages(mailingListRequest, messages);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			if ((folder != null) && folder.isOpen()) {
				try {
					folder.setFlags(
						messages, new Flags(Flags.Flag.DELETED), true);
				}
				catch (Exception e) {
				}

				try {
					folder.close(true);
				}
				catch (Exception e) {
				}
			}
		}
	}

	protected Folder getFolder(MailingListRequest mailingListRequest)
		throws Exception {

		String protocol = mailingListRequest.getInProtocol();
		String host = mailingListRequest.getInServerName();
		int port = mailingListRequest.getInServerPort();
		String user = mailingListRequest.getInUserName();
		String password = mailingListRequest.getInPassword();

		Account account = Account.getInstance(protocol, port);

		account.setHost(host);
		account.setPort(port);
		account.setUser(user);
		account.setPassword(password);

		Session session = MailEngine.getSession(account);

		URLName urlName = new URLName(
			protocol, host, port, StringPool.BLANK, user, password);

		Store store = session.getStore(urlName);

		store.connect();

		Folder defaultFolder = store.getDefaultFolder();

		Folder[] folders = defaultFolder.list();

		if ((folders != null) && (folders.length == 0)) {
			throw new MessagingException("Inbox not found");
		}

		Folder folder = folders[0];

		folder.open(Folder.READ_WRITE);

		return folder;
	}

	protected void processMessage(
			MailingListRequest mailingListRequest, Message mailMessage)
		throws Exception {

		if (MBUtil.hasMailIdHeader(mailMessage)) {
			return;
		}

		String from = null;

		Address[] addresses = mailMessage.getFrom();

		if ((addresses != null) && (addresses.length > 0)) {
			Address address = addresses[0];

			if (address instanceof InternetAddress) {
				from = ((InternetAddress)address).getAddress();
			}
			else {
				from = address.toString();
			}
		}

		Company company = CompanyLocalServiceUtil.getCompany(
			mailingListRequest.getCompanyId());

		long categoryId = mailingListRequest.getCategoryId();

		if (_log.isDebugEnabled()) {
			_log.debug("Category id " + categoryId);
		}

		boolean anonymous = false;

		User user = UserLocalServiceUtil.getUserById(
			company.getCompanyId(), mailingListRequest.getUserId());

		try {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				company.getCompanyId(), from);
		}
		catch (NoSuchUserException nsue) {
			anonymous = true;
		}

		MailMessageStrategy mailMessageStrategy =
			MailMessageStrategyFactory.getInstance();

		MBMessage parentMessage = mailMessageStrategy.getParentMessage(
			categoryId, mailMessage);

		MBMailMessage collector = new MBMailMessage();

		MBUtil.collectPartContent(mailMessage, collector);

		PermissionCheckerUtil.setThreadValues(user);

		MailingListThreadLocal.setSourceMailingList(true);

		if (parentMessage == null) {
			MBMessageServiceUtil.addMessage(
				categoryId, mailMessage.getSubject(), collector.getBody(),
				collector.getFiles(), anonymous, 0.0, null, true, true);
		}
		else {
			MBMessageServiceUtil.addMessage(
				categoryId, parentMessage.getThreadId(),
				parentMessage.getMessageId(), mailMessage.getSubject(),
				collector.getBody(), collector.getFiles(), anonymous, 0.0,
				null, true, true);
		}
	}

	protected void processMessages(
			MailingListRequest mailingListRequest, Message[] messages)
		throws Exception {

		for (Message message : messages) {
			processMessage(mailingListRequest, message);
		}
	}

	private static Log _log =
		LogFactory.getLog(MailingListMessageListener.class);

}