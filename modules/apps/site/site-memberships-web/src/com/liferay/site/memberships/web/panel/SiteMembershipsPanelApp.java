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

package com.liferay.site.memberships.web.panel;

import com.liferay.portal.service.PortletLocalService;
import com.liferay.productivity.center.panel.BaseControlPanelEntryPanelApp;
import com.liferay.productivity.center.panel.PanelApp;
import com.liferay.productivity.center.panel.constants.PanelCategoryKeys;
import com.liferay.site.memberships.web.constants.SiteMembershipsPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_USERS,
		"service.ranking:Integer=100"
	},
	service = PanelApp.class
)
public class SiteMembershipsPanelApp extends BaseControlPanelEntryPanelApp {

	@Override
	public String getPortletId() {
		return SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

}