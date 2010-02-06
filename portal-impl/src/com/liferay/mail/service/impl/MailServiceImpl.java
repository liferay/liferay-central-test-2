/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.mail.service.impl;

import com.liferay.mail.model.Filter;
import com.liferay.mail.service.MailService;
import com.liferay.mail.util.Hook;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;

/**
 * <a href="MailServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MailServiceImpl implements MailService {

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		if (_log.isDebugEnabled()) {
			_log.debug("addForward");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "addForward",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId), filters,
				emailAddresses, new BooleanWrapper(leaveCopy)
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("addUser");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "addUser",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId), password,
				firstName, middleName, lastName, emailAddress
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		if (_log.isDebugEnabled()) {
			_log.debug("addVacationMessage");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "addVacationMessage",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId),
				emailAddress, vacationMessage
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void clearSession() {
		_session = null;
	}

	public void deleteEmailAddress(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteEmailAddress");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "deleteEmailAddress",
			new Object[] {new LongWrapper(companyId), new LongWrapper(userId)});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void deleteUser(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteUser");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "deleteUser",
			new Object[] {new LongWrapper(companyId), new LongWrapper(userId)});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public Session getSession() throws SystemException {
		if (_session != null) {
			return _session;
		}

		Session session = InfrastructureUtil.getMailSession();

		if (!PrefsPropsUtil.getBoolean(PropsKeys.MAIL_SESSION_MAIL)) {
			_session = session;

			return _session;
		}

		String advancedPropertiesString = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES,
			PropsValues.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES);
		String pop3Host = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_HOST,
			PropsValues.MAIL_SESSION_MAIL_POP3_HOST);
		String pop3Password = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD,
			PropsValues.MAIL_SESSION_MAIL_POP3_PASSWORD);
		int pop3Port = PrefsPropsUtil.getInteger(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PORT,
			PropsValues.MAIL_SESSION_MAIL_POP3_PORT);
		String pop3User = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_USER,
			PropsValues.MAIL_SESSION_MAIL_POP3_USER);
		String smtpHost = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST,
			PropsValues.MAIL_SESSION_MAIL_SMTP_HOST);
		String smtpPassword = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD,
			PropsValues.MAIL_SESSION_MAIL_SMTP_PASSWORD);
		int smtpPort = PrefsPropsUtil.getInteger(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT,
			PropsValues.MAIL_SESSION_MAIL_SMTP_PORT);
		String smtpUser = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_USER,
			PropsValues.MAIL_SESSION_MAIL_SMTP_USER);
		String storeProtocol = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL,
			PropsValues.MAIL_SESSION_MAIL_STORE_PROTOCOL);
		String transportProtocol = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL,
			PropsValues.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL);

		Properties properties = session.getProperties();

		// Incoming

		if (!storeProtocol.equals(Account.PROTOCOL_POPS)) {
			storeProtocol = Account.PROTOCOL_POP;
		}

		properties.setProperty("mail.store.protocol", storeProtocol);

		String storePrefix = "mail." + storeProtocol + ".";

		properties.setProperty(storePrefix + "host", pop3Host);
		properties.setProperty(storePrefix + "password", pop3Password);
		properties.setProperty(storePrefix + "port", String.valueOf(pop3Port));
		properties.setProperty(storePrefix + "user", pop3User);

		// Outgoing

		if (!transportProtocol.equals(Account.PROTOCOL_SMTPS)) {
			transportProtocol = Account.PROTOCOL_SMTP;
		}

		properties.setProperty("mail.transport.protocol", transportProtocol);

		String transportPrefix = "mail." + transportProtocol + ".";

		boolean smtpAuth = false;

		if (Validator.isNotNull(smtpPassword) ||
			Validator.isNotNull(smtpUser)) {

			smtpAuth = true;
		}

		properties.setProperty(
			transportPrefix + "auth", String.valueOf(smtpAuth));
		properties.setProperty(transportPrefix + "host", smtpHost);
		properties.setProperty(transportPrefix + "password", smtpPassword);
		properties.setProperty(
			transportPrefix + "port", String.valueOf(smtpPort));
		properties.setProperty(transportPrefix + "user", smtpUser);

		// Advanced

		try {
			if (Validator.isNotNull(advancedPropertiesString)) {
				Properties advancedProperties = PropertiesUtil.load(
					advancedPropertiesString);

				Iterator<Map.Entry<Object, Object>> itr =
					advancedProperties.entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry<Object, Object> entry = itr.next();

					String key = (String)entry.getKey();
					String value = (String)entry.getValue();

					properties.setProperty(key, value);
				}
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		_session = Session.getInstance(properties);

		return _session;
	}

	public void sendEmail(MailMessage mailMessage) {
		if (_log.isDebugEnabled()) {
			_log.debug("sendEmail");
		}

		MessageBusUtil.sendMessage(DestinationNames.MAIL, mailMessage);
	}

	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateBlocked");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "updateBlocked",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId), blocked
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateEmailAddress");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "updateEmailAddress",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId),
				emailAddress
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void updatePassword(long companyId, long userId, String password) {
		if (_log.isDebugEnabled()) {
			_log.debug("updatePassword");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			Hook.class.getName(), "updatePassword",
			new Object[] {
				new LongWrapper(companyId), new LongWrapper(userId), password
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	private static Log _log = LogFactoryUtil.getLog(MailServiceImpl.class);

	private Session _session;

}