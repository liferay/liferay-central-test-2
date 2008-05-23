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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

/**
 * <a href="SynchronizedMessageSender.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SynchronizedMessageSender implements MessageListener {

	public static final long TIMEOUT = 1000;

	public SynchronizedMessageSender() {
		_replyTo = _SYNC_MESSAGES_PREFIX + PortalUUIDUtil.generate();

		_destination = new SerialDispatchedDestination(_replyTo);
		_destination.register(this);
	}

	public synchronized String sendMessage(String destination, String message)
		throws SystemException {

		String messageId = null;

		return sendMessage(destination, messageId, message);
	}

	public synchronized String sendMessage(
			String destination, String messageId, String message)
		throws SystemException {

		try {
			MessageBusUtil.addDestination(_destination);

			StringMaker sm = new StringMaker();
			sm.append(StringPool.OPEN_CURLY_BRACE);

			sm.append(StringPool.QUOTE);
			sm.append("messageBody");
			sm.append(StringPool.QUOTE);
			sm.append(StringPool.COLON);
			sm.append(StringPool.QUOTE);
			sm.append(message);
			sm.append(StringPool.QUOTE);

			sm.append(StringPool.COMMA);

			sm.append(StringPool.QUOTE);
			sm.append("replyTo");
			sm.append(StringPool.QUOTE);
			sm.append(StringPool.COLON);
			sm.append(StringPool.QUOTE);
			sm.append(_replyTo);
			sm.append(StringPool.QUOTE);

			sm.append(StringPool.CLOSE_CURLY_BRACE);

			if (messageId == null) {
				MessageBusUtil.sendMessage(destination, sm.toString());
			}
			else {
				MessageBusUtil.sendMessage(
					destination, messageId, sm.toString());
			}

			long startTime = System.currentTimeMillis();

			wait(TIMEOUT);

			if ((System.currentTimeMillis() - startTime) > TIMEOUT) {
				throw new SystemException("Reply exceeded timeout");
			}
		}
		catch (InterruptedException ie) {
			throw new SystemException(ie);
		}
		finally {
			MessageBusUtil.removeDestination(_replyTo);
		}

		return _message;
	}

	public synchronized void receive(String messageId, String message) {
		_message = message;

		notify();
	}

	private static final String _SYNC_MESSAGES_PREFIX =
		"liferay/messaging/sync_messages/";

	private Destination _destination;
	private String _message;
	private String _replyTo;

}