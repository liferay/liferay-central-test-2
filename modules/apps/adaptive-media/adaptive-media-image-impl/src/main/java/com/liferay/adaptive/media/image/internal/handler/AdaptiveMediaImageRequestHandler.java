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
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.util.Map;
import java.util.Optional;

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
public class AdaptiveMediaImageRequestHandler
	implements AdaptiveMediaRequestHandler<AdaptiveMediaImageProcessor> {

	@Override
	public Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>> handleRequest(
			HttpServletRequest request)
		throws IOException, ServletException {

		Optional<Tuple<FileVersion, AdaptiveMediaImageAttributeMapping>>
			interpretedPathOptional = _interpretPath(request.getPathInfo());

		return interpretedPathOptional.flatMap(
			tuple -> {
				Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
					adaptiveMediaOptional = _findAdaptiveMedia(
						tuple.first, tuple.second);

				adaptiveMediaOptional.ifPresent(
					adaptiveMedia ->
						_processAdaptiveMediaImage(
							adaptiveMedia, tuple.first, tuple.second));

				return adaptiveMediaOptional;
			});
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaImageConfigurationHelper(
		AdaptiveMediaImageConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaImageFinder(AdaptiveMediaImageFinder finder) {
		_finder = finder;
	}

	@Reference(unbind = "-")
	public void setPathInterpreter(PathInterpreter pathInterpreter) {
		_pathInterpreter = pathInterpreter;
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_findAdaptiveMedia(
			FileVersion fileVersion,
			AdaptiveMediaImageAttributeMapping attributeMapping) {

		try {
			Optional<AdaptiveMediaImageConfigurationEntry>
				configurationEntryOptional = attributeMapping.getAttributeValue(
					AdaptiveMediaAttribute.configurationUuid()).flatMap(
						configurationUuid ->
							_configurationHelper.
								getAdaptiveMediaImageConfigurationEntry(
									fileVersion.getCompanyId(),
									configurationUuid));

			if (!configurationEntryOptional.isPresent()) {
				return Optional.empty();
			}

			AdaptiveMediaImageConfigurationEntry configurationEntry =
				configurationEntryOptional.get();

			Map<String, String> properties = configurationEntry.getProperties();

			return _finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.forVersion(fileVersion).with(
					AdaptiveMediaImageAttribute.IMAGE_HEIGHT,
					GetterUtil.getInteger(properties.get("max-height"))).with(
						AdaptiveMediaImageAttribute.IMAGE_WIDTH,
						GetterUtil.getInteger(properties.get("max-width"))).
						done()).findFirst();
		}
		catch (AdaptiveMediaException | PortalException e) {
			throw new AdaptiveMediaRuntimeException(e);
		}
	}

	private Optional<Tuple<FileVersion, AdaptiveMediaImageAttributeMapping>>
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

			AdaptiveMediaImageAttributeMapping attributeMapping =
				AdaptiveMediaImageAttributeMapping.fromProperties(properties);

			return Optional.of(Tuple.of(fileVersion, attributeMapping));
		}
		catch (NumberFormatException | AdaptiveMediaRuntimeException e) {
			_log.error(e);

			return Optional.empty();
		}
	}

	private void _processAdaptiveMediaImage(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		FileVersion fileVersion,
		AdaptiveMediaImageAttributeMapping attributeMapping) {

		Optional<String> adaptiveMediaConfigurationUuidOptional =
			adaptiveMedia.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Optional<String> attributeMappingConfigurationUuidOptional =
			attributeMapping.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		if (adaptiveMediaConfigurationUuidOptional.equals(
				attributeMappingConfigurationUuidOptional)) {

			return;
		}

		try {
			AdaptiveMediaAsyncProcessor<FileVersion, ?> asyncProcessor =
				_asyncProcessorLocator.locateForClass(FileVersion.class);

			asyncProcessor.triggerProcess(
				fileVersion, String.valueOf(fileVersion.getFileVersionId()));
		}
		catch (PortalException | AdaptiveMediaException e) {
			_log.error(
				"Unable to create lazy adaptive media for file version id " +
					fileVersion.getFileVersionId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaImageRequestHandler.class);

	@Reference(unbind = "-")
	private AdaptiveMediaAsyncProcessorLocator _asyncProcessorLocator;

	private AdaptiveMediaImageConfigurationHelper _configurationHelper;
	private AdaptiveMediaImageFinder _finder;
	private PathInterpreter _pathInterpreter;

}