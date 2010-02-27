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
 * <a href="UserGroupRoleServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserGroupRoleService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRoleService
 * @generated
 */
public class UserGroupRoleServiceUtil {
	public static void addUserGroupRoles(long userId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupRoles(userId, groupId, roleIds);
	}

	public static void addUserGroupRoles(long[] userIds, long groupId,
		long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRoles(long userId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupRoles(userId, groupId, roleIds);
	}

	public static void deleteUserGroupRoles(long[] userIds, long groupId,
		long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupRoles(userIds, groupId, roleId);
	}

	public static UserGroupRoleService getService() {
		if (_service == null) {
			_service = (UserGroupRoleService)PortalBeanLocatorUtil.locate(UserGroupRoleService.class.getName());
		}

		return _service;
	}

	public void setService(UserGroupRoleService service) {
		_service = service;
	}

	private static UserGroupRoleService _service;
}