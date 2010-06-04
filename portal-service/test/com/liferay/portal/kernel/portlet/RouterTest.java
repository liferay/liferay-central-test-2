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

	public void test() {
		Router router = new Router();

		Route route = new Route();

		route.setPattern("GET/{controller}");

		route.addDefaultParameter("action", "index");
		route.addDefaultParameter("method", "GET");
		route.addDefaultParameter("format", "html");

		router.addRoute(route);

		router.init();

		Map<String, String> parameters = router.urlToParameters(
			"GET/boxes/index");

		System.out.println(parameters.toString());

		String generatedUrl = router.parametersToUrl(parameters);

		System.out.println(generatedUrl);
	}

}