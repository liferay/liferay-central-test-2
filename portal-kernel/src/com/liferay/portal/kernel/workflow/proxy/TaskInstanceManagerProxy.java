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

import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.TaskInstanceRequest;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="TaskInstanceManagerProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceManagerProxy implements TaskInstanceManager {

	public TaskInstanceManagerProxy(
			SingleDestinationSynchronousMessageSender
			synchronousMessageSender) {
		_synchronousMessageSender = synchronousMessageSender;
	}

	public TaskInstanceInfo assignTaskInstance(
			long taskInstanceId, long actorId, String description,
			Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (TaskInstanceInfo) _synchronousMessageSender.send(
				TaskInstanceRequest.createAssignRequest(
					taskInstanceId, actorId, description, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to assign " +
				"taskInstanceId:" + taskInstanceId + " to " +
				"userId:" + actorId, ex);
		}
	}

	public TaskInstanceInfo fulfillTaskInstance(
			long taskInstanceId, long actorId, String description,
			Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (TaskInstanceInfo) _synchronousMessageSender.send(
				TaskInstanceRequest.createFulfillRequest(
					taskInstanceId, actorId, description, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to fulfil " +
				"taskInstanceId:" + taskInstanceId + " by " +
				"userId:" + actorId, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByProcessInstanceId(
			long processInstanceId) throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByProcessInstanceIdRequest(
					processInstanceId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"processInstanceId:" + processInstanceId, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByProcessInstanceId(
			long processInstanceId, boolean finished) throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByProcessInstanceIdRequest(
					processInstanceId, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"processInstanceId:" + processInstanceId +
				" finished:" + finished, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId)
		throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
					TaskInstanceRequest.createGetByRoleRequest(roleId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"roleId:" + roleId, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, boolean finished) throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByRoleRequest(roleId, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"roleId:" + roleId + " finished:" + finished, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRoles(Set<Long> roleIds)
		throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByRolesRequest(roleIds));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"roleIds:" + roleIds, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRoles(
			Set<Long> roleIds, boolean finished) throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByRolesRequest(roleIds, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"roleIds:" + roleIds + " finished:" + finished, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByTokenId(long tokenId)
		throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByTokenIdRequest(tokenId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"tokenId:" + tokenId, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByTokenId(
			long tokenId, boolean finished)	throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByTokenIdRequest(
					tokenId, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"tokenId:" + tokenId + " finished:" + finished, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUserId(long actorId)
		throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByUserIdRequest(actorId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"actorId:" + actorId, ex);
		}
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUserId(
			long actorId, boolean finished)
		throws WorkflowException {
		try {
			return (List<TaskInstanceInfo>) _synchronousMessageSender.send(
				TaskInstanceRequest.createGetByUserIdRequest(
					actorId, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get taskInstances by " +
				"actorId:" + actorId + " finished:" + finished, ex);
		}
	}

	private SingleDestinationSynchronousMessageSender _synchronousMessageSender;

}