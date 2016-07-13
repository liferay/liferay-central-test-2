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

import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaProperty;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaPropertyMapping {

	public static final ImageAdaptiveMediaPropertyMapping fromProperties(
		Map<String, String> properties) {

		Map<AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			mediaProperties = new HashMap<>();

		mediaProperties.put(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT,
			getMediaPropertyValue(
				properties, ImageAdaptiveMediaProperty.IMAGE_HEIGHT));
		mediaProperties.put(
			ImageAdaptiveMediaProperty.IMAGE_WIDTH,
			getMediaPropertyValue(
				properties, ImageAdaptiveMediaProperty.IMAGE_WIDTH));

		return new ImageAdaptiveMediaPropertyMapping(mediaProperties);
	}

	public <V> Optional<V> getPropertyValue(
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, V> mediaProperty) {

		return (Optional<V>)_mediaProperties.get(mediaProperty);
	}

	protected ImageAdaptiveMediaPropertyMapping(
		Map<AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			mediaProperties) {

		_mediaProperties = mediaProperties;
	}

	private static <V> Optional<V> getMediaPropertyValue(
		Map<String, String> properties,
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, V> mediaProperty) {

		String value = properties.get(mediaProperty.getName());

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(mediaProperty.convert(value));
	}

	private Map<AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
		_mediaProperties;

}