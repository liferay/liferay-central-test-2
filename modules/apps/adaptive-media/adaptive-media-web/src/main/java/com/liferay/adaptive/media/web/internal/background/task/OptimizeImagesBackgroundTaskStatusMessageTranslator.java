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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Sergio González
 */
public class OptimizeImagesBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = message.getString(
			OptimizeImagesBackgroundTaskConstants.PHASE);

		if (Validator.isNotNull(phase)) {
			setPhaseAttributes(backgroundTaskStatus, message);

			return;
		}

		String className = message.getString(
			OptimizeImagesBackgroundTaskConstants.CLASS_NAME);

		backgroundTaskStatus.setAttribute(
			OptimizeImagesBackgroundTaskConstants.CLASS_NAME, className);

		long count = message.getLong(
			OptimizeImagesBackgroundTaskConstants.COUNT);

		backgroundTaskStatus.setAttribute(
			OptimizeImagesBackgroundTaskConstants.COUNT, count);

		long total = message.getLong(
			OptimizeImagesBackgroundTaskConstants.TOTAL);

		backgroundTaskStatus.setAttribute(
			OptimizeImagesBackgroundTaskConstants.TOTAL, total);

		int percentage = 100;

		if ((count != 0) && (total != 0)) {
			percentage = (int)(count / total);
		}

		backgroundTaskStatus.setAttribute("percentage", percentage);
	}

	protected void setPhaseAttributes(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(ReindexBackgroundTaskConstants.COMPANY_ID));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.PHASE,
			message.getString(ReindexBackgroundTaskConstants.PHASE));
	}

}