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

package com.liferay.portal.workflow.rest.internal.resource;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.rest.internal.helper.WorkflowHelper;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	public List<WorkflowTaskModel> getUserWorkflowTaskHeaders(
			@Context Company company, @Context User user,
			@Context Locale locale)
		throws PortalException {

		List<WorkflowTaskModel> workflowTaskModels = new ArrayList<>();

		long companyId = company.getCompanyId();
		long userId = user.getUserId();

		List<WorkflowTask> userWorkflowTasks =
			_workflowTaskManager.getWorkflowTasksByUser(
				companyId, userId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		int roleWorkflowTasks =
			_workflowTaskManager.getWorkflowTaskCountByUserRoles(
				companyId, userId, true);

		for (WorkflowTask workflowTask : userWorkflowTasks) {
			WorkflowTaskModel workflowListedTaskModel =
				_workflowHelper.getWorkflowTaskModel(
					companyId, userId, workflowTask.getWorkflowTaskId(),
					locale);

			workflowTaskModels.add(workflowListedTaskModel);
		}

		return workflowTaskModels;
	}

	@Reference
	private WorkflowHelper _workflowHelper;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}