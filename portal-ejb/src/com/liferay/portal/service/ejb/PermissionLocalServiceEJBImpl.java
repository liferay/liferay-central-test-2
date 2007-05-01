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
 * @see com.liferay.portal.service.PermissionLocalService
 * @see com.liferay.portal.service.PermissionLocalServiceUtil
 * @see com.liferay.portal.service.ejb.PermissionLocalServiceEJB
 * @see com.liferay.portal.service.ejb.PermissionLocalServiceHome
 * @see com.liferay.portal.service.impl.PermissionLocalServiceImpl
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

	public com.liferay.portal.model.Permission addPermission(long companyId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().addPermission(companyId,
			actionId, resourceId);
	}

	public java.util.List addPermissions(long companyId, java.lang.String name,
		long resourceId, boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().addPermissions(companyId,
			name, resourceId, portletActions);
	}

	public void addUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
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

	public java.util.List getGroupPermissions(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getGroupPermissions(groupId,
			companyId, name, scope, primKey);
	}

	public java.util.List getOrgGroupPermissions(long organizationId,
		long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public long getLatestPermissionId()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getLatestPermissionId();
	}

	public java.util.List getPermissions(long companyId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getPermissions(companyId,
			actionIds, resourceId);
	}

	public java.util.List getRolePermissions(long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getRolePermissions(roleId);
	}

	public java.util.List getUserPermissions(long userId, long resourceId)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getUserPermissions(userId,
			resourceId);
	}

	public java.util.List getUserPermissions(long userId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().getUserPermissions(userId,
			companyId, name, scope, primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasGroupPermission(groupId,
			actionId, resourceId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasRolePermission(roleId,
			companyId, name, scope, actionId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasRolePermission(roleId,
			companyId, name, scope, primKey, actionId);
	}

	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PermissionLocalServiceFactory.getTxImpl().hasUserPermission(userId,
			actionId, resourceId);
	}

	public boolean hasUserPermissions(long userId, long groupId,
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

	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setOrgGroupPermissions(organizationId,
			groupId, actionIds, resourceId);
	}

	public void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setRolePermission(roleId,
			companyId, name, scope, primKey, actionId);
	}

	public void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setRolePermissions(roleId,
			companyId, name, scope, primKey, actionIds);
	}

	public void setUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().setUserPermissions(userId,
			actionIds, resourceId);
	}

	public void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermission(roleId,
			permissionId);
	}

	public void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermission(roleId,
			companyId, name, scope, primKey, actionId);
	}

	public void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PermissionLocalServiceFactory.getTxImpl().unsetRolePermissions(roleId,
			companyId, name, scope, actionId);
	}

	public void unsetUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
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