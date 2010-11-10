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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;

/**
 * @author Shuyang Zhou
 */
public class DirectServletRegistry {

	public static Servlet getServlet(String path) {
		return _servlets.get(path);
	}

	public static void putServlet(String path, Servlet servlet) {
		_servlets.put(path, servlet);
	}

	public static void removeServlet(Servlet servlet) {
		for (Map.Entry<String, Servlet> entry : _servlets.entrySet()) {
			if (servlet == entry.getValue()) {
				_servlets.remove(entry.getKey());

				break;
			}
		}
	}

	private static Map<String, Servlet> _servlets =
		new ConcurrentHashMap<String, Servlet>();

}