/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="PermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionLocalServiceUtil {
	public static com.liferay.portal.model.Permission addPermission(
		java.lang.String companyId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.addPermission(companyId, actionId,
			resourceId);
	}

	public static java.util.List addPermissions(java.lang.String companyId,
		java.lang.String name, long resourceId, boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.addPermissions(companyId, name,
			resourceId, portletActions);
	}

	public static void addUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.addUserPermissions(userId, actionIds, resourceId);
	}

	public static java.util.List getActions(java.util.List permissions)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getActions(permissions);
	}

	public static java.util.List getGroupPermissions(long groupId,
		long resourceId) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getGroupPermissions(groupId, resourceId);
	}

	public static java.util.List getOrgGroupPermissions(
		java.lang.String organizationId, long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public static java.util.List getPermissions(java.lang.String companyId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getPermissions(companyId, actionIds,
			resourceId);
	}

	public static java.util.List getUserPermissions(java.lang.String userId,
		long resourceId) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getUserPermissions(userId, resourceId);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public static boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasRolePermission(roleId, companyId,
			name, typeId, scope, actionId);
	}

	public static boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasRolePermission(roleId, companyId,
			name, typeId, scope, primKey, actionId);
	}

	public static boolean hasUserPermission(java.lang.String userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasUserPermission(userId, actionId,
			resourceId);
	}

	public static boolean hasUserPermissions(java.lang.String userId,
		long groupId, java.lang.String actionId, long[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasUserPermissions(userId, groupId,
			actionId, resourceIds, permissionCheckerBag);
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.setGroupPermissions(groupId, actionIds,
			resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public static void setOrgGroupPermissions(java.lang.String organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public static void setRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.setRolePermission(roleId, companyId, name,
			typeId, scope, primKey, actionId);
	}

	public static void setUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.setUserPermissions(userId, actionIds, resourceId);
	}

	public static void unsetRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.unsetRolePermission(roleId, companyId, name,
			typeId, scope, primKey, actionId);
	}

	public static void unsetRolePermissions(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.unsetRolePermissions(roleId, companyId, name,
			typeId, scope, actionId);
	}

	public static void unsetUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();
		permissionLocalService.unsetUserPermissions(userId, actionIds,
			resourceId);
	}
}