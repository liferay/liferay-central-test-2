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

package com.liferay.product.navigation.product.menu.web.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	protected void deleteControlPanelMenuPortletPreferences() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete control panel menu portlet preferences");
		}

		try (PreparedStatement ps2 = connection.prepareStatement(
				"delete from PortletPreferences where portletId = ?")) {

			ps2.setString(1, _CONTROL_PANEL_MENU_PORTLET);

			ps2.execute();
		}
	}

	protected void deleteDockbarPortletPreferences() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete dockbar portlet preferences");
		}

		try (PreparedStatement ps2 = connection.prepareStatement(
				"delete from PortletPreferences where portletId = ?")) {

			ps2.setString(1, _DOCKBAR_PORTLET);

			ps2.execute();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteControlPanelMenuPortletPreferences();
		deleteDockbarPortletPreferences();
	}

	private static final String _CONTROL_PANEL_MENU_PORTLET = "160";

	private static final String _DOCKBAR_PORTLET = "145";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}