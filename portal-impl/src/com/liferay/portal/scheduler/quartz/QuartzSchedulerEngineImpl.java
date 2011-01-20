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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.scheduler.job.MessageSenderJob;
import com.liferay.portal.service.QuartzLocalService;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Wesley Gong
 * @author Tina Tian
 */
public class QuartzSchedulerEngineImpl implements SchedulerEngine {

	public void afterPropertiesSet() {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			quartzLocalService.checkQuartzTables();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

			schedulerFactory.initialize(
				PropsUtil.getProperties("org.quartz.", false));

			_scheduler = schedulerFactory.getScheduler();

			initJobState();
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public void delete(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				delete(jobName, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to delete jobs", e);
		}
	}

	public void delete(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			SchedulerContext schedulerContext = _scheduler.getContext();

			schedulerContext.remove(getFullName(jobName, groupName));

			_scheduler.deleteJob(jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public void destroy() {
		try {
			shutdown();
		}
		catch (SchedulerException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to shutdown", se);
			}
		}
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			JobDetail jobDetail = _scheduler.getJobDetail(
				jobName, groupName);

			if (jobDetail == null) {
				return null;
			}

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			String description = jobDataMap.getString(DESCRIPTION);
			String destinationName = jobDataMap.getString(DESTINATION_NAME);
			Message message = getMessage(jobDataMap);

			SchedulerResponse schedulerResponse = null;

			Trigger trigger = _scheduler.getTrigger(jobName, groupName);

			handleJobState(jobName, groupName, message, trigger);

			if (trigger == null) {
				schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setGroupName(groupName);
				schedulerResponse.setJobName(jobName);
				schedulerResponse.setMessage(message);
			}
			else {
				if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {

					CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

					schedulerResponse = new SchedulerResponse();

					schedulerResponse.setDescription(description);
					schedulerResponse.setDestinationName(destinationName);
					schedulerResponse.setMessage(message);
					schedulerResponse.setTrigger(
						new com.liferay.portal.kernel.scheduler.CronTrigger(
							jobName, groupName, cronTrigger.getStartTime(),
							cronTrigger.getEndTime(),
							cronTrigger.getCronExpression()));
				}
				else if (SimpleTrigger.class.isAssignableFrom(
							trigger.getClass())) {

					SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(
						trigger);

					schedulerResponse = new SchedulerResponse();

					schedulerResponse.setDescription(description);
					schedulerResponse.setDestinationName(destinationName);
					schedulerResponse.setMessage(message);
					schedulerResponse.setTrigger(
						new IntervalTrigger(
							jobName, groupName, simpleTrigger.getStartTime(),
							simpleTrigger.getEndTime(),
							simpleTrigger.getRepeatInterval()));
				}
			}

			return schedulerResponse;
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get job", e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			String[] groupNames = _scheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(getScheduledJobs(groupName));
			}

			return schedulerResponses;
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				SchedulerResponse schedulerResponse = getScheduledJob(
					jobName, groupName);

				if (schedulerResponse != null) {
					schedulerResponses.add(schedulerResponse);
				}
			}

			return schedulerResponses;
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	public void pause(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				JobState jobState = getJobState(jobName, groupName);

				if (jobState != null) {
					jobState.setTriggerState(TriggerState.PAUSED);
				}
			}

			_scheduler.pauseJobGroup(groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to pause jobs", e);
		}
	}

	public void pause(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			JobState jobState = getJobState(jobName, groupName);

			if (jobState != null) {
				jobState.setTriggerState(TriggerState.PAUSED);
			}

			_scheduler.pauseJob(jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to pause job", e);
		}
	}

	public void resume(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				JobState jobState = getJobState(jobName, groupName);

				if (jobState != null) {
					jobState.setTriggerState(TriggerState.NORMAL);
				}
			}

