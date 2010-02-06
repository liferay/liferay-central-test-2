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
 * <a href="UserGroupRoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserGroupRoleLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRoleLocalService
 * @generated
 */
public class UserGroupRoleLocalServiceUtil {
	public static com.liferay.portal.model.UserGroupRole addUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException {
		return getService().addUserGroupRole(userGroupRole);
	}

	public static com.liferay.portal.model.UserGroupRole createUserGroupRole(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK) {
		return getService().createUserGroupRole(userGroupRolePK);
	}

	public static void deleteUserGroupRole(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteUserGroupRole(userGroupRolePK);
	}

	public static void deleteUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRole(userGroupRole);
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

	public static com.liferay.portal.model.UserGroupRole getUserGroupRole(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserGroupRole(userGroupRolePK);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRoles(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getUserGroupRoles(start, end);
	}

	public static int getUserGroupRolesCount()
		throws com.liferay.portal.SystemException {
		return getService().getUserGroupRolesCount();
	}

	public static com.liferay.portal.model.UserGroupRole updateUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException {
		return getService().updateUserGroupRole(userGroupRole);
	}

	public static com.liferay.portal.model.UserGroupRole updateUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateUserGroupRole(userGroupRole, merge);
	}

	public static void addUserGroupRoles(long userId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addUserGroupRoles(userId, groupId, roleIds);
	}

	public static void addUserGroupRoles(long[] userIds, long groupId,
		long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRoles(long userId, long groupId,
		long[] roleIds) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRoles(userId, groupId, roleIds);
	}

	public static void deleteUserGroupRoles(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRoles(userId, groupIds);
	}

	public static void deleteUserGroupRoles(long[] userIds, long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRoles(userIds, groupId);
	}

	public static void deleteUserGroupRoles(long[] userIds, long groupId,
		long roleId) throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRolesByGroupId(groupId);
	}

	public static void deleteUserGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRolesByRoleId(roleId);
	}

	public static void deleteUserGroupRolesByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserGroupRolesByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRoles(
		long userId) throws com.liferay.portal.SystemException {
		return getService().getUserGroupRoles(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return getService().getUserGroupRoles(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRolesByGroupAndRole(
		long groupId, long roleId) throws com.liferay.portal.SystemException {
		return getService().getUserGroupRolesByGroupAndRole(groupId, roleId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRolesByUserUserGroupAndGroup(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return getService()
				   .getUserGroupRolesByUserUserGroupAndGroup(userId, groupId);
	}

	public static boolean hasUserGroupRole(long userId, long groupId,
		long roleId) throws com.liferay.portal.SystemException {
		return getService().hasUserGroupRole(userId, groupId, roleId);
	}

	public static boolean hasUserGroupRole(long userId, long groupId,
		long roleId, boolean inherit) throws com.liferay.portal.SystemException {
		return getService().hasUserGroupRole(userId, groupId, roleId, inherit);
	}

	public static boolean hasUserGroupRole(long userId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().hasUserGroupRole(userId, groupId, roleName);
	}

	public static boolean hasUserGroupRole(long userId, long groupId,
		java.lang.String roleName, boolean inherit)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().hasUserGroupRole(userId, groupId, roleName, inherit);
	}

	public static UserGroupRoleLocalService getService() {
		if (_service == null) {
			_service = (UserGroupRoleLocalService)PortalBeanLocatorUtil.locate(UserGroupRoleLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserGroupRoleLocalService service) {
		_service = service;
	}

	private static UserGroupRoleLocalService _service;
}