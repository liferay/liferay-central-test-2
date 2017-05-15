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

package com.liferay.portal.workflow.rest.internal.helper;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.rest.internal.model.WorkflowAssetModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowAssigneeModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskTransitionOperationModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowUserModel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = WorkflowHelper.class)
public class WorkflowHelper {

	public WorkflowTask assignWorkflowTask(
			long companyId, long userId, long workflowTaskId)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		return _workflowTaskManager.assignWorkflowTaskToUser(
			companyId, userId, workflowTaskId, userId, null, null,
			workflowContext);
	}

	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			WorkflowTaskTransitionOperationModel
				workflowTaskTransitionOperationModel)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		return _workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTaskId,
			workflowTaskTransitionOperationModel.getTransition(),
			workflowTaskTransitionOperationModel.getComment(), workflowContext);
	}

	public List<WorkflowLog> getWorkflowLogs(
			long companyId, WorkflowTask workflowTask)
		throws PortalException {

		return getWorkflowLogs(companyId, workflowTaskId, false);
	}

	public List<WorkflowLog> getWorkflowLogs(
			long companyId, long workflowTaskId, boolean oldestFirst)
		throws PortalException {

		List<Integer> logTypes = new ArrayList<>();

		logTypes.add(WorkflowLog.TASK_ASSIGN);
		logTypes.add(WorkflowLog.TASK_COMPLETION);
		logTypes.add(WorkflowLog.TASK_UPDATE);
		logTypes.add(WorkflowLog.TRANSITION);

		WorkflowInstance workflowInstance = getWorkflowInstance(
			companyId, workflowTask.getWorkflowTaskId());

		return _workflowLogManager.getWorkflowLogsByWorkflowInstance(
			companyId, workflowInstance.getWorkflowInstanceId(), logTypes,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_workflowComparatorFactory.getLogCreateDateComparator(oldestFirst));
	}

	public WorkflowTaskModel getWorkflowTaskModel(
			long companyId, long userId, long workflowTaskId, Locale locale)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		WorkflowAssigneeModel workflowAssigneeModel = getWorkflowAssigneeModel(
			workflowTask);

		WorkflowAssetModel workflowAssetModel = getWorkflowAssetModel(
			companyId, workflowTaskId, locale);
		String state = getState(companyId, workflowTaskId, locale);
		List<String> transitions = _workflowTaskManager.getNextTransitionNames(
			companyId, userId, workflowTaskId);

		List<WorkflowLog> workflowLogs = getWorkflowLogs(
			companyId, workflowTask);

		long lastActivityTime = 0;

		if (!workflowLogs.isEmpty()) {
			WorkflowLog workflowLog = workflowLogs.get(0);

			Date createDate = workflowLog.getCreateDate();

			lastActivityTime = createDate.getTime();
		}

		return new WorkflowTaskModel(
			workflowTask, workflowAssigneeModel, workflowAssetModel,
			lastActivityTime, state, transitions);
	}

	protected String getState(
			long companyId, long workflowTaskId, Locale locale)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		long groupId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));
		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		String state = _workflowInstanceLinkLocalService.getState(
			companyId, groupId, className, classPK);

		return LanguageUtil.get(locale, state);
	}

	protected WorkflowAssetModel getWorkflowAssetModel(
			long companyId, long workflowTaskId, Locale locale)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		AssetRendererFactory<?> assetRendererFactory =
			workflowHandler.getAssetRendererFactory();

		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			className, classPK);

		WorkflowUserModel workflowUserModel = getWorkflowUserModel(
			assetEntry.getUserId());

		return new WorkflowAssetModel(assetEntry, locale, workflowUserModel);
	}

	protected WorkflowAssigneeModel getWorkflowAssigneeModel(
			WorkflowTask workflowTask)
		throws PortalException {

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTask.getWorkflowTaskAssignees();

		for (WorkflowTaskAssignee workflowTaskAssignee :
				workflowTaskAssignees) {

			if (workflowTaskAssignee.getAssigneeClassName().equals(
					User.class.getName())) {

				User user = _userLocalService.fetchUser(
					workflowTaskAssignee.getAssigneeClassPK());

				return new WorkflowAssigneeModel(user);
			}
			else if (workflowTaskAssignee.getAssigneeClassName().equals(
						Role.class.getName())) {

				Role role = _roleLocalService.fetchRole(
					workflowTaskAssignee.getAssigneeClassPK());

				return new WorkflowAssigneeModel(role);
			}
		}

		return null;
	}

	protected Map<String, Serializable> getWorkflowContext(
			long companyId, long workflowTaskId)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(
			companyId, workflowTaskId);

		return workflowInstance.getWorkflowContext();
	}

	protected WorkflowInstance getWorkflowInstance(
			long companyId, long workflowTaskId)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			companyId, workflowTask.getWorkflowInstanceId());
	}

	protected WorkflowUserModel getWorkflowUserModel(long userId)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		if (user != null) {
			return new WorkflowUserModel(user);
		}

		return null;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowLogManager _workflowLogManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}