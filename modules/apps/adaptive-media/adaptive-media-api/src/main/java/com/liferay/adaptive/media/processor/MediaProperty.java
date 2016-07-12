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
 * A {@link MediaProperty} represents a characteristic of a media (width, size,
 * etc.). Instances of {@link MediaProperty} are annotated by a processor type
 * and by the property value type. The processor type restriction is there to
 * avoid users of {@link Media} to request properties not supported by the
 * processor (i.e. the set of available properties is checked at compile time).
 * The property value type annotation will reduce (or avoid completely) the
 * need for runtime casts when retrieving property values.
 *
 * @author Adolfo Pérez
 */
@ProviderType
public final class MediaProperty<T, V> {

	public MediaProperty(
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

	private final Function<String, V> _converter;
	private final BiFunction<V, V, Integer> _distanceFunction;
	private final String _name;

}