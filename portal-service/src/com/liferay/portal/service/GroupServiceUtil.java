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

package com.liferay.portal.service;

/**
 * <a href="GroupServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupServiceUtil {
	public static com.liferay.portal.model.Group addGroup(
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.addGroup(name, description, type, friendlyURL);
	}

	public static void addRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();
		groupService.addRoleGroups(roleId, groupIds);
	}

	public static void deleteGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();
		groupService.deleteGroup(groupId);
	}

	public static com.liferay.portal.model.Group getGroup(
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.getGroup(groupId);
	}

	public static com.liferay.portal.model.Group getGroup(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.getGroup(companyId, name);
	}

	public static java.util.List getOrganizationsGroups(
		java.util.List organizations)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.getOrganizationsGroups(organizations);
	}

	public static java.util.List getUserGroupsGroups(java.util.List userGroups)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.getUserGroupsGroups(userGroups);
	}

	public static boolean hasUserGroup(java.lang.String userId,
		java.lang.String groupId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.hasUserGroup(userId, groupId);
	}

	public static void setRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();
		groupService.setRoleGroups(roleId, groupIds);
	}

	public static void unsetRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();
		groupService.unsetRoleGroups(roleId, groupIds);
	}

	public static com.liferay.portal.model.Group updateGroup(
		java.lang.String groupId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		GroupService groupService = GroupServiceFactory.getService();

		return groupService.updateGroup(groupId, name, description, type,
			friendlyURL);
	}
}