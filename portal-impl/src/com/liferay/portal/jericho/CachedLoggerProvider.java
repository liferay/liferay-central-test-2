
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jericho;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Logger;
import net.htmlparser.jericho.LoggerProvider;

/**
 * @author Shuyang Zhou
 */
public class CachedLoggerProvider implements LoggerProvider {

	public static void install() throws Exception {
		Class loggerFactoryClass = Class.forName(
			"net.htmlparser.jericho.LoggerFactory");

		Method method = ReflectionUtil.getDeclaredMethod(
			loggerFactoryClass, "getDefaultLoggerProvider");

		LoggerProvider loggerProvider = (LoggerProvider)method.invoke(null);

		CachedLoggerProvider cachedLoggerProvider = new CachedLoggerProvider(
			loggerProvider);

		Config.LoggerProvider = cachedLoggerProvider;
	}

	public CachedLoggerProvider(LoggerProvider wrappedLoggerProvider) {
		_wrappedLoggerProvider = wrappedLoggerProvider;
	}

	public Logger getLogger(String name) {
		Logger logger = _loggerCache.get(name);

		if (logger == null) {
			logger = _wrappedLoggerProvider.getLogger(name);
			_loggerCache.put(name, logger);
		}

		return logger;
	}

	private final Map<String, Logger> _loggerCache =
		new ConcurrentHashMap<String, Logger>();

	private final LoggerProvider _wrappedLoggerProvider;

}