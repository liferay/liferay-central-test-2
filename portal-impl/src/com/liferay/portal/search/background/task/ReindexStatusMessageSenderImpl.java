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

package com.liferay.portal.search.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSender;

/**
 * @author Andrew Betts
 */
public class ReindexStatusMessageSenderImpl
	implements ReindexStatusMessageSender {

	@Override
	public void sendStatusMessage(String className, long progress, long count) {
		ReindexBackgroundTaskStatusMessage reindexBackgroundTaskStatusMessage =
			new ReindexBackgroundTaskStatusMessage();

		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.CLASS_NAME, className);
		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.PROGRESS, progress);
		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.COUNT, count);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			reindexBackgroundTaskStatusMessage);
	}

	@Override
	public void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		ReindexBackgroundTaskStatusMessage reindexBackgroundTaskStatusMessage =
			new ReindexBackgroundTaskStatusMessage();

		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.PHASE, phase);
		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.COMPANY_ID, companyId);
		reindexBackgroundTaskStatusMessage.put(
			ReindexBackgroundTaskConstants.COMPANY_IDS, companyIds);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			reindexBackgroundTaskStatusMessage);
	}

	public void setBackgroundTaskStatusMessageSender(
		BackgroundTaskStatusMessageSender backgroundTaskStatusMessageSender) {

		_backgroundTaskStatusMessageSender = backgroundTaskStatusMessageSender;
	}

	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}