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
		_mediaConfigurationHelper.setAdaptiveImageConfigurationParser(
			_adaptiveImageConfigurationParser);
		_mediaConfigurationHelper.setConfigurationProvider(
			_configurationProvider);
	}

	@Test
	public void testEmptyConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_adaptiveImageCompanyConfiguration
		);

		Mockito.when(
			_adaptiveImageCompanyConfiguration.imageVariants()
		).thenReturn(
			new String[0]
		);

		Iterable<ImageAdaptiveMediaVariantConfiguration>
			adaptiveImageVariantConfigurations =
				_mediaConfigurationHelper.
					getAdaptiveImageVariantConfigurations(1234);

		Iterator<ImageAdaptiveMediaVariantConfiguration> iterator =
			adaptiveImageVariantConfigurations.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_adaptiveImageCompanyConfiguration
		);

		Mockito.when(
			_adaptiveImageCompanyConfiguration.imageVariants()
		).thenReturn(
			new String[] {"test:xyz"}
		);

		_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(1234);
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

		_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(1234);
	}

	@Test
	public void testNullConfiguration() throws Exception {
		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(ImageAdaptiveMediaCompanyConfiguration.class),
				Mockito.any(long.class))
		).thenReturn(
			_adaptiveImageCompanyConfiguration
		);

		Mockito.when(
			_adaptiveImageCompanyConfiguration.imageVariants()
		).thenReturn(
			null
		);

		Iterable<ImageAdaptiveMediaVariantConfiguration>
			adaptiveImageVariantConfigurations =
				_mediaConfigurationHelper.
					getAdaptiveImageVariantConfigurations(1234);

		Iterator<ImageAdaptiveMediaVariantConfiguration> iterator =
			adaptiveImageVariantConfigurations.iterator();

		Assert.assertFalse(iterator.hasNext());
	}

	private final ImageAdaptiveMediaCompanyConfiguration
		_adaptiveImageCompanyConfiguration = Mockito.mock(
			ImageAdaptiveMediaCompanyConfiguration.class);
	private final ImageAdaptiveMediaConfigurationHelper
		_mediaConfigurationHelper = new ImageAdaptiveMediaConfigurationHelper();
	private final ImageAdaptiveMediaVariantConfigurationParser
		_adaptiveImageConfigurationParser =
			new ImageAdaptiveMediaVariantConfigurationParser();
	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);

}