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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.util.List;
import java.util.Map;

/**
 * <a href="BaseWorkflowInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The abstract proxy class implementing the {@link WorkflowInstanceManager} and
 * gets instrumented by Spring using the {@link ManagerProxyAdvice} by
 * serializing the method and its arguments being invoked on the proxy using the
 * event bus to finally invoke it on the target.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public class BaseWorkflowInstanceManager extends BaseWorkflowProxy
	implements WorkflowInstanceManager {

	public BaseWorkflowInstanceManager(
		SingleDestinationSynchronousMessageSender synchronousMessageSender) {
		super(synchronousMessageSender);
	}

	public WorkflowInstanceInfo addContextInformation(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<String> getPossibleNextActivityNames(
		long workflowInstanceId, long userId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			long workflowInstanceId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean retrieveChildrenInfo) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void removeWorkflowInstance(long workflowInstanceId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
		long workflowInstanceId, Map<String, Object> attributes,
		long callingUserId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId,
			String activityName) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId, String activityName) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}