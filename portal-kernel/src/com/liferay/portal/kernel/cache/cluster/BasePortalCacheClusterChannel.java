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

package com.liferay.portal.kernel.cache.cluster;

import com.liferay.portal.kernel.concurrent.CoalescedPipe;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <a href="BasePortalCacheClusterChannel.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public abstract class BasePortalCacheClusterChannel
	extends Thread implements PortalCacheClusterChannel, Runnable {

	public BasePortalCacheClusterChannel() {
		_dispatchThread = new Thread(
			this,
			"PortalCacheClusterChannel dispatch thread-" +
				_dispatchThreadCounter.getAndIncrement());
		_eventQueue = new CoalescedPipe<PortalCacheClusterEvent>(
			new PortalCacheClusterEventCoalesceComparator());
	}

	public void destroy() {
		_destroy = true;

		_dispatchThread.interrupt();
	}

	public abstract void dispatchEvent(PortalCacheClusterEvent event);

	public long getCoalescedEventNumber() {
		return _eventQueue.coalescedCount();
	}

	public int getPendingEventNumber() {
		return _eventQueue.pendingCount();
	}

	public long getSentEventNumber() {
		return _sentEventCounter.get();
	}

	public void run() {
		while (true) {
			try {
				if (_destroy) {
					Object[] events = _eventQueue.takeSnapshot();

					for (Object event : events) {
						dispatchEvent((PortalCacheClusterEvent)event);

						_sentEventCounter.incrementAndGet();
					}

					break;
				}
				else {
					try {
						PortalCacheClusterEvent portalCacheClusterEvent =
							_eventQueue.take();

						dispatchEvent(portalCacheClusterEvent);

						_sentEventCounter.incrementAndGet();
					}
					catch (InterruptedException ie) {
					}
				}
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn("Please fix the unexpected throwable", t);
				}
			}
		}
	}

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		if (_started == false) {
			synchronized (this) {
				if (_started == false) {
					_dispatchThread.start();

					_started = true;
				}
			}
		}

		try {
			_eventQueue.put(portalCacheClusterEvent);
		}
		catch (InterruptedException ie) {
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(BasePortalCacheClusterChannel.class);

	private static AtomicInteger _dispatchThreadCounter = new AtomicInteger(0);

	private volatile boolean _destroy = false;
	private final Thread _dispatchThread;
	private final CoalescedPipe<PortalCacheClusterEvent> _eventQueue;
	private final AtomicLong _sentEventCounter = new AtomicLong(0);
	private volatile boolean _started = false;

}