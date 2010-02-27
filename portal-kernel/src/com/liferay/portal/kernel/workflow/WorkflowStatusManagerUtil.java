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

package com.liferay.portal.kernel.workflow;

/**
 * <a href="WorkflowStatusManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class WorkflowStatusManagerUtil {

	public static void updateStatus(
			long companyId, long groupId, long userId, String className,
			long classPK, int status)
		throws WorkflowException {

		_workflowStatusManager.updateStatus(
			companyId, groupId, userId, className, classPK, status);
	}

	public void setWorkflowStatusManager(
		WorkflowStatusManager workflowStatusManager) {

		_workflowStatusManager = workflowStatusManager;
	}

	private static WorkflowStatusManager _workflowStatusManager;

}