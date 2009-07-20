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
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.request.WorkflowInstanceRequest;

import java.util.List;
import java.util.Map;

public class WorkflowInstanceManagerProxy implements WorkflowInstanceManager {

	public WorkflowInstanceManagerProxy(
			SingleDestinationSynchronousMessageSender synchronousMessageSender)
	{
		_synchronousMessageSender = synchronousMessageSender;
	}

	public WorkflowInstanceInfo addContextInformation(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createAddContextInformationRequest(
								workflowInstanceId, context));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to add context information.", ex);
		}
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			long workflowInstanceId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceInfoRequest(
								workflowInstanceId,retrieveChildrenInfo));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instance information.", ex);
		}
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceInfoRequest(
								relationType, relationId,retrieveChildrenInfo));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instance information.", ex);
		}
	}

	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<WorkflowInstanceHistory>> response =
				(WorkflowResultContainer<List<WorkflowInstanceHistory>>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceHistoryRequest(
								workflowInstanceId, includeChildren));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instance history.", ex);
		}
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean retrieveChildrenInfo)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<WorkflowInstanceInfo>> response =
				(WorkflowResultContainer<List<WorkflowInstanceInfo>>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceInfosRequest(
								workflowDefinitionName,
								workflowDefinitionVersion,
								retrieveChildrenInfo));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instances information.", ex);
		}
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<WorkflowInstanceInfo>> response =
				(WorkflowResultContainer<List<WorkflowInstanceInfo>>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceInfosRequest(
								workflowDefinitionName,
								workflowDefinitionVersion, finished,
								retrieveChildrenInfo));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instances information.", ex);
		}
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		try {
			WorkflowResultContainer<List<WorkflowInstanceInfo>> response =
				(WorkflowResultContainer<List<WorkflowInstanceInfo>>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createGetWorkflowInstanceInfosRequest(
								relationType, relationId,retrieveChildrenInfo));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to get workflow instances information.", ex);
		}
	}

	public void removeWorkflowInstance(long workflowInstanceId)
		throws WorkflowException{

		try {
			WorkflowResultContainer<Object> response =
				(WorkflowResultContainer<Object>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createRemoveWorkflowInstanceRequest(
								workflowInstanceId));
			if (response.hasError()) {
				throw response.getException();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to remove workflow instance:", ex);
		}
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, Map<String, Object> attributes,
			long callingUserId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createSignalWorkflowInstanceRequest(
								workflowInstanceId, attributes, callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to signal workflow instance.", ex);
		}
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createSignalWorkflowInstanceRequest(
								workflowInstanceId, activityName, attributes,
								callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to signal workflow instance.", ex);
		}
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createStartWorkflowInstanceRequest(
								workflowDefinitionName,
								workflowDefinitionVersion, context,
								callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to start workflow instance.", ex);
		}
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId,
			String activityName)
		throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createStartWorkflowInstanceRequest(
								workflowDefinitionName,
								workflowDefinitionVersion, context,
								callingUserId, activityName));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to start workflow instance.", ex);
		}
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId) throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createStartWorkflowInstanceRequest(
								workflowDefinitionName,
								workflowDefinitionVersion, relationType,
								relationId, context, callingUserId));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to start workflow instance.", ex);
		}
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId, String activityName) throws WorkflowException {
		try {
			WorkflowResultContainer<WorkflowInstanceInfo> response =
				(WorkflowResultContainer<WorkflowInstanceInfo>)
					_synchronousMessageSender.send(
						WorkflowInstanceRequest.
							createStartWorkflowInstanceRequest(
								workflowDefinitionName,
								workflowDefinitionVersion, relationType,
								relationId, context, callingUserId,
								activityName));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to start workflow instance.", ex);
		}
	}

	private final
		SingleDestinationSynchronousMessageSender _synchronousMessageSender;

}