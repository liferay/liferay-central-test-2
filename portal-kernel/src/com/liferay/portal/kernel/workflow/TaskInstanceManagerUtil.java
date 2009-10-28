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
 * <a href="TaskInstanceManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class TaskInstanceManagerUtil {

	public static TaskInstanceInfo assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId,
			Map<String, Object> parameters)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToRole(
			taskInstanceId, roleId, comment, attributes, callingUserId,
			parameters);
	}

	public static TaskInstanceInfo assignTaskInstanceToUser(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes, long callingUserId,
			Map<String, Object> parameters)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToUser(
			taskInstanceId, userId, comment, attributes, callingUserId,
			parameters);
	}

	public static TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes, Map<String, Object> parameters)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, comment, attributes, parameters);
	}

	public static TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String pathName,
			String comment, Map<String, Object> attributes,
			Map<String, Object> parameters)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, pathName, comment, attributes,
			parameters);
	}

	public static List<String> getPossibleNextPathNames(
			long taskInstanceId, long userId, Map<String, Object> parameters)
		throws WorkflowException {

		return _taskInstanceManager.getPossibleNextPathNames(
			taskInstanceId, userId, parameters);
	}

	public static int getTaskInstanceInfoCountByRole(long roleId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByRole(roleId);
	}

	public static int getTaskInstanceInfoCountByRole(
			long roleId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByRole(
			roleId, completed);
	}

	public static int getTaskInstanceInfoCountByUser(long userId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByUser(userId);
	}

	public static int getTaskInstanceInfoCountByUser(
			long userId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByUser(
			userId, completed);
	}

	public static int getTaskInstanceInfoCountByWorkflowInstance(
			long workflowInstanceId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByWorkflowInstance(
			workflowInstanceId);
	}

	public static int getTaskInstanceInfoCountByWorkflowInstance(
			long workflowInstanceId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfoCountByWorkflowInstance(
			workflowInstanceId, completed);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, start, end, orderByComparator);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, completed, start, end, orderByComparator);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, start, end, orderByComparator);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, completed, start, end, orderByComparator);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, start, end, orderByComparator);
	}

	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, completed, start, end, orderByComparator);
	}

	public static TaskInstanceManager getTaskInstanceManager() {
		return _taskInstanceManager;
	}

	public void setTaskInstanceManager(
		TaskInstanceManager taskInstanceManager) {

		_taskInstanceManager = taskInstanceManager;
	}

	private static TaskInstanceManager _taskInstanceManager;

}