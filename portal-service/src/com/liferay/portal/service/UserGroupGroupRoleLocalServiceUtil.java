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
		throws com.liferay.portal.SystemException {
		return getService().addUserGroupGroupRole(userGroupGroupRole);
	}

	public static com.liferay.portal.model.UserGroupGroupRole createUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return getService().createUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static void deleteUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static void deleteUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRole(userGroupGroupRole);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.UserGroupGroupRole getUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRole(userGroupGroupRolePK);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRoles(start, end);
	}

	public static int getUserGroupGroupRolesCount()
		throws com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRolesCount();
	}

	public static com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.SystemException {
		return getService().updateUserGroupGroupRole(userGroupGroupRole);
	}

	public static com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateUserGroupGroupRole(userGroupGroupRole, merge);
	}

	public static void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void addUserGroupGroupRoles(long[] userGroupIds,
		long groupId, long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	public static void deleteUserGroupGroupRoles(long userGroupId,
		long groupId, long[] roleIds) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void deleteUserGroupGroupRoles(long userGroupId,
		long[] groupIds) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupId, groupIds);
	}

	public static void deleteUserGroupGroupRoles(long[] userGroupIds,
		long groupId) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupIds, groupId);
	}

	public static void deleteUserGroupGroupRoles(long[] userGroupIds,
		long groupId, long roleId) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	public static void deleteUserGroupGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRolesByGroupId(groupId);
	}

	public static void deleteUserGroupGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRolesByRoleId(roleId);
	}

	public static void deleteUserGroupGroupRolesByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupGroupRolesByUserGroupId(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId) throws com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRoles(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRoles(userGroupId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId) throws com.liferay.portal.SystemException {
		return getService().getUserGroupGroupRolesByGroupAndRole(groupId, roleId);
	}

	public static boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId) throws com.liferay.portal.SystemException {
		return getService().hasUserGroupGroupRole(userGroupId, groupId, roleId);
	}

	public static boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
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