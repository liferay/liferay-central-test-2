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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * <a href="ThreadLocalManagerTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThreadLocalManagerTest extends TestCase {

	public void testNewThreadLocal() throws Exception {
		Thread thread1 = new Thread1();

		thread1.start();

		Thread thread2 = new Thread2();

		thread2.start();

		Thread.sleep(100);

		assertEquals(1, _threadLocalsSize1);
		assertEquals(1, _threadLocalsSize2);

		assertEquals("1", _value1);
		assertEquals("2", _value2);
	}

	private class Thread1 extends Thread {

		public void run() {
			_threadLocalsSize1 = ThreadLocalManager.getThreadLocals().size();

			_threadLocal.set("1");

			_value1 = _threadLocal.get();
		}

	}

	private class Thread2 extends Thread {

		public void run() {
			_threadLocalsSize2 = ThreadLocalManager.getThreadLocals().size();

			_threadLocal.set("2");

			_value2 = _threadLocal.get();
		}

	}

	private static ThreadLocal<String> _threadLocal =
		ThreadLocalManager.newThreadLocal();

	private int _threadLocalsSize1;
	private int _threadLocalsSize2;
	private String _value1;
	private String _value2;

}