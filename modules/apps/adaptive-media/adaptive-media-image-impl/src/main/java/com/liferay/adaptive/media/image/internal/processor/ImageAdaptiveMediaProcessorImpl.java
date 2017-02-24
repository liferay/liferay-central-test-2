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

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.RenderedImageUtil;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalService;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.awt.image.RenderedImage;

import java.io.IOException;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileVersion",
	service = {ImageAdaptiveMediaProcessor.class, AdaptiveMediaProcessor.class}
)
public final class ImageAdaptiveMediaProcessorImpl
	implements ImageAdaptiveMediaProcessor {

	@Override
	public void cleanUp(FileVersion fileVersion) {
		try {
			if (!_imageProcessor.isMimeTypeSupported(
					fileVersion.getMimeType())) {

				return;
			}

			_imageLocalService.deleteAdaptiveMediaImageFileVersion(
				fileVersion.getFileVersionId());
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException.IOException(pe);
		}
	}

	@Override
	public void process(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				fileVersion.getCompanyId());

		configurationEntries.forEach(configurationEntry ->
			process(fileVersion, configurationEntry.getUUID()));
	}

	@Override
	public void process(
		FileVersion fileVersion, String configurationEntryUuid) {

		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		Optional<ImageAdaptiveMediaConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
					fileVersion.getCompanyId(), configurationEntryUuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		AdaptiveMediaImage image = _imageLocalService.fetchAdaptiveMediaImage(
			configurationEntry.getUUID(), fileVersion.getFileVersionId());

		if (image != null) {
			return;
		}

		RenderedImage renderedImage = _imageProcessor.scaleImage(
			fileVersion, configurationEntry);

		try {
			byte[] bytes = RenderedImageUtil.getRenderedImageContentStream(
				renderedImage, fileVersion.getMimeType());

			_imageLocalService.addAdaptiveMediaImage(
				configurationEntry, fileVersion, renderedImage.getWidth(),
				renderedImage.getHeight(),
				new UnsyncByteArrayInputStream(bytes), bytes.length);
		}
		catch (IOException | PortalException e) {
			throw new AdaptiveMediaRuntimeException.IOException(e);
		}
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationHelper(
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setImageLocalService(
		AdaptiveMediaImageLocalService imageLocalService) {

		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private AdaptiveMediaImageLocalService _imageLocalService;
	private ImageProcessor _imageProcessor;

}