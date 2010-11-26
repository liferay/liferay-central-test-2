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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class SchedulerEngineUtil {

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script)
		throws SchedulerException {

		addScriptingJob(trigger, description, language, script, 0);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.SCRIPT, script);

		schedule(
			trigger, description, DestinationNames.SCHEDULER_SCRIPTING,
			message, exceptionsMaxSize);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent)
		throws SchedulerException {

		addScriptingJob(trigger, description, language, script, permanent, 0);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.PERMANENT, permanent);
		message.put(SchedulerEngine.SCRIPT, script);

		schedule(
			trigger, description, DestinationNames.SCHEDULER_SCRIPTING,
			message, exceptionsMaxSize);
	}

	public static void delete(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		MessageListener schedulerEventListener =
			schedulerEntry.getEventListener();

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Trigger trigger = schedulerEntry.getTrigger();

		_schedulerEngine.delete(trigger.getJobName(), trigger.getGroupName());
	}

	public static void delete(String groupName) throws SchedulerException {
		_unregisterMessageListener(groupName);

		_schedulerEngine.delete(groupName);
	}

	public static void delete(String jobName, String groupName)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName);

		_schedulerEngine.delete(jobName, groupName);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.END_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(SchedulerEngine.END_TIME);
		}
	}

	public static Date getEndTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getEndTime(schedulerResponse);
		}

		return null;
	}

	public static Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.FINAL_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.FINAL_FIRE_TIME);
		}
	}

	public static Date getFinalFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getFinalFireTime(schedulerResponse);
		}

		return null;
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getExceptions();
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getJobExceptions(schedulerResponse);
		}

		return null;
	}

	public static TriggerState getJobState(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getTriggerState();
	}

	public static TriggerState getJobState(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getJobState(schedulerResponse);
		}

		return null;
	}

	public static Date getNextFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.NEXT_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.NEXT_FIRE_TIME);
		}
	}

	public static Date getNextFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getNextFireTime(schedulerResponse);
		}

		return null;
	}

	public static Date getPreviousFireTime(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
	}

	public static Date getPreviousFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getPreviousFireTime(schedulerResponse);
		}

		return null;
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _schedulerEngine.getScheduledJob(
			jobName, groupName);

		if ((schedulerResponse.getGroupName() == null) &&
			(schedulerResponse.getJobName() == null) &&
			(schedulerResponse.getTrigger() == null)) {

			return null;
		}

		return schedulerResponse;
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.START_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.START_TIME);
		}
	}

	public static Date getStartTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return getStartTime(schedulerResponse);
		}

		return null;
	}

	public static void pause(String groupName) throws SchedulerException {
		_schedulerEngine.pause(groupName);
	}

	public static void pause(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.pause(jobName, groupName);
	}

	public static void resume(String groupName) throws SchedulerException {
		_schedulerEngine.resume(groupName);
	}

	public static void resume(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.resume(jobName, groupName);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		schedule(schedulerEntry, classLoader, 0);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader,
			int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerEventMessageListenerWrapper schedulerEventListenerWrapper =
			new SchedulerEventMessageListenerWrapper();

		schedulerEventListenerWrapper.setClassName(
			schedulerEntry.getEventListenerClass());
		schedulerEventListenerWrapper.setMessageListener(
			_getSchedulerEventListener(schedulerEntry, classLoader));

		schedulerEventListenerWrapper.afterPropertiesSet();

		schedulerEntry.setEventListener(schedulerEventListenerWrapper);

		MessageBusUtil.registerMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListenerWrapper);

		Message message = new Message();

		message.put(
			SchedulerEngine.MESSAGE_LISTENER_UUID,
			schedulerEventListenerWrapper.getMessageListenerUUID());

		schedule(
			schedulerEntry.getTrigger(), schedulerEntry.getDescription(),
			DestinationNames.SCHEDULER_DISPATCH, message, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Message message, int exceptionsMaxSize)
		throws SchedulerException {

		if (message == null) {
			message = new Message();
		}

		message.put(SchedulerEngine.EXCEPTIONS_MAX_SIZE, exceptionsMaxSize);

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Object payload)
		throws SchedulerException {

		schedule(trigger, description, destinationName, payload, 0);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		schedule(
			trigger, description, destinationName, message, exceptionsMaxSize);
	}

	public static void shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	public static void start() throws SchedulerException {
		_schedulerEngine.start();
	}

	public static void suppressError(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName);
	}

	public static void unschedule(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		MessageListener schedulerEventListener =
			schedulerEntry.getEventListener();

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Trigger trigger = schedulerEntry.getTrigger();

		_schedulerEngine.unschedule(
			trigger.getJobName(), trigger.getGroupName());
	}

	public static void unschedule(String groupName) throws SchedulerException {
		_unregisterMessageListener(groupName);

		_schedulerEngine.unschedule(groupName);
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName);

		_schedulerEngine.unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public static void unschedule(Trigger trigger) throws SchedulerException {
		_schedulerEngine.unschedule(trigger);
	}

	public static void update(
			String jobName, String groupName, String description,
			String language, String script)
		throws SchedulerException {

		update(jobName, groupName, description, language, script, 0);
	}

	public static void update(
			String jobName, String groupName, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		if (schedulerResponse == null) {
			return;
		}

		Trigger trigger = schedulerResponse.getTrigger();

		if (trigger == null) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		if (message == null) {
			return;
		}

		boolean permanent = message.getBoolean(SchedulerEngine.PERMANENT);

		_unregisterMessageListener(schedulerResponse);

		addScriptingJob(
			trigger, description, language, script, permanent,
			exceptionsMaxSize);
	}

	public static void update(Trigger trigger) throws SchedulerException {
		_schedulerEngine.update(trigger);
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	private static MessageListener _getSchedulerEventListener(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		try {
			MessageListener schedulerEventListener =
				(MessageListener)classLoader.loadClass(
					schedulerEntry.getEventListenerClass()).newInstance();

			return (MessageListener)Proxy.newProxyInstance(
				classLoader, new Class[] {MessageListener.class},
				new ClassLoaderBeanHandler(
					schedulerEventListener, classLoader));
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	private static void _unregisterMessageListener(
		SchedulerResponse schedulerResponse) {

		if (schedulerResponse == null) {
			return;
		}

		String destinationName = schedulerResponse.getDestinationName();

		if (!destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		String messageListenerUUID = message.getString(
			SchedulerEngine.MESSAGE_LISTENER_UUID);

		if (messageListenerUUID == null) {
			return;
		}

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(
			DestinationNames.SCHEDULER_DISPATCH);

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		for (MessageListener messageListener : messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			SchedulerEventMessageListenerWrapper schedulerMessageListener =
				(SchedulerEventMessageListenerWrapper)
					invokerMessageListener.getMessageListener();

			if (messageListenerUUID.equals(
					schedulerMessageListener.getMessageListenerUUID())) {

				MessageBusUtil.unregisterMessageListener(
					DestinationNames.SCHEDULER_DISPATCH,
					schedulerMessageListener);

				return;
			}
		}
	}

	private static void _unregisterMessageListener(String groupName)
		throws SchedulerException {

		List<SchedulerResponse> schedulerResponses = getScheduledJobs(
			groupName);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_unregisterMessageListener(schedulerResponse);
		}
	}

	private static void _unregisterMessageListener(
			String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName);

		_unregisterMessageListener(schedulerResponse);
	}

	private static SchedulerEngine _schedulerEngine;

}