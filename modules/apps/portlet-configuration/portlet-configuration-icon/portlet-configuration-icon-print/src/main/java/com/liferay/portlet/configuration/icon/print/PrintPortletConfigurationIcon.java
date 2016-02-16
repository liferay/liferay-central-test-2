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

package com.liferay.portlet.configuration.icon.print;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class PrintPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PrintPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "portlet-print portlet-print-icon";
	}

	@Override
	public String getMessage() {
		return "print";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "location.href = '".concat(
			portletDisplay.getURLPrint()).concat("'; return false;");
	}

	@Override
	public String getTarget() {
		return "_blank";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return portletDisplay.getURLPrint();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return portletDisplay.isShowPrintIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}