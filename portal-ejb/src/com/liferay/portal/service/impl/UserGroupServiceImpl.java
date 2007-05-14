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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.PortalPermission;
import com.liferay.portal.service.permission.UserGroupPermission;

import java.util.List;

/**
 * <a href="UserGroupServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 *
 */
public class UserGroupServiceImpl
	extends PrincipalBean implements UserGroupService {

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		UserGroupLocalServiceUtil.addGroupUserGroups(
			groupId, userGroupIds);
	}

	public UserGroup addUserGroup(String name, String description)
		throws PortalException, SystemException {

		PortalPermission.check(
			getPermissionChecker(), ActionKeys.ADD_USER_GROUP);

		User user = getUser();

		return UserGroupLocalServiceUtil.addUserGroup(
			user.getUserId(), user.getCompanyId(), name, description);
	}

	public void deleteUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroupPermission.check(
			getPermissionChecker(), userGroupId, ActionKeys.DELETE);

		UserGroupLocalServiceUtil.deleteUserGroup(userGroupId);
	}

	public UserGroup getUserGroup(long userGroupId)
		throws PortalException, SystemException {

		if (!UserGroupPermission.contains(
				getPermissionChecker(), userGroupId, ActionKeys.VIEW) &&
			!UserGroupPermission.contains(
				getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_USERS)) {

			throw new PrincipalException();
		}

		return UserGroupLocalServiceUtil.getUserGroup(userGroupId);
	}

	public List getUserUserGroups(long userId)
		throws PortalException, SystemException {

		return UserGroupLocalServiceUtil.getUserUserGroups(userId);
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		UserGroupLocalServiceUtil.unsetGroupUserGroups(
			groupId, userGroupIds);
	}

	public UserGroup updateUserGroup(
			long userGroupId, String name, String description)
		throws PortalException, SystemException {

		UserGroupPermission.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		return UserGroupLocalServiceUtil.updateUserGroup(
			getUser().getCompanyId(), userGroupId, name, description);
	}

}