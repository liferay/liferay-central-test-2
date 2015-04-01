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

package com.liferay.portal.kernel.portlet.configuration;

import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
import com.liferay.portal.theme.PortletDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = PortletConfigurationIcon.class
)
public class HelpPortletConfigurationIcon extends BasePortletConfigurationIcon {

	@Override
	public String getCssClass() {
		return "portlet-help portlet-help-icon";
	}

	@Override
	public String getImage() {
		return "../aui/question-sign";
	}

	@Override
	public String getMessage() {
		return "help";
	}

	@Override
	public String getURL() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getURLHelp();
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.isShowHelpIcon();
	}

	@Override
	public boolean showToolTip() {
		return false;
	}

}