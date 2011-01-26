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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class ThreadPoolExecutorTest extends TestCase {

	/**
	 * Bad arguments
	 */
	public void testAdjustPoolSize1() {
		// newCorePoolSize is less than 0
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);
		try {
			threadPoolExecutor.adjustPoolSize(-1, 10);
			fail();
		}
		catch (IllegalArgumentException ise) {
		}

		// newMaxPoolSize is less than 0
		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);
		try {
			threadPoolExecutor.adjustPoolSize(1, -1);
			fail();
		}
		catch (IllegalArgumentException ise) {
		}

		// newMaxPoolSize is 0
		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);
		try {
			threadPoolExecutor.adjustPoolSize(1, 0);
			fail();
		}
		catch (IllegalArgumentException ise) {
		}

		// newMaxPoolSize is less than newCorePoolSize
		threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);
		try {
			threadPoolExecutor.adjustPoolSize(2, 1);
			fail();
		}
		catch (IllegalArgumentException ise) {
		}
	}

	/**
	 * Adjust idle executor, increase size
	 */
	public void testAdjustPoolSize2() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		assertEquals(5, threadPoolExecutor.getCorePoolSize());
		assertEquals(10, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(10, 20);

		assertEquals(10, threadPoolExecutor.getCorePoolSize());
		assertEquals(20, threadPoolExecutor.getMaxPoolSize());
	}

	/**
	 * Adjust idle executor, decrease size
	 */
	public void testAdjustPoolSize3() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			10, 20, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);

		assertEquals(10, threadPoolExecutor.getCorePoolSize());
		assertEquals(20, threadPoolExecutor.getMaxPoolSize());

		threadPoolExecutor.adjustPoolSize(5, 10);

		assertEquals(5, threadPoolExecutor.getCorePoolSize());
		assertEquals(10, threadPoolExecutor.getMaxPoolSize());
	}

	/**
	 * Decrease corePoolSize size, _poolSize > newCorePoolSize
	 */
	public void testAdjustPoolSize4() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			2, 3, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);
			TestUtil.waitUtilBlock(markerBlockingJob1, markerBlockingJob2);
			TestUtil.unBlock(markerBlockingJob1, markerBlockingJob2);
			TestUtil.waitUtilEnded(markerBlockingJob1, markerBlockingJob2);

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 3);

			assertEquals(1, threadPoolExecutor.getCorePoolSize());
			assertEquals(3, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Decrease maxPoolSize size, _poolSize > newMaxPoolSize
	 */
	public void testAdjustPoolSize5() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			2, 3, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob3 = new MarkerBlockingJob(true);
			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);
			threadPoolExecutor.execute(markerBlockingJob3);
			TestUtil.waitUtilBlock(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);
			TestUtil.unBlock(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);
			TestUtil.waitUtilEnded(
				markerBlockingJob1, markerBlockingJob2, markerBlockingJob3);

			Thread.sleep(TestUtil.SHORT_WAIT);

			assertEquals(3, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(1, 1);

			assertEquals(1, threadPoolExecutor.getCorePoolSize());
			assertEquals(1, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.KEEPALIVE_WAIT * 2);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(0, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Increase corePoolSize size, _taskQueue is empty
	 */
	public void testAdjustPoolSize6() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(true);
			threadPoolExecutor.execute(markerBlockingJob);
			markerBlockingJob.waitUntilBlock();

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			assertEquals(2, threadPoolExecutor.getCorePoolSize());
			assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			Thread.sleep(TestUtil.SHORT_WAIT);

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	/**
	 * Increase corePoolSize size, _taskQueue is not empty
	 */
	public void testAdjustPoolSize7() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);

		try {
			MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
			MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
			threadPoolExecutor.execute(markerBlockingJob1);
			threadPoolExecutor.execute(markerBlockingJob2);
			markerBlockingJob1.waitUntilBlock();

			assertEquals(1, threadPoolExecutor.getPoolSize());
			assertEquals(1, threadPoolExecutor.getActiveCount());
			assertEquals(1, threadPoolExecutor.getPendingTaskCount());

			threadPoolExecutor.adjustPoolSize(2, 2);

			assertEquals(2, threadPoolExecutor.getCorePoolSize());
			assertEquals(2, threadPoolExecutor.getMaxPoolSize());

			markerBlockingJob2.waitUntilBlock();

			assertEquals(2, threadPoolExecutor.getPoolSize());
			assertEquals(2, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPendingTaskCount());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	/**
	 * AllowCoreThreadTimeOut is on
	 */
	public void testAutoResizePool1() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10);
		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<MarkerBlockingJob>();

			// Increase pool size first
			assertEquals(0, threadPoolExecutor.getPoolSize());
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					new MarkerBlockingJob(true);
				markerBlockingJobQueue.add(markerBlockingJob);
				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			// Pool is full, queue up
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					new MarkerBlockingJob(true);
				markerBlockingJobQueue.add(markerBlockingJob);
				threadPoolExecutor.execute(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getPendingTaskCount());
				assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			// Queue is full, start reject
			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));
					fail();
				}
				catch (RejectedExecutionException ree) {
				}
				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(10, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			// Finish running jobs
			assertEquals(20, markerBlockingJobQueue.size());
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();
				markerBlockingJob.unBlock();
				TestUtil.waitUtilEnded(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(9 - i, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			// Finish all jobs which will cause threads timeout
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();
				markerBlockingJob.unBlock();
				TestUtil.waitUtilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertEquals(9 - i, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(0, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	/**
	 * AllowCoreThreadTimeOut is off
	 */
	public void testAutoResizePool2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 10, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 10);
		try {
			Queue<MarkerBlockingJob> markerBlockingJobQueue =
				new LinkedList<MarkerBlockingJob>();

			// Increase pool size first
			assertEquals(0, threadPoolExecutor.getPoolSize());
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					new MarkerBlockingJob(true);
				markerBlockingJobQueue.add(markerBlockingJob);
				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilBlock();

				assertEquals(i + 1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getTaskCount());
			}

			// Pool is full, queue up
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					new MarkerBlockingJob(true);
				markerBlockingJobQueue.add(markerBlockingJob);
				threadPoolExecutor.execute(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getPendingTaskCount());
				assertEquals(i + 11, threadPoolExecutor.getTaskCount());
			}

			// Queue is full, start reject
			for (int i = 0; i < 10; i++) {
				try {
					threadPoolExecutor.execute(new MarkerBlockingJob(true));
					fail();
				}
				catch (RejectedExecutionException ree) {
				}
				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(10, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
			}

			// Finish running jobs
			assertEquals(20, markerBlockingJobQueue.size());
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();
				markerBlockingJob.unBlock();
				TestUtil.waitUtilEnded(markerBlockingJob);

				assertEquals(10, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(9 - i, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}

			// Finish all jobs which will cause threads timeout
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob =
					markerBlockingJobQueue.remove();
				markerBlockingJob.unBlock();
				TestUtil.waitUtilEnded(markerBlockingJob);

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				int expectPoolSize = i > 4 ? 5 : 9 - i;
				assertEquals(expectPoolSize, threadPoolExecutor.getPoolSize());
				assertEquals(10, threadPoolExecutor.getLargestPoolSize());
				assertEquals(0, threadPoolExecutor.getPendingTaskCount());
				assertEquals(20, threadPoolExecutor.getTaskCount());
				assertEquals(
					i + 11, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	/**
	 * Await on a non-shutdown executor, should timeout
	 */
	public void testAwaitTermination1() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		assertFalse(threadPoolExecutor.awaitTermination(
			TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime >= TestUtil.KEEPALIVE_TIME);
	}

	/**
	 * timeout time is 0, should timeout immediately
	 */
	public void testAwaitTermination2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		assertFalse(threadPoolExecutor.awaitTermination(
			0, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	/**
	 * timeout time is less than 0, should timeout immediately
	 */
	public void testAwaitTermination3() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		long startTime = System.currentTimeMillis();

		assertFalse(threadPoolExecutor.awaitTermination(
			0, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	/**
	 * Success termination
	 */
	public void testAwaitTermination4() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, 1, TimeUnit.SECONDS, true, 3);

		threadPoolExecutor.shutdown();

		long startTime = System.currentTimeMillis();

		assertTrue(threadPoolExecutor.awaitTermination(
			10, TimeUnit.MILLISECONDS));

		long waitTime = System.currentTimeMillis() - startTime;

		assertTrue(waitTime < TestUtil.SHORT_WAIT);
	}

	/**
	 * Bad arguments
	 */
	public void testConstructor1() {
		// corePoolSize is less than 0
		try {
			new ThreadPoolExecutor(
				-1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxPoolSize is less than 0
		try {
			new ThreadPoolExecutor(
				1, -1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxPoolSize is 0
		try {
			new ThreadPoolExecutor(
				1, 0, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// corePoolSize is greater than maxPoolSize
		try {
			new ThreadPoolExecutor(
				2, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// keepAliveTime is less than 0
		try {
			new ThreadPoolExecutor(1, 1, -1, TimeUnit.MILLISECONDS, true, 1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxQueueSize is less than 0
		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, -1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxQueueSize is 0
		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 0);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// rejectedExecutionHandler is null
		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				null,
				Executors.defaultThreadFactory(),
				new ThreadPoolHandlerAdapter());
			fail();
		}
		catch (NullPointerException npe) {
		}

		// threadFactory is null
		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(),
				null,
				new ThreadPoolHandlerAdapter());
			fail();
		}
		catch (NullPointerException npe) {
		}

		// threadPoolHandler is null
		try {
			new ThreadPoolExecutor(
				1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
				new AbortPolicy(),
				Executors.defaultThreadFactory(),
				null);
			fail();
		}
		catch (NullPointerException npe) {
		}
	}

	/**
	 * Normal construct 1
	 */
	public void testConstructor2() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2);

		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(60 * 1000000000L,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(false, threadPoolExecutor.isAllowCoreThreadTimeOut());
		assertEquals(
			Integer.MAX_VALUE, 
			threadPoolExecutor.getRemainingTaskQueueCapacity());
		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();
		assertTrue(
			rejectedExecutionHandler instanceof AbortPolicy);
		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();
		assertTrue(
			threadPoolHandler instanceof ThreadPoolHandlerAdapter);
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}
	
	/**
	 * Normal construct 2
	 */
	public void testConstructor3() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(true, threadPoolExecutor.isAllowCoreThreadTimeOut());
		assertEquals(3, threadPoolExecutor.getRemainingTaskQueueCapacity());
		RejectedExecutionHandler rejectedExecutionHandler =
			threadPoolExecutor.getRejectedExecutionHandler();
		assertTrue(
			rejectedExecutionHandler instanceof AbortPolicy);
		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();
		assertTrue(
			threadPoolHandler instanceof ThreadPoolHandlerAdapter);
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}

	/**
	 * Normal construct 3
	 */
	public void testConstructor4() {
		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy();
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		ThreadPoolHandler threadPoolHandler = new ThreadPoolHandlerAdapter();
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			rejectedExecutionHandler,
			threadFactory,
			threadPoolHandler);
		assertEquals(1, threadPoolExecutor.getCorePoolSize());
		assertEquals(2, threadPoolExecutor.getMaxPoolSize());
		assertEquals(TestUtil.KEEPALIVE_TIME * 1000000,
			threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
		assertEquals(true, threadPoolExecutor.isAllowCoreThreadTimeOut());
		assertEquals(3, threadPoolExecutor.getRemainingTaskQueueCapacity());
		assertSame(rejectedExecutionHandler,
			threadPoolExecutor.getRejectedExecutionHandler());
		assertSame(threadFactory, threadPoolExecutor.getThreadFactory());
		assertSame(
			threadPoolHandler, threadPoolExecutor.getThreadPoolHandler());
		assertFalse(threadPoolExecutor.isShutdown());
		assertFalse(threadPoolExecutor.isTerminating());
		assertFalse(threadPoolExecutor.isTerminated());
	}

	/**
	 * Null job
	 */
	public void testExecute1() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);

		try {
			threadPoolExecutor.execute(null);
			fail();
		}
		catch (NullPointerException npe) {
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Fail to execute because of _SHUTDOWN executor
	 */
	public void testExecute2() {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler,
			Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());

		threadPoolExecutor.shutdown();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		threadPoolExecutor.execute(markerBlockingJob);

		assertFalse(markerBlockingJob.isStarted());
		List<Runnable> rejectedList =
			recordRejectedExecutionHandler.getRejectedList();
		assertEquals(1, rejectedList.size());
		assertSame(markerBlockingJob, rejectedList.get(0));
	}

	/**
	 * Fail to execute because of _taskQueue is full
	 */
	public void testExecute3() {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1,
			recordRejectedExecutionHandler,
			Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());
		try {
			List<Runnable> rejectedList =
				recordRejectedExecutionHandler.getRejectedList();
			// Consume the single pool thread
			threadPoolExecutor.execute(new MarkerBlockingJob(true));
			assertEquals(0, rejectedList.size());
			// Consume the single _taskQueue slot
			threadPoolExecutor.execute(new MarkerBlockingJob(true));
			assertEquals(0, rejectedList.size());

			// Check reject
			MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();
			threadPoolExecutor.execute(markerBlockingJob);

			assertEquals(1, rejectedList.size());
			assertSame(markerBlockingJob, rejectedList.get(0));
			assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor, true);
		}
	}

	/**
	 * Execute with a concurrent shutdown, successfully rollback job
	 */
	public void testExecute4() throws InterruptedException {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler,
			Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());
		final TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		final CountDownLatch executeLatch = new CountDownLatch(1);

		// Concurrent shutdown thread
		new Thread() {

			public void run() {
				try {
					ReentrantLock putLock = taskQueue.getPutLock();
					putLock.lock();
					executeLatch.countDown();
					try {
						// Spin waiting until offer thread try to lock up take
						// lock
						while (!putLock.hasQueuedThreads()) {
							Thread.sleep(1);
						}
						// Shutdown after submit thread saw executor is in
						// _RUNNING state, before it finishes enqueue.
						threadPoolExecutor.shutdown();
					}
					finally {
						putLock.unlock();
					}
				}
				catch (InterruptedException ie) {
				}
			}

		}.start();

		executeLatch.await();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		try {
			threadPoolExecutor.execute(markerBlockingJob);
			List<Runnable> rejectedList =
				recordRejectedExecutionHandler.getRejectedList();

			assertEquals(1, rejectedList.size());
			assertSame(markerBlockingJob, rejectedList.get(0));
			assertFalse(markerBlockingJob.isStarted());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Execute with a concurrent shutdown, fail to rollback job
	 */
	public void testExecute5() throws InterruptedException {
		RecordRejectedExecutionHandler recordRejectedExecutionHandler =
			new RecordRejectedExecutionHandler();

		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3,
			recordRejectedExecutionHandler,
			Executors.defaultThreadFactory(),
			new ThreadPoolHandlerAdapter());
		final TaskQueue<Runnable> taskQueue = threadPoolExecutor.getTaskQueue();

		final CountDownLatch executeLatch = new CountDownLatch(1);

		// Concurrent shutdown thread
		new Thread() {

			public void run() {
				try {
					ReentrantLock takeLock = taskQueue.getTakeLock();
					takeLock.lock();
					executeLatch.countDown();
					try {
						// Spin waiting until offer thread try to lock up take
						// lock
						while (!takeLock.hasQueuedThreads()) {
							Thread.sleep(1);
						}
						// Steal the job before concurrent shutdown
						assertNotNull(taskQueue.take());

						// Shutdown after submit thread saw executor is in
						// _RUNNING state, before it finishes offering.
						threadPoolExecutor.shutdown();
					}
					finally {
						takeLock.unlock();
					}
				}
				catch (InterruptedException ie) {
				}
			}

		}.start();

		executeLatch.await();

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();

		try {
			threadPoolExecutor.execute(markerBlockingJob);
			assertTrue(
				recordRejectedExecutionHandler.getRejectedList().isEmpty());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Bad job kills worker threads
	 */
	public void testExecute6() throws InterruptedException {
		SetRecordUncaughtExceptionThreadFactory threadFactory =
			new SetRecordUncaughtExceptionThreadFactory();
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 10,
			new AbortPolicy(),
			threadFactory,
			new ThreadPoolHandlerAdapter());
		RecordUncaughtExceptionHandler recordUncaughtExceptionHandler =
			threadFactory.getRecordUncaughtExceptionHandler();
		Queue<MarkerBlockingJob> markerBlockingJobQueue =
			new LinkedList<MarkerBlockingJob>();
		try {
			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(
					false, true);
				markerBlockingJobQueue.add(markerBlockingJob);
				threadPoolExecutor.execute(markerBlockingJob);
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
			// Wait to allow worker threads finalizing
			Thread.sleep(TestUtil.SHORT_WAIT);
		}
		assertEquals(1, threadPoolExecutor.getLargestPoolSize());
		assertEquals(10,
			recordUncaughtExceptionHandler.getUncaughtMap().size());
		for (MarkerBlockingJob markerBlockingJob : markerBlockingJobQueue) {
			assertTrue(markerBlockingJob.isStarted());
			assertFalse(markerBlockingJob.isEnded());
		}
	}

	/**
	 * Success execute
	 */
	public void testExecute7() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);
		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();
		try {
			threadPoolExecutor.execute(markerBlockingJob);

			Thread.sleep(10);

			assertTrue(markerBlockingJob.isEnded());
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Fast consumer, slow producer, allowCoreThreadTimeOut is on
	 */
	public void testExecute8() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);
		try {
			assertEquals(0, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();
				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertTrue(markerBlockingJob.isEnded());
				assertEquals(0, threadPoolExecutor.getActiveCount());
				assertEquals(0, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Fast consumer, slow producer, allowCoreThreadTimeOut is off
	 */
	public void testExecute9() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 3);
		try {
			assertEquals(0, threadPoolExecutor.getActiveCount());
			assertEquals(0, threadPoolExecutor.getPoolSize());

			for (int i = 0; i < 10; i++) {
				MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob();
				threadPoolExecutor.execute(markerBlockingJob);

				markerBlockingJob.waitUntilEnded();

				Thread.sleep(TestUtil.KEEPALIVE_WAIT);

				assertTrue(markerBlockingJob.isEnded());
				assertEquals(0, threadPoolExecutor.getActiveCount());
				assertEquals(1, threadPoolExecutor.getPoolSize());
				assertEquals(i + 1, threadPoolExecutor.getCompletedTaskCount());
			}
		}
		finally {
			TestUtil.closePool(threadPoolExecutor);
		}
	}

	/**
	 * Enqueue new task right after _taskQueue polls timeout, before enters
	 * exit checking block
	 */
	public void testExecute10() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

		MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
		// Consume the single core thread
		threadPoolExecutor.execute(markerBlockingJob1);
		markerBlockingJob1.waitUntilBlock();

		ReentrantLock mainLock = threadPoolExecutor.getMainLock();
		ReentrantLock takeLock =
			threadPoolExecutor.getTaskQueue().getTakeLock();
		// Lock up _taskQueue's takeLock
		takeLock.lock();
		try {
			// Unblock the current running job.
			markerBlockingJob1.unBlock();

			// Wait until the core thread starts polling job from _taskQueue.
			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			// Lock up mainLock before letting core thread finishes polling
			mainLock.lock();
		}
		finally {
			// Unlock _taskQueue's takeLock, core thread starts timeout polling
			takeLock.unlock();
		}

		MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);

		try {
			// Wait to ensure core thread returns from timeout polling and
			// enters exit check block
			while (!mainLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			// add a new task
			threadPoolExecutor.execute(markerBlockingJob2);
		}
		finally {
			mainLock.unlock();
		}

		markerBlockingJob2.waitUntilBlock();
		markerBlockingJob2.unBlock();

		TestUtil.closePool(threadPoolExecutor);
		assertTrue(markerBlockingJob2.isEnded());
	}

	public void testFinalize() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 2, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 3);
		assertFalse(threadPoolExecutor.isShutdown());
		threadPoolExecutor.finalize();
		assertTrue(threadPoolExecutor.isShutdown());
	}

	/**
	 * ShutdownNow happens after _getTask() return, before WorkTask start
	 */
	public void testShutdownNow1() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, false, 1);

		MarkerBlockingJob markerBlockingJob1 = new MarkerBlockingJob(true);
		MarkerBlockingJob markerBlockingJob2 = new MarkerBlockingJob(true);
		// Consume the single core thread
		threadPoolExecutor.execute(markerBlockingJob1);
		markerBlockingJob1.waitUntilBlock();
		// Consume the single _taskQueue slot
		threadPoolExecutor.execute(markerBlockingJob2);

		TaskQueue taskQueue = threadPoolExecutor.getTaskQueue();
		AbstractQueuedSynchronizer headWorkerTask = null;
		// Lock up _taskQueue's takeLock
		ReentrantLock takeLock = taskQueue.getTakeLock();
		takeLock.lock();
		try {
			// Unblock the current running job.
			markerBlockingJob1.unBlock();

			// Wait until the core thread starts taking job from _taskQueue.
			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			// Get the core thread's WorkerTask.
			Set<? extends AbstractQueuedSynchronizer> workerSet =
				threadPoolExecutor.getWorkerTaskSet();
			assertEquals(1, workerSet.size());
			headWorkerTask = workerSet.iterator().next();

			// Lock up the WorkTask
			headWorkerTask.acquire(1);
		}
		finally {
			// Unlock _taskQueue's takeLock
			takeLock.unlock();
		}

		// Wait until core thread blocks at the begine of _runTask()
		while (!headWorkerTask.hasQueuedThreads()) {
			Thread.sleep(1);
		}

		// Call shutdownNow
		threadPoolExecutor.shutdownNow();

		// Unblock the core thread
		headWorkerTask.release(1);

		// Wait until executor fully terminated
		assertTrue(threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));

		// markerBlockingJob2 should be interrupted
		assertTrue(markerBlockingJob2.isInterrupted());
	}

	/**
	 * ShutdownNow happens after _taskQueue.poll() timeout return,
	 * before exit checking block
	 */
	public void testShutdownNow2() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			1, 1, TestUtil.KEEPALIVE_TIME, TimeUnit.MILLISECONDS, true, 1);

		MarkerBlockingJob markerBlockingJob = new MarkerBlockingJob(true);
		// Consume the single core thread
		threadPoolExecutor.execute(markerBlockingJob);
		markerBlockingJob.waitUntilBlock();

		ReentrantLock mainLock = threadPoolExecutor.getMainLock();
		ReentrantLock takeLock =
			threadPoolExecutor.getTaskQueue().getTakeLock();
		// Lock up _taskQueue's takeLock
		takeLock.lock();
		try {
			// Unblock the current running job.
			markerBlockingJob.unBlock();

			// Wait until the core thread starts polling job from _taskQueue.
			while (!takeLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			// Lock up mainLock before letting core thread finishes polling
			mainLock.lock();
		}
		finally {
			// Unlock _taskQueue's takeLock, core thread starts timeout polling
			takeLock.unlock();
		}

		try {
			// Wait to ensure core thread returns from timeout polling and
			// enters exit check block
			while (!mainLock.hasQueuedThreads()) {
				Thread.sleep(1);
			}

			// Change the _runState to _STOP
			threadPoolExecutor.shutdownNow();
		}
		finally {
			mainLock.unlock();
		}

		assertTrue(threadPoolExecutor.isShutdown());
		assertTrue(threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS));
	}

}