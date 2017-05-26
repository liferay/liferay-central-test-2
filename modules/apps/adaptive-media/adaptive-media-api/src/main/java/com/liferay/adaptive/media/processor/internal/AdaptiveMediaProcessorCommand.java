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

package com.liferay.adaptive.media.processor.internal;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public enum AdaptiveMediaProcessorCommand {

	CLEAN_UP {

		@Override
		protected <M> void execute(
				AdaptiveMediaProcessor<M, ?> processor, M model)
			throws AdaptiveMediaException, PortalException {

			processor.cleanUp(model);
		}

	},

	PROCESS {

		@Override
		protected <M> void execute(
				AdaptiveMediaProcessor<M, ?> processor, M model)
			throws AdaptiveMediaException, PortalException {

			processor.process(model);
		}

	};

	protected abstract <M> void execute(
			AdaptiveMediaProcessor<M, ?> processor, M model)
		throws AdaptiveMediaException, PortalException;

}