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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.rest.internal.helper.PortalWorkflowRestDisplayContext;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskModel;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = WorkflowTaskResource.class)
@Path("/task")
public class WorkflowTaskResource {

	@GET
	@Path("/{workflowTaskId}")
	@Produces("application/json")
	public WorkflowTaskModel getWorkflowTask(
			@Context Company company, @Context User user,
			@Context Locale locale,
			@PathParam("workflowTaskId") long workflowTaskId)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			company.getCompanyId(), workflowTaskId);

		return _portalWorkflowRestDisplayContext.getWorkflowTaskModel(
			company.getCompanyId(), user.getUserId(), workflowTask, locale);
	}

	@Reference
	private PortalWorkflowRestDisplayContext _portalWorkflowRestDisplayContext;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}