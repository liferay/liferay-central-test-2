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

import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.MockSubscriptionImpl;
import com.liferay.portal.model.MockUserImpl;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * <a href="MBSubscriptionNotificationMessageListenerTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MBSubscriptionNotificationMessageListenerTest extends TestCase {

	/**
	 * Tests to ensure if a message that originates from a mailing list,
	 * subscribers will still be notified
	 *
	 * @throws Exception
	 */
	public void testReceiveFromMailingList() throws Exception {
		_messageFromMB.put("sourceMailingList", true);
		testReceiveWithThreadAndCategorySubscriptions();
	}

	/**
	 * Tests to ensure category subscribers will be notified
	 *
	 * @throws Exception
	 */
	public void testReceiveWithCategorySubscriptions()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService.add(message);
					}
				}, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "2");
		final List<Subscription> categorySubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 2, 2)
				});
		final MockUserImpl categorySubscriber = new MockUserImpl(
			2, "test2@liferay.com", "Joe2", "", "Bloggs2", true);

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(new ArrayList<Subscription>()));

				never(_userLocalService).getUserById(
					with(any(Long.class)));

				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

				exactly(
					StringUtil.split(
						_messageFromMB.getString("categoryIds")).length).of(
					_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(categorySubscriptions));

				exactly(categorySubscriptions.size()).of(
					_userLocalService).getUserById(with(any(Long.class)));
				will(
					returnValue(categorySubscriber));
				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

			}});

		listener.receive(_messageFromMB);

		assertTrue(_toMailService.size() == 1);

		MailMessage toCategorySubscriber =
			(MailMessage) _toMailService.get(0).getPayload();
		assertNull(toCategorySubscriber.getSMTPAccount());

		_assertToMailServiceMessage(toCategorySubscriber);
		assertEquals(
			categorySubscriber.getEmailAddress(),
			toCategorySubscriber.getBulkAddresses()[0].getAddress());
		assertEquals(
			categorySubscriber.getFullName(),
			toCategorySubscriber.getBulkAddresses()[0].getPersonal());

	}

	/**
	 * Tests a message that originates from the message boards with custom
	 * outbound email settings
	 *
	 * @throws Exception
	 */
	public void testReceiveWithInactiveUser()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				_mockSender, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "");
		final List<Subscription> threadSubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});
		final MockUserImpl threadSubscriber = new MockUserImpl(
			1, "test@liferay.com", "Joe", "", "Bloggs", false);

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(threadSubscriptions));

				exactly(threadSubscriptions.size()).of(_userLocalService)
					.getUserById(
						with(any(Long.class)));
				will(
					returnValue(threadSubscriber));

				one(_subscriptionLocalService).deleteSubscription(
					with(equal(threadSubscriber.getUserId())));

				never(_mockSender);
				
				never(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));

			}});

		listener.receive(_messageFromMB);
	}

	/**
	 * Tests to ensure thread subscribers will be notified
	 *
	 * @throws Exception
	 */
	public void testReceiveWithThreadSubscription()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService.add(message);
					}
				}, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "");
		final List<Subscription> threadSubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});
		final MockUserImpl threadSubscriber = new MockUserImpl(
			1, "test@liferay.com", "Joe", "", "Bloggs", true);

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(threadSubscriptions));

				exactly(threadSubscriptions.size()).of(_userLocalService)
					.getUserById(
						with(any(Long.class)));
				will(
					returnValue(threadSubscriber));

				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

				never(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));
			}});

		listener.receive(_messageFromMB);

		assertTrue(_toMailService.size() == 1);
		MailMessage sentToMailService =
			(MailMessage) _toMailService.get(0).getPayload();

		assertNull(sentToMailService.getSMTPAccount());

		_assertToMailServiceMessage(sentToMailService);
		assertEquals(
			threadSubscriber.getEmailAddress(),
			sentToMailService.getBulkAddresses()[0].getAddress());
		assertEquals(
			threadSubscriber.getFullName(),
			sentToMailService.getBulkAddresses()[0].getPersonal());

	}

	/**
	 * Tests to ensure both thread and category subscribers will be notified
	 *
	 * @throws Exception
	 */
	public void testReceiveWithThreadAndCategorySubscriptions()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService.add(message);
					}
				}, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "2");
		final List<Subscription> threadSubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});
		final MockUserImpl threadSubscriber = new MockUserImpl(
			1, "test@liferay.com", "Joe", "", "Bloggs", true);
		final List<Subscription> categorySubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 2, 2)
				});
		final MockUserImpl categorySubscriber = new MockUserImpl(
			2, "test2@liferay.com", "Joe2", "", "Bloggs2", true);

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(threadSubscriptions));

				exactly(threadSubscriptions.size()).of(_userLocalService)
					.getUserById(
						with(any(Long.class)));
				will(
					returnValue(threadSubscriber));

				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

				exactly(
					StringUtil.split(
						_messageFromMB.getString("categoryIds")).length).of(
					_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(categorySubscriptions));

				exactly(categorySubscriptions.size()).of(
					_userLocalService).getUserById(with(any(Long.class)));
				will(
					returnValue(categorySubscriber));
				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

			}});

		listener.receive(_messageFromMB);

		assertTrue(_toMailService.size() == 2);

		MailMessage toThreadSubscriber =
			(MailMessage) _toMailService.get(0).getPayload();
		assertNull(toThreadSubscriber.getSMTPAccount());

		_assertToMailServiceMessage(toThreadSubscriber);
		assertEquals(
			threadSubscriber.getEmailAddress(),
			toThreadSubscriber.getBulkAddresses()[0].getAddress());
		assertEquals(
			threadSubscriber.getFullName(),
			toThreadSubscriber.getBulkAddresses()[0].getPersonal());

		MailMessage toCategorySubscriber =
			(MailMessage) _toMailService.get(1).getPayload();
		assertNull(toCategorySubscriber.getSMTPAccount());

		_assertToMailServiceMessage(toCategorySubscriber);
		assertEquals(
			categorySubscriber.getEmailAddress(),
			toCategorySubscriber.getBulkAddresses()[0].getAddress());
		assertEquals(
			categorySubscriber.getFullName(),
			toCategorySubscriber.getBulkAddresses()[0].getPersonal());

	}

	/**
	 * Tests to ensure both thread and category subscribers will be notified,
	 * but this time without any subscribers for categories
	 *
	 * @throws Exception
	 */
	public void testReceiveWithThreadAndCategorySubscriptionsNoCatSubscriber()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService.add(message);
					}
				}, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "2");
		final List<Subscription> threadSubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});
		final MockUserImpl threadSubscriber = new MockUserImpl(
			1, "test@liferay.com", "Joe", "", "Bloggs", true);

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(threadSubscriptions));

				exactly(threadSubscriptions.size()).of(_userLocalService)
					.getUserById(
						with(any(Long.class)));
				will(
					returnValue(threadSubscriber));

				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

				exactly(
					StringUtil.split(
						_messageFromMB.getString("categoryIds")).length).of(
					_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(new ArrayList<Subscription>()));

				never(_userLocalService).getUserById(with(any(Long.class)));
				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

			}});

		listener.receive(_messageFromMB);

		assertTrue(_toMailService.size() == 1);

		MailMessage toThreadSubscriber =
			(MailMessage) _toMailService.get(0).getPayload();
		assertNull(toThreadSubscriber.getSMTPAccount());

		_assertToMailServiceMessage(toThreadSubscriber);
		assertEquals(
			threadSubscriber.getEmailAddress(),
			toThreadSubscriber.getBulkAddresses()[0].getAddress());
		assertEquals(
			threadSubscriber.getFullName(),
			toThreadSubscriber.getBulkAddresses()[0].getPersonal());

	}

	/**
	 * Tests to ensure that if user listens to both thread and category, he will
	 * only receive one notification
	 *
	 * @throws Exception
	 */
	public void testWithDuplicateCategorySubscription()
		throws Exception {

		MBSubscriptionNotificationMessageListener listener =
			new MBSubscriptionNotificationMessageListener(
				new MessageSender() {
					public void send(String destination, Message message) {
						_toMailService.add(message);
					}
				}, _subscriptionLocalService, _userLocalService);

		_messageFromMB.put("categoryIds", "2");
		final List<Subscription> threadSubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});
		final MockUserImpl threadSubscriber = new MockUserImpl(
			1, "test@liferay.com", "Joe", "", "Bloggs", true);
		final List<Subscription> categorySubscriptions =
			Arrays.asList(
				new Subscription[]{
					new MockSubscriptionImpl(1, 1, 1)
				});

		_mockery.checking(
			new Expectations() {{
				one(_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBThread.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(threadSubscriptions));

				exactly(threadSubscriptions.size()).of(_userLocalService)
					.getUserById(
						with(any(Long.class)));
				will(
					returnValue(threadSubscriber));

				never(_subscriptionLocalService).deleteSubscription(
					with(any(Long.class)));

				exactly(
					StringUtil.split(
						_messageFromMB.getString("categoryIds")).length).of(
					_subscriptionLocalService).getSubscriptions(
					with(any(Long.class)),
					with(equal(MBCategory.class.getName())),
					with(any(Long.class)));
				will(
					returnValue(categorySubscriptions));

				never(_userLocalService).getUserById(
					with(any(Long.class)));

			}});

		listener.receive(_messageFromMB);

		assertTrue(_toMailService.size() == 1);

		MailMessage toThreadSubscriber =
			(MailMessage) _toMailService.get(0).getPayload();
		assertNull(toThreadSubscriber.getSMTPAccount());

		_assertToMailServiceMessage(toThreadSubscriber);
		assertEquals(
			threadSubscriber.getEmailAddress(),
			toThreadSubscriber.getBulkAddresses()[0].getAddress());
		assertEquals(
			threadSubscriber.getFullName(),
			toThreadSubscriber.getBulkAddresses()[0].getPersonal());
	}


	protected void setUp() {
		_mockery = new JUnit4Mockery();
		_mockSender = _mockery.mock(MessageSender.class);
		_subscriptionLocalService =
			_mockery.mock(SubscriptionLocalService.class);
		_userLocalService =
			_mockery.mock(UserLocalService.class);

		_messageFromMB = new Message();
		_messageFromMB.put("companyId", 1);
		_messageFromMB.put("userId", 1);
		_messageFromMB.put("categoryIds", "");
		_messageFromMB.put("threadId", 1);
		_messageFromMB.put("fromName", "Test Sender Name");
		_messageFromMB.put("fromAddress", "test@test.com");
		_messageFromMB.put("subject", "Test Subject");
		_messageFromMB.put("body", "Some Message Body");
		_messageFromMB.put("replyToAddress", "cdicuser@liferay.com");
		_messageFromMB.put("mailId", "<mb.123.4567@.liferay.com>");
		_messageFromMB.put("inReplyTo", "");
		_messageFromMB.put("htmlFormat", false);
		_messageFromMB.put("sourceMailingList", false);
	}

	private void _assertToMailServiceMessage(MailMessage sentToMailService) {
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
			_messageFromMB.get("inReplyTo"), sentToMailService.getInReplyTo());
		assertEquals(
			_messageFromMB.get("mailId"), sentToMailService.getMessageId());
	}


	private JUnit4Mockery _mockery;
	private SubscriptionLocalService _subscriptionLocalService;
	private UserLocalService _userLocalService;
	private Message _messageFromMB;
	private List<Message> _toMailService = new ArrayList<Message>();
	private MessageSender _mockSender;

}
