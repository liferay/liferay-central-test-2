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

import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.MockMBMailingListImpl;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.MBMailingListLocalService;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * <a href="MBMailingListNotificationMessageListenerTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MBMailingListNotificationMessageListenerTest extends TestCase {

	/**
	 * Tests a message that originates from a mailing list
	 *
	 * @throws Exception
	 */
	public void testReceiveFromAMailingList() throws Exception {
		_messageFromMB.put("sourceMailingList", true);
		MBMailingListNotificationMessageListener listener =
			new MBMailingListNotificationMessageListener(
				_mockMBMailingListService, _mockSender);

		_mockery.checking(
			new Expectations() {{
				never(_mockSender);
			}});
		listener.receive(_messageFromMB);

	}

	/**
	 * Tests a message that originates from the message boards with custom outbound
	 * email settings
	 *
	 * @throws Exception
	 */
	public void testReceiveWithCustomOutbound() throws Exception {
		_messageFromMB.put("sourceMailingList", false);

		MBMailingListNotificationMessageListener listener =
			new MBMailingListNotificationMessageListener(
				_mockMBMailingListService,
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService = message;
					}
				});

		final MBMailingList mailingListConfig =
			new MockMBMailingListImpl("mail-list@liferay.com", true, true);
		mailingListConfig.setOutServerPort(25);
		mailingListConfig.setOutServerName("mail.liferay.com");
		mailingListConfig.setOutUserName("userName");
		mailingListConfig.setOutPassword("password");
		_mockery.checking(
			new Expectations() {{
				one(_mockMBMailingListService).getCategoryMailingList(1);
				will(
					returnValue(mailingListConfig));
				one(_mockSender).send(
					with(equal(DestinationNames.MAIL)), with(
						any(Message.class)));
			}});

		listener.receive(_messageFromMB);

		assertNotNull(_toMailService);

		MailMessage sentToMailService =
			(MailMessage) _toMailService.getPayload();

		SMTPAccount account = sentToMailService.getSMTPAccount();
		assertNotNull(account);
		assertEquals(Account.PROTOCOL_SMTP, account.getProtocol());
		assertTrue(mailingListConfig.getOutServerPort() == account.getPort());
		assertEquals(mailingListConfig.getOutServerName(), account.getHost());
		assertEquals(mailingListConfig.getOutUserName(), account.getUser());
		assertEquals(mailingListConfig.getOutPassword(), account.getPassword());

		_assertToMailServiceMessage(mailingListConfig, sentToMailService);
	}

	/**
	 * Tests a message that originates from the message boards without any custom
	 * outbound email settings
	 *
	 * @throws Exception
	 */
	public void testReceiveWithInactiveMailingList() throws Exception {
		_messageFromMB.put("sourceMailingList", false);

		MBMailingListNotificationMessageListener listener =
			new MBMailingListNotificationMessageListener(
				_mockMBMailingListService, _mockSender);

		_mockery.checking(
			new Expectations() {{
				one(_mockMBMailingListService).getCategoryMailingList(1);
				will(
					returnValue(
						new MockMBMailingListImpl(
							"mail-list@liferay.com", false, false)));
				never(_mockSender);
			}});

		listener.receive(_messageFromMB);
	}


	/**
	 * Tests a message that originates from the message boards without any custom
	 * outbound email settings
	 *
	 * @throws Exception
	 */
	public void testReceiveWithNoCustomOutbound() throws Exception {
		_messageFromMB.put("sourceMailingList", false);

		MBMailingListNotificationMessageListener listener =
			new MBMailingListNotificationMessageListener(
				_mockMBMailingListService,
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService = message;
					}
				});

		final MBMailingList mailingListConfig =
			new MockMBMailingListImpl("mail-list@liferay.com", true, false);

		_mockery.checking(
			new Expectations() {{
				one(_mockMBMailingListService).getCategoryMailingList(1);
				will(
					returnValue(mailingListConfig));
				one(_mockSender).send(
					with(equal(DestinationNames.MAIL)), with(
						any(Message.class)));
			}});

		listener.receive(_messageFromMB);

		assertNotNull(_toMailService);

		MailMessage sentToMailService =
			(MailMessage) _toMailService.getPayload();
		assertNull(sentToMailService.getSMTPAccount());
		_assertToMailServiceMessage(mailingListConfig, sentToMailService);
	}


	protected void setUp() {
		_mockery = new JUnit4Mockery();
		_mockSender = _mockery.mock(MessageSender.class);
		_mockMBMailingListService =
			_mockery.mock(MBMailingListLocalService.class);

		_messageFromMB = new Message();
		_messageFromMB.put("companyId", 1);
		_messageFromMB.put("userId", 1);
		_messageFromMB.put("categoryIds", 1);
		_messageFromMB.put("threadId", 1);
		_messageFromMB.put("fromName", "Test Sender Name");
		_messageFromMB.put("fromAddress", "test@test.com");
		_messageFromMB.put("subject", "Test Subject");
		_messageFromMB.put("body", "Some Message Body");
		_messageFromMB.put("replyToAddress", "cdicuser@liferay.com");
		_messageFromMB.put("mailId", "<mb.123.4567@.liferay.com>");
		_messageFromMB.put("inReplyTo", "");
		_messageFromMB.put("htmlFormat", false);
	}

	private void _assertToMailServiceMessage(
		MBMailingList mailingListConfig, MailMessage sentToMailService) {
		assertEquals(
			_messageFromMB.get("fromName"),
			sentToMailService.getFrom().getPersonal());
		assertEquals(
			_messageFromMB.get("fromAddress"),
			sentToMailService.getFrom().getAddress());
		assertEquals(
			_messageFromMB.get("subject") + StringPool.SPACE +
			_messageFromMB.get("mailId"),
			sentToMailService.getSubject());
		assertEquals(_messageFromMB.get("body"), sentToMailService.getBody());
		assertTrue(sentToMailService.getReplyTo().length == 1);
		assertEquals(
			_messageFromMB.get("replyToAddress"),
			sentToMailService.getReplyTo()[0].getAddress());
		assertEquals(
			_messageFromMB.get("replyToAddress"),
			sentToMailService.getReplyTo()[0].getPersonal());
		assertFalse(sentToMailService.getHTMLFormat());
		assertTrue(sentToMailService.getBulkAddresses().length == 1);
		assertEquals(
			mailingListConfig.getEmailAddress(),
			sentToMailService.getBulkAddresses()[0].getAddress());
		assertEquals(
			mailingListConfig.getEmailAddress(),
			sentToMailService.getBulkAddresses()[0].getPersonal());
		assertEquals(
			_messageFromMB.get("inReplyTo"), sentToMailService.getInReplyTo());
		assertEquals(
			_messageFromMB.get("mailId"), sentToMailService.getMessageId());
	}

	private JUnit4Mockery _mockery;
	private MBMailingListLocalService _mockMBMailingListService;
	private Message _messageFromMB;
	private Message _toMailService;
	private MessageSender _mockSender;
}