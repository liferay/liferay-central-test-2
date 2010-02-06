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


/**
 * <a href="ResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceLocalService
 * @generated
 */
public class ResourceLocalServiceWrapper implements ResourceLocalService {
	public ResourceLocalServiceWrapper(
		ResourceLocalService resourceLocalService) {
		_resourceLocalService = resourceLocalService;
	}

	public com.liferay.portal.model.Resource addResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.addResource(resource);
	}

	public com.liferay.portal.model.Resource createResource(long resourceId) {
		return _resourceLocalService.createResource(resourceId);
	}

	public void deleteResource(long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.deleteResource(resourceId);
	}

	public void deleteResource(com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.SystemException {
		_resourceLocalService.deleteResource(resource);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _resourceLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _resourceLocalService.getResource(resourceId);
	}

	public java.util.List<com.liferay.portal.model.Resource> getResources(
		int start, int end) throws com.liferay.portal.SystemException {
		return _resourceLocalService.getResources(start, end);
	}

	public int getResourcesCount() throws com.liferay.portal.SystemException {
		return _resourceLocalService.getResourcesCount();
	}

	public com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.updateResource(resource);
	}

	public com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.updateResource(resource, merge);
	}

	public void addModelResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public void addModelResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public com.liferay.portal.model.Resource addResource(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.addResource(companyId, name, scope, primKey);
	}

	public void addResources(long companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.addResources(companyId, groupId, name,
			portletActions);
	}

	public void addResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey, boolean portletActions,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		boolean portletActions, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteResource(long companyId, java.lang.String name,
		int scope, long primKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public void deleteResource(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public void deleteResources(java.lang.String name)
		throws com.liferay.portal.SystemException {
		_resourceLocalService.deleteResources(name);
	}

	public long getLatestResourceId() throws com.liferay.portal.SystemException {
		return _resourceLocalService.getLatestResourceId();
	}

	public com.liferay.portal.model.Resource getResource(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _resourceLocalService.getResource(companyId, name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.Resource> getResources()
		throws com.liferay.portal.SystemException {
		return _resourceLocalService.getResources();
	}

	public void updateResources(long companyId, long groupId,
		java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.updateResources(companyId, groupId, name,
			primKey, communityPermissions, guestPermissions);
	}

	public void updateResources(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceLocalService.updateResources(companyId, groupId, name,
			primKey, communityPermissions, guestPermissions);
	}

	public ResourceLocalService getWrappedResourceLocalService() {
		return _resourceLocalService;
	}

	private ResourceLocalService _resourceLocalService;
}