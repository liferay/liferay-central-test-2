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
 * <a href="ResourcePermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ResourcePermissionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionLocalService
 * @generated
 */
public class ResourcePermissionLocalServiceUtil {
	public static com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourcePermission(resourcePermission);
	}

	public static com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return getService().createResourcePermission(resourcePermissionId);
	}

	public static void deleteResourcePermission(long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourcePermission(resourcePermissionId);
	}

	public static void deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourcePermission(resourcePermission);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> dynamicQuery(
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

	public static com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermission(resourcePermissionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermissions(start, end);
	}

	public static int getResourcePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermissionsCount();
	}

	public static com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourcePermission(resourcePermission);
	}

	public static com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourcePermission(resourcePermission, merge);
	}

	public static void addResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourcePermission(companyId, name, scope, primKey, roleId,
			actionId);
	}

	public static java.util.List<String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAvailableResourcePermissionActionIds(companyId, name,
			scope, primKey, roleId, actionIds);
	}

	public static int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourcePermissionsCount(companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleResourcePermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRoleResourcePermissions(roleId, scopes, start, end);
	}

	public static boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return getService().hasActionId(resourcePermission, resourceAction);
	}

	public static boolean hasResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasResourcePermission(companyId, name, scope, primKey,
			roleId, actionId);
	}

	public static boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasScopeResourcePermission(companyId, name, scope, roleId,
			actionId);
	}

	public static void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().mergePermissions(fromRoleId, toRoleId);
	}

	public static void reassignPermissions(long resourcePermissionId,
		long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().reassignPermissions(resourcePermissionId, toRoleId);
	}

	public static void removeResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermission(companyId, name, scope, primKey, roleId,
			actionId);
	}

	public static void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermissions(companyId, name, scope, roleId, actionId);
	}

	public static void setResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setResourcePermissions(companyId, name, scope, primKey, roleId,
			actionIds);
	}

	public static ResourcePermissionLocalService getService() {
		if (_service == null) {
			_service = (ResourcePermissionLocalService)PortalBeanLocatorUtil.locate(ResourcePermissionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ResourcePermissionLocalService service) {
		_service = service;
	}

	private static ResourcePermissionLocalService _service;
}