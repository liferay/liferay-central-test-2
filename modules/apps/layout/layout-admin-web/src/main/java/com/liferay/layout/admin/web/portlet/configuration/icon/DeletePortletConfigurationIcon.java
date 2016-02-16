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

package com.liferay.layout.admin.web.portlet.configuration.icon;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

/**
 * @author Eudaldo Alonso
 */
public class DeletePortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public DeletePortletConfigurationIcon(
		ServletContext servletContext, String jspPath,
		PortletRequest portletRequest, LayoutLocalService layoutLocalService) {

		super(servletContext, jspPath, portletRequest);

		_layoutLocalService = layoutLocalService;
	}

	@Override
	public String getId() {
		return "delete";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "delete";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			Layout layout = getLayout(portletRequest);

			if (layout == null) {
				return false;
			}

			Group group = layout.getGroup();

			if (group.isLayoutPrototype()) {
				return false;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _layoutLocalService.fetchLayout(selPlid);
	}

	private final LayoutLocalService _layoutLocalService;

}