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

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ImageAdaptiveMediaConfigurationHelper.class)
public class ImageAdaptiveMediaConfigurationHelper {

	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId) {

		try {
			ImageAdaptiveMediaCompanyConfiguration
				adaptiveImageCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						ImageAdaptiveMediaCompanyConfiguration.class,
						companyId);

			String[] imageVariants =
				adaptiveImageCompanyConfiguration.imageVariants();

			if (imageVariants == null) {
				return Collections.emptyList();
			}

			return Stream.of(imageVariants).
				map(_mediaConfigurationEntryParser::parse).
				collect(Collectors.toList());
		}
		catch (ConfigurationException ce) {
			throw new
				AdaptiveMediaProcessorRuntimeException.InvalidConfiguration(ce);
		}
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationEntryParser(
		ImageAdaptiveMediaConfigurationEntryParser
			mediaConfigurationEntryParser) {

		_mediaConfigurationEntryParser = mediaConfigurationEntryParser;
	}

	@Reference(unbind = "-")
	public void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private ImageAdaptiveMediaConfigurationEntryParser
		_mediaConfigurationEntryParser;
	private ConfigurationProvider _configurationProvider;

}