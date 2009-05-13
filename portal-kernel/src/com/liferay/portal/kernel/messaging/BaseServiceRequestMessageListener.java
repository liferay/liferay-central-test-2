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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

/**
 * <a href="BaseServiceMessageListener.java.html"><b><i>View Source</i></b></a>
 * <p/>
 * Message listener that all service oriented message listeners should extend
 * from.  This provides facilities to report on the status of a service request
 * with information like success/error, original request payload, and also
 * processing time.
 *
 * @author Michael C. Han
 */
public abstract class BaseServiceRequestMessageListener
	implements MessageListener {

	/**
	 * Creates a listener with a message sender to send response messages when the
	 * service request completes
	 *
	 * @param statusSender   to send back the service response
	 * @param responseSender used to send back reponses (if needed)
	 */
	public BaseServiceRequestMessageListener(
		SingleDestinationMessageSender statusSender,
		MessageSender responseSender) {

		_responseSender = responseSender;
		_statusSender = statusSender;
	}

	public void receive(Message message) {
		ServiceRequestStatus requestStatus = new ServiceRequestStatus();
		requestStatus.startTimer();
		try {
			doReceive(message, requestStatus);
		}
		catch (Exception e) {
			_log.error(
				"Unable to process request: " + message.getDestination(), e);
			requestStatus.setError(e);
		}
		finally {
			requestStatus.stopTimer();
			Message responseMessage = new Message();
			responseMessage.setPayload(requestStatus);
			_messageSender.send(responseMessage);
		}

	}

	protected MessageSender getResponseSender() {
		return _responseSender;
	}

	protected abstract void doReceive(
		Message message, ServiceRequestStatus serviceRequestStatus)
		throws Exception;

	private static Log _log =
		LogFactoryUtil.getLog(BaseServiceRequestMessageListener.class);

	private SingleDestinationMessageSender _messageSender;
	private MessageSender _responseSender;
	private SingleDestinationMessageSender _statusSender;
}