			_scheduler.resumeJobGroup(groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to resume jobs", e);
		}
	}

	public void resume(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			JobState jobState = getJobState(jobName, groupName);

			if (jobState != null) {
				jobState.setTriggerState(TriggerState.NORMAL);
			}

			_scheduler.resumeJob(jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to resume job", e);
		}
	}

	public void schedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			String description, String destination, Message message)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Trigger quartzTrigger = getQuartzTrigger(trigger);

			if (quartzTrigger == null) {
				return;
			}

			description = fixArgumentMaxLength(
				description, DESCRIPTION_MAX_LENGTH);

			if (message == null){
				message = new Message();
			}

			message.put(RECEIVER_KEY, quartzTrigger.getFullJobName());

			if (isPermanent(destination, message)) {
				message.put(PERMANENT, true);
			}

			schedule(quartzTrigger, description, destination, message);
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}
		catch (Exception e) {
			throw new SchedulerException("Unable to schedule job", e);
		}
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (!_scheduler.isShutdown()) {
				_scheduler.shutdown(false);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_scheduler.start();

			cleanTemporaryJobs();
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			JobState jobState = getJobState(jobName, groupName);

			if (jobState != null) {
				jobState.clearExceptions();
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to suppress error for job", e);
		}
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		unschedule(trigger.getJobName(), trigger.getGroupName());
	}

	public void unschedule(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				unschedule(jobName, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to unschedule jobs", e);
		}
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		jobName = fixArgumentMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixArgumentMaxLength(groupName, GROUP_NAME_MAX_LENGTH);

		try {
			JobDetail jobDetail = _scheduler.getJobDetail(jobName, groupName);

			if (jobDetail == null) {
				return;
			}

			JobState jobState = getJobState(jobName, groupName);

			Trigger trigger = _scheduler.getTrigger(jobName, groupName);

			Date previousFireTime = trigger.getPreviousFireTime();

			jobState.setTriggerTimeInfomation(END_TIME, new Date());
			jobState.setTriggerTimeInfomation(
				FINAL_FIRE_TIME, previousFireTime);
			jobState.setTriggerTimeInfomation(NEXT_FIRE_TIME, null);
			jobState.setTriggerTimeInfomation(
				PREVIOUS_FIRE_TIME, previousFireTime);
			jobState.setTriggerTimeInfomation(
				START_TIME, trigger.getStartTime());

			jobState.setTriggerState(TriggerState.UNSCHEDULED);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			Message message = getMessage(jobDataMap);

			if (message.getBoolean(PERMANENT)) {
				JobState jobStateClone = (JobState)jobState.clone();

				jobStateClone.clearExceptions();

				jobDataMap.put(JOB_STATE, jobStateClone);
			}

			_scheduler.unscheduleJob(jobName, groupName);

			_scheduler.addJob(jobDetail, true);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}
	}

	public void update(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		Trigger quartzTrigger = getQuartzTrigger(trigger);

		if (quartzTrigger == null) {
			return;
		}

		String jobName = quartzTrigger.getJobName();
		String groupName = quartzTrigger.getGroup();

		try {
			if (_scheduler.getTrigger(jobName, groupName) != null) {
				_scheduler.rescheduleJob(jobName, groupName, quartzTrigger);
			}
			else {
				JobDetail jobDetail = _scheduler.getJobDetail(
					jobName, groupName);

				if (jobDetail == null) {
					return;
				}

				JobState jobState = getJobState(jobName, groupName);

				jobState.setTriggerState(TriggerState.NORMAL);

				synchronized (this) {
					_scheduler.deleteJob(jobName, groupName);
					_scheduler.scheduleJob(jobDetail, quartzTrigger);
				}
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update trigger for job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}
	}

	protected void cleanTemporaryJobs() throws Exception {
		String[] groupNames = _scheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				JobDetail jobDetail = _scheduler.getJobDetail(
					jobName, groupName);

				if (jobDetail == null) {
					continue;
				}

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				Message message = getMessage(jobDataMap);

				if (!message.getBoolean(PERMANENT)) {
					_scheduler.deleteJob(jobName, groupName);
				}
			}
		}
	}

	protected String fixArgumentMaxLength(String argument, int maxLength) {
		if (argument == null) {
			return null;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected JobState getJobState(String jobName, String groupName)
		throws Exception {

		SchedulerContext schedulerContext = _scheduler.getContext();

		JobState jobState = (JobState)schedulerContext.get(
			getFullName(jobName, groupName));

		return jobState;
	}

	protected Message getMessage(JobDataMap jobDataMap) {
		String messageJSON = (String)jobDataMap.get(MESSAGE);

		return (Message)JSONFactoryUtil.deserialize(messageJSON);
	}

	protected Trigger getQuartzTrigger(
			com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (trigger == null) {
			return null;
		}

		String jobName = fixArgumentMaxLength(
			trigger.getJobName(), JOB_NAME_MAX_LENGTH);
		String groupName = fixArgumentMaxLength(
			trigger.getGroupName(), GROUP_NAME_MAX_LENGTH);

		Trigger quartzTrigger = null;

		TriggerType triggerType = trigger.getTriggerType();

		if (triggerType.equals(TriggerType.CRON)) {
			try {
				quartzTrigger = new CronTrigger(
					jobName, groupName, (String)trigger.getTriggerContent());
			}
			catch (ParseException pe) {
				throw new SchedulerException(
					"Unable to parse cron text " + trigger.getTriggerContent());
			}
		}
		else if (triggerType.equals(TriggerType.SIMPLE)) {
			long interval = (Long)trigger.getTriggerContent();

			if (interval <= 0) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not scheduling " + trigger.getJobName() +
							" because interval is less than or equal to 0");
				}

				return null;
			}

			quartzTrigger = new SimpleTrigger(
				jobName, groupName, SimpleTrigger.REPEAT_INDEFINITELY,
				interval);
		}
		else {
			throw new SchedulerException(
				"Unknown trigger type " + trigger.getTriggerType());
		}

		quartzTrigger.setJobName(jobName);
		quartzTrigger.setJobGroup(groupName);

		Date startDate = trigger.getStartDate();

		if (startDate == null) {
			if (ServerDetector.getServerId().equals(ServerDetector.TOMCAT_ID)) {
				quartzTrigger.setStartTime(
					new Date(System.currentTimeMillis() + Time.MINUTE));
			}
			else {
				quartzTrigger.setStartTime(
					new Date(System.currentTimeMillis() + Time.MINUTE * 3));
			}
		}
		else {
			quartzTrigger.setStartTime(startDate);
		}

		Date endDate = trigger.getEndDate();

		if (endDate != null) {
			quartzTrigger.setEndTime(endDate);
		}

		return quartzTrigger;
	}

	protected void handleJobState(
			String jobName, String groupName, Message message, Trigger trigger)
		throws Exception {

		JobState jobState = getJobState(jobName, groupName);

		if (trigger != null) {
			message.put(END_TIME, trigger.getEndTime());
			message.put(FINAL_FIRE_TIME, trigger.getFinalFireTime());
			message.put(NEXT_FIRE_TIME, trigger.getNextFireTime());
			message.put(PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
			message.put(START_TIME, trigger.getStartTime());
		}
		else {
			TriggerState triggerState = jobState.getTriggerState();

			if (triggerState.equals(TriggerState.NORMAL)) {
				jobState.setTriggerState(TriggerState.COMPLETE);
			}
		}

		message.put(JOB_STATE, jobState.clone());
	}

	protected void initJobState() throws Exception {
		String[] groupNames = _scheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				initJobState(jobName, groupName);
			}
		}
	}

	protected void initJobState(String jobName, String groupName)
		throws Exception {

		JobDetail jobDetail = _scheduler.getJobDetail(jobName, groupName);

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		Message message = getMessage(jobDataMap);

		if (!message.getBoolean(PERMANENT)) {
			return;
		}

		Trigger trigger = _scheduler.getTrigger(jobName, groupName);

		JobState jobState = null;

		if (trigger != null) {
			jobState = new JobState(
				TriggerState.NORMAL, message.getInteger(EXCEPTIONS_MAX_SIZE));

			jobState.setTriggerTimeInfomation(
				SchedulerEngine.END_TIME, trigger.getEndTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.FINAL_FIRE_TIME, trigger.getFinalFireTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.NEXT_FIRE_TIME, null);
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.PREVIOUS_FIRE_TIME,
				trigger.getPreviousFireTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.START_TIME, trigger.getStartTime());

			jobDataMap.put(JOB_STATE, jobState);

			_scheduler.addJob(jobDetail, true);
		}
		else {
			jobState = (JobState)jobDataMap.get(JOB_STATE);

			if (jobState == null) {
				throw new Exception(
					"Unable to get trigger details of job {jobName=" +
						jobName + ", groupName=" + groupName + "}");
			}
		}

		SchedulerContext schedulerContext = _scheduler.getContext();

		schedulerContext.put(jobDetail.getFullName(), jobState);
	}

	protected boolean isPermanent(String destinationName, Message message) {
		if (destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			return false;
		}
		else if (destinationName.equals(DestinationNames.SCHEDULER_SCRIPTING) &&
				 !message.getBoolean(SchedulerEngine.PERMANENT)) {

			return false;
		}
		else {
			return true;
		}
	}

	protected void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws Exception {

		try {
			String jobName = trigger.getName();
			String groupName = trigger.getGroup();

			JobDetail jobDetail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			jobDetail.setDurability(true);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION_NAME, destinationName);
			jobDataMap.put(MESSAGE, JSONFactoryUtil.serialize(message));

			JobState jobState = new JobState(
				TriggerState.NORMAL, message.getInteger(EXCEPTIONS_MAX_SIZE));

			SchedulerContext schedulerContext = _scheduler.getContext();

			schedulerContext.put(jobDetail.getFullName(), jobState);

			synchronized (this) {
				_scheduler.deleteJob(jobName, groupName);
				_scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService")
	protected QuartzLocalService quartzLocalService;

	private static Log _log = LogFactoryUtil.getLog(
		QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

}