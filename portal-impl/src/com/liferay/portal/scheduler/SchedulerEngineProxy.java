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

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.Date;
import java.util.List;

/**
 * <a href="SchedulerEngineProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SchedulerEngineProxy implements SchedulerEngine {

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			SchedulerRequest schedulerRequest =
				SchedulerRequest.createRetrieveRequest(groupName);

			List<SchedulerRequest> schedulerRequests =
				(List<SchedulerRequest>)MessageBusUtil.sendSynchronousMessage(
					DestinationNames.SCHEDULER, schedulerRequest,
					DestinationNames.SCHEDULER_RESPONSE);

			return schedulerRequests;
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	public void schedule(
		String groupName, String cronText, Date startDate, Date endDate,
		String description, String destinationName, String messageBody) {

		SchedulerRequest schedulerRequest =
			SchedulerRequest.createRegisterRequest(
				groupName, cronText, startDate, endDate, description,
				destinationName, messageBody);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER, schedulerRequest);
	}

	public void schedule(
		String groupName, long interval, Date startDate, Date endDate,
		String description, String destinationName, String messageBody) {

		SchedulerRequest schedulerRequest =
			SchedulerRequest.createRegisterRequest(
				groupName, interval, startDate, endDate, description,
				destinationName, messageBody);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER, schedulerRequest);
	}

	public void shutdown() {
		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER,
			SchedulerRequest.createShutdownRequest());
	}

	public void start() {
		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER,
			SchedulerRequest.createStartupRequest());
	}

	public void unschedule(String jobName, String groupName) {
		SchedulerRequest schedulerRequest =
			SchedulerRequest.createUnregisterRequest(groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER, schedulerRequest);
	}

}