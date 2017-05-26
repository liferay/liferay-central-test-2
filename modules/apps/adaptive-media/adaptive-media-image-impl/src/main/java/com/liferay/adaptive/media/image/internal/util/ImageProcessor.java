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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.SetUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ImageProcessor.class)
public class ImageProcessor {

	public boolean isMimeTypeSupported(String mimeType) {
		return _supportedMimeTypes.contains(mimeType);
	}

	public InputStream process(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		try {
			ImageAdaptiveMediaAttributeMapping attributeMapping =
				ImageAdaptiveMediaAttributeMapping.fromProperties(
					configurationEntry.getProperties());

			Optional<Integer> heightOptional =
				attributeMapping.getAttributeValue(
					ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

			Optional<Integer> widthOptional =
				attributeMapping.getAttributeValue(
					ImageAdaptiveMediaAttribute.IMAGE_WIDTH);

			RenderedImage renderedImage = _readImage(
				fileVersion.getContentStream(false));

			RenderedImage scaledImage = ImageToolUtil.scale(
				renderedImage, heightOptional.orElse(0),
				widthOptional.orElse(0));

			UnsyncByteArrayOutputStream baos =
				new UnsyncByteArrayOutputStream();

			ImageToolUtil.write(scaledImage, fileVersion.getMimeType(), baos);

			return new UnsyncByteArrayInputStream(baos.toByteArray());
		}
		catch (IOException | PortalException e) {
			throw new AdaptiveMediaRuntimeException.IOException(e);
		}
	}

	private RenderedImage _readImage(InputStream inputStream)
		throws IOException {

		ImageInputStream imageInputStream = ImageIO.createImageInputStream(
			inputStream);

		Iterator<ImageReader> iterator = ImageIO.getImageReaders(
			imageInputStream);

		while (iterator.hasNext()) {
			ImageReader imageReader = null;

			try {
				imageReader = iterator.next();

				imageReader.setInput(imageInputStream);

				return imageReader.read(0);
			}
			catch (IOException ioe) {
				continue;
			}
			finally {
				if (imageReader != null) {
					imageReader.dispose();
				}
			}
		}

		throw new IOException("Unsupported image type");
	}

	private static final Set<String> _supportedMimeTypes =
		SetUtil.fromArray(new String[] {
			"image/bmp", "image/gif", "image/jpeg", "image/pjpeg", "image/png",
			"image/tiff", "image/x-citrix-jpeg", "image/x-citrix-png",
			"image/x-ms-bmp", "image/x-png", "image/x-tiff"
		});

}