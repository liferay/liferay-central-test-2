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

package com.liferay.portal.upgrade.v5_1_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
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

		long rootPlid = GetterUtil.getLong(
			preferences.getValue("root-plid", StringPool.BLANK));

		if (rootPlid > 0) {
			Object[] layout = getLayout(rootPlid);

			if (layout != null) {
				long layoutId = (Long)layout[3];

				preferences.setValue(
					"root-layout-id", String.valueOf(layoutId));
			}
		}

		preferences.setValue("root-plid", null);

		return PortletPreferencesSerializer.toXML(preferences);
	}

}