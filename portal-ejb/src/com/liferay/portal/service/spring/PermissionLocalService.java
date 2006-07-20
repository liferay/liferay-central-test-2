/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="PermissionLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface PermissionLocalService {
	public com.liferay.portal.model.Permission addPermission(
		java.lang.String companyId, java.lang.String actionId,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List addPermissions(java.lang.String companyId,
		java.lang.String name, java.lang.String resourceId,
		boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getActions(java.util.List permissions)
		throws com.liferay.portal.SystemException;

	public java.util.List getGroupPermissions(java.lang.String groupId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException;

	public java.util.List getOrgGroupPermissions(
		java.lang.String organizationId, java.lang.String groupId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException;

	public java.util.List getPermissions(java.lang.String companyId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getUserPermissions(java.lang.String userId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException;

	public boolean hasGroupPermission(java.lang.String groupId,
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasUserPermission(java.lang.String userId,
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String actionId,
		java.lang.String[] resourceIds,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void setGroupPermissions(java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void setOrgGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void setRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void setUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void unsetRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void unsetRolePermissions(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void unsetUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;
}