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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.velocity.LiferayResourceCacheUtil;
import com.liferay.portal.velocity.VelocityContextPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * <a href="ThemeHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 */
public class ThemeHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error registering themes for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering themes for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String[] xmls = new String[] {
			HttpUtil.URLtoString(servletContext.getResource(
				"/WEB-INF/liferay-look-and-feel.xml"))
		};

		if (xmls[0] == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering themes for " + servletContextName);
		}

		List<String> themeIds = ThemeLocalServiceUtil.init(
			servletContextName, servletContext, null, true, xmls,
			event.getPluginPackage());

		// Class loader

		ClassLoader portletClassLoader = event.getContextClassLoader();

		servletContext.setAttribute(
			PortletServlet.PORTLET_CLASS_LOADER, portletClassLoader);

		// Velocity

		VelocityContextPool.put(servletContextName, servletContext);

		// Variables

		_vars.put(servletContextName, themeIds);

		if (_log.isInfoEnabled()) {
			if (themeIds.size() == 1) {
				_log.info(
					"1 theme for " + servletContextName +
						" is available for use");
			}
			else {
				_log.info(
					themeIds.size() + " themes for " + servletContextName +
						" are available for use");
			}
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		List<String> themeIds = _vars.remove(servletContextName);

		if (themeIds != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unregistering themes for " + servletContextName);
			}

			try {
				ThemeLocalServiceUtil.uninstallThemes(themeIds);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		else {
			return;
		}

		// LEP-2057

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			VelocityContextPool.remove(servletContextName);

			LiferayResourceCacheUtil.clear();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		if (_log.isInfoEnabled()) {
			if (themeIds.size() == 1) {
				_log.info(
					"1 theme for " + servletContextName + " was unregistered");
			}
			else {
				_log.info(
					themeIds.size() + " themes for " + servletContextName +
						" was unregistered");
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ThemeHotDeployListener.class);

	private static Map<String, List<String>> _vars =
		new HashMap<String, List<String>>();

}