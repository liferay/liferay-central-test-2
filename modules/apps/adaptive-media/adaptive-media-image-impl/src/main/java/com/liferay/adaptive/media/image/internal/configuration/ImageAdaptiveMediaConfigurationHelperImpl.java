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
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = ImageAdaptiveMediaConfigurationHelper.class
)
public class ImageAdaptiveMediaConfigurationHelperImpl
	implements ImageAdaptiveMediaConfigurationHelper {

	@Override
	public void addImageAdaptiveMediaConfigurationEntry(
			long companyId, String name, String uuid,
			Map<String, String> properties)
		throws IOException {

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			getImageAdaptiveMediaConfigurationEntries(companyId);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				name, uuid, properties);

		configurationEntries.add(configurationEntry);

		_updateConfiguration(new ArrayList<>(configurationEntries));
	}

	@Override
	public void deleteImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			getImageAdaptiveMediaConfigurationEntries(companyId);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(uuid)).collect(
						Collectors.toList());

		_updateConfiguration(updatedConfigurationEntries);
	}

	@Override
	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId) {

		Stream<ImageAdaptiveMediaConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		return configurationEntryStream.collect(Collectors.toList());
	}

	@Override
	public Optional<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntry(
			long companyId, String configurationEntryUUID) {

		Stream<ImageAdaptiveMediaConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		return configurationEntryStream.filter(
			configurationEntry -> configurationEntryUUID.equals(
				configurationEntry.getUUID())).findFirst();
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setImageAdaptiveMediaConfigurationEntryParser(
		ImageAdaptiveMediaConfigurationEntryParser configurationEntryParser) {

		_configurationEntryParser = configurationEntryParser;
	}

	private Configuration _getConfiguration() throws IOException {
		Registry registry = RegistryUtil.getRegistry();

		ConfigurationAdmin configurationAdmin = registry.getService(
			ConfigurationAdmin.class);

		return configurationAdmin.getConfiguration(
			ImageAdaptiveMediaCompanyConfiguration.class.getName(), null);
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

	private void _updateConfiguration(
			List<ImageAdaptiveMediaConfigurationEntry> configurationEntries)
		throws IOException {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("imageVariants", configurationEntries.stream().map(
			_configurationEntryParser::getConfigurationString).collect(
				Collectors.toList()).toArray(
					new String[configurationEntries.size()]));

		Configuration configuration = _getConfiguration();

		configuration.update(properties);
	}

	private ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser;
	private ConfigurationProvider _configurationProvider;

}