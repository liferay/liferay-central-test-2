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

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.finder.AdaptiveMediaFinder;
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMedia;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileVersion",
	service = {AdaptiveMediaFinder.class, ImageAdaptiveMediaFinder.class}
)
public class ImageAdaptiveMediaFinderImpl implements ImageAdaptiveMediaFinder {

	@Override
	public Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> getAdaptiveMedia(
			Function<ImageAdaptiveMediaQueryBuilder,
			AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>>
				queryBuilderFunction)
		throws PortalException {

		ImageAdaptiveMediaQueryBuilderImpl queryBuilder =
			new ImageAdaptiveMediaQueryBuilderImpl();

		AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> query =
			queryBuilderFunction.apply(queryBuilder);

		if (query != ImageAdaptiveMediaQueryBuilderImpl.QUERY) {
			throw new IllegalArgumentException(
				"Only queries built by the provided query build are valid.");
		}

		FileEntry fileEntry = queryBuilder.getFileEntry();

		if (!_imageProcessor.isMimeTypeSupported(fileEntry.getMimeType())) {
			return Stream.empty();
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				fileEntry.getCompanyId());

		try {
			AdaptiveMediaPropertyDistanceComparator comparator =
				new AdaptiveMediaPropertyDistanceComparator(
					queryBuilder.getAttributes());

			return configurationEntries.stream().
				map(
					configurationEntry ->
						_createMedia(
							fileEntry, queryBuilder.getFileVersion(),
							configurationEntry)).
				sorted(comparator);
		}
		catch (AdaptiveMediaProcessorRuntimeException ampre) {
			Throwable cause = ampre.getCause();

			if (cause instanceof PortalException) {
				throw (PortalException)cause;
			}

			throw ampre;
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

	private URI _buildRelativeURI(
		FileEntry fileEntry, Optional<FileVersion> fileVersionOptional,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		String relativePath = fileVersionOptional.map(
			fileVersion ->
				String.format(
					"image/%d/%d/%s/%s", fileEntry.getFileEntryId(),
					fileVersion.getFileVersionId(),
					configurationEntry.getUUID(),
					_encode(fileVersion.getFileName()))).
		orElseGet(
			() ->
				String.format(
					"image/%d/%s/%s", fileEntry.getFileEntryId(),
					configurationEntry.getUUID(),
					_encode(fileEntry.getFileName())));

		return URI.create(relativePath);
	}

	private AdaptiveMedia<ImageAdaptiveMediaProcessor> _createMedia(
		FileEntry fileEntry, Optional<FileVersion> fileVersionOptional,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		Map<String, String> properties = configurationEntry.getProperties();

		AdaptiveMediaAttribute<Object, Integer> contentLengthAttribute =
			AdaptiveMediaAttribute.contentLength();

		properties.put(
			contentLengthAttribute.getName(),
			String.valueOf(fileEntry.getSize()));

		AdaptiveMediaAttribute<Object, String> contentTypeAttribute =
			AdaptiveMediaAttribute.contentType();

		properties.put(contentTypeAttribute.getName(), fileEntry.getMimeType());

		AdaptiveMediaAttribute<Object, String> fileNameAttribute =
			AdaptiveMediaAttribute.fileName();

		properties.put(fileNameAttribute.getName(), fileEntry.getFileName());

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(properties);

		FileVersion fileVersion = fileVersionOptional.orElseGet(
			() -> {
				try {
					return fileEntry.getLatestFileVersion();
				}
				catch (PortalException pe) {
					throw new AdaptiveMediaProcessorRuntimeException(pe);
				}
			});

		return new ImageAdaptiveMedia(
			() -> _imageStorage.getContentStream(
				fileVersion, configurationEntry),
			attributeMapping,
			_buildRelativeURI(
				fileEntry, fileVersionOptional, configurationEntry));
	}

	private String _encode(String s) {
		try {
			return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException uee) {
			throw new AdaptiveMediaProcessorRuntimeException.
				UnsupportedEncodingException(uee);
		}
	}

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private ImageProcessor _imageProcessor;
	private ImageStorage _imageStorage;

}