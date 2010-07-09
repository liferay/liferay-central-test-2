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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
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
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Wesley Gong
 */
public class QuartzSchedulerEngineImpl implements SchedulerEngine {

	public void afterPropertiesSet() {
		try {
			if (!PropsValues.SCHEDULER_ENABLED) {
				return;
			}

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

			schedulerFactory.initialize(
				PropsUtil.getProperties("org.quartz.", false));

			quartzLocalService.checkQuartzTables();

			_scheduler = schedulerFactory.getScheduler();
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return new ArrayList<SchedulerRequest>();
		}

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			List<SchedulerRequest> requests = new ArrayList<SchedulerRequest>();

			for (String jobName : jobNames) {
				JobDetail jobDetail = _scheduler.getJobDetail(
					jobName, groupName);

				if (jobDetail == null) {
					continue;
				}

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				String description = jobDataMap.getString(DESCRIPTION);
				Message message = (Message)jobDataMap.get(MESSAGE);

				SchedulerRequest schedulerRequest = null;

				Trigger trigger = _scheduler.getTrigger(jobName, groupName);

				if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {
					CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							new com.liferay.portal.kernel.scheduler.CronTrigger(
								jobName, groupName, cronTrigger.getStartTime(),
								cronTrigger.getEndTime(),
								cronTrigger.getCronExpression()),
							description, message);
				}
				else if (SimpleTrigger.class.isAssignableFrom(
							trigger.getClass())) {

					SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(
						trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							new IntervalTrigger(
								jobName, groupName,
								simpleTrigger.getStartTime(),
								simpleTrigger.getEndTime(),
								simpleTrigger.getRepeatInterval()), description,
								message);
				}

				if (schedulerRequest != null) {
					requests.add(schedulerRequest);
				}
			}

			return requests;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to retrieve job", se);
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
			String jobName = trigger.getJobName();
			String groupName = trigger.getGroupName();

			if (jobName.length() > JOB_NAME_MAX_LENGTH) {
				jobName = jobName.substring(0, JOB_NAME_MAX_LENGTH);
			}

			if (groupName.length() > GROUP_NAME_MAX_LENGTH) {
				groupName = groupName.substring(0, GROUP_NAME_MAX_LENGTH);
			}

			Trigger quartzTrigger = null;

			if (trigger.getTriggerType() == TriggerType.CRON) {
				try {
					quartzTrigger = new CronTrigger(
						jobName, groupName,
						(String)trigger.getTriggerContent());
				}
				catch (ParseException pe) {
					throw new SchedulerException(
						"Unable to parse cron text " +
							trigger.getTriggerContent());
				}
			}
			else if (trigger.getTriggerType() == TriggerType.SIMPLE) {
				long interval = (Long)trigger.getTriggerContent();

				if (interval <= 0) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Not scheduling " + trigger.getJobName() +
								" because interval is less than or equal to 0");
					}

					return;
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
				if (ServerDetector.getServerId().equals(
						ServerDetector.TOMCAT_ID)) {

					quartzTrigger.setStartTime(
						new Date(System.currentTimeMillis() + Time.MINUTE));
				}
				else {
					quartzTrigger.setStartTime(
						new Date(
						System.currentTimeMillis() + Time.MINUTE * 3));
				}
			}
			else {
				quartzTrigger.setStartTime(startDate);
			}

			Date endDate = trigger.getEndDate();

			if (endDate != null) {
				quartzTrigger.setEndTime(endDate);
			}

			if ((description != null) &&
				(description.length() > DESCRIPTION_MAX_LENGTH)) {

				description = description.substring(0, DESCRIPTION_MAX_LENGTH);
			}

			if (message == null){
				message = new Message();
			}

			message.put(
				RECEIVER_KEY,
				jobName.concat(StringPool.COLON).concat(groupName));

			schedule(quartzTrigger, description, destination, message);
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_scheduler.shutdown(false);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to shutdown scheduler", se);
		}
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_scheduler.start();
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to start scheduler", se);
		}
	}

	public void unschedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		String jobName = trigger.getJobName();
		String groupName = trigger.getGroupName();

		try {
			_scheduler.unscheduleJob(jobName, groupName);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				se);
		}
	}

	protected void schedule(
			Trigger trigger, String description,
			String destination, Message message)
		throws SchedulerException {

		try {
			String jobName = trigger.getName();
			String groupName = trigger.getGroup();

			JobDetail jobDetail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION, destination);
			jobDataMap.put(MESSAGE, message);

			synchronized (this) {
				_scheduler.unscheduleJob(jobName, groupName);
				_scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to scheduled job", se);
		}
	}

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService")
	protected QuartzLocalService quartzLocalService;

	private Log _log = LogFactoryUtil.getLog(QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

}