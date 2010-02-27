/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service;


/**
 * <a href="GroupLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link GroupLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupLocalService
 * @generated
 */
public class GroupLocalServiceWrapper implements GroupLocalService {
	public GroupLocalServiceWrapper(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	public com.liferay.portal.model.Group addGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(group);
	}

	public com.liferay.portal.model.Group createGroup(long groupId) {
		return _groupLocalService.createGroup(groupId);
	}

	public void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.deleteGroup(groupId);
	}

	public void deleteGroup(com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.deleteGroup(group);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroup(groupId);
	}

	public java.util.List<com.liferay.portal.model.Group> getGroups(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroups(start, end);
	}

	public int getGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroupsCount();
	}

	public com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(group);
	}

	public com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(group, merge);
	}

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(userId, className, classPK, name,
			description, type, friendlyURL, active, serviceContext);
	}

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, long liveGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(userId, className, classPK,
			liveGroupId, name, description, type, friendlyURL, active,
			serviceContext);
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.addRoleGroups(roleId, groupIds);
	}

	public void addUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.addUserGroups(userId, groupIds);
	}

	public void checkCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.checkCompanyGroup(companyId);
	}

	public void checkSystemGroups(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.checkSystemGroups(companyId);
	}

	public com.liferay.portal.model.Group getCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroup(companyId);
	}

	public java.util.List<com.liferay.portal.model.Group> getCompanyGroups(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroups(companyId, start, end);
	}

	public int getCompanyGroupsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroupsCount(companyId);
	}

	public com.liferay.portal.model.Group getFriendlyURLGroup(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getFriendlyURLGroup(companyId, friendlyURL);
	}

	public com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroup(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Group> getGroups(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroups(groupIds);
	}

	public com.liferay.portal.model.Group getLayoutGroup(long companyId,
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutGroup(companyId, plid);
	}

	public com.liferay.portal.model.Group getLayoutPrototypeGroup(
		long companyId, long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutPrototypeGroup(companyId,
			layoutPrototypeId);
	}

	public com.liferay.portal.model.Group getLayoutSetPrototypeGroup(
		long companyId, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutSetPrototypeGroup(companyId,
			layoutSetPrototypeId);
	}

	public java.util.List<com.liferay.portal.model.Group> getNoLayoutsGroups(
		java.lang.String className, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getNoLayoutsGroups(className, privateLayout,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getNullFriendlyURLGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getNullFriendlyURLGroups();
	}

	public com.liferay.portal.model.Group getOrganizationGroup(long companyId,
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getOrganizationGroup(companyId, organizationId);
	}

	public java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _groupLocalService.getOrganizationsGroups(organizations);
	}

	public java.util.List<com.liferay.portal.model.Group> getRoleGroups(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getRoleGroups(roleId);
	}

	public com.liferay.portal.model.Group getStagingGroup(long liveGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getStagingGroup(liveGroupId);
	}

	public com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroup(companyId, userId);
	}

	public com.liferay.portal.model.Group getUserGroupGroup(long companyId,
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroupGroup(companyId, userGroupId);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, inherit);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, inherit, start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		return _groupLocalService.getUserGroupsGroups(userGroups);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserOrganizationsGroups(userId, start, end);
	}

	public boolean hasRoleGroup(long roleId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasRoleGroup(roleId, groupId);
	}

	public boolean hasStagingGroup(long liveGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasStagingGroup(liveGroupId);
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasUserGroup(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, name, description, params,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, name, description, params,
			start, end, obc);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.searchCount(companyId, name, description,
			params);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.setRoleGroups(roleId, groupIds);
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.unsetRoleGroups(roleId, groupIds);
	}

	public void unsetUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.unsetUserGroups(userId, groupIds);
	}

	public void updateAsset(long userId, com.liferay.portal.model.Group group,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.updateAsset(userId, group, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portal.model.Group updateFriendlyURL(long groupId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateFriendlyURL(groupId, friendlyURL);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(groupId, name, description, type,
			friendlyURL, active, serviceContext);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(groupId, typeSettings);
	}

	public com.liferay.portal.model.Group updateWorkflow(long groupId,
		boolean workflowEnabled, int workflowStages,
		java.lang.String workflowRoleNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateWorkflow(groupId, workflowEnabled,
			workflowStages, workflowRoleNames);
	}

	public GroupLocalService getWrappedGroupLocalService() {
		return _groupLocalService;
	}

	private GroupLocalService _groupLocalService;
}