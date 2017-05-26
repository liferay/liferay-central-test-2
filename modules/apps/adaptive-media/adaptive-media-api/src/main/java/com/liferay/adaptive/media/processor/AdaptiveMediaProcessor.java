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

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Generates a particular type of media.
 *
 * <p>
 * The type parameter <code>M</code> specifies the model used by the processor
 * to generate the media. The type parameter <code>T</code> restricts the valid
 * {@link com.liferay.adaptive.media.AdaptiveMediaAttribute} set available.
 * </p>
 *
 * @author Adolfo Pérez
 */
@ProviderType
public interface AdaptiveMediaProcessor<M, T> {

	/**
	 * Completely removes any generated media for the model. After calling this
	 * method on a model, the result of calling {@link
	 * com.liferay.adaptive.media.processor#getMedia} with that same model is
	 * undefined.
	 *
	 * @param  model the model for which all generated media is deleted
	 * @throws PortalException
	 */
	public void cleanUp(M model) throws AdaptiveMediaException, PortalException;

	/**
	 * Generates the media for the model. Some implementations might not
	 * generate any media for the model.
	 *
	 * @param  model the model for which media is generated
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void process(M model) throws AdaptiveMediaException, PortalException;

}