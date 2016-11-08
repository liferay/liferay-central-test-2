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

package com.liferay.websocket.whiteboard.test.client;

import java.io.IOException;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
@ClientEndpoint
public class TestWebSocketClient {

	public void await(long time, TimeUnit timeUnit)
		throws InterruptedException {

		_countDownLatch.await(time, timeUnit);
	}

	public long getMissingMessages() {
		return _countDownLatch.getCount();
	}

	public void initExpectedMessages(int expectedMessages) {
		_countDownLatch = new CountDownLatch(expectedMessages);
	}

	@OnOpen
	public void onOpen(Session session) {
		_session = session;
	}

	@OnMessage
	public void onText(String text, Session session) {
		if (_countDownLatch == null) {
			throw new RuntimeException("Count down latch is null");
		}

		_countDownLatch.countDown();

		_texts.add(text);
	}

	public String popReceivedTexts() {
		return _texts.pop();
	}

	public void sendText(String text) {
		try {
			Basic basic = _session.getBasicRemote();

			basic.sendText(text);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private CountDownLatch _countDownLatch;
	private Session _session;
	private final Stack<String> _texts = new Stack<>();

}