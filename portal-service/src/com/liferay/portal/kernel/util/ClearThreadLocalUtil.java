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
		_init();

		Thread[] threads = _getThreads();

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

	private static Field _getDeclaredField(Class<?> classObj, String name)
		throws Exception {

		Field field = classObj.getDeclaredField(name);

		field.setAccessible(true);

		return field;
	}

	private static Method _getDeclaredMethod(
			Class<?> classObj, String name, Class<?> ... parameterTypes)
		throws Exception {

		Method method = classObj.getDeclaredMethod(name, parameterTypes);

		method.setAccessible(true);

		return method;
	}

	private static Thread[] _getThreads() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup( );

		while (threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}

		int threadCountGuess = threadGroup.activeCount();

		Thread[] threads = new Thread[threadCountGuess];

		int threadCountActual = threadGroup.enumerate(threads);

		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;

			threads = new Thread[threadCountGuess];

			threadCountActual = threadGroup.enumerate(threads);
		}

		return threads;
	}

	private static void _init() throws Exception {
		if (_initialized) {
			return;
		}

		_inheritableThreadLocalsField = _getDeclaredField(
			Thread.class, "inheritableThreadLocals");
		_threadLocalsField = _getDeclaredField(Thread.class, "threadLocals");

		Class<?> threadLocalMapClass = Class.forName(
			"java.lang.ThreadLocal$ThreadLocalMap");

		_expungeStaleEntriesMethod = _getDeclaredMethod(
			threadLocalMapClass, "expungeStaleEntries");
		_removeMethod = _getDeclaredMethod(
			threadLocalMapClass, "remove", ThreadLocal.class);
		_tableField = _getDeclaredField(threadLocalMapClass, "table");

		Class<?> threadLocalMapEntryClass = Class.forName(
			"java.lang.ThreadLocal$ThreadLocalMap$Entry");

		_tableValueField = _getDeclaredField(threadLocalMapEntryClass, "value");

		_initialized = true;
	}

	private static Log _log = LogFactoryUtil.getLog(ClearThreadLocalUtil.class);

	private static Method _expungeStaleEntriesMethod;
	private static Field _inheritableThreadLocalsField;
	private static boolean _initialized;
	private static Method _removeMethod;
	private static Field _tableField;
	private static Field _tableValueField;
	private static Field _threadLocalsField;

}