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

package com.liferay.dynamic.data.lists.form.web.portlet.configuration.icon;

import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

/**
 * @author Rafael Praxedes
 */
public class ExportDDLRecordSetPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public ExportDDLRecordSetPortletConfigurationIcon(
		ServletContext servletContext, String jspPath,
		PortletRequest portletRequest) {

		super(servletContext, jspPath, portletRequest);
	}

	@Override
	public String getMessage() {
		return "export";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		long recordSetId = ParamUtil.getLong(portletRequest, "recordSetId");

		if (recordSetId == 0) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		try {
			return DDLRecordSetPermission.contains(
				themeDisplay.getPermissionChecker(), recordSetId,
				ActionKeys.VIEW);
		}
		catch (PortalException pe) {
			return false;
		}
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}