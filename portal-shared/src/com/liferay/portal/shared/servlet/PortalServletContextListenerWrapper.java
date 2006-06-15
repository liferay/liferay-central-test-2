/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.shared.servlet;

import com.liferay.portal.shared.log.Log;
import com.liferay.portal.shared.log.LogFactoryUtil;
import com.liferay.portal.shared.util.PortalClassLoaderUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <a href="PortalServletContextListenerWrapper.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public abstract class PortalServletContextListenerWrapper
	implements ServletContextListener {

	public PortalServletContextListenerWrapper() {
		try {
			_servletContextListener = getInstance();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener.contextDestroyed(sce);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener.contextInitialized(sce);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	protected abstract ServletContextListener getInstance() throws Exception;

	private static Log _log =
		LogFactoryUtil.getLog(PortalServletContextListenerWrapper.class);

	private ServletContextListener _servletContextListener;

}