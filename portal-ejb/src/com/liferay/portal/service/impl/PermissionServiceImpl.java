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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.service.spring.PermissionLocalServiceUtil;
import com.liferay.portal.service.spring.PermissionService;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.util.StringUtil;

/**
 * <a href="PermissionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionServiceImpl
	extends PrincipalBean implements PermissionService {

	public void checkPermission(String groupId, String name, String primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

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

		checkPermission(getPermissionChecker(), groupId, resourceId);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(
			String className, String classPK, String groupId,
			String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		PermissionLocalServiceUtil.setGroupPermissions(
			className, classPK, groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			String organizationId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		PermissionLocalServiceUtil.setOrgGroupPermissions(
			organizationId, groupId, actionIds, resourceId);
	}

	public void setRolePermission(
			String roleId, String groupId, String name, String typeId,
			String scope, String primKey, String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		PermissionLocalServiceUtil.setRolePermission(
			roleId, getUser().getCompanyId(), name, typeId, scope, primKey,
			actionId);
	}

	public void setUserPermissions(
			String userId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		PermissionLocalServiceUtil.setUserPermissions(
			userId, actionIds, resourceId);
	}

	public void unsetRolePermission(
			String roleId, String groupId, String name, String typeId,
			String scope, String primKey, String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		PermissionLocalServiceUtil.unsetRolePermission(
			roleId, getUser().getCompanyId(), name, typeId, scope, primKey,
			actionId);
	}

	public void unsetRolePermissions(
			String roleId, String groupId, String name, String typeId,
			String scope, String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		PermissionLocalServiceUtil.unsetRolePermissions(
			roleId, getUser().getCompanyId(), name, typeId, scope, actionId);
	}

	public void unsetUserPermissions(
			String userId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		PermissionLocalServiceUtil.unsetUserPermissions(
			userId, actionIds, resourceId);
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, String groupId,
			String resourceId)
		throws PortalException, SystemException {

		Resource resource = ResourceLocalServiceUtil.getResource(resourceId);

		checkPermission(
			permissionChecker, groupId, resource.getName(),
			resource.getPrimKey().toString());
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, String groupId, String name,
			String primKey)
		throws PortalException, SystemException {

		if (name.equals(Group.class.getName())) {
			GroupPermission.check(
				permissionChecker, primKey, ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Layout.class.getName())) {
			String layoutGroupId = StringUtil.split(primKey, ".")[1];

			layoutGroupId = StringUtil.replace(layoutGroupId, "}", "");

			GroupPermission.check(
				permissionChecker, layoutGroupId, ActionKeys.MANAGE_LAYOUTS);
		}
		else if (name.equals(User.class.getName())) {
			User user = UserLocalServiceUtil.getUserById(primKey);

			UserPermission.check(
				permissionChecker, primKey,
				user.getOrganization().getOrganizationId(),
				user.getLocation().getOrganizationId(), ActionKeys.PERMISSIONS);
		}
		else if ((primKey != null) &&
				 (primKey.indexOf(Portlet.LAYOUT_SEPARATOR) != -1)) {

			int pos = primKey.indexOf(Portlet.LAYOUT_SEPARATOR);

			String plid = primKey.substring(0, pos);

			String layoutId = Layout.getLayoutId(plid);
			String ownerId = Layout.getOwnerId(plid);

			String portletId = primKey.substring(
				pos + Portlet.LAYOUT_SEPARATOR.length() , primKey.length());

			if (!GroupPermission.contains(
					permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS) &&
				!LayoutPermission.contains(
					permissionChecker, layoutId, ownerId, ActionKeys.UPDATE) &&
				!PortletPermission.contains(
					permissionChecker, plid, portletId,
					ActionKeys.CONFIGURATION)) {

				throw new PrincipalException();
			}
		}
		else if (!permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.PERMISSIONS) &&
				 !permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.ADD_PERMISSIONS)) {

			throw new PrincipalException();
		}
	}

}