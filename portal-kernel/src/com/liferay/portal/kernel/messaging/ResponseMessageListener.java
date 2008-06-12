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

import com.liferay.portal.kernel.util.StringMaker;

/**
 * <a href="ResponseMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResponseMessageListener implements MessageListener {

	public ResponseMessageListener(
		Destination destination, Destination responseDestination,
		String responseId) {

		this(destination, responseDestination, responseId, _TIMEOUT);
	}

	public ResponseMessageListener(
		Destination destination, Destination responseDestination,
		String responseId, int timeout) {

		_destination = destination;
		_responseDestination = responseDestination;
		_responseId = responseId;
		_timeout = timeout;
	}

	public synchronized String send(String message)
		throws InterruptedException {

		if (message.equals(_EMTPY_MESSAGE)) {
			StringMaker sm = new StringMaker();

			sm.append("{\"lfrResponseDestination\":\"");
			sm.append(_responseDestination.getName());
			sm.append("\",\"lfrResponseId\":\"");
			sm.append(_responseId);
			sm.append("\"}");

			message = sm.toString();
		}
		else {
			StringMaker sm = new StringMaker();

			sm.append(message.substring(0, message.length() - 1));
			sm.append(",\"lfrResponseDestination\":\"");
			sm.append(_responseDestination.getName());
			sm.append("\",\"lfrResponseId\":\"");
			sm.append(_responseId);
			sm.append("\"}");

			message = sm.toString();
		}

		_destination.send(message);

		wait(_timeout);

		return _responseMessage;
	}

	public synchronized void receive(String message) {
		if (message.indexOf(
				"\"lfrResponseId\":\"" + _responseId + "\"") != -1) {

			_responseMessage = message;

			notify();
		}
	}

	private static final String _EMTPY_MESSAGE = "{}";

	private static final int _TIMEOUT = 10000;

	private Destination _destination;
	private Destination _responseDestination;
	private String _responseId;
	private int _timeout;
	private String _responseMessage;

}