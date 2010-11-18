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
import com.liferay.portal.kernel.messaging.DestinationNames;
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

			JobState jobState =(JobState)schedulerContext.get(
				jobDetail.getFullName());

			if (jobExecutionContext.getNextFireTime() == null) {
				message.put(SchedulerEngine.DISABLE, true);

				if (!destinationName.equals(
					DestinationNames.SCHEDULER_DISPATCH)) {

					Trigger trigger = jobExecutionContext.getTrigger();

					jobState.setTriggerTimeInfomation(
						SchedulerEngine.START_TIME, trigger.getStartTime());
					jobState.setTriggerTimeInfomation(
						SchedulerEngine.END_TIME, trigger.getEndTime());
					jobState.setTriggerTimeInfomation(
						SchedulerEngine.NEXT_FIRE_TIME, null);
					jobState.setTriggerTimeInfomation(
						SchedulerEngine.PREVIOUS_FIRE_TIME,
						trigger.getPreviousFireTime());
					jobState.setTriggerTimeInfomation(
						SchedulerEngine.FINAL_FIRE_TIME,
						trigger.getFinalFireTime());

					jobState.setTriggerState(TriggerState.COMPLETE);

					JobState jobStateCopy = (JobState)jobState.clone();

					jobStateCopy.clearExceptions();

					jobDataMap.put(SchedulerEngine.JOB_STATE, jobStateCopy);

					scheduler.addJob(jobDetail, true);
				}
			}

			message.put(SchedulerEngine.JOB_STATE, jobState);

			MessageBusUtil.sendMessage(destinationName, message);
		}
		catch (Exception e) {
			_log.error("Unable to execute job", e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MessageSenderJob.class);

}