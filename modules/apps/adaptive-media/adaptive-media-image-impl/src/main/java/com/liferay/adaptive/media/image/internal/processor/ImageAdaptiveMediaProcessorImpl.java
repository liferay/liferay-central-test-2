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

import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.finder.ImageAdaptiveMediaQueryBuilderImpl;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Comparator;
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
	service = {
		ImageAdaptiveMediaProcessor.class, ImageAdaptiveMediaFinder.class,
		AdaptiveMediaProcessor.class
	}
)
public final class ImageAdaptiveMediaProcessorImpl
	implements ImageAdaptiveMediaProcessor, ImageAdaptiveMediaFinder {

	@Override
	public void cleanUp(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		_imageStorage.delete(fileVersion);
	}

	@Override
	public Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> getAdaptiveMedia(
		Function<ImageAdaptiveMediaQueryBuilder,
		AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>>
			queryBuilderFunction) {

		ImageAdaptiveMediaQueryBuilderImpl queryBuilder =
			new ImageAdaptiveMediaQueryBuilderImpl();

		AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> query =
			queryBuilderFunction.apply(queryBuilder);

		if (query != ImageAdaptiveMediaQueryBuilderImpl.QUERY) {
			throw new IllegalArgumentException(
				"Only queries built by the provided query build are valid.");
		}

		FileVersion fileVersion = queryBuilder.getFileVersion();

		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return Stream.empty();
		}

		long companyId = fileVersion.getCompanyId();

		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				companyId);

		return configurationEntries.stream().
			map(
				configurationEntry ->
					_createMedia(fileVersion, configurationEntry)).
			sorted(_buildComparator(queryBuilder.getAttributes()));
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

			try (InputStream inputStream = _imageProcessor.process(
					fileVersion, configurationEntry)) {

				_imageStorage.save(
					fileVersion, configurationEntry, inputStream);
			}
			catch (IOException ioe) {
				throw new AdaptiveMediaProcessorRuntimeException.IOException(
					ioe);
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

	private Comparator<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
		_buildComparator(
			Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, ?>
				attributes) {

		return (adaptiveMedia1, adaptiveMedia2) -> {
			for (Map.Entry<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, ?>
					entry : attributes.entrySet()) {

				AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, Object>
					attribute =
						(AdaptiveMediaAttribute<
							ImageAdaptiveMediaProcessor, Object>)
								entry.getKey();

				Object requestedValue = entry.getValue();

				Optional<?> value1Optional = adaptiveMedia1.getAttributeValue(
					attribute);

				Optional<Integer> value1Distance = value1Optional.map(
					value1 -> attribute.distance(value1, requestedValue));

				Optional<?> value2Optional = adaptiveMedia2.getAttributeValue(
					attribute);

				Optional<Integer> value2Distance = value2Optional.map(
					value2 -> attribute.distance(value2, requestedValue));

				Optional<Integer> resultOptional = value1Distance.flatMap(
					value1 -> value2Distance.map(value2 -> value1 - value2));

				int result = resultOptional.orElse(0);

				if (result != 0) {
					return result;
				}
			}

			return 0;
		};
	}

	private URI _buildRelativeURI(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		String relativePath = String.format(
			"image/%d/%d/%s/%s", fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), configurationEntry.getUUID(),
			_encode(fileVersion.getFileName()));

		return URI.create(relativePath);
	}

	private AdaptiveMedia<ImageAdaptiveMediaProcessor> _createMedia(
		FileVersion fileVersion,
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		Map<String, String> properties = configurationEntry.getProperties();

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

		properties.put(fileNameAttribute.getName(), fileVersion.getFileName());

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(properties);

		return new ImageAdaptiveMedia(
			() -> _imageStorage.getContentStream(
					fileVersion, configurationEntry),
			attributeMapping,
			_buildRelativeURI(fileVersion, configurationEntry));
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