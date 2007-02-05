/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CalEventPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CalEventPersistence extends BasePersistence {
	public CalEvent create(String eventId) {
		CalEvent calEvent = new CalEventImpl();
		calEvent.setNew(true);
		calEvent.setPrimaryKey(eventId);

		return calEvent;
	}

	public CalEvent remove(String eventId)
		throws NoSuchEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CalEvent calEvent = (CalEvent)session.get(CalEventImpl.class,
					eventId);

			if (calEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No CalEvent exists with the primary key " +
						eventId);
				}

				throw new NoSuchEventException(
					"No CalEvent exists with the primary key " + eventId);
			}

			return remove(calEvent);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public CalEvent remove(CalEvent calEvent) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(calEvent);
			session.flush();

			return calEvent;
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
		return update(calEvent, false);
	}

	public com.liferay.portlet.calendar.model.CalEvent update(
		com.liferay.portlet.calendar.model.CalEvent calEvent,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(calEvent);
			}
			else {
				if (calEvent.isNew()) {
					session.save(calEvent);
				}
			}

			session.flush();
			calEvent.setNew(false);

			return calEvent;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public CalEvent findByPrimaryKey(String eventId)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByPrimaryKey(eventId);

		if (calEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No CalEvent exists with the primary key " + eventId);
			}

			throw new NoSuchEventException(
				"No CalEvent exists with the primary key " + eventId);
		}

		return calEvent;
	}

	public CalEvent fetchByPrimaryKey(String eventId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (CalEvent)session.get(CalEventImpl.class, eventId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public CalEvent findByGroupId_First(long groupId, OrderByComparator obc)
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent findByGroupId_Last(long groupId, OrderByComparator obc)
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent[] findByGroupId_PrevAndNext(String eventId, long groupId,
		OrderByComparator obc) throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, calEvent);
			CalEvent[] array = new CalEventImpl[3];
			array[0] = (CalEvent)objArray[0];
			array[1] = (CalEvent)objArray[1];
			array[2] = (CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_T(long groupId, String type) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (type != null) {
				q.setString(queryPos++, type);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_T(long groupId, String type, int begin, int end)
		throws SystemException {
		return findByG_T(groupId, type, begin, end, null);
	}

	public List findByG_T(long groupId, String type, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (type == null) {
				query.append("type_ IS NULL");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (type != null) {
				q.setString(queryPos++, type);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public CalEvent findByG_T_First(long groupId, String type,
		OrderByComparator obc) throws NoSuchEventException, SystemException {
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent findByG_T_Last(long groupId, String type,
		OrderByComparator obc) throws NoSuchEventException, SystemException {
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent[] findByG_T_PrevAndNext(String eventId, long groupId,
		String type, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByG_T(groupId, type);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (type == null) {
				query.append("type_ IS NULL");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, calEvent);
			CalEvent[] array = new CalEventImpl[3];
			array[0] = (CalEvent)objArray[0];
			array[1] = (CalEvent)objArray[1];
			array[2] = (CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_R(long groupId, boolean repeating)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("startDate ASC").append(", ");
			query.append("title ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, repeating);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_R(long groupId, boolean repeating, int begin, int end)
		throws SystemException {
		return findByG_R(groupId, repeating, begin, end, null);
	}

	public List findByG_R(long groupId, boolean repeating, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, repeating);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public CalEvent findByG_R_First(long groupId, boolean repeating,
		OrderByComparator obc) throws NoSuchEventException, SystemException {
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent findByG_R_Last(long groupId, boolean repeating,
		OrderByComparator obc) throws NoSuchEventException, SystemException {
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
			return (CalEvent)list.get(0);
		}
	}

	public CalEvent[] findByG_R_PrevAndNext(String eventId, long groupId,
		boolean repeating, OrderByComparator obc)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);
		int count = countByG_R(groupId, repeating);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, repeating);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, calEvent);
			CalEvent[] array = new CalEventImpl[3];
			array[0] = (CalEvent)objArray[0];
			array[1] = (CalEvent)objArray[1];
			array[2] = (CalEvent)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.calendar.model.CalEvent ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("startDate ASC").append(", ");
				query.append("title ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			CalEvent calEvent = (CalEvent)itr.next();
			remove(calEvent);
		}
	}

	public void removeByG_T(long groupId, String type)
		throws SystemException {
		Iterator itr = findByG_T(groupId, type).iterator();

		while (itr.hasNext()) {
			CalEvent calEvent = (CalEvent)itr.next();
			remove(calEvent);
		}
	}

	public void removeByG_R(long groupId, boolean repeating)
		throws SystemException {
		Iterator itr = findByG_R(groupId, repeating).iterator();

		while (itr.hasNext()) {
			CalEvent calEvent = (CalEvent)itr.next();
			remove(calEvent);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((CalEvent)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

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

	public int countByG_T(long groupId, String type) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

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

	public int countByG_R(long groupId, boolean repeating)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.calendar.model.CalEvent WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("repeating = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.calendar.model.CalEvent");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(CalEventPersistence.class);
}