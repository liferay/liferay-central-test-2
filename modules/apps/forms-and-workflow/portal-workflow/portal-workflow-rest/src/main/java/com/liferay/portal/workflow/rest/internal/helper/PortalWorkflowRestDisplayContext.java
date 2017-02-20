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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.rest.internal.model.WorkflowAssetModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowListedTaskModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskTransitionOperationModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowUserModel;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = PortalWorkflowRestDisplayContext.class)
public class PortalWorkflowRestDisplayContext {

	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			WorkflowTaskTransitionOperationModel operation)
		throws PortalException, WorkflowException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTask);

		return _workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTaskId, operation.getTransition(),
			operation.getComment(), workflowContext);
	}

	public Map<String, Serializable> getWorkflowContext(
			long companyId, WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(
			workflowTask, companyId);

		return workflowInstance.getWorkflowContext();
	}

	public WorkflowListedTaskModel getWorkflowListedTaskModel(
			WorkflowTask workflowTask)
		throws PortalException {

		WorkflowUserModel userModel = getWorkflowUserModel(workflowTask);

		WorkflowListedTaskModel workflowTaskModel = new WorkflowListedTaskModel(
			workflowTask, userModel);

		return workflowTaskModel;
	}

	public WorkflowTaskModel getWorkflowTaskModel(
			long companyId, long userId, long workflowTaskId, Locale locale)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		return getWorkflowTaskModel(companyId, userId, workflowTask, locale);
	}

	public WorkflowTaskModel getWorkflowTaskModel(
			long companyId, long userId, WorkflowTask workflowTask,
			Locale locale)
		throws PortalException {

		WorkflowAssetModel assetModel = getWorkflowAssetModel(
			companyId, workflowTask, locale);

		WorkflowUserModel userModel = getWorkflowUserModel(workflowTask);

		List<String> transitions = _workflowTaskManager.getNextTransitionNames(
			companyId, userId, workflowTask.getWorkflowTaskId());

		return new WorkflowTaskModel(
			workflowTask, userModel, assetModel, transitions);
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

	protected WorkflowAssetModel getWorkflowAssetModel(
			long companyId, WorkflowTask workflowTask, Locale locale)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			companyId, workflowTask);

		long classPK = getWorkflowContextEntryClassPK(companyId, workflowTask);

		AssetRenderer<?> assetRenderer = workflowHandler.getAssetRenderer(
			classPK);

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			workflowHandler.getClassName(), assetRenderer.getClassPK());

		WorkflowAssetModel assetModel = new WorkflowAssetModel(
			assetEntry, locale);

		return assetModel;
	}

	protected String getWorkflowContextEntryClassName(
			WorkflowTask workflowTask, long companyId)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTask);

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	protected long getWorkflowContextEntryClassPK(
			long companyId, WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTask);

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	protected WorkflowHandler<?> getWorkflowHandler(
			long companyId, WorkflowTask workflowTask)
		throws PortalException {

		String className = getWorkflowContextEntryClassName(
			workflowTask, companyId);

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	protected WorkflowInstance getWorkflowInstance(
			WorkflowTask workflowTask, long companyId)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			companyId, workflowTask.getWorkflowInstanceId());
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}