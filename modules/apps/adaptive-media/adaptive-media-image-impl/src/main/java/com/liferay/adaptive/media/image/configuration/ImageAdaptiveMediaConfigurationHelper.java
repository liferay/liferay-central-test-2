package com.liferay.adaptive.media.image.configuration;

import aQute.bnd.annotation.ProviderType;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Alejandro Hernández
 */
@ProviderType
public interface ImageAdaptiveMediaConfigurationHelper {

	public Collection<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntries(long companyId);

	public Optional<ImageAdaptiveMediaConfigurationEntry>
		getImageAdaptiveMediaConfigurationEntry(
			long companyId, String configurationEntryUUID);

}