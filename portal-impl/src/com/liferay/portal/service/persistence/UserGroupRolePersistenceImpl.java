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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserGroupRoleException;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.UserGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupRoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserGroupRolePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserGroupRolePersistenceImpl extends BasePersistenceImpl
	implements UserGroupRolePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROLEID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_G = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_G = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_R = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_R = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(UserGroupRole userGroupRole) {
		EntityCacheUtil.putResult(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey(),
			userGroupRole);
	}

	public void cacheResult(List<UserGroupRole> userGroupRoles) {
		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (EntityCacheUtil.getResult(
						UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupRoleImpl.class, userGroupRole.getPrimaryKey(),
						this) == null) {
				cacheResult(userGroupRole);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserGroupRoleImpl.class.getName());
		EntityCacheUtil.clearCache(UserGroupRoleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public UserGroupRole create(UserGroupRolePK userGroupRolePK) {
		UserGroupRole userGroupRole = new UserGroupRoleImpl();

		userGroupRole.setNew(true);
		userGroupRole.setPrimaryKey(userGroupRolePK);

		return userGroupRole;
	}

	public UserGroupRole remove(UserGroupRolePK userGroupRolePK)
		throws NoSuchUserGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupRole userGroupRole = (UserGroupRole)session.get(UserGroupRoleImpl.class,
					userGroupRolePK);

			if (userGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserGroupRole exists with the primary key " +
						userGroupRolePK);
				}

				throw new NoSuchUserGroupRoleException(
					"No UserGroupRole exists with the primary key " +
					userGroupRolePK);
			}

			return remove(userGroupRole);
		}
		catch (NoSuchUserGroupRoleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserGroupRole remove(UserGroupRole userGroupRole)
		throws SystemException {
		for (ModelListener<UserGroupRole> listener : listeners) {
			listener.onBeforeRemove(userGroupRole);
		}

		userGroupRole = removeImpl(userGroupRole);

		for (ModelListener<UserGroupRole> listener : listeners) {
			listener.onAfterRemove(userGroupRole);
		}

		return userGroupRole;
	}

	protected UserGroupRole removeImpl(UserGroupRole userGroupRole)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (userGroupRole.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserGroupRoleImpl.class,
						userGroupRole.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userGroupRole);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey());

		return userGroupRole;
	}

	/**
	 * @deprecated Use <code>update(UserGroupRole userGroupRole, boolean merge)</code>.
	 */
	public UserGroupRole update(UserGroupRole userGroupRole)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(UserGroupRole userGroupRole) method. Use update(UserGroupRole userGroupRole, boolean merge) instead.");
		}

		return update(userGroupRole, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        userGroupRole the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when userGroupRole is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public UserGroupRole update(UserGroupRole userGroupRole, boolean merge)
		throws SystemException {
		boolean isNew = userGroupRole.isNew();

		for (ModelListener<UserGroupRole> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(userGroupRole);
			}
			else {
				listener.onBeforeUpdate(userGroupRole);
			}
		}

		userGroupRole = updateImpl(userGroupRole, merge);

		for (ModelListener<UserGroupRole> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(userGroupRole);
			}
			else {
				listener.onAfterUpdate(userGroupRole);
			}
		}

		return userGroupRole;
	}

	public UserGroupRole updateImpl(
		com.liferay.portal.model.UserGroupRole userGroupRole, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userGroupRole, merge);

			userGroupRole.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey(),
			userGroupRole);

		return userGroupRole;
	}

	public UserGroupRole findByPrimaryKey(UserGroupRolePK userGroupRolePK)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = fetchByPrimaryKey(userGroupRolePK);

		if (userGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserGroupRole exists with the primary key " +
					userGroupRolePK);
			}

			throw new NoSuchUserGroupRoleException(
				"No UserGroupRole exists with the primary key " +
				userGroupRolePK);
		}

		return userGroupRole;
	}

	public UserGroupRole fetchByPrimaryKey(UserGroupRolePK userGroupRolePK)
		throws SystemException {
		UserGroupRole userGroupRole = (UserGroupRole)EntityCacheUtil.getResult(UserGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupRoleImpl.class, userGroupRolePK, this);

		if (userGroupRole == null) {
			Session session = null;

			try {
				session = openSession();

				userGroupRole = (UserGroupRole)session.get(UserGroupRoleImpl.class,
						userGroupRolePK);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userGroupRole != null) {
					cacheResult(userGroupRole);
				}

				closeSession(session);
			}
		}

		return userGroupRole;
	}

	public List<UserGroupRole> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupRole> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<UserGroupRole> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupRole findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		List<UserGroupRole> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		int count = countByUserId(userId);

		List<UserGroupRole> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole[] findByUserId_PrevAndNext(
		UserGroupRolePK userGroupRolePK, long userId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRolePK);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserGroupRole WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupRole);

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = (UserGroupRole)objArray[0];
			array[1] = (UserGroupRole)objArray[1];
			array[2] = (UserGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupRole> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupRole> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<UserGroupRole> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupRole findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		List<UserGroupRole> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		int count = countByGroupId(groupId);

		List<UserGroupRole> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole[] findByGroupId_PrevAndNext(
		UserGroupRolePK userGroupRolePK, long groupId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRolePK);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserGroupRole WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupRole);

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = (UserGroupRole)objArray[0];
			array[1] = (UserGroupRole)objArray[1];
			array[2] = (UserGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupRole> findByRoleId(long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	public List<UserGroupRole> findByRoleId(long roleId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(roleId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupRole findByRoleId_First(long roleId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		List<UserGroupRole> list = findByRoleId(roleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole findByRoleId_Last(long roleId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		int count = countByRoleId(roleId);

		List<UserGroupRole> list = findByRoleId(roleId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole[] findByRoleId_PrevAndNext(
		UserGroupRolePK userGroupRolePK, long roleId, OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRolePK);

		int count = countByRoleId(roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserGroupRole WHERE ");

			query.append("roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupRole);

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = (UserGroupRole)objArray[0];
			array[1] = (UserGroupRole)objArray[1];
			array[2] = (UserGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupRole> findByU_G(long userId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), new Long(groupId) };

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_G,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_G, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupRole> findByU_G(long userId, long groupId, int start,
		int end) throws SystemException {
		return findByU_G(userId, groupId, start, end, null);
	}

	public List<UserGroupRole> findByU_G(long userId, long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_G,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_G,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupRole findByU_G_First(long userId, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		List<UserGroupRole> list = findByU_G(userId, groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole findByU_G_Last(long userId, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		int count = countByU_G(userId, groupId);

		List<UserGroupRole> list = findByU_G(userId, groupId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole[] findByU_G_PrevAndNext(
		UserGroupRolePK userGroupRolePK, long userId, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRolePK);

		int count = countByU_G(userId, groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserGroupRole WHERE ");

			query.append("userId = ?");

			query.append(" AND ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupRole);

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = (UserGroupRole)objArray[0];
			array[1] = (UserGroupRole)objArray[1];
			array[2] = (UserGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupRole> findByG_R(long groupId, long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(roleId) };

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupRole> findByG_R(long groupId, long roleId, int start,
		int end) throws SystemException {
		return findByG_R(groupId, roleId, start, end, null);
	}

	public List<UserGroupRole> findByG_R(long groupId, long roleId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(roleId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupRole findByG_R_First(long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		List<UserGroupRole> list = findByG_R(groupId, roleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole findByG_R_Last(long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		int count = countByG_R(groupId, roleId);

		List<UserGroupRole> list = findByG_R(groupId, roleId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupRole[] findByG_R_PrevAndNext(
		UserGroupRolePK userGroupRolePK, long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupRoleException, SystemException {
		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRolePK);

		int count = countByG_R(groupId, roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserGroupRole WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupRole);

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = (UserGroupRole)objArray[0];
			array[1] = (UserGroupRole)objArray[1];
			array[2] = (UserGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<UserGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserGroupRole> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupRole> list = (List<UserGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.UserGroupRole ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserGroupRole>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (UserGroupRole userGroupRole : findByUserId(userId)) {
			remove(userGroupRole);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (UserGroupRole userGroupRole : findByGroupId(groupId)) {
			remove(userGroupRole);
		}
	}

	public void removeByRoleId(long roleId) throws SystemException {
		for (UserGroupRole userGroupRole : findByRoleId(roleId)) {
			remove(userGroupRole);
		}
	}

	public void removeByU_G(long userId, long groupId)
		throws SystemException {
		for (UserGroupRole userGroupRole : findByU_G(userId, groupId)) {
			remove(userGroupRole);
		}
	}

	public void removeByG_R(long groupId, long roleId)
		throws SystemException {
		for (UserGroupRole userGroupRole : findByG_R(groupId, roleId)) {
			remove(userGroupRole);
		}
	}

	public void removeAll() throws SystemException {
		for (UserGroupRole userGroupRole : findAll()) {
			remove(userGroupRole);
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByU_G(long userId, long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.UserGroupRole");

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
						"value.object.listener.com.liferay.portal.model.UserGroupRole")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserGroupRole>> listenersList = new ArrayList<ModelListener<UserGroupRole>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserGroupRole>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(UserGroupRolePersistenceImpl.class);
}