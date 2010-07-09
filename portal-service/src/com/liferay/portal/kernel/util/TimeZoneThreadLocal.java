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

import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneThreadLocal {

	public static TimeZone getTimeZone() {
		return _timeZone.get();
	}

	public static void setTimeZone(TimeZone timeZone) {
		_timeZone.set(timeZone);
	}

	private static ThreadLocal<TimeZone> _timeZone =
		new AutoResetThreadLocal<TimeZone>(
			TimeZoneThreadLocal.class + "._timeZone");

}