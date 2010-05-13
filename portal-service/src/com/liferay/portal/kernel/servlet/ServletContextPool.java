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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * <a href="ServletContextPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ServletContextPool {

	public static boolean containsKey(String servletContextName) {
		return _instance._containsKey(servletContextName);
	}

	public static ServletContext get(String servletContextName) {
		return _instance._get(servletContextName);
	}

	public static void put(
		String servletContextName, ServletContext servletContext) {

		_instance._put(servletContextName, servletContext);
	}

	public static ServletContext remove(String servletContextName) {
		return _instance._remove(servletContextName);
	}

	private ServletContextPool() {
		_servletContextPool = new ConcurrentHashMap<String, ServletContext>();
	}

	private boolean _containsKey(String servletContextName) {
		boolean value = _servletContextPool.containsKey(servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Contains key " + servletContextName + " " + value);
		}

		return value;
	}

	private ServletContext _get(String servletContextName) {
		ServletContext servletContext = _servletContextPool.get(
			servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Get " + servletContextName + " " + servletContext);
		}

		return servletContext;
	}

	private void _put(
		String servletContextName, ServletContext servletContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Put " + servletContextName + " " + servletContext);
		}

		_servletContextPool.put(servletContextName, servletContext);
	}

	private ServletContext _remove(String servletContextName) {
		ServletContext servletContext = _servletContextPool.remove(
			servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + servletContextName + " " + servletContext);
		}

		return servletContext;
	}

	private static Log _log = LogFactoryUtil.getLog(ServletContextPool.class);

	private static ServletContextPool _instance = new ServletContextPool();

	private Map<String, ServletContext> _servletContextPool;

}