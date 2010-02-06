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

package com.liferay.portal.scheduler.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.List;

/**
 * <a href="SchedulerMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class SchedulerMessageListener implements MessageListener {

	public SchedulerMessageListener() {
	}

	/**
	 * @deprecated
	 */
	public SchedulerMessageListener(
		MessageSender messageSender, SchedulerEngine schedulerEngine) {

		_messageSender = messageSender;
		_schedulerEngine = schedulerEngine;
	}

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	public void setMessageSender(MessageSender messageSender) {
		_messageSender = messageSender;
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	protected void doReceive(Message message) throws Exception {
		SchedulerRequest schedulerRequest =
			(SchedulerRequest)message.getPayload();

		String command = schedulerRequest.getCommand();

		if (command.equals(SchedulerRequest.COMMAND_REGISTER)) {
			_schedulerEngine.schedule(
				schedulerRequest.getTrigger(),
				schedulerRequest.getDescription(),
				schedulerRequest.getDestination(),
				schedulerRequest.getMessage());
		}
		else if (command.equals(SchedulerRequest.COMMAND_RETRIEVE)) {
			doCommandRetrieve(message, schedulerRequest);
		}
		else if (command.equals(SchedulerRequest.COMMAND_SHUTDOWN)) {
			_schedulerEngine.shutdown();
		}
		else if (command.equals(SchedulerRequest.COMMAND_STARTUP)) {
			_schedulerEngine.start();
		}
		else if (command.equals(SchedulerRequest.COMMAND_UNREGISTER)) {
			_schedulerEngine.unschedule(schedulerRequest.getTrigger());
		}
	}

	protected void doCommandRetrieve(
			Message message, SchedulerRequest schedulerRequest)
		throws Exception {

		List<SchedulerRequest> schedulerRequests =
			_schedulerEngine.getScheduledJobs(
				schedulerRequest.getTrigger().getGroupName());

		Message responseMessage = MessageBusUtil.createResponseMessage(
			message, schedulerRequests);

		_messageSender.send(
			responseMessage.getDestinationName(), responseMessage);
	}

	private static Log _log =
		LogFactoryUtil.getLog(SchedulerMessageListener.class);

	private MessageSender _messageSender;
	private SchedulerEngine _schedulerEngine;

}