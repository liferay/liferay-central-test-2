/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletResourceAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.testng.Assert;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(PowerMockRunner.class)
public class ComboServletStaticURLGeneratorTest extends PowerMockito {

	private static final String URL_PREFIX = "/combo?minifier=";

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();
		
		htmlUtil.setHtml(new HtmlImpl());

		HttpUtil httpUtil = new HttpUtil();
		
		httpUtil.setHttp(new HttpImpl());

		PortalUtil portalUtil = new PortalUtil();
		
		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testDoNotReturnSomeVisitedResources() {
		Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(
			SetUtil.fromArray(new String[] {"/css/main.css"}));

		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(urls, URL_PREFIX + "&/css/main1.css&t=0");
	}

	@Test
	public void testDoNotReturnVisitedExternalResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css",
			"http://www.terminus.com/main.css",
			"http://www.terminus.com/main2.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(
			SetUtil.fromArray(
				new String[] {
					"http://www.terminus.com/main.css", "/css/main.css"
				}));
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(
			urls, "http://www.terminus.com/main2.css",
			URL_PREFIX + "&/css/main1.css&t=0");
	}

	@Test
	public void testDoNotReturnVisitedResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(
			SetUtil.fromArray(new String[] {"/css/main.css"}));
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		Assert.assertEquals(urls, new ArrayList<String>());
	}

	@Test
	public void testFilterIsHonored() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		final Portlet portlet2 = createPortletWithContextPathAndResources(
			"/pluginContextPath2", "/css/main2.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);

		HashSet<String> visited = new HashSet<String>();

		comboServletStaticURLGenerator.setVisitedURLs(visited);
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		comboServletStaticURLGenerator.setPredicateFilter(PredicateFilter.NONE);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		Assert.assertTrue(urls.isEmpty());
	}

	@Test
	public void testPortletWithStaticContextPortletResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortletCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(
			urls, URL_PREFIX + "&/pluginContextPath:/css/main.css" +
			"&/pluginContextPath:/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalExternalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "http://www.someserver.com/someResource.css",
			"/css/main.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(
			urls, "http://www.someserver.com/someResource.css",
			URL_PREFIX + "&/css/main.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalOnlyExternalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "http://www.someserver.com/someResource.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(urls, "http://www.someserver.com/someResource.css");
	}

	@Test
	public void testPortletWithStaticPortalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(
			urls, URL_PREFIX + "&/css/main.css&/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalResourceNotAffectedByOrder() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main1.css", "/css/main.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(
			urls, URL_PREFIX + "&/css/main.css&/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalResourceNotAffectedByPortletOrder() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main1.css", "/css/main.css");
		final Portlet portlet2 = createPortletWithContextPathAndResources(
			"/pluginContextPath2", "/css/main2.css", "/css/main3.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> first = comboServletStaticURLGenerator.generate(
			portlets(portlet, portlet2));

		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Collection<String> second = comboServletStaticURLGenerator.generate(
			portlets(portlet2, portlet));

		Assert.assertEquals(first, second);
	}

	@Test
	public void testTimestamp() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		portlet.setTimestamp(10000L);

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(urls, URL_PREFIX + "&/css/main.css&t=10000");
	}

	@Test
	public void testTimestampReverse() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		portlet.setTimestamp(10000L);

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setTimestamp(20000L);
		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		Collection<String> urls = comboServletStaticURLGenerator.generate(
			portlets(portlet));

		assertURLs(urls, URL_PREFIX + "&/css/main.css&t=20000");
	}

	@Test
	public void testUpdateVisitedResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");

		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.headerPortalCss);

		HashSet<String> visited = new HashSet<String>();
		comboServletStaticURLGenerator.setVisitedURLs(visited);
		comboServletStaticURLGenerator.setURLPrefix(URL_PREFIX);

		comboServletStaticURLGenerator.generate(portlets(portlet));

		Assert.assertTrue(visited.contains("/css/main.css"));
	}

	private void assertURLs(Collection<String> urlCollection, String ... urls) {
		Assert.assertEquals(urlCollection, Arrays.asList(urls));
	}

	private Portlet createPortletWithContextPathAndResources(
		String contextPath, String... resources) {

		PortletImpl portlet = spy(new PortletImpl());
		portlet.setPortletName(contextPath);
		doReturn(contextPath).when(portlet).getContextPath();

		List<String> resourcesList = Arrays.asList(resources);

		portlet.setHeaderPortalCss(resourcesList);
		portlet.setFooterPortalCss(resourcesList);
		portlet.setHeaderPortletCss(resourcesList);
		portlet.setFooterPortletCss(resourcesList);
		portlet.setHeaderPortalJavaScript(resourcesList);
		portlet.setFooterPortalJavaScript(resourcesList);
		portlet.setHeaderPortletJavaScript(resourcesList);
		portlet.setFooterPortletJavaScript(resourcesList);

		return portlet;
	}

	private <T> List<T> portlets(T ... elements) {
		return Arrays.asList(elements);
	}

}