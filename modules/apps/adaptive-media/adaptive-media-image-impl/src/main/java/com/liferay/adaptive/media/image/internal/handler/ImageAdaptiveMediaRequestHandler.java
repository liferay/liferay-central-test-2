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

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.handler.AdaptiveMediaRequestHandler;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "adaptive.media.handler.pattern=image",
	service = AdaptiveMediaRequestHandler.class
)
public class ImageAdaptiveMediaRequestHandler
	implements AdaptiveMediaRequestHandler<ImageAdaptiveMediaProcessor> {

	@Override
	public Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> handleRequest(
			HttpServletRequest request)
		throws IOException, ServletException {

		Optional<Tuple<FileVersion, ImageAdaptiveMediaAttributeMapping>>
			interpretedPathOptional = _interpretPath(request.getPathInfo());

		return interpretedPathOptional.flatMap(
			tuple -> _findAdaptiveMedia(tuple.first, tuple.second));
	}

	@Reference(unbind = "-")
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaConfigurationHelper(
		ImageAdaptiveMediaConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setImageAdaptiveMediaFinder(ImageAdaptiveMediaFinder finder) {
		_finder = finder;
	}

	private Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
		_findAdaptiveMedia(
			FileVersion fileVersion,
			ImageAdaptiveMediaAttributeMapping attributeMapping) {

		try {
			Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
				_finder.getAdaptiveMedia(
					queryBuilder ->
						queryBuilder.
							forVersion(fileVersion).
							with(
								ImageAdaptiveMediaAttribute.IMAGE_HEIGHT,
								attributeMapping.getAttributeValue(
									ImageAdaptiveMediaAttribute.IMAGE_HEIGHT)).
							with(
								ImageAdaptiveMediaAttribute.IMAGE_WIDTH,
								attributeMapping.getAttributeValue(
									ImageAdaptiveMediaAttribute.IMAGE_WIDTH)).
							done());

			return mediaStream.findFirst();
		}
		catch (AdaptiveMediaProcessorException | PortalException e) {
			throw new AdaptiveMediaProcessorRuntimeException(e);
		}
	}

	private Optional<Tuple<FileVersion, ImageAdaptiveMediaAttributeMapping>>
		_interpretPath(String pathInfo) {

		try {
			String[] components = StringUtil.split(
				pathInfo.substring(1), CharPool.SLASH);

			if (components.length < 7) {
				return Optional.empty();
			}

			long fileVersionId = Long.parseUnsignedLong(components[4]);

			FileVersion fileVersion = _dlAppLocalService.getFileVersion(
				fileVersionId);

			String configurationEntryUUID = components[5];

			Optional<ImageAdaptiveMediaConfigurationEntry>
				configurationEntryOptional =
					_configurationHelper.
						getImageAdaptiveMediaConfigurationEntry(
							fileVersion.getCompanyId(), configurationEntryUUID);

			Map<String, String> properties = configurationEntryOptional.
				map(ImageAdaptiveMediaConfigurationEntry::getProperties).
				orElse(new HashMap<>());

			AdaptiveMediaAttribute<Object, Integer> contentLengthAttribute =
				AdaptiveMediaAttribute.contentLength();

			properties.put(
				contentLengthAttribute.getName(),
				String.valueOf(fileVersion.getSize()));

			AdaptiveMediaAttribute<Object, String> contentTypeAttribute =
				AdaptiveMediaAttribute.contentType();

			properties.put(
				contentTypeAttribute.getName(), fileVersion.getMimeType());

			AdaptiveMediaAttribute<Object, String> fileNameAttribute =
				AdaptiveMediaAttribute.fileName();

			properties.put(
				fileNameAttribute.getName(), fileVersion.getFileName());

			ImageAdaptiveMediaAttributeMapping attributeMapping =
				ImageAdaptiveMediaAttributeMapping.fromProperties(properties);

			return Optional.of(Tuple.of(fileVersion, attributeMapping));
		}
		catch (NumberFormatException | PortalException e) {
			_log.error(e);

			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageAdaptiveMediaRequestHandler.class);

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private DLAppLocalService _dlAppLocalService;
	private ImageAdaptiveMediaFinder _finder;

}