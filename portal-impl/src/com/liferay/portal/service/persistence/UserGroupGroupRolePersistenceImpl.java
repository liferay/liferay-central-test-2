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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchUserGroupGroupRoleException;
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
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserGroupGroupRolePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRolePersistence
 * @see       UserGroupGroupRoleUtil
 * @generated
 */
public class UserGroupGroupRolePersistenceImpl extends BasePersistenceImpl<UserGroupGroupRole>
	implements UserGroupGroupRolePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);
	}

	public void cacheResult(List<UserGroupGroupRole> userGroupGroupRoles) {
		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			if (EntityCacheUtil.getResult(
						UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKey(), this) == null) {
				cacheResult(userGroupGroupRole);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserGroupGroupRoleImpl.class.getName());
		EntityCacheUtil.clearCache(UserGroupGroupRoleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());
	}

	public UserGroupGroupRole create(UserGroupGroupRolePK userGroupGroupRolePK) {
		UserGroupGroupRole userGroupGroupRole = new UserGroupGroupRoleImpl();

		userGroupGroupRole.setNew(true);
		userGroupGroupRole.setPrimaryKey(userGroupGroupRolePK);

		return userGroupGroupRole;
	}

	public UserGroupGroupRole remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole remove(UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
					userGroupGroupRolePK);

			if (userGroupGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userGroupGroupRolePK);
				}

				throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userGroupGroupRolePK);
			}

			return remove(userGroupGroupRole);
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserGroupGroupRole remove(UserGroupGroupRole userGroupGroupRole)
		throws SystemException {
		for (ModelListener<UserGroupGroupRole> listener : listeners) {
			listener.onBeforeRemove(userGroupGroupRole);
		}

		userGroupGroupRole = removeImpl(userGroupGroupRole);

		for (ModelListener<UserGroupGroupRole> listener : listeners) {
			listener.onAfterRemove(userGroupGroupRole);
		}

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole removeImpl(
		UserGroupGroupRole userGroupGroupRole) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			if (userGroupGroupRole.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userGroupGroupRole);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		return userGroupGroupRole;
	}

	public UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userGroupGroupRole, merge);

			userGroupGroupRole.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole toUnwrappedModel(
		UserGroupGroupRole userGroupGroupRole) {
		if (userGroupGroupRole instanceof UserGroupGroupRoleImpl) {
			return userGroupGroupRole;
		}

		UserGroupGroupRoleImpl userGroupGroupRoleImpl = new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setNew(userGroupGroupRole.isNew());
		userGroupGroupRoleImpl.setPrimaryKey(userGroupGroupRole.getPrimaryKey());

		userGroupGroupRoleImpl.setUserGroupId(userGroupGroupRole.getUserGroupId());
		userGroupGroupRoleImpl.setGroupId(userGroupGroupRole.getGroupId());
		userGroupGroupRoleImpl.setRoleId(userGroupGroupRole.getRoleId());

		return userGroupGroupRoleImpl;
	}

	public UserGroupGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole findByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(userGroupGroupRolePK);

		if (userGroupGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userGroupGroupRolePK);
			}

			throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userGroupGroupRolePK);
		}

		return userGroupGroupRole;
	}

	public UserGroupGroupRole fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole fetchByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK) throws SystemException {
		UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)EntityCacheUtil.getResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupGroupRoleImpl.class, userGroupGroupRolePK, this);

		if (userGroupGroupRole == null) {
			Session session = null;

			try {
				session = openSession();

				userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRolePK);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userGroupGroupRole != null) {
					cacheResult(userGroupGroupRole);
				}

				closeSession(session);
			}
		}

		return userGroupGroupRole;
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userGroupId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERGROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERGROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end) throws SystemException {
		return findByUserGroupId(userGroupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERGROUPID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERGROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByUserGroupId_First(long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByUserGroupId_Last(long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByUserGroupId(userGroupId);

		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByUserGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByGroupId(groupId);

		List<UserGroupGroupRole> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
					groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
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

	protected UserGroupGroupRole getByGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(roleId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByRoleId(roleId);

		List<UserGroupGroupRole> list = findByRoleId(roleId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
					roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
					roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByRoleId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_G,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

				query.append(_FINDER_COLUMN_U_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_G, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end) throws SystemException {
		return findByU_G(userGroupId, groupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_G,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

				query.append(_FINDER_COLUMN_U_G_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_G,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByU_G_First(long userGroupId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByU_G_Last(long userGroupId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByU_G(userGroupId, groupId);

		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByU_G_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByU_G_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

		query.append(_FINDER_COLUMN_U_G_GROUPID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userGroupId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(roleId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_G_R_GROUPID_2);

				query.append(_FINDER_COLUMN_G_R_ROLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end) throws SystemException {
		return findByG_R(groupId, roleId, start, end, null);
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(roleId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_R,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_G_R_GROUPID_2);

				query.append(_FINDER_COLUMN_G_R_ROLEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByG_R_First(long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByG_R_Last(long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByG_R(groupId, roleId);

		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByG_R_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByG_R_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_ROLEID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserGroupGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserGroupGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserGroupGroupRole> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_USERGROUPGROUPROLE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_USERGROUPGROUPROLE;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserGroupId(long userGroupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByUserGroupId(
				userGroupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByGroupId(groupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByRoleId(long roleId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByRoleId(roleId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByU_G(long userGroupId, long groupId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByU_G(userGroupId,
				groupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByG_R(long groupId, long roleId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByG_R(groupId, roleId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeAll() throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findAll()) {
			remove(userGroupGroupRole);
		}
	}

	public int countByUserGroupId(long userGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userGroupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERGROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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

	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_G(long userGroupId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

				query.append(_FINDER_COLUMN_U_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_R(long groupId, long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

				query.append(_FINDER_COLUMN_G_R_GROUPID_2);

				query.append(_FINDER_COLUMN_G_R_ROLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_R, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_USERGROUPGROUPROLE);

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
						"value.object.listener.com.liferay.portal.model.UserGroupGroupRole")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserGroupGroupRole>> listenersList = new ArrayList<ModelListener<UserGroupGroupRole>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserGroupGroupRole>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_USERGROUPGROUPROLE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_SELECT_USERGROUPGROUPROLE_WHERE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE_WHERE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _FINDER_COLUMN_USERGROUPID_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";
	private static final String _FINDER_COLUMN_U_G_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	private static final String _FINDER_COLUMN_G_R_GROUPID_2 = "userGroupGroupRole.id.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_R_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroupGroupRole.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserGroupGroupRole exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserGroupGroupRole exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserGroupGroupRolePersistenceImpl.class);
}