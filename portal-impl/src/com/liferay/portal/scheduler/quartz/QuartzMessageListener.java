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
import com.liferay.portal.kernel.scheduler.SchedulerRequest;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jabsorb.JSONSerializer;

import org.json.JSONObject;

/**
 * <a href="QuartzMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class QuartzMessageListener implements MessageListener {

	private static JSONSerializer _serializer = new JSONSerializer();

	 static {
		 try {
			 _serializer.registerDefaultSerializers();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	}

	public void receive(String message) {
		try {
			JSONObject jsonObj = new JSONObject(message);

			String responseDestination = jsonObj.optString(
				"lfrResponseDestination");
			String responseId = jsonObj.optString("lfrResponseId");

			jsonObj.remove("lfrResponseDestination");
			jsonObj.remove("lfrResponseId");

			SchedulerRequest request =
				(SchedulerRequest)_serializer.fromJSON(jsonObj.toString());

			String type = request.getType();

			if (type.equals(SchedulerRequest.REGISTER_TYPE)) {
				QuartzSchedulerEngineUtil.schedule(
					String.valueOf(request.hashCode()), request.getGroupName(),
					request.getCronText(), request.getDestinationName(),
					request.getMessageBody(), request.getStartDate(),
					request.getEndDate(), request.getDescription());
			}
			else if (type.equals(SchedulerRequest.RETRIEVE_TYPE)) {
				Collection<SchedulerRequest> requests =
					QuartzSchedulerEngineUtil.retrieveScheduledJobs(
						request.getGroupName());

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("lfrResponseId", responseId);
				jsonObject.put("requests", _serializer.toJSON(requests));

				MessageBusUtil.sendMessage(
					responseDestination, jsonObject.toString());
			}
			else if (type.equals(SchedulerRequest.SHUTDOWN_TYPE)) {
				QuartzSchedulerEngineUtil.shutdown();
			}
			else if (type.equals(SchedulerRequest.UNREGISTER_TYPE)) {
				QuartzSchedulerEngineUtil.unschedule(
					request.getJobName(), request.getGroupName());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log =
		LogFactory.getLog(QuartzMessageListener.class);

}