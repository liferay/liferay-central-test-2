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

import com.liferay.portal.service.spring.GroupLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="GroupLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupLocalServiceEJBImpl implements GroupLocalService, SessionBean {
	public static final String CLASS_NAME = GroupLocalService.class.getName() +
		".transaction";

	public static GroupLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (GroupLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portal.model.Group addGroup(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addGroup(companyId, className, classPK, name,
			friendlyURL);
	}

	public boolean addRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addRoleGroups(roleId, groupIds);
	}

	public boolean addUserGroups(java.lang.String userId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addUserGroups(userId, groupIds);
	}

	public void checkSystemGroups(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().checkSystemGroups(companyId);
	}

	public void deleteGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteGroup(groupId);
	}

	public boolean hasRoleGroup(java.lang.String roleId,
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasRoleGroup(roleId, groupId);
	}

	public boolean hasUserGroup(java.lang.String userId,
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().hasUserGroup(userId, groupId);
	}

	public com.liferay.portal.model.Group getFriendlyURLGroup(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFriendlyURLGroup(companyId, friendlyURL);
	}

	public com.liferay.portal.model.Group getGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getGroup(groupId);
	}

	public com.liferay.portal.model.Group getGroup(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getGroup(companyId, name);
	}

	public com.liferay.portal.model.Group getOrganizationGroup(
		java.lang.String companyId, java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getOrganizationGroup(companyId, organizationId);
	}

	public java.util.List getOrganizationGroups(java.util.List organizations)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getOrganizationGroups(organizations);
	}

	public java.util.List getRoleGroups(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getRoleGroups(roleId);
	}

	public com.liferay.portal.model.Group getUserGroup(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getUserGroup(companyId, userId);
	}

	public java.util.List getUserGroups(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getUserGroups(userId);
	}

	public java.util.List getUserGroups(java.lang.String companyId,
		java.lang.String userId, boolean privateLayout)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getUserGroups(companyId, userId, privateLayout);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.util.Map params)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, name, params);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.util.Map params, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, name, params, begin, end);
	}

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.util.Map params) throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, name, params);
	}

	public void setRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().setRoleGroups(roleId, groupIds);
	}

	public boolean unsetRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().unsetRoleGroups(roleId, groupIds);
	}

	public com.liferay.portal.model.Group updateGroup(
		java.lang.String groupId, java.lang.String name,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateGroup(groupId, name, friendlyURL);
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