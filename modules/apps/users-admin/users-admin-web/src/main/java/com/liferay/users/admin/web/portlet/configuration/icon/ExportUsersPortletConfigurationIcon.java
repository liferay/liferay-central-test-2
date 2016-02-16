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

package com.liferay.users.admin.web.portlet.configuration.icon;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

/**
 * @author Pei-Jung Lan
 */
public class ExportUsersPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public ExportUsersPortletConfigurationIcon(
		ServletContext servletContext, String jspPath,
		PortletRequest portletRequest) {

		super(servletContext, jspPath, portletRequest);
	}

	@Override
	public String getMessage() {
		return "export-users";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.EXPORT_USER)) {

			return true;
		}

		try {
			return PortletPermissionUtil.contains(
				permissionChecker, UsersAdminPortletKeys.USERS_ADMIN,
				ActionKeys.EXPORT_USER);
		}
		catch (PortalException pe) {
		}

		return false;
	}

}