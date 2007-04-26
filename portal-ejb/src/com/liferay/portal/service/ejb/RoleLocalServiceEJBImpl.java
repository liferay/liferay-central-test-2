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

import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.RoleLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="RoleLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.RoleLocalService
 * @see com.liferay.portal.service.RoleLocalServiceUtil
 * @see com.liferay.portal.service.ejb.RoleLocalServiceEJB
 * @see com.liferay.portal.service.ejb.RoleLocalServiceHome
 * @see com.liferay.portal.service.impl.RoleLocalServiceImpl
 *
 */
public class RoleLocalServiceEJBImpl implements RoleLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.Role addRole(long userId,
		java.lang.String companyId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().addRole(userId, companyId,
			name, type);
	}

	public com.liferay.portal.model.Role addRole(long userId,
		java.lang.String companyId, java.lang.String name, int type,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().addRole(userId, companyId,
			name, type, className, classPK);
	}

	public void checkSystemRoles(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalServiceFactory.getTxImpl().checkSystemRoles(companyId);
	}

	public void deleteRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalServiceFactory.getTxImpl().deleteRole(roleId);
	}

	public com.liferay.portal.model.Role getGroupRole(
		java.lang.String companyId, long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getGroupRole(companyId,
			groupId);
	}

	public java.util.List getGroupRoles(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getGroupRoles(groupId);
	}

	public java.util.Map getResourceRoles(java.lang.String companyId,
		java.lang.String name, java.lang.String scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getResourceRoles(companyId,
			name, scope, primKey);
	}

	public com.liferay.portal.model.Role getRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getRole(roleId);
	}

	public com.liferay.portal.model.Role getRole(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getRole(companyId, name);
	}

	public java.util.List getUserGroupRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserGroupRoles(userId,
			groupId);
	}

	public java.util.List getUserRelatedRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRelatedRoles(userId,
			groupId);
	}

	public java.util.List getUserRelatedRoles(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRelatedRoles(userId,
			groupIds);
	}

	public java.util.List getUserRelatedRoles(long userId, java.util.List groups)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRelatedRoles(userId,
			groups);
	}

	public java.util.List getUserRoles(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRoles(userId);
	}

	public boolean hasUserRole(long userId, java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().hasUserRole(userId,
			companyId, name);
	}

	public boolean hasUserRoles(long userId, java.lang.String companyId,
		java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().hasUserRoles(userId,
			companyId, names);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String description,
		java.lang.Integer type, int begin, int end)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().search(companyId, name,
			description, type, begin, end);
	}

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().searchCount(companyId, name,
			description, type);
	}

	public void setUserRoles(long userId, java.lang.String[] roleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalServiceFactory.getTxImpl().setUserRoles(userId, roleIds);
	}

	public com.liferay.portal.model.Role updateRole(java.lang.String roleId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().updateRole(roleId, name);
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