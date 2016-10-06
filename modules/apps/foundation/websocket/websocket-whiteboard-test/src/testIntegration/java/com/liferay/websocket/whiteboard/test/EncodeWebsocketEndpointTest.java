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

package com.liferay.websocket.whiteboard.test;

import com.liferay.websocket.whiteboard.test.encode.client.EncodeWebSocketClient;
import com.liferay.websocket.whiteboard.test.encode.data.Example;

import java.net.URI;
import java.net.URL;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class EncodeWebsocketEndpointTest {

	@RunAsClient
	@Test
	public void testSendObjectAndReceiveTheSame() throws Exception {
		WebSocketContainer webSocketContainer =
			ContainerProvider.getWebSocketContainer();

		SynchronousQueue<Example> synchronousQueue = new SynchronousQueue<>();

		EncodeWebSocketClient encodeWebSocketClient = new EncodeWebSocketClient(
			synchronousQueue);

		StringBuilder sb = new StringBuilder();

		sb.append("ws://");
		sb.append(_url.getHost());
		sb.append(":");
		sb.append(_url.getPort());
		sb.append("/o/websocket/decoder");

		URI uri = new URI(sb.toString());

		webSocketContainer.connectToServer(encodeWebSocketClient, uri);

		encodeWebSocketClient.sendMessage(new Example(2, "echo"));

		Example example = synchronousQueue.poll(1, TimeUnit.HOURS);

		Assert.assertEquals("echo", example.getData());
		Assert.assertEquals(2, example.getNumber());
	}

	@ArquillianResource
	private URL _url;

}