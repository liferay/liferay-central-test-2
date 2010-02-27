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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserGroupRole;

import java.util.List;

/**
 * <a href="UserGroupRoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRolePersistence
 * @see       UserGroupRolePersistenceImpl
 * @generated
 */
public class UserGroupRoleUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static UserGroupRole remove(UserGroupRole userGroupRole)
		throws SystemException {
		return getPersistence().remove(userGroupRole);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserGroupRole update(UserGroupRole userGroupRole,
		boolean merge) throws SystemException {
		return getPersistence().update(userGroupRole, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.UserGroupRole userGroupRole) {
		getPersistence().cacheResult(userGroupRole);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles) {
		getPersistence().cacheResult(userGroupRoles);
	}

	public static com.liferay.portal.model.UserGroupRole create(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK) {
		return getPersistence().create(userGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupRole remove(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupRole updateImpl(
		com.liferay.portal.model.UserGroupRole userGroupRole, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userGroupRole, merge);
	}

	public static com.liferay.portal.model.UserGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userGroupRolePK);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByUserId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userGroupRolePK, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(userGroupRolePK, groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_First(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_Last(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(userGroupRolePK, roleId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByU_G(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G(userId, groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G(userId, groupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByU_G_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G_First(userId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByU_G_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G_Last(userId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByU_G_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_PrevAndNext(userGroupRolePK, userId, groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByG_R(
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, roleId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, roleId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByG_R_First(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R_First(groupId, roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole findByG_R_Last(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R_Last(groupId, roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupRole[] findByG_R_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK,
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_R_PrevAndNext(userGroupRolePK, groupId, roleId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_G(userId, groupId);
	}

	public static void removeByG_R(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_R(groupId, roleId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_G(userId, groupId);
	}

	public static int countByG_R(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_R(groupId, roleId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserGroupRolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserGroupRolePersistence)PortalBeanLocatorUtil.locate(UserGroupRolePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserGroupRolePersistence persistence) {
		_persistence = persistence;
	}

	private static UserGroupRolePersistence _persistence;
}