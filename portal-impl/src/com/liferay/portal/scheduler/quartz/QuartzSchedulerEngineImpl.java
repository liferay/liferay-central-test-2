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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.ServerDetector;
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
 * <a href="QuartzSchedulerEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
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

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				String description = jobDataMap.getString(DESCRIPTION);
				String messageBody = jobDataMap.getString(MESSAGE_BODY);

				SchedulerRequest schedulerRequest = null;

				Trigger trigger = _scheduler.getTrigger(jobName, groupName);

				if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {
					CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							jobName, groupName, cronTrigger.getCronExpression(),
							cronTrigger.getStartTime(),
							cronTrigger.getEndTime(), description, messageBody);
				}
				else if (SimpleTrigger.class.isAssignableFrom(
							trigger.getClass())) {

					SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(
						trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							jobName, groupName,
							simpleTrigger.getRepeatInterval(),
							simpleTrigger.getStartTime(),
							simpleTrigger.getEndTime(), description,
							messageBody);
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
			String groupName, long interval, Date startDate, Date endDate,
			String description, String destination, String messageBody)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger(
				groupName, groupName, SimpleTrigger.REPEAT_INDEFINITELY,
				interval);

			if (startDate == null) {
				if (ServerDetector.getServerId().equals(
					ServerDetector.TOMCAT_ID)) {
					simpleTrigger.setStartTime(
						new Date(System.currentTimeMillis() + Time.MINUTE));
				}
				else {
					simpleTrigger.setStartTime(
						new Date(
						System.currentTimeMillis() + Time.MINUTE * 3));
				}

			}
			else {
				simpleTrigger.setStartTime(startDate);
			}

			if (endDate != null) {
				simpleTrigger.setEndTime(endDate);
			}

			schedule(
				groupName, simpleTrigger, description, destination,
				messageBody);
		}
		catch (RuntimeException re) {
			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment
		}
	}

	public void schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destination, String messageBody)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			CronTrigger cronTrigger = new CronTrigger(
				groupName, groupName, cronText);

			if (startDate == null) {
				if (ServerDetector.getServerId().equals(
					ServerDetector.TOMCAT_ID)) {
					cronTrigger.setStartTime(
						new Date(System.currentTimeMillis() + Time.MINUTE));
				}
				else {
					cronTrigger.setStartTime(
						new Date(System.currentTimeMillis() + Time.MINUTE * 3));
				}

			}
			else {
				cronTrigger.setStartTime(startDate);
			}

			if (endDate != null) {
				cronTrigger.setEndTime(endDate);
			}

			schedule(
				groupName, cronTrigger, description, destination, messageBody);
		}
		catch(ParseException pe) {
			throw new SchedulerException("Unable to parse cron text", pe);
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

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

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
			String groupName, Trigger trigger, String description,
			String destination, String messageBody)
		throws SchedulerException {

		try {
			JobDetail jobDetail = new JobDetail(
				groupName, groupName, MessageSenderJob.class);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION, destination);
			jobDataMap.put(MESSAGE_BODY, messageBody);

			_scheduler.scheduleJob(jobDetail, trigger);
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

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService.impl")
	protected QuartzLocalService quartzLocalService;

	private Log _log = LogFactoryUtil.getLog(QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

}