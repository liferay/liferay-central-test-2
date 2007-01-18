/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.service.persistence.UserGroupRoleUtil;

import java.util.List;

/**
 * <a href="UserGroupRoleLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer Zarzuela
 *
 */
public class UserGroupRoleLocalServiceImpl
	implements UserGroupRoleLocalService {

	public void addGroupRoles(String userId, long groupId, String[]roleIds)
		throws PortalException, SystemException {
		for (int i = 0; i < roleIds.length; i++) {
			String roleId = roleIds[i];
			UserGroupRolePK pk = new UserGroupRolePK(userId, roleId, groupId);
			if (UserGroupRoleUtil.fetchByPrimaryKey(pk) == null) {
				UserGroupRoleUtil.update(UserGroupRoleUtil.create(pk));
			}
		}
	}

	public void addGroupRoleUsers(String roleId, long groupId, String[]userIds)
		throws PortalException, SystemException {
		for (int i = 0; i < userIds.length; i++) {
			String userId = userIds[i];
			UserGroupRolePK pk = new UserGroupRolePK(userId, roleId, groupId);
			if (UserGroupRoleUtil.fetchByPrimaryKey(pk) == null) {
				UserGroupRoleUtil.update(UserGroupRoleUtil.create(pk));
			}
		}
	}

	public List getUserRelatedGroupRoles(String userId, long groupId)
		throws SystemException {
		return RoleFinder.findGroupRolesByU_G(userId, groupId);
	}

	public boolean hasUserGroupRole (
		String userId, String roleId, long groupId)
		throws PortalException, SystemException {
		UserGroupRolePK pk = new UserGroupRolePK(userId, roleId, groupId);
		return (UserGroupRoleUtil.fetchByPrimaryKey(pk) != null);
	}

	public void unsetGroupRoles(
		String userId, long groupId, String[]roleIds)
		throws PortalException, SystemException {
		for (int i = 0; i < roleIds.length; i++) {
			String roleId = roleIds[i];
			try {
				UserGroupRoleUtil.remove(
					new UserGroupRolePK(userId, roleId, groupId));
			} catch (NoSuchUserGroupRoleException e) {
				// Ignore: assume it's already deleted
			}
		}
	}

	public void unsetGroupRoleUsers(
		String roleId, long groupId, String[]userIds)
		throws PortalException, SystemException {
		for (int i = 0; i < userIds.length; i++) {
			String userId = userIds[i];
			try {
				UserGroupRoleUtil.remove(
					new UserGroupRolePK(userId, roleId, groupId));
			} catch (NoSuchUserGroupRoleException e) {
				// Ignore: assume it's already deleted
			}
		}
	}

	public void deleteByRoleId(String roleId)
		throws SystemException {
		UserGroupRoleUtil.removeByRoleId(roleId);
	}

	public void deleteByUserId(String userId)
		throws SystemException {
		UserGroupRoleUtil.removeByUserId(userId);
	}

	public void deleteByGroupId(long groupId)
		throws SystemException {
		UserGroupRoleUtil.removeByGroupId(groupId);
	}

}