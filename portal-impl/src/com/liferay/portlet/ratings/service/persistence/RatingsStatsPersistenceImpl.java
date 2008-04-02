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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="RatingsStatsPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsStatsPersistenceImpl extends BasePersistence
	implements RatingsStatsPersistence {
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
		catch (NoSuchStatsException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats remove(RatingsStats ratingsStats)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(ratingsStats);
			}
		}

		ratingsStats = removeImpl(ratingsStats);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(ratingsStats);
			}
		}

		return ratingsStats;
	}

	protected RatingsStats removeImpl(RatingsStats ratingsStats)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(ratingsStats);

			session.flush();

			return ratingsStats;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(RatingsStats.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(RatingsStats ratingsStats, boolean merge)</code>.
	 */
	public RatingsStats update(RatingsStats ratingsStats)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(RatingsStats ratingsStats) method. Use update(RatingsStats ratingsStats, boolean merge) instead.");
		}

		return update(ratingsStats, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        ratingsStats the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when ratingsStats is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public RatingsStats update(RatingsStats ratingsStats, boolean merge)
		throws SystemException {
		boolean isNew = ratingsStats.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(ratingsStats);
				}
				else {
					listener.onBeforeUpdate(ratingsStats);
				}
			}
		}

		ratingsStats = updateImpl(ratingsStats, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(ratingsStats);
				}
				else {
					listener.onAfterUpdate(ratingsStats);
				}
			}
		}

		return ratingsStats;
	}

	public RatingsStats updateImpl(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(ratingsStats);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(RatingsStats.class.getName());
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = fetchByC_C(classNameId, classPK);

		if (ratingsStats == null) {
			StringMaker msg = new StringMaker();

			msg.append("No RatingsStats exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsException(msg.toString());
		}

		return ratingsStats;
	}

	public RatingsStats fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsStatsModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsStats.class.getName();
		String finderMethodName = "fetchByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.ratings.model.RatingsStats WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				List<RatingsStats> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<RatingsStats> list = (List<RatingsStats>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<RatingsStats> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
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

	public List<RatingsStats> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
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

	public List<RatingsStats> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<RatingsStats> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<RatingsStats> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsStatsModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsStats.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.ratings.model.RatingsStats ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<RatingsStats> list = (List<RatingsStats>)QueryUtil.list(q,
						getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<RatingsStats>)result;
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = findByC_C(classNameId, classPK);

		remove(ratingsStats);
	}

	public void removeAll() throws SystemException {
		for (RatingsStats ratingsStats : findAll()) {
			remove(ratingsStats);
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsStatsModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsStats.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.ratings.model.RatingsStats WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsStatsModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsStats.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.ratings.model.RatingsStats");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsStats")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(RatingsStatsPersistenceImpl.class);
	private ModelListener[] _listeners;
}