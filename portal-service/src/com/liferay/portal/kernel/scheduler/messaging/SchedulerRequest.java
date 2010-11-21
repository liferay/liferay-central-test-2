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

	public static final String COMMAND_DELETE = "DELETE";

	public static final String COMMAND_PAUSE = "PAUSE";

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_RESUME = "RESUME";

	public static final String COMMAND_RETRIEVE = "RETRIEVE";

	public static final String COMMAND_SHUTDOWN = "SHUTDOWN";

	public static final String COMMAND_STARTUP = "STARTUP";

	public static final String COMMAND_SUPPRESS_ERROR = "SUPPRESS_ERROR";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	public static final String COMMAND_UPDATE = "UPDATE";

	public static SchedulerRequest createDeleteRequest(String groupName) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_DELETE);
		schedulerRequest.setGroupName(groupName);

		return schedulerRequest;
	}

	public static SchedulerRequest createDeleteRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_DELETE);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	public static SchedulerRequest createPauseRequest(String groupName) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_PAUSE);
		schedulerRequest.setGroupName(groupName);

		return schedulerRequest;
	}

	public static SchedulerRequest createPauseRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_PAUSE);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	public static SchedulerRequest createRegisterRequest(
		Trigger trigger, String description, String destinationName,
		Message message) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_REGISTER);
		schedulerRequest.setDescription(description);
		schedulerRequest.setDestinationName(destinationName);
		schedulerRequest.setMessage(message);
		schedulerRequest.setTrigger(trigger);

		return schedulerRequest;
	}

	public static SchedulerRequest createResumeRequest(String groupName) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_RESUME);
		schedulerRequest.setGroupName(groupName);

		return schedulerRequest;
	}

	public static SchedulerRequest createResumeRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_RESUME);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	public static SchedulerRequest createRetrieveRequest() {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_RETRIEVE);

		return schedulerRequest;
	}

	public static SchedulerRequest createRetrieveRequest(String groupName) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_RETRIEVE);
		schedulerRequest.setGroupName(groupName);

		return schedulerRequest;
	}

	public static SchedulerRequest createRetrieveRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_RETRIEVE);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	/**
	 * @deprecated {@link #createRetrieveRequest(String)}
	 */
	public static SchedulerRequest createRetrieveRequest(Trigger trigger) {
		return createRetrieveRequest(trigger.getGroupName());
	}

	public static SchedulerRequest createRetrieveResponseRequest() {
		return new SchedulerRequest();
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		String jobName, String groupName, String description,
		String destinationName, Message message) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setDescription(description);
		schedulerRequest.setDestinationName(destinationName);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);
		schedulerRequest.setMessage(message);

		return schedulerRequest;
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		Trigger trigger, String description, String destinationName,
		Message message) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setDescription(description);
		schedulerRequest.setDestinationName(destinationName);
		schedulerRequest.setMessage(message);
		schedulerRequest.setTrigger(trigger);

		return schedulerRequest;
	}

	public static SchedulerRequest createShutdownRequest() {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_SHUTDOWN);

		return schedulerRequest;
	}

	public static SchedulerRequest createStartupRequest() {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_STARTUP);

		return schedulerRequest;
	}

	public static SchedulerRequest createSuppressErrorRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_SUPPRESS_ERROR);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	public static SchedulerRequest createUnregisterRequest(String groupName) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_UNREGISTER);
		schedulerRequest.setGroupName(groupName);

		return schedulerRequest;
	}

	public static SchedulerRequest createUnregisterRequest(
		String jobName, String groupName) {

		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_UNREGISTER);
		schedulerRequest.setGroupName(groupName);
		schedulerRequest.setJobName(jobName);

		return schedulerRequest;
	}

	/**
	 * @deprecated {@link #createUnregisterRequest(String, String)}
	 */
	public static SchedulerRequest createUnregisterRequest(Trigger trigger) {
		return createUnregisterRequest(
			trigger.getJobName(), trigger.getGroupName());
	}

	public static SchedulerRequest createUpdateRequest(Trigger trigger) {
		SchedulerRequest schedulerRequest = new SchedulerRequest();

		schedulerRequest.setCommand(COMMAND_UPDATE);
		schedulerRequest.setTrigger(trigger);

		return schedulerRequest;
	}

	public String getCommand() {
		return _command;
	}

	public String getDescription() {
		return _description;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getJobName() {
		return _jobName;
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

	public void setDescription(String description) {
		_description = description;
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setMessage(Message message) {
		_message = message;
	}

	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
	}

	private SchedulerRequest() {
	}

	private String _command;
	private String _description;
	private String _destinationName;
	private String _groupName;
	private String _jobName;
	private Message _message;
	private Trigger _trigger;

}