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
 * <p>
 * The utility class supporting static access to all methods for the {@link
 * TaskInstanceManager} interface. The target manager object is injected using
 * the {@link #setTaskInstanceManager(TaskInstanceManager)} method. Besides the
 * static method access, it is also available through {@link
 * #getTaskInstanceManager()}.
 * </p>
 *
 * @author Micha Kiener
 */
public class TaskInstanceManagerUtil {

	/**
	 * @see TaskInstanceManager#assignTaskInstanceToRole(long, long, String,
	 *		Map, long)
	 */
	public static TaskInstanceInfo assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToRole(
			taskInstanceId, roleId, comment, attributes, callingUserId);
	}

	/**
	 * @see TaskInstanceManager#assignTaskInstanceToUser(long, UserCredential,
	 *		String, Map, long)
	 */
	public static TaskInstanceInfo assignTaskInstanceToUser(
			long taskInstanceId, UserCredential userCredential, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {

		return _taskInstanceManager.assignTaskInstanceToUser(
			taskInstanceId, userCredential, comment, attributes, callingUserId);
	}

	/**
	 * @see TaskInstanceManager#completeTaskInstance(long, long, String, Map)
	 */
	public static TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, comment, attributes);
	}

	/**
	 * @see TaskInstanceManager#completeTaskInstance(long, long, String, String,
	 *		Map)
	 */
	public static TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String activityName,
			String comment, Map<String, Object> attributes)
		throws WorkflowException {

		return _taskInstanceManager.completeTaskInstance(
			taskInstanceId, userId, activityName, comment, attributes);
	}

	/**
	 * @see TaskInstanceManager#getPossibleNextActivityNames(long, long)
	 */
	public static List<String> getPossibleNextActivityNames(
			long taskInstanceId, long userId)
		throws WorkflowException {

		return _taskInstanceManager.getPossibleNextActivityNames(
			taskInstanceId, userId);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForCredential(
	 *		UserCredential)
	 */
	public static int getTaskInstanceCountForCredential(
			UserCredential userCredential)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForCredential(
			userCredential);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForCredential(
	 *		UserCredential, boolean)
	 */
	public static int getTaskInstanceCountForCredential(
			UserCredential userCredential, boolean completed)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForCredential(
			userCredential, completed);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForRole(long)
	 */
	public static int getTaskInstanceCountForRole(long roleId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForRole(roleId);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForRole(long, boolean)
	 */
	public static int getTaskInstanceCountForRole(
		long roleId, boolean completed) throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForRole(
			roleId, completed);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForUser(long)
	 */
	public static int getTaskInstanceCountForUser(long userId)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForUser(userId);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForUser(long, boolean)
	 */
	public static int getTaskInstanceCountForUser(
		long userId, boolean completed) throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceCountForUser(
			userId, completed);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForWorkflowInstance(long)
	 */
	public static int getTaskInstanceCountForWorkflowInstance(
		long workflowInstanceId) throws WorkflowException {
		return _taskInstanceManager.getTaskInstanceCountForWorkflowInstance(
			workflowInstanceId);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceCountForWorkflowInstance(long,
	 *		boolean)
	 */
	public static int getTaskInstanceCountForWorkflowInstance(
			long workflowInstanceId, boolean completed)
		throws WorkflowException {
		return _taskInstanceManager.getTaskInstanceCountForWorkflowInstance(
			workflowInstanceId, completed);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByCredential(UserCredential,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(
			userCredential, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByCredential(UserCredential,
	 *		int, int, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(
			userCredential, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByCredential(UserCredential,
	 *		boolean, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, boolean completed,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(
			userCredential, completed, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByCredential(UserCredential,
	 *		boolean, int, int, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, boolean completed, int start,
			int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByCredential(
			userCredential, completed, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByRole(long,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByRole(long, int, int,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByRole(long, boolean,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, boolean completed, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, completed, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByRole(long, boolean, int,
	 *		int, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByRole(
			roleId, completed, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByUser(long,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByUser(long, int, int,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByUser(long, boolean,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, boolean completed, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, completed, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByUser(long, boolean, int,
	 *		int, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByUser(
			userId, completed, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *		OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *		int, int, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, start, end, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *		boolean, OrderByComparator)
	 */
	public static List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, boolean completed,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _taskInstanceManager.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, completed, orderByComparator);
	}

	/**
	 * @see TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *		boolean, int, int, OrderByComparator)
	 */
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