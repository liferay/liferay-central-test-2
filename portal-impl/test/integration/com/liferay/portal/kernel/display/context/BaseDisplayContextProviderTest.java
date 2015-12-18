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

package com.liferay.portal.kernel.display.context;

import com.liferay.portal.kernel.display.context.bundle.basedisplaycontextfactory.TestBaseDisplayContextFactoryImpl;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.documentlibrary.display.context.DLDisplayContextFactory;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class BaseDisplayContextProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.basedisplaycontextfactory"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_baseDisplayContextProvider = new BaseDisplayContextProvider<>(
			DLDisplayContextFactory.class);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_baseDisplayContextProvider.destroy();
	}

	@Test
	public void testDisplayContextHasBeenRegistered() throws Exception {
		DLDisplayContextFactory dlDisplayContextFactoryExtension = null;

		Iterable<DLDisplayContextFactory> displayContextFactories =
			_baseDisplayContextProvider.getDisplayContextFactories();

		Iterator<DLDisplayContextFactory> iterator =
			displayContextFactories.iterator();

		while (iterator.hasNext()) {
			DLDisplayContextFactory dlDisplayContextFactory = iterator.next();

			Class<?> clazz = dlDisplayContextFactory.getClass();

			String className = clazz.getName();

			if (className.equals(
					TestBaseDisplayContextFactoryImpl.class.getName())) {

				dlDisplayContextFactoryExtension = dlDisplayContextFactory;
			}
		}

		Assert.assertNotNull(dlDisplayContextFactoryExtension);
	}

	private static BaseDisplayContextProvider<DLDisplayContextFactory>
		_baseDisplayContextProvider;

}