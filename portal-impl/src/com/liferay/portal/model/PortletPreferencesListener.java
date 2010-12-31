/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class PortletPreferencesListener
	extends BaseModelListener<PortletPreferences> {

	public void onAfterRemove(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);
	}

	public void onAfterUpdate(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);
	}

	protected void clearCache(PortletPreferences portletPreferences) {
		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				portletPreferences.getPlid());

			if (!layout.isPrivateLayout()) {
				CacheUtil.clearCache(layout.getCompanyId());
			}
		}
		catch (Exception e) {
			CacheUtil.clearCache();
		}
	}

}