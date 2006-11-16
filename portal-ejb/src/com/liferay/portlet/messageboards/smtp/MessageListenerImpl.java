/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.service.spring.CompanyLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.StringPool;
import com.liferay.util.mail.JavaMailUtil;
import com.liferay.util.mail.MailEngine;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MessageListenerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Jorge Ferrer
 *
 */
public class MessageListenerImpl implements MessageListener {

	public boolean accept(String from, String recipient) {
		try {
			if (!recipient.startsWith(MBUtil.SMTP_PORTLET_PREFIX)) {
				return false;
			}

			String companyId = _getCompanyId(recipient);
			String categoryId = _getCategoryId(recipient);

			CompanyLocalServiceUtil.getCompany(companyId);

			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				categoryId);

			if (!category.getCompanyId().equals(companyId)) {
				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Checking if user exists by email: " + from);
			}
			UserLocalServiceUtil.getUserByEmailAddress(companyId, from);

			return true;
		}
		catch (Exception e) {
			_log.warn("Mail rejected because of: " + e.toString());
			return false;
		}
	}

	public void deliver(String from, String recipient, InputStream data)
		throws MessageListenerException {

		try {
			String companyId = _getCompanyId(recipient);
			String categoryId = _getCategoryId(recipient);

			if (_log.isDebugEnabled()) {
				_log.debug("Deliver message from " + from + " to category " +
					categoryId);
			}

			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			User user = UserLocalServiceUtil.getUserByEmailAddress(
				companyId, from);

			MimeMessage message = new MimeMessage(
				MailEngine.getSession(), data);

			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			MBMessage prevMessage = null;

			if ((inReplyToHeaders != null) && (inReplyToHeaders.length > 0)) {
				String prevMessageId = MBUtil.getMessageId(inReplyToHeaders[0]);

				try {
					if (prevMessageId != null) {
						prevMessage = MBMessageLocalServiceUtil.getMessage(
							prevMessageId);
					}
				}
				catch (NoSuchMessageException nsme) {

					// If the previous message does not exist we ignore it and
					// post the message as a new thread.

				}
			}

			MBMailMessage collector = new MBMailMessage();

			_collectPartContent(message, collector);

			PrincipalSessionBean.setThreadValues(user);

			if (prevMessage == null) {
				MBMessageServiceUtil.addMessage(
					categoryId, message.getSubject(), collector.getBody(),
					collector.getFiles(), false, 0.0, null, true, true);
			}
			else {
				MBMessageServiceUtil.addMessage(
					categoryId, prevMessage.getThreadId(),
					prevMessage.getMessageId(), message.getSubject(),
					collector.getBody(), collector.getFiles(), false, 0.0, null,
					true, true);
			}
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Prevented unauthorized post from " + from);
			}

			throw new MessageListenerException(pe);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new MessageListenerException(e);
		}
	}

	public String getId() {
		return MessageListenerImpl.class.getName();
	}

	private String _getCategoryId(String recipient) {
		int pos = recipient.indexOf(StringPool.AT);

		String categoryId = recipient.substring(
			MBUtil.SMTP_PORTLET_PREFIX.length(), pos);

		return categoryId;
	}

	private String _getCompanyId(String recipient) {
		int pos = recipient.indexOf(StringPool.AT);

		String smtpServerSubdomain = PropsUtil.get(
			PropsUtil.SMTP_SERVER_SUBDOMAIN);

		String companyId = recipient.substring(
			pos + smtpServerSubdomain.length() + 2);

		return companyId;
	}

	private void _collectMultipartContent(
			MimeMultipart multipart, MBMailMessage collector)
		throws IOException, MessagingException {

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart part = multipart.getBodyPart(i);

			_collectPartContent(part, collector);
		}
	}

	private void _collectPartContent(Part part, MBMailMessage collector)
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
				bytes = ((String)partContent).getBytes();
			}
			else if (partContent instanceof InputStream) {
				bytes = JavaMailUtil.getBytes(part);
			}

			collector.addFile(part.getFileName(), bytes);
		}
		else {
			if (partContent instanceof MimeMultipart) {
				_collectMultipartContent(
					(MimeMultipart)partContent, collector);
			}
			else if (partContent instanceof String) {
				if (contentType.startsWith("text/html")) {
					collector.setHtmlBody((String)partContent);
				}
				else {
					collector.setPlainBody((String)partContent);
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(MessageListenerImpl.class);

}