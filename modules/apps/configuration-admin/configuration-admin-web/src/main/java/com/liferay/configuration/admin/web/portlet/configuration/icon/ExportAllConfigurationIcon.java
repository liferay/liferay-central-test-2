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

package com.liferay.configuration.admin.web.portlet.configuration.icon;

import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Jorge Ferrer
 */
public class ExportAllConfigurationIcon extends BasePortletConfigurationIcon {

	public ExportAllConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "export-all-settings";
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)PortalUtil.getControlPanelPortletURL(
				portletRequest, ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
				PortletRequest.RESOURCE_PHASE);

		liferayPortletURL.setResourceID("export");

		return liferayPortletURL.toString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

}