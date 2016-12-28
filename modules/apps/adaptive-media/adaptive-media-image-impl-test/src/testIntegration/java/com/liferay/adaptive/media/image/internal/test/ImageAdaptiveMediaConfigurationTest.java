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

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class ImageAdaptiveMediaConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" +
				ImageAdaptiveMediaConfigurationHelper.class.getName() + ")");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Test
	public void testEmptyConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			_serviceTracker.getService();

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(1234);

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testExistantConfigurationEntry() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			_serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("height", "100");
		properties.put("width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());
	}

	@Test
	public void testNonEmptyConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			_serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("height", "100");
		properties.put("width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				TestPropsValues.getCompanyId());

		Assert.assertFalse(configurationEntries.isEmpty());
	}

	@Test
	public void testNonExistantConfigurationEntry() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			_serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("height", "100");
		properties.put("width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "0");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	private static ServiceTracker
		<ImageAdaptiveMediaConfigurationHelper,
			ImageAdaptiveMediaConfigurationHelper> _serviceTracker;

}