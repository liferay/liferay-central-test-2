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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

/**
 * <a href="DefaultSynchronousMessageSender.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Michael C. Han
 *
 */
public class DefaultSynchronousMessageSender
	implements SynchronousMessageSender {

	public DefaultSynchronousMessageSender(
		MessageBus messageBus, PortalUUID portalUUID, long timeout) {

		_messageBus = messageBus;
		_portalUUID = portalUUID;
		_timeout = timeout;
	}

	public Object sendMessage(String destination, Message message)
		throws MessageBusException {

		return sendMessage(destination, message, _timeout);
	}

	public Object sendMessage(String destination, Message message, long timeout)
		throws MessageBusException {

		message.setDestination(destination);

		String responseDestination = message.getResponseDestination();

		//if no response destination previously created, go ahead and create
		//a temporary destination
		if (Validator.isNull(responseDestination) ||
			!_messageBus.hasDestination(responseDestination)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Response destination " + responseDestination +
						" is not configured");
			}

			message.setResponseDestination(
				DestinationNames.SERVICE_SYNCHRONOUS_RESPONSE);
		}

		String responseId = _portalUUID.generate();

		message.setResponseId(responseId);

		SynchronousMessageListener synchronousMessageListener =
			new SynchronousMessageListener(_messageBus, message, timeout);

		return synchronousMessageListener.send();
	}

	private static Log _log =
		LogFactoryUtil.getLog(DefaultSynchronousMessageSender.class);

	private MessageBus _messageBus;
	private PortalUUID _portalUUID;
	private long _timeout;

}