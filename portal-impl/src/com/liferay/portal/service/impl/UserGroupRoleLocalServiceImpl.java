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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchUserGroupRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupRoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.util.List;

/**
 * <a href="UserGroupRoleLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 */
public class UserGroupRoleLocalServiceImpl
	extends UserGroupRoleLocalServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		checkGroupResource(groupId);

		for (long roleId : roleIds) {
			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRolePersistence.fetchByPrimaryKey(pk);

			if (userGroupRole == null) {
				userGroupRole = userGroupRolePersistence.create(pk);

				userGroupRolePersistence.update(userGroupRole, false);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		checkGroupResource(groupId);

		for (long userId : userIds) {
			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRolePersistence.fetchByPrimaryKey(pk);

			if (userGroupRole == null) {
				userGroupRole = userGroupRolePersistence.create(pk);

				userGroupRolePersistence.update(userGroupRole, false);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRole(UserGroupRole userGroupRole)
		throws SystemException {

		userGroupRolePersistence.remove(userGroupRole);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(
			long userId, long groupId, long[] roleIds)
		throws SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			try {
				userGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long userId, long[] groupIds)
		throws SystemException {

		for (long groupId : groupIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId)
		throws SystemException {

		for (long userId : userIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws SystemException {

		for (long userId : userIds) {
			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			try {
				userGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByGroupId(long groupId)
		throws SystemException {

		userGroupRolePersistence.removeByGroupId(groupId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByRoleId(long roleId)
		throws SystemException {

		userGroupRolePersistence.removeByRoleId(roleId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByUserId(long userId)
		throws SystemException {

		userGroupRolePersistence.removeByUserId(userId);

		PermissionCacheUtil.clearCache();
	}

	public List<UserGroupRole> getUserGroupRoles(long userId)
		throws SystemException {

		return userGroupRolePersistence.findByUserId(userId);
	}

	public List<UserGroupRole> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return userGroupRolePersistence.findByU_G(userId, groupId);
	}

	public List<UserGroupRole> getUserGroupRolesByGroupAndRole(
			long groupId, long roleId)
		throws SystemException {

		return userGroupRolePersistence.findByG_R(groupId, roleId);
	}

	public List<UserGroupRole> getUserGroupRolesByUserUserGroupAndGroup(
			long userId, long groupId)
		throws SystemException {

		return userGroupRoleFinder.findByUserUserGroupGroupRole(
			userId, groupId);
	}

	public boolean hasUserGroupRole(long userId, long groupId, long roleId)
		throws SystemException {

		return hasUserGroupRole(userId, groupId, roleId, false);
	}

	public boolean hasUserGroupRole(
			long userId, long groupId, long roleId, boolean inherit)
		throws SystemException {

		UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

		UserGroupRole userGroupRole =
			userGroupRolePersistence.fetchByPrimaryKey(pk);

		if (userGroupRole != null) {
			return true;
		}

		if (inherit) {
			if (roleFinder.countByU_G_R(userId, groupId, roleId) > 0) {
				return true;
			}
		}

		return false;
	}

	public boolean hasUserGroupRole(long userId, long groupId, String roleName)
		throws PortalException, SystemException {

		return hasUserGroupRole(userId, groupId, roleName, false);
	}

	public boolean hasUserGroupRole(
			long userId, long groupId, String roleName, boolean inherit)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long companyId = user.getCompanyId();

		Role role = rolePersistence.findByC_N(companyId, roleName);

		long roleId = role.getRoleId();

		return hasUserGroupRole(userId, groupId, roleId, inherit);
	}

	protected void checkGroupResource(long groupId)
		throws PortalException, SystemException {

		// Make sure that the individual resource for the group exists

		Group group = groupPersistence.findByPrimaryKey(groupId);

		resourceLocalService.addResource(
			group.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(groupId));
	}

}