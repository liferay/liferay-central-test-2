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

import java.io.InputStream;

import java.net.URI;

import java.util.Optional;

/**
 * A {@link AdaptiveMedia} represents some content (images, audio, video, etc.)
 * along with a set of properties which characterize that content.
 *
 * @author Adolfo Pérez
 */
@ProviderType
public interface AdaptiveMedia<T> {

	/**
	 * Return an {@link InputStream} with the raw contents of this {@link
	 * AdaptiveMedia}.
	 *
	 * @return An {@link InputStream} with the raw contents of this {@link
	 *         AdaptiveMedia}
	 */
	public InputStream getInputStream();

	/**
	 * Return this media's value for the given property. This method will
	 * always return a non null optional.
	 *
	 * @param property The property to get the value from
	 * @param <V> The type of the property value
	 *
	 * @return {@link Optional#EMPTY} if the property is not present in this
	 *         {@link AdaptiveMedia}; otherwise, the value wrapped in an {@link
	 *         Optional}
	 */
	public <V> Optional<V> getPropertyValue(
		AdaptiveMediaProperty<T, V> property);

	/**
	 * Return the relative URI of this {@link AdaptiveMedia}. This can be used
	 * by other parts of the system to uniquely identify each
	 * {@link AdaptiveMedia}. This URI should be treated as an opaque value.
	 *
	 * @return A relative URI pointing to this {@link AdaptiveMedia}
	 */
	public URI getRelativeURI();

}