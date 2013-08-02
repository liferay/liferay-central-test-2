/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Charles May
 * @author Jorge Ferrer
 */
public class UserPermissionImpl implements UserPermission {

	/**
	 * @deprecated Replaced by {@link #check(PermissionChecker, long, long[],
	 *             String)}
	 */
	@Override
	public void check(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long locationId, String actionId)
		throws PrincipalException {

		check(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long userId,
			long[] organizationIds, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userId, organizationIds, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long userId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userId, actionId)) {
			throw new PrincipalException();
		}
	}

	/**
	 * @deprecated Replaced by {@link #contains(PermissionChecker, long, long[],
	 *             String)}
	 */
	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userId, long organizationId,
		long locationId, String actionId) {

		return contains(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userId,
		long[] organizationIds, String actionId) {

		if ((actionId.equals(ActionKeys.DELETE) ||
			 actionId.equals(ActionKeys.IMPERSONATE) ||
			 actionId.equals(ActionKeys.PERMISSIONS) ||
			 actionId.equals(ActionKeys.UPDATE)) &&
			PortalUtil.isOmniadmin(userId) &&
			!permissionChecker.isOmniadmin()) {

			return false;
		}

		try {
			User user = null;

			if (userId != ResourceConstants.PRIMKEY_DNE) {
				user = UserLocalServiceUtil.getUserById(userId);

				Contact contact = user.getContact();

				if ((((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) ||
					  (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6)) &&
					 permissionChecker.hasOwnerPermission(
						 permissionChecker.getCompanyId(), User.class.getName(),
						 userId, contact.getUserId(), actionId)) ||
					(permissionChecker.getUserId() == userId)) {

					return true;
				}
			}

			if (permissionChecker.hasPermission(
					0, User.class.getName(), userId, actionId)) {

				return true;
			}

			if (user == null) {
				return false;
			}

			if (organizationIds == null) {
				organizationIds = user.getOrganizationIds();
			}

			for (long organizationId : organizationIds) {
				if (OrganizationPermissionUtil.contains(
						permissionChecker, organizationId,
						ActionKeys.MANAGE_USERS)) {

					if (permissionChecker.getUserId() == user.getUserId()) {
						return true;
					}

					Organization organization =
						OrganizationLocalServiceUtil.getOrganization(
							organizationId);

					Group organizationGroup = organization.getGroup();

					// Organization administrators can only manage normal users.
					// Owners can only manage normal users and administrators.

					if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
							user.getUserId(), organizationGroup.getGroupId(),
							RoleConstants.ORGANIZATION_OWNER, true)) {

						continue;
					}
					else if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								user.getUserId(),
								organizationGroup.getGroupId(),
								RoleConstants.ORGANIZATION_ADMINISTRATOR,
								true) &&
							 !UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								permissionChecker.getUserId(),
								organizationGroup.getGroupId(),
								RoleConstants.ORGANIZATION_OWNER, true)) {

						continue;
					}

					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userId, String actionId) {

		return contains(permissionChecker, userId, null, actionId);
	}

	@Override
	public boolean hasMembershipProtected(
			PermissionChecker permissionChecker, Group group, Role role,
			User user)
		throws PortalException, SystemException {

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					group.getClassPK());

			return hasMembershipProtected(
				permissionChecker, organization, role, user);
		}

		if (permissionChecker.isGroupOwner(group.getGroupId())) {
			return false;
		}

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.SITE_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasMembershipProtected(
			PermissionChecker permissionChecker, Group group, User user)
		throws PortalException, SystemException {

		if (permissionChecker.isGroupOwner(group.getGroupId())) {
			return false;
		}

		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				siteAdministratorRole.getRoleId())) {

			return true;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				siteOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasMembershipProtected(
			PermissionChecker permissionChecker, Organization organization,
			Role role, User user)
		throws SystemException {

		Group group = organization.getGroup();

		if (permissionChecker.isOrganizationOwner(group.getOrganizationId())) {
			return false;
		}

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasMembershipProtected(
			PermissionChecker permissionChecker, Organization organization,
			User user)
		throws PortalException, SystemException {

		Group group = organization.getGroup();

		if (permissionChecker.isOrganizationOwner(group.getOrganizationId())) {
			return false;
		}

		Role organizationAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				organizationAdministratorRole.getRoleId())) {

			return true;
		}

		Role organizationOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				organizationOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(UserPermissionImpl.class);

}