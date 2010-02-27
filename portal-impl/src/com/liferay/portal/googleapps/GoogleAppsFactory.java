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

package com.liferay.portal.googleapps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="GoogleAppsFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GoogleAppsFactory {

	public static GoogleApps getGoogleApps(long companyId) throws Exception {
		GoogleApps googleApps = _googleAppsMap.get(companyId);

		if (googleApps == null) {
			googleApps = new GoogleApps(companyId);

			_googleAppsMap.put(companyId, googleApps);
		}

		googleApps.init(false);

		return googleApps;
	}

	private static Map<Long, GoogleApps> _googleAppsMap =
		new ConcurrentHashMap<Long, GoogleApps>();

}