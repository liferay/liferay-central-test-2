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

import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class ImageAdaptiveMediaConfigurationTest
	extends ImageAdaptiveMediaConfigurationBaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				TestPropsValues.getCompanyId(), configurationEntry -> true);

		Assert.assertEquals(
			configurationEntries.toString(), 2, configurationEntries.size());

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			iterator.next();

		Assert.assertEquals("1", configurationEntry.getUUID());

		configurationEntry = iterator.next();

		Assert.assertEquals("2", configurationEntry.getUUID());
	}

	@Test
	public void testEmptyConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				TestPropsValues.getCompanyId());

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testExistantConfigurationEntry() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());
	}

	@Test
	public void testGetConfigurationEntriesDoesNotReturnDisabledConfigurations()
		throws Exception {

		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.disableImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				TestPropsValues.getCompanyId());

		Assert.assertEquals(
			configurationEntries.toString(), 1, configurationEntries.size());

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			iterator.next();

		Assert.assertEquals("1", configurationEntry.getUUID());
	}

	@Test
	public void
			testGetConfigurationEntriesWithFilterReturnsDisabledConfigurations()
		throws Exception {

		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.disableImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				TestPropsValues.getCompanyId(), configurationEntry -> true);

		Assert.assertEquals(
			configurationEntries.toString(), 2, configurationEntries.size());

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			iterator.next();

		Assert.assertEquals("1", configurationEntry.getUUID());

		configurationEntry = iterator.next();

		Assert.assertEquals("2", configurationEntry.getUUID());
	}

	@Test
	public void testGetConfigurationEntryReturnsDisabledConfiguration()
		throws Exception {

		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());
	}

	@Test
	public void testNonEmptyConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

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
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "0");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test(
		expected = ImageAdaptiveMediaConfigurationException.DuplicateImageAdaptiveMediaConfigurationEntryException.class
	)
	public void testUpdateDuplicateConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "1", properties);
	}

}