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
 * <a href="PluginSettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PluginSettingLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingLocalService
 * @generated
 */
public class PluginSettingLocalServiceUtil {
	public static com.liferay.portal.model.PluginSetting addPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPluginSetting(pluginSetting);
	}

	public static com.liferay.portal.model.PluginSetting createPluginSetting(
		long pluginSettingId) {
		return getService().createPluginSetting(pluginSettingId);
	}

	public static void deletePluginSetting(long pluginSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePluginSetting(pluginSettingId);
	}

	public static void deletePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePluginSetting(pluginSetting);
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

	public static com.liferay.portal.model.PluginSetting getPluginSetting(
		long pluginSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPluginSetting(pluginSettingId);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> getPluginSettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPluginSettings(start, end);
	}

	public static int getPluginSettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPluginSettingsCount();
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePluginSetting(pluginSetting);
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePluginSetting(pluginSetting, merge);
	}

	public static void checkPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkPermission(userId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		return getService().getDefaultPluginSetting();
	}

	public static com.liferay.portal.model.PluginSetting getPluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPluginSetting(companyId, pluginId, pluginType);
	}

	public static boolean hasPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType) {
		return getService().hasPermission(userId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType,
		java.lang.String roles, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePluginSetting(companyId, pluginId, pluginType, roles,
			active);
	}

	public static PluginSettingLocalService getService() {
		if (_service == null) {
			_service = (PluginSettingLocalService)PortalBeanLocatorUtil.locate(PluginSettingLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PluginSettingLocalService service) {
		_service = service;
	}

	private static PluginSettingLocalService _service;
}