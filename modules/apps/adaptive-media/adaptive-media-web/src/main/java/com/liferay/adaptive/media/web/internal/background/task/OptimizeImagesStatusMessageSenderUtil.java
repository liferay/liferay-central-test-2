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

import com.liferay.adaptive.media.web.constants.OptimizeImagesBackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class OptimizeImagesStatusMessageSenderUtil {

	public static void sendStatusMessage(
		String phase, long companyId, String configurationEntryUuid) {

		_instance._sendStatusMessage(phase, companyId, configurationEntryUuid);
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	private void _sendStatusMessage(
		String phase, long companyId, String configurationEntryUuid) {

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(
			OptimizeImagesBackgroundTaskConstants.COMPANY_ID, companyId);
		message.put(OptimizeImagesBackgroundTaskConstants.PHASE, phase);
		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);
		message.put(
			OptimizeImagesBackgroundTaskConstants.CONFIGURATION_ENTRY_UUID,
			configurationEntryUuid);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private static OptimizeImagesStatusMessageSenderUtil _instance;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}