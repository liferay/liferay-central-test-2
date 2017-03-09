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

import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException.InvalidStateAdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
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
public class AdaptiveMediaImageDeleteConfigurationTest
	extends BaseAdaptiveMediaImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testDeleteAllConfigurationEntries() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstConfigurationEntryOptional.isPresent());

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteDeletedConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test(expected = InvalidStateAdaptiveMediaImageConfigurationException.class)
	public void testDeleteEnabledConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");
	}

	@Test
	public void testDeleteFirstConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstConfigurationEntryOptional.isPresent());

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);
	}

	@Test
	public void testDeleteSecondConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteUniqueConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.deleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteAllConfigurationEntries() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstConfigurationEntryOptional.isPresent());

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondConfigurationEntryOptional.isPresent());
	}

	@Test
	public void
			testForceDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteDeletedConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteEnabledConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteFirstConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstConfigurationEntryOptional.isPresent());

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);
	}

	@Test
	public void testForceDeleteSecondConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);

		configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		secondConfigurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondConfigurationEntryOptional.isPresent());
	}

}