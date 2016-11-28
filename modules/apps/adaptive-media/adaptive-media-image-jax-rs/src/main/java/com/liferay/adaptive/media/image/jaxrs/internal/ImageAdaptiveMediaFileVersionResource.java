package com.liferay.adaptive.media.image.jaxrs.internal;

import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaFileVersionResource {

	public ImageAdaptiveMediaFileVersionResource(
		FileVersion fileVersion, ImageAdaptiveMediaFinder finder) {

		_fileVersion = fileVersion;
		_finder = finder;
	}

	private final FileVersion _fileVersion;
	private final ImageAdaptiveMediaFinder _finder;

}