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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.internal.processor.AdaptiveImageMediaProperty;
import com.liferay.adaptive.media.image.processor.AdaptiveImageMediaProcessor;
import com.liferay.adaptive.media.processor.MediaProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveImagePropertyMapping {

	public static final AdaptiveImagePropertyMapping fromProperties(
		Map<String, String> properties) {

		Map<MediaProperty<AdaptiveImageMediaProcessor, ?>, Optional<?>>
			mediaProperties = new HashMap<>();

		mediaProperties.put(
			AdaptiveImageMediaProperty.IMAGE_HEIGHT,
			getMediaPropertyValue(
				properties, AdaptiveImageMediaProperty.IMAGE_HEIGHT));
		mediaProperties.put(
			AdaptiveImageMediaProperty.IMAGE_WIDTH,
			getMediaPropertyValue(
				properties, AdaptiveImageMediaProperty.IMAGE_WIDTH));

		return new AdaptiveImagePropertyMapping(mediaProperties);
	}

	public <V> Optional<V> getPropertyValue(
		MediaProperty<AdaptiveImageMediaProcessor, V> mediaProperty) {

		return (Optional<V>)_mediaProperties.get(mediaProperty);
	}

	protected AdaptiveImagePropertyMapping(
		Map<MediaProperty<AdaptiveImageMediaProcessor, ?>, Optional<?>>
			mediaProperties) {

		_mediaProperties = mediaProperties;
	}

	private static <V> Optional<V> getMediaPropertyValue(
		Map<String, String> properties,
		MediaProperty<AdaptiveImageMediaProcessor, V> mediaProperty) {

		String value = properties.get(mediaProperty.getName());

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(mediaProperty.convert(value));
	}

	private Map<MediaProperty<AdaptiveImageMediaProcessor, ?>, Optional<?>>
		_mediaProperties;

}