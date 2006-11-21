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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.RoleLocalService;
import com.liferay.portal.service.spring.RoleLocalServiceFactory;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="RoleLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RoleLocalServiceEJBImpl implements RoleLocalService, SessionBean {
	public com.liferay.portal.model.Role addRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().addRole(userId, companyId,
			name);
	}

	public com.liferay.portal.model.Role addRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().addRole(userId, companyId,
			name, className, classPK);
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
		java.lang.String companyId, java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getGroupRole(companyId,
			groupId);
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

	public java.util.List getUserRelatedRoles(java.lang.String userId,
		java.util.List groups)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRelatedRoles(userId,
			groups);
	}

	public java.util.List getUserRoles(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().getUserRoles(userId);
	}

	public boolean hasUserRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().hasUserRole(userId,
			companyId, name);
	}

	public boolean hasUserRoles(java.lang.String userId,
		java.lang.String companyId, java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().hasUserRoles(userId,
			companyId, names);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String description, int begin, int end)
		throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().search(companyId, name,
			description, begin, end);
	}

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.lang.String description) throws com.liferay.portal.SystemException {
		return RoleLocalServiceFactory.getTxImpl().searchCount(companyId, name,
			description);
	}

	public void setUserRoles(java.lang.String userId, java.lang.String[] roleIds)
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