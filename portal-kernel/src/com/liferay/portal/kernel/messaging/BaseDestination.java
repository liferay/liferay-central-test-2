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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="BaseDestination.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public abstract class BaseDestination implements Destination {

	public BaseDestination(String name) {
		_name = name;
		_listeners = new MessageListener[0];
	}

	public synchronized void close() {
		doClose();
	}

	public String getName() {
		return _name;
	}

	public synchronized void open() {
		doOpen();
	}

	public synchronized void register(MessageListener listener) {
		Set<MessageListener> listeners = new HashSet<MessageListener>(
			Arrays.asList(_listeners));

		listeners.add(new MessageListenerWrapper(listener));

		_listeners = listeners.toArray(new MessageListener[listeners.size()]);
	}

	public void send(String message) {
		if (_listeners.length == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("No listeners for destination " + _name);
			}

			return;
		}

		dispatch(_listeners, message);
	}

	public synchronized boolean unregister(MessageListener listener) {
		List<MessageListener> listeners = Arrays.asList(_listeners);

		boolean value = listeners.remove(listener);

		if (value) {
			_listeners = listeners.toArray(
				new MessageListener[listeners.size()]);
		}

		return value;
	}

	protected abstract void dispatch(
		MessageListener[] listeners, String message);

	protected abstract void doClose();

	protected abstract void doOpen();

	private static Log _log = LogFactoryUtil.getLog(BaseDestination.class);

	private String _name;
	private MessageListener[] _listeners;

	private class MessageListenerWrapper implements MessageListener {

		public MessageListenerWrapper(MessageListener messageListener) {
			this(
				messageListener,
				Thread.currentThread().getContextClassLoader());
		}

		public MessageListenerWrapper(
			MessageListener messageListener, ClassLoader classLoader) {

			_messageListener = messageListener;
			_classLoader = classLoader;
		}

		public void receive(String message) {
			ClassLoader contextClassLoader =
				Thread.currentThread().getContextClassLoader();

			Thread.currentThread().setContextClassLoader(_classLoader);

			try {
				_messageListener.receive(message);
			}
			finally {
				Thread.currentThread().setContextClassLoader(
					contextClassLoader);
			}
		}

		public boolean equals(Object obj) {
			return _messageListener.equals(obj);
		}

		public int hashCode() {
			return _messageListener.hashCode();
		}

		private MessageListener _messageListener;
		private ClassLoader _classLoader;

	}

}