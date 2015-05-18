/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserGroupServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * user groups. Its methods include permission checks.
 *
 * @author Charles May
 */
public class UserGroupServiceImpl extends UserGroupServiceBaseImpl {

	/**
	 * Adds the user groups to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws PortalException if a group or user group with the primary key
	 *         could not be found, or if the user did not have permission to
	 *         assign group members
	 */
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	/**
	 * Adds the user groups to the team
	 *
	 * @param  teamId the primary key of the team
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws PortalException if a team or user group with the primary key
	 *         could not be found, or if the user did not have permission to
	 *         assign team members
	 */
	@Override
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addTeamUserGroups(teamId, userGroupIds);
	}

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param      name the user group's name
	 * @param      description the user group's description
	 * @return     the user group
	 * @throws     PortalException if the user group's information was invalid
	 *             or if the user did not have permission to add the user group
	 * @deprecated As of 6.2.0, replaced by {@link #addUserGroup(String, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public UserGroup addUserGroup(String name, String description)
		throws PortalException {

		return addUserGroup(name, description, null);
	}

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param  name the user group's name
	 * @param  description the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 * @throws PortalException if the user group's information was invalid or if
	 *         the user did not have permission to add the user group
	 */
	@Override
	public UserGroup addUserGroup(
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_USER_GROUP);

		User user = getUser();

		UserGroup userGroup = userGroupLocalService.addUserGroup(
			user.getUserId(), user.getCompanyId(), name, description,
			serviceContext);

		UserGroupMembershipPolicyUtil.verifyPolicy(userGroup);

		return userGroup;
	}

	/**
	 * Deletes the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @throws PortalException if a user group with the primary key could not be
	 *         found, if the user did not have permission to delete the user
	 *         group, or if the user group had a workflow in approved status
	 */
	@Override
	public void deleteUserGroup(long userGroupId) throws PortalException {
		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.DELETE);

		userGroupLocalService.deleteUserGroup(userGroupId);
	}

	@Override
	public UserGroup fetchUserGroup(long userGroupId) throws PortalException {
		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroupLocalService.fetchUserGroup(userGroupId);
	}

	/**
	 * Returns the user group with the primary key.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @return Returns the user group with the primary key
	 * @throws PortalException if a user group with the primary key could not be
	 *         found or if the user did not have permission to view the user
	 *         group
	 */
	@Override
	public UserGroup getUserGroup(long userGroupId) throws PortalException {
		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroupLocalService.getUserGroup(userGroupId);
	}

	/**
	 * Returns the user group with the name.
	 *
	 * @param  name the user group's name
	 * @return Returns the user group with the name
	 * @throws PortalException if a user group with the name could not be found
	 *         or if the user did not have permission to view the user group
	 */
	@Override
	public UserGroup getUserGroup(String name) throws PortalException {
		User user = getUser();

		UserGroup userGroup = userGroupLocalService.getUserGroup(
			user.getCompanyId(), name);

		long userGroupId = userGroup.getUserGroupId();

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroup;
	}

	@Override
	public List<UserGroup> getUserGroups(long companyId)
		throws PortalException {

		return filterUserGroups(userGroupLocalService.getUserGroups(companyId));
	}

	/**
	 * Returns all the user groups to which the user belongs.
	 *
	 * @param  userId the primary key of the user
	 * @return the user groups to which the user belongs
	 * @throws PortalException if the current user did not have permission to
	 *         view the user or any one of the user group members
	 */
	@Override
	public List<UserGroup> getUserUserGroups(long userId)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<UserGroup> userGroups = userGroupLocalService.getUserUserGroups(
			userId);

		return filterUserGroups(userGroups);
	}

	/**
	 * Removes the user groups from the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws PortalException if the user did not have permission to assign
	 *         group members
	 */
	@Override
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	/**
	 * Removes the user groups from the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws PortalException if the user did not have permission to assign
	 *         team members
	 */
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetTeamUserGroups(teamId, userGroupIds);
	}

	/**
	 * Updates the user group.
	 *
	 * @param      userGroupId the primary key of the user group
	 * @param      name the user group's name
	 * @param      description the the user group's description
	 * @return     the user group
	 * @throws     PortalException if a user group with the primary key was not
	 *             found, if the new information was invalid, or if the user did
	 *             not have permission to update the user group information
	 * @deprecated As of 6.2.0, replaced by {@link #updateUserGroup(long,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public UserGroup updateUserGroup(
			long userGroupId, String name, String description)
		throws PortalException {

		UserGroup oldUserGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		ExpandoBridge oldExpandoBridge = oldUserGroup.getExpandoBridge();

		Map<String, Serializable> oldExpandoAttributes =
			oldExpandoBridge.getAttributes();

		UserGroup userGroup = updateUserGroup(
			userGroupId, name, description, null);

		UserGroupMembershipPolicyUtil.verifyPolicy(
			userGroup, oldUserGroup, oldExpandoAttributes);

		return userGroup;
	}

	/**
	 * Updates the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  name the user group's name
	 * @param  description the the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 * @throws PortalException if a user group with the primary key was not
	 *         found, if the new information was invalid, or if the user did not
	 *         have permission to update the user group information
	 */
	@Override
	public UserGroup updateUserGroup(
			long userGroupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		User user = getUser();

		return userGroupLocalService.updateUserGroup(
			user.getCompanyId(), userGroupId, name, description,
			serviceContext);
	}

	protected List<UserGroup> filterUserGroups(List<UserGroup> userGroups)
		throws PortalException {

		List<UserGroup> filteredGroups = new ArrayList<>();

		for (UserGroup userGroup : userGroups) {
			if (UserGroupPermissionUtil.contains(
					getPermissionChecker(), userGroup.getUserGroupId(),
					ActionKeys.VIEW)) {

				filteredGroups.add(userGroup);
			}
		}

		return filteredGroups;
	}

}