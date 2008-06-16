/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portlet.communities.messaging.LayoutsPublisherMessageListener;
import com.liferay.util.JSONUtil;

import java.util.Collection;
import java.util.Date;

import org.json.JSONObject;

/**
 * <a href="SchedulerEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SchedulerEngineImpl implements SchedulerEngine {

	public SchedulerEngineImpl() {
		// Layouts publisher

		Destination destination = new SerialDestination(
			DestinationNames.LAYOUTS_PUBLISHER);

		MessageBusUtil.addDestination(destination);

		destination.register(new LayoutsPublisherMessageListener());
	}

	public Collection<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			SchedulerRequest schedulerRequest = new SchedulerRequest(
				SchedulerRequest.COMMAND_RETRIEVE, null, groupName, null, null,
				null, null, null, null);

			String message = MessageBusUtil.sendSynchronizedMessage(
				DestinationNames.SCHEDULER,
				JSONUtil.serialize(schedulerRequest));

			JSONObject jsonObj = new JSONObject(message);

			return (Collection<SchedulerRequest>)JSONUtil.deserialize(
				jsonObj.getString("schedulerRequests"));
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	public void schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		SchedulerRequest schedulerRequest = new SchedulerRequest(
			SchedulerRequest.COMMAND_REGISTER, null, groupName, cronText,
			startDate, endDate, description, destinationName, messageBody);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER, JSONUtil.serialize(schedulerRequest));
	}

	public void shutdown() throws SchedulerException {
		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER,
			JSONUtil.serialize(
				new SchedulerRequest(SchedulerRequest.COMMAND_SHUTDOWN)));
	}

	public void start() throws SchedulerException {
	}

	public void unschedule(String jobName, String groupName)
			throws SchedulerException {

		SchedulerRequest schedulerRequest = new SchedulerRequest(
			SchedulerRequest.COMMAND_UNREGISTER, jobName, groupName);

		MessageBusUtil.sendMessage(
			DestinationNames.SCHEDULER, JSONUtil.serialize(schedulerRequest));
	}

}