/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public static int getWorkflowTaskCountByWorkflowInstance(
			long workflowInstanceId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
			workflowInstanceId, completed);
	}

	public static WorkflowTaskManager getWorkflowTaskManager() {
		return _workflowTaskManager;
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