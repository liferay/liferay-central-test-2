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
		if (!_isInitialized) {
			initial();
		}

		Thread[] threads = getThreads();

		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();

		for (Thread thread : threads) {
			doClearThreadLocal(thread, classLoader);
		}
	}

	private static void doClearThreadLocal(
		Thread thread, ClassLoader classLoader) throws Exception {

		if (thread == null) {
			return;
		}

		Object threadLocalMap = _threadLocalsField.get(thread);

		Object inheritableThreadLocalMap =
			_inheritableThreadLocalsField.get(thread);

		doClearThreadLocalMap(threadLocalMap, classLoader);

		doClearThreadLocalMap(inheritableThreadLocalMap, classLoader);
	}

	private static void doClearThreadLocalMap(
		Object threadLocalMap, ClassLoader classLoader) throws Exception {

		if (threadLocalMap == null) {
			return;
		}

		Object[] table = (Object[])_tableField.get(threadLocalMap);

		if (table == null) {
			return;
		}

		int staleEntriesCount = 0;

		for(Object tableEntry : table) {
			if (tableEntry == null) {
				continue;
			}

			boolean remove = false;

			Object key = ((Reference<?>)tableEntry).get();

			Object value = _tableValueField.get(tableEntry);

			if (key != null && key.getClass().getClassLoader() == classLoader) {
				remove = true;
			}

			if (value != null &&
				value.getClass().getClassLoader() == classLoader) {

				remove = true;
			}

			if (remove) {
				if (key != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Clear a ThreadLocal with key of type " +
							key.getClass().getCanonicalName());
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

	private static Thread[] getThreads() {
		ThreadGroup threadGroup = Thread.currentThread( ).getThreadGroup( );

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

	private static void initial() throws Exception {
		Class<?> threadLocalMapClass = Class.forName(
			"java.lang.ThreadLocal$ThreadLocalMap");
		Class<?> threadLocalMapEntryClass = Class.forName(
			"java.lang.ThreadLocal$ThreadLocalMap$Entry");

		_threadLocalsField = Thread.class.getDeclaredField("threadLocals");
		_inheritableThreadLocalsField = Thread.class.getDeclaredField(
			"inheritableThreadLocals");
		_tableField = threadLocalMapClass.getDeclaredField("table");
		_tableValueField = threadLocalMapEntryClass.getDeclaredField("value");
		_removeMethod = threadLocalMapClass.getDeclaredMethod(
			"remove", ThreadLocal.class);
		_expungeStaleEntriesMethod = threadLocalMapClass.getDeclaredMethod(
			"expungeStaleEntries");

		_threadLocalsField.setAccessible(true);
		_inheritableThreadLocalsField.setAccessible(true);
		_tableField.setAccessible(true);
		_tableValueField.setAccessible(true);
		_removeMethod.setAccessible(true);
		_expungeStaleEntriesMethod.setAccessible(true);

		_isInitialized = true;
	}

	private static Log _log = LogFactoryUtil.getLog(ClearThreadLocalUtil.class);

	private static Method _expungeStaleEntriesMethod;
	private static Field _inheritableThreadLocalsField;
	private static boolean _isInitialized;
	private static Method _removeMethod;
	private static Field _tableField;
	private static Field _tableValueField;
	private static Field _threadLocalsField;

}