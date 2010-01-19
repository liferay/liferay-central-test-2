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

import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="ThreadLocalRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ThreadLocalRegistry {

	public static ThreadLocal<?>[] captureSnapshot() {
		Set<ThreadLocal<?>> threadLocalSet = _THREAD_LOCAL_SET.get();

		return threadLocalSet.toArray(
			new ThreadLocal<?>[threadLocalSet.size()]);
	}

	public static <T> ThreadLocal<T> createAndRegisterThreadLocal() {
		return createAndRegisterThreadLocal(null);
	}

	public static <T> ThreadLocal<T> createAndRegisterThreadLocal(
		T initialValue) {
		Set<ThreadLocal<?>> threadLocalSet = _THREAD_LOCAL_SET.get();
		ThreadLocal<T> threadLocal = new InitialThreadLocal<T>(initialValue);
		threadLocalSet.add(threadLocal);
		return threadLocal;
	}

	public static void resetThreadLocals() {
		for(ThreadLocal<?> threadLocal : _THREAD_LOCAL_SET.get()) {
			threadLocal.remove();
		}
	}

	private static ThreadLocal<Set<ThreadLocal<?>>> _THREAD_LOCAL_SET =
		new InitialThreadLocal<Set<ThreadLocal<?>>>(
			new HashSet<ThreadLocal<?>>());

}