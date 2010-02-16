/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link GroupService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupService
 * @generated
 */
public class GroupServiceWrapper implements GroupService {
	public GroupServiceWrapper(GroupService groupService) {
		_groupService = groupService;
	}

	public com.liferay.portal.model.Group addGroup(java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.addGroup(name, description, type, friendlyURL,
			active, serviceContext);
	}

	public com.liferay.portal.model.Group addGroup(long liveGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.addGroup(liveGroupId, name, description, type,
			friendlyURL, active, serviceContext);
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupService.addRoleGroups(roleId, groupIds);
	}

	public void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupService.deleteGroup(groupId);
	}

	public com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.getGroup(groupId);
	}

	public com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.getGroup(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Group> getManageableGroups(
		java.lang.String actionId, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.getManageableGroups(actionId, max);
	}

	public java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _groupService.getOrganizationsGroups(organizations);
	}

	public com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.getUserGroup(companyId, userId);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		return _groupService.getUserGroupsGroups(userGroups);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.getUserOrganizationsGroups(userId, start, end);
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupService.hasUserGroup(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.String[] params, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupService.search(companyId, name, description, params,
			start, end);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupService.searchCount(companyId, name, description, params);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupService.setRoleGroups(roleId, groupIds);
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupService.unsetRoleGroups(roleId, groupIds);
	}

	public com.liferay.portal.model.Group updateFriendlyURL(long groupId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.updateFriendlyURL(groupId, friendlyURL);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.updateGroup(groupId, name, description, type,
			friendlyURL, active, serviceContext);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.updateGroup(groupId, typeSettings);
	}

	public com.liferay.portal.model.Group updateWorkflow(long groupId,
		boolean workflowEnabled, int workflowStages,
		java.lang.String workflowRoleNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupService.updateWorkflow(groupId, workflowEnabled,
			workflowStages, workflowRoleNames);
	}

	public GroupService getWrappedGroupService() {
		return _groupService;
	}

	private GroupService _groupService;
}