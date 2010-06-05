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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.util.InitUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="RouterTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouterTest extends TestCase {

	public RouterTest() {
		InitUtil.initWithSpring();
	}

	public void setUp() {
		_router = new Router();

		Route route = _router.addRoute("GET/{controller}");

		route.addDefaultParameter("action", "index");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "GET");

		route = _router.addRoute("GET/{controller}.{format}");

		route.addDefaultParameter("action", "index");
		route.addDefaultParameter("method", "GET");

		route = _router.addRoute("POST/{controller}");

		route.addDefaultParameter("action", "create");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "POST");

		route = _router.addRoute("POST/{controller}.{format}");

		route.addDefaultParameter("action", "create");
		route.addDefaultParameter("method", "POST");

		route = _router.addRoute("GET/{controller}/{\\d+:id}");

		route.addDefaultParameter("action", "view");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "GET");

		route = _router.addRoute("GET/{controller}/{\\d+:id}.{format}");

		route.addDefaultParameter("action", "view");
		route.addDefaultParameter("method", "GET");

		route = _router.addRoute("POST/{controller}/{\\d+:id}");

		route.addDefaultParameter("action", "update");
		route.addDefaultParameter("format", "html");
		route.addDefaultParameter("method", "POST");

		route = _router.addRoute("POST/{controller}/{\\d+:id}.{format}");

		route.addDefaultParameter("action", "update");
		route.addDefaultParameter("method", "POST");

		route = _router.addRoute("{method}/{controller}/{\\d+:id}/{action}");

		route.addDefaultParameter("format", "html");

		route = _router.addRoute(
			"{method}/{controller}/{\\d+:id}/{action}.{format}");

		route = _router.addRoute("{method}/{controller}/{action}");

		route.addDefaultParameter("format", "html");

		route = _router.addRoute("{method}/{controller}/{action}.{format}");
	}

	public void testPriority() {
		assertEqualsParametersToUrl(
			"GET/boxes/index?place=house", "GET/boxes?place=house");
	}

	public void testQueryStringOverride() {
		assertEqualsParametersToUrl(
			"GET/boxes/5/something?action=nowhere", "GET/boxes/5/something");
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
		assertEqualsParametersToUrl("POST/boxes.xml?hello=something");
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
		Map<String, String> expected, Map<String, String> actual) {

		assertEquals(expected.size(), actual.size());

		for (Map.Entry<String, String> entry : expected.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			assertEquals(value, actual.get(name));
		}
	}

	protected void assertEqualsParametersToUrl(String url) {
		assertEqualsParametersToUrl(url, url);
	}

	protected void assertEqualsParametersToUrl(String url, String expectedUrl) {
		Map<String, String> parameters = _router.urlToParameters(url);

		String generatedUrl = _router.parametersToUrl(parameters);

		assertEquals(expectedUrl, generatedUrl);
	}

	protected void assertEqualsUrlToParameters(String url, String queryString) {
		Map<String, String> parameters = new HashMap<String, String>();

		_router.queryStringToParameters(queryString, parameters);

		Map<String, String> generatedParameters = _router.urlToParameters(url);

		assertEquals(parameters, generatedParameters);
	}

	private Router _router;

}