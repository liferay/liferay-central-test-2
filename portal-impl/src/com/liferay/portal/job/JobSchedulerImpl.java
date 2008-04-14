/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.job;

import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobScheduler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.Time;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <a href="JobSchedulerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JobSchedulerImpl implements JobScheduler {

	public JobSchedulerImpl() {
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

		try {
			_scheduler = schedulerFactory.getScheduler();

			_scheduler.start();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void schedule(IntervalJob intervalJob) {
		String jobName =
			intervalJob.getClass().getName() + StringPool.AT +
				intervalJob.hashCode();

		JobClassUtil.put(jobName, (Class<IntervalJob>)intervalJob.getClass());

		Date startTime = new Date(System.currentTimeMillis() + Time.MINUTE * 1);
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

	private static Log _log = LogFactory.getLog(JobScheduler.class);

	private Scheduler _scheduler;

}