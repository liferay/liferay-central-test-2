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
import com.liferay.portal.kernel.workflow.proxy.WorkflowResultContainer;
import com.liferay.portal.kernel.workflow.request.BaseRequest;

/**
 * <a href="WorkflowMessageListener.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The generic message listener for workflow messages. The object (manager) to
 * invoke the method on has to be injected using constructor injection as well
 * as the expected request class to be handled by this listener.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public class WorkflowMessageListener implements MessageListener {

	/**
	 * Constructor using the manager to invoke methods on and the expected
	 * request class this listener will be able to handle.
	 * 
	 * @param manager the manager instance to invoke methods on given by the
	 *            payload of the message
	 * @param requestClazz the expected request class as the payload of the
	 *            message being handled by this listener
	 */
	public WorkflowMessageListener(
		Object manager, Class<? extends BaseRequest> requestClazz) {
		_manager = manager;
		_requestClazz = requestClazz;
	}

	/**
	 * @see com.liferay.portal.kernel.messaging.MessageListener#receive(com.liferay.portal.kernel.messaging.Message)
	 */
	public void receive(Message message) {
		Object payload = message.getPayload();
		
		// there must always be a payload having the request
		if (payload == null) {
			_log.error("Missing TaskInstanceRequest.");
		}
		
		// check for being the right request type
		else if (!_requestClazz.isAssignableFrom(payload.getClass())) {
			_log.error("Payload type is not from expected type [" +
				_requestClazz.getName() + "], but was [" +
				payload.getClass().getName() + "]");
		}
		else {
			BaseRequest request = (BaseRequest) payload;
			String responseDestination = message.getResponseDestination();
			Object result = null;
			try {
				result = request.execute(_manager);
				if (request.hasReturnValue() == false) {
					// setup dummy result to satisfy MessageBus
					result = new Object();
				}
			}
			catch (WorkflowException ex) {
				_log.error("Unable to execute request.", ex);
			}
			finally {
				if (Validator.isNotNull(responseDestination)) {
					Message responseMessage =
						MessageBusUtil.createResponseMessage(message);
					responseMessage.setPayload(new WorkflowResultContainer<Object>(
						result));
					MessageBusUtil.sendMessage(
						responseDestination, responseMessage);
				}
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WorkflowMessageListener.class);
	private final Object _manager;
	private final Class<? extends BaseRequest> _requestClazz;
}
