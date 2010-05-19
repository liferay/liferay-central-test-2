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

package com.liferay.portlet.blogs.service.persistence;

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
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BlogsStatsUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserPersistence
 * @see       BlogsStatsUserUtil
 * @generated
 */
public class BlogsStatsUserPersistenceImpl extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_E",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_E",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(BlogsStatsUser blogsStatsUser) {
		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(blogsStatsUser.getGroupId()),
				new Long(blogsStatsUser.getUserId())
			}, blogsStatsUser);
	}

	public void cacheResult(List<BlogsStatsUser> blogsStatsUsers) {
		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			if (EntityCacheUtil.getResult(
						BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKey(), this) == null) {
				cacheResult(blogsStatsUser);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(BlogsStatsUserImpl.class.getName());
		EntityCacheUtil.clearCache(BlogsStatsUserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(BlogsStatsUser blogsStatsUser) {
		EntityCacheUtil.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(blogsStatsUser.getGroupId()),
				new Long(blogsStatsUser.getUserId())
			});
	}

	public BlogsStatsUser create(long statsUserId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setNew(true);
		blogsStatsUser.setPrimaryKey(statsUserId);

		return blogsStatsUser;
	}

	public BlogsStatsUser remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
					new Long(statsUserId));

			if (blogsStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
				}

				throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					statsUserId);
			}

			return remove(blogsStatsUser);
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

	public BlogsStatsUser remove(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onBeforeRemove(blogsStatsUser);
		}

		blogsStatsUser = removeImpl(blogsStatsUser);

		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onAfterRemove(blogsStatsUser);
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (blogsStatsUser.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(blogsStatsUser);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(blogsStatsUserModelImpl.getOriginalGroupId()),
				new Long(blogsStatsUserModelImpl.getOriginalUserId())
			});

		EntityCacheUtil.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

		return blogsStatsUser;
	}

	public BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge) throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		boolean isNew = blogsStatsUser.isNew();

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsStatsUser, merge);

			blogsStatsUser.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		if (!isNew &&
				((blogsStatsUser.getGroupId() != blogsStatsUserModelImpl.getOriginalGroupId()) ||
				(blogsStatsUser.getUserId() != blogsStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(blogsStatsUserModelImpl.getOriginalGroupId()),
					new Long(blogsStatsUserModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((blogsStatsUser.getGroupId() != blogsStatsUserModelImpl.getOriginalGroupId()) ||
				(blogsStatsUser.getUserId() != blogsStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(blogsStatsUser.getGroupId()),
					new Long(blogsStatsUser.getUserId())
				}, blogsStatsUser);
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser toUnwrappedModel(BlogsStatsUser blogsStatsUser) {
		if (blogsStatsUser instanceof BlogsStatsUserImpl) {
			return blogsStatsUser;
		}

		BlogsStatsUserImpl blogsStatsUserImpl = new BlogsStatsUserImpl();

		blogsStatsUserImpl.setNew(blogsStatsUser.isNew());
		blogsStatsUserImpl.setPrimaryKey(blogsStatsUser.getPrimaryKey());

		blogsStatsUserImpl.setStatsUserId(blogsStatsUser.getStatsUserId());
		blogsStatsUserImpl.setGroupId(blogsStatsUser.getGroupId());
		blogsStatsUserImpl.setCompanyId(blogsStatsUser.getCompanyId());
		blogsStatsUserImpl.setUserId(blogsStatsUser.getUserId());
		blogsStatsUserImpl.setEntryCount(blogsStatsUser.getEntryCount());
		blogsStatsUserImpl.setLastPostDate(blogsStatsUser.getLastPostDate());
		blogsStatsUserImpl.setRatingsTotalEntries(blogsStatsUser.getRatingsTotalEntries());
		blogsStatsUserImpl.setRatingsTotalScore(blogsStatsUser.getRatingsTotalScore());
		blogsStatsUserImpl.setRatingsAverageScore(blogsStatsUser.getRatingsAverageScore());

		return blogsStatsUserImpl;
	}

	public BlogsStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(statsUserId);

		if (blogsStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
			}

			throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statsUserId);
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		BlogsStatsUser blogsStatsUser = (BlogsStatsUser)EntityCacheUtil.getResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, statsUserId, this);

		if (blogsStatsUser == null) {
			Session session = null;

			try {
				session = openSession();

				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						new Long(statsUserId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (blogsStatsUser != null) {
					cacheResult(blogsStatsUser);
				}

				closeSession(session);
			}
		}

		return blogsStatsUser;
	}

	public List<BlogsStatsUser> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByGroupId(groupId, 0, 1,
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

	public BlogsStatsUser findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsStatsUser> list = findByGroupId(groupId, count - 1, count,
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

	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsStatsUser,
					groupId, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByGroupId_PrevAndNext(session, blogsStatsUser,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByGroupId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

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
		}

		else {
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsStatsUser> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

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

	public BlogsStatsUser findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);

		List<BlogsStatsUser> list = findByUserId(userId, count - 1, count,
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

	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
					orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
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

	protected BlogsStatsUser getByUserId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

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
		}

		else {
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_U(groupId, userId);

		if (blogsStatsUser == null) {
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

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	public BlogsStatsUser fetchByG_U(long groupId, long userId,
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

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

				result = list;

				BlogsStatsUser blogsStatsUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					blogsStatsUser = list.get(0);

					cacheResult(blogsStatsUser);

					if ((blogsStatsUser.getGroupId() != groupId) ||
							(blogsStatsUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, blogsStatsUser);
					}
				}

				return blogsStatsUser;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, new ArrayList<BlogsStatsUser>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BlogsStatsUser)result;
			}
		}
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount)
		throws SystemException {
		return findByG_E(groupId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end) throws SystemException {
		return findByG_E(groupId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_E,
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

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_E_GROUPID_2);

				query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_E, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByG_E_First(long groupId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", entryCount=");
			msg.append(entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByG_E_Last(long groupId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByG_E(groupId, entryCount);

		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", entryCount=");
			msg.append(entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByG_E_PrevAndNext(long statsUserId,
		long groupId, int entryCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByG_E_PrevAndNext(session, blogsStatsUser, groupId,
					entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByG_E_PrevAndNext(session, blogsStatsUser, groupId,
					entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByG_E_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId, int entryCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_G_E_GROUPID_2);

		query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

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
		}

		else {
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount)
		throws SystemException {
		return findByC_E(companyId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end) throws SystemException {
		return findByC_E(companyId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_E,
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

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_E, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByC_E_First(long companyId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", entryCount=");
			msg.append(entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByC_E_Last(long companyId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		int count = countByC_E(companyId, entryCount);

		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", entryCount=");
			msg.append(entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByC_E_PrevAndNext(long statsUserId,
		long companyId, int entryCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByC_E_PrevAndNext(session, blogsStatsUser, companyId,
					entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByC_E_PrevAndNext(session, blogsStatsUser, companyId,
					entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByC_E_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long companyId, int entryCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

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
		}

		else {
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_BLOGSSTATSUSER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_BLOGSSTATSUSER.concat(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByGroupId(groupId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByUserId(userId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByG_U(groupId, userId);

		remove(blogsStatsUser);
	}

	public void removeByG_E(long groupId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByG_E(groupId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByC_E(long companyId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByC_E(companyId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeAll() throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findAll()) {
			remove(blogsStatsUser);
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

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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

	public int countByG_E(long groupId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_E_GROUPID_2);

				query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_E, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_E(long companyId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_E, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_BLOGSSTATSUSER);

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
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsStatsUser>> listenersList = new ArrayList<ModelListener<BlogsStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsStatsUser>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_BLOGSSTATSUSER = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _SQL_COUNT_BLOGSSTATSUSER = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_COUNT_BLOGSSTATSUSER_WHERE = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsStatsUser.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_E_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_E_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _FINDER_COLUMN_C_E_COMPANYID_2 = "blogsStatsUser.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_E_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsStatsUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsStatsUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsStatsUser exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(BlogsStatsUserPersistenceImpl.class);
}