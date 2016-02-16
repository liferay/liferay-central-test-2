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

package com.liferay.portlet.configuration.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ConfigurationPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ConfigurationPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "portlet-configuration portlet-configuration-icon";
	}

	@Override
	public String getMessage() {
		return "configuration";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick() {
		return portletDisplay.getURLConfigurationJS();
	}

	@Override
	public String getURL() {
		return portletDisplay.getURLConfiguration();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return portletDisplay.isShowConfigurationIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}