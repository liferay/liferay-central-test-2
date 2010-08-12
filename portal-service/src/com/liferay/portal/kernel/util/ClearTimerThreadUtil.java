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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ClearTimerThreadUtil {

	public static void clearTimerThread() throws Exception {
		if (!_capable) {
			return;
		}

		Thread[] threads = ThreadUtil.getThreads();
		for(Thread thread : threads) {
			if (thread != null &&
				thread.getClass().getName().equals("java.util.TimerThread")) {
				Object queue = _queueField.get(thread);
				synchronized(queue) {
					_newTasksMayBeScheduledField.setBoolean(thread, false);
					_clearMethod.invoke(queue);
					queue.notify();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClearTimerThreadUtil.class);

	private static boolean _capable;
	private static final Method _clearMethod;
	private static final Field _newTasksMayBeScheduledField;
	private static final Field _queueField;

	static {
		try {
			Class<?> timeThreadClass = Class.forName("java.util.TimerThread");

			_newTasksMayBeScheduledField = timeThreadClass.getDeclaredField(
				"newTasksMayBeScheduled");
			_newTasksMayBeScheduledField.setAccessible(true);

			_queueField = timeThreadClass.getDeclaredField("queue");
			_queueField.setAccessible(true);

			Class<?> taskQueueClass = Class.forName("java.util.TaskQueue");

			_clearMethod = taskQueueClass.getDeclaredMethod("clear");
			_clearMethod.setAccessible(true);

			_capable = true;
		} catch (Throwable t) {
			_capable = false;
			_log.error("Fail to initialize ClearTimerThreadUtil, this may "
				+ "cause by a incompatible jvm. No TimerThread will be forced "
				+ "clean up.", t);
			throw new ExceptionInInitializerError(t);
		}
	}

}