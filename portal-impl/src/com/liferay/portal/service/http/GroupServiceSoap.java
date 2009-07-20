/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.GroupServiceUtil;

import java.rmi.RemoteException;

public class GroupServiceSoap {
	public static com.liferay.portal.model.GroupSoap addGroup(
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.addGroup(name,
					description, type, friendlyURL, active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap addGroup(
		long liveGroupId, java.lang.String name, java.lang.String description,
		int type, java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.addGroup(liveGroupId,
					name, description, type, friendlyURL, active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.addRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteGroup(long groupId) throws RemoteException {
		try {
			GroupServiceUtil.deleteGroup(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap getGroup(long groupId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getGroup(groupId);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap getGroup(long companyId,
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getGroup(companyId,
					name);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getManageableGroups(
		java.lang.String actionId, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getManageableGroups(actionId,
					max);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getOrganizationsGroups(
		com.liferay.portal.model.OrganizationSoap[] organizations)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getOrganizationsGroups(com.liferay.portal.model.impl.OrganizationModelImpl.toModels(
						organizations));

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap getUserGroup(
		long companyId, long userId) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getUserGroup(companyId,
					userId);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getUserGroupsGroups(
		com.liferay.portal.model.UserGroupSoap[] userGroups)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserGroupsGroups(com.liferay.portal.model.impl.UserGroupModelImpl.toModels(
						userGroups));

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getUserOrganizationsGroups(
		long userId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserOrganizationsGroups(userId,
					start, end);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasUserGroup(long userId, long groupId)
		throws RemoteException {
		try {
			boolean returnValue = GroupServiceUtil.hasUserGroup(userId, groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] search(long companyId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] params, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.search(companyId,
					name, description, params, start, end);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params)
		throws RemoteException {
		try {
			int returnValue = GroupServiceUtil.searchCount(companyId, name,
					description, params);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.setRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.unsetRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap updateFriendlyURL(
		long groupId, java.lang.String friendlyURL) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateFriendlyURL(groupId,
					friendlyURL);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateGroup(groupId,
					name, description, type, friendlyURL, active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap updateGroup(long groupId,
		java.lang.String typeSettings) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateGroup(groupId,
					typeSettings);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap updateWorkflow(
		long groupId, boolean workflowEnabled, int workflowStages,
		java.lang.String workflowRoleNames) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateWorkflow(groupId,
					workflowEnabled, workflowStages, workflowRoleNames);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GroupServiceSoap.class);
}