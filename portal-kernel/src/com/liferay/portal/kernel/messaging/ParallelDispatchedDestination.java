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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <a href="ParallelDispatchedDestination.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 *
 */
public class ParallelDispatchedDestination extends BaseDestination {

	public ParallelDispatchedDestination(
		String name, int numWorkers, int maxWorkers) {

		super(name);

		_numWorkers = numWorkers;
		_maxWorkers = maxWorkers;

		open();
	}

	protected void dispatch(MessageListener[] listeners, String message) {
		if (executor.isShutdown()) {
			throw new IllegalStateException(
				"Destination " + getName() + " is shutdown and cannot " +
					"receive more messages");
		}

		doDispatch(listeners, message);
	}

	protected void doClose() {
		if (!executor.isShutdown() && !executor.isTerminating()) {
			executor.shutdown();
		}
	}

	protected void doDispatch(
		MessageListener[] listeners, final String message) {

		for (final MessageListener listener : listeners) {
			Runnable runnable = new Runnable() {

				public void run() {
					listener.receive(message);
				}

			};

			executor.execute(runnable);
		}
	}

	protected void doOpen() {
		if ((executor == null) || executor.isShutdown()) {
			executor = new ThreadPoolExecutor(
				_numWorkers, _maxWorkers, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new NamedThreadFactory(getName(), Thread.NORM_PRIORITY));
		}
	}

	protected void resetDispatcher(int length) {
		executor.setCorePoolSize(length);
		executor.setMaximumPoolSize(length + _MAX_SIZE_PADDING);
	}

	protected ThreadPoolExecutor executor;

	private static final int _MAX_SIZE_PADDING = 5;

	private int _numWorkers;
	private int _maxWorkers;

}