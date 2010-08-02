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
 * The utility for the portlet preferences remote service. This utility wraps {@link com.liferay.portal.service.impl.PortletPreferencesServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PortletPreferencesServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesService
 * @see com.liferay.portal.service.base.PortletPreferencesServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletPreferencesServiceImpl
 * @generated
 */
public class PortletPreferencesServiceUtil {
	public static void deleteArchivedPreferences(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArchivedPreferences(portletItemId);
	}

	public static void restoreArchivedPreferences(long groupId,
		java.lang.String name, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.restoreArchivedPreferences(groupId, name, portletId, preferences);
	}

	public static void updateArchivePreferences(long userId, long groupId,
		java.lang.String name, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateArchivePreferences(userId, groupId, name, portletId,
			preferences);
	}

	public static PortletPreferencesService getService() {
		if (_service == null) {
			_service = (PortletPreferencesService)PortalBeanLocatorUtil.locate(PortletPreferencesService.class.getName());
		}

		return _service;
	}

	public void setService(PortletPreferencesService service) {
		_service = service;
	}

	private static PortletPreferencesService _service;
}