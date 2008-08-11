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
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.pop.MBMailMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.mail.JavaMailUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="EmailReaderMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class EmailReaderMessageListener implements MessageListener {

	public void receive(Object message) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void receive(String message) {

		EmailReaderRequest request =
			(EmailReaderRequest) JSONFactoryUtil.deserialize(message);
		List<Message> processedMessages = new LinkedList<Message>();
		try {
			openInbox(request);

			Message[] messages = fetchMessages();

			for (Message mailMessage : messages) {
				if (!isSpam(mailMessage)) {
					processMessage(request, mailMessage);
					processedMessages.add(mailMessage);
				}
			}

		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			try {
				_inboxFolder.setFlags(
					processedMessages.toArray(new Message[0]), new Flags(
						Flags.Flag.DELETED), true);
			}
			catch (Exception e) {
			}
			closeInbox();
		}

	}

	protected void closeInbox() {

		if (_inboxFolder != null && _inboxFolder.isOpen()) {
			try {
				_inboxFolder.close(true);
			}
			catch (MessagingException e) {
				_log.error(e);
			}
		}
		else {
			_log.warn("Trying to close a not open folder.");
		}

	}

	protected void collectMultipartContent(
		MimeMultipart multipart, MBMailMessage collector)
		throws IOException, MessagingException {

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart part = multipart.getBodyPart(i);

			collectPartContent(part, collector);
		}
	}

	protected void collectPartContent(Part part, MBMailMessage collector)
		throws IOException, MessagingException {

		Object partContent = part.getContent();

		String contentType = part.getContentType().toLowerCase();

		if ((part.getDisposition() != null) &&
			(part.getDisposition().equalsIgnoreCase(MimeMessage.ATTACHMENT))) {

			if (_log.isDebugEnabled()) {
				_log.debug("Processing attachment");
			}

			byte[] bytes = null;

			if (partContent instanceof String) {
				bytes = ((String) partContent).getBytes();
			}
			else if (partContent instanceof InputStream) {
				bytes = JavaMailUtil.getBytes(part);
			}

			collector.addFile(part.getFileName(), bytes);
		}
		else {
			if (partContent instanceof MimeMultipart) {
				collectMultipartContent((MimeMultipart) partContent, collector);
			}
			else if (partContent instanceof String) {
				if (contentType.startsWith("text/html")) {
					collector.setHtmlBody((String) partContent);
				}
				else {
					collector.setPlainBody((String) partContent);
				}
			}
		}
	}

	protected Message[] fetchMessages()
		throws MessagingException {

		Message[] messages = new Message[0];

		if (_inboxFolder != null && _inboxFolder.isOpen()) {
			messages = _inboxFolder.getMessages();
		}
		else {
			_log.warn("Trying to fetch messages from a not open folder!");
		}

		return messages;
	}

	protected long getParentMessageId(Message message)
		throws MessagingException {

		long parentMessageId = -1;

		// If the previous block failed, try to get the parent message ID from
		// the "References" header as explained in
		// http://cr.yp.to/immhf/thread.html. Some mail clients such as Yahoo!
		// Mail use the "In-Reply-To" header, so we check that as well.

		String parentHeader = null;

		String[] references = message.getHeader("References");

		if ((references != null) && (references.length > 0)) {
			parentHeader =
				references[0].substring(references[0].lastIndexOf("<"));
		}

		if (parentHeader == null) {
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			if ((inReplyToHeaders != null) && (inReplyToHeaders.length > 0)) {

				parentHeader = inReplyToHeaders[0];
			}
		}

		if (parentHeader != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Parent header " + parentHeader);
			}

			if (parentMessageId == -1) {
				parentMessageId = MBUtil.getMessageId(parentHeader);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Previous message id " + parentMessageId);
			}
		}

		return parentMessageId;
	}

	protected boolean isSpam(Message message) {

		return false;
	}

	protected void openInbox(EmailReaderRequest request)
		throws Exception {

		Session session = null;

		Properties properties = new Properties();

		String protocol = request.getMailInProtocol();
		Integer portNumber = request.getMailInServerPort();

		if (request.getMailInUseSSL()) {
			if (portNumber == null) {

				// trying the default ones

				if ("imap".equals(protocol)) {
					portNumber = new Integer(993);
				}
				else {
					portNumber = new Integer(995);
				}
			}

			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			properties.setProperty(
				"mail." + protocol + ".socketFactory.class", SSL_FACTORY);
			properties.setProperty("mail." + protocol +
				".socketFactory.fallback", "false");
			properties.setProperty(
				"mail." + protocol + ".port", portNumber.toString());
			properties.setProperty(
				"mail." + protocol + ".socketFactory.port",
				portNumber.toString());
		}
		else {
			if (portNumber == null) {

				// trying the default ones

				if ("imap".equals(protocol)) {
					portNumber = new Integer(143);
				}
				else {
					portNumber = new Integer(110);
				}
			}
		}

		session = Session.getInstance(properties, null);

		if (_log.isDebugEnabled()) {
			session.setDebug(true);

			_log.debug(
				"Trying to read a email using the following properties:");

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			session.getProperties().list(pw);
			pw.flush();
			_log.debug(sw.toString());

		}

		URLName url =
			new URLName(
				protocol, request.getMailInServerName(), portNumber, "",
				request.getMailInUserName(), request.getMailInPassword());

		Store store = session.getStore(url);
		store.connect();

		Folder defaultFolder = store.getDefaultFolder();

		Folder[] folders = defaultFolder.list();

		if (folders.length == 0) {
			throw new MessagingException("Inbox not found");
		}
		else {
			_inboxFolder = folders[0];
			_inboxFolder.open(Folder.READ_WRITE);
		}
	}

	protected void processMessage(EmailReaderRequest request, Message message)
		throws Exception {

		String from = StringPool.BLANK;

		String[] messageIds = message.getHeader("Message-ID");
		if (Validator.isNotNull(messageIds)) {
			for (String messageId : messageIds) {
				if (messageId.contains(_SUBDOMAIN_PART)) {
					_log.debug("Message received but already posted.");
					return;
				}
			}

		}

		Address[] addresses = message.getFrom();

		if (addresses != null && addresses.length != 0) {
			from = addresses[0].toString();
		}

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.debug("Deliver message from " + from);
		}

		Company company =
			CompanyLocalServiceUtil.getCompany(request.getCompanyId());

		long categoryId = request.getCategoryId();

		if (_log.isDebugEnabled()) {
			_log.debug("Category id " + categoryId);
		}
		boolean anonymous = false;
		User user =
			UserLocalServiceUtil.getUserById(
				company.getCompanyId(), request.getUserId());

		try {
			user =
				UserLocalServiceUtil.getUserByEmailAddress(
					company.getCompanyId(), from);
		}
		catch (NoSuchUserException nsue) {
			anonymous = true;
		}

		long parentMessageId = getParentMessageId(message);

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message id " + parentMessageId);
		}

		MBMessage parentMessage = null;

		try {
			if (parentMessageId > 0) {
				parentMessage =
					MBMessageLocalServiceUtil.getMessage(parentMessageId);
			}
		}
		catch (NoSuchMessageException nsme) {
			// If the parent message does not exist we ignore it and post
			// the message as a new thread.
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message " + parentMessage);
		}

		MBMailMessage collector = new MBMailMessage();

		collectPartContent(message, collector);

		PermissionCheckerUtil.setThreadValues(user);

		MBUtil.addEmailReceivedByMailing(collector.getBody());

		if (parentMessage == null) {
			MBMessageServiceUtil.addMessage(
				categoryId, message.getSubject(), collector.getBody(),
				collector.getFiles(), anonymous, 0.0, null, true, true);
		}
		else {
			MBMessageServiceUtil.addMessage(
				categoryId, parentMessage.getThreadId(),
				parentMessage.getMessageId(), message.getSubject(),
				collector.getBody(), collector.getFiles(), anonymous, 0.0,
				null, true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delivering message takes " + stopWatch.getTime() +
				" ms");
		}
	}

	private static Log _log =
		LogFactory.getLog(EmailReaderMessageListener.class);

	private static final String _SUBDOMAIN_PART =
		StringPool.AT + PropsValues.POP_SERVER_SUBDOMAIN + StringPool.PERIOD;

	protected Folder _inboxFolder;

}