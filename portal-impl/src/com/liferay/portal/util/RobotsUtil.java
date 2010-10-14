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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;

/**
 * @author David Truong
 */
public class RobotsUtil {

	public static String getRobots(LayoutSet layoutSet)
		throws PortalException, SystemException {
	
		if (layoutSet != null) {
			return GetterUtil.get(
				layoutSet.getSettingsProperty(
					layoutSet.getPrivateLayout() + "-robots.txt"),
					getDefaultRobots(layoutSet.getVirtualHost()));
		}

		return getDefaultRobots(null);
	}
	
	public static String getDefaultRobots() {

		return  getDefaultRobots(null);
	}

	public static String getDefaultRobots(String virtualHost) {

		if (Validator.isNotNull(virtualHost)) {
			return DEFAULT_ROBOTS_TXT.replace(
				"$HOSTS$", virtualHost);
		}

		return DEFAULT_ROBOTS_TXT_WITHOUT_SITEMAP;
	}

	private static String DEFAULT_ROBOTS_TXT =
		"User-agent: *\nDisallow:\nSitemap: http://$HOSTS$/sitemap.xml";

	private static String DEFAULT_ROBOTS_TXT_WITHOUT_SITEMAP =
		"User-agent: *\nDisallow:";

}