/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

/**
 * <a href="SynchronousMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class SynchronousMessageListener implements MessageListener {

	public SynchronousMessageListener(
		MessageBus messageBus, Message message, long timeout) {

		_messageBus = messageBus;
		_message = message;
		_timeout = timeout;
		_responseId = _message.getResponseId();
	}

	public Object getResults() {
		return _results;
	}

	public void receive(Message message) {
		if (!message.getResponseId().equals(_responseId)) {
			return;
		}

		synchronized (this) {
			_results = message.getPayload();

			notify();
		}
	}

	public Object send() throws MessageBusException {
		String destinationName = _message.getDestinationName();
		String responseDestinationName = _message.getResponseDestinationName();

		_messageBus.registerMessageListener(responseDestinationName, this);

		try {
			synchronized (this) {
				_messageBus.sendMessage(destinationName, _message);

				wait(_timeout);

				if (_results == null) {
					throw new MessageBusException(
						"No reply received for message: " + _message);
				}
			}

			return _results;
		}
		catch (InterruptedException ie) {
			throw new MessageBusException(
				"Message sending interrupted for: " + _message, ie);
		}
		finally {
			_messageBus.unregisterMessageListener(
				responseDestinationName, this);
		}
	}

	private MessageBus _messageBus;
	private Message _message;
	private long _timeout;
	private String _responseId;
	private Object _results;

}