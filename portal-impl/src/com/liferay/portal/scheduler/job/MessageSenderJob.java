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
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.TriggerState;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class MessageSenderJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			doExecute(jobExecutionContext);
		}
		catch (Exception e) {
			_log.error("Unable to execute job", e);
		}
	}

	protected void doExecute(JobExecutionContext jobExecutionContext)
		throws Exception {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		Message message = (Message)jobDataMap.get(SchedulerEngine.MESSAGE);

		if (message == null) {
			message = new Message();
		}

		message.put(SchedulerEngine.DESTINATION_NAME, destinationName);

		Scheduler scheduler = jobExecutionContext.getScheduler();

		SchedulerContext schedulerContext = scheduler.getContext();

		JobState jobState = (JobState)schedulerContext.get(
			jobDetail.getFullName());

		if (jobExecutionContext.getNextFireTime() == null) {
			if (message.getBoolean(SchedulerEngine.PERMANENT)) {
				Trigger trigger = jobExecutionContext.getTrigger();

				jobState.setTriggerTimeInfomation(
					SchedulerEngine.END_TIME, trigger.getEndTime());
				jobState.setTriggerTimeInfomation(
					SchedulerEngine.FINAL_FIRE_TIME,
					trigger.getFinalFireTime());
				jobState.setTriggerTimeInfomation(
					SchedulerEngine.NEXT_FIRE_TIME, null);
				jobState.setTriggerTimeInfomation(
					SchedulerEngine.PREVIOUS_FIRE_TIME,
					trigger.getPreviousFireTime());
				jobState.setTriggerTimeInfomation(
					SchedulerEngine.START_TIME, trigger.getStartTime());

				jobState.setTriggerState(TriggerState.COMPLETE);

				JobState jobStateClone = (JobState)jobState.clone();

				jobStateClone.clearExceptions();

				jobDataMap.put(SchedulerEngine.JOB_STATE, jobStateClone);

				scheduler.addJob(jobDetail, true);
			}
			else {
				message.put(SchedulerEngine.DISABLE, true);
			}
		}

		message.put(SchedulerEngine.JOB_STATE, jobState);

		MessageBusUtil.sendMessage(destinationName, message);
	}

	private static Log _log = LogFactoryUtil.getLog(MessageSenderJob.class);

}