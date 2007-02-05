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

import com.liferay.portal.NoSuchUserIdMapperException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.dao.DynamicQuery;
import com.liferay.util.dao.DynamicQueryInitializer;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserIdMapperPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserIdMapperPersistence extends BasePersistence {
	public UserIdMapper create(UserIdMapperPK userIdMapperPK) {
		UserIdMapper userIdMapper = new UserIdMapperImpl();
		userIdMapper.setNew(true);
		userIdMapper.setPrimaryKey(userIdMapperPK);

		return userIdMapper;
	}

	public UserIdMapper remove(UserIdMapperPK userIdMapperPK)
		throws NoSuchUserIdMapperException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserIdMapper userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
					userIdMapperPK);

			if (userIdMapper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserIdMapper exists with the primary key " +
						userIdMapperPK);
				}

				throw new NoSuchUserIdMapperException(
					"No UserIdMapper exists with the primary key " +
					userIdMapperPK);
			}

			return remove(userIdMapper);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper remove(UserIdMapper userIdMapper)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(userIdMapper);
			session.flush();

			return userIdMapper;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserIdMapper update(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws SystemException {
		return update(userIdMapper, false);
	}

	public com.liferay.portal.model.UserIdMapper update(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(userIdMapper);
			}
			else {
				if (userIdMapper.isNew()) {
					session.save(userIdMapper);
				}
			}

			session.flush();
			userIdMapper.setNew(false);

			return userIdMapper;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper findByPrimaryKey(UserIdMapperPK userIdMapperPK)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByPrimaryKey(userIdMapperPK);

		if (userIdMapper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserIdMapper exists with the primary key " +
					userIdMapperPK);
			}

			throw new NoSuchUserIdMapperException(
				"No UserIdMapper exists with the primary key " +
				userIdMapperPK);
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByPrimaryKey(UserIdMapperPK userIdMapperPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserIdMapper)session.get(UserIdMapperImpl.class,
				userIdMapperPK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.UserIdMapper WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.UserIdMapper WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public UserIdMapper findByUserId_First(String userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No UserIdMapper exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserIdMapperException(msg);
		}
		else {
			return (UserIdMapper)list.get(0);
		}
	}

	public UserIdMapper findByUserId_Last(String userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No UserIdMapper exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserIdMapperException(msg);
		}
		else {
			return (UserIdMapper)list.get(0);
		}
	}

	public UserIdMapper[] findByUserId_PrevAndNext(
		UserIdMapperPK userIdMapperPK, String userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByPrimaryKey(userIdMapperPK);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.UserIdMapper WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userIdMapper);
			UserIdMapper[] array = new UserIdMapperImpl[3];
			array[0] = (UserIdMapper)objArray[0];
			array[1] = (UserIdMapper)objArray[1];
			array[2] = (UserIdMapper)objArray[2];

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
			query.append("FROM com.liferay.portal.model.UserIdMapper ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

	public void removeByUserId(String userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			UserIdMapper userIdMapper = (UserIdMapper)itr.next();
			remove(userIdMapper);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((UserIdMapper)itr.next());
		}
	}

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.UserIdMapper WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.UserIdMapper");

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

	private static Log _log = LogFactory.getLog(UserIdMapperPersistence.class);
}