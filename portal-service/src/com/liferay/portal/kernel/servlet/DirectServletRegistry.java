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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.File;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class DirectServletRegistry {

	public static Servlet getServlet(String path) {
		ObjectValuePair<Servlet, Long> servletInfo = _servlets.get(path);

		if (servletInfo == null) {
			return null;
		}

		Servlet servlet = servletInfo.getKey();

		if (_checkUpdate) {
			long previousLastModifiedTime = servletInfo.getValue();
			long lastModifiedTime = _getJspFileLastModifiedTime(path, servlet);
			if (lastModifiedTime > previousLastModifiedTime ||
				lastModifiedTime == 0) {
				_servlets.remove(path);
				servlet = null;
			}
		}

		return servlet;
	}

	public static void putServlet(String path, Servlet servlet) {
		if (!_servlets.containsKey(path)) {
			long lastModifiedTime = 1;

			if (_checkUpdate) {
				lastModifiedTime = _getJspFileLastModifiedTime(path, servlet);
			}

			if (lastModifiedTime > 0) {
				_servlets.put(path, new ObjectValuePair<Servlet, Long>(
					servlet, lastModifiedTime));
			}
		}
	}

	public static void removeServlet(Servlet servlet) {
		for (Map.Entry<String, ObjectValuePair<Servlet, Long>> entry :
			_servlets.entrySet()) {
			if (servlet == entry.getValue().getKey()) {
				_servlets.remove(entry.getKey());

				break;
			}
		}
	}

	private static long _getJspFileLastModifiedTime(
		String path, Servlet servlet) {

		ServletContext servletContext =
			servlet.getServletConfig().getServletContext();
		String rootPath = servletContext.getRealPath("");

		File file = new File(rootPath, path);

		return file.lastModified();
	}

	private static boolean _checkUpdate = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_CHECK_UPDATE_ENABLED));

	private static Map<String, ObjectValuePair<Servlet, Long>> _servlets =
		new ConcurrentHashMap<String, ObjectValuePair<Servlet, Long>>();

}