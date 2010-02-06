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

package com.liferay.portal.kernel.messaging;

/**
 * <a href="InvokerMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class InvokerMessageListener implements MessageListener {

	public InvokerMessageListener(MessageListener messageListener) {
		this(
			messageListener,
			Thread.currentThread().getContextClassLoader());
	}

	public InvokerMessageListener(
		MessageListener messageListener, ClassLoader classLoader) {

		_messageListener = messageListener;
		_classLoader = classLoader;
	}

	public boolean equals(Object obj) {
		InvokerMessageListener messageListenerInvoker =
			(InvokerMessageListener)obj;

		return _messageListener.equals(
			messageListenerInvoker.getMessageListener());
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public MessageListener getMessageListener() {
		return _messageListener;
	}

	public int hashCode() {
		return _messageListener.hashCode();
	}

	public void receive(Message message) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_messageListener.receive(message);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private MessageListener _messageListener;
	private ClassLoader _classLoader;

}