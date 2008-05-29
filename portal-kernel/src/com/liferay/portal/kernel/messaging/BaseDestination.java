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
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <a href="BaseDestination.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public abstract class BaseDestination implements Destination {

	public BaseDestination(String name) {
		this(name, _WORKERS_CORE_SIZE, _WORKERS_MAX_SIZE);
	}

	public BaseDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		_name = name;
		_workersCoreSize = workersCoreSize;
		_workersMaxSize = workersMaxSize;

		open();
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

	protected void doClose() {
		if (!_threadPoolExecutor.isShutdown() &&
			!_threadPoolExecutor.isTerminating()) {

			_threadPoolExecutor.shutdown();
		}
	}

	protected void doOpen() {
		if ((_threadPoolExecutor == null) || _threadPoolExecutor.isShutdown()) {
			_threadPoolExecutor = new ThreadPoolExecutor(
				_workersCoreSize, _workersMaxSize, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new NamedThreadFactory(getName(), Thread.NORM_PRIORITY));
		}
	}

	protected ThreadPoolExecutor getThreadPoolExecutor() {
		return _threadPoolExecutor;
	}

	protected int getWorkersCoreSize() {
		return _workersCoreSize;
	}

	protected int getWorkersMaxSize() {
		return _workersMaxSize;
	}

	private static final int _WORKERS_CORE_SIZE = 5;

	private static final int _WORKERS_MAX_SIZE = 10;

	private static Log _log = LogFactoryUtil.getLog(BaseDestination.class);

	private String _name;
	private ThreadPoolExecutor _threadPoolExecutor;
	private int _workersCoreSize;
	private int _workersMaxSize;

}