/*
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

package com.liferay.portal.kernel.proxy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="WorkflowMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 */
public class ServiceProxyMessageListener implements MessageListener {

	public ServiceProxyMessageListener(Object manager) {
		_manager = manager;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void receive(Message message) {
		ServiceResponseContainer serviceResponseContainer =
			new ServiceResponseContainer();

		try {
			Object payload = message.getPayload();

			if (payload == null) {
				throw new Exception("Payload is null");
			}
			else if (!ServiceRequest.class.isAssignableFrom(
						payload.getClass())) {

				throw new Exception(
					"Payload " + payload.getClass() + " is not of type " +
						ServiceRequest.class.getName());
			}
			else {
				ServiceRequest serviceRequest = (ServiceRequest)payload;

				Object result = serviceRequest.execute(_manager);

				serviceResponseContainer.setResult(result);
			}
		}
		catch (Exception we) {
			_log.error(we, we);

			serviceResponseContainer.setException(we);
		}
		finally {
			String responseDestination = message.getResponseDestinationName();

			if (Validator.isNotNull(responseDestination)) {
				Message responseMessage = MessageBusUtil.createResponseMessage(
					message);

				responseMessage.setPayload(serviceResponseContainer);

				_messageBus.sendMessage(responseDestination, responseMessage);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ServiceProxyMessageListener.class);

	private Object _manager;
	private MessageBus _messageBus;

}