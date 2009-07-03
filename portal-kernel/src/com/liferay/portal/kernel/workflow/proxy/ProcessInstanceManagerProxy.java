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
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.ProcessInstanceHistory;
import com.liferay.portal.kernel.workflow.ProcessInstanceInfo;
import com.liferay.portal.kernel.workflow.ProcessInstanceManager;
import com.liferay.portal.kernel.workflow.ProcessInstanceRequest;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.List;
import java.util.Map;

/**
 * <a href="ProcessInstanceManagerProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class ProcessInstanceManagerProxy implements ProcessInstanceManager {

	public ProcessInstanceManagerProxy(
			SingleDestinationMessageSender messageSender,
			SingleDestinationSynchronousMessageSender
			synchronousMessageSender) {
		_messageSender = messageSender;
		_synchronousMessageSender = synchronousMessageSender;
	}

	public ProcessInstanceInfo getProcessInstanceInfo(long processInstanceId)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetRequest(processInstanceId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get processInstance by id:" +
				processInstanceId, ex);
		}
	}

	public ProcessInstanceInfo getProcessInstanceInfo(
			long processInstanceId, boolean retrieveTokenInfo)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetRequest(
					processInstanceId, retrieveTokenInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get processInstance by id:" +
				processInstanceId +
				" retrieve token:" + retrieveTokenInfo, ex);
		}
	}

	public List<ProcessInstanceInfo> getProcessInstanceInfos(
			String workflowDefinitionName) throws WorkflowException {
		try {
			return (List<ProcessInstanceInfo>) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetRequest(
					workflowDefinitionName));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get processInstances for " +
				"workflowDefinitionName:" + workflowDefinitionName, ex);
		}
	}

	public List<ProcessInstanceInfo> getProcessInstanceInfos(
			String workflowDefinitionName, boolean retrieveTokenInfo)
		throws WorkflowException {
		try {
			return (List<ProcessInstanceInfo>) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetRequest(
					workflowDefinitionName, retrieveTokenInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get processInstances for " +
				"workflowDefinitionName:" + workflowDefinitionName +
				" retrieve token:" + retrieveTokenInfo, ex);
		}
	}

	public List<ProcessInstanceInfo> getProcessInstanceInfos(
			String workflowDefinitionName, boolean retrieveTokenInfo,
			boolean finished) throws WorkflowException {
		try {
			return (List<ProcessInstanceInfo>) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetRequest(
					workflowDefinitionName, retrieveTokenInfo, finished));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get processInstances for " +
				"workflowDefinitionName:" + workflowDefinitionName +
				" retrieve token:" + retrieveTokenInfo +
				" finished:" + finished, ex);
		}
	}

	public ProcessInstanceHistory getProcessInstanceHistory(
			long processInstanceId)	throws WorkflowException {
		try {
			return (ProcessInstanceHistory) _synchronousMessageSender.send(
				ProcessInstanceRequest.createGetHistoryRequest(
					processInstanceId));
		}
		catch (MessageBusException ex) {
			throw new WorkflowException("Unable to get history for id:" +
				processInstanceId, ex);
		}
	}

	public void removeProcessInstance(long processInstanceId) {
		_messageSender.send(ProcessInstanceRequest.createRemoveRequest(
			processInstanceId));
	}

	public ProcessInstanceInfo signalProcessInstanceById(
			long processInstanceId, Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createSignalProcessInstanceByIdRequest(
					processInstanceId, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to signal by " +
				"processInstanceId:" + processInstanceId, ex);
		}
	}

	public ProcessInstanceInfo signalProcessInstanceById(
			long processInstanceId, String activityName,
			Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createSignalProcessInstanceByIdRequest(
					processInstanceId, activityName, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to signal by " +
				"processInstanceId:" + processInstanceId + " to activityName:" +
				activityName, ex);
		}
	}

	public ProcessInstanceInfo signalProcessInstanceByTokenId(
			long tokenId, Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.
					createSignalProcessInstanceByTokenIdRequest(
						tokenId, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to signal by tokenId:" +
				tokenId, ex);
		}
	}

	public ProcessInstanceInfo signalProcessInstanceByTokenId(
			long tokenId, String activityName, Map<String, Object> contextInfo)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.
					createSignalProcessInstanceByTokenIdRequest(
						tokenId, activityName, contextInfo));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to signal by tokenId:" +
				tokenId + " to activityName:" + activityName, ex);
		}
	}

	public ProcessInstanceInfo startProcessInstance(
			String workflowDefinitionName,
			Map<String, Object> contextInfo, long userId)
		throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createStartRequest(
					workflowDefinitionName, contextInfo, userId));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to start " +
				"workflowDefinitionName:" + workflowDefinitionName +
				" by userId:" + userId +
				" with contextInfo:" + contextInfo, ex);
		}
	}

	public ProcessInstanceInfo startProcessInstance(
			String workflowDefinitionName, Map<String, Object> contextInfo,
			long userId, String activityName) throws WorkflowException {
		try {
			return (ProcessInstanceInfo) _synchronousMessageSender.send(
				ProcessInstanceRequest.createStartRequest(
					workflowDefinitionName, contextInfo, userId, activityName));
		} catch (MessageBusException ex) {
			throw new WorkflowException("Unable to start " +
				"workflowDefinitionName:" + workflowDefinitionName +
				" by userId:" + userId +
				" with contextInfo:" + contextInfo +
				" to activityName:" + activityName, ex);
		}
	}

	private SingleDestinationMessageSender _messageSender;
	private SingleDestinationSynchronousMessageSender _synchronousMessageSender;

}