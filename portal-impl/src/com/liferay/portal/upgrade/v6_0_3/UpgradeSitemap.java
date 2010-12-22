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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

/**
 * @author Julio Camarero
 */
public class UpgradeSitemap extends BaseUpgradePortletPreferences {

	protected String[] getPortletIds() {
		return new String[] {"85_INSTANCE_%"};
	}

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long rootLayoutId = GetterUtil.getLong(
			preferences.getValue("root-layout-id", StringPool.BLANK));

		if (rootLayoutId > 0) {
			String uuid = getLayoutUuid(plid, rootLayoutId);

			if (uuid != null) {
				preferences.setValue("root-layout-uuid", uuid);
			}

			preferences.reset("root-layout-id");
		}

		return PortletPreferencesSerializer.toXML(preferences);
	}

}