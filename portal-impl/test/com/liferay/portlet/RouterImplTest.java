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

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.util.BaseTestCase;
import com.liferay.portal.util.InitUtil;

import java.util.Map;

/**
 * <a href="RouterImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouterImplTest extends BaseTestCase {

	public RouterImplTest() {
		InitUtil.initWithSpring();
	}

	public void setUp() {
		_routerImpl = new RouterImpl();

		Route route = _routerImpl.addRoute("GET/{controller}");

		route.addDefaultParameter("action", "index");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "GET");

		route = _routerImpl.addRoute("GET/{controller}.{format}");

		route.addDefaultParameter("action", "index");
		route.addDefaultParameter("method", "GET");

		route = _routerImpl.addRoute("POST/{controller}");

		route.addDefaultParameter("action", "create");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "POST");

		route = _routerImpl.addRoute("POST/{controller}.{format}");

		route.addDefaultParameter("action", "create");
		route.addDefaultParameter("method", "POST");

		route = _routerImpl.addRoute("GET/{controller}/{id:\\d+}");

		route.addDefaultParameter("action", "view");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "GET");

		route = _routerImpl.addRoute("GET/{controller}/{id:\\d+}.{format}");

		route.addDefaultParameter("action", "view");
		route.addDefaultParameter("method", "GET");

		route = _routerImpl.addRoute("POST/{controller}/{id:\\d+}");

		route.addDefaultParameter("action", "update");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "POST");

		route = _routerImpl.addRoute("POST/{controller}/{id:\\d+}.{format}");

		route.addDefaultParameter("action", "update");
		route.addDefaultParameter("method", "POST");

		route = _routerImpl.addRoute(
			"{method}/{controller}/{id:\\d+}/{action}");

		route.addDefaultParameter("format", "html");

		route = _routerImpl.addRoute(
			"{method}/{controller}/{id:\\d+}/{action}.{format}");

		route = _routerImpl.addRoute("{method}/{controller}/{action}");

		route.addDefaultParameter("format", "html");

		route = _routerImpl.addRoute("{method}/{controller}/{action}.{format}");
	}

	public void testPriority() {
		assertEqualsParametersToUrl("GET/boxes/index", "GET/boxes");
	}

	public void testReproduction() {
		assertEqualsParametersToUrl("GET/boxes/16");
		assertEqualsParametersToUrl("GET/boxes/25.xml");
		assertEqualsParametersToUrl("POST/boxes/8");
		assertEqualsParametersToUrl("POST/boxes/34.xml");
		assertEqualsParametersToUrl("GET/boxes/new");
		assertEqualsParametersToUrl("GET/boxes/8/export");
		assertEqualsParametersToUrl("GET/boxes");
		assertEqualsParametersToUrl("GET/boxes.xml");
		assertEqualsParametersToUrl("POST/boxes");
		assertEqualsParametersToUrl("POST/boxes.xml");
	}

	public void testUrlToParameters() {
		assertEqualsUrlToParameters(
			"GET/boxes/16",
			"id=16&action=view&method=GET&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"GET/boxes/25.xml",
			"id=25&action=view&method=GET&controller=boxes&format=xml");
		assertEqualsUrlToParameters(
			"POST/boxes/8",
			"id=8&action=update&method=POST&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"POST/boxes/34.xml",
			"id=34&action=update&method=POST&controller=boxes&format=xml");
		assertEqualsUrlToParameters(
			"GET/boxes/new",
			"action=new&method=GET&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"GET/boxes/8/export",
			"id=8&action=export&method=GET&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"GET/boxes",
			"action=index&method=GET&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"GET/boxes.xml",
			"action=index&method=GET&controller=boxes&format=xml");
		assertEqualsUrlToParameters(
			"POST/boxes",
			"action=create&method=POST&format=html&controller=boxes");
		assertEqualsUrlToParameters(
			"POST/boxes.xml",
			"action=create&method=POST&controller=boxes&format=xml");
	}

	protected void assertEquals(
		Map<String, ?> expected, Map<String, ?> actual) {

		assertEquals(expected.size(), actual.size());

		for (String name : expected.keySet()) {
			assertEquals(
				MapUtil.getString(expected, name),
				MapUtil.getString(actual, name));
		}
	}

	protected void assertEqualsParametersToUrl(String url) {
		assertEqualsParametersToUrl(url, url);
	}

	protected void assertEqualsParametersToUrl(String url, String expectedUrl) {
		Map<String, String> parameters = _routerImpl.urlToParameters(url);

		String generatedUrl = _routerImpl.parametersToUrl(parameters);

		assertEquals(expectedUrl, generatedUrl);
	}

	protected void assertEqualsUrlToParameters(String url, String queryString) {
		Map<String, String[]> parameters = HttpUtil.parameterMapFromString(
			queryString);

		Map<String, String> generatedParameters = _routerImpl.urlToParameters(
			url);

		assertEquals(parameters, generatedParameters);
	}

	private RouterImpl _routerImpl;

}