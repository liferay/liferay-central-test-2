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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.PermissionLocalService;
import com.liferay.portal.service.PermissionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PermissionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionLocalServiceEJBImpl implements PermissionLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.Permission addPermission(
		java.lang.String companyId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().addPermission(companyId,
			actionId, resourceId);
	}

	public java.util.List addPermissions(java.lang.String companyId,
		java.lang.String name, long resourceId, boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().addPermissions(companyId,
			name, resourceId, portletActions);
	}

	public void addUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().addUserPermissions(userId,
			actionIds, resourceId);
	}

	public java.util.List getActions(java.util.List permissions)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getActions(permissions);
	}

	public java.util.List getGroupPermissions(long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getGroupPermissions(groupId,
			resourceId);
	}

	public java.util.List getOrgGroupPermissions(
		java.lang.String organizationId, long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public long getLatestPermissionId()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getLatestPermissionId();
	}

	public java.util.List getPermissions(java.lang.String companyId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getPermissions(companyId,
			actionIds, resourceId);
	}

	public java.util.List getRolePermissions(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getRolePermissions(roleId);
	}

	public java.util.List getUserPermissions(java.lang.String userId,
		long resourceId) throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getUserPermissions(userId,
			resourceId);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasGroupPermission(groupId,
			actionId, resourceId);
	}

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasRolePermission(roleId,
			companyId, name, typeId, scope, actionId);
	}

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasRolePermission(roleId,
			companyId, name, typeId, scope, primKey, actionId);
	}

	public boolean hasUserPermission(java.lang.String userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasUserPermission(userId,
			actionId, resourceId);
	}

	public boolean hasUserPermissions(java.lang.String userId, long groupId,
		java.lang.String actionId, long[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasUserPermissions(userId,
			groupId, actionId, resourceIds, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setGroupPermissions(groupId,
			actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setGroupPermissions(className,
			classPK, groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(java.lang.String organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setOrgGroupPermissions(organizationId,
			groupId, actionIds, resourceId);
	}

	public void setRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setRolePermission(roleId,
			companyId, name, typeId, scope, primKey, actionId);
	}

	public void setRolePermissions(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String[] actionIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setRolePermissions(roleId,
			companyId, name, typeId, scope, primKey, actionIds);
	}

	public void setUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setUserPermissions(userId,
			actionIds, resourceId);
	}

	public void unsetRolePermission(java.lang.String roleId, long permissionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermission(roleId,
			permissionId);
	}

	public void unsetRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermission(roleId,
			companyId, name, typeId, scope, primKey, actionId);
	}

	public void unsetRolePermissions(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermissions(roleId,
			companyId, name, typeId, scope, actionId);
	}

	public void unsetUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetUserPermissions(userId,
			actionIds, resourceId);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}