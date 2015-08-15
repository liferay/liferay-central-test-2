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
public class ResourceRequestPortletContainerTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testInvalidPortletId() throws Exception {
		HttpServletRequest httpServletRequest = getHttpServletRequest();

		String layoutURL = layout.getRegularURL(httpServletRequest);

		String url =
			layoutURL +
				"?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=2&";

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					SecurityPortletContainerWrapper.class.getName(),
					Level.WARN)) {

			Map<String, List<String>> responseMap = request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Invalid portlet ID '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			loggingEvent = loggingEvents.get(1);

			Assert.assertEquals(
				"Reject serveResource for " + layoutURL +
					" on '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			Assert.assertEquals("400", getString(responseMap, "code"));
		}
	}

	@Test
	public void testIsAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String testTargetPortletId = "testTargetPortletId";

		properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(
			new TestPortlet(map), properties, testTargetPortletId, false);

		testPortlet = new TestPortlet() {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PrintWriter printWriter = resourceResponse.getWriter();

				PortletURL portletURL = PortletURLFactoryUtil.create(
					resourceRequest, testTargetPortletId, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String portletAuthenticationToken = MapUtil.getString(
					parameterMap, "p_p_auth");

				printWriter.write(portletAuthenticationToken);
			}

		};

		properties = new Hashtable<>();

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		// Get the portlet authentication token by making a resource request

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String portletAuthenticationToken = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Make a resource request to the target portlet using the portlet
		// authentication token

		portletURL = new PortletURLImpl(
			httpServletRequest, testTargetPortletId, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(
			url, "p_p_auth", portletAuthenticationToken);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("serveResource"));
	}

	@Test
	public void testIsAccessGrantedByPortletOnPage() throws Exception {
		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("serveResource"));
	}

	@Test
	public void testIsAccessGrantedByRuntimePortlet() throws Exception {
		testPortlet = new TestPortlet() {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				portletRequestDispatcher.include(
					resourceRequest, resourceResponse);
			}

		};

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		String testRuntimePortletId = "testRuntimePortletId";

		setUpPortlet(
			new TestPortlet(map), properties, testRuntimePortletId, false);

		portletURL.setParameter("testRuntimePortletId", testRuntimePortletId);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("render"));
	}

}