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

import com.liferay.portlet.social.NoSuchEquityUserException;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.impl.SocialEquityUserImpl;
import com.liferay.portlet.social.model.impl.SocialEquityUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquityUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserPersistence
 * @see       SocialEquityUserUtil
 * @generated
 */
public class SocialEquityUserPersistenceImpl extends BasePersistenceImpl<SocialEquityUser>
	implements SocialEquityUserPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquityUser socialEquityUser) {
		EntityCacheUtil.putResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey(),
			socialEquityUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(socialEquityUser.getUserId()) },
			socialEquityUser);
	}

	public void cacheResult(List<SocialEquityUser> socialEquityUsers) {
		for (SocialEquityUser socialEquityUser : socialEquityUsers) {
			if (EntityCacheUtil.getResult(
						SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityUserImpl.class,
						socialEquityUser.getPrimaryKey(), this) == null) {
				cacheResult(socialEquityUser);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialEquityUserImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquityUserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SocialEquityUser socialEquityUser) {
		EntityCacheUtil.removeResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(socialEquityUser.getUserId()) });
	}

	public SocialEquityUser create(long equityUserId) {
		SocialEquityUser socialEquityUser = new SocialEquityUserImpl();

		socialEquityUser.setNew(true);
		socialEquityUser.setPrimaryKey(equityUserId);

		return socialEquityUser;
	}

	public SocialEquityUser remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquityUser remove(long equityUserId)
		throws NoSuchEquityUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityUser socialEquityUser = (SocialEquityUser)session.get(SocialEquityUserImpl.class,
					new Long(equityUserId));

			if (socialEquityUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityUserId);
				}

				throw new NoSuchEquityUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityUserId);
			}

			return remove(socialEquityUser);
		}
		catch (NoSuchEquityUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialEquityUser remove(SocialEquityUser socialEquityUser)
		throws SystemException {
		for (ModelListener<SocialEquityUser> listener : listeners) {
			listener.onBeforeRemove(socialEquityUser);
		}

		socialEquityUser = removeImpl(socialEquityUser);

		for (ModelListener<SocialEquityUser> listener : listeners) {
			listener.onAfterRemove(socialEquityUser);
		}

		return socialEquityUser;
	}

	protected SocialEquityUser removeImpl(SocialEquityUser socialEquityUser)
		throws SystemException {
		socialEquityUser = toUnwrappedModel(socialEquityUser);

		Session session = null;

		try {
			session = openSession();

			if (socialEquityUser.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquityUserImpl.class,
						socialEquityUser.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquityUser);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquityUserModelImpl socialEquityUserModelImpl = (SocialEquityUserModelImpl)socialEquityUser;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(socialEquityUserModelImpl.getOriginalUserId()) });

		EntityCacheUtil.removeResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey());

		return socialEquityUser;
	}

	public SocialEquityUser updateImpl(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge) throws SystemException {
		socialEquityUser = toUnwrappedModel(socialEquityUser);

		boolean isNew = socialEquityUser.isNew();

		SocialEquityUserModelImpl socialEquityUserModelImpl = (SocialEquityUserModelImpl)socialEquityUser;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityUser, merge);

			socialEquityUser.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey(),
			socialEquityUser);

		if (!isNew &&
				(socialEquityUser.getUserId() != socialEquityUserModelImpl.getOriginalUserId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] {
					new Long(socialEquityUserModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				(socialEquityUser.getUserId() != socialEquityUserModelImpl.getOriginalUserId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] { new Long(socialEquityUser.getUserId()) },
				socialEquityUser);
		}

		return socialEquityUser;
	}

	protected SocialEquityUser toUnwrappedModel(
		SocialEquityUser socialEquityUser) {
		if (socialEquityUser instanceof SocialEquityUserImpl) {
			return socialEquityUser;
		}

		SocialEquityUserImpl socialEquityUserImpl = new SocialEquityUserImpl();

		socialEquityUserImpl.setNew(socialEquityUser.isNew());
		socialEquityUserImpl.setPrimaryKey(socialEquityUser.getPrimaryKey());

		socialEquityUserImpl.setEquityUserId(socialEquityUser.getEquityUserId());
		socialEquityUserImpl.setCompanyId(socialEquityUser.getCompanyId());
		socialEquityUserImpl.setUserId(socialEquityUser.getUserId());
		socialEquityUserImpl.setContributionEquity(socialEquityUser.getContributionEquity());
		socialEquityUserImpl.setParticipationK(socialEquityUser.getParticipationK());
		socialEquityUserImpl.setParticipationB(socialEquityUser.getParticipationB());
		socialEquityUserImpl.setParticipationEquity(socialEquityUser.getParticipationEquity());
		socialEquityUserImpl.setPersonalEquity(socialEquityUser.getPersonalEquity());

		return socialEquityUserImpl;
	}

	public SocialEquityUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityUser findByPrimaryKey(long equityUserId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = fetchByPrimaryKey(equityUserId);

		if (socialEquityUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityUserId);
			}

			throw new NoSuchEquityUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityUserId);
		}

		return socialEquityUser;
	}

	public SocialEquityUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityUser fetchByPrimaryKey(long equityUserId)
		throws SystemException {
		SocialEquityUser socialEquityUser = (SocialEquityUser)EntityCacheUtil.getResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityUserImpl.class, equityUserId, this);

		if (socialEquityUser == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquityUser = (SocialEquityUser)session.get(SocialEquityUserImpl.class,
						new Long(equityUserId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquityUser != null) {
					cacheResult(socialEquityUser);
				}

				closeSession(session);
			}
		}

		return socialEquityUser;
	}

	public SocialEquityUser findByUserId(long userId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = fetchByUserId(userId);

		if (socialEquityUser == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityUserException(msg.toString());
		}

		return socialEquityUser;
	}

	public SocialEquityUser fetchByUserId(long userId)
		throws SystemException {
		return fetchByUserId(userId, true);
	}

	public SocialEquityUser fetchByUserId(long userId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<SocialEquityUser> list = q.list();

				result = list;

				SocialEquityUser socialEquityUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					socialEquityUser = list.get(0);

					cacheResult(socialEquityUser);

					if ((socialEquityUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, socialEquityUser);
					}
				}

				return socialEquityUser;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, new ArrayList<SocialEquityUser>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialEquityUser)result;
			}
		}
	}

	public List<SocialEquityUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquityUser> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYUSER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_SOCIALEQUITYUSER;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityUser>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByUserId(userId);

		remove(socialEquityUser);
	}

	public void removeAll() throws SystemException {
		for (SocialEquityUser socialEquityUser : findAll()) {
			remove(socialEquityUser);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYUSER);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityUser>> listenersList = new ArrayList<ModelListener<SocialEquityUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityUser>)Class.forName(
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
	private static final String _SQL_SELECT_SOCIALEQUITYUSER = "SELECT socialEquityUser FROM SocialEquityUser socialEquityUser";
	private static final String _SQL_SELECT_SOCIALEQUITYUSER_WHERE = "SELECT socialEquityUser FROM SocialEquityUser socialEquityUser WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYUSER = "SELECT COUNT(socialEquityUser) FROM SocialEquityUser socialEquityUser";
	private static final String _SQL_COUNT_SOCIALEQUITYUSER_WHERE = "SELECT COUNT(socialEquityUser) FROM SocialEquityUser socialEquityUser WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialEquityUser.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityUser exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialEquityUserPersistenceImpl.class);
}