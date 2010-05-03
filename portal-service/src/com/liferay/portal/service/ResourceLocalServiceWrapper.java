/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.addResource(resource);
	}

	public com.liferay.portal.model.Resource createResource(long resourceId) {
		return _resourceLocalService.createResource(resourceId);
	}

	public void deleteResource(long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.deleteResource(resourceId);
	}

	public void deleteResource(com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.deleteResource(resource);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getResource(resourceId);
	}

	public java.util.List<com.liferay.portal.model.Resource> getResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getResources(start, end);
	}

	public int getResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getResourcesCount();
	}

	public com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.updateResource(resource);
	}

	public com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.updateResource(resource, merge);
	}

	public void addModelResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public void addModelResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.addModelResources(companyId, groupId, userId,
			name, primKey, communityPermissions, guestPermissions);
	}

	public com.liferay.portal.model.Resource addResource(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.addResource(companyId, name, scope, primKey);
	}

	public void addResources(long companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.addResources(companyId, groupId, name,
			portletActions);
	}

	public void addResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey, boolean portletActions,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		boolean portletActions, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.addResources(companyId, groupId, userId, name,
			primKey, portletActions, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteResource(long companyId, java.lang.String name,
		int scope, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public void deleteResource(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.deleteResource(companyId, name, scope, primKey);
	}

	public void deleteResources(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.deleteResources(name);
	}

	public long getLatestResourceId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getLatestResourceId();
	}

	public com.liferay.portal.model.Resource getResource(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getResource(companyId, name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.Resource> getResources()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceLocalService.getResources();
	}

	public void updateResources(long companyId, long groupId,
		java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.updateResources(companyId, groupId, name,
			primKey, communityPermissions, guestPermissions);
	}

	public void updateResources(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceLocalService.updateResources(companyId, groupId, name,
			primKey, communityPermissions, guestPermissions);
	}

	public ResourceLocalService getWrappedResourceLocalService() {
		return _resourceLocalService;
	}

	private ResourceLocalService _resourceLocalService;
}