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

package com.liferay.portal.kernel.workflow.proxy;

import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.request.TaskInstanceRequest;

/**
 * <a href="TaskInstanceManagerProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceManagerProxy implements TaskInstanceManager {

	public TaskInstanceManagerProxy(
			SingleDestinationSynchronousMessageSender synchronousMessageSender)
	{
		_synchronousMessageSender = synchronousMessageSender;
	}

	public TaskInstanceInfo assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<TaskInstanceInfo> response =
				(WorkflowResultContainer<TaskInstanceInfo>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createAssignTaskInstanceToRoleRequest(
								taskInstanceId, roleId, comment, attributes, callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to assign task instance to role.", ex);
		}
	}

	public TaskInstanceInfo assignTaskInstanceToUser(
			long taskInstanceId, UserCredential userCredential, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<TaskInstanceInfo> response =
				(WorkflowResultContainer<TaskInstanceInfo>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createAssignTaskInstanceToUserRequest(
								taskInstanceId, userCredential, comment,
								attributes, callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to assign task instance to user.", ex);
		}
	}

	public TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long callingUserId, String comment,
			Map<String, Object> attributes) throws WorkflowException {
		try {
			WorkflowResultContainer<TaskInstanceInfo> response =
				(WorkflowResultContainer<TaskInstanceInfo>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.createCompleteTaskInstanceRequest(
							taskInstanceId, callingUserId, comment, attributes));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to complete task instance.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByRoleRequest(roleId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by role.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, boolean completed) throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByRoleRequest(
								roleId, completed));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by role.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(long userId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByUserRequest(userId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by user.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, boolean completed) throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByUserRequest(
								userId, completed));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by user.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByWorkflowInstanceRequest(
								workflowInstanceId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by workflow " +
				"instance.", ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId,
		boolean completed) throws WorkflowException {
		try {
			WorkflowResultContainer<List<TaskInstanceInfo>> response =
				(WorkflowResultContainer<List<TaskInstanceInfo>>)
					_synchronousMessageSender.send(
						TaskInstanceRequest.
							createGetTaskInstanceInfosByWorkflowInstanceRequest(
								workflowInstanceId, completed));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get task instances information by workflow " +
				"instance.", ex);
		}
	}

	private final
		SingleDestinationSynchronousMessageSender _synchronousMessageSender;

}