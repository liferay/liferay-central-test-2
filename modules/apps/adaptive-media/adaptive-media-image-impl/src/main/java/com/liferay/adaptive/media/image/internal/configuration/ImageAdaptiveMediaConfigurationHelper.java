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

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = ImageAdaptiveMediaConfigurationHelper.class
)
public class ImageAdaptiveMediaConfigurationHelper {

	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId) {

		Stream<ImageAdaptiveMediaConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		return configurationEntryStream.collect(Collectors.toList());
	}

	public Optional<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntry(
			long companyId, String configurationEntryUUID) {

		Stream<ImageAdaptiveMediaConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		return configurationEntryStream.
			filter(
				configurationEntry ->
					configurationEntryUUID.equals(
						configurationEntry.getUUID())).findFirst();
	}

	@Reference(unbind = "-")
	public void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationEntryParser(
		ImageAdaptiveMediaConfigurationEntryParser configurationEntryParser) {

		_configurationEntryParser = configurationEntryParser;
	}

	private Stream<ImageAdaptiveMediaConfigurationEntry>
		_getConfigurationEntries(long companyId) {

		try {
			ImageAdaptiveMediaCompanyConfiguration companyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ImageAdaptiveMediaCompanyConfiguration.class, companyId);

			String[] imageVariants = companyConfiguration.imageVariants();

			if (imageVariants == null) {
				return Stream.empty();
			}

			return
				Stream.of(imageVariants).map(_configurationEntryParser::parse);
		}
		catch (ConfigurationException ce) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(ce);
		}
	}

	private ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser;
	private ConfigurationProvider _configurationProvider;

}