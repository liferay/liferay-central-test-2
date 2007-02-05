/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserGroupRoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserGroupRoleUtil {
	public static com.liferay.portal.model.UserGroupRole create(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK) {
		return getPersistence().create(userGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupRole remove(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(userGroupRolePK));
		}

		com.liferay.portal.model.UserGroupRole userGroupRole = getPersistence()
																   .remove(userGroupRolePK);

		if (listener != null) {
			listener.onAfterRemove(userGroupRole);
		}

		return userGroupRole;
	}

	public static com.liferay.portal.model.UserGroupRole remove(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(userGroupRole);
		}

		userGroupRole = getPersistence().remove(userGroupRole);

		if (listener != null) {
			listener.onAfterRemove(userGroupRole);
		}

		return userGroupRole;
	}

	public static com.liferay.portal.model.UserGroupRole update(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userGroupRole.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userGroupRole);
			}
			else {
				listener.onBeforeUpdate(userGroupRole);
			}
		}

		userGroupRole = getPersistence().update(userGroupRole);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userGroupRole);
			}
			else {
				listener.onAfterUpdate(userGroupRole);
			}
		}

		return userGroupRole;
	}

	public static com.liferay.portal.model.UserGroupRole update(
		com.liferay.portal.model.UserGroupRole userGroupRole,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userGroupRole.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userGroupRole);
			}
			else {
				listener.onBeforeUpdate(userGroupRole);
			}
		}

		userGroupRole = getPersistence().update(userGroupRole, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userGroupRole);
			}
			else {
				listener.onAfterUpdate(userGroupRole);
			}
		}

		return userGroupRole;
	}

	public static com.liferay.portal.model.UserGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByPrimaryKey(userGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userGroupRolePK);
	}

	public static java.util.List findByU_G(java.lang.String userId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userId, groupId);
	}

	public static java.util.List findByU_G(java.lang.String userId,
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userId, groupId, begin, end);
	}

	public static java.util.List findByU_G(java.lang.String userId,
		long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userId, groupId, begin, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByU_G_First(
		java.lang.String userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByU_G_First(userId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByU_G_Last(
		java.lang.String userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByU_G_Last(userId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByU_G_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		java.lang.String userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByU_G_PrevAndNext(userGroupRolePK, userId,
			groupId, obc);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByUserId_First(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByUserId_Last(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByUserId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByUserId_PrevAndNext(userGroupRolePK,
			userId, obc);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByGroupId_PrevAndNext(userGroupRolePK,
			groupId, obc);
	}

	public static java.util.List findByRoleId(java.lang.String roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List findByRoleId(java.lang.String roleId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, begin, end);
	}

	public static java.util.List findByRoleId(java.lang.String roleId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, begin, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByRoleId_First(
		java.lang.String roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByRoleId_First(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByRoleId_Last(
		java.lang.String roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByRoleId_Last(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		java.lang.String roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserGroupRoleException {
		return getPersistence().findByRoleId_PrevAndNext(userGroupRolePK,
			roleId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByU_G(java.lang.String userId, long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU_G(userId, groupId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByRoleId(java.lang.String roleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByU_G(java.lang.String userId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_G(userId, groupId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByRoleId(java.lang.String roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static UserGroupRolePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(UserGroupRolePersistence persistence) {
		_persistence = persistence;
	}

	private static UserGroupRoleUtil _getUtil() {
		if (_util == null) {
			_util = (UserGroupRoleUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = UserGroupRoleUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.UserGroupRole"));
	private static Log _log = LogFactory.getLog(UserGroupRoleUtil.class);
	private static UserGroupRoleUtil _util;
	private UserGroupRolePersistence _persistence;
}