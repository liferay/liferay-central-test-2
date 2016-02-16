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
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class PermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PermissionsPortletConfigurationIcon(
		PortletRequest portletRequest, LayoutLocalService layoutLocalService) {

		super(portletRequest);

		_layoutLocalService = layoutLocalService;
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "permissions";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String url = StringPool.BLANK;

		try {
			Layout layout = getLayout(portletRequest);

			url = PermissionsURLTag.doTag(
				StringPool.BLANK, Layout.class.getName(),
				layout.getName(themeDisplay.getLocale()), null,
				String.valueOf(layout.getPlid()),
				LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
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

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					getLayout(portletRequest), ActionKeys.PERMISSIONS)) {

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

	@Override
	public boolean isUseDialog() {
		return true;
	}

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _layoutLocalService.fetchLayout(selPlid);
	}

	private final LayoutLocalService _layoutLocalService;

}