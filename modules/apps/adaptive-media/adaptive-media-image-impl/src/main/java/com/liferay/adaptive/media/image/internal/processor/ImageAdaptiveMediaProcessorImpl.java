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
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalService;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StreamUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

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
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		_imageStorage.delete(fileVersion);

		_imageLocalService.deleteAdaptiveMediaImageFileVersion(
			fileVersion.getFileVersionId());
	}

	@Override
	public void process(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		long companyId = fileVersion.getCompanyId();

		Iterable<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				companyId);

		for (ImageAdaptiveMediaConfigurationEntry configurationEntry :
				configurationEntries) {

			RenderedImage renderedImage = _imageProcessor.scaleImage(
				fileVersion, configurationEntry);

			InputStream inputStream = null;

			try {
				byte[] bytes = _getBytes(fileVersion, renderedImage);

				inputStream = new UnsyncByteArrayInputStream(bytes);

				_imageStorage.save(
					fileVersion, configurationEntry, inputStream);

				_imageLocalService.addAdaptiveMediaImage(
					configurationEntry.getUUID(),
					fileVersion.getFileVersionId(), renderedImage.getHeight(),
					renderedImage.getWidth(), bytes.length);
			}
			catch (IOException | PortalException e) {
				throw new AdaptiveMediaRuntimeException.IOException(e);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationHelper(
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	@Reference(unbind = "-")
	public void setImageStorage(ImageStorage imageStorage) {
		_imageStorage = imageStorage;
	}

	private byte[] _getBytes(
			FileVersion fileVersion, RenderedImage renderedImage)
		throws IOException {

		try (UnsyncByteArrayOutputStream baos =
				new UnsyncByteArrayOutputStream()) {

			ImageToolUtil.write(renderedImage, fileVersion.getMimeType(), baos);

			return baos.toByteArray();
		}
	}

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;

	@Reference
	private AdaptiveMediaImageLocalService _imageLocalService;

	private ImageProcessor _imageProcessor;
	private ImageStorage _imageStorage;

}