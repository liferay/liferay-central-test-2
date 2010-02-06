/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.poller.PollerProcessorUtil;

/**
 * <a href="PollerMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PollerMessageListener implements MessageListener {

	public void receive(Message message) {
		PollerResponse pollerResponse = null;

		try {
			PollerRequest pollerRequest = (PollerRequest)message.getPayload();

			String portletId = pollerRequest.getPortletId();

			PollerProcessor pollerProcessor =
				PollerProcessorUtil.getPollerProcessor(portletId);

			if (pollerRequest.isReceiveRequest()) {
				pollerResponse = new PollerResponse(
					portletId, pollerRequest.getChunkId());

				try {
					pollerProcessor.receive(pollerRequest, pollerResponse);
				}
				catch (PollerException pe) {
					_log.error(
						"Unable to receive poller request " + pollerRequest,
						pe);

					pollerResponse.setParameter(
						"pollerException", pe.getMessage());
				}
			}
			else {
				try {
					pollerProcessor.send(pollerRequest);
				}
				catch (PollerException pe) {
					_log.error(
						"Unable to send poller request " + pollerRequest,
						pe);
				}
			}
		}
		finally {
			String responseDestinationName =
				message.getResponseDestinationName();

			if (Validator.isNotNull(responseDestinationName)) {
				Message responseMessage = MessageBusUtil.createResponseMessage(
					message);

				responseMessage.setPayload(pollerResponse);

				MessageBusUtil.sendMessage(
					responseDestinationName, responseMessage);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(PollerMessageListener.class);

}