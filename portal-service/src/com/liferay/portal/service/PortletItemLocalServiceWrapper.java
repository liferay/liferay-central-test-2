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
 * <a href="PortletItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletItemLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemLocalService
 * @generated
 */
public class PortletItemLocalServiceWrapper implements PortletItemLocalService {
	public PortletItemLocalServiceWrapper(
		PortletItemLocalService portletItemLocalService) {
		_portletItemLocalService = portletItemLocalService;
	}

	public com.liferay.portal.model.PortletItem addPortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.addPortletItem(portletItem);
	}

	public com.liferay.portal.model.PortletItem createPortletItem(
		long portletItemId) {
		return _portletItemLocalService.createPortletItem(portletItemId);
	}

	public void deletePortletItem(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletItemLocalService.deletePortletItem(portletItemId);
	}

	public void deletePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portletItemLocalService.deletePortletItem(portletItem);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.PortletItem getPortletItem(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItem(portletItemId);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItems(start, end);
	}

	public int getPortletItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItemsCount();
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.updatePortletItem(portletItem);
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.updatePortletItem(portletItem, merge);
	}

	public com.liferay.portal.model.PortletItem addPortletItem(long userId,
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.addPortletItem(userId, groupId, name,
			portletId, className);
	}

	public com.liferay.portal.model.PortletItem getPortletItem(long groupId,
		java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItem(groupId, name,
			portletId, className);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItems(groupId, className);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.getPortletItems(groupId, portletId,
			className);
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(long userId,
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletItemLocalService.updatePortletItem(userId, groupId,
			name, portletId, className);
	}

	public PortletItemLocalService getWrappedPortletItemLocalService() {
		return _portletItemLocalService;
	}

	private PortletItemLocalService _portletItemLocalService;
}