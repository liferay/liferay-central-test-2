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

package com.liferay.portal.service;

/**
 * <a href="GroupService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.GroupServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be accessed
 * remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.GroupServiceFactory
 * @see com.liferay.portal.service.GroupServiceUtil
 *
 */
public interface GroupService {
	public com.liferay.portal.model.Group addGroup(java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Group addGroup(long liveGroupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void addPasswordPolicyGroups(long passwordPolicyId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void addRoleGroups(java.lang.String roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void deleteGroup(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Group getGroup(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public java.util.List getOrganizationsGroups(java.util.List organizations)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public java.util.List getUserGroupsGroups(java.util.List userGroups)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public boolean hasUserGroup(java.lang.String userId, long groupId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] params, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public void setRoleGroups(java.lang.String roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsetPasswordPolicyGroups(long passwordPolicyId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void unsetRoleGroups(java.lang.String roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;
}