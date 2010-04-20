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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.TimeZone;

/**
 * <a href="TimeZoneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TimeZoneUtil {

	public static TimeZone getDefault() {
		return _instance._getDefault();
	}

	public static TimeZone getTimeZone(String timeZoneId) {
		TimeZone timeZone = _timeZoneCache.get(timeZoneId);

		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone(timeZoneId);

			_timeZoneCache.put(timeZoneId, timeZone);
		}

		return timeZone;
	}

	public static void setDefault(String id) {
		_instance._setDefault(id);
	}

	private TimeZoneUtil() {
		_timeZone = getTimeZone(StringPool.UTC);
	}

	private TimeZone _getDefault() {
		return _timeZone;
	}

	private void _setDefault(String id) {
		if (Validator.isNotNull(id)) {
			_timeZone = TimeZone.getTimeZone(id);
		}
	}

	private static TimeZoneUtil _instance = new TimeZoneUtil();
	private static HashMap<String, TimeZone> _timeZoneCache =
		new HashMap<String, TimeZone>();

	private TimeZone _timeZone;

}