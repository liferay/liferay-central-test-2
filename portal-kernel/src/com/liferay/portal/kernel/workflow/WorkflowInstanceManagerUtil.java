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

import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstanceManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowInstanceManagerUtil {

	public static void deleteWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		_workflowInstanceManager.deleteWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static List<String> getNextTransitionNames(
			long companyId, long userId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getNextTransitionNames(
			companyId, userId, workflowInstanceId);
	}

	public static WorkflowInstance getWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static int getWorkflowInstanceCount(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceCount(
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			completed);
	}

	public static WorkflowInstanceManager getWorkflowInstanceManager() {
		return _workflowInstanceManager;
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed, int start,
			int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstances(
			companyId, workflowDefinitionName,
			workflowDefinitionVersion, completed, start,
			end, orderByComparator);
	}

	public static WorkflowInstance signalWorkflowInstance(
			long companyId, long userId, long workflowInstanceId,
			String transitionName, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.signalWorkflowInstance(
			companyId, userId, workflowInstanceId, transitionName,
			workflowContext);
	}

	public static WorkflowInstance startWorkflowInstance(
			long companyId, long userId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, String transitionName,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			companyId, userId, workflowDefinitionName,
			workflowDefinitionVersion, transitionName, workflowContext);
	}

	public static WorkflowInstance updateContext(
			long companyId, long workflowInstanceId,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.updateContext(
			companyId, workflowInstanceId, workflowContext);
	}

	public void setWorkflowInstanceManager(
		WorkflowInstanceManager workflowInstanceManager) {

		_workflowInstanceManager = workflowInstanceManager;
	}

	private static WorkflowInstanceManager _workflowInstanceManager;

}