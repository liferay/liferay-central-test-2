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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.IOException;

import java.util.Collection;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseAdaptiveMediaImageConfigurationTestCase {

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" +
				AdaptiveMediaImageConfigurationHelper.class.getName() + ")");

		serviceTracker = registry.trackServices(filter);

		serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		deleteAllConfigurationEntries();
	}

	@After
	public void tearDown() throws Exception {
		deleteAllConfigurationEntries();
	}

	protected static void deleteAllConfigurationEntries()
		throws IOException, PortalException {

		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				TestPropsValues.getCompanyId(), configurationEntry -> true);

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			configurationHelper.forceDeleteAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), configurationEntry.getUUID());
		}
	}

	protected void assertDisabled(
		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional) {

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Assert.assertFalse(configurationEntry.isEnabled());
	}

	protected void assertEnabled(
		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional) {

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Assert.assertTrue(configurationEntry.isEnabled());
	}

	protected static ServiceTracker
		<AdaptiveMediaImageConfigurationHelper,
			AdaptiveMediaImageConfigurationHelper> serviceTracker;

}