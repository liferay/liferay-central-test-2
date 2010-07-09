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

package com.liferay.portal.security.permission;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public interface PermissionCheckerBag extends Serializable {

	public List<Group> getUserGroups();

	public List<Organization> getUserOrgs();

	public List<Group> getUserOrgGroups();

	public List<Group> getUserUserGroupGroups();

	public List<Group> getGroups();

	public long[] getRoleIds();

	public List<Role> getRoles();

	public boolean isCommunityAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	public boolean isCommunityOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

}