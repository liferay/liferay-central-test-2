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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.SchedulerRequest;
import com.liferay.portal.tools.sql.DB2Util;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.tools.sql.DerbyUtil;
import com.liferay.portal.tools.sql.HypersonicUtil;
import com.liferay.portal.tools.sql.PostgreSQLUtil;
import com.liferay.portal.tools.sql.SQLServerUtil;
import com.liferay.portal.tools.sql.SybaseUtil;
import com.liferay.portal.util.PropsUtil;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.CloudscapeDelegate;
import org.quartz.impl.jdbcjobstore.DB2v7Delegate;
import org.quartz.impl.jdbcjobstore.HSQLDBDelegate;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

/**
 * <a href="QuartzSchedulerEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 *
 */
public class QuartzSchedulerEngineImpl implements SchedulerEngine {

	public QuartzSchedulerEngineImpl() {
		try {
			// Message Bus registration
			Destination destination = new SerialDestination(
				DestinationNames.SCHEDULER);

			MessageBusUtil.addDestination(destination);

			MessageBusUtil.registerMessageListener(
				destination.getName(), new QuartzMessageListener());

			// Quartz properties

			Properties props = new Properties();

			props.put(
				StdSchedulerFactory.PROP_SCHED_INSTANCE_ID,
				StdSchedulerFactory.AUTO_GENERATE_INSTANCE_ID);
			props.put(
				StdSchedulerFactory.PROP_THREAD_POOL_CLASS,
				PropsUtil.get(PropsUtil.QUARTZ_THREAD_POOL_CLASS));
			props.put(
				"org.quartz.threadPool.threadCount",
				PropsUtil.get(PropsUtil.QUARTZ_THREAD_POOL_COUNT));
			props.put(
				"org.quartz.threadPool.threadPriority",
				PropsUtil.get(PropsUtil.QUARTZ_THREAD_POOL_PRIORITY));
			props.put(
				"org.quartz.jobStore.misfireThreshold",
				PropsUtil.get(PropsUtil.QUARTZ_JOBSTORE_MISFIRE_THRESHOLD));
			props.put(
				StdSchedulerFactory.PROP_JOB_STORE_CLASS,
				PropsUtil.get(PropsUtil.QUARTZ_JOBSTORE_CLASS));
			props.put(
				"org.quartz.jobStore.isClustered",
				PropsUtil.get(PropsUtil.QUARTZ_JOBSTORE_CLUSTERED));
			props.put(
				StdSchedulerFactory.PROP_JOB_STORE_USE_PROP, "true");
			props.put(
				"org.quartz.jobStore.dataSource", "ds");
			props.put(
				"org.quartz.dataSource.ds.connectionProvider.class",
				QuartzConnectionProviderImpl.class.getName());
			props.put(
				"org.quartz.jobStore.tablePrefix", "quartz_");
			props.put(
				"org.quartz.jobStore.driverDelegateClass",
				getDriverDelegateClass());

			StdSchedulerFactory factory = new StdSchedulerFactory();

			factory.initialize(props);

			_scheduler = factory.getScheduler();
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public Collection<SchedulerRequest> retrieveScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			String names[] = _scheduler.getJobNames(groupName);

			List<SchedulerRequest> requests =
				new ArrayList<SchedulerRequest>();

			for (int i = 0; i < names.length; i++) {
				JobDetail detail = _scheduler.getJobDetail(names[i], groupName);

				String description = detail.getJobDataMap().getString(
					SchedulerEngine.DESCRIPTION);
				String messageBody = detail.getJobDataMap().getString(
					SchedulerEngine.MESSAGE_BODY);

				CronTrigger trigger = (CronTrigger)_scheduler.getTrigger(
					names[i], groupName);

				SchedulerRequest sr = new SchedulerRequest(
					trigger.getCronExpression(), groupName, names[i],
					messageBody, trigger.getStartTime(), trigger.getEndTime(),
					description);

				requests.add(sr);
			}

			return requests;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to retrieve job", se);
		}
	}

	public void schedule(
			String jobName, String groupName, String cronText,
			String destinationName, String messageBody, Date startDate,
			Date endDate, String description)
		throws SchedulerException {

		try {
			CronTrigger trigger = new CronTrigger(jobName, groupName, cronText);

			if (startDate != null) {
				trigger.setStartTime(startDate);
			}

			if (endDate != null) {
				trigger.setEndTime(endDate);
			}

			JobDetail detail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			detail.getJobDataMap().put(DESCRIPTION, description);
			detail.getJobDataMap().put(DESTINATION_NAME, destinationName);
			detail.getJobDataMap().put(MESSAGE_BODY, messageBody);

			_scheduler.scheduleJob(detail, trigger);
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
		catch (ParseException pe) {
			throw new SchedulerException("Unable to parse cron text", pe);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to scheduled job", se);
		}
	}

	public void shutdown() throws SchedulerException {
		try {
			_scheduler.shutdown(false);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to shutdown scheduler", se);
		}
	}

	public void start() throws SchedulerException {
		try {
			_scheduler.start();
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to start scheduler", se);
		}
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		try {
			_scheduler.unscheduleJob(jobName, groupName);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException(
				"Unable to unschedule job: " + jobName + "," + groupName , se);
		}
	}

	protected String getDriverDelegateClass() {
		String driverDelegateClass = StdJDBCDelegate.class.getName();

		DBUtil dbUtil = DBUtil.getInstance();

		if (dbUtil instanceof DB2Util) {
			driverDelegateClass = DB2v7Delegate.class.getName();
		}
		else if (dbUtil instanceof DerbyUtil) {
			driverDelegateClass = CloudscapeDelegate.class.getName();
		}
		else if (dbUtil instanceof HypersonicUtil) {
			driverDelegateClass = HSQLDBDelegate.class.getName();
		}
		else if (dbUtil instanceof PostgreSQLUtil) {
			driverDelegateClass = PostgreSQLDelegate.class.getName();
		}
		else if (dbUtil instanceof SQLServerUtil) {
			driverDelegateClass = MSSQLDelegate.class.getName();
		}
		else if (dbUtil instanceof SybaseUtil) {
			driverDelegateClass = MSSQLDelegate.class.getName();
		}

		return driverDelegateClass;
	}

	private Log _log = LogFactory.getLog(QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("oi", "oi");
	}

}