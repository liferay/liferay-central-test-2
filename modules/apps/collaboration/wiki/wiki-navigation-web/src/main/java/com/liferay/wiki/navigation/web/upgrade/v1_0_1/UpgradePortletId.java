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

package com.liferay.wiki.navigation.web.upgrade.v1_0_1;

import com.liferay.wiki.navigation.web.constants.WikiNavigationPortletKeys;

/**
 * @author Sergio González
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"1_WAR_wikinavigationportlet",
				WikiNavigationPortletKeys.TREE_MENU
			},
			new String[] {
				"2_WAR_wikinavigationportlet",
				WikiNavigationPortletKeys.PAGE_MENU
			}
		};
	}

}