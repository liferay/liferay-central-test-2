/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <a href="ServletContextListenerExecutor.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class ServletContextListenerExecutor
	implements ServletContextListener {

	public ServletContextListenerExecutor() {
	}

	public void contextDestroyed(ServletContextEvent event) {
		if (_servletContextListener != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Destroying servlet context listener instance");
			}

			_servletContextListener.contextDestroyed(event);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not destroying servlet context listener instance " +
						"because it is null");
			}
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		try {
			_servletContextListener = getInstance();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_servletContextListener != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing servlet context listener instance");
			}

			_servletContextListener.contextInitialized(event);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not initializing servlet context listener instance " +
						"because it is null");
			}
		}
	}

	protected abstract ServletContextListener getInstance() throws Exception;

	private static Log _log =
		LogFactoryUtil.getLog(ServletContextListenerExecutor.class);

	private ServletContextListener _servletContextListener;

}