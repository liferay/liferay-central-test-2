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
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Gives convenient access to a set of media attributes. It offers a type-safe
 * interface to access attribute values, accepting only attributes of the
 * correct type (those for adaptive images), and returning values of the correct
 * type.
 *
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaAttributeMapping {

	/**
	 * Returns an {@link ImageAdaptiveMediaAttributeMapping} that uses the map
	 * as the underlying attribute storage.
	 *
	 * @param  properties the map to get the properties from
	 * @return a non-<code>null</code> mapping that provides type-safe access to
	 *         an underlying map
	 */
	public static ImageAdaptiveMediaAttributeMapping fromProperties(
		Map<String, String> properties) {

		if (properties == null) {
			throw new IllegalArgumentException("properties map cannot be null");
		}

		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
			attributes = new HashMap<>();

		attributes.put(
			AdaptiveMediaAttribute.configurationUuid(),
			_getAttributeValue(
				properties, AdaptiveMediaAttribute.configurationUuid()));
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
	 * Returns an {@link Optional} instance that contains the value of the
	 * attribute (if any) in this mapping.
	 *
	 * @param  attribute a non <code>null</code> attribute
	 * @return A non-<code>null</code> optional that will contain the
	 *         (non-<code>null</code>) value (if any)
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

	private final Map<
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Optional<?>>
		_attributes;

}