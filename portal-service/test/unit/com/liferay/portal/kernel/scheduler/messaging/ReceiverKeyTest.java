/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.SerializableUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author László Csontos
 */
public class ReceiverKeyTest {

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testSerializeToBytes() {
		Message message = createMessage();

		byte[] bytes = SerializableUtil.serialize(message);

		message = (Message)SerializableUtil.deserialize(bytes);

		assertReceiverKey(message);
	}

	@Test
	public void testSerializeToJSON() {
		Message message = createMessage();

		String json = JSONFactoryUtil.serialize(message);

		message = (Message)JSONFactoryUtil.deserialize(json);

		assertReceiverKey(message);
	}

	protected void assertReceiverKey(Message message) {
		Assert.assertNotNull(message);

		ReceiverKey receiverKey = (ReceiverKey)message.get(
			SchedulerEngine.RECEIVER_KEY);

		Assert.assertEquals(_RECEIVER_KEY, receiverKey);
	}

	protected Message createMessage() {
		Message message = new Message();

		message.put(SchedulerEngine.RECEIVER_KEY, _RECEIVER_KEY);

		return message;
	}

	private static final ReceiverKey _RECEIVER_KEY = new ReceiverKey(
		StringUtil.randomString(), StringUtil.randomString());

}