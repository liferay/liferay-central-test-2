/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.scheduler.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
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
		else if (command.equals(SchedulerRequest.COMMAND_SUPPRESS_ERROR)) {
			_schedulerEngine.suppressError(
				schedulerRequest.getJobName(), schedulerRequest.getGroupName());
		}
		else if (command.equals(SchedulerRequest.COMMAND_UNREGISTER)) {
			_schedulerEngine.unschedule(
				schedulerRequest.getJobName(), schedulerRequest.getGroupName());
		}
	}

	protected void doCommandRetrieve(
			Message message, SchedulerRequest schedulerRequest)
		throws Exception {

		Object schedulerResponse = null;

		String jobName = schedulerRequest.getJobName();
		String groupName = schedulerRequest.getGroupName();

		if (groupName == null) {
			schedulerResponse = _schedulerEngine.getAllScheduledJobs();
		}
		else if (jobName == null) {
			schedulerResponse = _schedulerEngine.getScheduledJobs(
				groupName);
		}
		else {
			schedulerResponse = _schedulerEngine.getScheduledJob(
				jobName, groupName);

			if (schedulerRequest == null) {
				schedulerResponse =
					SchedulerRequest.createRetrieveResponseRequest();
			}
		}

		Message responseMessage = MessageBusUtil.createResponseMessage(
			message, schedulerResponse);

		_messageSender.send(
			responseMessage.getDestinationName(), responseMessage);
	}

	private static Log _log = LogFactoryUtil.getLog(
		SchedulerMessageListener.class);

	private MessageSender _messageSender;
	private SchedulerEngine _schedulerEngine;

}