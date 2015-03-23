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

import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.model.Layout;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public interface SettingsFactory {

	public void clearCache();

	public Settings getCompanyServiceSettings(
		long companyId, String serviceName);

	public Settings getGroupServiceSettings(long groupId, String serviceName)
		throws SettingsException;

	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException;

	public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId);

	public Settings getPortletInstanceSettings(Layout layout, String portletId)
		throws SettingsException;

	public Settings getServerSettings(String settingsId);

	public SettingsDescriptor getSettingsDescriptor(String settingsId);

	public void registerSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition, Object configurationBean);

	public void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys);

	public void unregisterSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition);

}