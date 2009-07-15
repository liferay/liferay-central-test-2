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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.request.WorkflowDefinitionRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WorkflowDefinitionManagerProxy.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowDefinitionManagerProxy
	implements WorkflowDefinitionManager {

	public WorkflowDefinitionManagerProxy(
			SingleDestinationSynchronousMessageSender synchronousMessageSender)
	{
		_synchronousMessageSender = synchronousMessageSender;
	}

	public void deployWorkflowDefinition(
		WorkflowDefinition workflowDefinition)
		throws WorkflowException {

		try {
			WorkflowResultContainer<Object> response =
				(WorkflowResultContainer<Object>)
					_synchronousMessageSender.send(
						WorkflowDefinitionRequest.
							createDeployWorkflowDefinitionRequest(
								workflowDefinition));
			if (response.hasError()) {
				throw response.getException();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to deploy workflow definition:" +
				workflowDefinition, ex);
		}
	}

	public List<WorkflowDefinition> getWorkflowDefinitions() {
		try {
			WorkflowResultContainer<List<WorkflowDefinition>> response =
				(WorkflowResultContainer<List<WorkflowDefinition>>)
					_synchronousMessageSender.send(
						WorkflowDefinitionRequest.
							createGetWorkflowDefinitionsRequest());
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (Exception ex) {
			_log.error("Unable to get workflow definitions.", ex);
			return new ArrayList<WorkflowDefinition>();
		}
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
			String workflowDefinitionName) {
		try {
			WorkflowResultContainer<List<WorkflowDefinition>> response =
				(WorkflowResultContainer<List<WorkflowDefinition>>)
					_synchronousMessageSender.send(
						WorkflowDefinitionRequest.
							createGetWorkflowDefinitionsRequest(
								workflowDefinitionName));
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (Exception ex) {
			_log.error("Unable to get workflow definitions with name:" +
				workflowDefinitionName, ex);
			return new ArrayList<WorkflowDefinition>();
		}
	}

	public boolean isSupportsVersioning() {
		try {
			WorkflowResultContainer<Boolean> response =
				(WorkflowResultContainer<Boolean>)
					_synchronousMessageSender.send(
						WorkflowDefinitionRequest.
							createIsSupportsVersioningRequest());
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (Exception ex) {
			_log.error("Unable to query does the underline workflow engine " +
				"supports versioning.", ex);
			return false;
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WorkflowDefinitionManagerProxy.class);
	private SingleDestinationSynchronousMessageSender _synchronousMessageSender;

}