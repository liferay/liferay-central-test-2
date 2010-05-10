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
 * <a href="PermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PermissionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionLocalService
 * @generated
 */
public class PermissionLocalServiceUtil {
	public static com.liferay.portal.model.Permission addPermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermission(permission);
	}

	public static com.liferay.portal.model.Permission createPermission(
		long permissionId) {
		return getService().createPermission(permissionId);
	}

	public static void deletePermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePermission(permissionId);
	}

	public static void deletePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePermission(permission);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Permission getPermission(
		long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermission(permissionId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissions(start, end);
	}

	public static int getPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissionsCount();
	}

	public static com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePermission(permission);
	}

	public static com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePermission(permission, merge);
	}

	public static com.liferay.portal.model.Permission addPermission(
		long companyId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermission(companyId, actionId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.lang.String name, long resourceId,
		boolean portletActions)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPermissions(companyId, name, resourceId, portletActions);
	}

	public static java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.util.List<java.lang.String> actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermissions(companyId, actionIds, resourceId);
	}

	public static void addUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserPermissions(userId, actionIds, resourceId);
	}

	public static java.util.List<java.lang.String> getActions(
		java.util.List<com.liferay.portal.model.Permission> permissions) {
		return getService().getActions(permissions);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupPermissions(groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupPermissions(groupId, companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getOrgGroupPermissions(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getOrgGroupPermissions(organizationId, groupId, resourceId);
	}

	public static long getLatestPermissionId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestPermissionId();
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long companyId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissions(companyId, actionIds, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRolePermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRolePermissions(roleId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserPermissions(userId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserPermissions(userId, companyId, name, scope, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasRolePermission(roleId, companyId, name, scope, actionId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasRolePermission(roleId, companyId, name, scope, primKey,
			actionId);
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

	public static void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setRolePermission(roleId, companyId, name, scope, primKey, actionId);
	}

	public static void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setRolePermissions(roleId, companyId, name, scope, primKey,
			actionIds);
	}

	public static void setRolePermissions(long roleId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setRolePermissions(roleId, actionIds, resourceId);
	}

	public static void setUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserPermissions(userId, actionIds, resourceId);
	}

	public static void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRolePermission(roleId, permissionId);
	}

	public static void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetRolePermission(roleId, companyId, name, scope, primKey,
			actionId);
	}

	public static void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetRolePermissions(roleId, companyId, name, scope, actionId);
	}

	public static void unsetUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserPermissions(userId, actionIds, resourceId);
	}

	public static PermissionLocalService getService() {
		if (_service == null) {
			_service = (PermissionLocalService)PortalBeanLocatorUtil.locate(PermissionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PermissionLocalService service) {
		_service = service;
	}

	private static PermissionLocalService _service;
}