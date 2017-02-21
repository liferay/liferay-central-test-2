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
import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

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
	public ImageAdaptiveMediaConfigurationEntry
			addImageAdaptiveMediaConfigurationEntry(
				long companyId, String name, String uuid,
				Map<String, String> properties)
		throws ImageAdaptiveMediaConfigurationException, IOException {

		_checkProperties(properties);

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			getImageAdaptiveMediaConfigurationEntries(companyId);

		_checkDuplicates(configurationEntries, uuid);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(uuid)).collect(
				Collectors.toList());

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				name, uuid, properties, true);

		updatedConfigurationEntries.add(configurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);

		return configurationEntry;
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

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public void disableImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				getImageAdaptiveMediaConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		if (!configurationEntry.isEnabled()) {
			return;
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			getImageAdaptiveMediaConfigurationEntries(companyId);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				curConfigurationEntry ->
					!curConfigurationEntry.getUUID().equals(uuid)).collect(
				Collectors.toList());

		ImageAdaptiveMediaConfigurationEntry newConfigurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				configurationEntry.getName(), configurationEntry.getUUID(),
				configurationEntry.getProperties(), false);

		updatedConfigurationEntries.add(newConfigurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public void enableImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				getImageAdaptiveMediaConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		if (configurationEntry.isEnabled()) {
			return;
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			getImageAdaptiveMediaConfigurationEntries(companyId);

		List<ImageAdaptiveMediaConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				curConfigurationEntry ->
					!curConfigurationEntry.getUUID().equals(uuid)).collect(
				Collectors.toList());

		ImageAdaptiveMediaConfigurationEntry newConfigurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				configurationEntry.getName(), configurationEntry.getUUID(),
				configurationEntry.getProperties(), true);

		updatedConfigurationEntries.add(newConfigurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId) {

		Stream<ImageAdaptiveMediaConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		configurationEntryStream = configurationEntryStream.sorted(
			(configurationEntry1, configurationEntry2) ->
				configurationEntry1.getName().compareTo(
					configurationEntry2.getName()));

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

	@Override
	public boolean isDefaultConfiguration(long companyId) {
		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					ImageAdaptiveMediaCompanyConfiguration.class.getName()));

			String[] nullableImageVariants = _getNullableImageVariants(
				settings);

			if (nullableImageVariants != null) {
				return false;
			}

			return true;
		}
		catch (SettingsException se) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(se);
		}
	}

	@Override
	public void resetDefaultConfiguration(long companyId) {
		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					ImageAdaptiveMediaCompanyConfiguration.class.getName()));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			modifiableSettings.reset("imageVariants");

			modifiableSettings.store();
		}
		catch (IOException | SettingsException | ValidatorException e) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(e);
		}
	}

	@Reference(unbind = "-")
	protected void setImageAdaptiveMediaConfigurationEntryParser(
		ImageAdaptiveMediaConfigurationEntryParser configurationEntryParser) {

		_configurationEntryParser = configurationEntryParser;
	}

	private void _checkDuplicates(
			Collection<ImageAdaptiveMediaConfigurationEntry>
				configurationEntries,
			String uuid)
		throws ImageAdaptiveMediaConfigurationException {

		Optional<ImageAdaptiveMediaConfigurationEntry>
			duplicateConfigurationEntryOptional =
				configurationEntries.stream().filter(
					configurationEntry -> configurationEntry.getUUID().equals(
						uuid)).findFirst();

		if (duplicateConfigurationEntryOptional.isPresent()) {
			throw new ImageAdaptiveMediaConfigurationException.
				DuplicateImageAdaptiveMediaConfigurationEntryException();
		}
	}

	private void _checkProperties(Map<String, String> properties)
		throws ImageAdaptiveMediaConfigurationException {

		long maxHeight = GetterUtil.getLong(properties.get("max-height"));

		if (maxHeight <= 0) {
			throw new
				ImageAdaptiveMediaConfigurationException.
					InvalidHeightException();
		}

		long maxWidth = GetterUtil.getLong(properties.get("max-width"));

		if (maxWidth <= 0) {
			throw new
				ImageAdaptiveMediaConfigurationException.
					InvalidWidthException();
		}
	}

	private Stream<ImageAdaptiveMediaConfigurationEntry>
		_getConfigurationEntries(long companyId) {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					ImageAdaptiveMediaCompanyConfiguration.class.getName()));

			String[] nullableImageVariants = _getNullableImageVariants(
				settings);

			if (nullableImageVariants != null) {
				return Stream.of(nullableImageVariants).map(
					_configurationEntryParser::parse);
			}

			String[] imageVariants = settings.getValues("imageVariants", null);

			if (ArrayUtil.isEmpty(imageVariants)) {
				return Stream.empty();
			}

			return
				Stream.of(imageVariants).map(_configurationEntryParser::parse);
		}
		catch (SettingsException se) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(se);
		}
	}

	private String[] _getNullableImageVariants(Settings settings) {
		PortletPreferencesSettings portletPreferencesSettings =
			(PortletPreferencesSettings)settings;

		PortletPreferences portletPreferences =
			portletPreferencesSettings.getPortletPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		return map.get("imageVariants");
	}

	private void _updateConfiguration(
			long companyId,
			List<ImageAdaptiveMediaConfigurationEntry> configurationEntries)
		throws IOException {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					ImageAdaptiveMediaCompanyConfiguration.class.getName()));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			List<String> imageVariants = configurationEntries.stream().map(
				_configurationEntryParser::getConfigurationString).collect(
					Collectors.toList());

			modifiableSettings.setValues(
				"imageVariants",
				imageVariants.toArray(new String[imageVariants.size()]));

			modifiableSettings.store();
		}
		catch (SettingsException | ValidatorException e) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(e);
		}
	}

	private ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser;

}