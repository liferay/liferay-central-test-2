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

import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.UnavailableException;

/**
 * <a href="PortletURLListenerFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletURLListenerFactory {

	public static PortletURLGenerationListener create(
			PortletURLListener portletURLListener)
		throws PortletException {

		return _instance._create(portletURLListener);
	}

	public static void destroy(PortletURLListener portletURLListener) {
		_instance._destroy(portletURLListener);
	}

	private PortletURLListenerFactory() {
		_pool = new ConcurrentHashMap
			<String, Map<String, PortletURLGenerationListener>>();
	}

	private PortletURLGenerationListener _create(
			PortletURLListener portletURLListener)
		throws PortletException {

		PortletApp portletApp = portletURLListener.getPortletApp();

		Map<String, PortletURLGenerationListener>
			portletURLGenerationListeners = _pool.get(
				portletApp.getServletContextName());

		if (portletURLGenerationListeners == null) {
			portletURLGenerationListeners =
				new ConcurrentHashMap<String, PortletURLGenerationListener>();

			_pool.put(
				portletApp.getServletContextName(),
				portletURLGenerationListeners);
		}

		PortletURLGenerationListener portletURLGenerationListener =
			portletURLGenerationListeners.get(
				portletURLListener.getListenerClass());

		if (portletURLGenerationListener == null) {
			if (portletApp.isWARFile()) {
				PortletContextBag portletContextBag = PortletContextBagPool.get(
					portletApp.getServletContextName());

				portletURLGenerationListener =
					portletContextBag.getPortletURLListeners().get(
						portletURLListener.getListenerClass());

				portletURLGenerationListener = _init(
					portletURLListener, portletURLGenerationListener);
			}
			else {
				portletURLGenerationListener = _init(portletURLListener);
			}

			portletURLGenerationListeners.put(
				portletURLListener.getListenerClass(),
				portletURLGenerationListener);
		}

		return portletURLGenerationListener;
	}

	private void _destroy(PortletURLListener portletURLListener) {
		PortletApp portletApp = portletURLListener.getPortletApp();

		Map<String, PortletURLGenerationListener>
			portletURLGenerationListeners = _pool.get(
				portletApp.getServletContextName());

		if (portletURLGenerationListeners == null) {
			return;
		}

		PortletURLGenerationListener portletURLGenerationListener =
			portletURLGenerationListeners.get(
				portletURLListener.getListenerClass());

		if (portletURLGenerationListener == null) {
			return;
		}

		portletURLGenerationListeners.remove(
			portletURLListener.getListenerClass());
	}

	private PortletURLGenerationListener _init(
			PortletURLListener portletURLListener)
		throws PortletException {

		return _init(portletURLListener, null);
	}

	private PortletURLGenerationListener _init(
			PortletURLListener portletURLListener,
			PortletURLGenerationListener portletURLGenerationListener)
		throws PortletException {

		try {
			if (portletURLGenerationListener == null) {
				portletURLGenerationListener =
					(PortletURLGenerationListener)Class.forName(
						portletURLListener.getListenerClass()).newInstance();
			}
		}
		catch (ClassNotFoundException cnofe) {
			throw new UnavailableException(cnofe.getMessage());
		}
		catch (InstantiationException ie) {
			throw new UnavailableException(ie.getMessage());
		}
		catch (IllegalAccessException iae) {
			throw new UnavailableException(iae.getMessage());
		}

		return portletURLGenerationListener;
	}

	private static PortletURLListenerFactory _instance =
		new PortletURLListenerFactory();

	private Map<String, Map<String, PortletURLGenerationListener>> _pool;

}