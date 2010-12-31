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

package com.liferay.portal.kernel.util;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class OSDetector {

	public static boolean isAIX() {
		if (_aix == null) {
			String osName = System.getProperty("os.name");

			if (osName.equalsIgnoreCase("aix")) {
				_aix = Boolean.TRUE;
			}
			else {
				_aix = Boolean.FALSE;
			}
		}

		return _aix.booleanValue();
	}

	public static boolean isUnix() {
		if (_unix == null) {
			if (File.pathSeparator.equals(StringPool.COLON)) {
				_unix = Boolean.TRUE;
			}
			else {
				_unix = Boolean.FALSE;
			}
		}

		return _unix.booleanValue();
	}

	public static boolean isWindows() {
		if (_windows == null) {
			if (File.pathSeparator.equals(StringPool.SEMICOLON)) {
				_windows = Boolean.TRUE;
			}
			else {
				_windows = Boolean.FALSE;
			}
		}

		return _windows.booleanValue();
	}

	private static Boolean _aix;
	private static Boolean _unix;
	private static Boolean _windows;

}