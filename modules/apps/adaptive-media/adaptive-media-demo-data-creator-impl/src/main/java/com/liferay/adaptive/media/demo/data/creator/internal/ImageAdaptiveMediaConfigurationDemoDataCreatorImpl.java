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

package com.liferay.adaptive.media.demo.data.creator.internal;

import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.demo.data.creator.DemoImageAdaptiveMediaConfigurationVariant;
import com.liferay.adaptive.media.demo.data.creator.ImageAdaptiveMediaConfigurationDemoDataCreator;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = ImageAdaptiveMediaConfigurationDemoDataCreator.class)
public class ImageAdaptiveMediaConfigurationDemoDataCreatorImpl
	implements ImageAdaptiveMediaConfigurationDemoDataCreator {

	@Override
	public Collection<ImageAdaptiveMediaConfigurationEntry> create(
			long companyId)
		throws IOException {

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			new ArrayList<>();

		for (DemoImageAdaptiveMediaConfigurationVariant
				demoConfigurationVariant :
					DemoImageAdaptiveMediaConfigurationVariant.values()) {

			ImageAdaptiveMediaConfigurationEntry configurationEntry = create(
				companyId, demoConfigurationVariant);

			configurationEntries.add(configurationEntry);
		}

		return configurationEntries;
	}

	@Override
	public ImageAdaptiveMediaConfigurationEntry create(
			long companyId,
			DemoImageAdaptiveMediaConfigurationVariant configurationVariant)
		throws IOException {

		ImageAdaptiveMediaConfigurationEntry configurationEntry = null;

		try {
			configurationEntry =
				_configurationHelper.addImageAdaptiveMediaConfigurationEntry(
					companyId, configurationVariant.getName(),
					configurationVariant.getUuid(),
					configurationVariant.getProperties());

			_addConfigurationUuid(companyId, configurationEntry.getUUID());
		}
		catch (ImageAdaptiveMediaConfigurationException iamce) {
			_log.error(
				"Unable to add image adaptive media configuration", iamce);
		}

		return configurationEntry;
	}

	@Override
	public void delete() throws IOException {
		for (Long companyId : _configurationIds.keySet()) {
			List<String> uuids = _configurationIds.get(companyId);

			for (String uuid : uuids) {
				_configurationHelper.
					forceDeleteImageAdaptiveMediaConfigurationEntry(
						companyId, uuid);

				uuids.remove(uuid);
			}
		}
	}

	@Reference(unbind = "-")
	public void setConfigurationHelper(
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	private void _addConfigurationUuid(long companyId, String uuid) {
		_configurationIds.computeIfAbsent(
			companyId, k -> new CopyOnWriteArrayList<>());

		List<String> uuids = _configurationIds.get(companyId);

		uuids.add(uuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageAdaptiveMediaConfigurationDemoDataCreatorImpl.class);

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private final Map<Long, List<String>> _configurationIds = new HashMap<>();

}