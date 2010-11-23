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

package com.liferay.portlet.messageboards.messaging;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;
import com.liferay.portlet.messageboards.util.BBCodeUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class MBMessageListener extends BaseMessageListener {

	protected void doReceive(
			com.liferay.portal.kernel.messaging.Message message)
		throws Exception {

		long companyId = message.getLong("companyId");
		long userId = message.getLong("userId");
		long groupId = message.getLong("groupId");
		String categoryIds = message.getString("categoryIds");
		long threadId = message.getLong("threadId");
		String fromName = message.getString("fromName");
		String fromAddress = message.getString("fromAddress");
		String subject = message.getString("subject");
		String body = message.getString("body");
		String replyToAddress = message.getString("replyToAddress");
		String mailId = message.getString("mailId");
		String inReplyTo = message.getString("inReplyTo");
		boolean htmlFormat = message.getBoolean("htmlFormat");
		boolean sourceMailingList = message.getBoolean("sourceMailingList");

		if (sourceMailingList) {
			subject = getMailingListSubject(subject, mailId);
		}

		Set<Long> sent = new HashSet<Long>();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" + mailId + ", threadId=" +
					threadId + ", categoryIds=" + categoryIds + "}");
		}

		// Threads

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, MBThread.class.getName(), threadId);

		sendEmail(
			userId, groupId, fromName, fromAddress, subject, body,
			subscriptions, sent, replyToAddress, mailId, inReplyTo, htmlFormat);

		// Categories

		long[] categoryIdsArray = StringUtil.split(categoryIds, 0L);

		for (long categoryId : categoryIdsArray) {
			if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				categoryId = groupId;
			}

			subscriptions = SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, MBCategory.class.getName(), categoryId);

			sendEmail(
				userId, groupId, fromName, fromAddress, subject, body,
				subscriptions, sent, replyToAddress, mailId, inReplyTo,
				htmlFormat);
		}

		// Mailing list

		if (!sourceMailingList) {
			for (long categoryId : categoryIdsArray) {
				try {
					notifyMailingList(
						subject, body, replyToAddress, mailId, inReplyTo,
						htmlFormat, groupId, categoryId);
				}
				catch (NoSuchMailingListException nsmle) {
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished sending notifications");
		}
	}

	protected String getMailingListSubject(String subject, String mailId) {
		return subject + StringPool.SPACE + mailId;
	}

	protected void notifyMailingList(
			String subject, String body, String replyToAddress, String mailId,
			String inReplyTo, boolean htmlFormat, long groupId, long categoryId)
		throws Exception {

		MBMailingList mailingList =
			MBMailingListLocalServiceUtil.getCategoryMailingList(
				groupId, categoryId);

		if (!mailingList.isActive()) {
			return;
		}

		subject = getMailingListSubject(subject, mailId);

		String fromAddress = mailingList.getOutEmailAddress();

		InternetAddress[] bulkAddresses = new InternetAddress[] {
			new InternetAddress(mailingList.getEmailAddress())
		};

		SMTPAccount account = null;

		if (mailingList.isOutCustom()) {
			String protocol = Account.PROTOCOL_SMTP;

			if (mailingList.isOutUseSSL()) {
				protocol = Account.PROTOCOL_SMTPS;
			}

			account = (SMTPAccount)Account.getInstance(
				protocol, mailingList.getOutServerPort());

			account.setHost(mailingList.getOutServerName());
			account.setUser(mailingList.getOutUserName());
			account.setPassword(mailingList.getOutPassword());
		}

		sendMail(
			fromAddress, null, bulkAddresses, subject, body, replyToAddress,
			mailId, inReplyTo, htmlFormat, account);
	}

	protected void sendEmail(
			long userId, long groupId, String fromName, String fromAddress,
			String subject, String body, List<Subscription> subscriptions,
			Set<Long> sent, String replyToAddress, String mailId,
			String inReplyTo, boolean htmlFormat)
		throws Exception {

		List<InternetAddress> addresses = new ArrayList<InternetAddress>();

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

			if (!GroupLocalServiceUtil.hasUserGroup(
					subscribedUserId, groupId)) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Subscription " + subscription.getSubscriptionId() +
							" is stale and will be deleted");
				}

				SubscriptionLocalServiceUtil.deleteSubscription(
					subscription.getSubscriptionId());

				continue;
			}

			InternetAddress userAddress = new InternetAddress(
				user.getEmailAddress(), user.getFullName());

			addresses.add(userAddress);
		}

		InternetAddress[] bulkAddresses = addresses.toArray(
			new InternetAddress[addresses.size()]);

		sendMail(
			fromAddress, fromName, bulkAddresses, subject, body, replyToAddress,
			mailId, inReplyTo, htmlFormat, null);
	}

	protected void sendMail(
		String fromAddress, String fromName, InternetAddress[] bulkAddresses,
		String subject, String body, String replyToAddress, String mailId,
		String inReplyTo, boolean htmlFormat, SMTPAccount account) {

		try {
			if (bulkAddresses.length == 0) {
				return;
			}

			InternetAddress from = new InternetAddress(fromAddress, fromName);

			InternetAddress to = new InternetAddress(
				replyToAddress, replyToAddress);

			String curSubject = StringUtil.replace(
				subject,
				new String[] {
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					replyToAddress,
					replyToAddress
				});

			String curBody = StringUtil.replace(
				body,
				new String[] {
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					replyToAddress,
					replyToAddress
				});

			InternetAddress replyTo = new InternetAddress(
				replyToAddress, replyToAddress);

			if (htmlFormat) {
				try {
					curBody = BBCodeUtil.getHTML(curBody);
				}
				catch (Exception e) {
					_log.error(
						"Could not parse message " + mailId + " " +
							e.getMessage());
				}
			}

			MailMessage message = new MailMessage(
				from, to, curSubject, curBody, htmlFormat);

			message.setBulkAddresses(bulkAddresses);
			message.setMessageId(mailId);
			message.setInReplyTo(inReplyTo);
			message.setReplyTo(new InternetAddress[] {replyTo});
			message.setSMTPAccount(account);

			MailServiceUtil.sendEmail(message);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageListener.class);

}