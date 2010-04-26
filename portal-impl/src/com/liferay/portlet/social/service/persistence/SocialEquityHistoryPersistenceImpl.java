/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquityHistoryException;
import com.liferay.portlet.social.model.SocialEquityHistory;
import com.liferay.portlet.social.model.impl.SocialEquityHistoryImpl;
import com.liferay.portlet.social.model.impl.SocialEquityHistoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquityHistoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryPersistence
 * @see       SocialEquityHistoryUtil
 * @generated
 */
public class SocialEquityHistoryPersistenceImpl extends BasePersistenceImpl<SocialEquityHistory>
	implements SocialEquityHistoryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityHistoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquityHistory socialEquityHistory) {
		EntityCacheUtil.putResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey(),
			socialEquityHistory);
	}

	public void cacheResult(List<SocialEquityHistory> socialEquityHistories) {
		for (SocialEquityHistory socialEquityHistory : socialEquityHistories) {
			if (EntityCacheUtil.getResult(
						SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityHistoryImpl.class,
						socialEquityHistory.getPrimaryKey(), this) == null) {
				cacheResult(socialEquityHistory);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialEquityHistoryImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquityHistoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialEquityHistory create(long equityHistoryId) {
		SocialEquityHistory socialEquityHistory = new SocialEquityHistoryImpl();

		socialEquityHistory.setNew(true);
		socialEquityHistory.setPrimaryKey(equityHistoryId);

		return socialEquityHistory;
	}

	public SocialEquityHistory remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquityHistory remove(long equityHistoryId)
		throws NoSuchEquityHistoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityHistory socialEquityHistory = (SocialEquityHistory)session.get(SocialEquityHistoryImpl.class,
					new Long(equityHistoryId));

			if (socialEquityHistory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equityHistoryId);
				}

				throw new NoSuchEquityHistoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityHistoryId);
			}

			return remove(socialEquityHistory);
		}
		catch (NoSuchEquityHistoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialEquityHistory remove(SocialEquityHistory socialEquityHistory)
		throws SystemException {
		for (ModelListener<SocialEquityHistory> listener : listeners) {
			listener.onBeforeRemove(socialEquityHistory);
		}

		socialEquityHistory = removeImpl(socialEquityHistory);

		for (ModelListener<SocialEquityHistory> listener : listeners) {
			listener.onAfterRemove(socialEquityHistory);
		}

		return socialEquityHistory;
	}

	protected SocialEquityHistory removeImpl(
		SocialEquityHistory socialEquityHistory) throws SystemException {
		socialEquityHistory = toUnwrappedModel(socialEquityHistory);

		Session session = null;

		try {
			session = openSession();

			if (socialEquityHistory.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquityHistoryImpl.class,
						socialEquityHistory.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquityHistory);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey());

		return socialEquityHistory;
	}

	public SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge) throws SystemException {
		socialEquityHistory = toUnwrappedModel(socialEquityHistory);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityHistory, merge);

			socialEquityHistory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey(),
			socialEquityHistory);

		return socialEquityHistory;
	}

	protected SocialEquityHistory toUnwrappedModel(
		SocialEquityHistory socialEquityHistory) {
		if (socialEquityHistory instanceof SocialEquityHistoryImpl) {
			return socialEquityHistory;
		}

		SocialEquityHistoryImpl socialEquityHistoryImpl = new SocialEquityHistoryImpl();

		socialEquityHistoryImpl.setNew(socialEquityHistory.isNew());
		socialEquityHistoryImpl.setPrimaryKey(socialEquityHistory.getPrimaryKey());

		socialEquityHistoryImpl.setEquityHistoryId(socialEquityHistory.getEquityHistoryId());
		socialEquityHistoryImpl.setGroupId(socialEquityHistory.getGroupId());
		socialEquityHistoryImpl.setCompanyId(socialEquityHistory.getCompanyId());
		socialEquityHistoryImpl.setUserId(socialEquityHistory.getUserId());
		socialEquityHistoryImpl.setCreateDate(socialEquityHistory.getCreateDate());
		socialEquityHistoryImpl.setPersonalEquity(socialEquityHistory.getPersonalEquity());

		return socialEquityHistoryImpl;
	}

	public SocialEquityHistory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityHistory findByPrimaryKey(long equityHistoryId)
		throws NoSuchEquityHistoryException, SystemException {
		SocialEquityHistory socialEquityHistory = fetchByPrimaryKey(equityHistoryId);

		if (socialEquityHistory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityHistoryId);
			}

			throw new NoSuchEquityHistoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityHistoryId);
		}

		return socialEquityHistory;
	}

	public SocialEquityHistory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityHistory fetchByPrimaryKey(long equityHistoryId)
		throws SystemException {
		SocialEquityHistory socialEquityHistory = (SocialEquityHistory)EntityCacheUtil.getResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityHistoryImpl.class, equityHistoryId, this);

		if (socialEquityHistory == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquityHistory = (SocialEquityHistory)session.get(SocialEquityHistoryImpl.class,
						new Long(equityHistoryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquityHistory != null) {
					cacheResult(socialEquityHistory);
				}

				closeSession(session);
			}
		}

		return socialEquityHistory;
	}

	public List<SocialEquityHistory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityHistory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquityHistory> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityHistory> list = (List<SocialEquityHistory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_SOCIALEQUITYHISTORY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALEQUITYHISTORY;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityHistory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityHistory>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityHistory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeAll() throws SystemException {
		for (SocialEquityHistory socialEquityHistory : findAll()) {
			remove(socialEquityHistory);
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYHISTORY);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityHistory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityHistory>> listenersList = new ArrayList<ModelListener<SocialEquityHistory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityHistory>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYHISTORY = "SELECT socialEquityHistory FROM SocialEquityHistory socialEquityHistory";
	private static final String _SQL_COUNT_SOCIALEQUITYHISTORY = "SELECT COUNT(socialEquityHistory) FROM SocialEquityHistory socialEquityHistory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityHistory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityHistory exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(SocialEquityHistoryPersistenceImpl.class);
}