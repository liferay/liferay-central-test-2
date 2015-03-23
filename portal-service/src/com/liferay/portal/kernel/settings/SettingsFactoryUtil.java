/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Layout;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public class SettingsFactoryUtil {

	public static void clearCache() {
		getSettingsFactory().clearCache();
	}

	public static Settings getCompanyServiceSettings(
		long companyId, String serviceName) {

		return getSettingsFactory().getCompanyServiceSettings(
			companyId, serviceName);
	}

	public static Settings getGroupServiceSettings(
			long groupId, String serviceName)
		throws SettingsException {

		return getSettingsFactory().getGroupServiceSettings(
			groupId, serviceName);
	}

	public static ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		return getSettingsFactory().getPortletInstanceArchivedSettings(
			groupId, portletId, name);
	}

	public static List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		return getSettingsFactory().getPortletInstanceArchivedSettingsList(
			groupId, portletId);
	}

	public static Settings getPortletInstanceSettings(
			Layout layout, String portletId)
		throws SettingsException {

		return getSettingsFactory().getPortletInstanceSettings(
			layout, portletId);
	}

	public static Settings getServerSettings(String settingsId) {
		return getSettingsFactory().getServerSettings(settingsId);
	}

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		return getSettingsFactory().getSettingsDescriptor(settingsId);
	}

	public static SettingsFactory getSettingsFactory() {
		PortalRuntimePermission.checkGetBeanProperty(SettingsFactoryUtil.class);

		return _settingsFactory;
	}

	public static void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		getSettingsFactory().registerSettingsMetadata(
			settingsClass, null, fallbackKeys);
	}

	public void setSettingsFactory(SettingsFactory settingsFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_settingsFactory = settingsFactory;
	}

	private static SettingsFactory _settingsFactory;

}