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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public interface SettingsFactory {

	public void clearCache();

	public Settings getCompanyServiceSettings(
			long companyId, String serviceName)
		throws SystemException;

	public Settings getGroupServiceCompanyDefaultSettings(
			long companyId, String serviceName)
		throws SystemException;

	public Settings getGroupServiceSettings(long groupId, String serviceName)
		throws PortalException, SystemException;

	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws PortalException, SystemException;

	public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
			long groupId, String portletId)
		throws SystemException;

	public Settings getPortletInstanceCompanyDefaultSettings(
			long companyId, String portletId)
		throws SystemException;

	public Settings getPortletInstanceGroupDefaultSettings(
			long groupId, String portletId)
		throws PortalException, SystemException;

	public Settings getPortletInstanceSettings(Layout layout, String portletId)
		throws PortalException, SystemException;

	public void registerFallbackKeys(
		String settingsId, FallbackKeys fallbackKeys);

}