/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.UserGroupGroupRole;

import java.util.List;

/**
 * <a href="UserGroupGroupRoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRolePersistence
 * @see       UserGroupGroupRolePersistenceImpl
 * @generated
 */
public class UserGroupGroupRoleUtil {
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
	public static UserGroupGroupRole remove(
		UserGroupGroupRole userGroupGroupRole) throws SystemException {
		return getPersistence().remove(userGroupGroupRole);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserGroupGroupRole update(
		UserGroupGroupRole userGroupGroupRole, boolean merge)
		throws SystemException {
		return getPersistence().update(userGroupGroupRole, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		getPersistence().cacheResult(userGroupGroupRole);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroupGroupRole> userGroupGroupRoles) {
		getPersistence().cacheResult(userGroupGroupRoles);
	}

	public static com.liferay.portal.model.UserGroupGroupRole create(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return getPersistence().create(userGroupGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupGroupRole remove(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(userGroupGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(userGroupGroupRole, merge);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(userGroupGroupRolePK);
	}

	public static com.liferay.portal.model.UserGroupGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userGroupGroupRolePK);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserGroupId(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserGroupId(userGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserGroupId(userGroupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_First(
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserGroupId_First(userGroupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_Last(
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserGroupId_Last(userGroupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByUserGroupId_PrevAndNext(userGroupGroupRolePK,
			userGroupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(userGroupGroupRolePK, groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByRoleId_First(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByRoleId_Last(roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(userGroupGroupRolePK, roleId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userGroupId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userGroupId, groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_G(userGroupId, groupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByU_G_First(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByU_G_First(userGroupId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByU_G_Last(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByU_G_Last(userGroupId, groupId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole[] findByU_G_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByU_G_PrevAndNext(userGroupGroupRolePK, userGroupId,
			groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, roleId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, roleId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByG_R_First(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_R_First(groupId, roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole findByG_R_Last(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_R_Last(groupId, roleId, obc);
	}

	public static com.liferay.portal.model.UserGroupGroupRole[] findByG_R_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_R_PrevAndNext(userGroupGroupRolePK, groupId,
			roleId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserGroupId(userGroupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU_G(userGroupId, groupId);
	}

	public static void removeByG_R(long groupId, long roleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_R(groupId, roleId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserGroupId(userGroupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_G(userGroupId, groupId);
	}

	public static int countByG_R(long groupId, long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_R(groupId, roleId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static UserGroupGroupRolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserGroupGroupRolePersistence)PortalBeanLocatorUtil.locate(UserGroupGroupRolePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserGroupGroupRolePersistence persistence) {
		_persistence = persistence;
	}

	private static UserGroupGroupRolePersistence _persistence;
}