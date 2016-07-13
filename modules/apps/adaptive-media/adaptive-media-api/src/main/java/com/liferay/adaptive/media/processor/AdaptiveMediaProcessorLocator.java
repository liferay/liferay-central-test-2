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

/**
 * Implementations of this interface are responsible of the location of the
 * a valid {@link AdaptiveMediaProcessor} for a particular class.
 *
 * @author Adolfo Pérez
 */
public interface AdaptiveMediaProcessorLocator {

	/**
	 * Return a {@link AdaptiveMediaProcessor} for the given class. If no
	 * processor is found, implementations are free to return a processor that
	 * does nothing.
	 *
	 * @param clazz The class of models the returned processor will support
	 * @param <M> The class generic type
	 *
	 * @return A non null {@link AdaptiveMediaProcessor}
	 */
	public <M> AdaptiveMediaProcessor<M, ?> locateForClass(Class<M> clazz);

}