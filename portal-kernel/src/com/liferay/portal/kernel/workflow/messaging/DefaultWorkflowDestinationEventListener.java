/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.workflow.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="DefaultWorkflowDestinationEventListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultWorkflowDestinationEventListener
	extends BaseDestinationEventListener {


	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Un-registering default workflow engine: " +
				_workflowEngineName);
		}
		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_DEFINITION,
			_workflowDefinitionManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_ENGINE, _workflowEngineManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, _workflowInstanceManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_LOG, _workflowLogManagerListener);

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.WORKFLOW_TASK, _workflowTaskManagerListener);

	}

	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering default workflow engine: " +
				_workflowEngineName);
		}
		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_DEFINITION,
			_workflowDefinitionManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_ENGINE, _workflowEngineManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE,
			_workflowInstanceManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_LOG, _workflowLogManagerListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, _workflowTaskManagerListener);
	}

	public void setWorkflowEngineName(String workflowEngineName) {
		_workflowEngineName = workflowEngineName;
	}

	public void setWorkflowDefinitionManagerListener(
		MessageListener workflowDefinitionManagerListener) {
		_workflowDefinitionManagerListener = workflowDefinitionManagerListener;
	}

	public void setWorkflowEngineManagerListener(
		MessageListener workflowEngineManagerListener) {
		_workflowEngineManagerListener = workflowEngineManagerListener;
	}

	public void setWorkflowInstanceManagerListener(
		MessageListener workflowInstanceManagerListener) {
		_workflowInstanceManagerListener = workflowInstanceManagerListener;
	}

	public void setWorkflowTaskManagerListener(
		MessageListener workflowTaskManagerListener) {
		_workflowTaskManagerListener = workflowTaskManagerListener;
	}

	public void setWorkflowLogManagerListener(
		MessageListener workflowLogManagerListener) {
		_workflowLogManagerListener = workflowLogManagerListener;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultWorkflowDestinationEventListener.class);


	private String _workflowEngineName;
	private MessageListener _workflowDefinitionManagerListener;
	private MessageListener _workflowEngineManagerListener;
	private MessageListener _workflowInstanceManagerListener;
	private MessageListener _workflowTaskManagerListener;
	private MessageListener _workflowLogManagerListener;
}
