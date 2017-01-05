/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.delegate;

import com.liferay.portal.asm.ASMWrapperUtil;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class ServletContextDelegate {

	public static ServletContext create(ServletContext servletContext) {
		Class<?> clazz = servletContext.getClass();

		return ASMWrapperUtil.createASMWrapper(
			clazz.getClassLoader(), ServletContext.class,
			new ServletContextDelegate(
				servletContext.getContextPath(),
				servletContext.getServletContextName()),
			servletContext);
	}

	public String getContextPath() {
		return _contextPath;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	private ServletContextDelegate(
		String contextPath, String servletContextName) {

		_contextPath = contextPath;
		_servletContextName = servletContextName;
	}

	private final String _contextPath;
	private final String _servletContextName;

}