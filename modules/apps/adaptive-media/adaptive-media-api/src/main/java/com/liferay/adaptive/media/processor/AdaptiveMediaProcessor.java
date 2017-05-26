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

import com.liferay.adaptive.media.AdaptiveMediaProcessorException;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * A {@link AdaptiveMediaProcessor} is responsible of generating media of a
 * particular type.
 *
 * The type parameter M specifies the model used by the processor to generate
 * the media. The type parameter T is used to restrict the set of valid
 * {@link com.liferay.adaptive.media.AdaptiveMediaAttribute} available.
 *
 * @author Adolfo Pérez
 */
@ProviderType
public interface AdaptiveMediaProcessor<M, T> {

	/**
	 * Completely remove any generated media for the given model. After calling
	 * this method on a model, the result of calling
	 * AdaptiveMediaProcessor#getMedia with that same model is undefined.
	 *
	 * @param model The model for which all generated media will be deleted
	 *
	 * @throws AdaptiveMediaProcessorException if a processing error occurred.
	 *         See {@link AdaptiveMediaProcessorException} inner classes for the
	 *         set of possible exceptions.
	 * @throws com.liferay.adaptive.media.AdaptiveMediaProcessorRuntimeException
	 *         if a system error occurred.
	 */
	public void cleanUp(M model)
		throws AdaptiveMediaProcessorException, PortalException;

	/**
	 * Generate the media for the given model. Implementations may decide not to
	 * generate any media for a model.
	 *
	 * @param model The model for which media will be generated
	 *
	 * @throws AdaptiveMediaProcessorException if an error occurred while
	 *         generating the media. See {@link AdaptiveMediaProcessorException}
	 *         inner classes for the set of possible exceptions.
	 * @throws com.liferay.adaptive.media.AdaptiveMediaProcessorRuntimeException
	 *         if a system error occurred.
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void process(M model)
		throws AdaptiveMediaProcessorException, PortalException;

}