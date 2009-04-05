/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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


/**
 * <a href="ResourcePermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ResourcePermissionLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ResourcePermissionLocalService
 *
 */
public class ResourcePermissionLocalServiceUtil {
	public static com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException {
		return getService().addResourcePermission(resourcePermission);
	}

	public static com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return getService().createResourcePermission(resourcePermissionId);
	}

	public static void deleteResourcePermission(long resourcePermissionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteResourcePermission(resourcePermissionId);
	}

	public static void deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException {
		getService().deleteResourcePermission(resourcePermission);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getResourcePermission(resourcePermissionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getResourcePermissions(start, end);
	}

	public static int getResourcePermissionsCount()
		throws com.liferay.portal.SystemException {
		return getService().getResourcePermissionsCount();
	}

	public static com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException {
		return getService().updateResourcePermission(resourcePermission);
	}

	public static com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateResourcePermission(resourcePermission, merge);
	}

	public static java.util.List<String> getAvailableResourcePermissionActionIds(
		long resourceId, long roleId, java.lang.String name,
		java.util.List<String> actionIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getAvailableResourcePermissionActionIds(resourceId, roleId,
			name, actionIds);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.SystemException {
		return getService().getRoleResourcePermissions(roleId);
	}

	public static boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return getService().hasActionId(resourcePermission, resourceAction);
	}

	public static boolean hasResourcePermission(long resourceId, long roleId,
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .hasResourcePermission(resourceId, roleId, name, actionId);
	}

	public static boolean hasScopeResourcePermission(long roleId,
		long companyId, java.lang.String name, int scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .hasScopeResourcePermission(roleId, companyId, name, scope,
			actionId);
	}

	public static void setResourcePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.setResourcePermission(roleId, companyId, name, scope, primKey,
			actionId);
	}

	public static void setResourcePermissions(long roleId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setResourcePermissions(roleId, actionIds, resourceId);
	}

	public static void unsetResourcePermission(long roleId, long resourceId,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetResourcePermission(roleId, resourceId, actionId);
	}

	public static void unsetResourcePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.unsetResourcePermissions(roleId, companyId, name, scope, actionId);
	}

	public static ResourcePermissionLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"ResourcePermissionLocalService is not set");
		}

		return _service;
	}

	public void setService(ResourcePermissionLocalService service) {
		_service = service;
	}

	private static ResourcePermissionLocalService _service;
}