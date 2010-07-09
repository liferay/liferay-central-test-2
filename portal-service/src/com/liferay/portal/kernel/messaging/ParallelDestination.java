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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.ThreadLocalRegistry;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * Destination that delivers a message to a list of message listeners in
 * parallel.
 * </p>
 *
 * @author Michael C. Han
 */
public class ParallelDestination extends BaseDestination {

	public ParallelDestination() {
	}

	/**
	 * @deprecated
	 */
	public ParallelDestination(String name) {
		super(name);
	}

	/**
	 * @deprecated
	 */
	public ParallelDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		super(name, workersCoreSize, workersMaxSize);
	}

	protected void dispatch(
		Set<MessageListener> messageListeners, final Message message) {

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		for (final MessageListener messageListener : messageListeners) {
			Runnable runnable = new Runnable() {

				public void run() {
					try {
						messageListener.receive(message);
					}
					finally {
						ThreadLocalRegistry.resetThreadLocals();
					}
				}

			};

			threadPoolExecutor.execute(runnable);
		}
	}

}