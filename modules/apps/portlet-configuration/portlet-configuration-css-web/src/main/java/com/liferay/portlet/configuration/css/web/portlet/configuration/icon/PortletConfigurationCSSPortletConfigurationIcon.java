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

package com.liferay.portlet.configuration.css.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.css.web.constants.PortletConfigurationCSSPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationCSSPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PortletConfigurationCSSPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "lfr-js-required portlet-css portlet-css-icon";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "look-and-feel";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL baseActionURL = PortletURLFactoryUtil.create(
			portletRequest,
			PortletConfigurationCSSPortletKeys.PORTLET_CONFIGURATION_CSS,
			themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

		PortletURL baseRenderURL = PortletURLFactoryUtil.create(
			portletRequest,
			PortletConfigurationCSSPortletKeys.PORTLET_CONFIGURATION_CSS,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		PortletURL baseResourceURL = PortletURLFactoryUtil.create(
			portletRequest,
			PortletConfigurationCSSPortletKeys.PORTLET_CONFIGURATION_CSS,
			themeDisplay.getPlid(), PortletRequest.RESOURCE_PHASE);

		StringBundler sb = new StringBundler(9);

		sb.append("Liferay.Portlet.loadCSSEditor('");

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(portletDisplay.getId());
		sb.append("', '");
		sb.append(baseActionURL);
		sb.append("', '");
		sb.append(baseRenderURL);
		sb.append("', '");
		sb.append(baseResourceURL);
		sb.append("'); return false;");

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getURLPortletCss();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.isShowPortletCssIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}