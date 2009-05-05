/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ConcurrentHashSet;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <a href="IteratorDispatcherDestination.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 *
 */
public abstract class IteratorDispatcherDestination extends BaseDestination {

	public IteratorDispatcherDestination(String name) {
		super(name);
	}

	public IteratorDispatcherDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		super(name, workersCoreSize, workersMaxSize);
	}

	public void register(MessageListener listener) {
		listener = new InvokerMessageListener(listener);

		_listeners.add(listener);
	}

	public void send(Message message) {
		if (_listeners.size() == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("No listeners for destination " + getName());
			}

			return;
		}

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		if (threadPoolExecutor.isShutdown()) {
			throw new IllegalStateException(
				"Destination " + getName() + " is shutdown and cannot " +
					"receive more messages");
		}

		dispatch(_listeners.iterator(), message);
	}

	public boolean unregister(MessageListener listener) {
		listener = new InvokerMessageListener(listener);

		return _listeners.remove(listener);
	}

	public int getListenerCount() {
		return _listeners.size();
	}

	protected abstract void dispatch(
		Iterator<MessageListener> listenersItr, Message message);

	private static Log _log =
		LogFactoryUtil.getLog(IteratorDispatcherDestination.class);

	private Set<MessageListener> _listeners =
		new ConcurrentHashSet<MessageListener>();

}