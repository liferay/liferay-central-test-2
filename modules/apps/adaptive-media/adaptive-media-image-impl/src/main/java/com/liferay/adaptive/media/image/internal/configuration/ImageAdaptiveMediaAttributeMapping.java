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

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class is responsible of giving convenient access to a set of media
 * attributes. It will offer a typesafe interface to access attribute values,
 * accepting only attributes of the correct type (those for adaptive images),
 * and returning values of the correct type.
 *
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaAttributeMapping {

	/**
	 * Return an {@link ImageAdaptiveMediaAttributeMapping} that will use the
	 * given map as the underlying attribute storage.
	 *
	 * @param properties The map to get the properties from
	 *
	 * @return A non-null mapping that will give typesafe access to the
	 *         underlying map
	 *
	 * @throws IllegalArgumentException if <code>properties</code> is null
	 */
	public static ImageAdaptiveMediaAttributeMapping fromProperties(
		Map<String, String> properties) {

		if (properties == null) {
			throw new IllegalArgumentException("properties map cannot be null");
		}

		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			attributes = new HashMap<>();

		attributes.put(
			AdaptiveMediaAttribute.contentLength(),
			_getAttributeValue(
				properties, AdaptiveMediaAttribute.contentLength()));
		attributes.put(
			AdaptiveMediaAttribute.contentType(),
			_getAttributeValue(
				properties, AdaptiveMediaAttribute.contentType()));
		attributes.put(
			AdaptiveMediaAttribute.fileName(),
			_getAttributeValue(properties, AdaptiveMediaAttribute.fileName()));
		attributes.put(
			ImageAdaptiveMediaAttribute.IMAGE_HEIGHT,
			_getAttributeValue(
				properties, ImageAdaptiveMediaAttribute.IMAGE_HEIGHT));
		attributes.put(
			ImageAdaptiveMediaAttribute.IMAGE_WIDTH,
			_getAttributeValue(
				properties, ImageAdaptiveMediaAttribute.IMAGE_WIDTH));

		return new ImageAdaptiveMediaAttributeMapping(attributes);
	}

	/**
	 * Return an {@link Optional} that will contain the value of
	 * <code>attribute</code> in this mapping (if any).
	 *
	 * @param attribute a non null attribute
	 * @param <V> the type of the value mapped to the attribute

	 * @return A non-null optional that will contain the (non-null) value (if
	 *         any)
	 */
	public <V> Optional<V> getAttributeValue(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute) {

		if (attribute == null) {
			throw new IllegalArgumentException("attribute cannot be null");
		}

		return (Optional<V>)_attributes.get(attribute);
	}

	protected ImageAdaptiveMediaAttributeMapping(
		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			attributes) {

		_attributes = attributes;
	}

	private static <V> Optional<V> _getAttributeValue(
		Map<String, String> properties,
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute) {

		String value = properties.get(attribute.getName());

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(attribute.convert(value));
	}

	private Map<
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
		_attributes;

}