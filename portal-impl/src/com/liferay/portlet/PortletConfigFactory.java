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

import com.liferay.portal.model.Portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * <a href="PortletConfigFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletConfigFactory {

	public static PortletConfig create(
		Portlet portlet, ServletContext servletContext) {

		return _instance._create(portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		_instance._destroy(portlet);
	}

	private PortletConfigFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, PortletConfig>>();
	}

	private PortletConfig _create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletConfig> portletConfigs =
			_pool.get(portlet.getRootPortletId());

		if (portletConfigs == null) {
			portletConfigs = new ConcurrentHashMap<String, PortletConfig>();

			_pool.put(portlet.getRootPortletId(), portletConfigs);
		}

		PortletConfig portletConfig = portletConfigs.get(
			portlet.getPortletId());

		if (portletConfig == null) {
			PortletContext portletContext =
				PortletContextFactory.create(portlet, servletContext);

			portletConfig = new PortletConfigImpl(portlet, portletContext);

			portletConfigs.put(portlet.getPortletId(), portletConfig);
		}

		return portletConfig;
	}

	private void _destroy(Portlet portlet) {
		_pool.remove(portlet.getRootPortletId());
	}

	private static PortletConfigFactory _instance = new PortletConfigFactory();

	private Map<String, Map<String, PortletConfig>> _pool;

}