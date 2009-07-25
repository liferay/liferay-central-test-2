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
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.proxy.WorkflowResultContainer;
import com.liferay.portal.kernel.workflow.request.BaseRequest;

import java.util.Formatter;

public class WorkflowMessageListener implements MessageListener {

	public WorkflowMessageListener(
		Object manager, Class<? extends BaseRequest> requestClazz,
		MessageBus messageBus) {
		_manager = manager;
		_requestClazz = requestClazz;
		_messageBus=messageBus;
	}

	public void receive(Message message) {

		Object payload = message.getPayload();
		String responseDestination = message.getResponseDestinationName();

		WorkflowResultContainer resultContainer = new WorkflowResultContainer();
		WorkflowException workflowException = null;
		try {
			// there must always be a payload having the request
			if (payload == null) {
				workflowException =
					new WorkflowException(MISSING_REQUEST_ERROR);
				_log.error(MISSING_REQUEST_ERROR);
			}
			// check for being the right request type
			else if (!_requestClazz.isAssignableFrom(payload.getClass())) {
				String errorMessage =
					new Formatter().format(
						WRONG_REQUEST_ERROR, _requestClazz.getName(),
						payload.getClass().getName()).
					toString();

				workflowException = new WorkflowException(errorMessage);
				_log.error(errorMessage);
			}
			else {

				BaseRequest request = (BaseRequest) payload;
				resultContainer.setResult(request.execute(_manager));
			}
		}
		catch (WorkflowException ex) {
			workflowException = new WorkflowException(EXECUTE_ERROR);
			_log.error(EXECUTE_ERROR, ex);

		}
		finally {
			if (Validator.isNotNull(responseDestination)) {
				Message responseMessage =
					MessageBusUtil.createResponseMessage(message);
				resultContainer.setException(workflowException);
				responseMessage.setPayload(resultContainer);
				_messageBus.sendMessage(
					responseDestination, responseMessage);
			}

		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WorkflowMessageListener.class);
	private static String MISSING_REQUEST_ERROR = "Missing workflow request.";
	private static String WRONG_REQUEST_ERROR =
		"Payload type is not from expected type [%s], but was [%s]";
	private static String EXECUTE_ERROR = "Unable to execute request.";
	private final Object _manager;
	private final Class<? extends BaseRequest> _requestClazz;
	private final MessageBus _messageBus;

}