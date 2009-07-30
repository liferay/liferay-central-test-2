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

import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.tools.sql.DB2Util;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.tools.sql.DerbyUtil;
import com.liferay.portal.tools.sql.HypersonicUtil;
import com.liferay.portal.tools.sql.PostgreSQLUtil;
import com.liferay.portal.tools.sql.SQLServerUtil;
import com.liferay.portal.tools.sql.SybaseUtil;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;

import org.quartz.Calendar;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.jdbcjobstore.CloudscapeDelegate;
import org.quartz.impl.jdbcjobstore.DB2v7Delegate;
import org.quartz.impl.jdbcjobstore.HSQLDBDelegate;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.utils.Key;
import org.quartz.utils.TriggerStatus;

/**
 * <a href="DynamicDriverDelegate.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DynamicDriverDelegate extends StdJDBCDelegate {

	public DynamicDriverDelegate(
		Log logger, String tablePrefix, String instanceId) {

		super(logger, tablePrefix, instanceId);

		try {
			Class<?> driverDelegateClass = getDriverDelegateClass();

			Constructor<?> driverDelegateConstructor =
				driverDelegateClass.getConstructor(
					Log.class, String.class, String.class);

			_jdbcDelegate =
				(StdJDBCDelegate)driverDelegateConstructor.newInstance(
					logger, tablePrefix, instanceId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public DynamicDriverDelegate(
		Log logger, String tablePrefix, String instanceId,
		Boolean useProperties) {

		super(logger, tablePrefix, instanceId, useProperties);

		try {
			Class<?> driverDelegateClass = getDriverDelegateClass();

			Constructor<?> driverDelegateConstructor =
				driverDelegateClass.getConstructor(
					Log.class, String.class, String.class, Boolean.class);

			_jdbcDelegate =
				(StdJDBCDelegate)driverDelegateConstructor.newInstance(
					logger, tablePrefix, instanceId, useProperties);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public boolean calendarExists(Connection conn, String calendarName)
		throws SQLException {

		return _jdbcDelegate.calendarExists(conn, calendarName);
	}

	public boolean calendarIsReferenced(Connection conn, String calendarName)
		throws SQLException {

		return _jdbcDelegate.calendarIsReferenced(conn, calendarName);
	}

	public int countMisfiredTriggersInStates(
			Connection conn, String state1, String state2, long ts)
		throws SQLException {

		return _jdbcDelegate.countMisfiredTriggersInStates(
			conn, state1, state2, ts);
	}

	public int deleteAllPausedTriggerGroups(Connection arg0)
		throws SQLException {

		return _jdbcDelegate.deleteAllPausedTriggerGroups(arg0);
	}

	public int deleteBlobTrigger(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteBlobTrigger(conn, triggerName, groupName);
	}

	public int deleteCalendar(Connection conn, String calendarName)
		throws SQLException {

		return _jdbcDelegate.deleteCalendar(conn, calendarName);
	}

	public int deleteCronTrigger(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteCronTrigger(conn, triggerName, groupName);
	}

	public int deleteFiredTrigger(Connection conn, String entryId)
		throws SQLException {

		return _jdbcDelegate.deleteFiredTrigger(conn, entryId);
	}

	public int deleteFiredTriggers(Connection conn) throws SQLException {
		return _jdbcDelegate.deleteFiredTriggers(conn);
	}

	public int deleteFiredTriggers(Connection conn, String instanceId)
		throws SQLException {

		return _jdbcDelegate.deleteFiredTriggers(conn, instanceId);
	}

	public int deleteJobDetail(
			Connection conn, String jobName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteJobDetail(conn, jobName, groupName);
	}

	public int deleteJobListeners(
			Connection conn, String jobName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteJobListeners(conn, jobName, groupName);
	}

	public int deletePausedTriggerGroup(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.deletePausedTriggerGroup(arg0, arg1);
	}

	public int deleteSchedulerState(Connection conn, String instanceId)
		throws SQLException {

		return _jdbcDelegate.deleteSchedulerState(conn, instanceId);
	}

	public int deleteSimpleTrigger(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteSimpleTrigger(conn, triggerName, groupName);
	}

	public int deleteTrigger(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteTrigger(conn, triggerName, groupName);
	}

	public int deleteTriggerListeners(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.deleteTriggerListeners(
			conn, triggerName, groupName);
	}

	public int deleteVolatileFiredTriggers(Connection conn)
		throws SQLException {

		return _jdbcDelegate.deleteVolatileFiredTriggers(conn);
	}

	public int insertBlobTrigger(Connection arg0, Trigger arg1)
		throws SQLException, IOException {

		return _jdbcDelegate.insertBlobTrigger(arg0, arg1);
	}

	public int insertCalendar(
			Connection conn, String calendarName, Calendar calendar)
		throws IOException, SQLException {

		return _jdbcDelegate.insertCalendar(conn, calendarName, calendar);
	}

	public int insertCronTrigger(Connection conn, CronTrigger trigger)
		throws SQLException {

		return _jdbcDelegate.insertCronTrigger(conn, trigger);
	}

	public int insertFiredTrigger(
			Connection conn, Trigger trigger, String state, JobDetail job)
		throws SQLException {

		return _jdbcDelegate.insertFiredTrigger(conn, trigger, state, job);
	}

	public int insertJobDetail(Connection arg0, JobDetail arg1)
		throws IOException, SQLException {

		return _jdbcDelegate.insertJobDetail(arg0, arg1);
	}

	public int insertJobListener(
			Connection conn, JobDetail job, String listener)
		throws SQLException {

		return _jdbcDelegate.insertJobListener(conn, job, listener);
	}

	public int insertPausedTriggerGroup(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.insertPausedTriggerGroup(arg0, arg1);
	}

	public int insertSchedulerState(
			Connection conn, String instanceId, long checkInTime, long interval)
		throws SQLException {

		return _jdbcDelegate.insertSchedulerState(
			conn, instanceId, checkInTime, interval);
	}

	public int insertSimpleTrigger(Connection conn, SimpleTrigger trigger)
		throws SQLException {

		return _jdbcDelegate.insertSimpleTrigger(conn, trigger);
	}

	public int insertTrigger(
			Connection arg0, Trigger arg1, String arg2, JobDetail arg3)
		throws SQLException, IOException {

		return _jdbcDelegate.insertTrigger(arg0, arg1, arg2, arg3);
	}

	public int insertTriggerListener(
			Connection conn, Trigger trigger, String listener)
		throws SQLException {

		return _jdbcDelegate.insertTriggerListener(conn, trigger, listener);
	}

	public boolean isExistingTriggerGroup(Connection conn, String groupName)
		throws SQLException {

		return _jdbcDelegate.isExistingTriggerGroup(conn, groupName);
	}

	public boolean isJobStateful(
			Connection conn, String jobName, String groupName)
		throws SQLException {

		return _jdbcDelegate.isJobStateful(conn, jobName, groupName);
	}

	public boolean isTriggerGroupPaused(Connection conn, String groupName)
		throws SQLException {

		return _jdbcDelegate.isTriggerGroupPaused(conn, groupName);
	}

	public boolean jobExists(Connection conn, String jobName, String groupName)
		throws SQLException {

		return _jdbcDelegate.jobExists(conn, jobName, groupName);
	}

	public Calendar selectCalendar(Connection arg0, String arg1)
		throws ClassNotFoundException, IOException, SQLException {

		return _jdbcDelegate.selectCalendar(arg0, arg1);
	}

	public String[] selectCalendars(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectCalendars(arg0);
	}

	public Set selectFiredTriggerInstanceNames(Connection arg0)
		throws SQLException {

		return _jdbcDelegate.selectFiredTriggerInstanceNames(arg0);
	}

	public List selectFiredTriggerRecords(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectFiredTriggerRecords(arg0, arg1, arg2);
	}

	public List selectFiredTriggerRecordsByJob(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectFiredTriggerRecordsByJob(arg0, arg1, arg2);
	}

	public List selectInstancesFiredTriggerRecords(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.selectInstancesFiredTriggerRecords(arg0, arg1);
	}

	public JobDetail selectJobDetail(
			Connection arg0, String arg1, String arg2, ClassLoadHelper arg3)
		throws ClassNotFoundException, IOException, SQLException {

		return _jdbcDelegate.selectJobDetail(arg0, arg1, arg2, arg3);
	}

	public int selectJobExecutionCount(
			Connection conn, String jobName, String jobGroup)
		throws SQLException {

		return _jdbcDelegate.selectJobExecutionCount(conn, jobName, jobGroup);
	}

	public JobDetail selectJobForTrigger(
			Connection arg0, String arg1, String arg2, ClassLoadHelper arg3)
		throws ClassNotFoundException, SQLException {

		return _jdbcDelegate.selectJobForTrigger(arg0, arg1, arg2, arg3);
	}

	public String[] selectJobGroups(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectJobGroups(arg0);
	}

	public String[] selectJobListeners(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectJobListeners(arg0, arg1, arg2);
	}

	public String[] selectJobsInGroup(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.selectJobsInGroup(arg0, arg1);
	}

	public Key[] selectMisfiredTriggers(Connection arg0, long arg1)
		throws SQLException {

		return _jdbcDelegate.selectMisfiredTriggers(arg0, arg1);
	}

	public Key[] selectMisfiredTriggersInGroupInState(
			Connection arg0, String arg1, String arg2, long arg3)
		throws SQLException {

		return _jdbcDelegate.selectMisfiredTriggersInGroupInState(
			arg0, arg1, arg2, arg3);
	}

	public Key[] selectMisfiredTriggersInState(
			Connection arg0, String arg1, long arg2)
		throws SQLException {

		return _jdbcDelegate.selectMisfiredTriggersInState(arg0, arg1, arg2);
	}

	public boolean selectMisfiredTriggersInStates(
			Connection arg0, String arg1, String arg2, long arg3, int arg4,
			List arg5)
		throws SQLException {

		return _jdbcDelegate.selectMisfiredTriggersInStates(
			arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public long selectNextFireTime(Connection conn) throws SQLException {
		return _jdbcDelegate.selectNextFireTime(conn);
	}

	public int selectNumCalendars(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectNumCalendars(arg0);
	}

	public int selectNumJobs(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectNumJobs(arg0);
	}

	public int selectNumTriggers(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectNumTriggers(arg0);
	}

	public int selectNumTriggersForJob(
			Connection conn, String jobName, String groupName)
		throws SQLException {

		return _jdbcDelegate.selectNumTriggersForJob(conn, jobName, groupName);
	}

	public Set selectPausedTriggerGroups(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectPausedTriggerGroups(arg0);
	}

	public List selectSchedulerStateRecords(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.selectSchedulerStateRecords(arg0, arg1);
	}

	public List selectStatefulJobsOfTriggerGroup(
			Connection conn, String groupName)
		throws SQLException {

		return _jdbcDelegate.selectStatefulJobsOfTriggerGroup(conn, groupName);
	}

	public Trigger selectTrigger(Connection arg0, String arg1, String arg2)
		throws SQLException, ClassNotFoundException, IOException {

		return _jdbcDelegate.selectTrigger(arg0, arg1, arg2);
	}

	public Key selectTriggerForFireTime(Connection conn, long fireTime)
		throws SQLException {

		return _jdbcDelegate.selectTriggerForFireTime(conn, fireTime);
	}

	public String[] selectTriggerGroups(Connection arg0)
		throws SQLException {

		return _jdbcDelegate.selectTriggerGroups(arg0);
	}

	public JobDataMap selectTriggerJobDataMap(
			Connection arg0, String arg1, String arg2)
		throws SQLException, ClassNotFoundException, IOException {

		return _jdbcDelegate.selectTriggerJobDataMap(arg0, arg1, arg2);
	}

	public String[] selectTriggerListeners(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectTriggerListeners(arg0, arg1, arg2);
	}

	public Key[] selectTriggerNamesForJob(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectTriggerNamesForJob(arg0, arg1, arg2);
	}

	public Trigger[] selectTriggersForCalendar(Connection conn, String calName)
		throws SQLException, ClassNotFoundException, IOException {

		return _jdbcDelegate.selectTriggersForCalendar(conn, calName);
	}

	public Trigger[] selectTriggersForJob(
			Connection arg0, String arg1, String arg2)
		throws SQLException, ClassNotFoundException, IOException {

		return _jdbcDelegate.selectTriggersForJob(arg0, arg1, arg2);
	}

	public Trigger[] selectTriggersForRecoveringJobs(Connection arg0)
		throws SQLException, IOException, ClassNotFoundException {

		return _jdbcDelegate.selectTriggersForRecoveringJobs(arg0);
	}

	public String[] selectTriggersInGroup(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.selectTriggersInGroup(arg0, arg1);
	}

	public Key[] selectTriggersInState(Connection arg0, String arg1)
		throws SQLException {

		return _jdbcDelegate.selectTriggersInState(arg0, arg1);
	}

	public String selectTriggerState(Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectTriggerState(arg0, arg1, arg2);
	}

	public TriggerStatus selectTriggerStatus(
			Connection arg0, String arg1, String arg2)
		throws SQLException {

		return _jdbcDelegate.selectTriggerStatus(arg0, arg1, arg2);
	}

	public Key selectTriggerToAcquire(
			Connection conn, long noLaterThan, long noEarlierThan)
		throws SQLException {

		return _jdbcDelegate.selectTriggerToAcquire(
			conn, noLaterThan, noEarlierThan);
	}

	public Key[] selectVolatileJobs(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectVolatileJobs(arg0);
	}

	public Key[] selectVolatileTriggers(Connection arg0) throws SQLException {
		return _jdbcDelegate.selectVolatileTriggers(arg0);
	}

	public boolean triggerExists(
			Connection conn, String triggerName, String groupName)
		throws SQLException {

		return _jdbcDelegate.triggerExists(conn, triggerName, groupName);
	}

	public int updateBlobTrigger(Connection arg0, Trigger arg1)
		throws SQLException, IOException {

		return _jdbcDelegate.updateBlobTrigger(arg0, arg1);
	}

	public int updateCalendar(
			Connection conn, String calendarName, Calendar calendar)
		throws IOException, SQLException {

		return _jdbcDelegate.updateCalendar(conn, calendarName, calendar);
	}

	public int updateCronTrigger(Connection conn, CronTrigger trigger)
		throws SQLException {

		return _jdbcDelegate.updateCronTrigger(conn, trigger);
	}

	public int updateJobData(Connection conn, JobDetail job)
		throws IOException, SQLException {

		return _jdbcDelegate.updateJobData(conn, job);
	}

	public int updateJobDetail(Connection arg0, JobDetail arg1)
		throws IOException, SQLException {

		return _jdbcDelegate.updateJobDetail(arg0, arg1);
	}

	public int updateSchedulerState(
			Connection conn, String instanceId, long checkInTime)
		throws SQLException {

		return _jdbcDelegate.updateSchedulerState(
			conn, instanceId, checkInTime);
	}

	public int updateSimpleTrigger(Connection conn, SimpleTrigger trigger)
		throws SQLException {

		return _jdbcDelegate.updateSimpleTrigger(conn, trigger);
	}

	public int updateTrigger(
			Connection arg0, Trigger arg1, String arg2, JobDetail arg3)
		throws SQLException, IOException {

		return _jdbcDelegate.updateTrigger(arg0, arg1, arg2, arg3);
	}

	public int updateTriggerGroupStateFromOtherState(
			Connection conn, String groupName, String newState, String oldState)
		throws SQLException {

		return _jdbcDelegate.updateTriggerGroupStateFromOtherState(
			conn, groupName, newState, oldState);
	}

	public int updateTriggerGroupStateFromOtherStates(
			Connection conn, String groupName, String newState,
			String oldState1, String oldState2, String oldState3)
		throws SQLException {

		return _jdbcDelegate.updateTriggerGroupStateFromOtherStates(
			conn, groupName, newState, oldState1, oldState2, oldState3);
	}

	public int updateTriggerState(
			Connection conn, String triggerName, String groupName, String state)
		throws SQLException {

		return _jdbcDelegate.updateTriggerState(
			conn, triggerName, groupName, state);
	}

	public int updateTriggerStateFromOtherState(
			Connection conn, String triggerName, String groupName,
			String newState, String oldState)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStateFromOtherState(
			conn, triggerName, groupName, newState, oldState);
	}

	public int updateTriggerStateFromOtherStates(
			Connection conn, String triggerName, String groupName,
			String newState, String oldState1, String oldState2,
			String oldState3)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStateFromOtherStates(
			conn, triggerName, groupName, newState, oldState1, oldState2,
			oldState3);
	}

	public int updateTriggerStateFromOtherStatesBeforeTime(
			Connection conn, String newState, String oldState1,
			String oldState2, long time)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStateFromOtherStatesBeforeTime(
			conn, newState, oldState1, oldState2, time);
	}

	public int updateTriggerStatesForJob(
			Connection conn, String jobName, String groupName, String state)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStatesForJob(
			conn, jobName, groupName, state);
	}

	public int updateTriggerStatesForJobFromOtherState(
			Connection conn, String jobName, String groupName, String state,
			String oldState)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStatesForJobFromOtherState(
			conn, jobName, groupName, state, oldState);
	}

	public int updateTriggerStatesFromOtherStates(
			Connection conn, String newState, String oldState1,
			String oldState2)
		throws SQLException {

		return _jdbcDelegate.updateTriggerStatesFromOtherStates(
			conn, newState, oldState1, oldState2);
	}

	protected Class<?> getDriverDelegateClass() {
		Class<?> driverDelegateClass = StdJDBCDelegate.class;

		DBUtil dbUtil = DBUtil.getInstance();

		if (dbUtil instanceof DB2Util) {
			driverDelegateClass = DB2v7Delegate.class;
		}
		else if (dbUtil instanceof DerbyUtil) {
			driverDelegateClass = CloudscapeDelegate.class;
		}
		else if (dbUtil instanceof HypersonicUtil) {
			driverDelegateClass = HSQLDBDelegate.class;
		}
		else if (dbUtil instanceof PostgreSQLUtil) {
			driverDelegateClass = PostgreSQLDelegate.class;
		}
		else if (dbUtil instanceof SQLServerUtil) {
			driverDelegateClass = MSSQLDelegate.class;
		}
		else if (dbUtil instanceof SybaseUtil) {
			driverDelegateClass = MSSQLDelegate.class;
		}

		return driverDelegateClass;
	}

	private com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(DynamicDriverDelegate.class);

	private StdJDBCDelegate _jdbcDelegate;

}