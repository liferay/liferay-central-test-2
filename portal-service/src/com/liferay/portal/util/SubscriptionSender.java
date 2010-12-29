/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 */
public class SubscriptionSender {

	public void addPersistedSubscribers(String className, long classPK) {
		ObjectValuePair<String, Long> ovp = new ObjectValuePair<String, Long>(
			className, classPK);

		persistestedSubscribersOVPs.add(ovp);
	}

	public void addRuntimeSubscribers(String toAddress, String toName) {
		ObjectValuePair<String, String> ovp =
			new ObjectValuePair<String, String>(toAddress, toName);

		runtimeSubscribersOVPs.add(ovp);
	}

	public void flushNotifications() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if ((classLoader != null) && (contextClassLoader != classLoader)) {
				currentThread.setContextClassLoader(classLoader);
			}

			for (ObjectValuePair<String, Long> ovp :
					persistestedSubscribersOVPs) {

				String className = ovp.getKey();
				long classPK = ovp.getValue();

				List<Subscription> subscriptions =
					SubscriptionLocalServiceUtil.getSubscriptions(
						companyId, className, classPK);

				for (Subscription subscription : subscriptions) {
					notifySubscriber(subscription);
				}

				if (bulk) {
					InternetAddress to = new InternetAddress(
						replyToAddress, replyToAddress);

					sendEmail(to, LocaleUtil.getDefault());
				}
			}

			persistestedSubscribersOVPs.clear();

			for (ObjectValuePair<String, String> ovp : runtimeSubscribersOVPs) {
				String toAddress = ovp.getKey();
				String toName = ovp.getValue();

				InternetAddress to = new InternetAddress(toAddress, toName);

				sendEmail(to, LocaleUtil.getDefault());
			}

			runtimeSubscribersOVPs.clear();
		}
		finally {
			if ((classLoader != null) && (contextClassLoader != classLoader)) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void flushNotificationsAsync() {
		Thread currentThread = Thread.currentThread();

		classLoader = currentThread.getContextClassLoader();

		MessageBusUtil.sendMessage(DestinationNames.SUBSCRIPTION_SENDER, this);
	}

	public String getMailId() {
		return this.mailId;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBulk(boolean bulk) {
		this.bulk = bulk;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setFrom(String address, String name) throws SystemException {
		try {
			from = new InternetAddress(address, name);
		}
		catch (UnsupportedEncodingException uee) {
			throw new SystemException(uee);
		}
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setHtmlFormat(boolean htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	}

	public void setSMTPAccount(SMTPAccount smtpAccount) {
		this.smtpAccount = smtpAccount;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	protected void deleteSubscription(Subscription subscription)
		throws Exception {

		SubscriptionLocalServiceUtil.deleteSubscription(
			subscription.getSubscriptionId());
	}

	protected boolean hasPermission(Subscription subscription, User user)
		throws Exception {

		return _PERMISSION;
	}

	protected void notifySubscriber(Subscription subscription)
		throws Exception {

		long subscriptionUserId = subscription.getUserId();

		if (sentUserIds.contains(subscriptionUserId)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not send a duplicate email to user " +
						subscriptionUserId);
			}

			return;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add user " + subscriptionUserId +
						" to the list of users who have received an email");
			}

			sentUserIds.add(subscriptionUserId);
		}

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserById(subscriptionUserId);
		}
		catch (NoSuchUserException nsue) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Subscription " + subscription.getSubscriptionId() +
						" is stale and will be deleted");
			}

			deleteSubscription(subscription);

			return;
		}

		if (!user.isActive()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip inactive user " + subscriptionUserId);
			}

			return;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		int type = group.getType();

		if (!GroupLocalServiceUtil.hasUserGroup(subscriptionUserId, groupId) &&
			(type != GroupConstants.TYPE_COMMUNITY_OPEN)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Subscription " + subscription.getSubscriptionId() +
						" is stale and will be deleted");
			}

			deleteSubscription(subscription);

			return;
		}

		try {
			if (!hasPermission(subscription, user)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skip unauthorized user " + subscriptionUserId);
				}

				return;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		if (bulk) {
			InternetAddress bulkAddress = new InternetAddress(
				user.getEmailAddress(), user.getFullName());

			if (bulkAddresses == null) {
				bulkAddresses = new ArrayList<InternetAddress>();
			}

			bulkAddresses.add(bulkAddress);
		}
		else {
			try {
				InternetAddress to = new InternetAddress(
					user.getEmailAddress(), user.getFullName());

				sendEmail(to, user.getLocale());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void processMailMessage(MailMessage mailMessage, Locale locale)
		throws Exception {

		InternetAddress to = mailMessage.getTo()[0];

		String processedSubject = StringUtil.replace(
			mailMessage.getSubject(),
			new String[] {
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				to.getAddress(),
				GetterUtil.getString(to.getPersonal(), to.getAddress())
			});

		mailMessage.setSubject(processedSubject);

		String processedBody = StringUtil.replace(
			mailMessage.getBody(),
			new String[] {
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				to.getAddress(),
				GetterUtil.getString(to.getPersonal(), to.getAddress())
			});

		mailMessage.setBody(processedBody);
	}

	protected void sendEmail(InternetAddress to, Locale locale)
		throws Exception {

		MailMessage mailMessage = new MailMessage(
			from, to, subject, body, htmlFormat);

		if (bulk) {
			mailMessage.setBulkAddresses(
				bulkAddresses.toArray(
					new InternetAddress[bulkAddresses.size()]));

			bulkAddresses.clear();
		}

		if (inReplyTo != null) {
			mailMessage.setInReplyTo(inReplyTo);
		}

		mailMessage.setMessageId(mailId);

		InternetAddress replyTo = new InternetAddress(
			replyToAddress, replyToAddress);

		mailMessage.setReplyTo(new InternetAddress[] {replyTo});

		if (smtpAccount != null) {
			mailMessage.setSMTPAccount(smtpAccount);
		}

		processMailMessage(mailMessage, locale);

		MailServiceUtil.sendEmail(mailMessage);
	}

	protected String body;
	protected boolean bulk;
	protected List<InternetAddress> bulkAddresses;
	protected ClassLoader classLoader;
	protected long companyId;
	protected Map<String, Object> context = new HashMap<String, Object>();
	protected InternetAddress from;
	protected long groupId;
	protected boolean htmlFormat;
	protected String inReplyTo;
	protected String mailId;
	protected List<ObjectValuePair<String, Long>> persistestedSubscribersOVPs =
		new ArrayList<ObjectValuePair<String, Long>>();
	protected String replyToAddress;
	protected List<ObjectValuePair<String, String>> runtimeSubscribersOVPs =
		new ArrayList<ObjectValuePair<String, String>>();
	protected Set<Long> sentUserIds = new HashSet<Long>();
	protected SMTPAccount smtpAccount;
	protected String subject;
	protected long userId;

	private static Log _log = LogFactoryUtil.getLog(SubscriptionSender.class);

	private static final boolean _PERMISSION = true;

}