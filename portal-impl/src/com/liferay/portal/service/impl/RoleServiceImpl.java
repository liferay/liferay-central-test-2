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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.RoleService;
import com.liferay.portal.service.permission.PortalPermission;
import com.liferay.portal.service.permission.RolePermission;

import java.util.List;

/**
 * <a href="RoleServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RoleServiceImpl extends PrincipalBean implements RoleService {

	public Role addRole(String name, int type)
		throws PortalException, SystemException {

		User user = getUser();

		PortalPermission.check(getPermissionChecker(), ActionKeys.ADD_ROLE);

		return RoleLocalServiceUtil.addRole(
			user.getUserId(), user.getCompanyId(), name, type);
	}

	public void deleteRole(long roleId)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.DELETE);

		RoleLocalServiceUtil.deleteRole(roleId);
	}

	public Role getGroupRole(long companyId, long groupId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getGroupRole(companyId, groupId);
	}

	public List getGroupRoles(long groupId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getGroupRoles(groupId);
	}

	public Role getRole(long roleId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getRole(roleId);
	}

	public Role getRole(long companyId, String name)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getRole(companyId, name);
	}

	public List getUserGroupRoles(long userId, long groupId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getUserGroupRoles(userId, groupId);
	}

	public List getUserRelatedRoles(long userId, List groups)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getUserRelatedRoles(userId, groups);
	}

	public List getUserRoles(long userId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getUserRoles(userId);
	}

	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.hasUserRole(
			userId, companyId, name, inherited);
	}

	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.hasUserRoles(
			userId, companyId, names, inherited);
	}

	public Role updateRole(long roleId, String name)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		return RoleLocalServiceUtil.updateRole(roleId, name);
	}

}