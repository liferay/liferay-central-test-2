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

package com.liferay.adaptive.media.image.internal.util;

/**
 * @author Adolfo Pérez
 */
public class Tuple<S, T> {

	public static final <U, V> Tuple<U, V> of(U first, V second) {
		return new Tuple<>(first, second);
	}

	public final S first;
	public final T second;

	private Tuple(S first, T second) {
		this.first = first;
		this.second = second;
	}

}