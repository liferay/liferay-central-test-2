package com.liferay.portlet.messageboards.messaging.notification;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.MBMailingListLocalService;

import javax.mail.internet.InternetAddress;

/**
 * <a href="MailingListMBMessageListener.java.html"><b><i>View
 * Source</i></b></a>
 * <p/>
 *
 * Notify the configured mailing lists (if any) of a message
 *
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 * @author Michael C. Han
 */
public class MBMailingListNotificationMessageListener
	extends BaseMBNotificationMessageListener {


	public MBMailingListNotificationMessageListener(
		MBMailingListLocalService mbMailingListLocalService,
		MessageSender mailMessageSender) {

		super(mailMessageSender);
		_mbMailingListLocalService = mbMailingListLocalService;
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

		boolean sourceMailingList = message.getBoolean("sourceMailingList");
		if (sourceMailingList) {
			//if we received this email from a mailing list, do not retransmit
			return;
		}

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

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending mailing list notifications for " +
				"{mailId=" + mailId + ", threadId=" +
				threadId + ", categoryIds=" + categoryIds + "}");
		}

		// Categories

		long[] categoryIdsArray = StringUtil.split(categoryIds, 0L);

		// Mailing list

		for (long categoryId : categoryIdsArray) {
			MBMailingList mailingList;
			try {
				mailingList =
					_mbMailingListLocalService.getCategoryMailingList(
						categoryId);
				if (!mailingList.isActive()) {
					continue;
				}
			}
			catch (NoSuchMailingListException nsmle) {
				continue;
			}

			notifyMailingList(
				mailingList, fromAddress, fromName, subject,
				body, replyToAddress, mailId, inReplyTo, htmlFormat);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished notifying mailing lists");
		}
	}

	protected void notifyMailingList(
			MBMailingList mailingList, String fromAddress, String fromName,
			String subject, String body, String replyToAddress, String mailId,
			String inReplyTo, boolean htmlFormat)
		throws Exception {

		InternetAddress[] bulkAddresses = new InternetAddress[]{
			new InternetAddress(mailingList.getEmailAddress(),
								mailingList.getEmailAddress())
		};

		SMTPAccount account = null;

		if (mailingList.isOutCustom()) {
			String protocol = Account.PROTOCOL_SMTP;

			if (mailingList.isOutUseSSL()) {
				protocol = Account.PROTOCOL_SMTPS;
			}

			account = (SMTPAccount) Account.getInstance(
				protocol, mailingList.getOutServerPort());

			account.setHost(mailingList.getOutServerName());
			account.setUser(mailingList.getOutUserName());
			account.setPassword(mailingList.getOutPassword());
		}

		sendMail(
			fromAddress, fromName, bulkAddresses, subject, body, replyToAddress,
			mailId, inReplyTo, htmlFormat, account);
	}

	

	private static Log _log = LogFactoryUtil.getLog(
		MBMailingListNotificationMessageListener.class);

	private MBMailingListLocalService _mbMailingListLocalService;
}