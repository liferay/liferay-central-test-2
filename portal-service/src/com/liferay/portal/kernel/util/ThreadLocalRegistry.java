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

package com.liferay.portal.kernel.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalRegistry {

	public static ThreadLocal<?>[] captureSnapshot() {
		Set<ThreadLocal<?>> threadLocalSet = _threadLocalSet.get();

		return threadLocalSet.toArray(
			new ThreadLocal<?>[threadLocalSet.size()]);
	}

	public static void registerThreadLocal(ThreadLocal<?> threadLocal) {
		Set<ThreadLocal<?>> threadLocalSet = _threadLocalSet.get();

		threadLocalSet.add(threadLocal);
	}

	public static void resetThreadLocals() {
		Set<ThreadLocal<?>> threadLocalSet = _threadLocalSet.get();

		if (threadLocalSet == null) {
			return;
		}

		for (ThreadLocal<?> threadLocal : threadLocalSet) {
			threadLocal.remove();
		}
	}

	private static ThreadLocal<Set<ThreadLocal<?>>> _threadLocalSet =
		new InitialThreadLocal<Set<ThreadLocal<?>>>(
			ThreadLocalRegistry.class + "._threadLocalSet",
			new HashSet<ThreadLocal<?>>());

}