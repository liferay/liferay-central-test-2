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

package com.liferay.httpservice.internal.http;

import com.liferay.httpservice.internal.servlet.BundleServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class FilterTracker extends
	BaseServiceTrackerCustomizer<Filter, Filter> {

	public FilterTracker(HttpSupport httpSupport) {
		_httpSupport = httpSupport;
	}

	public Filter addingService(ServiceReference<Filter> serviceReference) {
		BundleContext bundleContext = _httpSupport.getBundleContext();

		Filter filter = bundleContext.getService(serviceReference);

		return doAction(serviceReference, filter, ACTION_ADDING);
	}

	public void modifiedService(
		ServiceReference<Filter> serviceReference, Filter filter) {

		doAction(serviceReference, filter, ACTION_MODIFIED);
	}

	public void removedService(
		ServiceReference<Filter> serviceReference, Filter filter) {

		doAction(serviceReference, filter, ACTION_REMOVED);
	}

	protected Filter doAction(
		ServiceReference<Filter> serviceReference, Filter filter, int action) {

		String pattern = GetterUtil.getString(
			serviceReference.getProperty("pattern"));

		Map<String, String> initParameters = new HashMap<String, String>();

		if (action != ACTION_REMOVED) {
			for (String key : serviceReference.getPropertyKeys()) {
				if (key.startsWith("init.")) {
					String value = GetterUtil.getString(
						serviceReference.getProperty(key));

					initParameters.put(key.substring(5), value);
				}
			}

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty("service.ranking"));

			initParameters.put(
				"service.ranking", String.valueOf(serviceRanking));
		}

		Bundle bundle = serviceReference.getBundle();

		try {
			BundleServletContext bundleServletContext =
				_httpSupport.getBundleServletContext(bundle);

			if (action != ACTION_ADDING) {
				bundleServletContext.unregisterFilter(pattern);
			}

			if (action != ACTION_REMOVED) {
				String contextId = GetterUtil.getString(
					serviceReference.getProperty("contextId"));

				HttpContext httpContext = _httpSupport.getHttpContext(
					contextId);

				if (httpContext == null) {
					httpContext = bundleServletContext.getHttpContext();
				}

				bundleServletContext.registerFilter(
					pattern, filter, initParameters, httpContext);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return filter;
	}

	private static Log _log = LogFactoryUtil.getLog(FilterTracker.class);

	private HttpSupport _httpSupport;

}