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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;

/**
 * @author David Truong
 */
public class RobotsUtil {

	public static String getDefaultRobots() {
		return getDefaultRobots(null);
	}

	public static String getDefaultRobots(String virtualHost) {
		if (Validator.isNotNull(virtualHost)) {
			return _DEFAULT_ROBOTS_TXT.replace(
				"$HOSTS$", virtualHost);
		}

		return _DEFAULT_ROBOTS_TXT_WITHOUT_SITEMAP;
	}

	public static String getRobots(LayoutSet layoutSet) {
		if (layoutSet != null) {
			String virtualHost = "";

			try {
				virtualHost = layoutSet.getVirtualHost();
			}
			catch (Exception e) {
			}

			return GetterUtil.get(
				layoutSet.getSettingsProperty(
					layoutSet.getPrivateLayout() + "-robots.txt"),
					getDefaultRobots(virtualHost));
		}

		return getDefaultRobots(null);
	}

	private static String _DEFAULT_ROBOTS_TXT =
		"User-Agent: *\nDisallow:\nSitemap: http://$HOSTS$/sitemap.xml";

	private static String _DEFAULT_ROBOTS_TXT_WITHOUT_SITEMAP =
		"User-Agent: *\nDisallow:";

}