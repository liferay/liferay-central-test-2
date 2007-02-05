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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;

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
 * <a href="RatingsStatsPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsStatsPersistence extends BasePersistence {
	public RatingsStats create(long statsId) {
		RatingsStats ratingsStats = new RatingsStatsImpl();
		ratingsStats.setNew(true);
		ratingsStats.setPrimaryKey(statsId);

		return ratingsStats;
	}

	public RatingsStats remove(long statsId)
		throws NoSuchStatsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RatingsStats ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
					new Long(statsId));

			if (ratingsStats == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No RatingsStats exists with the primary key " +
						statsId);
				}

				throw new NoSuchStatsException(
					"No RatingsStats exists with the primary key " + statsId);
			}

			return remove(ratingsStats);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats remove(RatingsStats ratingsStats)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(ratingsStats);
			session.flush();

			return ratingsStats;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws SystemException {
		return update(ratingsStats, false);
	}

	public com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(ratingsStats);
			}
			else {
				if (ratingsStats.isNew()) {
					session.save(ratingsStats);
				}
			}

			session.flush();
			ratingsStats.setNew(false);

			return ratingsStats;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats findByPrimaryKey(long statsId)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = fetchByPrimaryKey(statsId);

		if (ratingsStats == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No RatingsStats exists with the primary key " +
					statsId);
			}

			throw new NoSuchStatsException(
				"No RatingsStats exists with the primary key " + statsId);
		}

		return ratingsStats;
	}

	public RatingsStats fetchByPrimaryKey(long statsId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (RatingsStats)session.get(RatingsStatsImpl.class,
				new Long(statsId));
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats findByC_C(String className, String classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = fetchByC_C(className, classPK);

		if (ratingsStats == null) {
			String msg = "No RatingsStats exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchStatsException(msg);
		}

		return ratingsStats;
	}

	public RatingsStats fetchByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.ratings.model.RatingsStats WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			RatingsStats ratingsStats = (RatingsStats)list.get(0);

			return ratingsStats;
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
			query.append("FROM com.liferay.portlet.ratings.model.RatingsStats ");

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

	public void removeByC_C(String className, String classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = findByC_C(className, classPK);
		remove(ratingsStats);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((RatingsStats)itr.next());
		}
	}

	public int countByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.ratings.model.RatingsStats WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
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
			query.append("FROM com.liferay.portlet.ratings.model.RatingsStats");

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

	private static Log _log = LogFactory.getLog(RatingsStatsPersistence.class);
}