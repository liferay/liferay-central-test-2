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

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.permission.LayoutPermissionUtil;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CopyApplicationsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public CopyApplicationsPortletConfigurationIcon(
		PortletRequest portletRequest, LayoutLocalService layoutLocalService) {

		super(portletRequest);

		_layoutLocalService = layoutLocalService;
	}

	@Override
	public String getId() {
		return "copyApplications";
	}

	@Override
	public String getMessage() {
		return "copy-applications";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			Layout layout = getLayout();

			if (layout == null) {
				return false;
			}

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE)) {

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

	protected Layout getLayout() throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _layoutLocalService.fetchLayout(selPlid);
	}

	private final LayoutLocalService _layoutLocalService;

}