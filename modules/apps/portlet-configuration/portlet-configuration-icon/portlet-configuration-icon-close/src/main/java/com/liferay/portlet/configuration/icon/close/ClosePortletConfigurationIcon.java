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

package com.liferay.portlet.configuration.icon.close;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class ClosePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ClosePortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "item-remove portlet-close portlet-close-icon";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "remove";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "Liferay.Portlet.close('#p_p_id_".concat(
			portletDisplay.getId()).concat("_'); return false;");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return portletDisplay.getURLClose();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return portletDisplay.isShowCloseIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}