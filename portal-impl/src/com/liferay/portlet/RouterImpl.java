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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.RouteNotFoundException;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <a href="RouterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouterImpl implements Router {

	public Route addRoute(String pattern) {
		Route route = new RouteImpl(pattern);

		_routes.add(route);

		return route;
	}

	public String parametersToUrl(Map<String, ?> parameters)
		throws RouteNotFoundException {

		for (Route route : _routes) {
			String url = route.parametersToUrl(parameters);

			if (url != null) {
				return url;
			}
		}

		throw new RouteNotFoundException(
			"No route could be found to use for parameters " +
				MapUtil.toString(parameters));
	}

	public Map<String, String> urlToParameters(String url)
		throws RouteNotFoundException {

		for (Route route : _routes) {
			Map<String, String> parameters = route.urlToParameters(url);

			if (parameters != null) {
				return parameters;
			}
		}

		throw new RouteNotFoundException(
			"No route could be found to match url " + url);
	}

	private static Log _log = LogFactoryUtil.getLog(Router.class);

	private List<Route> _routes = new ArrayList<Route>();

}