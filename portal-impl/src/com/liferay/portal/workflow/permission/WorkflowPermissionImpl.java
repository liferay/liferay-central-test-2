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

package com.liferay.portal.workflow.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;

import java.util.List;

/**
 * <a href="WorkflowPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class WorkflowPermissionImpl implements WorkflowPermission {

	public Boolean hasPermission(
		long companyId, long groupId, String className, long classPK,
		long userId, String actionId) {

		try {
			return doHasPermission(
				companyId, groupId, className, classPK, userId, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	protected Boolean doHasPermission(
			long companyId, long groupId, String className, long classPK,
			long userId, String actionId)
		throws Exception {

		if (!WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				companyId, groupId, className)) {

			return null;
		}

		if (WorkflowInstanceLinkLocalServiceUtil.hasWorkflowInstanceLink(
				companyId, groupId, className, classPK)) {

			WorkflowInstanceLink workflowInstanceLink =
				WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
						companyId, groupId, className, classPK);

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					companyId, workflowInstanceLink.getWorkflowInstanceId());

			if (workflowInstance.isComplete()) {
				return null;
			}

			return isWorkflowTaskAssignedToUser(
				companyId, userId, workflowInstance);
		}

		return null;
	}

	protected boolean isWorkflowTaskAssignedToUser(
			long companyId, long userId, WorkflowInstance workflowInstance)
		throws WorkflowException {

		List<WorkflowTask> workflowTasks =
			WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
				companyId, workflowInstance.getWorkflowInstanceId(),
				Boolean.FALSE, 0, 100, null);

		for (WorkflowTask workflowTask : workflowTasks) {
			if ((workflowTask.isAssignedToSingleUser()) &&
				(workflowTask.getAssigneeUserId() == userId)) {

				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		WorkflowPermissionImpl.class);

}