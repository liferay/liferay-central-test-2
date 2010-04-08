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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.UnavailableException;

import javax.servlet.ServletContext;

/**
 * <a href="PortletInstanceFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletInstanceFactoryImpl implements PortletInstanceFactory {

	public PortletInstanceFactoryImpl() {
		_pool = new ConcurrentHashMap<String, Map<String, InvokerPortlet>>();
	}

	public void clear(Portlet portlet) {
		Map<String, InvokerPortlet> portletInstances = _pool.get(
			portlet.getRootPortletId());

		if (portletInstances != null) {
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
		}

		_pool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			if (portletBag != null) {
				portletBag.setPortletInstance(null);
			}
		}
	}

	public InvokerPortlet create(Portlet portlet, ServletContext servletContext)
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

					rootInvokerPortletInstance = init(
						portlet, portletConfig,
						portletBag.getPortletInstance());
				}
				else {
					rootInvokerPortletInstance = init(portlet, portletConfig);
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
				boolean checkAuthToken =
					rootInvokerPortletInstance.isCheckAuthToken();
				boolean facesPortlet =
					rootInvokerPortletInstance.isFacesPortlet();
				boolean strutsPortlet =
					rootInvokerPortletInstance.isStrutsPortlet();
				boolean strutsBridgePortlet =
					rootInvokerPortletInstance.isStrutsBridgePortlet();

				instanceInvokerPortletInstance =
					_internalInvokerPortletPrototype.create(
						portlet, portletInstance, portletConfig, portletContext,
						checkAuthToken, facesPortlet, strutsPortlet,
						strutsBridgePortlet);

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

	public void destroy(Portlet portlet) {
		clear(portlet);

		PortletConfigFactory.destroy(portlet);
		PortletContextFactory.destroy(portlet);

		PortletLocalServiceUtil.destroyPortlet(portlet);
	}

	public void setInternalInvokerPortletPrototype(
		InvokerPortlet internalInvokerPortletPrototype) {

		_internalInvokerPortletPrototype = internalInvokerPortletPrototype;
	}

	protected InvokerPortlet init(Portlet portlet, PortletConfig portletConfig)
		throws PortletException {

		return init(portlet, portletConfig, null);
	}

	protected InvokerPortlet init(
			Portlet portlet, PortletConfig portletConfig,
			javax.portlet.Portlet portletInstance)
		throws PortletException {

		InvokerPortlet invokerPortlet = null;

		try {
			if (portletInstance == null) {
				portletInstance =
					(javax.portlet.Portlet)Class.forName(
						portlet.getPortletClass()).newInstance();
			}

			PortletContext portletContext = portletConfig.getPortletContext();

			invokerPortlet = _internalInvokerPortletPrototype.create(
				portlet, portletInstance, portletContext);

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

	private InvokerPortlet _internalInvokerPortletPrototype;
	private Map<String, Map<String, InvokerPortlet>> _pool;

}