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
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.service.spring.PermissionLocalServiceUtil;
import com.liferay.portal.service.spring.PermissionService;

/**
 * <a href="PermissionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionServiceImpl
	extends PrincipalBean implements PermissionService {

	public boolean hasGroupPermission(
			String groupId, String actionId, String resourceId)
		throws PortalException, SystemException {

		return PermissionLocalServiceUtil.hasGroupPermission(
			groupId, actionId, resourceId);
	}

	public boolean hasUserPermissions(
			String userId, String groupId, String actionId,
			String[] resourceIds, PermissionCheckerBag permissionCheckerBag)
		throws PortalException, SystemException {

		return PermissionLocalServiceUtil.hasUserPermissions(
			userId, groupId, actionId, resourceIds, permissionCheckerBag);
	}

	public void setGroupPermissions(
			String groupId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(
			String organizationId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		PermissionLocalServiceUtil.setGroupPermissions(
			organizationId, groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			String organizationId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		PermissionLocalServiceUtil.setOrgGroupPermissions(
			organizationId, groupId, actionIds, resourceId);
	}

	public void setRolePermission(
			String roleId, String name, String typeId, String scope,
			String primKey, String actionId)
		throws PortalException, SystemException {

		PermissionLocalServiceUtil.setRolePermission(
			roleId, getUser().getCompanyId(), name, typeId, scope, primKey,
			actionId);
	}

	public void setUserPermissions(
			String userId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		PermissionLocalServiceUtil.setUserPermissions(
			userId, actionIds, resourceId);
	}

	public boolean unsetRolePermission(
			String roleId, String name, String typeId, String scope,
			String primKey, String actionId)
		throws PortalException, SystemException {

		return PermissionLocalServiceUtil.unsetRolePermission(
			roleId, getUser().getCompanyId(), name, typeId, scope, primKey,
			actionId);
	}

	public boolean unsetRolePermissions(
			String roleId, String name, String typeId, String scope,
			String actionId)
		throws PortalException, SystemException {

		return PermissionLocalServiceUtil.unsetRolePermissions(
			roleId, getUser().getCompanyId(), name, typeId, scope, actionId);
	}

	public boolean unsetUserPermissions(
			String userId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		return PermissionLocalServiceUtil.unsetUserPermissions(
			userId, actionIds, resourceId);
	}

}