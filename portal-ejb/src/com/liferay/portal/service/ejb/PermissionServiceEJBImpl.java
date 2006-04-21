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

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.service.spring.PermissionService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PermissionServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionServiceEJBImpl implements PermissionService, SessionBean {
	public static final String CLASS_NAME = PermissionService.class.getName() +
		".transaction";

	public static PermissionService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PermissionService)ctx.getBean(CLASS_NAME);
	}

	public boolean hasGroupPermission(java.lang.String groupId,
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public boolean hasUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String actionId,
		java.lang.String[] resourceIds,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().hasUserPermissions(userId, groupId, actionId,
			resourceIds, permissionCheckerBag);
	}

	public boolean unsetRolePermission(java.lang.String roleId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().unsetRolePermission(roleId, name, typeId, scope,
			primKey, actionId);
	}

	public boolean unsetRolePermissions(java.lang.String roleId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().unsetRolePermissions(roleId, name, typeId, scope,
			actionId);
	}

	public void setGroupPermissions(java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().setGroupPermissions(groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().setGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public void setOrgGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().setOrgGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public void setRolePermission(java.lang.String roleId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().setRolePermission(roleId, name, typeId, scope, primKey,
			actionId);
	}

	public void setUserPermissions(java.lang.String userId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().setUserPermissions(userId, actionIds, resourceId);
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