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

package com.liferay.adaptive.media.finder;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A {@link AdaptiveMediaFinder} is responsible of locating and returning media
 * related to a model.
 *
 * All media matching the query will be returned, sorted by score. This means
 * better matches will appear before lesser ones.
 *
 * @author Adolfo Pérez
 *
 * @review
 */
public interface AdaptiveMediaFinder
	<B extends AdaptiveMediaQueryBuilder<M, T>, M, T> {

	/**
	 * Return all {@link AdaptiveMedia} for the given model that matches the
	 * query. The provided function will be invoked with an instance of an
	 * implementation dependant {@link AdaptiveMediaQueryBuilder}, that callers
	 * must use to create the query.
	 *
	 * @param queryBuilderFunction Function that will be invoked with a {@link
	 *        AdaptiveMediaQueryBuilder} as argument. This query builder will
	 *        provide operations to filter and sort the returned media.
	 *
	 * @return A non-null, possibly empty stream with all media matching the
	 *         query ordered by score: better matches are returned before
	 *         worser ones
	 *
	 * @throws AdaptiveMediaException if an error occurred while
	 *         getting the {@link AdaptiveMedia}. See
	 *         {@link AdaptiveMediaException} inner classes for the set
	 *         of possible exceptions.
	 * @throws AdaptiveMediaRuntimeException.IOException
	 *         if an error occurs while processing the
	 * @throws IllegalArgumentException if <code>queryBuilderFunction</code> is
	 *         null, or the query returned by it is not valid.
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 *
	 * @review
	 */
	public Stream<AdaptiveMedia<T>> getAdaptiveMedia(
			Function<B, AdaptiveMediaQuery<M, T>> queryBuilderFunction)
		throws AdaptiveMediaException, PortalException;

}