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

package com.liferay.portal.kernel.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LogFactoryUtil {

	public static Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	public static Log getLog(String name) {
		// The following concurrent collection retrieve has a side effect as
		// memory fence read, this will invalidate all dirty cache data if there
		// is any. So if LogWrapper swap happens-before this, the new Log will
		// be visible for current Thread.
		LogWrapper logWrapper = _logWrappers.get(name);

		if (logWrapper == null) {
			logWrapper = new LogWrapper(_logFactory.getLog(name));

			LogWrapper previousLog = _logWrappers.putIfAbsent(name, logWrapper);
			if (previousLog != null) {
				logWrapper = previousLog;
			}
		}

		return logWrapper;
	}

	public static void setLogFactory(LogFactory logFactory) {
		for(Map.Entry<String, LogWrapper> entry : _logWrappers.entrySet()) {
			String name = entry.getKey();
			LogWrapper logWrapper = entry.getValue();
			logWrapper.setLog(logFactory.getLog(name));
		}
		// The following volatile-write will flush out all cache data, all
		// previous LogWrapper swap will be visible for any reading after any
		// memory fence read, according to the happen-before rules.
		_logFactory = logFactory;
	}

	private static volatile LogFactory _logFactory = new Jdk14LogFactoryImpl();

	private static final ConcurrentMap<String, LogWrapper> _logWrappers =
		new ConcurrentHashMap<String, LogWrapper>();

}