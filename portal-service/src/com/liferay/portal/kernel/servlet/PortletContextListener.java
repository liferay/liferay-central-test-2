/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.ServerDetector;

import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class PortletContextListener extends PluginContextListener {

	@Override
	protected void doPortalDestroy() {
		PortletContextLifecycleThreadLocal.setDestroying(true);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != pluginClassLoader) {
			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			HotDeployUtil.fireUndeployEvent(
				new HotDeployEvent(servletContext, pluginClassLoader));

			_unbindDataSource();
		}
		finally {
			PortletContextLifecycleThreadLocal.setDestroying(false);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	protected void doPortalInit() throws Exception {
		PortletContextLifecycleThreadLocal.setInitializing(true);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != pluginClassLoader) {
			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			HotDeployUtil.fireDeployEvent(
				new HotDeployEvent(servletContext, pluginClassLoader));

			_bindDataSource();
		}
		finally {
			PortletContextLifecycleThreadLocal.setInitializing(false);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private void _bindDataSource() throws Exception {
		if (ServerDetector.isGlassfish() || ServerDetector.isJOnAS()) {
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
					Class<?> clazz = dataSource.getClass();

					Method method = clazz.getMethod("getTargetDataSource");

					dataSource = (DataSource)method.invoke(dataSource);
				}
				catch (NoSuchMethodException nsme) {
				}

				context.bind(_JNDI_JDBC_LIFERAY_POOL, dataSource);
			}

			_bindDataSource = true;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dynamically bind the Liferay data source: " +
						e.getMessage());
			}
		}
	}

	private void _unbindDataSource() {
		if (!_bindDataSource) {
			return;
		}

		try {
			_bindDataSource = false;

			if (_log.isDebugEnabled()) {
				_log.debug("Dynamically unbinding the Liferay data source");
			}

			Context context = new InitialContext();

			try {
				context.lookup(_JNDI_JDBC_LIFERAY_POOL);

				context.unbind(_JNDI_JDBC_LIFERAY_POOL);
			}
			catch (NamingException ne) {
			}

			try {
				context.lookup(_JNDI_JDBC);

				context.destroySubcontext(_JNDI_JDBC);
			}
			catch (NamingException ne) {
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dynamically unbind the Liferay data source: " +
						e.getMessage());
			}
		}
	}

	private static final String _JNDI_JDBC = "java_liferay:jdbc";

	private static final String _JNDI_JDBC_LIFERAY_POOL =
		_JNDI_JDBC + "/LiferayPool";

	private static Log _log = LogFactoryUtil.getLog(
		PortletContextListener.class);

	private boolean _bindDataSource;

}