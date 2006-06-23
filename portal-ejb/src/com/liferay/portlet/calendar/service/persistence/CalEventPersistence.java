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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.calendar.NoSuchEventException;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="CalEventPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventPersistence extends BasePersistence {
	public com.liferay.portlet.calendar.model.CalEvent create(String eventId) {
		CalEventHBM calEventHBM = new CalEventHBM();
		calEventHBM.setNew(true);
		calEventHBM.setPrimaryKey(eventId);

		return CalEventHBMUtil.model(calEventHBM);
	}

	public com.liferay.portlet.calendar.model.CalEvent remove(String eventId)
		throws NoSuchEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CalEventHBM calEventHBM = (CalEventHBM)session.get(CalEventHBM.class,
					eventId);

			if (calEventHBM == null) {
				_log.warn("No CalEvent exists with the primary key " +
					eventId.toString());
				throw new NoSuchEventException(
					"No CalEvent exists with the primary key " +
					eventId.toString());
			}

			session.delete(calEventHBM);
			session.flush();

			return CalEventHBMUtil.model(calEventHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent update(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws SystemException {
		Session session = null;

		try {
			if (calEvent.isNew() || calEvent.isModified()) {
				session = openSession();

				if (calEvent.isNew()) {
					CalEventHBM calEventHBM = new CalEventHBM();
					calEventHBM.setEventId(calEvent.getEventId());
					calEventHBM.setGroupId(calEvent.getGroupId());
					calEventHBM.setCompanyId(calEvent.getCompanyId());
					calEventHBM.setUserId(calEvent.getUserId());
					calEventHBM.setUserName(calEvent.getUserName());
					calEventHBM.setCreateDate(calEvent.getCreateDate());
					calEventHBM.setModifiedDate(calEvent.getModifiedDate());
					calEventHBM.setTitle(calEvent.getTitle());
					calEventHBM.setDescription(calEvent.getDescription());
					calEventHBM.setStartDate(calEvent.getStartDate());
					calEventHBM.setEndDate(calEvent.getEndDate());
					calEventHBM.setDurationHour(calEvent.getDurationHour());
					calEventHBM.setDurationMinute(calEvent.getDurationMinute());
					calEventHBM.setAllDay(calEvent.getAllDay());
					calEventHBM.setTimeZoneSensitive(calEvent.getTimeZoneSensitive());
					calEventHBM.setType(calEvent.getType());
					calEventHBM.setRepeating(calEvent.getRepeating());
					calEventHBM.setRecurrence(calEvent.getRecurrence());
					calEventHBM.setRemindBy(calEvent.getRemindBy());
					calEventHBM.setFirstReminder(calEvent.getFirstReminder());
					calEventHBM.setSecondReminder(calEvent.getSecondReminder());
					session.save(calEventHBM);
					session.flush();
				}
				else {
					CalEventHBM calEventHBM = (CalEventHBM)session.get(CalEventHBM.class,
							calEvent.getPrimaryKey());

					if (calEventHBM != null) {
						calEventHBM.setGroupId(calEvent.getGroupId());
						calEventHBM.setCompanyId(calEvent.getCompanyId());
						calEventHBM.setUserId(calEvent.getUserId());
						calEventHBM.setUserName(calEvent.getUserName());
						calEventHBM.setCreateDate(calEvent.getCreateDate());
						calEventHBM.setModifiedDate(calEvent.getModifiedDate());
						calEventHBM.setTitle(calEvent.getTitle());
						calEventHBM.setDescription(calEvent.getDescription());
						calEventHBM.setStartDate(calEvent.getStartDate());
						calEventHBM.setEndDate(calEvent.getEndDate());
						calEventHBM.setDurationHour(calEvent.getDurationHour());
						calEventHBM.setDurationMinute(calEvent.getDurationMinute());
						calEventHBM.setAllDay(calEvent.getAllDay());
						calEventHBM.setTimeZoneSensitive(calEvent.getTimeZoneSensitive());
						calEventHBM.setType(calEvent.getType());
						calEventHBM.setRepeating(calEvent.getRepeating());
						calEventHBM.setRecurrence(calEvent.getRecurrence());
						calEventHBM.setRemindBy(calEvent.getRemindBy());
						calEventHBM.setFirstReminder(calEvent.getFirstReminder());
						calEventHBM.setSecondReminder(calEvent.getSecondReminder());
						session.flush();
					}
					else {
						calEventHBM = new CalEventHBM();
						calEventHBM.setEventId(calEvent.getEventId());
						calEventHBM.setGroupId(calEvent.getGroupId());
						calEventHBM.setCompanyId(calEvent.getCompanyId());
						calEventHBM.setUserId(calEvent.getUserId());
						calEventHBM.setUserName(calEvent.getUserName());
						calEventHBM.setCreateDate(calEvent.getCreateDate());
						calEventHBM.setModifiedDate(calEvent.getModifiedDate());
						calEventHBM.setTitle(calEvent.getTitle());
						calEventHBM.setDescription(calEvent.getDescription());
						calEventHBM.setStartDate(calEvent.getStartDate());
						calEventHBM.setEndDate(calEvent.getEndDate());
						calEventHBM.setDurationHour(calEvent.getDurationHour());
						calEventHBM.setDurationMinute(calEvent.getDurationMinute());
						calEventHBM.setAllDay(calEvent.getAllDay());
						calEventHBM.setTimeZoneSensitive(calEvent.getTimeZoneSensitive());
						calEventHBM.setType(calEvent.getType());
						calEventHBM.setRepeating(calEvent.getRepeating());
						calEventHBM.setRecurrence(calEvent.getRecurrence());
						calEventHBM.setRemindBy(calEvent.getRemindBy());
						calEventHBM.setFirstReminder(calEvent.getFirstReminder());
						calEventHBM.setSecondReminder(calEvent.getSecondReminder());
						session.save(calEventHBM);
						session.flush();
					}
				}

				calEvent.setNew(false);
				calEvent.setModified(false);
			}

			return calEvent;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		String eventId) throws NoSuchEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CalEventHBM calEventHBM = (CalEventHBM)session.get(CalEventHBM.class,
					eventId);

			if (calEventHBM == null) {
				_log.warn("No CalEvent exists with the primary key " +
					eventId.toString());
				throw new NoSuchEventException(
					"No CalEvent exists with the primary key " +
					eventId.toString());
			}

			return CalEventHBMUtil.model(calEventHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		String eventId, String groupId, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		com.liferay.portlet.calendar.model.CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					calEvent, CalEventHBMUtil.getInstance());
			com.liferay.portlet.calendar.model.CalEvent[] array = new com.liferay.portlet.calendar.model.CalEvent[3];
			array[0] = (com.liferay.portlet.calendar.model.CalEvent)objArray[0];
			array[1] = (com.liferay.portlet.calendar.model.CalEvent)objArray[1];
			array[2] = (com.liferay.portlet.calendar.model.CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_T(String groupId, String type)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (type == null) {
				query.append("type_ is null");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_T(String groupId, String type, int begin, int end)
		throws SystemException {
		return findByG_T(groupId, type, begin, end, null);
	}

	public List findByG_T(String groupId, String type, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (type == null) {
				query.append("type_ is null");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (type != null) {
				q.setString(queryPos++, type);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		String groupId, String type, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		List list = findByG_T(groupId, type, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "type=";
			msg += type;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		String groupId, String type, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		int count = countByG_T(groupId, type);
		List list = findByG_T(groupId, type, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "type=";
			msg += type;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		String eventId, String groupId, String type, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		com.liferay.portlet.calendar.model.CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByG_T(groupId, type);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (type == null) {
				query.append("type_ is null");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					calEvent, CalEventHBMUtil.getInstance());
			com.liferay.portlet.calendar.model.CalEvent[] array = new com.liferay.portlet.calendar.model.CalEvent[3];
			array[0] = (com.liferay.portlet.calendar.model.CalEvent)objArray[0];
			array[1] = (com.liferay.portlet.calendar.model.CalEvent)objArray[1];
			array[2] = (com.liferay.portlet.calendar.model.CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_R(String groupId, boolean repeating)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, repeating);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_R(String groupId, boolean repeating, int begin, int end)
		throws SystemException {
		return findByG_R(groupId, repeating, begin, end, null);
	}

	public List findByG_R(String groupId, boolean repeating, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, repeating);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		String groupId, boolean repeating, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		List list = findByG_R(groupId, repeating, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "repeating=";
			msg += repeating;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		String groupId, boolean repeating, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		int count = countByG_R(groupId, repeating);
		List list = findByG_R(groupId, repeating, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No CalEvent exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "repeating=";
			msg += repeating;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEventException(msg);
		}
		else {
			return (com.liferay.portlet.calendar.model.CalEvent)list.get(0);
		}
	}

	public com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		String eventId, String groupId, boolean repeating, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		com.liferay.portlet.calendar.model.CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByG_R(groupId, repeating);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, repeating);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					calEvent, CalEventHBMUtil.getInstance());
			com.liferay.portlet.calendar.model.CalEvent[] array = new com.liferay.portlet.calendar.model.CalEvent[3];
			array[0] = (com.liferay.portlet.calendar.model.CalEvent)objArray[0];
			array[1] = (com.liferay.portlet.calendar.model.CalEvent)objArray[1];
			array[2] = (com.liferay.portlet.calendar.model.CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				list.add(CalEventHBMUtil.model(calEventHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				session.delete(calEventHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByG_T(String groupId, String type)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (type == null) {
				query.append("type_ is null");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				session.delete(calEventHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByG_R(String groupId, boolean repeating)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, repeating);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				CalEventHBM calEventHBM = (CalEventHBM)itr.next();
				session.delete(calEventHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_T(String groupId, String type)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (type == null) {
				query.append("type_ is null");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_R(String groupId, boolean repeating)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM CalEvent IN CLASS com.liferay.portlet.calendar.service.persistence.CalEventHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, repeating);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactory.getLog(CalEventPersistence.class);
}