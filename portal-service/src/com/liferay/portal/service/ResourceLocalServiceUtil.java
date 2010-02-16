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
 * <a href="ResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ResourceLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceLocalService
 * @generated
 */
public class ResourceLocalServiceUtil {
	public static com.liferay.portal.model.Resource addResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResource(resource);
	}

	public static com.liferay.portal.model.Resource createResource(
		long resourceId) {
		return getService().createResource(resourceId);
	}

	public static void deleteResource(long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(resourceId);
	}

	public static void deleteResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(resource);
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

	public static com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResource(resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Resource> getResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResources(start, end);
	}

	public static int getResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcesCount();
	}

	public static com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResource(resource);
	}

	public static com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResource(resource, merge);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addModelResources(companyId, groupId, userId, name, primKey,
			communityPermissions, guestPermissions);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addModelResources(companyId, groupId, userId, name, primKey,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portal.model.Resource addResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResource(companyId, name, scope, primKey);
	}

	public static void addResources(long companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addResources(companyId, groupId, name, portletActions);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey, boolean portletActions,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResources(companyId, groupId, userId, name, primKey,
			portletActions, addCommunityPermissions, addGuestPermissions);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		boolean portletActions, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResources(companyId, groupId, userId, name, primKey,
			portletActions, addCommunityPermissions, addGuestPermissions);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResources(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResources(name);
	}

	public static long getLatestResourceId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestResourceId();
	}

	public static com.liferay.portal.model.Resource getResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResource(companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Resource> getResources()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResources();
	}

	public static void updateResources(long companyId, long groupId,
		java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResources(companyId, groupId, name, primKey,
			communityPermissions, guestPermissions);
	}

	public static void updateResources(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResources(companyId, groupId, name, primKey,
			communityPermissions, guestPermissions);
	}

	public static ResourceLocalService getService() {
		if (_service == null) {
			_service = (ResourceLocalService)PortalBeanLocatorUtil.locate(ResourceLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ResourceLocalService service) {
		_service = service;
	}

	private static ResourceLocalService _service;
}