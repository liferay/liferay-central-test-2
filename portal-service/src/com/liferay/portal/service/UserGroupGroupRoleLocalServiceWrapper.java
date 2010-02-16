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
 * <a href="UserGroupGroupRoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupGroupRoleLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRoleLocalService
 * @generated
 */
public class UserGroupGroupRoleLocalServiceWrapper
	implements UserGroupGroupRoleLocalService {
	public UserGroupGroupRoleLocalServiceWrapper(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	public com.liferay.portal.model.UserGroupGroupRole addUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.addUserGroupGroupRole(userGroupGroupRole);
	}

	public com.liferay.portal.model.UserGroupGroupRole createUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return _userGroupGroupRoleLocalService.createUserGroupGroupRole(userGroupGroupRolePK);
	}

	public void deleteUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRole(userGroupGroupRolePK);
	}

	public void deleteUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRole(userGroupGroupRole);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portal.model.UserGroupGroupRole getUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRole(userGroupGroupRolePK);
	}

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(start, end);
	}

	public int getUserGroupGroupRolesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesCount();
	}

	public com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.updateUserGroupGroupRole(userGroupGroupRole);
	}

	public com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.updateUserGroupGroupRole(userGroupGroupRole,
			merge);
	}

	public void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(userGroupId,
			groupId, roleIds);
	}

	public void addUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	public void deleteUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupId,
			groupId, roleIds);
	}

	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupId,
			groupIds);
	}

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupIds,
			groupId);
	}

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	public void deleteUserGroupGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByGroupId(groupId);
	}

	public void deleteUserGroupGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(roleId);
	}

	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(userGroupId);
	}

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(userGroupId);
	}

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(userGroupId,
			groupId);
	}

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesByGroupAndRole(groupId,
			roleId);
	}

	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(userGroupId,
			groupId, roleId);
	}

	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(userGroupId,
			groupId, roleName);
	}

	public UserGroupGroupRoleLocalService getWrappedUserGroupGroupRoleLocalService() {
		return _userGroupGroupRoleLocalService;
	}

	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;
}