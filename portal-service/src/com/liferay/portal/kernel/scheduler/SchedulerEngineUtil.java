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

		_instance._addScriptingJob(trigger, description, language, script);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent)
		throws SchedulerException {

		_instance._addScriptingJob(
			trigger, description, language, script, permanent);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._addScriptingJob(
			trigger, description, language, script, permanent,
			exceptionsMaxSize);
	}

	public static void addScriptingJob(
			Trigger trigger, String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		_instance._addScriptingJob(
			trigger, description, language, script, exceptionsMaxSize);
	}

	public static void delete(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		_instance._delete(schedulerEntry);
	}

	public static void delete(String groupName) throws SchedulerException {
		_instance._delete(groupName);
	}

	public static void delete(String jobName, String groupName)
		throws SchedulerException {

		_instance._delete(jobName, groupName);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		return _instance._getEndTime(schedulerResponse);
	}

	public static Date getEndTime(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getEndTime(jobName, groupName);
	}

	public static Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		return _instance._getFinalFireTime(schedulerResponse);
	}

	public static Date getFinalFireTime(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getFinalFireTime(jobName, groupName);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		return _instance._getJobExceptions(schedulerResponse);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName)
		throws SchedulerException {

		return _instance._getJobExceptions(jobName, groupName);
	}

	public static TriggerState getJobState(
		SchedulerResponse schedulerResponse) {

		return _instance._getJobState(schedulerResponse);
	}

	public static TriggerState getJobState(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getJobState(jobName, groupName);
	}

	public static Date getNextFireTime(SchedulerResponse schedulerResponse) {
		return _instance._getNextFireTime(schedulerResponse);
	}

	public static Date getNextFireTime(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getNextFireTime(jobName, groupName);
	}

	public static Date getPreviousFireTime(
		SchedulerResponse schedulerResponse) {

		return _instance._getPreviousFireTime(schedulerResponse);
	}

	public static Date getPreviousFireTime(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getPreviousFireTime(jobName, groupName);
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName)
		throws SchedulerException {

		return _instance._getScheduledJob(jobName, groupName);
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _instance._getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _instance._getScheduledJobs(groupName);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		return _instance._getStartTime(schedulerResponse);
	}

	public static Date getStartTime(String jobName, String groupName)
		throws SchedulerException {

		return _instance._getStartTime(jobName, groupName);
	}

	public static void pause(String groupName) throws SchedulerException {
		_instance._pause(groupName);
	}

	public static void pause(String jobName, String groupName)
		throws SchedulerException {

		_instance._pause(jobName, groupName);
	}

	public static void resume(String groupName) throws SchedulerException {
		_instance._resume(groupName);
	}

	public static void resume(String jobName, String groupName)
		throws SchedulerException {

		_instance._resume(jobName, groupName);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		_instance._schedule(schedulerEntry, classLoader);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader,
			int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(schedulerEntry, classLoader, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_instance._schedule(trigger, description, destinationName, message);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Message message, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(
			trigger, description, destinationName, message, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Object payload)
		throws SchedulerException {

		_instance._schedule(trigger, description, destinationName, payload);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(
			trigger, description, destinationName, payload, exceptionsMaxSize);
	}

	public static void shutdown() throws SchedulerException {
		_instance._shutdown();
	}

	public static void start() throws SchedulerException {
		_instance._start();
	}

	public static void suppressError(String jobName, String groupName)
		throws SchedulerException {

		_instance._suppressError(jobName, groupName);
	}

	public static void unschedule(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		_instance._unschedule(schedulerEntry);
	}

	public static void unschedule(String groupName) throws SchedulerException {

		_instance._unschedule(groupName);
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_instance._unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public static void unschedule(Trigger trigger) throws SchedulerException {
		_instance._unschedule(trigger);
	}

	public static void update(
			String jobName, String groupName, String description,
			String language, String script)
		throws SchedulerException {

		_instance._update(jobName, groupName, description, language, script);
	}

	public static void update(
			String jobName, String groupName, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._update(
			jobName, groupName, description, language, script,
			exceptionsMaxSize);
	}

	public static void update(Trigger trigger) throws SchedulerException {
		_instance._update(trigger);
	}

	public static void updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		_instance._updateMemorySchedulerClusterMaster();
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	public void setSchedulerEngineClusterManager(
		SchedulerEngineClusterManager schedulerEngineClusterManager) {

		_schedulerEngineClusterManager = schedulerEngineClusterManager;
	}

	private void _addScriptingJob(
			Trigger trigger, String description, String language, String script)
		throws SchedulerException {

		_addScriptingJob(trigger, description, language, script, 0);
	}

	private void _addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent)
		throws SchedulerException {

		_addScriptingJob(trigger, description, language, script, permanent, 0);
	}

	private void _addScriptingJob(
			Trigger trigger, String description, String language, String script,
			boolean permanent, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.PERMANENT, permanent);
		message.put(SchedulerEngine.SCRIPT, script);

		_schedule(
			trigger, description, DestinationNames.SCHEDULER_SCRIPTING,
			message, exceptionsMaxSize);
	}

	private void _addScriptingJob(
			Trigger trigger, String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.SCRIPT, script);

		_schedule(
			trigger, description, DestinationNames.SCHEDULER_SCRIPTING,
			message, exceptionsMaxSize);
	}

	private void _delete(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		MessageListener schedulerEventListener =
			schedulerEntry.getEventListener();

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Trigger trigger = schedulerEntry.getTrigger();

		_schedulerEngine.delete(trigger.getJobName(), trigger.getGroupName());
	}

	private void _delete(String groupName) throws SchedulerException {
		_unregisterMessageListener(groupName);

		_schedulerEngine.delete(groupName);
	}

	private void _delete(String jobName, String groupName)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName);

		_schedulerEngine.delete(jobName, groupName);
	}

	private Date _getEndTime(SchedulerResponse schedulerResponse) {
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

	private Date _getEndTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getEndTime(schedulerResponse);
		}

		return null;
	}

	private Date _getFinalFireTime(SchedulerResponse schedulerResponse) {
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

	private Date _getFinalFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getFinalFireTime(schedulerResponse);
		}

		return null;
	}

	private ObjectValuePair<Exception, Date>[] _getJobExceptions(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getExceptions();
	}

	private ObjectValuePair<Exception, Date>[] _getJobExceptions(
			String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getJobExceptions(schedulerResponse);
		}

		return null;
	}

	private TriggerState _getJobState(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getTriggerState();
	}

	private TriggerState _getJobState(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getJobState(schedulerResponse);
		}

		return null;
	}

	private Date _getNextFireTime(SchedulerResponse schedulerResponse) {
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

	private Date _getNextFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getNextFireTime(schedulerResponse);
		}

		return null;
	}

	private Date _getPreviousFireTime(SchedulerResponse schedulerResponse) {
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

	private Date _getPreviousFireTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getPreviousFireTime(schedulerResponse);
		}

		return null;
	}

	private SchedulerResponse _getScheduledJob(String jobName, String groupName)
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

	private List<SchedulerResponse> _getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	private List<SchedulerResponse> _getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	private MessageListener _getSchedulerEventListener(
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

	private Date _getStartTime(SchedulerResponse schedulerResponse) {
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

	private Date _getStartTime(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		if (schedulerResponse != null) {
			return _getStartTime(schedulerResponse);
		}

		return null;
	}

	private void _pause(String groupName) throws SchedulerException {
		_schedulerEngine.pause(groupName);
	}

	private void _pause(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.pause(jobName, groupName);
	}

	private void _resume(String groupName) throws SchedulerException {
		_schedulerEngine.resume(groupName);
	}

	private void _resume(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.resume(jobName, groupName);
	}

	private void _schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		_schedule(schedulerEntry, classLoader, 0);
	}

	private void _schedule(
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

		_schedule(
			schedulerEntry.getTrigger(), schedulerEntry.getDescription(),
			DestinationNames.SCHEDULER_DISPATCH, message, exceptionsMaxSize);
	}

	private void _schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	private void _schedule(
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

	private void _schedule(
			Trigger trigger, String description, String destinationName,
			Object payload)
		throws SchedulerException {

		_schedule(trigger, description, destinationName, payload, 0);
	}

	private void _schedule(
			Trigger trigger, String description, String destinationName,
			Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		_schedule(
			trigger, description, destinationName, message, exceptionsMaxSize);
	}

	private void _shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	private void _start() throws SchedulerException {
		_schedulerEngine.start();
	}

	private void _suppressError(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName);
	}

	private void _unregisterMessageListener(
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

	private void _unregisterMessageListener(String groupName)
		throws SchedulerException {

		List<SchedulerResponse> schedulerResponses = _getScheduledJobs(
			groupName);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_unregisterMessageListener(schedulerResponse);
		}
	}

	private void _unregisterMessageListener(String jobName, String groupName)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName);

		_unregisterMessageListener(schedulerResponse);
	}

	private void _unschedule(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		MessageListener schedulerEventListener =
			schedulerEntry.getEventListener();

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Trigger trigger = schedulerEntry.getTrigger();

		_schedulerEngine.unschedule(
			trigger.getJobName(), trigger.getGroupName());
	}

	private void _unschedule(String groupName) throws SchedulerException {
		_unregisterMessageListener(groupName);

		_schedulerEngine.unschedule(groupName);
	}

	private void _unschedule(String jobName, String groupName)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName);

		_schedulerEngine.unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #_unschedule(String, String)}
	 */
	private void _unschedule(Trigger trigger) throws SchedulerException {
		_schedulerEngine.unschedule(trigger);
	}

	private void _update(
			String jobName, String groupName, String description,
			String language, String script)
		throws SchedulerException {

		_update(jobName, groupName, description, language, script, 0);
	}

	private void _update(
			String jobName, String groupName, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
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

		_addScriptingJob(
			trigger, description, language, script, permanent,
			exceptionsMaxSize);
	}

	private void _update(Trigger trigger) throws SchedulerException {
		_schedulerEngine.update(trigger);
	}

	private void _updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		_schedulerEngineClusterManager.updateMemorySchedulerClusterMaster();
	}

	private static SchedulerEngineUtil _instance = new SchedulerEngineUtil();

	private static SchedulerEngine _schedulerEngine;
	private static SchedulerEngineClusterManager _schedulerEngineClusterManager;

}