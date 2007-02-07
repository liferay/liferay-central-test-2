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

package com.liferay.portal.service.http;

import com.liferay.portal.service.PermissionServiceUtil;

/**
 * <a href="PermissionServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionServiceJSON {
	public static void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.checkPermission(groupId, name, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = PermissionServiceUtil.hasGroupPermission(groupId,
				actionId, resourceId);

		return returnValue;
	}

	public static boolean hasUserPermissions(java.lang.String userId,
		long groupId, java.lang.String actionId, long[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = PermissionServiceUtil.hasUserPermissions(userId,
				groupId, actionId, resourceIds, permissionCheckerBag);

		return returnValue;
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.setGroupPermissions(groupId, actionIds, resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public static void setOrgGroupPermissions(java.lang.String organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public static void setRolePermission(java.lang.String roleId, long groupId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.setRolePermission(roleId, groupId, name, typeId,
			scope, primKey, actionId);
	}

	public static void setUserPermissions(java.lang.String userId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.setUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	public static void unsetRolePermission(java.lang.String roleId,
		long groupId, long permissionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.unsetRolePermission(roleId, groupId, permissionId);
	}

	public static void unsetRolePermission(java.lang.String roleId,
		long groupId, java.lang.String name, java.lang.String typeId,
		java.lang.String scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.unsetRolePermission(roleId, groupId, name,
			typeId, scope, primKey, actionId);
	}

	public static void unsetRolePermissions(java.lang.String roleId,
		long groupId, java.lang.String name, java.lang.String typeId,
		java.lang.String scope, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.unsetRolePermissions(roleId, groupId, name,
			typeId, scope, actionId);
	}

	public static void unsetUserPermissions(java.lang.String userId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		PermissionServiceUtil.unsetUserPermissions(userId, groupId, actionIds,
			resourceId);
	}
}