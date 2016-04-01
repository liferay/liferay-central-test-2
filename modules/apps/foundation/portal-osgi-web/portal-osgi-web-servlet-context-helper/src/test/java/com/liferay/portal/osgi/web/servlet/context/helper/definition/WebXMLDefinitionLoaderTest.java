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

package com.liferay.portal.osgi.web.servlet.context.helper.definition;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.definition.WebXMLDefinitionLoader;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.order.OrderImpl;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.order.OrderUtil;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order;
import com.liferay.portal.osgi.web.servlet.context.helper.order.OrderBeforeAndAfterException;
import com.liferay.portal.osgi.web.servlet.context.helper.order.OrderCircularDependencyException;

import java.net.URL;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.osgi.framework.Bundle;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class WebXMLDefinitionLoaderTest {

	@Test
	public void testLoadCustomDependencies() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, entryLoaderMockBundle.getEntry());
	}

	@Test
	public void testLoadCustomDependenciesAbsoluteOrder() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		List<String> absoluteOrderNames = new ArrayList<>();

		absoluteOrderNames.add("fragment2");
		absoluteOrderNames.add("fragment1");
		absoluteOrderNames.add(Order.OTHERS);

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, entryLoaderMockBundle.getEntry(),
			null, null, absoluteOrderNames);
	}

	@Test
	public void testLoadCustomWebFragment1Dependencies() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 0, entryLoaderMockBundle.getEntry(),
			"fragment1", null, null);
	}

	@Test
	public void testLoadCustomWebFragment2Dependencies() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		Order order = new OrderImpl();

		EnumMap<Order.Path, String[]> routes = order.getRoutes();

		routes.put(Order.Path.AFTER, new String[] {"fragment1"});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, entryLoaderMockBundle.getEntry(),
			"fragment2", order, null);
	}

	@Test
	public void testLoadCustomWebFragment3Dependencies() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-fragment-4.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		Order order = new OrderImpl();

		EnumMap<Order.Path, String[]> routes = order.getRoutes();

		routes.put(Order.Path.BEFORE, new String[] {Order.OTHERS});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, entryLoaderMockBundle.getEntry(),
			"fragment4", order, null);
	}

	@Test
	public void testLoadDefaultDependencies() throws Exception {
		Bundle bundle = new MockBundle();

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0,
			bundle.getEntry("WEB-INF/web.xml"));
	}

	@Test
	public void testLoadMetadataComplete() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		Assert.assertTrue(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testLoadNotMetadataComplete() throws Exception {
		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		Assert.assertFalse(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testOrderBeforeAndAfterException() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle fragment5EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-5.xml");

		WebXMLDefinitionLoader fragment5WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment5EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment5WebXMLDefinition =
			fragment5WebXMLDefinitionLoader.loadWebXML(
				fragment5EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment5WebXMLDefinition);

		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		boolean threwOrderBeforeAndAfterException = false;

		try {
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());
		}
		catch (Exception e) {
			if (e instanceof OrderBeforeAndAfterException) {
				threwOrderBeforeAndAfterException = true;
			}
		}

		Assert.assertTrue(threwOrderBeforeAndAfterException);
	}

	@Test
	public void testOrderCircularDependencyException() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle circular1EntryLoaderMockBundle =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-fragment-circular-1.xml");

		WebXMLDefinitionLoader circular1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				circular1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition circular1WebXMLDefinition =
			circular1WebXMLDefinitionLoader.loadWebXML(
				circular1EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(circular1WebXMLDefinition);

		EntryLoaderMockBundle circular2EntryLoaderMockBundle =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-fragment-circular-2.xml");

		WebXMLDefinitionLoader circular2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				circular2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition circular2WebXMLDefinition =
			circular2WebXMLDefinitionLoader.loadWebXML(
				circular2EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(circular2WebXMLDefinition);

		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		boolean threwOrderCircularDependencyException = false;

		try {
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());
		}
		catch (Exception e) {
			if (e instanceof OrderCircularDependencyException) {
				threwOrderCircularDependencyException = true;
			}
		}

		Assert.assertTrue(threwOrderCircularDependencyException);
	}

	@Test
	public void testSortFragments1() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle fragment3EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		EntryLoaderMockBundle fragment1EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		EntryLoaderMockBundle fragment2EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment2WebXMLDefinition);

		EntryLoaderMockBundle absolute1EntryLoaderMockBundle =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader absolute1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				absolute1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition absolute1WebXMLDefinition =
			absolute1WebXMLDefinitionLoader.loadWebXML(
				absolute1EntryLoaderMockBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions,
				absolute1WebXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, orderedWebXMLDefinitions.size());

		WebXMLDefinition firstWebXMLDefinition = orderedWebXMLDefinitions.get(
			0);

		Assert.assertEquals(
			"fragment2", firstWebXMLDefinition.getFragmentName());

		WebXMLDefinition secondWebXMLDefinition = orderedWebXMLDefinitions.get(
			1);

		Assert.assertEquals(
			"fragment1", secondWebXMLDefinition.getFragmentName());

		WebXMLDefinition thirdWebXMLDefinition = orderedWebXMLDefinitions.get(
			2);

		Assert.assertEquals(
			"fragment3", thirdWebXMLDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments2() throws Exception {
		EntryLoaderMockBundle absolute2EntryLoaderMockBundle =
			new EntryLoaderMockBundle(
				"dependencies/custom-web-absolute-ordering-2.xml");

		WebXMLDefinitionLoader absolute2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				absolute2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition absolute2WebXMLDefinition =
			absolute2WebXMLDefinitionLoader.loadWebXML(
				absolute2EntryLoaderMockBundle.getEntry());

		EntryLoaderMockBundle fragment1EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1EntryLoaderMockBundle.getEntry());

		EntryLoaderMockBundle fragment2EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2EntryLoaderMockBundle.getEntry());

		EntryLoaderMockBundle fragment3EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3EntryLoaderMockBundle.getEntry());

		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();
		webXMLDefinitions.add(fragment3WebXMLDefinition);
		webXMLDefinitions.add(fragment2WebXMLDefinition);
		webXMLDefinitions.add(fragment1WebXMLDefinition);

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions,
				absolute2WebXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(2, orderedWebXMLDefinitions.size());

		WebXMLDefinition firstWebXMLDefinition = orderedWebXMLDefinitions.get(
			0);

		Assert.assertEquals(
			"fragment1", firstWebXMLDefinition.getFragmentName());

		WebXMLDefinition secondWebXMLDefinition = orderedWebXMLDefinitions.get(
			1);

		Assert.assertEquals(
			"fragment2", secondWebXMLDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments3() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle fragment3EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		EntryLoaderMockBundle fragment2EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment2WebXMLDefinition);

		EntryLoaderMockBundle fragment1EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, orderedWebXMLDefinitions.size());

		WebXMLDefinition firstWebXMLDefinition = orderedWebXMLDefinitions.get(
			0);

		Assert.assertEquals(
			"fragment1", firstWebXMLDefinition.getFragmentName());

		WebXMLDefinition secondWebXMLDefinition = orderedWebXMLDefinitions.get(
			1);

		Assert.assertEquals(
			"fragment3", secondWebXMLDefinition.getFragmentName());

		WebXMLDefinition thirdWebXMLDefinition = orderedWebXMLDefinitions.get(
			2);

		Assert.assertEquals(
			"fragment2", thirdWebXMLDefinition.getFragmentName());
	}

	@Test
	public void testSortFragments4() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle fragment2EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2webXMLDefinition =
			fragment2webXMLDefinitionLoader.loadWebXML(
				fragment2EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment2webXMLDefinition);

		EntryLoaderMockBundle fragment1EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		EntryLoaderMockBundle fragment4EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-4.xml");

		WebXMLDefinitionLoader fragment4WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment4EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment4WebXMLDefinition =
			fragment4WebXMLDefinitionLoader.loadWebXML(
				fragment4EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment4WebXMLDefinition);

		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(3, orderedWebXMLDefinitions.size());

		WebXMLDefinition firstWebXMLDefinition = orderedWebXMLDefinitions.get(
			0);

		Assert.assertEquals(
			"fragment4", firstWebXMLDefinition.getFragmentName());

		WebXMLDefinition secondWebXMLDefinition = orderedWebXMLDefinitions.get(
			1);

		Assert.assertEquals(
			"fragment1", secondWebXMLDefinition.getFragmentName());

		WebXMLDefinition thirdWebXMLDefinition = orderedWebXMLDefinitions.get(
			2);

		Assert.assertEquals(
			"fragment2", thirdWebXMLDefinition.getFragmentName());
	}

	@Test
	public void testUnsortedFragments() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		EntryLoaderMockBundle fragment1EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		EntryLoaderMockBundle fragment3EntryLoaderMockBundle =
			new EntryLoaderMockBundle("dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3EntryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3EntryLoaderMockBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		EntryLoaderMockBundle entryLoaderMockBundle = new EntryLoaderMockBundle(
			"dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				entryLoaderMockBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			entryLoaderMockBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());

		Assert.assertEquals(2, orderedWebXMLDefinitions.size());

		WebXMLDefinition firstWebXMLDefinition = orderedWebXMLDefinitions.get(
			0);

		Assert.assertEquals(
			"fragment1", firstWebXMLDefinition.getFragmentName());

		WebXMLDefinition secondWebXMLDefinition = orderedWebXMLDefinitions.get(
			1);

		Assert.assertEquals(
			"fragment3", secondWebXMLDefinition.getFragmentName());
	}

	protected void testLoadDependencies(
			WebXMLDefinitionLoader webXMLDefinitionLoader, int numfOfListeners,
			int numOfFilters, int numfOfServlets, URL webXML)
		throws Exception {

		testLoadDependencies(
			webXMLDefinitionLoader, numfOfListeners, numOfFilters,
			numfOfServlets, webXML, null, null, null);
	}

	protected void testLoadDependencies(
			WebXMLDefinitionLoader webXMLDefinitionLoader, int numfOfListeners,
			int numOfFilters, int numfOfServlets, URL webXML,
			String fragmentName, Order order, List<String> absoluteOrderNames)
		throws Exception {

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			webXML);

		if (Validator.isNotNull(fragmentName)) {
			Assert.assertEquals(
				fragmentName, webXMLDefinition.getFragmentName());
		}

		if (order != null) {
			EnumMap<Order.Path, String[]> expectedRoutes = order.getRoutes();

			Order webXMLDefinitionOrder = webXMLDefinition.getOrder();

			EnumMap<Order.Path, String[]> actualRoutes =
				webXMLDefinitionOrder.getRoutes();

			Assert.assertArrayEquals(
				expectedRoutes.get(Order.Path.AFTER),
				actualRoutes.get(Order.Path.AFTER));
			Assert.assertArrayEquals(
				expectedRoutes.get(Order.Path.BEFORE),
				actualRoutes.get(Order.Path.BEFORE));
		}

		if (ListUtil.isNotEmpty(absoluteOrderNames)) {
			List<String> webXMLDefinitionAbsoluteOrderNames =
				webXMLDefinition.getAbsoluteOrderNames();

			Assert.assertArrayEquals(
				absoluteOrderNames.toArray(new String[0]),
				webXMLDefinitionAbsoluteOrderNames.toArray(new String[0]));
		}

		List<ListenerDefinition> listenerDefinitions =
			webXMLDefinition.getListenerDefinitions();

		Assert.assertEquals(numfOfListeners, listenerDefinitions.size());

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			EventListener eventListener = listenerDefinition.getEventListener();

			Assert.assertTrue(eventListener instanceof ServletContextListener);
		}

		Map<String, ServletDefinition> servletDefinitions =
			webXMLDefinition.getServletDefinitions();

		Assert.assertEquals(numfOfServlets, servletDefinitions.size());

		Map<String, FilterDefinition> filterDefinitions =
			webXMLDefinition.getFilterDefinitions();

		Assert.assertEquals(numOfFilters, filterDefinitions.size());
	}

	@Mock
	private Servlet _servlet;

	@Mock
	private ServletContextListener _servletContextListener;

	private static class EntryLoaderMockBundle extends MockBundle {

		public EntryLoaderMockBundle(String path) {
			_path = path;
		}

		public URL getEntry() {
			Class<?> clazz = getClass();

			return clazz.getResource(_path);
		}

		private final String _path;

	}

}