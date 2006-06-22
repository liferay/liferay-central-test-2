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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.PermissionLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PermissionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionLocalServiceEJBImpl implements PermissionLocalService,
	SessionBean {
	public static final String CLASS_NAME = PermissionLocalService.class.getName() +
		".transaction";

	public static PermissionLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PermissionLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portal.model.Permission addPermission(
		java.lang.String companyId, java.lang.String actionId,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addPermission(companyId, actionId, resourceId);
	}

	public java.util.List addPermissions(java.lang.String companyId,
		java.lang.String name, java.lang.String resourceId,
		boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addPermissions(companyId, name, resourceId,
			portletActions);
	}

	public java.util.List getActions(java.util.List permissions)
		throws com.liferay.portal.SystemException {
		return getService().getActions(permissions);
	}

	public java.util.List getGroupPermissions(java.lang.String groupId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException {
		return getService().getGroupPermissions(groupId, resourceId);
	}

	public java.util.List getOrgGroupPermissions(
		java.lang.String organizationId, java.lang.String groupId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException {
		return getService().getOrgGroupPermissions(organizationId, groupId,
			resourceId);
	}

	public java.util.List getPermissions(java.lang.String companyId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPermissions(companyId, actionIds, resourceId);
	}

	public java.util.List getUserPermissions(java.lang.String userId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException {
		return getService().getUserPermissions(userId, resourceId);
	}

	public boolean hasGroupPermission(java.lang.String groupId,
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasRolePermission(roleId, companyId, name, typeId,
			scope, actionId);
	}

	public boolean hasRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasRolePermission(roleId, companyId, name, typeId,
			scope, primKey, actionId);
	}

	public boolean hasUserPermission(java.lang.String userId,
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasUserPermission(userId, actionId, resourceId);
	}

	public boolean hasUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String actionId,
		java.lang.String[] resourceIds,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasUserPermissions(userId, groupId, actionId,
			resourceIds, permissionCheckerBag);
	}

	public void setGroupPermissions(java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setGroupPermissions(groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public void setOrgGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setOrgGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public void setRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setRolePermission(roleId, companyId, name, typeId, scope,
			primKey, actionId);
	}

	public void setUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setUserPermissions(userId, actionIds, resourceId);
	}

	public boolean unsetRolePermission(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().unsetRolePermission(roleId, companyId, name,
			typeId, scope, primKey, actionId);
	}

	public boolean unsetRolePermissions(java.lang.String roleId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().unsetRolePermissions(roleId, companyId, name,
			typeId, scope, actionId);
	}

	public boolean unsetUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().unsetUserPermissions(userId, actionIds, resourceId);
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