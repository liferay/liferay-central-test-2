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
 * <a href="PermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PermissionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionLocalService
 * @generated
 */
public class PermissionLocalServiceWrapper implements PermissionLocalService {
	public PermissionLocalServiceWrapper(
		PermissionLocalService permissionLocalService) {
		_permissionLocalService = permissionLocalService;
	}

	public com.liferay.portal.model.Permission addPermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermission(permission);
	}

	public com.liferay.portal.model.Permission createPermission(
		long permissionId) {
		return _permissionLocalService.createPermission(permissionId);
	}

	public void deletePermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.deletePermission(permissionId);
	}

	public void deletePermission(com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.deletePermission(permission);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Permission getPermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermission(permissionId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissions(start, end);
	}

	public int getPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissionsCount();
	}

	public com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.updatePermission(permission);
	}

	public com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.updatePermission(permission, merge);
	}

	public com.liferay.portal.model.Permission addPermission(long companyId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermission(companyId, actionId,
			resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.lang.String name, long resourceId,
		boolean portletActions)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermissions(companyId, name,
			resourceId, portletActions);
	}

	public java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.util.List<String> actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermissions(companyId, actionIds,
			resourceId);
	}

	public void addUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.addUserPermissions(userId, actionIds, resourceId);
	}

	public java.util.List<String> getActions(
		java.util.List<com.liferay.portal.model.Permission> permissions) {
		return _permissionLocalService.getActions(permissions);
	}

	public java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getGroupPermissions(groupId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getGroupPermissions(groupId, companyId,
			name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.Permission> getOrgGroupPermissions(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public long getLatestPermissionId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getLatestPermissionId();
	}

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long companyId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissions(companyId, actionIds,
			resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getRolePermissions(roleId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getRolePermissions(roleId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getUserPermissions(userId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getUserPermissions(userId, companyId,
			name, scope, primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, actionId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, primKey, actionId);
	}

	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasUserPermission(userId, actionId,
			resourceId);
	}

	public boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasUserPermissions(userId, groupId,
			resources, actionId, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setGroupPermissions(groupId, actionIds,
			resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setGroupPermissions(className, classPK,
			groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermissions(roleId, companyId, name,
			scope, primKey, actionIds);
	}

	public void setRolePermissions(long roleId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermissions(roleId, actionIds, resourceId);
	}

	public void setUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setUserPermissions(userId, actionIds, resourceId);
	}

	public void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermission(roleId, permissionId);
	}

	public void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermissions(roleId, companyId, name,
			scope, actionId);
	}

	public void unsetUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetUserPermissions(userId, actionIds,
			resourceId);
	}

	public PermissionLocalService getWrappedPermissionLocalService() {
		return _permissionLocalService;
	}

	private PermissionLocalService _permissionLocalService;
}