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

import com.liferay.portal.service.PermissionService;
import com.liferay.portal.service.PermissionServiceFactory;
import com.liferay.portal.service.impl.PrincipalSessionBean;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PermissionServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PermissionService
 * @see com.liferay.portal.service.PermissionServiceUtil
 * @see com.liferay.portal.service.ejb.PermissionServiceEJB
 * @see com.liferay.portal.service.ejb.PermissionServiceHome
 * @see com.liferay.portal.service.impl.PermissionServiceImpl
 *
 */
public class PermissionServiceEJBImpl implements PermissionService, SessionBean {
	public void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().checkPermission(groupId, name,
			primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return PermissionServiceFactory.getTxImpl().hasGroupPermission(groupId,
			actionId, resourceId);
	}

	public boolean hasUserPermissions(java.lang.String userId, long groupId,
		java.lang.String actionId, long[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return PermissionServiceFactory.getTxImpl().hasUserPermissions(userId,
			groupId, actionId, resourceIds, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().setGroupPermissions(groupId,
			actionIds, resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().setGroupPermissions(className,
			classPK, groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(java.lang.String organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().setOrgGroupPermissions(organizationId,
			groupId, actionIds, resourceId);
	}

	public void setRolePermission(java.lang.String roleId, long groupId,
		java.lang.String name, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().setRolePermission(roleId, groupId,
			name, scope, primKey, actionId);
	}

	public void setUserPermissions(java.lang.String userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().setUserPermissions(userId,
			groupId, actionIds, resourceId);
	}

	public void unsetRolePermission(java.lang.String roleId, long groupId,
		long permissionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().unsetRolePermission(roleId,
			groupId, permissionId);
	}

	public void unsetRolePermission(java.lang.String roleId, long groupId,
		java.lang.String name, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().unsetRolePermission(roleId,
			groupId, name, scope, primKey, actionId);
	}

	public void unsetRolePermissions(java.lang.String roleId, long groupId,
		java.lang.String name, java.lang.String scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().unsetRolePermissions(roleId,
			groupId, name, scope, actionId);
	}

	public void unsetUserPermissions(java.lang.String userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PermissionServiceFactory.getTxImpl().unsetUserPermissions(userId,
			groupId, actionIds, resourceId);
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