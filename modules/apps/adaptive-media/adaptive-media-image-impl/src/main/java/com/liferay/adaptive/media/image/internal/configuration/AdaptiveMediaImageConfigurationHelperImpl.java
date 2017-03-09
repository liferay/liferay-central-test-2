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

import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException.InvalidStateAdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
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
	immediate = true, service = AdaptiveMediaImageConfigurationHelper.class
)
public class AdaptiveMediaImageConfigurationHelperImpl
	implements AdaptiveMediaImageConfigurationHelper {

	@Override
	public AdaptiveMediaImageConfigurationEntry
			addAdaptiveMediaImageConfigurationEntry(
				long companyId, String name, String uuid,
				Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException {

		String normalizedUuid = FriendlyURLNormalizerUtil.normalize(uuid);

		_checkProperties(properties);

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			getAdaptiveMediaImageConfigurationEntries(
				companyId, configurationEntry -> true);

		_checkDuplicates(configurationEntries, normalizedUuid);

		List<AdaptiveMediaImageConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(normalizedUuid)).
				collect(Collectors.toList());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				name, normalizedUuid, properties, true);

		updatedConfigurationEntries.add(configurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);

		return configurationEntry;
	}

	@Override
	public void deleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws InvalidStateAdaptiveMediaImageConfigurationException,
			IOException {

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				getAdaptiveMediaImageConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		if (configurationEntry.isEnabled()) {
			throw new InvalidStateAdaptiveMediaImageConfigurationException();
		}

		forceDeleteAdaptiveMediaImageConfigurationEntry(companyId, uuid);
	}

	@Override
	public void disableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				getAdaptiveMediaImageConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		if (!configurationEntry.isEnabled()) {
			return;
		}

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			getAdaptiveMediaImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AdaptiveMediaImageConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				curConfigurationEntry ->
					!uuid.equals(curConfigurationEntry.getUUID())).collect(
				Collectors.toList());

		AdaptiveMediaImageConfigurationEntry newConfigurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				configurationEntry.getName(), configurationEntry.getUUID(),
				configurationEntry.getProperties(), false);

		updatedConfigurationEntries.add(newConfigurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public void enableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				getAdaptiveMediaImageConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		if (configurationEntry.isEnabled()) {
			return;
		}

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			getAdaptiveMediaImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AdaptiveMediaImageConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				curConfigurationEntry ->
					!uuid.equals(curConfigurationEntry.getUUID())).collect(
				Collectors.toList());

		AdaptiveMediaImageConfigurationEntry newConfigurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				configurationEntry.getName(), configurationEntry.getUUID(),
				configurationEntry.getProperties(), true);

		updatedConfigurationEntries.add(newConfigurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public void forceDeleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				getAdaptiveMediaImageConfigurationEntry(companyId, uuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		configurationEntryOptional.ifPresent(
			configurationEntry ->
				_imageEntryLocalService.deleteAdaptiveMediaImageEntries(
					companyId, configurationEntry));

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			getAdaptiveMediaImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AdaptiveMediaImageConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				curConfigurationEntry ->
					!uuid.equals(curConfigurationEntry.getUUID())).collect(
				Collectors.toList());

		_updateConfiguration(companyId, updatedConfigurationEntries);
	}

	@Override
	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(long companyId) {

		Stream<AdaptiveMediaImageConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		return configurationEntryStream.filter(
			configurationEntry -> configurationEntry.isEnabled()).sorted(
				Comparator.comparing(
					AdaptiveMediaImageConfigurationEntry::getName)).collect(
				Collectors.toList());
	}

	@Override
	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(
			long companyId,
			Predicate<? super AdaptiveMediaImageConfigurationEntry> predicate) {

		Stream<AdaptiveMediaImageConfigurationEntry> configurationEntryStream =
			_getConfigurationEntries(companyId);

		Comparator<AdaptiveMediaImageConfigurationEntry> comparator =
			Comparator.comparing(AdaptiveMediaImageConfigurationEntry::getName);

		Stream<AdaptiveMediaImageConfigurationEntry>
			adaptiveMediaImageConfigurationEntryStream =
				configurationEntryStream.filter(predicate);

		return adaptiveMediaImageConfigurationEntryStream.sorted(comparator).
			collect(Collectors.toList());
	}

	@Override
	public Optional<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntry(
			long companyId, String configurationEntryUUID) {

		Stream<AdaptiveMediaImageConfigurationEntry> configurationEntryStream =
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
					AdaptiveMediaImageCompanyConfiguration.class.getName()));

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
					AdaptiveMediaImageCompanyConfiguration.class.getName()));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			modifiableSettings.reset("imageVariants");

			modifiableSettings.store();
		}
		catch (IOException | SettingsException | ValidatorException e) {
			throw new AdaptiveMediaRuntimeException.InvalidConfiguration(e);
		}
	}

	@Override
	public AdaptiveMediaImageConfigurationEntry
			updateAdaptiveMediaImageConfigurationEntry(
				long companyId, String oldUuid, String name, String newUuid,
				Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException {

		String normalizedUuid = FriendlyURLNormalizerUtil.normalize(newUuid);

		_checkProperties(properties);

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			getAdaptiveMediaImageConfigurationEntries(
				companyId, configurationEntry -> true);

		Optional<AdaptiveMediaImageConfigurationEntry>
			oldConfigurationEntryOptional =
				configurationEntries.stream().filter(
					configurationEntry -> configurationEntry.getUUID().equals(
						oldUuid)).findFirst();

		if (!oldConfigurationEntryOptional.isPresent()) {
			throw new AdaptiveMediaImageConfigurationException.
				NoSuchAdaptiveMediaImageConfigurationException(
					"{uuid=" + oldUuid + "}");
		}

		AdaptiveMediaImageConfigurationEntry oldConfigurationEntry =
			oldConfigurationEntryOptional.get();

		if (!oldUuid.equals(normalizedUuid)) {
			_checkDuplicates(configurationEntries, normalizedUuid);
		}

		List<AdaptiveMediaImageConfigurationEntry> updatedConfigurationEntries =
			configurationEntries.stream().filter(
				configurationEntry ->
					!configurationEntry.getUUID().equals(oldUuid)).collect(
				Collectors.toList());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				name, normalizedUuid, properties,
				oldConfigurationEntry.isEnabled());

		updatedConfigurationEntries.add(configurationEntry);

		_updateConfiguration(companyId, updatedConfigurationEntries);

		return configurationEntry;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageConfigurationEntryParser(
		AdaptiveMediaImageConfigurationEntryParser configurationEntryParser) {

		_configurationEntryParser = configurationEntryParser;
	}

	private void _checkDuplicates(
			Collection<AdaptiveMediaImageConfigurationEntry>
				configurationEntries,
			String uuid)
		throws AdaptiveMediaImageConfigurationException {

		Optional<AdaptiveMediaImageConfigurationEntry>
			duplicateConfigurationEntryOptional =
				configurationEntries.stream().filter(
					configurationEntry -> configurationEntry.getUUID().equals(
						uuid)).findFirst();

		if (duplicateConfigurationEntryOptional.isPresent()) {
			throw new AdaptiveMediaImageConfigurationException.
				DuplicateAdaptiveMediaImageConfigurationException();
		}
	}

	private void _checkProperties(Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException {

		long maxHeight = GetterUtil.getLong(properties.get("max-height"));

		if (maxHeight <= 0) {
			throw new
				AdaptiveMediaImageConfigurationException.
					InvalidHeightException();
		}

		long maxWidth = GetterUtil.getLong(properties.get("max-width"));

		if (maxWidth <= 0) {
			throw new
				AdaptiveMediaImageConfigurationException.
					InvalidWidthException();
		}
	}

	private Stream<AdaptiveMediaImageConfigurationEntry>
		_getConfigurationEntries(long companyId) {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					AdaptiveMediaImageCompanyConfiguration.class.getName()));

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
			List<AdaptiveMediaImageConfigurationEntry> configurationEntries)
		throws IOException {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId,
					AdaptiveMediaImageCompanyConfiguration.class.getName()));

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

	private AdaptiveMediaImageConfigurationEntryParser
		_configurationEntryParser;

	@Reference
	private AdaptiveMediaImageEntryLocalService _imageEntryLocalService;

}