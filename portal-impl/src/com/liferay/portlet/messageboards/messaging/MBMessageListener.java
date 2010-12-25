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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;
import com.liferay.portlet.messageboards.util.BBCodeUtil;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class MBMessageListener extends BaseMessageListener {

	protected void doReceive(Message message) throws Exception {
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

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" + mailId + ", threadId=" +
					threadId + ", categoryIds=" + categoryIds + "}");
		}

		SubscriptionSender subscriptionSender = new MBSubscriptionSender();

		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setUserId(userId);
		subscriptionSender.setGroupId(groupId);
		subscriptionSender.setFrom(fromName, fromAddress);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setBody(body);
		subscriptionSender.setReplyToAddress(replyToAddress);
		subscriptionSender.setMailId(mailId);
		subscriptionSender.setInReplyTo(inReplyTo);
		subscriptionSender.setHtmlFormat(htmlFormat);
		subscriptionSender.setBulk(true);

		long[] categoryIdsArray = StringUtil.split(categoryIds, 0L);

		for (long categoryId : categoryIdsArray) {
			if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				categoryId = groupId;
			}

			subscriptionSender.notifyPersistedSubscribers(
				MBCategory.class.getName(), categoryId);
		}

		subscriptionSender.notifyPersistedSubscribers(
			MBThread.class.getName(), threadId);

		if (!sourceMailingList) {
			subscriptionSender.setBulk(false);

			for (long categoryId : categoryIdsArray) {
				notifyMailingList(
					groupId, categoryId, subscriptionSender,
					getMailingListSubject(subject, mailId));
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished sending notifications");
		}
	}

	protected String getMailingListSubject(String subject, String mailId) {
		return subject.concat(StringPool.SPACE).concat(mailId);
	}

	protected void notifyMailingList(
			long groupId, long categoryId,
			SubscriptionSender subscriptionSender, String subject)
		throws Exception {

		MBMailingList mailingList = null;

		try {
			mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(
				groupId, categoryId);
		}
		catch (NoSuchMailingListException nsmle) {
			return;
		}

		if (!mailingList.isActive()) {
			return;
		}

		subscriptionSender.setFrom(mailingList.getOutEmailAddress(), null);

		if (mailingList.isOutCustom()) {
			String protocol = Account.PROTOCOL_SMTP;

			if (mailingList.isOutUseSSL()) {
				protocol = Account.PROTOCOL_SMTPS;
			}

			SMTPAccount smtpAccount = (SMTPAccount)Account.getInstance(
				protocol, mailingList.getOutServerPort());

			smtpAccount.setHost(mailingList.getOutServerName());
			smtpAccount.setUser(mailingList.getOutUserName());
			smtpAccount.setPassword(mailingList.getOutPassword());

			subscriptionSender.setSMTPAccount(smtpAccount);
		}

		subscriptionSender.setSubject(subject);

		subscriptionSender.notifyRuntimeSubscribers(
			mailingList.getEmailAddress(), mailingList.getEmailAddress());
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageListener.class);

	private class MBSubscriptionSender extends SubscriptionSender {

		protected void processMailMessage(
				MailMessage mailMessage, Locale locale)
			throws Exception {

			super.processMailMessage(mailMessage, locale);

			if (htmlFormat) {
				try {
					String processedBody = BBCodeUtil.getHTML(
						mailMessage.getBody());

					mailMessage.setBody(processedBody);
				}
				catch (Exception e) {
					_log.error(
						"Could not parse message " + mailId + " " +
							e.getMessage());
				}
			}
		}

	};

}