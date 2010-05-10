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
 * <a href="ResourceActionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceActionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionLocalService
 * @generated
 */
public class ResourceActionLocalServiceWrapper
	implements ResourceActionLocalService {
	public ResourceActionLocalServiceWrapper(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	public com.liferay.portal.model.ResourceAction addResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.addResourceAction(resourceAction);
	}

	public com.liferay.portal.model.ResourceAction createResourceAction(
		long resourceActionId) {
		return _resourceActionLocalService.createResourceAction(resourceActionId);
	}

	public void deleteResourceAction(long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.deleteResourceAction(resourceActionId);
	}

	public void deleteResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ResourceAction getResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceAction(resourceActionId);
	}

	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActions(start, end);
	}

	public int getResourceActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActionsCount();
	}

	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.updateResourceAction(resourceAction);
	}

	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.updateResourceAction(resourceAction,
			merge);
	}

	public void checkResourceActions()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.checkResourceActions();
	}

	public void checkResourceActions(java.lang.String name,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.checkResourceActions(name, actionIds);
	}

	public com.liferay.portal.model.ResourceAction getResourceAction(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.getResourceAction(name, actionId);
	}

	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActions(name);
	}

	public ResourceActionLocalService getWrappedResourceActionLocalService() {
		return _resourceActionLocalService;
	}

	private ResourceActionLocalService _resourceActionLocalService;
}