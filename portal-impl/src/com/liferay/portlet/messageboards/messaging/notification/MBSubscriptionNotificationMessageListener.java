/*
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

package com.liferay.portlet.messageboards.messaging.notification;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * <a href="MBSubscriptionMessageListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * Notifies subscribers of new posts in the message boards
 *
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 * @author Michael C. Han
 */
public class MBSubscriptionNotificationMessageListener
	extends BaseMBNotificationMessageListener {

	public MBSubscriptionNotificationMessageListener(
		MessageSender mailMessageSender,
		SubscriptionLocalService subscriptionLocalService,
		UserLocalService userLocalService) {

		super(mailMessageSender);
		_subscriptionLocalService = subscriptionLocalService;
		_userLocalService = userLocalService;

	}

	public void receive(com.liferay.portal.kernel.messaging.Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(com.liferay.portal.kernel.messaging.Message message)
		throws Exception {

		long companyId = message.getLong("companyId");
		String categoryIds = message.getString("categoryIds");
		long threadId = message.getLong("threadId");
		String fromName = message.getString("fromName");
		String fromAddress = message.getString("fromAddress");
		String body = message.getString("body");
		String replyToAddress = message.getString("replyToAddress");
		String mailId = message.getString("mailId");
		String inReplyTo = message.getString("inReplyTo");
		boolean htmlFormat = message.getBoolean("htmlFormat");
		//some mail clients do not preserve the mail headers when replying,
		//to avoid confusion, always append the mail id to the subject
		String subject =
			message.getString("subject") + StringPool.SPACE + mailId;
		Set<Long> sent = new HashSet<Long>();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" + mailId + ", threadId=" +
				threadId + ", categoryIds=" + categoryIds + "}");
		}

		// Threads

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				companyId, MBThread.class.getName(), threadId);

		sendEmail(
			fromName, fromAddress, subject, body, subscriptions, sent,
			replyToAddress, mailId, inReplyTo, htmlFormat);

		// Categories

		long[] categoryIdsArray = StringUtil.split(categoryIds, 0L);

		for (long categoryId : categoryIdsArray) {
			subscriptions = _subscriptionLocalService.getSubscriptions(
				companyId, MBCategory.class.getName(), categoryId);

			sendEmail(
				fromName, fromAddress, subject, body, subscriptions,
				sent, replyToAddress, mailId, inReplyTo, htmlFormat);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished sending notifications");
		}
	}

	protected void sendEmail(
		String fromName, String fromAddress, String subject,
		String body, List<Subscription> subscriptions, Set<Long> sent,
		String replyToAddress, String mailId, String inReplyTo,
		boolean htmlFormat)
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
				user = _userLocalService.getUserById(subscription.getUserId());
			}
			catch (NoSuchUserException nsue) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Subscription " + subscription.getSubscriptionId() +
						" is stale and will be deleted");
				}

				_subscriptionLocalService.deleteSubscription(
					subscription.getSubscriptionId());
			}

			if ((user == null) || !user.isActive()) {
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
			mailId, inReplyTo, htmlFormat);
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBSubscriptionNotificationMessageListener.class);

	private SubscriptionLocalService _subscriptionLocalService;
	private UserLocalService _userLocalService;
}