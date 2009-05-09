package com.liferay.portlet.messageboards.messaging.notification;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.messageboards.util.BBCodeUtil;

import javax.mail.internet.InternetAddress;

/**
 * <a href="BaseMBNotificationMessageListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public abstract class BaseMBNotificationMessageListener
	implements MessageListener {

	protected BaseMBNotificationMessageListener(MessageSender mailMessageSender) {
		_mailMessageSender = mailMessageSender;
	}

	protected void sendMail(
			String fromAddress, String fromName, InternetAddress[] bulkAddresses,
			String subject, String body, String replyToAddress, String mailId,
			String inReplyTo, boolean htmlFormat) {
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
				new String[]{
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[]{
					replyToAddress,
					replyToAddress
				});

			String curBody = StringUtil.replace(
				body,
				new String[]{
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[]{
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

			MailMessage mailMessage = new MailMessage(
				from, to, curSubject, curBody, htmlFormat);

			mailMessage.setBulkAddresses(bulkAddresses);
			mailMessage.setMessageId(mailId);
			mailMessage.setInReplyTo(inReplyTo);
			mailMessage.setReplyTo(new InternetAddress[]{replyTo});
			mailMessage.setSMTPAccount(account);

			Message message = new Message();

			message.setPayload(mailMessage);

			_mailMessageSender.send(DestinationNames.MAIL, message);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(BaseMBNotificationMessageListener.class);
	private MessageSender _mailMessageSender;
}
