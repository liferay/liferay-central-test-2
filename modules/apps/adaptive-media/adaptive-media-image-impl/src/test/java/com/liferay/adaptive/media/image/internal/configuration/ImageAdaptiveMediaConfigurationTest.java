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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaConfigurationTest {

	@Before
	public void setUp() {
		_configurationHelper.setImageAdaptiveMediaConfigurationEntryParser(
			_configurationEntryParser);
		_configurationHelper.setConfigurationProvider(_configurationProvider);
	}

	@Test
	public void testEmptyConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_companyConfiguration
		);

		Mockito.when(
			_companyConfiguration.imageVariants()
		).thenReturn(
			new String[0]
		);

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.
				getImageAdaptiveMediaConfigurationEntries(1234);

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_companyConfiguration
		);

		Mockito.when(
			_companyConfiguration.imageVariants()
		).thenReturn(
			new String[] {"test:xyz"}
		);

		_configurationHelper.getImageAdaptiveMediaConfigurationEntries(1234);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class)
	public void testModuleConfigurationException() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenThrow(
			ConfigurationException.class
		);

		_configurationHelper.getImageAdaptiveMediaConfigurationEntries(1234);
	}

	@Test
	public void testNullConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_companyConfiguration
		);

		Mockito.when(
			_companyConfiguration.imageVariants()
		).thenReturn(
			null
		);

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.
				getImageAdaptiveMediaConfigurationEntries(1234);

		Iterator<ImageAdaptiveMediaConfigurationEntry> iterator =
			configurationEntries.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	private final ImageAdaptiveMediaCompanyConfiguration _companyConfiguration =
		Mockito.mock(ImageAdaptiveMediaCompanyConfiguration.class);
	private final ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser =
		new ImageAdaptiveMediaConfigurationEntryParser();
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		new ImageAdaptiveMediaConfigurationHelper();
	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);

}