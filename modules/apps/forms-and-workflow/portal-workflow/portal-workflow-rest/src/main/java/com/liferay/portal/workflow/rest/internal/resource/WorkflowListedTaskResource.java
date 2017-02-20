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

package com.liferay.portal.workflow.rest.internal.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.rest.internal.helper.PortalWorkflowRestDisplayContext;
import com.liferay.portal.workflow.rest.internal.model.WorkflowListedTaskModel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = WorkflowListedTaskResource.class)
@Path("/tasks")
public class WorkflowListedTaskResource {

	@GET
	@Produces("application/json")
	public List<WorkflowListedTaskModel> getUserWorkflowTaskHeaders(
			@Context User user)
		throws PortalException {

		List<WorkflowListedTaskModel> workflowTaskModels = new ArrayList<>();

		List<WorkflowTask> workflowTasks =
			_workflowTaskManager.getWorkflowTasksByUser(
				user.getCompanyId(), user.getUserId(), false, -1, -1, null);

		for (WorkflowTask workflowTask : workflowTasks) {
			WorkflowListedTaskModel workflowTaskModel =
				_portalWorkflowRestDisplayContext.getWorkflowListedTaskModel(
					workflowTask);

			workflowTaskModels.add(workflowTaskModel);
		}

		return workflowTaskModels;
	}

	@Reference
	private PortalWorkflowRestDisplayContext _portalWorkflowRestDisplayContext;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}