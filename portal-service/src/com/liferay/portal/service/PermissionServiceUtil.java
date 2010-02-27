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
 * <a href="PermissionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PermissionService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionService
 * @generated
 */
public class PermissionServiceUtil {
	public static void checkPermission(long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPermission(groupId, resourceId);
	}

	public static void checkPermission(long groupId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPermission(groupId, name, primKey);
	}

	public static void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPermission(groupId, name, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserPermission(userId, actionId, resourceId);
	}

	public static boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasUserPermissions(userId, groupId, resources, actionId,
			permissionCheckerBag);
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setGroupPermissions(groupId, actionIds, resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setGroupPermissions(className, classPK, groupId, actionIds,
			resourceId);
	}

	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setOrgGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public static void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setRolePermission(roleId, groupId, name, scope, primKey, actionId);
	}

	public static void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setRolePermissions(roleId, groupId, actionIds, resourceId);
	}

	public static void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserPermissions(userId, groupId, actionIds, resourceId);
	}

	public static void unsetRolePermission(long roleId, long groupId,
		long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRolePermission(roleId, groupId, permissionId);
	}

	public static void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetRolePermission(roleId, groupId, name, scope, primKey, actionId);
	}

	public static void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRolePermissions(roleId, groupId, name, scope, actionId);
	}

	public static void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserPermissions(userId, groupId, actionIds, resourceId);
	}

	public static PermissionService getService() {
		if (_service == null) {
			_service = (PermissionService)PortalBeanLocatorUtil.locate(PermissionService.class.getName());
		}

		return _service;
	}

	public void setService(PermissionService service) {
		_service = service;
	}

	private static PermissionService _service;
}