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

import com.liferay.portal.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.util.TextFormatter;

import java.util.Map;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	protected String getUpdatePortletPreferencesWhereClause() {
		return "portletId = '20' OR " +
			"portletId = '33' OR " +
			"portletId like '73_INSTANCE_%' OR " +
			"portletId like '82_INSTANCE_%' OR " +
			"portletId like '85_INSTANCE_%' OR " +
			"portletId = '114' OR " +
			"portletId = '115' OR " +
			"portletId like '122_INSTANCE_%'";
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