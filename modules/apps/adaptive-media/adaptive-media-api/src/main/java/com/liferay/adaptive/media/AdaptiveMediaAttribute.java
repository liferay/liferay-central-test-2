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

package com.liferay.adaptive.media;

import aQute.bnd.annotation.ProviderType;

import java.util.Comparator;
import java.util.function.Function;

/**
 * A {@link AdaptiveMediaAttribute} represents a characteristic of a media
 * (width, size, etc.). Instances of {@link AdaptiveMediaAttribute} are
 * annotated by a processor type and by the attribute value type. The processor
 * type restriction is there to avoid users of {@link AdaptiveMedia} to request
 * attributes not supported by the processor (i.e. the set of available
 * attributes is checked at compile time). The attribute value type annotation
 * will reduce (or avoid completely) the need for runtime casts when retrieving
 * attribute values.
 *
 * @author Adolfo Pérez
 */
@ProviderType
public final class AdaptiveMediaAttribute<T, V> {

	/**
	 * A generic attribute representing the content length of the media. This
	 * attribute can be used with any kind of media.
	 *
	 * @return The content length attribute
	 */
	public static final <S> AdaptiveMediaAttribute<S, Integer> contentLength() {
		return (AdaptiveMediaAttribute<S, Integer>)_CONTENT_LENGTH;
	}

	/**
	 * A generic attribute representing the content type of the media. This
	 * attribute can be used with any kind of media.
	 *
	 * @return the content type attribute
	 */
	public static final <S> AdaptiveMediaAttribute<S, String> contentType() {
		return (AdaptiveMediaAttribute<S, String>)_CONTENT_TYPE;
	}

	/**
	 * A generic attribute representing the file name (if any) of the media.
	 * This attribute can be used with any kind of media.
	 *
	 * @return the file name attribute
	 */
	public static final <S> AdaptiveMediaAttribute<S, String> fileName() {
		return (AdaptiveMediaAttribute<S, String>)_FILE_NAME;
	}

	/**
	 * Create a new attribute. As all attributes live in the same global
	 * namespace. <code>name</code> should uniquely identify this attribute, and
	 * it is recommended for it to be a human readable value. <code>converter
	 * </code> should be a function capable of converting a String to a value
	 * of the correct type; this function should throw a {@link
	 * AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException}
	 * when the given String is not convertible. <code>comparator</code>
	 * is a comparator that compare its two arguments for order considering the
	 * distance between their values; it should return a value between
	 * {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE} based on the
	 * distance of the values.
	 */
	public AdaptiveMediaAttribute(
		String name, Function<String, V> converter, Comparator<V> comparator) {

		_name = name;
		_converter = converter;
		_comparator = comparator;
	}

	/**
	 * Compares its two arguments for order. Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second
	 *
	 * @param value1 The first value to be compared
	 * @param value2 The second value to be compared
	 * @return a negative integer, zero, or a positive integer as the
	 *         first argument is less than, equal to, or greater than the
	 *         second.
	 */
	public int compare(V value1, V value2) {
		return _comparator.compare(value1, value2);
	}

	/**
	 * Convert the given string to a value of the correct type.
	 *
	 * @param value the string containing the value to convert
	 * @return The converted value
	 * @throws {@link
	 *         AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException}
	 *         when the give value is not convertible.
	 */
	public V convert(String value) {
		return _converter.apply(value);
	}

	/**
	 * Compute the distance between the two values.
	 *
	 * @param value1 The first value
	 * @param value2 The second value
	 * @return A value between 0 and {@link Integer#MAX_VALUE} representing how
	 *         close both values are
	 */
	public int distance(V value1, V value2) {
		return Math.abs(_comparator.compare(value1, value2));
	}

	/**
	 * Return the globally unique name for this attribute.
	 *
	 * @return the name of this attribute
	 */
	public String getName() {
		return _name;
	}

	private static final AdaptiveMediaAttribute<?, Integer> _CONTENT_LENGTH =
		new AdaptiveMediaAttribute<>(
			"content-length", Integer::parseInt, (i1, i2) -> i1 - i2);

	private static final AdaptiveMediaAttribute<?, String> _CONTENT_TYPE =
		new AdaptiveMediaAttribute<>(
			"content-type", (s) -> s, (s1, s2) -> s1.compareTo(s2));

	private static final AdaptiveMediaAttribute<?, String> _FILE_NAME =
		new AdaptiveMediaAttribute<>(
			"file-name", (s) -> s, (s1, s2) -> s1.compareTo(s2));

	private final Comparator<V> _comparator;
	private final Function<String, V> _converter;
	private final String _name;

}