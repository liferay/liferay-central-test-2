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

package com.liferay.adaptive.media.processor;

import aQute.bnd.annotation.ProviderType;

import java.util.function.BiFunction;
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

	public static final <S> AdaptiveMediaAttribute<S, Integer> contentLength() {
		return (AdaptiveMediaAttribute<S, Integer>)_CONTENT_LENGTH;
	}

	public static final <S> AdaptiveMediaAttribute<S, String> contentType() {
		return (AdaptiveMediaAttribute<S, String>)_CONTENT_TYPE;
	}

	public static final <S> AdaptiveMediaAttribute<S, String> fileName() {
		return (AdaptiveMediaAttribute<S, String>)_FILE_NAME;
	}

	public AdaptiveMediaAttribute(
		String name, Function<String, V> converter,
		BiFunction<V, V, Integer> distanceFunction) {

		_name = name;
		_converter = converter;
		_distanceFunction = distanceFunction;
	}

	public V convert(String value) {
		return _converter.apply(value);
	}

	public int distance(V value1, V value2) {
		return _distanceFunction.apply(value1, value2);
	}

	public String getName() {
		return _name;
	}

	private static final AdaptiveMediaAttribute<?, Integer> _CONTENT_LENGTH =
		new AdaptiveMediaAttribute<>(
			"content-length", Integer::parseInt, (i1, i2) -> Math.abs(i1 - i2));

	private static final AdaptiveMediaAttribute<?, String> _CONTENT_TYPE =
		new AdaptiveMediaAttribute<>(
			"content-type", (s) -> s,
			(s1, s2) -> s1.equals(s2) ? 0 : Integer.MAX_VALUE);

	private static final AdaptiveMediaAttribute<?, String> _FILE_NAME =
		new AdaptiveMediaAttribute<>(
			"file-name", (s) -> s,
			(s1, s2) -> s1.equals(s2) ? 0 : Integer.MAX_VALUE);

	private final Function<String, V> _converter;
	private final BiFunction<V, V, Integer> _distanceFunction;
	private final String _name;

}