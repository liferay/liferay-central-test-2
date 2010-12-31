/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalRegistryTest extends TestCase {

	public void testThreadIsolation() throws Exception {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

		final Thread parentThread = Thread.currentThread();

		Thread childThread = new Thread("Child Thread") {

			public void run() {
				try {
					ThreadLocal<Boolean> threadLocal =
						new AutoResetThreadLocal<Boolean>(
							ThreadLocalRegistryTest.class.getName(),
							Boolean.FALSE);

					ThreadLocal<?>[] threadLocals =
						ThreadLocalRegistry.captureSnapshot();

					assertEquals(0, threadLocals.length);

					// Trigger to register

					assertFalse(threadLocal.get());

					threadLocals = ThreadLocalRegistry.captureSnapshot();

					assertEquals(1, threadLocals.length);
					assertFalse((Boolean)threadLocals[0].get());

					// Check programming change

					threadLocal.set(Boolean.TRUE);

					threadLocals = ThreadLocalRegistry.captureSnapshot();

					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());

					// Sync point 1, parent thread should not see this thread
					// local

					cyclicBarrier.await();
					cyclicBarrier.await();

					// Sync point 2, should not to see parent thread's thread
					// local

					threadLocals = ThreadLocalRegistry.captureSnapshot();

					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());

					cyclicBarrier.await();
					cyclicBarrier.await();

					// Sync point 3, parent thread's reset should not affect
					// child thread

					threadLocals = ThreadLocalRegistry.captureSnapshot();

					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());
				}
				catch (Exception e) {
					parentThread.interrupt();
				}
			}
		};

		childThread.start();

		cyclicBarrier.await();

		// Sync point 1, should see nothing

		ThreadLocal<?>[] threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(0, threadLocals.length);

		ThreadLocal<Integer> threadLocal = new AutoResetThreadLocal<Integer>(
			ThreadLocalRegistryTest.class.getName(), 0);

		threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(0, threadLocals.length);

		// Trigger to register

		assertEquals(0, threadLocal.get().intValue());

		threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(1, threadLocals.length);
		assertEquals(0, threadLocals[0].get());

		// Check programming change

		threadLocal.set(1);

		threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(1, threadLocals.length);
		assertEquals(1, threadLocals[0].get());

		cyclicBarrier.await();

		// Sync point 2, should not see child thread's thread local

		threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(1, threadLocals.length);
		assertEquals(1, threadLocals[0].get());

		cyclicBarrier.await();

		// Check reset

		ThreadLocalRegistry.resetThreadLocals();

		threadLocals = ThreadLocalRegistry.captureSnapshot();

		assertEquals(1, threadLocals.length);
		assertEquals(0, threadLocals[0].get());

		cyclicBarrier.await();

		childThread.join();
	}

}