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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

/**
 * <a href="BaseMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public abstract class BaseMessageListener implements MessageListener {

	public BaseMessageListener() {
	}

	/**
	 * @deprecated
	 */
	public BaseMessageListener(
		SingleDestinationMessageSender statusSender,
		MessageSender responseSender) {

		_statusSender = statusSender;
		_responseSender = responseSender;
	}

	public void receive(Message message) {
		MessageStatus messageStatus = new MessageStatus();

		messageStatus.startTimer();

		try {
			doReceive(message, messageStatus);
		}
		catch (Exception e) {
			_log.error(
				"Unable to process request " + message.getDestinationName(), e);

			messageStatus.setException(e);
		}
		finally {
			messageStatus.stopTimer();

			_statusSender.send(messageStatus);
		}
	}

	public void setResponseSender(MessageSender responseSender) {
		_responseSender = responseSender;
	}

	public void setStatusSender(SingleDestinationMessageSender statusSender) {
		_statusSender = statusSender;
	}

	protected abstract void doReceive(
			Message message, MessageStatus messageStatus)
		throws Exception;

	protected MessageSender getResponseSender() {
		return _responseSender;
	}

	private static Log _log = LogFactoryUtil.getLog(BaseMessageListener.class);

	private MessageSender _responseSender;
	private SingleDestinationMessageSender _statusSender;

}