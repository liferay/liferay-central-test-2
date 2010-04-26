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

import com.liferay.portlet.social.NoSuchEquityLogException;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialEquityLogImpl;
import com.liferay.portlet.social.model.impl.SocialEquityLogModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquityLogPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogPersistence
 * @see       SocialEquityLogUtil
 * @generated
 */
public class SocialEquityLogPersistenceImpl extends BasePersistenceImpl<SocialEquityLog>
	implements SocialEquityLogPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityLogImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquityLog socialEquityLog) {
		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);
	}

	public void cacheResult(List<SocialEquityLog> socialEquityLogs) {
		for (SocialEquityLog socialEquityLog : socialEquityLogs) {
			if (EntityCacheUtil.getResult(
						SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityLogImpl.class,
						socialEquityLog.getPrimaryKey(), this) == null) {
				cacheResult(socialEquityLog);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialEquityLogImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquityLogImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialEquityLog create(long equityLogId) {
		SocialEquityLog socialEquityLog = new SocialEquityLogImpl();

		socialEquityLog.setNew(true);
		socialEquityLog.setPrimaryKey(equityLogId);

		return socialEquityLog;
	}

	public SocialEquityLog remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquityLog remove(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityLog socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
					new Long(equityLogId));

			if (socialEquityLog == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
				}

				throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityLogId);
			}

			return remove(socialEquityLog);
		}
		catch (NoSuchEquityLogException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialEquityLog remove(SocialEquityLog socialEquityLog)
		throws SystemException {
		for (ModelListener<SocialEquityLog> listener : listeners) {
			listener.onBeforeRemove(socialEquityLog);
		}

		socialEquityLog = removeImpl(socialEquityLog);

		for (ModelListener<SocialEquityLog> listener : listeners) {
			listener.onAfterRemove(socialEquityLog);
		}

		return socialEquityLog;
	}

	protected SocialEquityLog removeImpl(SocialEquityLog socialEquityLog)
		throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		Session session = null;

		try {
			session = openSession();

			if (socialEquityLog.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquityLogImpl.class,
						socialEquityLog.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquityLog);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey());

		return socialEquityLog;
	}

	public SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge) throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityLog, merge);

			socialEquityLog.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);

		return socialEquityLog;
	}

	protected SocialEquityLog toUnwrappedModel(SocialEquityLog socialEquityLog) {
		if (socialEquityLog instanceof SocialEquityLogImpl) {
			return socialEquityLog;
		}

		SocialEquityLogImpl socialEquityLogImpl = new SocialEquityLogImpl();

		socialEquityLogImpl.setNew(socialEquityLog.isNew());
		socialEquityLogImpl.setPrimaryKey(socialEquityLog.getPrimaryKey());

		socialEquityLogImpl.setEquityLogId(socialEquityLog.getEquityLogId());
		socialEquityLogImpl.setGroupId(socialEquityLog.getGroupId());
		socialEquityLogImpl.setCompanyId(socialEquityLog.getCompanyId());
		socialEquityLogImpl.setUserId(socialEquityLog.getUserId());
		socialEquityLogImpl.setAssetEntryId(socialEquityLog.getAssetEntryId());
		socialEquityLogImpl.setActionId(socialEquityLog.getActionId());
		socialEquityLogImpl.setActionDate(socialEquityLog.getActionDate());
		socialEquityLogImpl.setType(socialEquityLog.getType());
		socialEquityLogImpl.setValue(socialEquityLog.getValue());
		socialEquityLogImpl.setValidity(socialEquityLog.getValidity());

		return socialEquityLogImpl;
	}

	public SocialEquityLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityLog findByPrimaryKey(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = fetchByPrimaryKey(equityLogId);

		if (socialEquityLog == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
			}

			throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityLogId);
		}

		return socialEquityLog;
	}

	public SocialEquityLog fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityLog fetchByPrimaryKey(long equityLogId)
		throws SystemException {
		SocialEquityLog socialEquityLog = (SocialEquityLog)EntityCacheUtil.getResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityLogImpl.class, equityLogId, this);

		if (socialEquityLog == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
						new Long(equityLogId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquityLog != null) {
					cacheResult(socialEquityLog);
				}

				closeSession(session);
			}
		}

		return socialEquityLog;
	}

	public List<SocialEquityLog> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquityLog> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYLOG);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALEQUITYLOG;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeAll() throws SystemException {
		for (SocialEquityLog socialEquityLog : findAll()) {
			remove(socialEquityLog);
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYLOG);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityLog")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityLog>> listenersList = new ArrayList<ModelListener<SocialEquityLog>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityLog>)Class.forName(
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
	private static final String _SQL_SELECT_SOCIALEQUITYLOG = "SELECT socialEquityLog FROM SocialEquityLog socialEquityLog";
	private static final String _SQL_COUNT_SOCIALEQUITYLOG = "SELECT COUNT(socialEquityLog) FROM SocialEquityLog socialEquityLog";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityLog.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityLog exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(SocialEquityLogPersistenceImpl.class);
}