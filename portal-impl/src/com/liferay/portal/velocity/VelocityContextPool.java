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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * <a href="VelocityContextPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityContextPool {

	public static boolean containsKey(String name) {
		return _instance._containsKey(name);
	}

	public static ServletContext get(String name) {
		return _instance._get(name);
	}

	public static void put(String name, ServletContext servletContext) {
		_instance._put(name, servletContext);
	}

	public static ServletContext remove(String name) {
		return _instance._remove(name);
	}

	private Map<String, ServletContext> _pool;

	private VelocityContextPool() {
		_pool = new ConcurrentHashMap<String, ServletContext>();
	}

	private boolean _containsKey(String name) {
		boolean value = _pool.containsKey(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Contains key " + name + " " + value);
		}

		return value;
	}

	private ServletContext _get(String name) {
		ServletContext servletContext = _pool.get(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Get " + name + " " + servletContext);
		}

		return servletContext;
	}

	private void _put(String name, ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Put " + name + " " + servletContext);
		}

		_pool.put(name, servletContext);
	}

	private ServletContext _remove(String name) {
		ServletContext servletContext = _pool.remove(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + name + " " + servletContext);
		}

		return servletContext;
	}

	private static Log _log = LogFactoryUtil.getLog(VelocityContextPool.class);

	private static VelocityContextPool _instance = new VelocityContextPool();

}