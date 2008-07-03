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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="QuartzMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class QuartzMessageListener implements MessageListener {

	public void receive(Object message) {
		throw new UnsupportedOperationException();
	}

	public void receive(String message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(String message) throws Exception {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		String responseDestination = jsonObj.getString(
			"lfrResponseDestination");
		String responseId = jsonObj.getString("lfrResponseId");

		jsonObj.remove("lfrResponseDestination");
		jsonObj.remove("lfrResponseId");

		SchedulerRequest schedulerRequest =
			(SchedulerRequest)JSONFactoryUtil.deserialize(jsonObj);

		String command = schedulerRequest.getCommand();

		if (command.equals(SchedulerRequest.COMMAND_REGISTER)) {
			QuartzSchedulerEngineUtil.schedule(
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

		List<SchedulerRequest> schedulerRequests =
			QuartzSchedulerEngineUtil.getScheduledJobs(
				schedulerRequest.getGroupName());

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("lfrResponseId", responseId);
		jsonObj.put(
			"schedulerRequests", JSONFactoryUtil.serialize(schedulerRequests));

		MessageBusUtil.sendMessage(responseDestination, jsonObj.toString());
	}

	private static Log _log = LogFactory.getLog(QuartzMessageListener.class);

}