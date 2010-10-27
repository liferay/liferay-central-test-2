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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.Trigger;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 */
public class SchedulerRequest implements Serializable {

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_RETRIEVE = "RETRIEVE";

	public static final String COMMAND_SHUTDOWN = "SHUTDOWN";

	public static final String COMMAND_STARTUP = "STARTUP";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	public static SchedulerRequest createRegisterRequest(
		Trigger trigger, String description, String destination,
		Message message) {

		return new SchedulerRequest(
			COMMAND_REGISTER, trigger, null, null, description, destination,
			message);
	}

	public static SchedulerRequest createRetrieveRequest(String groupName) {
		return new SchedulerRequest(
			COMMAND_RETRIEVE, null, groupName, null, null, null, null);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		Trigger trigger, String description, Message message) {

		return new SchedulerRequest(
			null, trigger, null, null, description, null, message);
	}

	public static SchedulerRequest createShutdownRequest() {
		return new SchedulerRequest(
			COMMAND_SHUTDOWN, null, null, null, null, null, null);
	}

	public static SchedulerRequest createStartupRequest() {
		return new SchedulerRequest(
			COMMAND_STARTUP, null, null, null, null, null, null);
	}

	public static SchedulerRequest createUnregisterRequest(
		String jobName, String groupName) {

		return new SchedulerRequest(
			COMMAND_UNREGISTER, null, jobName, groupName, null, null, null);
	}

	public String getCommand() {
		return _command;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getJobName() {
		return _jobName;
	}

	public String getDescription() {
		return _description;
	}

	public String getDestination() {
		return _destination;
	}

	public Message getMessage() {
		return _message;
	}

	public Trigger getTrigger() {
		return _trigger;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public void setMessage(Message message) {
		_message = message;
	}

	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
	}

	private SchedulerRequest(
		String command, Trigger trigger, String jobName, String groupName,
		String description, String destination, Message message) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
		_trigger = trigger;
		_description = description;
		_destination = destination;
		_message = message;

	}

	private String _command;
	private String _description;
	private String _destination;
	private String _groupName;
	private String _jobName;
	private Message _message;
	private Trigger _trigger;

}