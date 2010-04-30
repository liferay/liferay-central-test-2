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

/**
 * <a href="LogFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LogFactoryUtil {

	public static LogFactory getLogFactory() {
		return _logFactory;
	}

	public static void setLogFactory(LogFactory logFactory) {
		_logFactory.setLogFactory(logFactory);
	}

	public static Log getLog(Class<?> c) {
		return getLogFactory().getLog(c);
	}

	public static Log getLog(String name) {
		return getLogFactory().getLog(name);
	}

	private static LogFactoryWrapper _logFactory =
		new LogFactoryWrapper(new Jdk14LogFactoryImpl());

}