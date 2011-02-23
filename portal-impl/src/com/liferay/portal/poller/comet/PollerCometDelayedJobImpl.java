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

package com.liferay.portal.poller.comet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.util.PropsValues;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Edward Han
 */
public class PollerCometDelayedJobImpl
	extends BaseMessageListener implements PollerCometDelayedJob {

	//	TODO: Switch code to transient scheduling when complete
	public void addPollerCometDelayedTask(
		PollerCometDelayedTask pollerCometDelayedTask) {

		synchronized (_pollerCometDelayedTasks) {
			if (!jobScheduled) {
				//	remove when Transient scheduler available.
				_jobTimer = new Timer(_CRON_JOB_NAME);

				_jobTimer.schedule(
					new PollerCometTimerTask(),
					PropsValues.POLLER_NOTIFICATION_BATCH_WAIT);

				jobScheduled = true;

				/*	Uncomment when Transient Scheduling complete
				Trigger trigger = new IntervalTrigger(
					_CRON_JOB_NAME, _CRON_GROUP_NAME,
					PropsValues.POLLER_NOTIFICATION_BATCH_WAIT);

				try {
					SchedulerEngineUtil.schedule(
						trigger, _JOB_DESCRIPTION,
						DestinationNames.POLLER_COMET_RESPONSE, null);

					jobScheduled = true;
				}
				catch (SchedulerException se) {
					if (_log.isErrorEnabled()) {
						_log.error(
							"Unable to schedule heartbeat job for Poller", se);
					}
				}
				*/
			}

			_pollerCometDelayedTasks.add(pollerCometDelayedTask);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		synchronized (_pollerCometDelayedTasks) {
			for (PollerCometDelayedTask pollerCometDelayedTask :
				_pollerCometDelayedTasks) {

				try {
					pollerCometDelayedTask.doTask();
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Error occurred while processing" +
							" PollerCometDelayedTask", e);
					}
				}
			}

			_pollerCometDelayedTasks.clear();
		}
	}

	//	remove when Transient scheduler available.
	private class PollerCometTimerTask extends TimerTask {
		@Override
		public void run() {
			synchronized (_pollerCometDelayedTasks) {
				for (PollerCometDelayedTask pollerCometDelayedTask :
					_pollerCometDelayedTasks) {

					try {
						pollerCometDelayedTask.doTask();
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("Error occurred while processing" +
								" PollerCometDelayedTask", e);
						}
					}
				}

				_pollerCometDelayedTasks.clear();
			}
		}
	}

	private static final String _CRON_GROUP_NAME = "poller";

	private static final String _CRON_JOB_NAME = "poller";

	private static final String _JOB_DESCRIPTION = "Comet Poller";

	private static final Log _log = LogFactoryUtil.getLog(
		PollerCometDelayedJob.class);

	private List<PollerCometDelayedTask> _pollerCometDelayedTasks =
		new LinkedList<PollerCometDelayedTask>();

	//	remove when Transient scheduler available.
	private Timer _jobTimer = null;

	private boolean jobScheduled = false;
}