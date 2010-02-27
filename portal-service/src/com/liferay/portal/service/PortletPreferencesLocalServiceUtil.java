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
 * <a href="PortletPreferencesLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PortletPreferencesLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesLocalService
 * @generated
 */
public class PortletPreferencesLocalServiceUtil {
	public static com.liferay.portal.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortletPreferences(portletPreferences);
	}

	public static com.liferay.portal.model.PortletPreferences createPortletPreferences(
		long portletPreferencesId) {
		return getService().createPortletPreferences(portletPreferencesId);
	}

	public static void deletePortletPreferences(long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortletPreferences(portletPreferencesId);
	}

	public static void deletePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortletPreferences(portletPreferences);
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

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferences(portletPreferencesId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferenceses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferenceses(start, end);
	}

	public static int getPortletPreferencesesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferencesesCount();
	}

	public static com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortletPreferences(portletPreferences);
	}

	public static com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortletPreferences(portletPreferences, merge);
	}

	public static com.liferay.portal.model.PortletPreferences addPortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, com.liferay.portal.model.Portlet portlet,
		java.lang.String defaultPreferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPortletPreferences(companyId, ownerId, ownerType, plid,
			portletId, portlet, defaultPreferences);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortletPreferences(ownerId, ownerType, plid);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deletePortletPreferences(ownerId, ownerType, plid, portletId);
	}

	public static javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultPreferences(companyId, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferences();
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferences(plid, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferences(ownerId, ownerType, plid);
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPortletPreferences(ownerId, ownerType, plid, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletPreferencesByPlid(plid);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPreferences(companyId, ownerId, ownerType, plid,
			portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, java.lang.String defaultPreferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPreferences(companyId, ownerId, ownerType, plid,
			portletId, defaultPreferences);
	}

	public static com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePreferences(ownerId, ownerType, plid, portletId,
			preferences);
	}

	public static com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePreferences(ownerId, ownerType, plid, portletId, xml);
	}

	public static PortletPreferencesLocalService getService() {
		if (_service == null) {
			_service = (PortletPreferencesLocalService)PortalBeanLocatorUtil.locate(PortletPreferencesLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PortletPreferencesLocalService service) {
		_service = service;
	}

	private static PortletPreferencesLocalService _service;
}