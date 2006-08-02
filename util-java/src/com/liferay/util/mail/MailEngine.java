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

package com.liferay.util.mail;

import com.liferay.util.GetterUtil;
import com.liferay.util.JNDIUtil;
import com.liferay.util.Validator;

import java.io.ByteArrayInputStream;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MailEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 *
 */
public class MailEngine {

	public static final String MAIL_SESSION = "java:comp/env/mail/MailSession";

	public static Session getSession() throws NamingException {
		return getSession(false);
	}

	public static Session getSession(boolean cache) throws NamingException {
		Session session = (Session)JNDIUtil.lookup(
			new InitialContext(), MAIL_SESSION, cache);

		session.setDebug(_log.isDebugEnabled());

		if (_log.isDebugEnabled()) {
			session.getProperties().list(System.out);
		}

		return session;
	}

	public static void send(MailMessage mailMessage)
		throws MailEngineException {

		send(
			mailMessage.getFrom(), mailMessage.getTo(), mailMessage.getCC(),
			mailMessage.getBCC(), mailMessage.getSubject(),
			mailMessage.getBody(), mailMessage.isHTMLFormat());
	}

	public static void send(String from, String to, String subject, String body)
		throws MailEngineException {

		try {
			send(
				new InternetAddress(from), new InternetAddress(to), subject,
				body);
		}
		catch (AddressException ae) {
			throw new MailEngineException(ae);
		}
	}

	public static void send(
			InternetAddress from, InternetAddress to,
			String subject, String body)
		throws MailEngineException {

		send(
			from, new InternetAddress[] {to}, null, null, subject, body, false);
	}

	public static void send(
			InternetAddress from, InternetAddress to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException {

		send(
			from, new InternetAddress[] {to}, null, null, subject, body,
			htmlFormat);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body)
		throws MailEngineException {

		send(from, to, null, null, subject, body, false);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException {

		send(from, to, null, null, subject, body, htmlFormat);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body)
		throws MailEngineException {

		send(from, to, cc, null, subject, body, false);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body, boolean htmlFormat)
		throws MailEngineException {

		send(from, to, cc, null, subject, body, htmlFormat);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body)
		throws MailEngineException {

		send(from, to, cc, bcc, subject, body, false);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body,
			boolean htmlFormat)
		throws MailEngineException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();

			_log.debug("From: " + from);
			_log.debug("To: " + to);
			_log.debug("CC: " + cc);
			_log.debug("BCC: " + bcc);
			_log.debug("Subject: " + subject);
			_log.debug("Body: " + body);
			_log.debug("HTML Format: " + htmlFormat);
		}

		try {
			Session session = getSession();

			Message msg = new MimeMessage(session);

			msg.setFrom(from);
			msg.setRecipients(Message.RecipientType.TO, to);

			if (cc != null) {
				msg.setRecipients(Message.RecipientType.CC, cc);
			}

			if (bcc != null) {
				msg.setRecipients(Message.RecipientType.BCC, bcc);
			}

			msg.setSubject(subject);

			/*BodyPart bodyPart = new MimeBodyPart();

			if (htmlFormat) {
				bodyPart.setContent(body, "text/html");
			}
			else {
				bodyPart.setText(body);
			}

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);

			msg.setContent(multipart);*/

			if (htmlFormat) {
				msg.setContent(body, _TEXT_HTML);
			}
			else {
				msg.setContent(body, _TEXT_PLAIN);
			}

			msg.setSentDate(new Date());

			_send(session, msg);
		}
		catch (SendFailedException sfe) {
			_log.error(sfe);
		}
		catch (Exception e) {
			throw new MailEngineException(e);
		}

		if (_log.isDebugEnabled()) {
			long end = System.currentTimeMillis();

			_log.debug("Sending mail takes " + (end - start) + " ms");
		}
	}

	public static void send(byte[] msgByteArray) throws MailEngineException {
		try {
			Session session = getSession();

			Message msg = new MimeMessage(
				session, new ByteArrayInputStream(msgByteArray));

			_send(session, msg);
		}
		catch (Exception e) {
			throw new MailEngineException(e);
		}
	}

	private static void _send(Session session, Message msg)
		throws MessagingException {

		boolean smtpAuth = GetterUtil.getBoolean(
			session.getProperty("mail.smtp.auth"), false);
		String smtpHost = session.getProperty("mail.smtp.host");
		String user = session.getProperty("mail.smtp.user");
		String password = session.getProperty("mail.smtp.password");

		if (smtpAuth && Validator.isNotNull(user) &&
			Validator.isNotNull(password)) {

			Transport transport = session.getTransport("smtp");

			transport.connect(smtpHost, user, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}
		else {
			Transport.send(msg);
		}
	}

	private static final String _TEXT_HTML = "text/html;charset=\"UTF-8\"";

	private static final String _TEXT_PLAIN = "text/plain;charset=\"UTF-8\"";

	private static Log _log = LogFactory.getLog(MailEngine.class);

}