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
 * @author Tina Tian
 */
public class SchedulerResponse implements Serializable {

	public static SchedulerResponse createSchedulerResponse() {
		return new SchedulerResponse();
	}

	public static SchedulerResponse createSchedulerResponse(
		String jobName, String groupName, String description,
		String destinationName, Message message) {

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		schedulerResponse.setDescription(description);
		schedulerResponse.setDestinationName(destinationName);
		schedulerResponse.setGroupName(groupName);
		schedulerResponse.setJobName(jobName);
		schedulerResponse.setMessage(message);

		return schedulerResponse;
	}

	public static SchedulerResponse createSchedulerResponse(
		Trigger trigger, String description, String destinationName,
		Message message) {

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		schedulerResponse.setDescription(description);
		schedulerResponse.setDestinationName(destinationName);
		schedulerResponse.setMessage(message);
		schedulerResponse.setTrigger(trigger);

		return schedulerResponse;
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

	private SchedulerResponse() {
	}

	private String _description;
	private String _destinationName;
	private String _groupName;
	private String _jobName;
	private Message _message;
	private Trigger _trigger;

}