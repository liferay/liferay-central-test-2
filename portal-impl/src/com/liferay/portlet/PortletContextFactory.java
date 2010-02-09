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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.velocity.VelocityContextPool;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * <a href="PortletContextFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletContextFactory {

	public static PortletContext create(
		Portlet portlet, ServletContext servletContext) {

		return _instance._create(portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		_instance._destroy(portlet);
	}

	private PortletContextFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, PortletContext>>();
	}

	private PortletContext _create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletContext> portletContexts = _pool.get(
			portlet.getRootPortletId());

		if (portletContexts == null) {
			portletContexts = new ConcurrentHashMap<String, PortletContext>();

			_pool.put(portlet.getRootPortletId(), portletContexts);
		}

		PortletContext portletContext =
			portletContexts.get(portlet.getPortletId());

		if (portletContext == null) {
			PortletApp portletApp = portlet.getPortletApp();

			if (portletApp.isWARFile()) {
				PortletBag portletBag = PortletBagPool.get(
					portlet.getRootPortletId());

				if (portletBag == null) {
					_log.error(
						"Portlet " + portlet.getRootPortletId() +
							" has a null portlet bag");
				}

				//String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);

				servletContext = portletBag.getServletContext();

				// Context path for the portal must be passed to individual
				// portlets

				//ctx.setAttribute(WebKeys.MAIN_PATH, mainPath);
			}

			portletContext = new PortletContextImpl(portlet, servletContext);

			VelocityContextPool.put(
				portletContext.getPortletContextName(), servletContext);

			portletContexts.put(portlet.getPortletId(), portletContext);
		}

		return portletContext;
	}

	private void _destroy(Portlet portlet) {
		Map<String, PortletContext> portletContexts = _pool.remove(
			portlet.getRootPortletId());

		if (portletContexts == null) {
			return;
		}

		Iterator<Map.Entry<String, PortletContext>> itr =
			portletContexts.entrySet().iterator();

		if (itr.hasNext()) {
			Map.Entry<String, PortletContext> entry = itr.next();

			PortletContext portletContext = entry.getValue();

			VelocityContextPool.remove(portletContext.getPortletContextName());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletContextFactory.class);

	private static PortletContextFactory _instance =
		new PortletContextFactory();

	private Map<String, Map<String, PortletContext>> _pool;

}