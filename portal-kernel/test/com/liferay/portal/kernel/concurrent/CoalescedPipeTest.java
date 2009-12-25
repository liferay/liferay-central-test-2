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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <a href="CoalescedPipeTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class CoalescedPipeTest extends TestCase {

	public void testBlockingTake() throws InterruptedException {
		final CoalescedPipe<String> coalescedPipe = new CoalescedPipe<String>();
		ScheduledExecutorService scheduledExecutorService =
			Executors.newScheduledThreadPool(1);
		scheduledExecutorService.schedule(new Runnable() {

			public void run() {
				try {
					coalescedPipe.put("test1");
				}
				catch (InterruptedException ex) {
					fail("Should never happen");
				}
			}

		}, 500, TimeUnit.MILLISECONDS);
		long startTime = System.currentTimeMillis();
		assertEquals("test1", coalescedPipe.take());
		long time = System.currentTimeMillis() - startTime;
		assertTrue(time > 250L);
		scheduledExecutorService.shutdownNow();
		scheduledExecutorService.awaitTermination(2, TimeUnit.MINUTES);
	}

	public void testNonBlockingTake() throws InterruptedException {
		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<String>();
		coalescedPipe.put("test2");
		coalescedPipe.put("test3");
		long startTime = System.currentTimeMillis();
		assertEquals("test2", coalescedPipe.take());
		long time = System.currentTimeMillis() - startTime;
		assertTrue(time < 100);
		startTime = System.currentTimeMillis();
		assertEquals("test3", coalescedPipe.take());
		time = System.currentTimeMillis() - startTime;
		assertTrue(time < 100);
	}

	public void testPut() throws InterruptedException {
		// Without comparator
		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<String>();
		// Null put
		boolean hasException = false;
		try {
			coalescedPipe.put(null);
		}
		catch (NullPointerException ex) {
			hasException = true;
		}
		assertTrue(hasException);
		// Normal put
		coalescedPipe.put("test1");
		assertEquals(1, coalescedPipe.pendingCount());
		assertEquals(0, coalescedPipe.coalescedCount());
		coalescedPipe.put("test2");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(0, coalescedPipe.coalescedCount());
		// Coalesce put
		coalescedPipe.put("test1");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(1, coalescedPipe.coalescedCount());
		coalescedPipe.put("test2");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(2, coalescedPipe.coalescedCount());

		// with comparator
		coalescedPipe = new CoalescedPipe<String>(new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}

		});

		// Null put
		hasException = false;
		try {
			coalescedPipe.put(null);
		}
		catch (NullPointerException ex) {
			hasException = true;
		}
		assertTrue(hasException);
		// Normal put
		coalescedPipe.put("a");
		assertEquals(1, coalescedPipe.pendingCount());
		assertEquals(0, coalescedPipe.coalescedCount());
		coalescedPipe.put("ab");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(0, coalescedPipe.coalescedCount());
		// Coalesce put
		coalescedPipe.put("c");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(1, coalescedPipe.coalescedCount());
		coalescedPipe.put("cd");
		assertEquals(2, coalescedPipe.pendingCount());
		assertEquals(2, coalescedPipe.coalescedCount());
	}

	public void testTakeSnapshot() throws InterruptedException {
		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<String>();
		Object[] snapShot = coalescedPipe.takeSnapshot();
		assertEquals(0, snapShot.length);
		coalescedPipe.put("test1");
		snapShot = coalescedPipe.takeSnapshot();
		assertEquals(1, snapShot.length);
		assertEquals("test1", snapShot[0]);
		coalescedPipe.put("test2");
		snapShot = coalescedPipe.takeSnapshot();
		assertEquals(2, snapShot.length);
		assertEquals("test1", snapShot[0]);
		assertEquals("test2", snapShot[1]);
	}

}