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
			propertiesXX = new HashMap<>();

		propertiesXX.put(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT,
			getPropertyValue(
				properties, ImageAdaptiveMediaProperty.IMAGE_HEIGHT));
		propertiesXX.put(
			ImageAdaptiveMediaProperty.IMAGE_WIDTH,
			getPropertyValue(
				properties, ImageAdaptiveMediaProperty.IMAGE_WIDTH));

		return new ImageAdaptiveMediaPropertyMapping(propertiesXX);
	}

	public <V> Optional<V> getPropertyValue(
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, V> property) {

		return (Optional<V>)_properties.get(property);
	}

	protected ImageAdaptiveMediaPropertyMapping(
		Map<AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			properties) {

		_properties = properties;
	}

	private static <V> Optional<V> getPropertyValue(
		Map<String, String> properties,
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, V> propertyXX) {

		String value = properties.get(propertyXX.getName());

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(propertyXX.convert(value));
	}

	private Map<
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			_properties;

}