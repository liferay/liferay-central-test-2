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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBStatsUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUserPersistence
 * @see       MBStatsUserUtil
 * @generated
 */
public class MBStatsUserPersistenceImpl extends BasePersistenceImpl<MBStatsUser>
	implements MBStatsUserPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_M = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_M",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_M = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_M",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(MBStatsUser mbStatsUser) {
		EntityCacheUtil.putResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(mbStatsUser.getGroupId()),
				new Long(mbStatsUser.getUserId())
			}, mbStatsUser);
	}

	public void cacheResult(List<MBStatsUser> mbStatsUsers) {
		for (MBStatsUser mbStatsUser : mbStatsUsers) {
			if (EntityCacheUtil.getResult(
						MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), this) == null) {
				cacheResult(mbStatsUser);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBStatsUserImpl.class.getName());
		EntityCacheUtil.clearCache(MBStatsUserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(MBStatsUser mbStatsUser) {
		EntityCacheUtil.removeResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(mbStatsUser.getGroupId()),
				new Long(mbStatsUser.getUserId())
			});
	}

	public MBStatsUser create(long statsUserId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setNew(true);
		mbStatsUser.setPrimaryKey(statsUserId);

		return mbStatsUser;
	}

	public MBStatsUser remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUser mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
					new Long(statsUserId));

			if (mbStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
				}

				throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					statsUserId);
			}

			return remove(mbStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser remove(MBStatsUser mbStatsUser)
		throws SystemException {
		for (ModelListener<MBStatsUser> listener : listeners) {
			listener.onBeforeRemove(mbStatsUser);
		}

		mbStatsUser = removeImpl(mbStatsUser);

		for (ModelListener<MBStatsUser> listener : listeners) {
			listener.onAfterRemove(mbStatsUser);
		}

		return mbStatsUser;
	}

	protected MBStatsUser removeImpl(MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUser = toUnwrappedModel(mbStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (mbStatsUser.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBStatsUserImpl.class,
						mbStatsUser.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbStatsUser);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBStatsUserModelImpl mbStatsUserModelImpl = (MBStatsUserModelImpl)mbStatsUser;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(mbStatsUserModelImpl.getOriginalGroupId()),
				new Long(mbStatsUserModelImpl.getOriginalUserId())
			});

		EntityCacheUtil.removeResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

		return mbStatsUser;
	}

	public MBStatsUser updateImpl(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge) throws SystemException {
		mbStatsUser = toUnwrappedModel(mbStatsUser);

		boolean isNew = mbStatsUser.isNew();

		MBStatsUserModelImpl mbStatsUserModelImpl = (MBStatsUserModelImpl)mbStatsUser;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbStatsUser, merge);

			mbStatsUser.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser);

		if (!isNew &&
				((mbStatsUser.getGroupId() != mbStatsUserModelImpl.getOriginalGroupId()) ||
				(mbStatsUser.getUserId() != mbStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(mbStatsUserModelImpl.getOriginalGroupId()),
					new Long(mbStatsUserModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((mbStatsUser.getGroupId() != mbStatsUserModelImpl.getOriginalGroupId()) ||
				(mbStatsUser.getUserId() != mbStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(mbStatsUser.getGroupId()),
					new Long(mbStatsUser.getUserId())
				}, mbStatsUser);
		}

		return mbStatsUser;
	}

	protected MBStatsUser toUnwrappedModel(MBStatsUser mbStatsUser) {
		if (mbStatsUser instanceof MBStatsUserImpl) {
			return mbStatsUser;
		}

		MBStatsUserImpl mbStatsUserImpl = new MBStatsUserImpl();

		mbStatsUserImpl.setNew(mbStatsUser.isNew());
		mbStatsUserImpl.setPrimaryKey(mbStatsUser.getPrimaryKey());

		mbStatsUserImpl.setStatsUserId(mbStatsUser.getStatsUserId());
		mbStatsUserImpl.setGroupId(mbStatsUser.getGroupId());
		mbStatsUserImpl.setUserId(mbStatsUser.getUserId());
		mbStatsUserImpl.setMessageCount(mbStatsUser.getMessageCount());
		mbStatsUserImpl.setLastPostDate(mbStatsUser.getLastPostDate());

		return mbStatsUserImpl;
	}

	public MBStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByPrimaryKey(statsUserId);

		if (mbStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
			}

			throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statsUserId);
		}

		return mbStatsUser;
	}

	public MBStatsUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		MBStatsUser mbStatsUser = (MBStatsUser)EntityCacheUtil.getResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				MBStatsUserImpl.class, statsUserId, this);

		if (mbStatsUser == null) {
			Session session = null;

			try {
				session = openSession();

				mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
						new Long(statsUserId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbStatsUser != null) {
					cacheResult(mbStatsUser);
				}

				closeSession(session);
			}
		}

		return mbStatsUser;
	}

	public List<MBStatsUser> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBStatsUser findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);

		List<MBStatsUser> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbStatsUser, groupId,
					orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByGroupId_PrevAndNext(session, mbStatsUser, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByGroupId_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<MBStatsUser> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBStatsUser findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);

		List<MBStatsUser> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbStatsUser, userId,
					orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByUserId_PrevAndNext(session, mbStatsUser, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByUserId_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public MBStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByG_U(groupId, userId);

		if (mbStatsUser == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsUserException(msg.toString());
		}

		return mbStatsUser;
	}

	public MBStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	public MBStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<MBStatsUser> list = q.list();

				result = list;

				MBStatsUser mbStatsUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					mbStatsUser = list.get(0);

					cacheResult(mbStatsUser);

					if ((mbStatsUser.getGroupId() != groupId) ||
							(mbStatsUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, mbStatsUser);
					}
				}

				return mbStatsUser;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, new ArrayList<MBStatsUser>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBStatsUser)result;
			}
		}
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount)
		throws SystemException {
		return findByG_M(groupId, messageCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount,
		int start, int end) throws SystemException {
		return findByG_M(groupId, messageCount, start, end, null);
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(messageCount),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_M,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_M_GROUPID_2);

				query.append(_FINDER_COLUMN_G_M_MESSAGECOUNT_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(messageCount);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_M, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBStatsUser findByG_M_First(long groupId, int messageCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByG_M(groupId, messageCount, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", messageCount=");
			msg.append(messageCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByG_M_Last(long groupId, int messageCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByG_M(groupId, messageCount);

		List<MBStatsUser> list = findByG_M(groupId, messageCount, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", messageCount=");
			msg.append(messageCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByG_M_PrevAndNext(long statsUserId, long groupId,
		int messageCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByG_M_PrevAndNext(session, mbStatsUser, groupId,
					messageCount, orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByG_M_PrevAndNext(session, mbStatsUser, groupId,
					messageCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByG_M_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long groupId, int messageCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_G_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_MESSAGECOUNT_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(messageCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<MBStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBStatsUser> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_MBSTATSUSER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_MBSTATSUSER.concat(MBStatsUserModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByGroupId(groupId)) {
			remove(mbStatsUser);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByUserId(userId)) {
			remove(mbStatsUser);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByG_U(groupId, userId);

		remove(mbStatsUser);
	}

	public void removeByG_M(long groupId, int messageCount)
		throws SystemException {
		for (MBStatsUser mbStatsUser : findByG_M(groupId, messageCount)) {
			remove(mbStatsUser);
		}
	}

	public void removeAll() throws SystemException {
		for (MBStatsUser mbStatsUser : findAll()) {
			remove(mbStatsUser);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_M(long groupId, int messageCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(messageCount)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_M,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_M_GROUPID_2);

				query.append(_FINDER_COLUMN_G_M_MESSAGECOUNT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(messageCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_M, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_MBSTATSUSER);

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBStatsUser>> listenersList = new ArrayList<ModelListener<MBStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBStatsUser>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFlagPersistence.class)
	protected MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBSTATSUSER = "SELECT mbStatsUser FROM MBStatsUser mbStatsUser";
	private static final String _SQL_SELECT_MBSTATSUSER_WHERE = "SELECT mbStatsUser FROM MBStatsUser mbStatsUser WHERE ";
	private static final String _SQL_COUNT_MBSTATSUSER = "SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser";
	private static final String _SQL_COUNT_MBSTATSUSER_WHERE = "SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbStatsUser.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "mbStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "mbStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_M_GROUPID_2 = "mbStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_MESSAGECOUNT_2 = "mbStatsUser.messageCount != ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbStatsUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBStatsUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBStatsUser exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(MBStatsUserPersistenceImpl.class);
}