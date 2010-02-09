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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

/**
 * <a href="DefaultSynchronousMessageSender.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 */
public class DefaultSynchronousMessageSender
	implements SynchronousMessageSender {

	public DefaultSynchronousMessageSender() {
	}

	/**
	 * @deprecated
	 */
	public DefaultSynchronousMessageSender(
		MessageBus messageBus, PortalUUID portalUUID, long timeout) {

		_messageBus = messageBus;
		_portalUUID = portalUUID;
		_timeout = timeout;
	}

	public Object send(String destinationName, Message message)
		throws MessageBusException {

		return send(destinationName, message, _timeout);
	}

	public Object send(String destinationName, Message message, long timeout)
		throws MessageBusException {

		Destination destination = _messageBus.getDestination(destinationName);

		if (destination == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Destination " + destinationName + " is not configured");
			}

			return null;
		}

		if (destination.getMessageListenerCount() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Destination " + destinationName +
						" does not have any message listeners");
			}

			return null;
		}

		message.setDestinationName(destinationName);

		String responseDestinationName = message.getResponseDestinationName();

		// Create a temporary destination if no response destination is
		// configured

		if (Validator.isNull(responseDestinationName) ||
			!_messageBus.hasDestination(responseDestinationName)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Response destination " + responseDestinationName +
						" is not configured");
			}

			message.setResponseDestinationName(
				DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE);
		}

		String responseId = _portalUUID.generate();

		message.setResponseId(responseId);

		SynchronousMessageListener synchronousMessageListener =
			new SynchronousMessageListener(_messageBus, message, timeout);

		return synchronousMessageListener.send();
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setPortalUUID(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultSynchronousMessageSender.class);

	private MessageBus _messageBus;
	private PortalUUID _portalUUID;
	private long _timeout;

}