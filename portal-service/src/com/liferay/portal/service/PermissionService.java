/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * <a href="PermissionService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface PermissionService {
	public void checkPermission(java.lang.String groupId,
		java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public boolean hasGroupPermission(java.lang.String groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public boolean hasUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String actionId,
		long[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setGroupPermissions(java.lang.String groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, java.lang.String groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setOrgGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setRolePermission(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsetRolePermission(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsetRolePermissions(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsetUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;
}