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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PortletItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PortletItemLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemLocalService
 * @generated
 */
public class PortletItemLocalServiceUtil {
	public static com.liferay.portal.model.PortletItem addPortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortletItem(portletItem);
	}

	public static com.liferay.portal.model.PortletItem createPortletItem(
		long portletItemId) {
		return getService().createPortletItem(portletItemId);
	}

	public static void deletePortletItem(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortletItem(portletItemId);
	}

	public static void deletePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortletItem(portletItem);
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

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItem(portletItemId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItems(start, end);
	}

	public static int getPortletItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItemsCount();
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortletItem(portletItem);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortletItem(portletItem, merge);
	}

	public static com.liferay.portal.model.PortletItem addPortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPortletItem(userId, groupId, name, portletId, className);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItem(groupId, name, portletId, className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItems(groupId, className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletItems(groupId, portletId, className);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePortletItem(userId, groupId, name, portletId,
			className);
	}

	public static PortletItemLocalService getService() {
		if (_service == null) {
			_service = (PortletItemLocalService)PortalBeanLocatorUtil.locate(PortletItemLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PortletItemLocalService service) {
		_service = service;
	}

	private static PortletItemLocalService _service;
}