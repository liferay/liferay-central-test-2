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