package com.liferay.adaptive.media.image.configuration;

import aQute.bnd.annotation.ProviderType;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Alejandro Hernández
 */
@ProviderType
public interface ImageAdaptiveMediaConfigurationHelper {

	Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId);

	Optional<ImageAdaptiveMediaConfigurationEntry>
	getImageAdaptiveMediaConfigurationEntry(
		long companyId, String configurationEntryUUID);
}