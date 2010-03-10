/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <a href="FutureResult.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class FutureResult<V> implements Future<V> {

	public boolean cancel(boolean mayInterruptIfRunning) {

		if (_cancelled || isDone()) {
			return false;
		}

		_cancelled = true;

		return true;
	}

	public V get() throws InterruptedException, ExecutionException {

		if (_cancelled) {
			throw new CancellationException();
		}

		_countDownLatch.await();

		if (_exception != null) {
			throw new ExecutionException(_exception);
		}

		return _result;
	}

	public V get(long timeout, TimeUnit unit)
		throws InterruptedException, ExecutionException, TimeoutException {

		if (_cancelled) {
			throw new CancellationException();
		}

		if (_countDownLatch.await(timeout, unit)) {
			if (_exception != null) {
				throw new ExecutionException(_exception);
			}
			return _result;
		}
		else {
			throw new TimeoutException();
		}
	}

	public boolean hasException() {
		return _exception != null;
	}

	public boolean isCancelled() {
		return _cancelled;
	}

	public boolean isDone() {

		if ((_countDownLatch.getCount() == 0) || _cancelled) {
			return true;
		}

		return false;
	}

	public void setException(Exception exception) {
		_exception = exception;
		_countDownLatch.countDown();
	}

	public void setResult(V result) {
		_result = result;
		_countDownLatch.countDown();
	}

	private boolean _cancelled = false;
	private CountDownLatch _countDownLatch = new CountDownLatch(1);
	private Exception _exception;
	private V _result;

}