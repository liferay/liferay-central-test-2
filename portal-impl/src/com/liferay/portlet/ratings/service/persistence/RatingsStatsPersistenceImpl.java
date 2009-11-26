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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="RatingsStatsPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStatsPersistence
 * @see       RatingsStatsUtil
 * @generated
 */
public class RatingsStatsPersistenceImpl extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = RatingsStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(RatingsStats ratingsStats) {
		EntityCacheUtil.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(ratingsStats.getClassNameId()),
				new Long(ratingsStats.getClassPK())
			}, ratingsStats);
	}

	public void cacheResult(List<RatingsStats> ratingsStatses) {
		for (RatingsStats ratingsStats : ratingsStatses) {
			if (EntityCacheUtil.getResult(
						RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
						RatingsStatsImpl.class, ratingsStats.getPrimaryKey(),
						this) == null) {
				cacheResult(ratingsStats);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(RatingsStatsImpl.class.getName());
		EntityCacheUtil.clearCache(RatingsStatsImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public RatingsStats create(long statsId) {
		RatingsStats ratingsStats = new RatingsStatsImpl();

		ratingsStats.setNew(true);
		ratingsStats.setPrimaryKey(statsId);

		return ratingsStats;
	}

	public RatingsStats remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsStats remove(RatingsStats ratingsStats)
		throws SystemException {
		for (ModelListener<RatingsStats> listener : listeners) {
			listener.onBeforeRemove(ratingsStats);
		}

		ratingsStats = removeImpl(ratingsStats);

		for (ModelListener<RatingsStats> listener : listeners) {
			listener.onAfterRemove(ratingsStats);
		}

		return ratingsStats;
	}

	protected RatingsStats removeImpl(RatingsStats ratingsStats)
		throws SystemException {
		ratingsStats = toUnwrappedModel(ratingsStats);

		Session session = null;

		try {
			session = openSession();

			if (ratingsStats.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(RatingsStatsImpl.class,
						ratingsStats.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(ratingsStats);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RatingsStatsModelImpl ratingsStatsModelImpl = (RatingsStatsModelImpl)ratingsStats;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(ratingsStatsModelImpl.getOriginalClassNameId()),
				new Long(ratingsStatsModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

		return ratingsStats;
	}

	public RatingsStats updateImpl(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge) throws SystemException {
		ratingsStats = toUnwrappedModel(ratingsStats);

		boolean isNew = ratingsStats.isNew();

		RatingsStatsModelImpl ratingsStatsModelImpl = (RatingsStatsModelImpl)ratingsStats;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ratingsStats, merge);

			ratingsStats.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		if (!isNew &&
				((ratingsStats.getClassNameId() != ratingsStatsModelImpl.getOriginalClassNameId()) ||
				(ratingsStats.getClassPK() != ratingsStatsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(ratingsStatsModelImpl.getOriginalClassNameId()),
					new Long(ratingsStatsModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((ratingsStats.getClassNameId() != ratingsStatsModelImpl.getOriginalClassNameId()) ||
				(ratingsStats.getClassPK() != ratingsStatsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(ratingsStats.getClassNameId()),
					new Long(ratingsStats.getClassPK())
				}, ratingsStats);
		}

		return ratingsStats;
	}

	protected RatingsStats toUnwrappedModel(RatingsStats ratingsStats) {
		if (ratingsStats instanceof RatingsStatsImpl) {
			return ratingsStats;
		}

		RatingsStatsImpl ratingsStatsImpl = new RatingsStatsImpl();

		ratingsStatsImpl.setNew(ratingsStats.isNew());
		ratingsStatsImpl.setPrimaryKey(ratingsStats.getPrimaryKey());

		ratingsStatsImpl.setStatsId(ratingsStats.getStatsId());
		ratingsStatsImpl.setClassNameId(ratingsStats.getClassNameId());
		ratingsStatsImpl.setClassPK(ratingsStats.getClassPK());
		ratingsStatsImpl.setTotalEntries(ratingsStats.getTotalEntries());
		ratingsStatsImpl.setTotalScore(ratingsStats.getTotalScore());
		ratingsStatsImpl.setAverageScore(ratingsStats.getAverageScore());

		return ratingsStatsImpl;
	}

	public RatingsStats findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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

	public RatingsStats fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public RatingsStats fetchByPrimaryKey(long statsId)
		throws SystemException {
		RatingsStats ratingsStats = (RatingsStats)EntityCacheUtil.getResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
				RatingsStatsImpl.class, statsId, this);

		if (ratingsStats == null) {
			Session session = null;

			try {
				session = openSession();

				ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
						new Long(statsId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ratingsStats != null) {
					cacheResult(ratingsStats);
				}

				closeSession(session);
			}
		}

		return ratingsStats;
	}

	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = fetchByC_C(classNameId, classPK);

		if (ratingsStats == null) {
			StringBundler msg = new StringBundler();

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
		return fetchByC_C(classNameId, classPK, true);
	}

	public RatingsStats fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_RATINGSSTATS_WHERE);

				query.append("ratingsStats.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsStats.classPK = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsStats> list = q.list();

				result = list;

				RatingsStats ratingsStats = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					ratingsStats = list.get(0);

					cacheResult(ratingsStats);

					if ((ratingsStats.getClassNameId() != classNameId) ||
							(ratingsStats.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, ratingsStats);
					}
				}

				return ratingsStats;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<RatingsStats>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (RatingsStats)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<RatingsStats> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<RatingsStats> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<RatingsStats> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<RatingsStats> list = (List<RatingsStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_RATINGSSTATS);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("ratingsStats.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<RatingsStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_RATINGSSTATS_WHERE);

				query.append("ratingsStats.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsStats.classPK = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RATINGSSTATS);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsStats")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RatingsStats>> listenersList = new ArrayList<ModelListener<RatingsStats>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RatingsStats>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_RATINGSSTATS = "SELECT ratingsStats FROM RatingsStats ratingsStats";
	private static final String _SQL_SELECT_RATINGSSTATS_WHERE = "SELECT ratingsStats FROM RatingsStats ratingsStats WHERE ";
	private static final String _SQL_COUNT_RATINGSSTATS = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats";
	private static final String _SQL_COUNT_RATINGSSTATS_WHERE = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats WHERE ";
	private static Log _log = LogFactoryUtil.getLog(RatingsStatsPersistenceImpl.class);
}