package com.liferay.adaptive.media.image.jaxrs;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfiguration;

/**
 * @author Alejandro Hernández
 */
public class AdaptiveMediaImageConfigResource {

	public AdaptiveMediaImageConfigResource(
		long companyId,
		ImageAdaptiveMediaConfiguration imageAdaptiveMediaConfiguration) {

		_companyId = companyId;
		_imageAdaptiveMediaConfiguration = imageAdaptiveMediaConfiguration;
	}

	private final long _companyId;
	private final ImageAdaptiveMediaConfiguration
		_imageAdaptiveMediaConfiguration;

}