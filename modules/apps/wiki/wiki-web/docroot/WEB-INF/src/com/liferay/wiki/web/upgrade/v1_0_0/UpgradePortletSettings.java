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

package com.liferay.wiki.web.upgrade.v1_0_0;

import com.liferay.portal.util.PortletKeys;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.settings.WikiSettings;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;

/**
 * @author Iván Zaera
 */
public class UpgradePortletSettings
	extends com.liferay.portal.upgrade.v7_0_0.UpgradePortletSettings {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeMainPortlet(
			WikiPortletKeys.WIKI, WikiConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiPortletInstanceSettings.ALL_KEYS, WikiSettings.ALL_KEYS);
	}

}