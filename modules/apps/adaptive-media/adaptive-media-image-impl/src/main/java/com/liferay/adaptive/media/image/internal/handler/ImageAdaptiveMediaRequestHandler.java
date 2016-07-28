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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.handler.AdaptiveMediaRequestHandler;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.IOException;

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
	public void setImageAdaptiveMediaFinder(ImageAdaptiveMediaFinder finder) {
		_finder = finder;
	}

	@Reference(unbind = "-")
	public void setPathInterpreter(PathInterpreter pathInterpreter) {
		_pathInterpreter = pathInterpreter;
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
		catch (AdaptiveMediaException | PortalException e) {
			throw new AdaptiveMediaRuntimeException(e);
		}
	}

	private Optional<Tuple<FileVersion, ImageAdaptiveMediaAttributeMapping>>
		_interpretPath(String pathInfo) {

		try {
			Optional<Tuple<FileVersion, Map<String, String>>>
				fileVersionPropertiesTupleOptional =
					_pathInterpreter.interpretPath(pathInfo);

			if (!fileVersionPropertiesTupleOptional.isPresent()) {
				return Optional.empty();
			}

			Tuple<FileVersion, Map<String, String>> fileVersionMapTuple =
				fileVersionPropertiesTupleOptional.get();

			FileVersion fileVersion = fileVersionMapTuple.first;

			Map<String, String> properties = fileVersionMapTuple.second;

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
		catch (NumberFormatException | AdaptiveMediaRuntimeException e) {
			_log.error(e);

			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageAdaptiveMediaRequestHandler.class);

	private ImageAdaptiveMediaFinder _finder;
	private PathInterpreter _pathInterpreter;

}