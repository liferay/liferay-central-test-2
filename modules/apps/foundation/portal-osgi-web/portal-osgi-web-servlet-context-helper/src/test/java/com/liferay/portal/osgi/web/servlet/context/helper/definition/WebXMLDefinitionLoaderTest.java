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
	public void testLoadCustomWebXML() throws Exception {
		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, testBundle.getEntry());
	}

	@Test
	public void testLoadCustomWebAbsoluteOrdering1XML() throws Exception {
		TestBundle testBundle = new TestBundle(
			"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		List<String> absoluteOrderingNames = new ArrayList<>();

		absoluteOrderingNames.add("fragment2");
		absoluteOrderingNames.add("fragment1");
		absoluteOrderingNames.add(Order.OTHERS);

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 1, testBundle.getEntry(), null, null,
			absoluteOrderingNames);
	}

	@Test
	public void testLoadCustomWebFragment1XML() throws Exception {
		TestBundle testBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 1, 1, 0, testBundle.getEntry(), "fragment1",
			null, null);
	}

	@Test
	public void testLoadCustomWebFragment2XML() throws Exception {
		TestBundle testBundle = new TestBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		Order order = new OrderImpl();

		EnumMap<Order.Path, String[]> routes = order.getRoutes();

		routes.put(Order.Path.AFTER, new String[] {"fragment1"});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, testBundle.getEntry(), "fragment2",
			order, null);
	}

	@Test
	public void testLoadCustomWebFragment4XML() throws Exception {
		TestBundle testBundle = new TestBundle(
			"dependencies/custom-web-fragment-4.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		Order order = new OrderImpl();

		EnumMap<Order.Path, String[]> routes = order.getRoutes();

		routes.put(Order.Path.BEFORE, new String[] {Order.OTHERS});

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0, testBundle.getEntry(), "fragment4",
			order, null);
	}

	@Test
	public void testLoadWebXML() throws Exception {
		Bundle bundle = new MockBundle();

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				bundle, SAXParserFactory.newInstance(), new Logger(null));

		testLoadDependencies(
			webXMLDefinitionLoader, 0, 0, 0,
			bundle.getEntry("WEB-INF/web.xml"));
	}

	@Test
	public void testLoadCustomWebXMLMetadataComplete() throws Exception {
		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		Assert.assertTrue(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testLoadCustomWebAbsoluteOrdering1XMLMetadataIncomplete()
		throws Exception {

		TestBundle testBundle = new TestBundle(
			"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		Assert.assertFalse(webXMLDefinition.isMetadataComplete());
	}

	@Test
	public void testOrderBeforeAndAfterException() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		TestBundle fragment5TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-5.xml");

		WebXMLDefinitionLoader fragment5WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment5TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment5WebXMLDefinition =
			fragment5WebXMLDefinitionLoader.loadWebXML(
				fragment5TestBundle.getEntry());

		webXMLDefinitions.add(fragment5WebXMLDefinition);

		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		boolean threwOrderBeforeAndAfterException = false;

		try {
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderingNames());
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

		TestBundle circular1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-circular-1.xml");

		WebXMLDefinitionLoader circular1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				circular1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition circular1WebXMLDefinition =
			circular1WebXMLDefinitionLoader.loadWebXML(
				circular1TestBundle.getEntry());

		webXMLDefinitions.add(circular1WebXMLDefinition);

		TestBundle circular2TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-circular-2.xml");

		WebXMLDefinitionLoader circular2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				circular2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition circular2WebXMLDefinition =
			circular2WebXMLDefinitionLoader.loadWebXML(
				circular2TestBundle.getEntry());

		webXMLDefinitions.add(circular2WebXMLDefinition);

		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		boolean threwOrderCircularDependencyException = false;

		try {
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderingNames());
		}
		catch (Exception e) {
			if (e instanceof OrderCircularDependencyException) {
				threwOrderCircularDependencyException = true;
			}
		}

		Assert.assertTrue(threwOrderCircularDependencyException);
	}

	@Test
	public void testOrderCustomWebFragments1() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		TestBundle fragment3TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3TestBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		TestBundle fragment1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1TestBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		TestBundle fragment2TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2TestBundle.getEntry());

		webXMLDefinitions.add(fragment2WebXMLDefinition);

		TestBundle absolute1TestBundle = new TestBundle(
			"dependencies/custom-web-absolute-ordering-1.xml");

		WebXMLDefinitionLoader absolute1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				absolute1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition absolute1WebXMLDefinition =
			absolute1WebXMLDefinitionLoader.loadWebXML(
				absolute1TestBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions,
				absolute1WebXMLDefinition.getAbsoluteOrderingNames());

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
	public void testOrderCustomWebFragments2() throws Exception {
		TestBundle absolute2TestBundle = new TestBundle(
			"dependencies/custom-web-absolute-ordering-2.xml");

		WebXMLDefinitionLoader absolute2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				absolute2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition absolute2WebXMLDefinition =
			absolute2WebXMLDefinitionLoader.loadWebXML(
				absolute2TestBundle.getEntry());

		TestBundle fragment1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1TestBundle.getEntry());

		TestBundle fragment2TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2TestBundle.getEntry());

		TestBundle fragment3TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3TestBundle.getEntry());

		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();
		webXMLDefinitions.add(fragment3WebXMLDefinition);
		webXMLDefinitions.add(fragment2WebXMLDefinition);
		webXMLDefinitions.add(fragment1WebXMLDefinition);

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions,
				absolute2WebXMLDefinition.getAbsoluteOrderingNames());

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
	public void testOrderCustomWebFragments3() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		TestBundle fragment3TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3TestBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		TestBundle fragment2TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2WebXMLDefinition =
			fragment2WebXMLDefinitionLoader.loadWebXML(
				fragment2TestBundle.getEntry());

		webXMLDefinitions.add(fragment2WebXMLDefinition);

		TestBundle fragment1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1TestBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderingNames());

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
	public void testOrderCustomWebFragments4() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		TestBundle fragment2TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-2.xml");

		WebXMLDefinitionLoader fragment2webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment2TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment2webXMLDefinition =
			fragment2webXMLDefinitionLoader.loadWebXML(
				fragment2TestBundle.getEntry());

		webXMLDefinitions.add(fragment2webXMLDefinition);

		TestBundle fragment1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1TestBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		TestBundle fragment4TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-4.xml");

		WebXMLDefinitionLoader fragment4WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment4TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment4WebXMLDefinition =
			fragment4WebXMLDefinitionLoader.loadWebXML(
				fragment4TestBundle.getEntry());

		webXMLDefinitions.add(fragment4WebXMLDefinition);

		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderingNames());

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
	public void testUnorderedWebFragments() throws Exception {
		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		TestBundle fragment1TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-1.xml");

		WebXMLDefinitionLoader fragment1WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment1TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment1WebXMLDefinition =
			fragment1WebXMLDefinitionLoader.loadWebXML(
				fragment1TestBundle.getEntry());

		webXMLDefinitions.add(fragment1WebXMLDefinition);

		TestBundle fragment3TestBundle = new TestBundle(
			"dependencies/custom-web-fragment-3.xml");

		WebXMLDefinitionLoader fragment3WebXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				fragment3TestBundle, SAXParserFactory.newInstance(),
				new Logger(null));

		WebXMLDefinition fragment3WebXMLDefinition =
			fragment3WebXMLDefinitionLoader.loadWebXML(
				fragment3TestBundle.getEntry());

		webXMLDefinitions.add(fragment3WebXMLDefinition);

		TestBundle testBundle = new TestBundle("dependencies/custom-web.xml");

		WebXMLDefinitionLoader webXMLDefinitionLoader =
			new WebXMLDefinitionLoader(
				testBundle, SAXParserFactory.newInstance(), new Logger(null));

		WebXMLDefinition webXMLDefinition = webXMLDefinitionLoader.loadWebXML(
			testBundle.getEntry());

		List<WebXMLDefinition> orderedWebXMLDefinitions =
			OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderingNames());

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
			WebXMLDefinitionLoader webXMLDefinitionLoader, int listenerDefinitionsCount,
			int filterDefinitionsCount, int servletDefinitionsCount, URL webXML)
		throws Exception {

		testLoadDependencies(
			webXMLDefinitionLoader, listenerDefinitionsCount, filterDefinitionsCount,
			servletDefinitionsCount, webXML, null, null, null);
	}

	protected void testLoadDependencies(
			WebXMLDefinitionLoader webXMLDefinitionLoader,
			int listenerDefinitionsCount, int filterDefinitionsCount,
			int servletDefinitionsCount, URL webXML, String fragmentName,
			Order order, List<String> absoluteOrderingNames)
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

		if (ListUtil.isNotEmpty(absoluteOrderingNames)) {
			List<String> webXMLDefinitionAbsoluteOrderingNames =
				webXMLDefinition.getAbsoluteOrderingNames();

			Assert.assertArrayEquals(
				absoluteOrderingNames.toArray(new String[0]),
				webXMLDefinitionAbsoluteOrderingNames.toArray(new String[0]));
		}

		List<ListenerDefinition> listenerDefinitions =
			webXMLDefinition.getListenerDefinitions();

		Assert.assertEquals(
			listenerDefinitionsCount, listenerDefinitions.size());

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			EventListener eventListener = listenerDefinition.getEventListener();

			Assert.assertTrue(eventListener instanceof ServletContextListener);
		}

		Map<String, ServletDefinition> servletDefinitions =
			webXMLDefinition.getServletDefinitions();

		Assert.assertEquals(servletDefinitionsCount, servletDefinitions.size());

		Map<String, FilterDefinition> filterDefinitions =
			webXMLDefinition.getFilterDefinitions();

		Assert.assertEquals(filterDefinitionsCount, filterDefinitions.size());
	}

	@Mock
	private Servlet _servlet;

	@Mock
	private ServletContextListener _servletContextListener;

	private static class TestBundle extends MockBundle {

		public TestBundle(String path) {
			_path = path;
		}

		public URL getEntry() {
			Class<?> clazz = getClass();

			return clazz.getResource(_path);
		}

		private final String _path;

	}

}