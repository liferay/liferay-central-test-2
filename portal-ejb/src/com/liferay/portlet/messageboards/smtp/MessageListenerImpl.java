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

package com.liferay.portlet.messageboards.smtp;

import com.liferay.portal.kernel.smtp.MessageListener;
import com.liferay.portal.kernel.smtp.MessageListenerException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.service.spring.CompanyLocalServiceUtil;
import com.liferay.portal.SystemException;
import com.liferay.portal.PortalException;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.mail.MailEngine;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.documentlibrary.FileNameException;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MessageListenerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class MessageListenerImpl implements MessageListener {

	public boolean accept(String from, String recipient) {
		String categoryId = recipient.substring(MBUtil.PORTLET_PREFIX.length(),
				recipient.indexOf('@'));
		String companyId = recipient.substring(recipient.indexOf('@') +
				(PropsUtil.get(PropsUtil.SMTP_SERVER_SUBDOMAIN) + ".").length() + 1);

		try {
			// Check that the company exists
			CompanyLocalServiceUtil.getCompany(companyId);

			// Check that the category exists
			List categoryIds = new ArrayList();
			categoryIds.add(categoryId);
			int count = MBMessageLocalServiceUtil.getCategoriesMessagesCount(categoryIds);
			if (count < 0) {
				return false;
			}

			// Check that the user exists
			UserLocalServiceUtil.getUserByEmailAddress(
				companyId, from);

			return true;
		}
		catch (NoSuchCompanyException e) {
			return false;
		}
		catch (NoSuchUserException e) {
			return false;
		}
		catch (PortalException e) {
			return false;
		}
		catch (SystemException e) {
			return false;
		}
	}

	public void deliver(String from, String recipient, InputStream data)
		throws MessageListenerException {
		String categoryId = recipient.substring(MBUtil.PORTLET_PREFIX.length(),
				recipient.indexOf('@'));
		String companyId = recipient.substring(recipient.indexOf('@') +
				(PropsUtil.get(PropsUtil.SMTP_SERVER_SUBDOMAIN) + ".").length() + 1);
		_log.debug("Deliver message from " + from + " to MB category " + categoryId);

		try {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);
			User user = UserLocalServiceUtil.getUserByEmailAddress(
				company.getCompanyId(), from);

			MimeMessage message = new MimeMessage(
				MailEngine.getSession(), data);
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");
			MBMessage prevMessage = null;
			if ((inReplyToHeaders != null) && (inReplyToHeaders.length > 0)) {
				int startIndex = inReplyToHeaders[0].indexOf("<") + 1;
				int endIndex = inReplyToHeaders[0].indexOf("@");
				try {
					if ((startIndex > 0 ) && (endIndex != -1)) {
						String prevMessageId = inReplyToHeaders[0].substring(startIndex, endIndex);
						// Check if message exists
						prevMessage = MBMessageLocalServiceUtil.getMessage(prevMessageId);
					}
				}
				catch (NoSuchMessageException ignore) {
					// If the previous message does not exist we ignore it and
					// post the msg as a new thread.
				}
			}

			MBMailMessage collector = new MBMailMessage();
			_collectPartContent(message, collector);

			if (prevMessage == null) {
				MBMessageLocalServiceUtil.addMessage(user.getUserId(), categoryId,
						message.getSubject(), collector.getBody(),
						collector.getFiles(), false, 0.0, null, true, true);
			}
			else {
				MBMessageLocalServiceUtil.addMessage(user.getUserId(), categoryId,
						prevMessage.getThreadId(), prevMessage.getMessageId(),
						message.getSubject(), collector.getBody(),
						collector.getFiles(), false, 0.0, null, true, true);
			}
		}
		catch (FileNameException e) {
//			String msg = "The file could not be delivered because the attached " +
//					"file " + e.getMessage() + " was not accepted";
			throw new MessageListenerException(e);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("MB Message not sent", e);
			}
			throw new MessageListenerException(e);
		}
	}

	public String getId() {
		return MessageListenerImpl.class.getName();
	}

	private void _collectPartContent(Part part, MBMailMessage collector)
			throws MessagingException, IOException {
		Object partContent = part.getContent();
		String contentType = part.getContentType().toLowerCase();
		if ((part.getDisposition() != null) && part.getDisposition().
				equalsIgnoreCase(MimeMessage.ATTACHMENT)) {
			_log.debug("Processing attachment...");
			byte[] bytes = null;
			if (partContent instanceof String) {
				bytes = ((String) partContent).getBytes();
			}
			else if (partContent instanceof InputStream) {
				bytes = _readPartStream(part);
			}
			collector.addFile(part.getFileName(), bytes);
		}
		else {
			if (partContent instanceof MimeMultipart) {
				_collectMultipartContent((MimeMultipart) partContent, collector);
			}
			else if (partContent instanceof String) {
				if (contentType.startsWith("text/html")) {
					collector.setHtmlBody((String) partContent);
				} else {
					collector.setPlainBody((String) partContent);
				}
			}
		}
	}

	// This method could be moved as public to mail util so that all input stream
	// handling related to parts is centralized and connections are never left
	// open
	private byte[] _readPartStream(Part part) throws MessagingException, IOException {
		InputStream is = part.getInputStream();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			byte[] buffer = new byte[8192];
			int count = 0;

			while ((count = is.read(buffer)) >= 0) {
				baos.write(buffer,0,count);
			}
			return baos.toByteArray();
		}
		finally {
			is.close();
		}
	}

	private void _collectMultipartContent(MimeMultipart multipart,
										  MBMailMessage collector)
			throws MessagingException, IOException {
		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart part = multipart.getBodyPart(i);
			_collectPartContent(part, collector);
		}
	}

	private static Log _log = LogFactory.getLog(MessageListenerImpl.class);

}

