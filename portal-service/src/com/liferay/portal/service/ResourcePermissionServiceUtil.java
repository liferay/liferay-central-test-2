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
 * <a href="ResourcePermissionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ResourcePermissionService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionService
 * @generated
 */
public class ResourcePermissionServiceUtil {
	public static void addResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
	}

	public static void setIndividualResourcePermissions(long groupId,
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setIndividualResourcePermissions(groupId, companyId, name,
			primKey, roleId, actionIds);
	}

	public static void removeResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
	}

	public static void removeResourcePermissions(long groupId, long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermissions(groupId, companyId, name, scope, roleId,
			actionId);
	}

	public static ResourcePermissionService getService() {
		if (_service == null) {
			_service = (ResourcePermissionService)PortalBeanLocatorUtil.locate(ResourcePermissionService.class.getName());
		}

		return _service;
	}

	public void setService(ResourcePermissionService service) {
		_service = service;
	}

	private static ResourcePermissionService _service;
}