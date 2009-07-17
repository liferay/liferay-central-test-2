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

package com.liferay.portal.kernel.workflow.request;

import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowUtil;

import java.lang.reflect.Method;

import java.util.Map;

/**
 * <a href="TaskInstanceRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceRequest extends BaseRequest {

	public static TaskInstanceRequest createAssignTaskInstanceToRoleRequest(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		return new TaskInstanceRequest(
			assignTaskInstanceToRole_long_long_String_Map_long, callingUserId,
			taskInstanceId,
			roleId, comment, attributes, callingUserId);
	}

	public static TaskInstanceRequest createAssignTaskInstanceToUserRequest(
			long taskInstanceId, UserCredential userCredential, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		return new TaskInstanceRequest(
			assignTaskInstanceToUser_long_UserCredential_String_Map_long,
			callingUserId, taskInstanceId, userCredential, comment, attributes,
			callingUserId);
	}

	public static TaskInstanceRequest createCompleteTaskInstanceRequest(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes)
		throws WorkflowException {
		return new TaskInstanceRequest(
			completeTaskInstance_long_long_String_Map, userId, taskInstanceId,
			userId, comment, attributes);
	}

	public static TaskInstanceRequest createGetTaskInstanceInfosByRoleRequest(
			long roleId)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByRole_long, 0, roleId);
	}

	public static TaskInstanceRequest createGetTaskInstanceInfosByRoleRequest(
			long roleId, boolean completed)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByRole_long_boolean, 0, roleId, completed);
	}

	public static TaskInstanceRequest createGetTaskInstanceInfosByUserRequest(
			long userId)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByUser_long, 0, userId);
	}

	public static TaskInstanceRequest createGetTaskInstanceInfosByUserRequest(
			long userId, boolean completed)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByUser_long_boolean, 0, userId, completed);
	}

	public static TaskInstanceRequest
		createGetTaskInstanceInfosByWorkflowInstanceRequest(
			long workflowInstanceId)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByWorkflowInstance_long, 0, workflowInstanceId);
	}

	public static TaskInstanceRequest
		createGetTaskInstanceInfosByWorkflowInstanceRequest(
			long workflowInstanceId, boolean completed)
		throws WorkflowException {
		return new TaskInstanceRequest(
			getTaskInstanceInfosByWorkflowInstance_long_boolean, 0,
			workflowInstanceId, completed);
	}

	private TaskInstanceRequest(
		Method method, long callingUserId, Object... args)
		throws WorkflowException {
		super(method, WorkflowUtil.createUserCredential(callingUserId), args);
	}

	private static Method assignTaskInstanceToRole_long_long_String_Map_long;
	private static
		Method assignTaskInstanceToUser_long_UserCredential_String_Map_long;
	private static Method completeTaskInstance_long_long_String_Map;
	private static Method getTaskInstanceInfosByRole_long;
	private static Method getTaskInstanceInfosByRole_long_boolean;
	private static Method getTaskInstanceInfosByUser_long;
	private static Method getTaskInstanceInfosByUser_long_boolean;
	private static Method getTaskInstanceInfosByWorkflowInstance_long;
	private static Method getTaskInstanceInfosByWorkflowInstance_long_boolean;

	static {
		Class<?> clazz = TaskInstanceManager.class;
		try {
			assignTaskInstanceToRole_long_long_String_Map_long =
				clazz.getMethod(
					"assignTaskInstanceToRole", long.class, long.class,
					String.class, Map.class, long.class);
			assignTaskInstanceToUser_long_UserCredential_String_Map_long =
				clazz.getMethod(
					"assignTaskInstanceToUser", long.class,
					UserCredential.class, String.class, Map.class, long.class);
			completeTaskInstance_long_long_String_Map =
				clazz.getMethod(
					"completeTaskInstance", long.class, long.class,
					String.class, Map.class);
			getTaskInstanceInfosByRole_long =
				clazz.getMethod("getTaskInstanceInfosByRole", long.class);
			getTaskInstanceInfosByRole_long_boolean =
				clazz.getMethod(
					"getTaskInstanceInfosByRole", long.class, boolean.class);
			getTaskInstanceInfosByUser_long =
				clazz.getMethod("getTaskInstanceInfosByUser", long.class);
			getTaskInstanceInfosByUser_long_boolean =
				clazz.getMethod(
					"getTaskInstanceInfosByUser", long.class, boolean.class);
			getTaskInstanceInfosByWorkflowInstance_long =
				clazz.getMethod(
					"getTaskInstanceInfosByWorkflowInstance", long.class);
			getTaskInstanceInfosByWorkflowInstance_long_boolean =
				clazz.getMethod(
					"getTaskInstanceInfosByWorkflowInstance", long.class,
					boolean.class);
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

}