/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

/**
 * @author Juan Fernández
 */
package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

public class WorkflowUtil {

	public static String getStatus(int status) {

		String statusMessage = StringPool.BLANK;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			statusMessage = "approved";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			statusMessage = "draft";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			statusMessage = "expired";
		}
		else if (status == WorkflowConstants.STATUS_PENDING) {
			statusMessage = "pending";
		}

		return statusMessage;
	}
}