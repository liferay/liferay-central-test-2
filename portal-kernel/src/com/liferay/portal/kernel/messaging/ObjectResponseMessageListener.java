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

/**
 * <a href="ObjectResponseMessageListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ObjectResponseMessageListener implements MessageListener {

	public ObjectResponseMessageListener(Destination destination,
										 String responseId, long timeout) {
		_responseId = responseId;
		_destination = destination;
		_timeout = timeout;
	}

	public Object send(Message message)
		throws MessageBusException {
		_destination.send(message);

		synchronized (this) {
			try {
				wait(_timeout);
			}
			catch (InterruptedException ie) {
				throw new MessageBusException(
					"Unable to receive response for request", ie);
			}

			if (_responseValue == null) {
				throw new MessageBusException(
					"No reply received for request: " + message);
			}

			return _responseValue;
		}
	}

	public void receive(Message message) {
		if (message.getMessageId().equals(_responseId)) {
			synchronized (this) {
				_responseValue = message.getPayload();
				notify();
			}
		}
	}

	private Destination _destination;
	private Object _responseValue;
	private String _responseId;
	private long _timeout;
}