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
 * <a href="ResourceActionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ResourceActionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionLocalService
 * @generated
 */
public class ResourceActionLocalServiceUtil {
	public static com.liferay.portal.model.ResourceAction addResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceAction(resourceAction);
	}

	public static com.liferay.portal.model.ResourceAction createResourceAction(
		long resourceActionId) {
		return getService().createResourceAction(resourceActionId);
	}

	public static void deleteResourceAction(long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceAction(resourceActionId);
	}

	public static void deleteResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceAction(resourceAction);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.ResourceAction getResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceAction(resourceActionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceActions(start, end);
	}

	public static int getResourceActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceActionsCount();
	}

	public static com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceAction(resourceAction);
	}

	public static com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceAction(resourceAction, merge);
	}

	public static void checkResourceActions()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkResourceActions();
	}

	public static void checkResourceActions(java.lang.String name,
		java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkResourceActions(name, actionIds);
	}

	public static com.liferay.portal.model.ResourceAction getResourceAction(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getResourceAction(name, actionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceActions(name);
	}

	public static ResourceActionLocalService getService() {
		if (_service == null) {
			_service = (ResourceActionLocalService)PortalBeanLocatorUtil.locate(ResourceActionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ResourceActionLocalService service) {
		_service = service;
	}

	private static ResourceActionLocalService _service;
}