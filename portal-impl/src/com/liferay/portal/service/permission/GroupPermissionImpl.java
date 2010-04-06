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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;

import java.util.List;

/**
 * <a href="GroupPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GroupPermissionImpl implements GroupPermission {

	public void check(
			PermissionChecker permissionChecker, long groupId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (hasWorkflowRolePermission(
				permissionChecker.getUserId(), group, actionId)) {

			return true;
		}

		if (group.isOrganization()) {
			long organizationId = group.getClassPK();

			return OrganizationPermissionUtil.contains(
				permissionChecker, organizationId, actionId);
		}
		else if (group.isUser()) {

			// An individual user would never reach this block because he would
			// be an administrator of his own layouts. However, a user who
			// manages a set of organizations may be modifying pages of a user
			// he manages.

			long userId = group.getClassPK();

			List<Organization> organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(userId);

			for (Organization organization : organizations) {
				if (OrganizationPermissionUtil.contains(
						permissionChecker, organization.getOrganizationId(),
						ActionKeys.MANAGE_USERS)) {

					return true;
				}
			}
		}

		// Group id must be set so that users can modify their personal pages

		return permissionChecker.hasPermission(
			groupId, Group.class.getName(), groupId, actionId);
	}

	protected boolean hasWorkflowRolePermission(
			long userId, Group group, String actionId)
		throws PortalException, SystemException {

		if (!group.isWorkflowEnabled()) {
			return false;
		}

		String[] workflowRoleNames = StringUtil.split(
			group.getWorkflowRoleNames());

		if (actionId == ActionKeys.APPROVE_PROPOSAL) {
			for (String workflowRoleName : workflowRoleNames) {
				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						userId, group.getGroupId(), workflowRoleName) ||
					isMemberRole(userId, group, workflowRoleName)) {

					return true;
				}
			}
		}
		else if (actionId == ActionKeys.MANAGE_LAYOUTS) {
			String stageTwoRoleName = workflowRoleNames[0];

			String publisherRoleName = workflowRoleNames[
				workflowRoleNames.length - 1];

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(), stageTwoRoleName) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(), publisherRoleName) ||
				isMemberRole(userId, group, stageTwoRoleName) ||
				isMemberRole(userId, group, publisherRoleName)) {

				return true;
			}
		}
		else if (actionId == ActionKeys.PUBLISH_STAGING) {
			String publisherRoleName = workflowRoleNames[
				workflowRoleNames.length - 1];

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(), publisherRoleName) ||
				isMemberRole(userId, group, publisherRoleName)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isMemberRole(
			long userId, Group group, String roleName)
		throws SystemException {

		if (((group.isCommunity() &&
			  roleName.equals(RoleConstants.COMMUNITY_MEMBER)) ||
			 (group.isOrganization() &&
			  roleName.equals(RoleConstants.ORGANIZATION_MEMBER))) &&
			GroupLocalServiceUtil.hasUserGroup(userId, group.getGroupId())) {

			return true;
		}
		else {
			return false;
		}
	}

}