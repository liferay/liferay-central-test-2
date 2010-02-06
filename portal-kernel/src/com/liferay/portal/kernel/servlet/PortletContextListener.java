/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalInitable;
import com.liferay.portal.kernel.util.PortalInitableUtil;
import com.liferay.portal.kernel.util.ServerDetector;

import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.sql.DataSource;

/**
 * <a href="PortletContextListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class PortletContextListener
	implements PortalInitable, ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		HotDeployUtil.fireUndeployEvent(
			new HotDeployEvent(servletContext, contextClassLoader));

		try {
			if (!_bindLiferayPool) {
				return;
			}

			_bindLiferayPool = false;

			if (_log.isDebugEnabled()) {
				_log.debug("Dynamically unbinding the Liferay data source");
			}

			Context context = new InitialContext();

			try {
				context.lookup(_JNDI_JDBC_LIFERAY_POOL);
			}
			catch (NamingException ne) {
				context.unbind(_JNDI_JDBC_LIFERAY_POOL);
			}

			try {
				context.lookup(_JNDI_JDBC);
			}
			catch (NamingException ne) {
				context.destroySubcontext(_JNDI_JDBC);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dynamically unbind the Liferay data source: "
						+ e.getMessage());
			}
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		_servletContext = event.getServletContext();

		Thread currentThread = Thread.currentThread();

		_classLoader = currentThread.getContextClassLoader();

		PortalInitableUtil.init(this);
	}

	public void portalInit() {
		HotDeployUtil.fireDeployEvent(
			new HotDeployEvent(_servletContext, _classLoader));

		try {
			if (ServerDetector.isGlassfish2()) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Dynamically binding the Liferay data source");
			}

			DataSource dataSource = InfrastructureUtil.getDataSource();

			if (dataSource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Abort dynamically binding the Liferay data source " +
							"because it is not available");
				}

				return;
			}

			Context context = new InitialContext();

			try {
				context.lookup(_JNDI_JDBC);
			}
			catch (NamingException ne) {
				context.createSubcontext(_JNDI_JDBC);
			}

			try {
				context.lookup(_JNDI_JDBC_LIFERAY_POOL);
			}
			catch (NamingException ne) {
				try {
					Method method = dataSource.getClass().getMethod(
						"getTargetDataSource");

					dataSource = (DataSource)method.invoke(dataSource);
				}
				catch (NoSuchMethodException nsme) {
				}

				context.bind(_JNDI_JDBC_LIFERAY_POOL, dataSource);
			}

			_bindLiferayPool = true;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dynamically bind the Liferay data source: "
						+ e.getMessage());
			}
		}
	}

	private static final String _JNDI_JDBC = "java_liferay:jdbc";

	private static final String _JNDI_JDBC_LIFERAY_POOL =
		_JNDI_JDBC + "/LiferayPool";

	private static Log _log =
		LogFactoryUtil.getLog(PortletContextListener.class);

	private ServletContext _servletContext;
	private ClassLoader _classLoader;
	private boolean _bindLiferayPool;

}