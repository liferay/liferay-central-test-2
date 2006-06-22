/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.PortalPermission;
import com.liferay.portal.service.permission.RolePermission;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.GroupService;

import java.util.List;

/**
 * <a href="GroupServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupServiceImpl extends PrincipalBean implements GroupService {

	public Group addGroup(
			String name, String description, String type, String friendlyURL)
		throws PortalException, SystemException {

		PortalPermission.check(
			getPermissionChecker(), ActionKeys.ADD_COMMUNITY);

		return GroupLocalServiceUtil.addGroup(
			getUserId(), null, null, name, description, type, friendlyURL);
	}

	public boolean addRoleGroups(String roleId, String[] groupIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		return GroupLocalServiceUtil.addRoleGroups(roleId, groupIds);
	}

	public void deleteGroup(String groupId)
		throws PortalException, SystemException {

		GroupLocalServiceUtil.deleteGroup(groupId);
	}

	public Group getGroup(String companyId, String name)
		throws PortalException, SystemException {

		return GroupLocalServiceUtil.getGroup(companyId, name);
	}

	public List getOrganizationsGroups(List organizations)
		throws PortalException, SystemException {

		return GroupLocalServiceUtil.getOrganizationsGroups(organizations);
	}

	public List getUserGroupsGroups(List userGroups)
		throws PortalException, SystemException {

		return GroupLocalServiceUtil.getUserGroupsGroups(userGroups);
	}

	public void setRoleGroups(String roleId, String[] groupIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		GroupLocalServiceUtil.setRoleGroups(roleId, groupIds);
	}

	public boolean unsetRoleGroups(String roleId, String[] groupIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		return GroupLocalServiceUtil.unsetRoleGroups(roleId, groupIds);
	}

	public Group updateGroup(
			String groupId, String name, String description, String type,
			String friendlyURL)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return GroupLocalServiceUtil.updateGroup(
			groupId, name, description, type, friendlyURL);
	}

}