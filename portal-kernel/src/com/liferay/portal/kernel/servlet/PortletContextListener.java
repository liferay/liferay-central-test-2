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

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.jndi.PortalJNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalInitable;
import com.liferay.portal.kernel.util.PortalInitableUtil;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.sql.DataSource;

/**
 * <a href="PortletContextListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 *
 */
public class PortletContextListener
	implements PortalInitable, ServletContextListener {

	public void portalInit() {
		HotDeployUtil.fireDeployEvent(new HotDeployEvent(_ctx, _classLoader));

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Dynamically binding the Liferay data source");
			}

			DataSource dataSource = PortalJNDIUtil.getDataSource();

			if (dataSource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Abort dynamically binding the Liferay data source " +
							"because it is not available");
				}

				return;
			}

			Context ctx = new InitialContext();

			ctx.createSubcontext(_JNDI_JDBC);

			ctx.bind(_JNDI_JDBC_LIFERAY_POOL, PortalJNDIUtil.getDataSource());

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

	public void contextInitialized(ServletContextEvent event) {
		_classLoader = Thread.currentThread().getContextClassLoader();
		_ctx = event.getServletContext();

		PortalInitableUtil.init(this);
	}

	public void contextDestroyed(ServletContextEvent event) {
		HotDeployUtil.fireUndeployEvent(
			new HotDeployEvent(
				event.getServletContext(),
				Thread.currentThread().getContextClassLoader()));

		try {
			if (!_bindLiferayPool) {
				return;
			}

			_bindLiferayPool = false;

			if (_log.isDebugEnabled()) {
				_log.debug("Dynamically unbinding the Liferay data source");
			}

			Context ctx = new InitialContext();

			ctx.unbind(_JNDI_JDBC_LIFERAY_POOL);

			ctx.destroySubcontext(_JNDI_JDBC);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dynamically unbind the Liferay data source: "
						+ e.getMessage());
			}
		}
	}

	private static final String _JNDI_JDBC = "java_liferay:jdbc";

	private static final String _JNDI_JDBC_LIFERAY_POOL =
		_JNDI_JDBC + "/LiferayPool";

	private static Log _log =
		LogFactoryUtil.getLog(PortletContextListener.class);

	private ClassLoader _classLoader;
	private ServletContext _ctx;
	private boolean _bindLiferayPool;

}