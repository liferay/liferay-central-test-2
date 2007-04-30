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

package com.liferay.portal.service;

/**
 * <a href="ResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.ResourceLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ResourceLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ResourceLocalService
 * @see com.liferay.portal.service.ResourceLocalServiceFactory
 *
 */
public class ResourceLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public static com.liferay.portal.model.Resource addResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.addResource(companyId, name, scope, primKey);
	}

	public static void addResources(long companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.addResources(companyId, groupId, name,
			portletActions);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey, boolean portletActions,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		boolean portletActions, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void deleteResource(long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.deleteResource(resourceId);
	}

	public static void deleteResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.deleteResource(resource);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, long primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResources(java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();
		resourceLocalService.deleteResources(name);
	}

	public static long getLatestResourceId()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.getLatestResourceId();
	}

	public static com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.getResource(resourceId);
	}

	public static java.util.List getResources()
		throws com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.getResources();
	}

	public static com.liferay.portal.model.Resource getResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalService resourceLocalService = ResourceLocalServiceFactory.getService();

		return resourceLocalService.getResource(companyId, name, scope, primKey);
	}
}