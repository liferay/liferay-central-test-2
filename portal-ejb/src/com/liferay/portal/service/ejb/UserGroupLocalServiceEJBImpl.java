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

import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserGroupLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="UserGroupLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.UserGroupLocalService
 * @see com.liferay.portal.service.UserGroupLocalServiceUtil
 * @see com.liferay.portal.service.ejb.UserGroupLocalServiceEJB
 * @see com.liferay.portal.service.ejb.UserGroupLocalServiceHome
 * @see com.liferay.portal.service.impl.UserGroupLocalServiceImpl
 *
 */
public class UserGroupLocalServiceEJBImpl implements UserGroupLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void addGroupUserGroups(long groupId, java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalServiceFactory.getTxImpl().addGroupUserGroups(groupId,
			userGroupIds);
	}

	public com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().addUserGroup(userId,
			companyId, name, description);
	}

	public void deleteUserGroup(java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalServiceFactory.getTxImpl().deleteUserGroup(userGroupId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().getUserGroup(userGroupId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().getUserGroup(companyId,
			name);
	}

	public java.util.List getUserUserGroups(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().getUserUserGroups(userId);
	}

	public boolean hasGroupUserGroup(long groupId, java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().hasGroupUserGroup(groupId,
			userGroupId);
	}

	public java.util.List search(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params,
		int begin, int end) throws com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().search(companyId, name,
			description, params, begin, end);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params)
		throws com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().searchCount(companyId,
			name, description, params);
	}

	public void unsetGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalServiceFactory.getTxImpl().unsetGroupUserGroups(groupId,
			userGroupIds);
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(long companyId,
		java.lang.String userGroupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserGroupLocalServiceFactory.getTxImpl().updateUserGroup(companyId,
			userGroupId, name, description);
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