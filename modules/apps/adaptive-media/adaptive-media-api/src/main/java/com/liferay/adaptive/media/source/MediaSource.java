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

package com.liferay.adaptive.media.source;

import com.liferay.adaptive.media.processor.Media;
import com.liferay.adaptive.media.processor.MediaProcessorException;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.stream.Stream;

/**
 * A {@link MediaSource} is responsible of locating and returning media
 * related to a model.
 *
 * @author Adolfo Pérez
 */
public interface MediaSource<M, T> {

	/**
	 * Return all {@link Media} for the given model.
	 *
	 * @param model The model for which all generated media will be retrieved
	 *
	 * @return A non-null, possibly empty stream with all generated media for
	 *         the given model.
	 *
	 * @throws MediaProcessorException if an error occurred while getting the
	 *         {@link Media}. See {@link MediaProcessorException} inner classes
	 *         for the set of possible exceptions.
	 * @throws MediaProcessorRuntimeException if a system error occurred.
	 * @throws PortalException if an error occurred while cally any Liferay
	 *         services
	 */
	public Stream<Media<T>> getMedia(M model)
		throws MediaProcessorException, PortalException;

}