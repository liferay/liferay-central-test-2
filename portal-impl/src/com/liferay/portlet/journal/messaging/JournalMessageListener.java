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

package com.liferay.portlet.journal.messaging;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * @author Sergio González
 */
public class JournalMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		long companyId = message.getLong("companyId");
		long userId = message.getLong("userId");
		long groupId = message.getLong("groupId");
		String articleId = message.getString("articleId");
		String fromName = message.getString("fromName");
		String fromAddress = message.getString("fromAddress");
		String subject = message.getString("subject");
		String body = message.getString("body");
		String replyToAddress = message.getString("replyToAddress");
		String mailId = message.getString("mailId");
		boolean htmlFormat = message.getBoolean("htmlFormat");

		Set<Long> sent = new HashSet<Long>();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" + mailId + ", articleId=" +
					articleId + "}");
		}

		// Articles

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, JournalArticle.class.getName(), groupId);

		sendEmail(
			userId, fromName, fromAddress, subject, body, subscriptions, sent,
			replyToAddress, mailId, htmlFormat);

		if (_log.isInfoEnabled()) {
			_log.info("Finished sending notifications");
		}
	}

	protected void sendEmail(
			long userId, String fromName, String fromAddress, String subject,
			String body, List<Subscription> subscriptions, Set<Long> sent,
			String replyToAddress, String mailId, boolean htmlFormat)
		throws Exception {

		for (Subscription subscription : subscriptions) {
			long subscribedUserId = subscription.getUserId();

			if (sent.contains(subscribedUserId)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not send a duplicate email to user " +
							subscribedUserId);
				}

				continue;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Add user " + subscribedUserId +
							" to the list of users who have received an email");
				}

				sent.add(subscribedUserId);
			}

			User user = null;

			try {
				user = UserLocalServiceUtil.getUserById(subscribedUserId);
			}
			catch (NoSuchUserException nsue) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Subscription " + subscription.getSubscriptionId() +
							" is stale and will be deleted");
				}

				SubscriptionLocalServiceUtil.deleteSubscription(
					subscription.getSubscriptionId());

				continue;
			}

			if (!user.isActive()) {
				continue;
			}

			try {
				InternetAddress from = new InternetAddress(
					fromAddress, fromName);

				InternetAddress to = new InternetAddress(
					user.getEmailAddress(), user.getFullName());

				String curSubject = StringUtil.replace(
					subject,
					new String[] {
						"[$TO_ADDRESS$]",
						"[$TO_NAME$]"
					},
					new String[] {
						user.getFullName(),
						user.getEmailAddress()
					});

				String curBody = StringUtil.replace(
					body,
					new String[] {
						"[$TO_ADDRESS$]",
						"[$TO_NAME$]"
					},
					new String[] {
						user.getFullName(),
						user.getEmailAddress()
					});

				InternetAddress replyTo = new InternetAddress(
					replyToAddress, replyToAddress);

				MailMessage message = new MailMessage(
					from, to, curSubject, curBody, htmlFormat);

				message.setReplyTo(new InternetAddress[] {replyTo});
				message.setMessageId(mailId);

				MailServiceUtil.sendEmail(message);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalMessageListener.class);

}