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

package com.liferay.portal.kernel.workflow.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.proxy.WorkflowResultContainer;
import com.liferay.portal.kernel.workflow.request.WorkflowInstanceRequest;

/**
 * <a href="WorkflowInstanceMessageListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowInstanceMessageListener implements MessageListener {

	public WorkflowInstanceMessageListener(
			WorkflowInstanceManager workflowInstanceManager) {
		_workflowInstanceManager = workflowInstanceManager;
	}

	public void receive(Message message) {
		Object payload = message.getPayload();
		if (payload == null) {
			_log.error("Missing WorkflowInstanceRequest.");
		}
		else if (!(payload instanceof WorkflowInstanceRequest)) {
			_log.error(
				"Payload type is not WorkflowInstanceRequest:" +
				payload.getClass().getName());
		}
		else {
			WorkflowInstanceRequest request = (WorkflowInstanceRequest) payload;
			String responseDestinationName = message.getResponseDestinationName();
			Object result = null;
			try {
				result = request.execute(_workflowInstanceManager);
				if (request.hasReturnValue()==false){
					//setup dummy result to satisfy MessageBus
					result=new Object();
				}
			}
			catch (WorkflowException ex) {
				_log.error("Unable to execute request.", ex);
			}
			finally {
				if (Validator.isNotNull(responseDestinationName)) {
					Message responseMessage =
						MessageBusUtil.createResponseMessage(message);
					responseMessage.setPayload(
						new WorkflowResultContainer(result));
					MessageBusUtil.sendMessage(
						responseDestinationName, responseMessage);
				}
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(TaskInstanceMessageListener.class);
	private final WorkflowInstanceManager _workflowInstanceManager;

}