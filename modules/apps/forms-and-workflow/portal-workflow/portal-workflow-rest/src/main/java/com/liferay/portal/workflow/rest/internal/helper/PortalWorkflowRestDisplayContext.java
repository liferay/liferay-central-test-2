/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.rest.internal.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.rest.internal.model.WorkflowListedTaskModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowUserModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = PortalWorkflowRestDisplayContext.class)
public class PortalWorkflowRestDisplayContext {

	public WorkflowListedTaskModel getWorkflowListedTaskModel(
			WorkflowTask workflowTask)
		throws PortalException {

		WorkflowUserModel userModel = getWorkflowUserModel(workflowTask);

		WorkflowListedTaskModel workflowTaskModel = new WorkflowListedTaskModel(
			workflowTask, userModel);

		return workflowTaskModel;
	}

	public WorkflowUserModel getWorkflowUserModel(WorkflowTask workflowTask)
		throws PortalException {

		User assignedUser = _userLocalService.fetchUser(
			workflowTask.getAssigneeUserId());

		WorkflowUserModel userModel = null;

		if (assignedUser != null) {
			userModel = new WorkflowUserModel(assignedUser);
		}

		return userModel;
	}

	@Reference
	private UserLocalService _userLocalService;

}