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

package com.liferay.util.mail;

import com.liferay.portal.kernel.mail.SMTPAccount;

import java.util.Properties;

import javax.mail.Session;

import junit.framework.TestCase;

/**
 * <a href="MailEngineTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MailEngineTest extends TestCase {

	public void testGetSessionSMTPAccountWithoutSSLWithPort()
		throws Exception {

		SMTPAccount smtpAccount = new SMTPAccount();
		smtpAccount.setServerName("smtp.liferay.com");
		smtpAccount.setServerPort(25);
		smtpAccount.setUserName("thiagom");
		smtpAccount.setPassword("");

		Session session = MailEngine.getSession(smtpAccount);

		Properties properties = session.getProperties();

		assertEquals("smtp", properties.get(_MAIL_TRANSPORT_PROTOCOL));
		assertEquals("true", properties.get(_MAIL_SMTP_AUTH));
		assertEquals(
			smtpAccount.getServerName(), properties.get(_MAIL_SMTP_HOST));
		assertEquals(
			String.valueOf(smtpAccount.getServerPort()),
			properties.get(_MAIL_SMTP_PORT));
		assertEquals(
			smtpAccount.getUserName(), properties.get(_MAIL_SMTP_USER));
		assertEquals(
			smtpAccount.getPassword(), properties.get(_MAIL_SMTP_PASSWORD));
	}

	public void testGetSessionSMTPAccountWithoutSSLWithoutPort()
		throws Exception {

		SMTPAccount smtpAccount = new SMTPAccount();
		smtpAccount.setServerName("smtp.liferay.com");
		smtpAccount.setUserName("thiagom");
		smtpAccount.setPassword("");

		Session session = MailEngine.getSession(smtpAccount);

		Properties properties = session.getProperties();

		assertEquals("smtp", properties.get(_MAIL_TRANSPORT_PROTOCOL));
		assertEquals("true", properties.get(_MAIL_SMTP_AUTH));
		assertEquals(
			smtpAccount.getServerName(), properties.get(_MAIL_SMTP_HOST));
		assertEquals(MailEngine.DEFAULT_SMTP_PORT,
			properties.get(_MAIL_SMTP_PORT));
		assertEquals(
			smtpAccount.getUserName(), properties.get(_MAIL_SMTP_USER));
		assertEquals(
			smtpAccount.getPassword(), properties.get(_MAIL_SMTP_PASSWORD));
	}

	public void testGetSessionSMTPAccountWithSSLWithPort()
		throws Exception {

		SMTPAccount smtpAccount = new SMTPAccount();
		smtpAccount.setServerName("smtp.liferay.com");
		smtpAccount.setServerPort(23546);
		smtpAccount.setUserName("thiagom");
		smtpAccount.setPassword("");
		smtpAccount.setUseSSL(true);

		Session session = MailEngine.getSession(smtpAccount);

		Properties properties = session.getProperties();

		assertEquals("smtps", properties.get(_MAIL_TRANSPORT_PROTOCOL));
		assertEquals("true", properties.get(_MAIL_SMTPS_AUTH));
		assertEquals(
			smtpAccount.getServerName(), properties.get(_MAIL_SMTPS_HOST));
		assertEquals(
			String.valueOf(smtpAccount.getServerPort()),
			properties.get(_MAIL_SMTPS_PORT));
		assertEquals(
			smtpAccount.getUserName(), properties.get(_MAIL_SMTPS_USER));
		assertEquals(
			smtpAccount.getPassword(), properties.get(_MAIL_SMTPS_PASSWORD));
		assertEquals(
			"false", properties.get(_MAIL_SMTPS_SOCKETFACTORY_FALLBACK));
		assertEquals(
			"javax.net.ssl.SSLSocketFactory",
			properties.get(_MAIL_SMTPS_SOCKETFACTORY_CLASS));
		assertEquals(
			String.valueOf(smtpAccount.getServerPort()),
			properties.get(_MAIL_SMTPS_SOCKETFACTORY_PORT));
	}

	public void testGetSessionSMTPAccountWithSSLWithoutPort()
		throws Exception {

		SMTPAccount smtpAccount = new SMTPAccount();
		smtpAccount.setServerName("smtp.liferay.com");
		smtpAccount.setUserName("thiagom");
		smtpAccount.setPassword("");
		smtpAccount.setUseSSL(true);

		Session session = MailEngine.getSession(smtpAccount);

		Properties properties = session.getProperties();

		assertEquals("smtps", properties.get(_MAIL_TRANSPORT_PROTOCOL));
		assertEquals("true", properties.get(_MAIL_SMTPS_AUTH));
		assertEquals(
			smtpAccount.getServerName(), properties.get(_MAIL_SMTPS_HOST));
		assertEquals(MailEngine.DEFAULT_SMTPS_PORT,
			properties.get(_MAIL_SMTPS_PORT));
		assertEquals(
			smtpAccount.getUserName(), properties.get(_MAIL_SMTPS_USER));
		assertEquals(
			smtpAccount.getPassword(), properties.get(_MAIL_SMTPS_PASSWORD));
		assertEquals(
			"false", properties.get(_MAIL_SMTPS_SOCKETFACTORY_FALLBACK));
		assertEquals(
			"javax.net.ssl.SSLSocketFactory",
			properties.get(_MAIL_SMTPS_SOCKETFACTORY_CLASS));
		assertEquals(MailEngine.DEFAULT_SMTPS_PORT,
			properties.get(_MAIL_SMTPS_SOCKETFACTORY_PORT));
	}

	private static final String _MAIL_TRANSPORT_PROTOCOL =
		"mail.transport.protocol";
	private static final String _MAIL_SMTPS_SOCKETFACTORY_PORT =
		"mail.smtps.socketFactory.port";
	private static final String _MAIL_SMTPS_SOCKETFACTORY_CLASS =
		"mail.smtps.socketFactory.class";
	private static final String _MAIL_SMTPS_SOCKETFACTORY_FALLBACK =
		"mail.smtps.socketFactory.fallback";
	private static final String _MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String _MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String _MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String _MAIL_SMTP_USER = "mail.smtp.user";
	private static final String _MAIL_SMTP_PASSWORD = "mail.smtp.password";
	private static final String _MAIL_SMTPS_AUTH = "mail.smtps.auth";
	private static final String _MAIL_SMTPS_HOST = "mail.smtps.host";
	private static final String _MAIL_SMTPS_PORT = "mail.smtps.port";
	private static final String _MAIL_SMTPS_USER = "mail.smtps.user";
	private static final String _MAIL_SMTPS_PASSWORD = "mail.smtps.password";

}