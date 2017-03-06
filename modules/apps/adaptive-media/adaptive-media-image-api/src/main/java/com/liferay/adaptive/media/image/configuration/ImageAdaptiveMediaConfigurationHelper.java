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

package com.liferay.adaptive.media.image.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException;
import com.liferay.adaptive.media.ImageAdaptiveMediaConfigurationException.InvalidStateImageAdaptiveMediaConfigurationEntryException;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Alejandro Hernández
 */
@ProviderType
public interface ImageAdaptiveMediaConfigurationHelper {

	public ImageAdaptiveMediaConfigurationEntry
			addImageAdaptiveMediaConfigurationEntry(
				long companyId, String name, String uuid,
				Map<String, String> properties)
		throws ImageAdaptiveMediaConfigurationException, IOException;

	public void deleteImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws InvalidStateImageAdaptiveMediaConfigurationEntryException,
			IOException;

	public void disableImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public void enableImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public void forceDeleteImageAdaptiveMediaConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId);

	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(
			long companyId,
			Predicate<? super ImageAdaptiveMediaConfigurationEntry> predicate);

	public Optional<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntry(
			long companyId, String configurationEntryUUID);

	public boolean isDefaultConfiguration(long companyId);

	public void resetDefaultConfiguration(long companyId);

	public ImageAdaptiveMediaConfigurationEntry
			updateImageAdaptiveMediaConfigurationEntry(
				long companyId, String oldUuid, String name, String newUuid,
				Map<String, String> properties)
		throws ImageAdaptiveMediaConfigurationException, IOException;

}