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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <a href="BatchablePipeTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BatchablePipeTest extends TestCase {

	public void testBatchPutAndGet() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();
		// Batch same entry should fail
		IntegerIncreasableEntry entry1 = new IntegerIncreasableEntry("1st", 1);
		assertTrue(batchablePipe.put(entry1));
		assertTrue(batchablePipe.put(entry1));
		assertEquals(entry1, batchablePipe.take());
		assertEquals(entry1, batchablePipe.take());

		// Batch 2 entries
		IntegerIncreasableEntry entry2 = new IntegerIncreasableEntry("2nd", 1);
		IntegerIncreasableEntry entry3 = new IntegerIncreasableEntry("2nd", 2);
		assertTrue(batchablePipe.put(entry2));
		assertFalse(batchablePipe.put(entry3));
		IncreasableEntry<String, Integer> resultEntry = batchablePipe.take();
		assertNotNull(resultEntry);
		assertEquals("2nd", resultEntry.getKey());
		assertEquals(3, (int)resultEntry.getValue());
		assertNull(batchablePipe.take());

		// Mix batch
		IntegerIncreasableEntry entry4 = new IntegerIncreasableEntry("3rd", 1);
		IntegerIncreasableEntry entry5 = new IntegerIncreasableEntry("4th", 1);
		IntegerIncreasableEntry entry6 = new IntegerIncreasableEntry("4th", 2);
		IntegerIncreasableEntry entry7 = new IntegerIncreasableEntry("3rd", 2);
		IntegerIncreasableEntry entry8 = new IntegerIncreasableEntry("5th", 1);
		assertTrue(batchablePipe.put(entry4));
		assertTrue(batchablePipe.put(entry5));
		assertFalse(batchablePipe.put(entry6));
		assertFalse(batchablePipe.put(entry7));
		assertTrue(batchablePipe.put(entry8));
		resultEntry = batchablePipe.take();
		assertNotNull(resultEntry);
		assertEquals("3rd", resultEntry.getKey());
		assertEquals(3, (int)resultEntry.getValue());
		resultEntry = batchablePipe.take();
		assertNotNull(resultEntry);
		assertEquals("4th", resultEntry.getKey());
		assertEquals(3, (int)resultEntry.getValue());
		resultEntry = batchablePipe.take();
		assertNotNull(resultEntry);
		assertEquals("5th", resultEntry.getKey());
		assertEquals(1, (int)resultEntry.getValue());
		assertNull(batchablePipe.take());
	}

	public void testConcurrent() throws Exception {
		final BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();

		final BlockingQueue<IncreasableEntry<String, Integer>> resultQueue =
			new LinkedBlockingQueue<IncreasableEntry<String, Integer>>();

		ExecutorService putThreadPool = Executors.newFixedThreadPool(5);
		ExecutorService takeThreadPool = Executors.newFixedThreadPool(5);

		Runnable putJob = new Runnable() {

			public void run() {
				for(int i = 0; i < 100; i++) {
					batchablePipe.put(new IntegerIncreasableEntry(
						String.valueOf(i % 10), 1));
				}
			}

		};

		Runnable takeJob = new Runnable() {

			public void run() {
				while (true) {
					try {
						IncreasableEntry<String, Integer> entry =
							batchablePipe.take();
						if (entry != null) {
							if (entry.getKey().equals("exit")) {
								return;
							}
							resultQueue.put(entry);
						}
					} catch (InterruptedException ex) {
					}
				}
			}

		};

		// Submit jobs
		for(int i = 0; i < 10; i++) {
			putThreadPool.submit(putJob);
			takeThreadPool.submit(takeJob);
		}

		// Wait until put finish
		putThreadPool.shutdown();
		putThreadPool.awaitTermination(240, TimeUnit.SECONDS);

		// Poison take thread pool
		IncreasableEntry<String, Integer> poisonEntry =
			new IntegerIncreasableEntry("exit", -1);
		for(int i = 0; i < 10; i++) {
			batchablePipe.put(poisonEntry);
		}
		takeThreadPool.shutdown();
		takeThreadPool.awaitTermination(240, TimeUnit.SECONDS);

		// Do statistics
		Map<String, Integer> verifyMap = new HashMap<String, Integer>();
		for(IncreasableEntry<String, Integer> entry : resultQueue) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			Integer sum = verifyMap.get(key);
			if (sum == null) {
				verifyMap.put(key, value);
			}
			else {
				verifyMap.put(key, sum + value);
			}
		}

		// Verify statistics
		for(int i = 0; i < 10; i++) {
			Integer sum = verifyMap.get(String.valueOf(i));
			assertEquals(100, (int)sum);
		}

		// Verify batch rate
		assertLessThan(1000, resultQueue.size());
	}

	public void testCreation() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();
		assertNull(batchablePipe.take());
		assertNull(batchablePipe.take());
		assertNull(batchablePipe.take());
	}

	public void testSimplePutAndTake() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();
		// Put 1st
		IntegerIncreasableEntry entry1 = new IntegerIncreasableEntry("1st", 1);
		assertTrue(batchablePipe.put(entry1));
		// Get 1st
		assertEquals(entry1, batchablePipe.take());
		// Assure still increasable
		assertTrue(entry1.increase(1));
		// Assure get the correct increaed value
		assertEquals(2, (int)entry1.getValue());
		// Assure can't increase anymore
		assertFalse(entry1.increase(1));
		// Assure value has not changed
		assertEquals(2, (int)entry1.getValue());

		// Sequence put
		IntegerIncreasableEntry entry2 = new IntegerIncreasableEntry("2nd", 2);
		assertTrue(batchablePipe.put(entry2));
		IntegerIncreasableEntry entry3 = new IntegerIncreasableEntry("3nd", 3);
		assertTrue(batchablePipe.put(entry3));
		IntegerIncreasableEntry entry4 = new IntegerIncreasableEntry("4th", 4);
		assertTrue(batchablePipe.put(entry4));

		// Sequence take
		// Get 2nd
		assertEquals(entry2, batchablePipe.take());
		// Get 3rd
		assertEquals(entry3, batchablePipe.take());
		// Get 4th
		assertEquals(entry4, batchablePipe.take());

		assertNull(batchablePipe.take());
	}

	private static class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		public Integer doIncrease(Integer originalValue, Integer deltaValue) {
			return originalValue + deltaValue;
		}

	}

}