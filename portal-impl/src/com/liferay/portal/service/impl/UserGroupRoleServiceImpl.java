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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleServiceImpl extends UserGroupRoleServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

			throw new PrincipalException();
		}

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

			throw new PrincipalException();
		}

		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId)) {
			throw new PrincipalException();
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

			throw new PrincipalException();
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);
	}

}