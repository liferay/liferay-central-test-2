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

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@Ignore
@RunWith(Arquillian.class)
@Sync
public class AdaptiveMediaImageEnableConfigurationTest
	extends BaseAdaptiveMediaImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testDoesNotSendAMessageToTheMessageBusIfAlreadyEnabled()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		List<Message> messages = collectConfigurationMessages(
			() ->
				adaptiveMediaImageConfigurationHelper.
					enableAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1"));

		Assert.assertEquals(messages.toString(), 0, messages.size());
	}

	@Test
	public void testEnableAllConfigurationEntries() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondConfigurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");
		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		secondConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);
	}

	@Test
	public void testEnableConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		configurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertDisabled(configurationEntryOptional);
	}

	@Test
	public void testEnableEnabledConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);
	}

	@Test
	public void testEnableFirstConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondConfigurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		firstConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstConfigurationEntryOptional);

		secondConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondConfigurationEntryOptional);
	}

	@Test
	public void testEnableNonExistantConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		String uuid = StringUtil.randomString();

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), uuid);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), uuid);

		Assert.assertFalse(configurationEntryOptional.isPresent());
	}

	@Test
	public void testEnableSecondConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Optional<AdaptiveMediaImageConfigurationEntry>
			firstConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstConfigurationEntryOptional);

		Optional<AdaptiveMediaImageConfigurationEntry>
			secondConfigurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondConfigurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		firstConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstConfigurationEntryOptional);

		secondConfigurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondConfigurationEntryOptional);
	}

	@Test
	public void testEnableUniqueConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			enableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		configurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(configurationEntryOptional);
	}

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		List<Message> messages = collectConfigurationMessages(
			() ->
				adaptiveMediaImageConfigurationHelper.
					enableAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1"));

		Assert.assertEquals(messages.toString(), 1, messages.size());

		Message message = messages.get(0);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			(AdaptiveMediaImageConfigurationEntry)message.getPayload();

		Assert.assertEquals("one", configurationEntry.getName());
		Assert.assertEquals("onedesc", configurationEntry.getDescription());
		Assert.assertEquals("1", configurationEntry.getUUID());
		Assert.assertEquals(properties, configurationEntry.getProperties());
	}

}