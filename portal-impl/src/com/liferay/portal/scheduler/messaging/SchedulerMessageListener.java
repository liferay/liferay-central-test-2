/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.scheduler.messaging;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * <a href="SchedulerMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class SchedulerMessageListener
    implements MessageListener {

    public SchedulerMessageListener(SchedulerEngine engine) {
        _engine = engine;
    }

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
			_engine.schedule(
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
			_engine.shutdown();
		}
		else if (command.equals(SchedulerRequest.COMMAND_UNREGISTER)) {
			_engine.unschedule(
				schedulerRequest.getJobName(), schedulerRequest.getGroupName());
		}
	}

	protected void doCommandRetrieve(
			String responseDestination, String responseId,
			SchedulerRequest schedulerRequest)
		throws Exception {

		List<SchedulerRequest> schedulerRequests =
			_engine.getScheduledJobs(
				schedulerRequest.getGroupName());

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("lfrResponseId", responseId);
		jsonObj.put(
			"schedulerRequests", JSONFactoryUtil.serialize(schedulerRequests));

		MessageBusUtil.sendMessage(responseDestination, jsonObj.toString());
	}

	private static Log _log = LogFactory.getLog(SchedulerMessageListener.class);

    private SchedulerEngine _engine;
}
