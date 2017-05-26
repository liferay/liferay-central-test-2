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

package com.liferay.adaptive.media.web.internal.background.task;

import com.liferay.adaptive.media.image.optimizer.AdaptiveMediaImageOptimizerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;

/**
 * @author Sergio González
 */
public class OptimizeImagesAllConfigurationsBackgroundTaskExecutor
	extends OptimizeImagesBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return new OptimizeImagesAllConfigurationsBackgroundTaskExecutor();
	}

	@Override
	protected void optimizeImages(String configurationEntryUuid, long companyId)
		throws Exception {

		OptimizeImagesStatusMessageSenderUtil.sendStatusMessage(
			OptimizeImagesBackgroundTaskConstants.PORTAL_START, companyId,
			configurationEntryUuid);

		AdaptiveMediaImageOptimizerUtil.optimize(companyId);

		OptimizeImagesStatusMessageSenderUtil.sendStatusMessage(
			OptimizeImagesBackgroundTaskConstants.PORTAL_END, companyId,
			configurationEntryUuid);
	}

}