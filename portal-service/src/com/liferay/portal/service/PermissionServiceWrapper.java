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


/**
 * <a href="PermissionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PermissionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionService
 * @generated
 */
public class PermissionServiceWrapper implements PermissionService {
	public PermissionServiceWrapper(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	public void checkPermission(long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, resourceId);
	}

	public void checkPermission(long groupId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	public void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermission(userId, actionId, resourceId);
	}

	public boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermissions(userId, groupId,
			resources, actionId, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	public void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermissions(roleId, groupId, actionIds,
			resourceId);
	}

	public void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	public void unsetRolePermission(long roleId, long groupId, long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, permissionId);
	}

	public void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	public void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermissions(roleId, groupId, name, scope,
			actionId);
	}

	public void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	public PermissionService getWrappedPermissionService() {
		return _permissionService;
	}

	private PermissionService _permissionService;
}