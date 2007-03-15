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

import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.UserGroupServiceFactory;
import com.liferay.portal.service.impl.PrincipalSessionBean;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="UserGroupServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.UserGroupService
 * @see com.liferay.portal.service.UserGroupServiceUtil
 * @see com.liferay.portal.service.ejb.UserGroupServiceEJB
 * @see com.liferay.portal.service.ejb.UserGroupServiceHome
 * @see com.liferay.portal.service.impl.UserGroupServiceImpl
 *
 */
public class UserGroupServiceEJBImpl implements UserGroupService, SessionBean {
	public void addGroupUserGroups(long groupId, java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		UserGroupServiceFactory.getTxImpl().addGroupUserGroups(groupId,
			userGroupIds);
	}

	public com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return UserGroupServiceFactory.getTxImpl().addUserGroup(name,
			description);
	}

	public void deleteUserGroup(java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		UserGroupServiceFactory.getTxImpl().deleteUserGroup(userGroupId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return UserGroupServiceFactory.getTxImpl().getUserGroup(userGroupId);
	}

	public java.util.List getUserUserGroups(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return UserGroupServiceFactory.getTxImpl().getUserUserGroups(userId);
	}

	public void unsetGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		UserGroupServiceFactory.getTxImpl().unsetGroupUserGroups(groupId,
			userGroupIds);
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(
		java.lang.String userGroupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return UserGroupServiceFactory.getTxImpl().updateUserGroup(userGroupId,
			name, description);
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