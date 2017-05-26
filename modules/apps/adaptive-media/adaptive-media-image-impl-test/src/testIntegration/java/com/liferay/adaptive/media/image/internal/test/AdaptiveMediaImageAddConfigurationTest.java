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

import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
public class AdaptiveMediaImageAddConfigurationTest
	extends BaseAdaptiveMediaImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidNameException.class
	)
	public void testAddConfigurationEntryWithBlankName() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), StringPool.BLANK, "1", properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidUuidException.class
	)
	public void testAddConfigurationEntryWithBlankUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", StringPool.BLANK,
			properties);
	}

	@Test
	public void testAddConfigurationEntryWithExistingDisabledConfiguration()
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

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				TestPropsValues.getCompanyId(), configurationEntry -> true);

		Assert.assertEquals(
			configurationEntries.toString(), 2, configurationEntries.size());

		Iterator<AdaptiveMediaImageConfigurationEntry> iterator =
			configurationEntries.iterator();

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			iterator.next();

		Assert.assertEquals("1", configurationEntry.getUUID());

		configurationEntry = iterator.next();

		Assert.assertEquals("2", configurationEntry.getUUID());
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationNameException.class
	)
	public void testAddDuplicateConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "2", properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationUuidException.class
	)
	public void testAddDuplicateConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "1", properties);
	}

}