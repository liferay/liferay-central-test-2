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

package com.liferay.portlet;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.portletcontainer.WindowInvoker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.UnavailableException;

import javax.servlet.ServletContext;

/**
 * <a href="PortletInstanceFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletInstanceFactory {

	public static InvokerPortlet create(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		return _instance._create(portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		_instance._destroy(portlet);
	}

	private PortletInstanceFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, InvokerPortlet>>();
	}

	private InvokerPortlet _create(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		boolean instanceable = false;

		if ((portlet.isInstanceable()) &&
			(PortletConstants.getInstanceId(portlet.getPortletId()) != null)) {

			instanceable = true;
		}

		Map<String, InvokerPortlet> portletInstances = _pool.get(
			portlet.getRootPortletId());

		if (portletInstances == null) {
			portletInstances = new ConcurrentHashMap<String, InvokerPortlet>();

			_pool.put(portlet.getRootPortletId(), portletInstances);
		}

		InvokerPortlet instanceInvokerPortletInstance = null;

		if (instanceable) {
			instanceInvokerPortletInstance = portletInstances.get(
				portlet.getPortletId());
		}

		InvokerPortlet rootInvokerPortletInstance = null;

		if (instanceInvokerPortletInstance == null) {
			rootInvokerPortletInstance = portletInstances.get(
				portlet.getRootPortletId());

			if (rootInvokerPortletInstance == null) {
				PortletConfig portletConfig =
					PortletConfigFactory.create(portlet, servletContext);

				PortletApp portletApp = portlet.getPortletApp();

				if (portletApp.isWARFile()) {
					PortletBag portletBag = PortletBagPool.get(
						portlet.getRootPortletId());

					rootInvokerPortletInstance = _init(
						portlet, portletConfig,
						portletBag.getPortletInstance());
				}
				else {
					rootInvokerPortletInstance = _init(portlet, portletConfig);
				}

				portletInstances.put(
					portlet.getRootPortletId(), rootInvokerPortletInstance);
			}
		}

		if (instanceable) {
			if (instanceInvokerPortletInstance == null) {
				javax.portlet.Portlet portletInstance =
					rootInvokerPortletInstance.getPortletInstance();

				PortletConfig portletConfig =
					PortletConfigFactory.create(portlet, servletContext);

				PortletContext portletContext =
					portletConfig.getPortletContext();
				boolean facesPortlet =
					rootInvokerPortletInstance.isFacesPortlet();
				boolean strutsPortlet =
					rootInvokerPortletInstance.isStrutsPortlet();
				boolean strutsBridgePortlet =
					rootInvokerPortletInstance.isStrutsBridgePortlet();

				if (PropsValues.PORTLET_CONTAINER_IMPL_SUN) {
					instanceInvokerPortletInstance = new WindowInvoker(
						portlet, portletInstance, portletConfig, portletContext,
						facesPortlet, strutsPortlet, strutsBridgePortlet);
				}
				else {
					instanceInvokerPortletInstance = new InvokerPortlet(
						portlet, portletInstance, portletConfig, portletContext,
						facesPortlet, strutsPortlet, strutsBridgePortlet);
				}

				portletInstances.put(
					portlet.getPortletId(), instanceInvokerPortletInstance);
			}
		}
		else {
			if (rootInvokerPortletInstance != null) {
				instanceInvokerPortletInstance = rootInvokerPortletInstance;
			}
		}

		return instanceInvokerPortletInstance;
	}

	private void _destroy(Portlet portlet) {
		Map<String, InvokerPortlet> portletInstances = _pool.get(
			portlet.getRootPortletId());

		if (portletInstances == null) {
			return;
		}

		Iterator<Map.Entry<String, InvokerPortlet>> itr =
			portletInstances.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, InvokerPortlet> entry = itr.next();

			String portletId = entry.getKey();
			InvokerPortlet invokerPortletInstance = entry.getValue();

			if (PortletConstants.getInstanceId(portletId) == null) {
				invokerPortletInstance.destroy();

				break;
			}
		}

		_pool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			portletBag.removePortletInstance();
		}

		PortletConfigFactory.destroy(portlet);
		PortletContextFactory.destroy(portlet);

		PortletLocalServiceUtil.destroyPortlet(portlet);
	}

	private InvokerPortlet _init(Portlet portlet, PortletConfig portletConfig)
		throws PortletException {

		return _init(portlet, portletConfig, null);
	}

	private InvokerPortlet _init(
			Portlet portlet, PortletConfig portletConfig,
			javax.portlet.Portlet portletInstance)
		throws PortletException {

		InvokerPortlet invokerPortlet = null;

		try {
			if (portletInstance == null) {
				portletInstance = (javax.portlet.Portlet)
					Class.forName(portlet.getPortletClass()).newInstance();
			}

			PortletContext portletContext = portletConfig.getPortletContext();

			if (PropsValues.PORTLET_CONTAINER_IMPL_SUN) {
				invokerPortlet = new WindowInvoker(
					portlet, portletInstance, portletContext);
			}
			else {
				invokerPortlet = new InvokerPortlet(
					portlet, portletInstance, portletContext);
			}

			invokerPortlet.init(portletConfig);
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

		return invokerPortlet;
	}

	private static PortletInstanceFactory _instance =
		new PortletInstanceFactory();

	private Map<String, Map<String, InvokerPortlet>> _pool;

}