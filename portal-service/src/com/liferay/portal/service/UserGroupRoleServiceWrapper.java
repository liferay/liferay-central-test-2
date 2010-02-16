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

package com.liferay.portal.service;


/**
 * <a href="UserGroupRoleServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupRoleService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRoleService
 * @generated
 */
public class UserGroupRoleServiceWrapper implements UserGroupRoleService {
	public UserGroupRoleServiceWrapper(
		UserGroupRoleService userGroupRoleService) {
		_userGroupRoleService = userGroupRoleService;
	}

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupRoleService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupRoleService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupRoleService.deleteUserGroupRoles(userId, groupId, roleIds);
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupRoleService.deleteUserGroupRoles(userIds, groupId, roleId);
	}

	public UserGroupRoleService getWrappedUserGroupRoleService() {
		return _userGroupRoleService;
	}

	private UserGroupRoleService _userGroupRoleService;
}