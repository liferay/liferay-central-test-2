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

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.List;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class SchedulerEngineProxy implements SchedulerEngine {

	public void delete(String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createDeleteRequest(groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void delete(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createDeleteRequest(jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public SchedulerRequest getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		try {
			SchedulerRequest schedulerRequest =
				SchedulerRequest.createRetrieveRequest(jobName, groupName);

			SchedulerRequest schedulerResponse =
				(SchedulerRequest)MessageBusUtil.sendSynchronousMessage(
					DestinationNames.SCHEDULER_ENGINE, schedulerRequest,
					DestinationNames.SCHEDULER_ENGINE_RESPONSE);

			return schedulerResponse;
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	public List<SchedulerRequest> getScheduledJobs()
		throws SchedulerException {

		try {
			SchedulerRequest schedulerRequest =
				SchedulerRequest.createRetrieveRequest();

			List<SchedulerRequest> schedulerRequests =
				(List<SchedulerRequest>)MessageBusUtil.sendSynchronousMessage(
					DestinationNames.SCHEDULER_ENGINE, schedulerRequest,
					DestinationNames.SCHEDULER_ENGINE_RESPONSE);

			return schedulerRequests;
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			SchedulerRequest schedulerRequest =
				SchedulerRequest.createRetrieveRequest(groupName);

			List<SchedulerRequest> schedulerRequests =
				(List<SchedulerRequest>)MessageBusUtil.sendSynchronousMessage(
					DestinationNames.SCHEDULER_ENGINE, schedulerRequest,
					DestinationNames.SCHEDULER_ENGINE_RESPONSE);

			return schedulerRequests;
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	public void pause(String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createPauseRequest(groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void pause(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createPauseRequest(jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void resume(String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createResumeRequest(groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void resume(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createResumeRequest(jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void schedule(
		Trigger trigger, String description, String destinationName,
		Message message) {

		SchedulerRequest schedulerRequest =
			SchedulerRequest.createRegisterRequest(
				trigger, description, destinationName, message);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void shutdown() {
		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE,
			SchedulerRequest.createShutdownRequest());
	}

	public void start() {
		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE,
			SchedulerRequest.createStartupRequest());
	}

	public void suppressError(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createSuppressErrorRequest(jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void unschedule(String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createUnregisterRequest(groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	public void unschedule(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createUnregisterRequest(jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(Trigger trigger) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createUnregisterRequest(trigger);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER_ENGINE, schedulerRequest);
	}

}