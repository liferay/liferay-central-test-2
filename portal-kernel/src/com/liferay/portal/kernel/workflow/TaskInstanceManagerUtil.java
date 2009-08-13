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

import java.util.List;
import java.util.Map;

/**
 * <a href="TaskInstanceManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The utility class supporting static access to all methods for the
 * {@link TaskInstanceManager} interface. The target manager object is injected
 * using the {@link #setTaskInstanceManager(TaskInstanceManager)} method.
 * Besides the static method access, it is also available through
 * {@link #getTaskInstanceManager()}.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public class TaskInstanceManagerUtil {

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#assignTaskInstanceToRole(long, long, java.lang.String, java.util.Map, long)
	 */
	public static TaskInstanceInfo assignTaskInstanceToRole(
		long taskInstanceId, long roleId, String comment,
		Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToRole(
			taskInstanceId, roleId, comment, attributes, callingUserId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#assignTaskInstanceToUser(long,
	 *      com.liferay.portal.kernel.workflow.UserCredential, java.lang.String,
	 *      java.util.Map, long)
	 */
	public static TaskInstanceInfo assignTaskInstanceToUser(
		long taskInstanceId, UserCredential userCredential, String comment,
		Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToUser(
			taskInstanceId, userCredential, comment, attributes, callingUserId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#completeTaskInstance(long,
	 *      long, java.lang.String, java.util.Map)
	 */
	public static TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String comment,
		Map<String, Object> attributes)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, comment, attributes);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#completeTaskInstance(long,
	 *      long, java.lang.String, java.lang.String, java.util.Map)
	 */
	public static TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String activityName, String comment,
		Map<String, Object> attributes)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, activityName, comment, attributes);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getPossibleNextActivityNames(long,
	 *      long)
	 */
	public static List<String> getPossibleNextActivityNames(
		long taskInstanceId, long userId)
		throws WorkflowException {

		return _taskInstanceManager.getPossibleNextActivityNames(
			taskInstanceId, userId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceCountForCredential(com.liferay.portal.kernel.workflow.UserCredential)
	 */
	public static int getTaskInstanceCountForCredential(
		UserCredential userCredential)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForCredential(userCredential);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceCountForRole(long)
	 */
	public static int getTaskInstanceCountForRole(long roleId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForRole(roleId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceCountForUser(long)
	 */
	public static int getTaskInstanceCountForUser(long userId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForUser(userId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByCredential(com.liferay.portal.kernel.workflow.UserCredential)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(userCredential);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByCredential(com.liferay.portal.kernel.workflow.UserCredential,
	 *      boolean)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(
			userCredential, completed);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByRole(long)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(roleId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByRole(long,
	 *      boolean)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, completed);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByUser(long)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(long userId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(userId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByUser(long,
	 *      boolean)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, completed);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(workflowInstanceId);
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *      boolean)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, completed);
	}

	public static TaskInstanceManager getTaskInstanceManager() {
		return _taskInstanceManager;
	}

	public void setTaskInstanceManager(TaskInstanceManager taskInstanceManager) {
		TaskInstanceManagerUtil._taskInstanceManager = taskInstanceManager;
	}

	private static TaskInstanceManager _taskInstanceManager;
}