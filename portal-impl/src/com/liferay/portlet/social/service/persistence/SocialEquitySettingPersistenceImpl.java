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

import com.liferay.portlet.social.NoSuchEquitySettingException;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.impl.SocialEquitySettingImpl;
import com.liferay.portlet.social.model.impl.SocialEquitySettingModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquitySettingPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySettingPersistence
 * @see       SocialEquitySettingUtil
 * @generated
 */
public class SocialEquitySettingPersistenceImpl extends BasePersistenceImpl<SocialEquitySetting>
	implements SocialEquitySettingPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquitySettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquitySetting socialEquitySetting) {
		EntityCacheUtil.putResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey(),
			socialEquitySetting);
	}

	public void cacheResult(List<SocialEquitySetting> socialEquitySettings) {
		for (SocialEquitySetting socialEquitySetting : socialEquitySettings) {
			if (EntityCacheUtil.getResult(
						SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquitySettingImpl.class,
						socialEquitySetting.getPrimaryKey(), this) == null) {
				cacheResult(socialEquitySetting);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialEquitySettingImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquitySettingImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialEquitySetting create(long equitySettingId) {
		SocialEquitySetting socialEquitySetting = new SocialEquitySettingImpl();

		socialEquitySetting.setNew(true);
		socialEquitySetting.setPrimaryKey(equitySettingId);

		return socialEquitySetting;
	}

	public SocialEquitySetting remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquitySetting remove(long equitySettingId)
		throws NoSuchEquitySettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquitySetting socialEquitySetting = (SocialEquitySetting)session.get(SocialEquitySettingImpl.class,
					new Long(equitySettingId));

			if (socialEquitySetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equitySettingId);
				}

				throw new NoSuchEquitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equitySettingId);
			}

			return remove(socialEquitySetting);
		}
		catch (NoSuchEquitySettingException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialEquitySetting remove(SocialEquitySetting socialEquitySetting)
		throws SystemException {
		for (ModelListener<SocialEquitySetting> listener : listeners) {
			listener.onBeforeRemove(socialEquitySetting);
		}

		socialEquitySetting = removeImpl(socialEquitySetting);

		for (ModelListener<SocialEquitySetting> listener : listeners) {
			listener.onAfterRemove(socialEquitySetting);
		}

		return socialEquitySetting;
	}

	protected SocialEquitySetting removeImpl(
		SocialEquitySetting socialEquitySetting) throws SystemException {
		socialEquitySetting = toUnwrappedModel(socialEquitySetting);

		Session session = null;

		try {
			session = openSession();

			if (socialEquitySetting.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquitySettingImpl.class,
						socialEquitySetting.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquitySetting);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey());

		return socialEquitySetting;
	}

	public SocialEquitySetting updateImpl(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge) throws SystemException {
		socialEquitySetting = toUnwrappedModel(socialEquitySetting);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquitySetting, merge);

			socialEquitySetting.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey(),
			socialEquitySetting);

		return socialEquitySetting;
	}

	protected SocialEquitySetting toUnwrappedModel(
		SocialEquitySetting socialEquitySetting) {
		if (socialEquitySetting instanceof SocialEquitySettingImpl) {
			return socialEquitySetting;
		}

		SocialEquitySettingImpl socialEquitySettingImpl = new SocialEquitySettingImpl();

		socialEquitySettingImpl.setNew(socialEquitySetting.isNew());
		socialEquitySettingImpl.setPrimaryKey(socialEquitySetting.getPrimaryKey());

		socialEquitySettingImpl.setEquitySettingId(socialEquitySetting.getEquitySettingId());
		socialEquitySettingImpl.setGroupId(socialEquitySetting.getGroupId());
		socialEquitySettingImpl.setCompanyId(socialEquitySetting.getCompanyId());
		socialEquitySettingImpl.setClassNameId(socialEquitySetting.getClassNameId());
		socialEquitySettingImpl.setActionId(socialEquitySetting.getActionId());
		socialEquitySettingImpl.setType(socialEquitySetting.getType());
		socialEquitySettingImpl.setValue(socialEquitySetting.getValue());
		socialEquitySettingImpl.setValidity(socialEquitySetting.getValidity());
		socialEquitySettingImpl.setActive(socialEquitySetting.isActive());

		return socialEquitySettingImpl;
	}

	public SocialEquitySetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquitySetting findByPrimaryKey(long equitySettingId)
		throws NoSuchEquitySettingException, SystemException {
		SocialEquitySetting socialEquitySetting = fetchByPrimaryKey(equitySettingId);

		if (socialEquitySetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equitySettingId);
			}

			throw new NoSuchEquitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equitySettingId);
		}

		return socialEquitySetting;
	}

	public SocialEquitySetting fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquitySetting fetchByPrimaryKey(long equitySettingId)
		throws SystemException {
		SocialEquitySetting socialEquitySetting = (SocialEquitySetting)EntityCacheUtil.getResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquitySettingImpl.class, equitySettingId, this);

		if (socialEquitySetting == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquitySetting = (SocialEquitySetting)session.get(SocialEquitySettingImpl.class,
						new Long(equitySettingId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquitySetting != null) {
					cacheResult(socialEquitySetting);
				}

				closeSession(session);
			}
		}

		return socialEquitySetting;
	}

	public List<SocialEquitySetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquitySetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquitySetting> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquitySetting> list = (List<SocialEquitySetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYSETTING);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALEQUITYSETTING;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquitySetting>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquitySetting>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquitySetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeAll() throws SystemException {
		for (SocialEquitySetting socialEquitySetting : findAll()) {
			remove(socialEquitySetting);
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYSETTING);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquitySetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquitySetting>> listenersList = new ArrayList<ModelListener<SocialEquitySetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquitySetting>)Class.forName(
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
	private static final String _SQL_SELECT_SOCIALEQUITYSETTING = "SELECT socialEquitySetting FROM SocialEquitySetting socialEquitySetting";
	private static final String _SQL_COUNT_SOCIALEQUITYSETTING = "SELECT COUNT(socialEquitySetting) FROM SocialEquitySetting socialEquitySetting";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquitySetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquitySetting exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(SocialEquitySettingPersistenceImpl.class);
}