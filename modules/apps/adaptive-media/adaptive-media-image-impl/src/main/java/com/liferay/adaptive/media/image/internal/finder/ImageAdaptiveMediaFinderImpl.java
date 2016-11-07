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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.AdaptiveMediaURIResolver;
import com.liferay.adaptive.media.finder.AdaptiveMediaFinder;
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMedia;
import com.liferay.adaptive.media.image.internal.util.ImageInfo;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
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

		if (queryBuilderFunction == null) {
			throw new IllegalArgumentException("queryBuilder must be non null");
		}

		ImageAdaptiveMediaQueryBuilderImpl queryBuilder =
			new ImageAdaptiveMediaQueryBuilderImpl();

		AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> query =
			queryBuilderFunction.apply(queryBuilder);

		if (query != ImageAdaptiveMediaQueryBuilderImpl.QUERY) {
			throw new IllegalArgumentException(
				"Only queries built by the provided query builder are valid.");
		}

		FileVersion fileVersion = queryBuilder.getFileVersion();

		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return Stream.empty();
		}

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				fileVersion.getCompanyId());

		BiFunction<FileVersion, ImageAdaptiveMediaConfigurationEntry, URI>
			uriFactory = _getURIFactory(queryBuilder);

		return configurationEntries.stream().
			filter(
				configurationEntry ->
					_imageStorage.getImageInfo(fileVersion, configurationEntry)
						.isPresent()).
			map(
				configurationEntry ->
					_createMedia(
						fileVersion, uriFactory, configurationEntry)).
			sorted(queryBuilder.getComparator());
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaURIResolver(
		AdaptiveMediaURIResolver adaptiveMediaURIResolver) {

		_uriResolver = adaptiveMediaURIResolver;
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

	private URI _createFileEntryURL(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		String relativeURI = String.format(
			"image/%d/%s/%s", fileVersion.getFileEntryId(),
			configurationEntry.getUUID(), _encode(fileVersion.getFileName()));

		return _uriResolver.resolveURI(URI.create(relativeURI));
	}

	private URI _createFileVersionURL(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		String relativeURI = String.format(
			"image/%d/%d/%s/%s", fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), configurationEntry.getUUID(),
			_encode(fileVersion.getFileName()));

		return _uriResolver.resolveURI(URI.create(relativeURI));
	}

	private AdaptiveMedia<ImageAdaptiveMediaProcessor> _createMedia(
		FileVersion fileVersion,
		BiFunction<FileVersion, ImageAdaptiveMediaConfigurationEntry, URI>
			uriFactory,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		Map<String, String> properties = configurationEntry.getProperties();

		AdaptiveMediaAttribute<Object, Integer> contentLengthAttribute =
			AdaptiveMediaAttribute.contentLength();

		Optional<ImageInfo> imageInfoOptional = _imageStorage.getImageInfo(
			fileVersion, configurationEntry);

		imageInfoOptional.ifPresent(
			imageInfo ->
				properties.put(
					contentLengthAttribute.getName(),
					String.valueOf(imageInfo.getSize())));

		AdaptiveMediaAttribute<Object, String> contentTypeAttribute =
			AdaptiveMediaAttribute.contentType();

		imageInfoOptional.ifPresent(
			imageInfo ->
				properties.put(
					contentTypeAttribute.getName(), imageInfo.getMimeType()));

		AdaptiveMediaAttribute<Object, String> fileNameAttribute =
			AdaptiveMediaAttribute.fileName();

		properties.put(fileNameAttribute.getName(), fileVersion.getFileName());

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(properties);

		return new ImageAdaptiveMedia(
			() -> _imageStorage.getContentStream(
				fileVersion, configurationEntry),
			attributeMapping,
			uriFactory.apply(fileVersion, configurationEntry));
	}

	private String _encode(String s) {
		try {
			return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException uee) {
			throw new AdaptiveMediaRuntimeException.
				UnsupportedEncodingException(uee);
		}
	}

	private BiFunction<FileVersion, ImageAdaptiveMediaConfigurationEntry, URI>
		_getURIFactory(ImageAdaptiveMediaQueryBuilderImpl queryBuilder) {

		if (queryBuilder.hasFileVersion()) {
			return this::_createFileVersionURL;
		}

		return this::_createFileEntryURL;
	}

	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;
	private ImageProcessor _imageProcessor;
	private ImageStorage _imageStorage;
	private AdaptiveMediaURIResolver _uriResolver;

}