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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationApplicationType;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class PermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PermissionsPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		try {
			String returnToFullPageURL = ParamUtil.getString(
				portletRequest, "returnToFullPageURL");

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest,
				PortletConfigurationApplicationType.
					PortletConfiguration.CLASS_NAME,
				PortletProvider.Action.VIEW);

			portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
			portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
			portletURL.setParameter(
				"portletConfiguration", Boolean.TRUE.toString());
			portletURL.setParameter("portletResource", portletDisplay.getId());
			portletURL.setParameter(
				"resourcePrimKey",
				PortletPermissionUtil.getPrimaryKey(
					themeDisplay.getPlid(), portletDisplay.getId()));
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			if (!PortletPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getLayout(),
					portletDisplay.getPortletResource(),
					ActionKeys.PERMISSIONS)) {

				return false;
			}
		}
		catch (PortalException pe) {
			return false;
		}

		return portletDisplay.isShowConfigurationIcon();
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}