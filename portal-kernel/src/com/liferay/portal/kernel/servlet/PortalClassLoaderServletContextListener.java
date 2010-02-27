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
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalInitable;
import com.liferay.portal.kernel.util.PortalInitableUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <a href="PortalClassLoaderServletContextListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public abstract class PortalClassLoaderServletContextListener
	implements PortalInitable, ServletContextListener {

	public PortalClassLoaderServletContextListener() {
	}

	public void contextDestroyed(ServletContextEvent event) {
		PortletClassLoaderUtil.setClassLoader(_portletClassLoader);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener.contextDestroyed(event);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		_event = event;

		Thread currentThread = Thread.currentThread();

		_portletClassLoader = currentThread.getContextClassLoader();

		PortalInitableUtil.init(this);
	}

	public void portalInit() {
		PortletClassLoaderUtil.setClassLoader(_portletClassLoader);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener = getInstance();

			_servletContextListener.contextInitialized(_event);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected abstract ServletContextListener getInstance() throws Exception;

	private static Log _log = LogFactoryUtil.getLog(
		PortalClassLoaderServletContextListener.class);

	private ServletContextEvent _event;
	private ServletContextListener _servletContextListener;
	private ClassLoader _portletClassLoader;

}