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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Zsolt Oláh
 */
public class VirtualHostFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String getPathContext() {
					return _pathContext;
				}

				@Override
				public String getPathProxy() {
					return _pathProxy;
				}

			});

		_virtualHostFilter.init(_mockFilterConfig);
	}

	@Test
	public void testFilterProxyContext() throws Exception {
		_pathContext = _PATH_PROXY + _PATH_CONTEXT;
		_pathProxy = _PATH_PROXY;

		_mockHttpServletRequest.setRequestURI(
			_PATH_CONTEXT + _FRIENDLY_URL);

		String filteredFriendlyURL = getFilteredFriendlyURL(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		Assert.assertEquals(
			_FRIENDLY_URL, filteredFriendlyURL);
	}

	@Test
	public void testFilterProxyFriendlyURLBeginsWithProxy() throws Exception {
		_pathContext = _PATH_PROXY;
		_pathProxy = _PATH_PROXY;

		_mockHttpServletRequest.setRequestURI(
			_FRIENDLY_URL);

		String filteredFriendlyURL = getFilteredFriendlyURL(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		Assert.assertEquals(
			_FRIENDLY_URL, filteredFriendlyURL);
	}

	@Test
	public void testFilterProxyFriendlyUrlBeginsWithProxyEmptyPathProxy()
		throws Exception {

		_pathContext = _PATH_PROXY;
		_pathProxy = "";

		_mockHttpServletRequest.setRequestURI(
			_FRIENDLY_URL);

		String filteredFriendlyURL = getFilteredFriendlyURL(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		Assert.assertEquals(
			_FRIENDLY_URL, filteredFriendlyURL);
	}

	protected String getFilteredFriendlyURL(
			MockHttpServletRequest request, MockHttpServletResponse response,
			MockFilterChain filterChain)
		throws Exception {

		_virtualHostFilter.processFilter(request, response, filterChain);

		LastPath lastPath = (LastPath)request.getAttribute(WebKeys.LAST_PATH);

		if (lastPath != null) {
			return lastPath.getPath();
		}

		return "";
	}

	private static final String _PATH_CONTEXT = "/test_context";

	private static final String _PATH_PROXY = "/test_proxy";

	private static final String _FRIENDLY_URL =
		VirtualHostFilterTest._PATH_PROXY + "_test_friendly_url";

	private MockFilterChain _mockFilterChain = new MockFilterChain();
	private MockFilterConfig _mockFilterConfig = new MockFilterConfig();
	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();
	private String _pathContext;
	private String _pathProxy;
	private VirtualHostFilter _virtualHostFilter = new VirtualHostFilter();

}