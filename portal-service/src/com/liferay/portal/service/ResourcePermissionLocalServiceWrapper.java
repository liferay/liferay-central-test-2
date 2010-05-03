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
 * <a href="ResourcePermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourcePermissionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionLocalService
 * @generated
 */
public class ResourcePermissionLocalServiceWrapper
	implements ResourcePermissionLocalService {
	public ResourcePermissionLocalServiceWrapper(
		ResourcePermissionLocalService resourcePermissionLocalService) {
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.addResourcePermission(resourcePermission);
	}

	public com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return _resourcePermissionLocalService.createResourcePermission(resourcePermissionId);
	}

	public void deleteResourcePermission(long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.deleteResourcePermission(resourcePermissionId);
	}

	public void deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.deleteResourcePermission(resourcePermission);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermission(resourcePermissionId);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissions(start, end);
	}

	public int getResourcePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissionsCount();
	}

	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.updateResourcePermission(resourcePermission);
	}

	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.updateResourcePermission(resourcePermission,
			merge);
	}

	public void addResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.addResourcePermission(companyId, name,
			scope, primKey, roleId, actionId);
	}

	public java.util.List<String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getAvailableResourcePermissionActionIds(companyId,
			name, scope, primKey, roleId, actionIds);
	}

	public int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissionsCount(companyId,
			name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId,
			scopes, start, end);
	}

	public boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourcePermissionLocalService.hasActionId(resourcePermission,
			resourceAction);
	}

	public boolean hasResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.hasResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
	}

	public boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.hasScopeResourcePermission(companyId,
			name, scope, roleId, actionId);
	}

	public void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.mergePermissions(fromRoleId, toRoleId);
	}

	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.reassignPermissions(resourcePermissionId,
			toRoleId);
	}

	public void removeResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.removeResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
	}

	public void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.removeResourcePermissions(companyId,
			name, scope, roleId, actionId);
	}

	public void setResourcePermissions(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.setResourcePermissions(companyId, name,
			scope, primKey, roleId, actionIds);
	}

	public ResourcePermissionLocalService getWrappedResourcePermissionLocalService() {
		return _resourcePermissionLocalService;
	}

	private ResourcePermissionLocalService _resourcePermissionLocalService;
}