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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BasePortletProvider implements PortletProvider {

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		return getPortletURL(request, null);
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request, Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = getPlid(themeDisplay);
		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			themeDisplay.getCompanyId());

		if (plid == controlPanelPlid) {
			return PortalUtil.getControlPanelPortletURL(
				request, group, getPortletName(), 0, 0,
				PortletRequest.RENDER_PHASE);
		}
		else {
			return PortletURLFactoryUtil.create(
				request, getPortletName(), plid, PortletRequest.RENDER_PHASE);
		}
	}

	protected long getPlid(ThemeDisplay themeDisplay) throws PortalException {
		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

}