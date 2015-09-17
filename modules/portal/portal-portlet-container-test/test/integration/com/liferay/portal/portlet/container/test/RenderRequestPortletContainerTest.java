/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.SecurityPortletContainerWrapper;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class RenderRequestPortletContainerTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testRenderRequest_invalidPortletId() throws Exception {
		HttpServletRequest request = getHttpServletRequest();

		String url =
			layout.getRegularURL(request) +
				"?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=0&" +
					"p_p_state=exclusive";

		try (CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				SecurityPortletContainerWrapper.class.getName(), Level.WARN)) {

			Map<String, List<String>> responseMap = request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Invalid portlet ID '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			Assert.assertEquals("200", getString(responseMap, "code"));
		}
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String portletToTarget = "TEST_TARGET";

		properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(new TestPortlet(map), properties, portletToTarget, false);

		properties = new Hashtable<>();

		Map<String, String> ignored = new HashMap<>();

		testPortlet = new TestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PortletURL portletURL = PortletURLFactoryUtil.create(
					resourceRequest, portletToTarget, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String p_p_auth = MapUtil.getString(parameterMap, "p_p_auth");

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(p_p_auth);
			}

		};

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		// Get the p_p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Now make the render request to the target portlet using the p_p_auth
		// parameter

		portletURL = new PortletURLImpl(
			request, portletToTarget, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_p_auth", p_p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletOnPage()
		throws Exception {

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByRuntimePortlet()
		throws Exception {

		Map<String, String> ignored = new HashMap<>();

		testPortlet = new TestPortlet(ignored) {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher requestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				requestDispatcher.include(renderRequest, renderResponse);
			}

		};

		String portletToEmbbed = "TEST_EMBEDDED";

		setUpPortlet(new TestPortlet(map), properties, portletToEmbbed, false);
		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("portletToEmbbed", portletToEmbbed);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("render"));
	}

}