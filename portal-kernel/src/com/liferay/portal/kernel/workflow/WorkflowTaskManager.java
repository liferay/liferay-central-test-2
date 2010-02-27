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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

@MessagingProxy(mode = ProxyMode.SYNC)
/**
 * <a href="WorkflowTaskManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public interface WorkflowTaskManager {

	public WorkflowTask assignWorkflowTaskToRole(
			long userId, long workflowTaskId, long roleId, String comment,
			Map<String, Object> context)
		throws WorkflowException;

	public WorkflowTask assignWorkflowTaskToUser(
			long userId, long workflowTaskId, long assigneeUserId,
			String comment, Map<String, Object> context)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long userId, long workflowTaskId, String transitionName,
			String comment, Map<String, Object> context)
		throws WorkflowException;

	public List<String> getNextTransitionNames(long userId, long workflowTaskId)
		throws WorkflowException;

	public long[] getPooledActorsIds(long workflowTaskId)
		throws WorkflowException;

	public WorkflowTask getWorkflowTask(long workflowTaskId)
		throws WorkflowException;

	public int getWorkflowTaskCount(Boolean completed) throws WorkflowException;

	public int getWorkflowTaskCountByRole(long roleId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUser(long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUserRoles(long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByWorkflowInstance(
			long workflowInstanceId, Boolean completed)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasks(
			Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByRole(
			long roleId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUser(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUserRoles(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long workflowInstanceId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

}