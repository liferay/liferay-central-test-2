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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessage;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;

/**
 * @author Andrew Betts
 */
public class ReindexBackgroundTaskStatusMessage
	extends BackgroundTaskStatusMessage {

	public ReindexBackgroundTaskStatusMessage(
		String className, long progress, long count) {

		put(ReindexBackgroundTaskConstants.CLASS_NAME, className);
		put(ReindexBackgroundTaskConstants.PROGRESS, progress);
		put(ReindexBackgroundTaskConstants.COUNT, count);
	}

	public ReindexBackgroundTaskStatusMessage(
		String phase, long companyId, long[] companyIds) {

		put(ReindexBackgroundTaskConstants.PHASE, phase);
		put(ReindexBackgroundTaskConstants.COMPANY_ID, companyId);
		put(ReindexBackgroundTaskConstants.COMPANY_IDS, companyIds);
	}

}