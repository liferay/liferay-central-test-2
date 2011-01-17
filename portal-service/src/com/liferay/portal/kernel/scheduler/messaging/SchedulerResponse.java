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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.Trigger;

import java.io.Serializable;

/**
 *
 * @author Tina Tian
 */
public class SchedulerResponse implements Serializable {

	public static SchedulerResponse createRetrieveResponse() {
		return new SchedulerResponse();
	}

	public static SchedulerResponse createRetrieveResponse(
		String jobName, String groupName, String description,
		String destinationName, Message message) {

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		schedulerResponse._description = description;
		schedulerResponse._destinationName = destinationName;
		schedulerResponse._groupName = groupName;
		schedulerResponse._jobName = jobName;
		schedulerResponse._message = message;

		return schedulerResponse;
	}

	public static SchedulerResponse createRetrieveResponse(
		Trigger trigger, String description, String destinationName,
		Message message) {

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		schedulerResponse._description = description;
		schedulerResponse._destinationName = destinationName;
		schedulerResponse._message = message;
		schedulerResponse._trigger = trigger;

		return schedulerResponse;
	}

	public String getDescription() {
		return _description;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public String getGroupName() {
		if (_trigger != null) {
			return _trigger.getGroupName();
		}
		
		return _groupName;
	}

	public String getJobName() {
		if (_trigger != null) {
			return _trigger.getJobName();
		}
		
		return _jobName;
	}

	public Message getMessage() {
		return _message;
	}

	public Trigger getTrigger() {
		return _trigger;
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