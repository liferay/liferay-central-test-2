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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PermissionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PermissionService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionService
 * @generated
 */
public class PermissionServiceUtil {
	public static void checkPermission(long groupId, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkPermission(groupId, resourceId);
	}

	public static void checkPermission(long groupId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkPermission(groupId, name, primKey);
	}

	public static void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkPermission(groupId, name, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException {
		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException {
		return getService().hasUserPermission(userId, actionId, resourceId);
	}

	public static boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .hasUserPermissions(userId, groupId, resources, actionId,
			permissionCheckerBag);
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setGroupPermissions(groupId, actionIds, resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.setGroupPermissions(className, classPK, groupId, actionIds,
			resourceId);
	}

	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.setOrgGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public static void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.setRolePermission(roleId, groupId, name, scope, primKey, actionId);
	}

	public static void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setRolePermissions(roleId, groupId, actionIds, resourceId);
	}

	public static void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setUserPermissions(userId, groupId, actionIds, resourceId);
	}

	public static void unsetRolePermission(long roleId, long groupId,
		long permissionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetRolePermission(roleId, groupId, permissionId);
	}

	public static void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.unsetRolePermission(roleId, groupId, name, scope, primKey, actionId);
	}

	public static void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetRolePermissions(roleId, groupId, name, scope, actionId);
	}

	public static void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetUserPermissions(userId, groupId, actionIds, resourceId);
	}

	public static PermissionService getService() {
		if (_service == null) {
			_service = (PermissionService)PortalBeanLocatorUtil.locate(PermissionService.class.getName());
		}

		return _service;
	}

	public void setService(PermissionService service) {
		_service = service;
	}

	private static PermissionService _service;
}