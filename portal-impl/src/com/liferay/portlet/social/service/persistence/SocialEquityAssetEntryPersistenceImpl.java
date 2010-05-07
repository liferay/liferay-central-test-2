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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.impl.SocialEquityAssetEntryImpl;
import com.liferay.portlet.social.model.impl.SocialEquityAssetEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquityAssetEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryPersistence
 * @see       SocialEquityAssetEntryUtil
 * @generated
 */
public class SocialEquityAssetEntryPersistenceImpl extends BasePersistenceImpl<SocialEquityAssetEntry>
	implements SocialEquityAssetEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityAssetEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_ASSETENTRYID = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByAssetEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByAssetEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquityAssetEntry socialEquityAssetEntry) {
		EntityCacheUtil.putResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey(), socialEquityAssetEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] { new Long(socialEquityAssetEntry.getAssetEntryId()) },
			socialEquityAssetEntry);
	}

	public void cacheResult(
		List<SocialEquityAssetEntry> socialEquityAssetEntries) {
		for (SocialEquityAssetEntry socialEquityAssetEntry : socialEquityAssetEntries) {
			if (EntityCacheUtil.getResult(
						SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityAssetEntryImpl.class,
						socialEquityAssetEntry.getPrimaryKey(), this) == null) {
				cacheResult(socialEquityAssetEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialEquityAssetEntryImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquityAssetEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SocialEquityAssetEntry socialEquityAssetEntry) {
		EntityCacheUtil.removeResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] { new Long(socialEquityAssetEntry.getAssetEntryId()) });
	}

	public SocialEquityAssetEntry create(long equityAssetEntryId) {
		SocialEquityAssetEntry socialEquityAssetEntry = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntry.setNew(true);
		socialEquityAssetEntry.setPrimaryKey(equityAssetEntryId);

		return socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquityAssetEntry remove(long equityAssetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityAssetEntry socialEquityAssetEntry = (SocialEquityAssetEntry)session.get(SocialEquityAssetEntryImpl.class,
					new Long(equityAssetEntryId));

			if (socialEquityAssetEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equityAssetEntryId);
				}

				throw new NoSuchEquityAssetEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityAssetEntryId);
			}

			return remove(socialEquityAssetEntry);
		}
		catch (NoSuchEquityAssetEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialEquityAssetEntry remove(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		for (ModelListener<SocialEquityAssetEntry> listener : listeners) {
			listener.onBeforeRemove(socialEquityAssetEntry);
		}

		socialEquityAssetEntry = removeImpl(socialEquityAssetEntry);

		for (ModelListener<SocialEquityAssetEntry> listener : listeners) {
			listener.onAfterRemove(socialEquityAssetEntry);
		}

		return socialEquityAssetEntry;
	}

	protected SocialEquityAssetEntry removeImpl(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		socialEquityAssetEntry = toUnwrappedModel(socialEquityAssetEntry);

		Session session = null;

		try {
			session = openSession();

			if (socialEquityAssetEntry.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquityAssetEntryImpl.class,
						socialEquityAssetEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquityAssetEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquityAssetEntryModelImpl socialEquityAssetEntryModelImpl = (SocialEquityAssetEntryModelImpl)socialEquityAssetEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] {
				new Long(socialEquityAssetEntryModelImpl.getOriginalAssetEntryId())
			});

		EntityCacheUtil.removeResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey());

		return socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry updateImpl(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge) throws SystemException {
		socialEquityAssetEntry = toUnwrappedModel(socialEquityAssetEntry);

		boolean isNew = socialEquityAssetEntry.isNew();

		SocialEquityAssetEntryModelImpl socialEquityAssetEntryModelImpl = (SocialEquityAssetEntryModelImpl)socialEquityAssetEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityAssetEntry, merge);

			socialEquityAssetEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey(), socialEquityAssetEntry);

		if (!isNew &&
				(socialEquityAssetEntry.getAssetEntryId() != socialEquityAssetEntryModelImpl.getOriginalAssetEntryId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
				new Object[] {
					new Long(socialEquityAssetEntryModelImpl.getOriginalAssetEntryId())
				});
		}

		if (isNew ||
				(socialEquityAssetEntry.getAssetEntryId() != socialEquityAssetEntryModelImpl.getOriginalAssetEntryId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
				new Object[] { new Long(socialEquityAssetEntry.getAssetEntryId()) },
				socialEquityAssetEntry);
		}

		return socialEquityAssetEntry;
	}

	protected SocialEquityAssetEntry toUnwrappedModel(
		SocialEquityAssetEntry socialEquityAssetEntry) {
		if (socialEquityAssetEntry instanceof SocialEquityAssetEntryImpl) {
			return socialEquityAssetEntry;
		}

		SocialEquityAssetEntryImpl socialEquityAssetEntryImpl = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntryImpl.setNew(socialEquityAssetEntry.isNew());
		socialEquityAssetEntryImpl.setPrimaryKey(socialEquityAssetEntry.getPrimaryKey());

		socialEquityAssetEntryImpl.setEquityAssetEntryId(socialEquityAssetEntry.getEquityAssetEntryId());
		socialEquityAssetEntryImpl.setGroupId(socialEquityAssetEntry.getGroupId());
		socialEquityAssetEntryImpl.setCompanyId(socialEquityAssetEntry.getCompanyId());
		socialEquityAssetEntryImpl.setUserId(socialEquityAssetEntry.getUserId());
		socialEquityAssetEntryImpl.setAssetEntryId(socialEquityAssetEntry.getAssetEntryId());
		socialEquityAssetEntryImpl.setInformationK(socialEquityAssetEntry.getInformationK());
		socialEquityAssetEntryImpl.setInformationB(socialEquityAssetEntry.getInformationB());
		socialEquityAssetEntryImpl.setInformationEquity(socialEquityAssetEntry.getInformationEquity());

		return socialEquityAssetEntryImpl;
	}

	public SocialEquityAssetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityAssetEntry findByPrimaryKey(long equityAssetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = fetchByPrimaryKey(equityAssetEntryId);

		if (socialEquityAssetEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityAssetEntryId);
			}

			throw new NoSuchEquityAssetEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityAssetEntryId);
		}

		return socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityAssetEntry fetchByPrimaryKey(long equityAssetEntryId)
		throws SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = (SocialEquityAssetEntry)EntityCacheUtil.getResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityAssetEntryImpl.class, equityAssetEntryId, this);

		if (socialEquityAssetEntry == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquityAssetEntry = (SocialEquityAssetEntry)session.get(SocialEquityAssetEntryImpl.class,
						new Long(equityAssetEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquityAssetEntry != null) {
					cacheResult(socialEquityAssetEntry);
				}

				closeSession(session);
			}
		}

		return socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry findByAssetEntryId(long assetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = fetchByAssetEntryId(assetEntryId);

		if (socialEquityAssetEntry == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityAssetEntryException(msg.toString());
		}

		return socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry fetchByAssetEntryId(long assetEntryId)
		throws SystemException {
		return fetchByAssetEntryId(assetEntryId, true);
	}

	public SocialEquityAssetEntry fetchByAssetEntryId(long assetEntryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(assetEntryId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALEQUITYASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				List<SocialEquityAssetEntry> list = q.list();

				result = list;

				SocialEquityAssetEntry socialEquityAssetEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
						finderArgs, list);
				}
				else {
					socialEquityAssetEntry = list.get(0);

					cacheResult(socialEquityAssetEntry);

					if ((socialEquityAssetEntry.getAssetEntryId() != assetEntryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
							finderArgs, socialEquityAssetEntry);
					}
				}

				return socialEquityAssetEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
						finderArgs, new ArrayList<SocialEquityAssetEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialEquityAssetEntry)result;
			}
		}
	}

	public List<SocialEquityAssetEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityAssetEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquityAssetEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityAssetEntry> list = (List<SocialEquityAssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYASSETENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALEQUITYASSETENTRY;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityAssetEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityAssetEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityAssetEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByAssetEntryId(long assetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = findByAssetEntryId(assetEntryId);

		remove(socialEquityAssetEntry);
	}

	public void removeAll() throws SystemException {
		for (SocialEquityAssetEntry socialEquityAssetEntry : findAll()) {
			remove(socialEquityAssetEntry);
		}
	}

	public int countByAssetEntryId(long assetEntryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(assetEntryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ASSETENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALEQUITYASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ASSETENTRYID,
					finderArgs, count);

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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYASSETENTRY);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityAssetEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityAssetEntry>> listenersList = new ArrayList<ModelListener<SocialEquityAssetEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityAssetEntry>)Class.forName(
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
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYASSETENTRY = "SELECT socialEquityAssetEntry FROM SocialEquityAssetEntry socialEquityAssetEntry";
	private static final String _SQL_SELECT_SOCIALEQUITYASSETENTRY_WHERE = "SELECT socialEquityAssetEntry FROM SocialEquityAssetEntry socialEquityAssetEntry WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYASSETENTRY = "SELECT COUNT(socialEquityAssetEntry) FROM SocialEquityAssetEntry socialEquityAssetEntry";
	private static final String _SQL_COUNT_SOCIALEQUITYASSETENTRY_WHERE = "SELECT COUNT(socialEquityAssetEntry) FROM SocialEquityAssetEntry socialEquityAssetEntry WHERE ";
	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "socialEquityAssetEntry.assetEntryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityAssetEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityAssetEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityAssetEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialEquityAssetEntryPersistenceImpl.class);
}