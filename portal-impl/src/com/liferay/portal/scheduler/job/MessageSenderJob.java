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

package com.liferay.portal.scheduler.job;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class MessageSenderJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			JobDetail jobDetail = jobExecutionContext.getJobDetail();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			String destinationName = jobDataMap.getString(
				SchedulerEngine.DESTINATION_NAME);

			Message message = (Message)jobDataMap.get(SchedulerEngine.MESSAGE);

			if (message == null) {
				message = new Message();
			}

			message.put(
				"scheduledFireTime",
				jobExecutionContext.getScheduledFireTime());

			Scheduler scheduler = jobExecutionContext.getScheduler();

			SchedulerContext schedulerContext = scheduler.getContext();

			message.put(
				SchedulerEngine.JOB_STATE,
				schedulerContext.get(jobDetail.getFullName()));

			if (jobExecutionContext.getNextFireTime() == null) {
				message.put(SchedulerEngine.DISABLE, true);
			}

			MessageBusUtil.sendMessage(destinationName, message);
		}
		catch (Exception e) {
			_log.error("Unable to execute job", e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MessageSenderJob.class);

}