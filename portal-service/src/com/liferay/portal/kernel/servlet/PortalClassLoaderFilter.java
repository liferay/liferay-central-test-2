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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalClassLoaderFilter
	extends BasePortalLifecycle implements Filter {

	public void destroy() {
		portalDestroy();
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			FilterChain contextClassLoaderFilterChain =
				(FilterChain)Proxy.newProxyInstance(
					contextClassLoader, new Class[] {FilterChain.class},
					new ClassLoaderBeanHandler(
						filterChain, contextClassLoader));

			_filter.doFilter(
				servletRequest, servletResponse, contextClassLoaderFilterChain);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void init(FilterConfig filterConfig) {
		_filterConfig = filterConfig;

		registerPortalLifecycle();
	}

	protected void doPortalDestroy() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_filter.destroy();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected void doPortalInit() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String filterClass = _filterConfig.getInitParameter("filter-class");

		if (filterClass.startsWith("com.liferay.filters.")) {
			filterClass = StringUtil.replace(
				filterClass, "com.liferay.filters.",
				"com.liferay.portal.servlet.filters.");
		}

		_filter = (Filter)classLoader.loadClass(filterClass).newInstance();

		_filter.init(_filterConfig);
	}

	private Filter _filter;
	private FilterConfig _filterConfig;

}