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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Tina Tian
 */
public class ClearThreadLocalUtil {

	public static void clearThreadLocal() throws Exception {
		if (!_capable) {
			return;
		}

		Thread[] threads = ThreadUtil.getThreads();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		for (Thread thread : threads) {
			_clearThreadLocal(thread, contextClassLoader);
		}
	}

	private static void _clearThreadLocal(
			Thread thread, ClassLoader classLoader)
		throws Exception {

		if (thread == null) {
			return;
		}

		Object threadLocalMap = _threadLocalsField.get(thread);

		Object inheritableThreadLocalMap = _inheritableThreadLocalsField.get(
			thread);

		_clearThreadLocalMap(threadLocalMap, classLoader);
		_clearThreadLocalMap(inheritableThreadLocalMap, classLoader);
	}

	private static void _clearThreadLocalMap(
			Object threadLocalMap, ClassLoader classLoader)
		throws Exception {

		if (threadLocalMap == null) {
			return;
		}

		Object[] table = (Object[])_tableField.get(threadLocalMap);

		if (table == null) {
			return;
		}

		int staleEntriesCount = 0;

		for (Object tableEntry : table) {
			if (tableEntry == null) {
				continue;
			}

			Object key = ((Reference<?>)tableEntry).get();
			Object value = _tableValueField.get(tableEntry);

			boolean remove = false;

			if (key != null) {
				Class<?> keyClass = key.getClass();

				ClassLoader keyClassLoader = keyClass.getClassLoader();

				if (keyClassLoader == classLoader) {
					remove = true;
				}
			}

			if (value != null) {
				Class<?> valueClass = value.getClass();

				ClassLoader valueClassLoader = valueClass.getClassLoader();

				if (valueClassLoader == classLoader) {
					remove = true;
				}
			}

			if (remove) {
				if (key != null) {
					if (_log.isDebugEnabled()) {
						Class<?> keyClass = key.getClass();

						_log.debug(
							"Clear a ThreadLocal with key of type " +
								keyClass.getCanonicalName());
					}

					_removeMethod.invoke(threadLocalMap, key);
				}
				else {
					staleEntriesCount++;
				}
			}
		}

		if (staleEntriesCount > 0) {
			_expungeStaleEntriesMethod.invoke(threadLocalMap);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClearThreadLocalUtil.class);

	private static boolean _capable;
	private static final Method _expungeStaleEntriesMethod;
	private static final Field _inheritableThreadLocalsField;
	private static final Method _removeMethod;
	private static final Field _tableField;
	private static final Field _tableValueField;
	private static final Field _threadLocalsField;

	static {
		try {
			_inheritableThreadLocalsField = Thread.class.getDeclaredField(
				"inheritableThreadLocals");
			_inheritableThreadLocalsField.setAccessible(true);

			_threadLocalsField = Thread.class.getDeclaredField("threadLocals");
			_threadLocalsField.setAccessible(true);

			Class<?> threadLocalMapClass = Class.forName(
				"java.lang.ThreadLocal$ThreadLocalMap");

			_expungeStaleEntriesMethod = threadLocalMapClass.getDeclaredMethod(
				"expungeStaleEntries");
			_expungeStaleEntriesMethod.setAccessible(true);

			_removeMethod = threadLocalMapClass.getDeclaredMethod(
				"remove", ThreadLocal.class);
			_removeMethod.setAccessible(true);

			_tableField = threadLocalMapClass.getDeclaredField("table");
			_tableField.setAccessible(true);

			Class<?> threadLocalMapEntryClass = Class.forName(
				"java.lang.ThreadLocal$ThreadLocalMap$Entry");

			_tableValueField = threadLocalMapEntryClass.getDeclaredField(
				"value");
			_tableValueField.setAccessible(true);

			_capable = true;
		} catch (Throwable t) {
			_capable = false;
			_log.error("Fail to initialize ClearThreadLocalUtil, this may "
				+ "cause by a incompatible jvm. No ThreadLocal will be forced "
				+ "clean up.", t);
			throw new ExceptionInInitializerError(t);
		}
	}

}