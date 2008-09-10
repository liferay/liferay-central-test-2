/*
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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.uuid.PortalUUID;

/**
 * <a href="SynchronousMessageSender.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultSynchronousMessageSender implements SynchronousMessageSender {
	public DefaultSynchronousMessageSender(MessageBus bus,
										   PortalUUID uuidGenerator,
										   long defaultTimeout) {
		_bus = bus;
		_uuidGenerator = uuidGenerator;
		_defaultTimeout = defaultTimeout;
	}

	public Object sendMessage(String destination, Message message)
	throws MessageBusException {
		return sendMessage(destination, message, _defaultTimeout);

	}

	public Object sendMessage(String destination, Message message, long timeout)
	throws MessageBusException {
		String replyTo = message.getResponseDestination();
		if (!_bus.hasDestination(replyTo)) {
			throw new MessageBusException(
				"No response destination configured: " + replyTo);
		}
		message.setDestination(destination);
		String responseId = _uuidGenerator.generate();
		message.setResponseId(responseId);
		_SynchronousMessageListener listener =
			new _SynchronousMessageListener(message, timeout);
		return listener.send();
	}

	private class _SynchronousMessageListener implements MessageListener {
		public _SynchronousMessageListener(Message message, long timeout) {
			_message = message;
			_responseId = _message.getResponseId();
			_timeout = timeout;
		}

		public Object getResults() {
			return _results;
		}

		public void receive(Message message) {
			if (!message.getResponseId().equals(_responseId)) {
				//not my message...
				return;
			}
			synchronized (this) {
				_results = message.getPayload();
				notify();
			}
		}

		public Object send()
		throws MessageBusException {
			_bus.registerMessageListener(_message.getResponseDestination(),
										 this);
			try {
				synchronized (this) {
					_bus.sendMessage(_message.getDestination(), _message);
					this.wait(_timeout);
					if (_results == null) {
						//we timed out
						throw new MessageBusException(
							"Messaging time out trying to send message" +
								_message);
					}
				}
				return _results;
			} catch (InterruptedException e) {
				throw new MessageBusException(
					"Message sending interrupted for: " + _message, e);
			} finally {
				_bus.unregisterMessageListener(_message.getResponseDestination(),
											   this);
			}
		}

		private String _responseId;
		private Object _results;
		private Message _message;
		private long _timeout;
	}

	private MessageBus _bus;
	private PortalUUID _uuidGenerator;
	private long _defaultTimeout;
}
