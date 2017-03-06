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

import java.util.HashMap;
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
public class ImageAdaptiveMediaUpdateConfigurationTest
	extends ImageAdaptiveMediaConfigurationBaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test(
		expected = ImageAdaptiveMediaConfigurationException.DuplicateImageAdaptiveMediaConfigurationEntryException.class
	)
	public void testUpdateDuplicateConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "two-bis", "2",
			configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateFirstConfigurationEntryName() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "1",
			configurationEntry1.getProperties());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateDisabledConfigurationEntry() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry.getUUID());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "1-bis",
				configurationEntry.getProperties());

		configurationEntryOptional =
			configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "1-bis");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		assertDisabled(configurationEntryOptional);

		Assert.assertTrue(configurationEntryOptional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryProperties() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry1.getUUID(),
			configurationEntry1.getName(), configurationEntry1.getUUID(),
			properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry1Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryUuid() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", configurationEntry1.getName(),
			"1-bis", configurationEntry1.getProperties());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			nonExistantConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(nonExistantConfigurationEntry1Optional.isPresent());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1-bis");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test(
		expected = ImageAdaptiveMediaConfigurationException.NoSuchImageAdaptiveMediaConfigurationEntryException.class
	)
	public void testUpdateNonExistingConfiguration() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two", "2",
			configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateSecondConfigurationEntryName() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two-bis", "2",
			configurationEntry2.getProperties());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals("two-bis", actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryProperties()
		throws Exception {

		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry2.getUUID(),
			configurationEntry2.getName(), configurationEntry2.getUUID(),
			properties);

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry2Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryUuid() throws Exception {
		ImageAdaptiveMediaConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			configurationHelper.addImageAdaptiveMediaConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateImageAdaptiveMediaConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", configurationEntry2.getName(),
			"2-bis", configurationEntry2.getProperties());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			nonExistantConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(nonExistantConfigurationEntry2Optional.isPresent());

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "2-bis");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<ImageAdaptiveMediaConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		ImageAdaptiveMediaConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

}