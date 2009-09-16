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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * <a href="WorkflowMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 */
public class WorkflowMessageListener implements MessageListener {

	public WorkflowMessageListener(Object manager, MessageBus messageBus) {
		_manager = manager;
		_messageBus = messageBus;
	}

	public void receive(Message message) {
		WorkflowResultContainer workflowResultContainer =
			new WorkflowResultContainer();

		try {
			Object payload = message.getPayload();

			if (payload == null) {
				throw new WorkflowException("Payload is null");
			}
			else if (!WorkflowRequest.class.isAssignableFrom(
						payload.getClass())) {

				throw new WorkflowException(
					"Payload " + payload.getClass() + " is not of type " +
						WorkflowRequest.class.getName());
			}
			else {
				WorkflowRequest workflowRequest = (WorkflowRequest)payload;

				Object result = workflowRequest.execute(_manager);

				workflowResultContainer.setResult(result);
			}
		}
		catch (WorkflowException we) {
			_log.error(we, we);

			workflowResultContainer.setWorkflowException(we);
		}
		finally {
			String responseDestination = message.getResponseDestinationName();

			if (Validator.isNotNull(responseDestination)) {
				Message responseMessage = MessageBusUtil.createResponseMessage(
					message);

				responseMessage.setPayload(workflowResultContainer);

				_messageBus.sendMessage(responseDestination, responseMessage);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WorkflowMessageListener.class);

	private Object _manager;
	private MessageBus _messageBus;

}