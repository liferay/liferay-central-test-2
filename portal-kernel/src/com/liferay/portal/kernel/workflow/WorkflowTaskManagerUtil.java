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

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowTaskManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskManagerUtil {

	public static WorkflowTask assignWorkflowTaskToRole(
			long userId, long workflowTaskId, long roleId, String comment,
			Map<String, Object> context)
		throws WorkflowException {

		return _workflowTaskManager.assignWorkflowTaskToRole(
			userId, workflowTaskId, roleId, comment, context);
	}

	public static WorkflowTask assignWorkflowTaskToUser(
			long userId, long workflowTaskId, long assigneeUserId,
			String comment, Map<String, Object> context)
		throws WorkflowException {

		return _workflowTaskManager.assignWorkflowTaskToUser(
			userId, workflowTaskId, assigneeUserId, comment, context);
	}

	public static WorkflowTask completeWorkflowTask(
			long userId, long workflowTaskId, String transitionName,
			String comment, Map<String, Object> context)
		throws WorkflowException {

		return _workflowTaskManager.completeWorkflowTask(
			userId, workflowTaskId, transitionName, comment, context);
	}

	public static List<String> getNextTransitionNames(
			long userId, long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getNextTransitionNames(
			userId, workflowTaskId);
	}

	public static long[] getPooledActorsIds(long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getPooledActorsIds(workflowTaskId);
	}

	public static WorkflowTask getWorkflowTask(long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTask(workflowTaskId);
	}

	public static int getWorkflowTaskCount(Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCount(completed);
	}

	public static int getWorkflowTaskCountByRole(long roleId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByRole(
			roleId, completed);
	}

	public static int getWorkflowTaskCountByUser(long userId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByUser(
			userId, completed);
	}

	public static int getWorkflowTaskCountByUserRoles(
			long userId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByUserRoles(
			userId, completed);
	}

	public static int getWorkflowTaskCountByWorkflowInstance(
			long workflowInstanceId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
			workflowInstanceId, completed);
	}

	public static WorkflowTaskManager getWorkflowTaskManager() {
		return _workflowTaskManager;
	}

	public static List<WorkflowTask> getWorkflowTasks(
			Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasks(
			completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByRole(
			long roleId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByRole(
			roleId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUser(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByUser(
			userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUserRoles(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByUserRoles(
			userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long workflowInstanceId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByWorkflowInstance(
			workflowInstanceId, completed, start, end, orderByComparator);
	}

	public void setWorkflowTaskManager(
		WorkflowTaskManager workflowTaskManager) {

		_workflowTaskManager = workflowTaskManager;
	}

	private static WorkflowTaskManager _workflowTaskManager;

}