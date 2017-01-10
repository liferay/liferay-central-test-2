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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ShutdownUtil {

	public static void cancel() {
		_date = null;
		_message = null;
	}

	public static long getInProcess() {
		long milliseconds = 0;

		if (_date != null) {
			milliseconds = _date.getTime() - System.currentTimeMillis();
		}

		return milliseconds;
	}

	public static String getMessage() {
		return _message;
	}

	public static boolean isInProcess() {
		if (_date == null) {
			return false;
		}

		if (_date.after(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isShutdown() {
		if (_date == null) {
			return false;
		}

		if (_date.before(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void shutdown(long milliseconds) {
		shutdown(milliseconds, StringPool.BLANK);
	}

	public static void shutdown(long milliseconds, String message) {
		_date = new Date(System.currentTimeMillis() + milliseconds);
		_message = message;
	}

	private static Date _date;
	private static String _message;

}