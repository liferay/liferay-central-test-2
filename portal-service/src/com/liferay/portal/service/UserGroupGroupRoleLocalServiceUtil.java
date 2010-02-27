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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="UserGroupGroupRoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserGroupGroupRoleLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRoleLocalService
 * @generated
 */
public class UserGroupGroupRoleLocalServiceUtil {
	public static com.liferay.portal.model.UserGroupGroupRole addUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserGroupGroupRole(userGroupGroupRole);
	}

	public static com.liferay.portal.model.UserGroupGroupRole createUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return getService().createUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static void deleteUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static void deleteUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRole(userGroupGroupRole);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.UserGroupGroupRole getUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRoles(start, end);
	}

	public static int getUserGroupGroupRolesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRolesCount();
	}

	public static com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserGroupGroupRole(userGroupGroupRole);
	}

	public static com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserGroupGroupRole(userGroupGroupRole, merge);
	}

	public static void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void addUserGroupGroupRoles(long[] userGroupIds,
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	public static void deleteUserGroupGroupRoles(long userGroupId,
		long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void deleteUserGroupGroupRoles(long userGroupId,
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupId, groupIds);
	}

	public static void deleteUserGroupGroupRoles(long[] userGroupIds,
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupIds, groupId);
	}

	public static void deleteUserGroupGroupRoles(long[] userGroupIds,
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	public static void deleteUserGroupGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRolesByGroupId(groupId);
	}

	public static void deleteUserGroupGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRolesByRoleId(roleId);
	}

	public static void deleteUserGroupGroupRolesByUserGroupId(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupGroupRolesByUserGroupId(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRoles(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRoles(userGroupId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRolesByGroupAndRole(groupId, roleId);
	}

	public static boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroupGroupRole(userGroupId, groupId, roleId);
	}

	public static boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroupGroupRole(userGroupId, groupId, roleName);
	}

	public static UserGroupGroupRoleLocalService getService() {
		if (_service == null) {
			_service = (UserGroupGroupRoleLocalService)PortalBeanLocatorUtil.locate(UserGroupGroupRoleLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserGroupGroupRoleLocalService service) {
		_service = service;
	}

	private static UserGroupGroupRoleLocalService _service;
}