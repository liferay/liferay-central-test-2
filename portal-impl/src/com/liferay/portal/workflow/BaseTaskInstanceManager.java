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
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.List;
import java.util.Map;

/**
 * <a href="BaseTaskInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The abstract proxy class implementing the {@link TaskInstanceManager} and
 * gets instrumented by Spring using the {@link ManagerProxyAdvice} by
 * serializing the method and its arguments being invoked on the proxy using the
 * event bus to finally invoke it on the target.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public class BaseTaskInstanceManager extends BaseWorkflowProxy
	implements TaskInstanceManager {

	public BaseTaskInstanceManager(
		SingleDestinationSynchronousMessageSender synchronousMessageSender) {
		super(synchronousMessageSender);
	}

	public TaskInstanceInfo assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public TaskInstanceInfo assignTaskInstanceToUser(
			long taskInstanceId, UserCredential userCredential, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String comment,
		Map<String, Object> attributes) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String activityName, String comment,
		Map<String, Object> attributes) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<String> getPossibleNextActivityNames(
		long taskInstanceId, long userId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getTaskInstanceCountForCredential(UserCredential userCredential)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getTaskInstanceCountForRole(long roleId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getTaskInstanceCountForUser(long userId)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, boolean completed)
		throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId,
		boolean completed) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, boolean completed) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, boolean completed) throws WorkflowException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}