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

import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorException;
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
 */
public interface AdaptiveMediaFinder<B extends AdaptiveMediaQueryBuilder<M, T>, M, T> {

	/**
	 * Return all {@link AdaptiveMedia} for the given model. The provided
	 * function will be invoked with an instance of an implementation dependant
	 * {@link AdaptiveMediaQueryBuilder}.
	 *
	 * @param queryBuilderFunction Function that will be invoked with a {@link
	 *        AdaptiveMediaQueryBuilder} as argument. This query builder will
	 *        provide operations to filter and sort the returned media.
	 *
	 * @return A non-null, possibly empty stream with all media matching the
	 *         query ordered by score: better matches are returned before
	 *         worser ones
	 *
	 * @throws AdaptiveMediaProcessorException if an error occurred while
	 *         getting the {@link AdaptiveMedia}. See
	 *         {@link AdaptiveMediaProcessorException} inner classes for the set
	 *         of possible exceptions.
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public Stream<AdaptiveMedia<T>> getMedia(
			Function<B, AdaptiveMediaQuery<M, T>> queryBuilderFunction)
		throws AdaptiveMediaProcessorException, PortalException;

}