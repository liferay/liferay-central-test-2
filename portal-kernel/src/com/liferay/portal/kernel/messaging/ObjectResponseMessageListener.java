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

	public ObjectResponseMessageListener(
		Destination destination, Destination responseDestination,
		String responseId, long timeout) {

		_destination = destination;
		_responseDestination = responseDestination;
		_responseId = responseId;
		_timeout = timeout;
	}

	public synchronized Object send(Message message)
		throws MessageBusException {

		message.setResponseDestination(_responseDestination.getName());
		message.setResponseId(_responseId);

		_destination.send(message);

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

	public synchronized void receive(Object message) {
		receive((Message)message);
	}

	public void receive(String message) {
		throw new UnsupportedOperationException();
	}

	protected void receive(Message message) {
		if (message.getResponseId().equals(_responseId)) {
			_responseValue = message.getResponseValue();

			notify();
		}
	}

	private Destination _destination;
	private Destination _responseDestination;
	private String _responseId;
	private long _timeout;
	private Object _responseValue;

}