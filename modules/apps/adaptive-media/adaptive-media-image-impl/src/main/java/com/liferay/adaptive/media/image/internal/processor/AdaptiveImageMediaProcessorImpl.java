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

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.image.internal.configuration.AdaptiveImageConfiguration;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveImagePropertyMapping;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveImageVariantConfiguration;
import com.liferay.adaptive.media.image.internal.image.ImageProcessor;
import com.liferay.adaptive.media.image.internal.image.ImageStorage;
import com.liferay.adaptive.media.image.processor.AdaptiveImageMediaProcessor;
import com.liferay.adaptive.media.image.source.AdaptiveImageMediaSource;
import com.liferay.adaptive.media.processor.Media;
import com.liferay.adaptive.media.processor.MediaProcessor;
import com.liferay.adaptive.media.processor.MediaProcessorRuntimeException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileVersion",
	service = {
		AdaptiveImageMediaProcessor.class, AdaptiveImageMediaSource.class,
		MediaProcessor.class
	}
)
public final class AdaptiveImageMediaProcessorImpl
	implements AdaptiveImageMediaProcessor, AdaptiveImageMediaSource {

	@Override
	public void cleanUp(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		_imageStorage.delete(fileVersion);
	}

	@Override
	public Stream<Media<AdaptiveImageMediaProcessor>> getMedia(
		FileVersion fileVersion) {

		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return Stream.empty();
		}

		long companyId = fileVersion.getCompanyId();

		Collection<AdaptiveImageVariantConfiguration>
			adaptiveImageVariantConfigurations =
				_adaptiveImageConfiguration.
					getAdaptiveImageVariantConfigurations(companyId);

		return adaptiveImageVariantConfigurations.stream().
			map(
				adaptiveImageVariantConfiguration ->
					_createMedia(
						fileVersion, adaptiveImageVariantConfiguration));
	}

	@Override
	public void process(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		long companyId = fileVersion.getCompanyId();

		Iterable<AdaptiveImageVariantConfiguration>
			adaptiveImageVariantConfigurations =
				_adaptiveImageConfiguration.
					getAdaptiveImageVariantConfigurations(companyId);

		for (AdaptiveImageVariantConfiguration
				adaptiveImageVariantConfiguration :
						adaptiveImageVariantConfigurations) {

			try (InputStream inputStream = _imageProcessor.process(
					fileVersion, adaptiveImageVariantConfiguration)) {

				_imageStorage.save(
					fileVersion, adaptiveImageVariantConfiguration,
					inputStream);
			}
			catch (IOException ioe) {
				throw new MediaProcessorRuntimeException.IOException(ioe);
			}
		}
	}

	@Reference(unbind = "-")
	public void setAdaptiveImageConfiguration(
		AdaptiveImageConfiguration adaptiveImageConfiguration) {

		_adaptiveImageConfiguration = adaptiveImageConfiguration;
	}

	@Reference(unbind = "-")
	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	@Reference(unbind = "-")
	public void setImageStorage(ImageStorage imageStorage) {
		_imageStorage = imageStorage;
	}

	private Media<AdaptiveImageMediaProcessor> _createMedia(
		FileVersion fileVersion,
		AdaptiveImageVariantConfiguration adaptiveImageVariantConfiguration) {

		AdaptiveImagePropertyMapping adaptiveImagePropertyMapping =
			AdaptiveImagePropertyMapping.fromProperties(
				adaptiveImageVariantConfiguration.getProperties());

		return new AdaptiveImageMedia(
			() ->
				_imageStorage.getContentStream(
					fileVersion, adaptiveImageVariantConfiguration),
			adaptiveImagePropertyMapping);
	}

	private AdaptiveImageConfiguration _adaptiveImageConfiguration;
	private ImageProcessor _imageProcessor;
	private ImageStorage _imageStorage;

}