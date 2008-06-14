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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.util.JSONUtil;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * <a href="QuartzMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class QuartzMessageListener implements MessageListener {

	public void receive(String message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(String message) throws Exception {
		JSONObject jsonObj = new JSONObject(message);

		String responseDestination = jsonObj.optString(
			"lfrResponseDestination");
		String responseId = jsonObj.optString("lfrResponseId");

		jsonObj.remove("lfrResponseDestination");
		jsonObj.remove("lfrResponseId");

		SchedulerRequest schedulerRequest =
			(SchedulerRequest)JSONUtil.deserialize(jsonObj);

		String command = schedulerRequest.getCommand();

		if (command.equals(SchedulerRequest.COMMAND_REGISTER)) {
			QuartzSchedulerEngineUtil.schedule(
				String.valueOf(schedulerRequest.hashCode()),
				schedulerRequest.getGroupName(), schedulerRequest.getCronText(),
				schedulerRequest.getStartDate(), schedulerRequest.getEndDate(),
				schedulerRequest.getDescription(),
				schedulerRequest.getDestination(),
				schedulerRequest.getMessageBody());
		}
		else if (command.equals(SchedulerRequest.COMMAND_RETRIEVE)) {
			doCommandRetrieve(
				responseDestination, responseId, schedulerRequest);
		}
		else if (command.equals(SchedulerRequest.COMMAND_SHUTDOWN)) {
			QuartzSchedulerEngineUtil.shutdown();
		}
		else if (command.equals(SchedulerRequest.COMMAND_UNREGISTER)) {
			QuartzSchedulerEngineUtil.unschedule(
				schedulerRequest.getJobName(), schedulerRequest.getGroupName());
		}
	}

	protected void doCommandRetrieve(
			String responseDestination, String responseId,
			SchedulerRequest schedulerRequest)
		throws Exception {

		Collection<SchedulerRequest> schedulerRequests =
			QuartzSchedulerEngineUtil.getScheduledJobs(
				schedulerRequest.getGroupName());

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("lfrResponseId", responseId);
		jsonObj.put(
			"schedulerRequests", JSONUtil.serialize(schedulerRequests));

		MessageBusUtil.sendMessage(responseDestination, jsonObj.toString());
	}

	private static final Log _log =
		LogFactory.getLog(QuartzMessageListener.class);

}