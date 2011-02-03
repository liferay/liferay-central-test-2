/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 */

package com.liferay.portal.poller.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.poller.DefaultPollerResponse;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.poller.PollerProcessorUtil;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PollerMessageListener extends BaseMessageListener {

	protected void doReceive(Message message) throws Exception {
		PollerResponse pollerResponse = null;

		try {
			PollerRequest pollerRequest = (PollerRequest)message.getPayload();

			String portletId = pollerRequest.getPortletId();

			PollerProcessor pollerProcessor =
				PollerProcessorUtil.getPollerProcessor(portletId);

			if (pollerRequest.isReceiveRequest()) {
				pollerResponse = new DefaultPollerResponse(
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

	private static Log _log = LogFactoryUtil.getLog(
		PollerMessageListener.class);

}