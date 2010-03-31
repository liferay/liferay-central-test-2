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

package com.liferay.portal.job;

import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobScheduler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * <a href="JobSchedulerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobSchedulerImpl implements JobScheduler {

	public void schedule(IntervalJob intervalJob) {
		if (intervalJob == null) {
			return;
		}

		try {
			if (_scheduler.isShutdown()) {
				return;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			if (!_scheduler.isStarted()) {
				_scheduler.start();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String jobName =
			intervalJob.getClass().getName() + StringPool.AT +
				intervalJob.hashCode();

		JobClassUtil.put(jobName, (Class<IntervalJob>)intervalJob.getClass());

		Date startTime = null;

		try {
			if (ServerDetector.getServerId().equals(ServerDetector.TOMCAT_ID)) {
				startTime = new Date(System.currentTimeMillis() + Time.MINUTE);
			}
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}

		if (startTime == null) {
			startTime = new Date(System.currentTimeMillis() + Time.MINUTE * 3);
		}

		Date endTime = null;

		JobDetail jobDetail = new JobDetail(
			jobName, Scheduler.DEFAULT_GROUP, JobWrapper.class);

		Trigger trigger = new SimpleTrigger(
			jobName, Scheduler.DEFAULT_GROUP, startTime, endTime,
			SimpleTrigger.REPEAT_INDEFINITELY, intervalJob.getInterval());

		try {
			_scheduler.scheduleJob(jobDetail, trigger);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setScheduler(Scheduler scheduler) {
		_scheduler = scheduler;
	}

	public void shutdown() {
		try {
			if (!_scheduler.isShutdown()) {
				_scheduler.shutdown();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void unschedule(IntervalJob intervalJob) {
		if (intervalJob == null) {
			return;
		}

		try {
			if (_scheduler.isShutdown()) {
				return;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			String jobName =
				intervalJob.getClass().getName() + StringPool.AT +
					intervalJob.hashCode();

			_scheduler.unscheduleJob(jobName, Scheduler.DEFAULT_GROUP);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JobScheduler.class);

	private Scheduler _scheduler;

}