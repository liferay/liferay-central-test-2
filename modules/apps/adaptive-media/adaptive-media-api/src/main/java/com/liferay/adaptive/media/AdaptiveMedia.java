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

import java.io.InputStream;

import java.net.URI;

import java.util.Optional;

/**
 * A {@link AdaptiveMedia} represents some content (images, audio, video, etc.)
 * along with a set of attributes which characterize that content.
 *
 * @author Adolfo Pérez
 *
 * @review
 */
@ProviderType
public interface AdaptiveMedia<T> {

	/**
	 * Return this media's value for the given attribute. This method will
	 * always return a non null optional.
	 *
	 * @param attribute The attribute to get the value from
	 * @param <V> The type of the attribute value
	 *
	 * @return {@link Optional#EMPTY} if the attribute is not present in this
	 *         {@link AdaptiveMedia}; otherwise, the value wrapped in an {@link
	 *         Optional}
	 *
	 * @review
	 */
	public <V> Optional<V> getAttributeValue(
		AdaptiveMediaAttribute<T, V> attribute);

	/**
	 * Return an {@link InputStream} with the raw contents of this {@link
	 * AdaptiveMedia}.
	 *
	 * @return An {@link InputStream} with the raw contents of this {@link
	 *         AdaptiveMedia}
	 *
	 * @review
	 */
	public InputStream getInputStream();

	/**
	 * Return the URI of this {@link AdaptiveMedia}. This can be used by other
	 * parts of the system to uniquely identify each {@link AdaptiveMedia}. This
	 * URI should be treated as an opaque value.
	 *
	 * @return A URI pointing to this {@link AdaptiveMedia}
	 *
	 * @review
	 */
	public URI getURI();

}