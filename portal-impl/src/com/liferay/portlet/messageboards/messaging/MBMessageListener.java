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

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.util.BBCodeUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageListener implements MessageListener {

	public void receive(Object message) {
		throw new UnsupportedOperationException();
	}

	public void receive(String message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	public void doReceive(String message) throws Exception {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		long companyId = jsonObj.getLong("companyId");
		long userId = jsonObj.getLong("userId");
		String categoryIds = jsonObj.getString("categoryIds");
		String threadId = jsonObj.getString("threadId");
		String fromName = jsonObj.getString("fromName");
		String fromAddress = jsonObj.getString("fromAddress");
		String subject = jsonObj.getString("subject");
		String body = jsonObj.getString("body");
		String replyToAddress = jsonObj.getString("replyToAddress");
		String mailId = jsonObj.getString("mailId");
		String inReplyTo = jsonObj.getString("inReplyTo");
		boolean htmlFormat = jsonObj.getBoolean("htmlFormat");

		Set<Long> sent = new HashSet<Long>();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" + mailId + ", threadId=" +
					threadId + ", categoryIds=" + categoryIds + "}");
		}

		// Threads

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, MBThread.class.getName(),
				GetterUtil.getLong(threadId));

		sendEmail(
			userId, fromName, fromAddress, subject, body, subscriptions, sent,
			replyToAddress, mailId, inReplyTo, htmlFormat);

		// Categories

		long[] categoryIdsArray = StringUtil.split(categoryIds, 0L);

		for (long categoryId : categoryIdsArray) {
			subscriptions = SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, MBCategory.class.getName(), categoryId);

			sendEmail(
				userId, fromName, fromAddress, subject, body, subscriptions,
				sent, replyToAddress, mailId, inReplyTo, htmlFormat);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished sending notifications");
		}
	}

	protected void sendEmail(
			long userId, String fromName, String fromAddress, String subject,
			String body, List<Subscription> subscriptions, Set<Long> sent,
			String replyToAddress, String mailId, String inReplyTo,
			boolean htmlFormat)
		throws Exception {

		List<InternetAddress> addresses =
			new ArrayList<InternetAddress>();

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
				user = UserLocalServiceUtil.getUserById(
					subscription.getUserId());
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

			InternetAddress userAddress = new InternetAddress(
				user.getEmailAddress(), user.getFullName());

			addresses.add(userAddress);
		}

		try {
			InternetAddress[] bulkAddresses = addresses.toArray(
				new InternetAddress[addresses.size()]);

			if (bulkAddresses.length == 0) {
				return;
			}

			InternetAddress from = new InternetAddress(fromAddress, fromName);

			InternetAddress[] to = new InternetAddress[] {
				new InternetAddress(replyToAddress, replyToAddress)};

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
				from, to, null, null, bulkAddresses, curSubject, curBody,
				htmlFormat);

			message.setMessageId(mailId);
			message.setInReplyTo(inReplyTo);
			message.setReplyTo(new InternetAddress[] {replyTo});

			MailServiceUtil.sendEmail(message);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactory.getLog(MBMessageListener.class);

}