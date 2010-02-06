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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.base.UserGroupGroupRoleServiceBaseImpl;

/**
 * <a href="UserGroupGroupRoleServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brett Swaim
 */
public class UserGroupGroupRoleServiceImpl
	extends UserGroupGroupRoleServiceBaseImpl {

	public void addUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId)) {
			throw new PrincipalException();
		}

		userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	public void addUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId)) {
			throw new PrincipalException();
		}

		userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	public void deleteUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId)) {
			throw new PrincipalException();
		}

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	public void deleteUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isCommunityOwner(groupId)) {
			throw new PrincipalException();
		}

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

}