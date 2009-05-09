/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.messaging.mailinglist;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.mail.MailEngine;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

/**
 * <a href="MailingListMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * Receives trigger from Scheduler and then polls the inbox for a mailing list
 * for messages to import into the previously configured category.
 * 
 * @author Thiago Moreira
 *
 */
public class MLPollingMessageListener implements MessageListener {

	public MLPollingMessageListener(
		MailingListMessageProcessor messageProcessor) {

		_messageProcessor = messageProcessor;
	}

	public void receive(com.liferay.portal.kernel.messaging.Message message) {
		MailingListRequest mailingListRequest =
			(MailingListRequest)JSONFactoryUtil.deserialize(
				(String)message.getPayload());

		Folder folder = null;

		Message[] messages = null;

		try {
			folder = getFolder(mailingListRequest);

			messages = folder.getMessages();

			for (Message mailMessage : messages) {
				if (_messageProcessor.accept(mailMessage)) {
					_messageProcessor.deliver(mailingListRequest, mailMessage);
				}
			}

		}
		catch (Exception e) {
			_log.error(
				"Error retrieving messages from " + mailingListRequest, e);
		}
		finally {
			if ((folder != null) && folder.isOpen()) {
				try {
					folder.setFlags(
						messages, new Flags(Flags.Flag.DELETED), true);
				}
				catch (Exception e) {
					if (_log.isErrorEnabled()) {
					    _log.error("Unable to flag messages deleted", e);
					}
				}

				try {
					folder.close(true);
				}
				catch (Exception e) {
					if (_log.isErrorEnabled()) {
					    _log.error("Unable to close inbox", e);
					}
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

		if ((folders == null) || (folders.length == 0)) {
			throw new MessagingException("Inbox not found");
		}

		Folder folder = folders[0];

		folder.open(Folder.READ_WRITE);

		return folder;
	}

	private static Log _log =
		LogFactoryUtil.getLog(MLPollingMessageListener.class);

	private MailingListMessageProcessor _messageProcessor;
}