/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.util.TextFormatter;

import java.util.Map;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	protected String[] getPortletIds() {
		return new String[] {
			"8", "15", "19", "20", "33", "36", "39_INSTANCE_%", "47_INSTANCE_%",
			"56_INSTANCE_%", "54_INSTANCE_%", "59_INSTANCE_%", "62_INSTANCE_%",
			"71_INSTANCE_%", "73_INSTANCE_%", "77", "82_INSTANCE_%",
			"85_INSTANCE_%", "100", "101_INSTANCE_%", "102_INSTANCE_%", "114",
			"115", "118_INSTANCE_%", "122_INSTANCE_%"
		};
	}

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		Map<String, String[]> preferencesMap = preferences.getMap();

		for (String oldName : preferencesMap.keySet()) {
			String[] values = preferencesMap.get(oldName);

			String newName = TextFormatter.format(oldName, TextFormatter.M);

			preferences.reset(oldName);

			preferences.setValues(newName, values);
		}

		return PortletPreferencesSerializer.toXML(preferences);
	}

}