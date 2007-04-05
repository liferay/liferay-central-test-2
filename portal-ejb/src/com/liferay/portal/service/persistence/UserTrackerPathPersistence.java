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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserTrackerPathException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserTrackerPathPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserTrackerPathPersistence extends BasePersistence {
	public UserTrackerPath create(String userTrackerPathId) {
		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();
		userTrackerPath.setNew(true);
		userTrackerPath.setPrimaryKey(userTrackerPathId);

		return userTrackerPath;
	}

	public UserTrackerPath remove(String userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPath userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
					userTrackerPathId);

			if (userTrackerPath == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserTrackerPath exists with the primary key " +
						userTrackerPathId);
				}

				throw new NoSuchUserTrackerPathException(
					"No UserTrackerPath exists with the primary key " +
					userTrackerPathId);
			}

			return remove(userTrackerPath);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTrackerPath remove(UserTrackerPath userTrackerPath)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(userTrackerPath);
			session.flush();

			return userTrackerPath;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws SystemException {
		return update(userTrackerPath, false);
	}

	public com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(userTrackerPath);
			}
			else {
				if (userTrackerPath.isNew()) {
					session.save(userTrackerPath);
				}
			}

			session.flush();
			userTrackerPath.setNew(false);

			return userTrackerPath;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTrackerPath findByPrimaryKey(String userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(userTrackerPathId);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserTrackerPath exists with the primary key " +
					userTrackerPathId);
			}

			throw new NoSuchUserTrackerPathException(
				"No UserTrackerPath exists with the primary key " +
				userTrackerPathId);
		}

		return userTrackerPath;
	}

	public UserTrackerPath fetchByPrimaryKey(String userTrackerPathId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserTrackerPath)session.get(UserTrackerPathImpl.class,
				userTrackerPathId);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserTrackerId(String userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.UserTrackerPath WHERE ");

			if (userTrackerId == null) {
				query.append("userTrackerId IS NULL");
			}
			else {
				query.append("userTrackerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userTrackerId != null) {
				q.setString(queryPos++, userTrackerId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserTrackerId(String userTrackerId, int begin, int end)
		throws SystemException {
		return findByUserTrackerId(userTrackerId, begin, end, null);
	}

	public List findByUserTrackerId(String userTrackerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.UserTrackerPath WHERE ");

			if (userTrackerId == null) {
				query.append("userTrackerId IS NULL");
			}
			else {
				query.append("userTrackerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userTrackerId != null) {
				q.setString(queryPos++, userTrackerId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTrackerPath findByUserTrackerId_First(String userTrackerId,
		OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		List list = findByUserTrackerId(userTrackerId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No UserTrackerPath exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userTrackerId=");
			msg.append(userTrackerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return (UserTrackerPath)list.get(0);
		}
	}

	public UserTrackerPath findByUserTrackerId_Last(String userTrackerId,
		OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		int count = countByUserTrackerId(userTrackerId);
		List list = findByUserTrackerId(userTrackerId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No UserTrackerPath exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userTrackerId=");
			msg.append(userTrackerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return (UserTrackerPath)list.get(0);
		}
	}

	public UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		String userTrackerPathId, String userTrackerId, OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = findByPrimaryKey(userTrackerPathId);
		int count = countByUserTrackerId(userTrackerId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.UserTrackerPath WHERE ");

			if (userTrackerId == null) {
				query.append("userTrackerId IS NULL");
			}
			else {
				query.append("userTrackerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userTrackerId != null) {
				q.setString(queryPos++, userTrackerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTrackerPath);
			UserTrackerPath[] array = new UserTrackerPathImpl[3];
			array[0] = (UserTrackerPath)objArray[0];
			array[1] = (UserTrackerPath)objArray[1];
			array[2] = (UserTrackerPath)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.UserTrackerPath ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByUserTrackerId(String userTrackerId)
		throws SystemException {
		Iterator itr = findByUserTrackerId(userTrackerId).iterator();

		while (itr.hasNext()) {
			UserTrackerPath userTrackerPath = (UserTrackerPath)itr.next();
			remove(userTrackerPath);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((UserTrackerPath)itr.next());
		}
	}

	public int countByUserTrackerId(String userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.UserTrackerPath WHERE ");

			if (userTrackerId == null) {
				query.append("userTrackerId IS NULL");
			}
			else {
				query.append("userTrackerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userTrackerId != null) {
				q.setString(queryPos++, userTrackerId);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.UserTrackerPath");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(UserTrackerPathPersistence.class);
}