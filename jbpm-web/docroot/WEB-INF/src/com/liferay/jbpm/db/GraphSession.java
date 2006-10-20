/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.jbpm.db;

import com.liferay.jbpm.util.WorkflowUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.sql.Timestamp;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * <a href="GraphSession.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class GraphSession extends org.jbpm.db.GraphSession {

	public static String COUNT_PROCESS_DEFINITIONS_BY_NAME =
		GraphSession.class.getName() + ".countProcessDefinitionsByName";

	public static String COUNT_PROCESS_INSTANCES_BY_SEARCH_TERMS =
		GraphSession.class.getName() + ".countProcessInstancesBySearchTerms";

	public static String COUNT_TASK_INSTANCES_BY_ACTOR =
		GraphSession.class.getName() + ".countTaskInstancesByActor";

	public static String COUNT_TASK_INSTANCES_BY_POOL =
		GraphSession.class.getName() + ".countTaskInstancesByPool";

	public static String FIND_PROCESS_DEFINITIONS_BY_NAME =
		GraphSession.class.getName() + ".findProcessDefinitionsByName";

	public static String FIND_PROCESS_INSTANCES_BY_SEARCH_TERMS =
		GraphSession.class.getName() + ".findProcessInstancesBySearchTerms";

	public static String FIND_TASK_INSTANCES_BY_ACTOR =
		GraphSession.class.getName() + ".findTaskInstancesByActor";

	public static String FIND_TASK_INSTANCES_BY_POOL =
		GraphSession.class.getName() + ".findTaskInstancesByPool";

	public GraphSession(String userId, String timeZoneId,
						JbpmContext jbpmContext) {

		super(jbpmContext.getSession());

		_userId = userId;
		_timeZoneId = timeZoneId;
		_jbpmContext = jbpmContext;
		_session = jbpmContext.getSession();

		if (_session != null) {
			SessionFactoryImplementor sessionFactory =
				(SessionFactoryImplementor)_session.getSessionFactory();

			_dialect = sessionFactory.getDialect();
		}
	}

	public void close() {
		if (_session != null) {
			_session.close();
		}
	}

	public long countProcessDefinitionsByName(String name) {
		try {
			String sql = CustomSQLUtil.get(COUNT_PROCESS_DEFINITIONS_BY_NAME);

			Query q = _session.createQuery(sql);

			q.setString("name", name);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.longValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}
	}

	public long countProcessInstancesBySearchTerms(
		String workflowName, String workflowVersion, String gtStartDate,
		String ltStartDate, String gtEndDate, String ltEndDate,
		boolean hideEndedTasks, boolean andOperator) {

		try {
			int workflowVersionInt = 0;

			if (!Validator.isNumber(workflowVersion)) {
				workflowVersion = null;
			}
			else {
				workflowVersionInt = GetterUtil.getInteger(workflowVersion);
			}

			String endDateCheck = "(pi.end IS NULL) ";

			if (hideEndedTasks) {
				endDateCheck =
					"((pi.end >= ? [$AND_OR_NULL_CHECK$]) AND " +
						"(pi.end <= ? [$AND_OR_NULL_CHECK$])) ";
			}

			String sql = CustomSQLUtil.get(
				COUNT_PROCESS_INSTANCES_BY_SEARCH_TERMS);

			sql = StringUtil.replace(sql, "[$END_DATE_CHECK$]", endDateCheck);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			Query q = _session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(workflowName);
			qPos.add(workflowName);
			qPos.add(workflowVersionInt);
			qPos.add(workflowVersion);
			qPos.add(_getDate(gtStartDate, true));
			qPos.add(_getDate(gtStartDate, true));
			qPos.add(_getDate(ltStartDate, false));
			qPos.add(_getDate(ltStartDate, false));

			if (hideEndedTasks) {
				qPos.add(_getDate(gtEndDate, true));
				qPos.add(_getDate(gtEndDate, true));
				qPos.add(_getDate(ltEndDate, false));
				qPos.add(_getDate(ltEndDate, false));
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.longValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}
	}

	public long countTaskInstancesBySearchTerms(
		String taskName, String workflowName, String assignedTo,
		String gtCreateDate, String ltCreateDate, String gtStartDate,
		String ltStartDate, String gtEndDate, String ltEndDate,
		boolean hideEndedTasks, boolean andOperator) {

		try {
			String sql = "";

			int index = 0;

			if (Validator.isNull(assignedTo) || assignedTo.equals("all")) {
				sql += "(";
				sql += CustomSQLUtil.get(COUNT_TASK_INSTANCES_BY_ACTOR);
				sql += ") ";
				sql += "UNION ";
				sql += "(";
				sql += CustomSQLUtil.get(COUNT_TASK_INSTANCES_BY_POOL);
				sql += ") ";

				index = 2;
			}
			else if (assignedTo.equals("me")) {
				sql += CustomSQLUtil.get(COUNT_TASK_INSTANCES_BY_ACTOR);

				index = 1;
			}
			else if (assignedTo.equals("pool")) {
				sql += CustomSQLUtil.get(COUNT_TASK_INSTANCES_BY_POOL);

				index = 1;
			}

			sql += "ORDER BY taskActorId DESC, taskCreate ASC";

			String endDateCheck = "(JBPM_TaskInstance.END_ IS NULL) ";

			if (hideEndedTasks) {
				endDateCheck =
					"((JBPM_TaskInstance.END_ >= ? " +
							"[$AND_OR_NULL_CHECK$]) AND " +
						"(JBPM_TaskInstance.END_ <= ? [$AND_OR_NULL_CHECK$])) ";
			}

			sql = StringUtil.replace(sql, "[$END_DATE_CHECK$]", endDateCheck);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = _session.createSQLQuery(sql);

			q.addScalar("taskId", Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < index; i++) {
				qPos.add(taskName);
				qPos.add(taskName);
				qPos.add(workflowName);
				qPos.add(workflowName);
				qPos.add(_getDate(gtCreateDate, true));
				qPos.add(_getDate(gtCreateDate, true));
				qPos.add(_getDate(ltCreateDate, false));
				qPos.add(_getDate(ltCreateDate, false));
				qPos.add(_getDate(gtStartDate, true));
				qPos.add(_getDate(gtStartDate, true));
				qPos.add(_getDate(ltStartDate, false));
				qPos.add(_getDate(ltStartDate, false));

				if (hideEndedTasks) {
					qPos.add(_getDate(gtEndDate, true));
					qPos.add(_getDate(gtEndDate, true));
					qPos.add(_getDate(ltEndDate, false));
					qPos.add(_getDate(ltEndDate, false));
				}

				qPos.add(_userId);
			}

			long count = 0;

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Long l = (Long)itr.next();

				if (l != null) {
					count += l.longValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}
	}

	public List findProcessDefinitionsByName(String name, int begin, int end) {
		try {
			String sql = CustomSQLUtil.get(FIND_PROCESS_DEFINITIONS_BY_NAME);

			Query q = _session.createQuery(sql);

			q.setString("name", name);

			return QueryUtil.list(q, _dialect, begin, end);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}
	}

	public List findProcessInstancesBySearchTerms(
		String workflowName, String workflowVersion, String gtStartDate,
		String ltStartDate, String gtEndDate, String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end) {

		List list = new ArrayList();

		try {
			int workflowVersionInt = 0;

			if (!Validator.isNumber(workflowVersion)) {
				workflowVersion = null;
			}
			else {
				workflowVersionInt = GetterUtil.getInteger(workflowVersion);
			}

			String endDateCheck = "(pi.end IS NULL) ";

			if (hideEndedTasks) {
				endDateCheck =
					"((pi.end >= ? [$AND_OR_NULL_CHECK$]) AND " +
						"(pi.end <= ? [$AND_OR_NULL_CHECK$])) ";
			}

			String sql = CustomSQLUtil.get(
				FIND_PROCESS_INSTANCES_BY_SEARCH_TERMS);

			sql = StringUtil.replace(sql, "[$END_DATE_CHECK$]", endDateCheck);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			Query q = _session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(workflowName);
			qPos.add(workflowName);
			qPos.add(workflowVersionInt);
			qPos.add(workflowVersion);
			qPos.add(_getDate(gtStartDate, true));
			qPos.add(_getDate(gtStartDate, true));
			qPos.add(_getDate(ltStartDate, false));
			qPos.add(_getDate(ltStartDate, false));

			if (hideEndedTasks) {
				qPos.add(_getDate(gtEndDate, true));
				qPos.add(_getDate(gtEndDate, true));
				qPos.add(_getDate(ltEndDate, false));
				qPos.add(_getDate(ltEndDate, false));
			}

			list = QueryUtil.list(q, _dialect, begin, end);

			for (int i = 0; i < list.size(); i++) {
				ProcessInstance processInstance = (ProcessInstance)list.get(i);

				WorkflowUtil.initInstance(processInstance);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}

		return list;
	}

	public List findTaskInstancesBySearchTerms(
		String taskName, String workflowName, String assignedTo,
		String gtCreateDate, String ltCreateDate, String gtStartDate,
		String ltStartDate, String gtEndDate, String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end) {

		List list = new ArrayList();

		try {
			String sql = "";

			int index = 0;

			if (Validator.isNull(assignedTo) || assignedTo.equals("all")) {
				sql += "(";
				sql += CustomSQLUtil.get(FIND_TASK_INSTANCES_BY_ACTOR);
				sql += ") ";
				sql += "UNION ";
				sql += "(";
				sql += CustomSQLUtil.get(FIND_TASK_INSTANCES_BY_POOL);
				sql += ") ";

				index = 2;
			}
			else if (assignedTo.equals("me")) {
				sql += CustomSQLUtil.get(FIND_TASK_INSTANCES_BY_ACTOR);

				index = 1;
			}
			else if (assignedTo.equals("pool")) {
				sql += CustomSQLUtil.get(FIND_TASK_INSTANCES_BY_POOL);

				index = 1;
			}

			sql += "ORDER BY taskActorId DESC, taskCreate ASC";

			String endDateCheck = "(JBPM_TaskInstance.END_ IS NULL) ";

			if (hideEndedTasks) {
				endDateCheck =
					"((JBPM_TaskInstance.END_ >= ? " +
							"[$AND_OR_NULL_CHECK$]) AND " +
						"(JBPM_TaskInstance.END_ <= ? [$AND_OR_NULL_CHECK$])) ";
			}

			sql = StringUtil.replace(sql, "[$END_DATE_CHECK$]", endDateCheck);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = _session.createSQLQuery(sql);

			q.addScalar("taskId", Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < index; i++) {
				qPos.add(taskName);
				qPos.add(taskName);
				qPos.add(workflowName);
				qPos.add(workflowName);
				qPos.add(_getDate(gtCreateDate, true));
				qPos.add(_getDate(gtCreateDate, true));
				qPos.add(_getDate(ltCreateDate, false));
				qPos.add(_getDate(ltCreateDate, false));
				qPos.add(_getDate(gtStartDate, true));
				qPos.add(_getDate(gtStartDate, true));
				qPos.add(_getDate(ltStartDate, false));
				qPos.add(_getDate(ltStartDate, false));

				if (hideEndedTasks) {
					qPos.add(_getDate(gtEndDate, true));
					qPos.add(_getDate(gtEndDate, true));
					qPos.add(_getDate(ltEndDate, false));
					qPos.add(_getDate(ltEndDate, false));
				}

				qPos.add(_userId);
			}

			Iterator itr = QueryUtil.iterate(q, _dialect, begin, end);

			while (itr.hasNext()) {
				Long taskId = (Long)itr.next();

				TaskInstance taskInstance =
					_jbpmContext.loadTaskInstance(taskId.longValue());

				WorkflowUtil.initTask(taskInstance);

				list.add(taskInstance);
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new RuntimeException(e.getMessage());
		}

		return list;
	}

	private Timestamp _getDate(String date, boolean greaterThan) {
		if (Validator.isNull(date)) {
			return null;
		}
		else {
			Calendar calendar = Calendar.getInstance();

			DateFormat dateFormat = DateUtil.getISOFormat();

			calendar.setTime(GetterUtil.getDate(date, dateFormat));

			if (greaterThan) {
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			}
			else {
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
			}

			TimeZone timeZone = TimeZone.getTimeZone(_timeZoneId);

			int offset = timeZone.getOffset(calendar.getTimeInMillis());

			return new Timestamp(calendar.getTimeInMillis() - offset);
		}
	}

	private static final Log _log = LogFactory.getLog(GraphSession.class);

	private String _userId;
	private String _timeZoneId;
	private JbpmContext _jbpmContext;
	private Session _session;
	private Dialect _dialect;

}