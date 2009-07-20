/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebDirDetector {

	public static String getLibDir(ClassLoader classLoader) {
		URL url = classLoader.getResource(
			"com/liferay/util/bean/PortletBeanLocatorUtil.class");

		String file = null;

		try {
			file = new URI(url.getPath()).getPath();
		}
		catch (URISyntaxException urise) {
			file = url.getFile();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Lib url " + file);
		}

		int pos = file.indexOf("/com/liferay/util/bean/");

		String libDir = file.substring(0, pos + 1);

		if (libDir.endsWith("/WEB-INF/classes/")) {
			libDir = libDir.substring(0, libDir.length() - 8) + "lib/";
		}
		else {
			pos = libDir.indexOf("/WEB-INF/lib/");

			if (pos != -1) {
				libDir = libDir.substring(0, pos) + "/WEB-INF/lib/";
			}
		}

		if (libDir.startsWith("file:/")) {
			libDir = libDir.substring(5, libDir.length());
		}

		return libDir;
	}

	public static String getRootDir(ClassLoader classLoader) {
		return getRootDir(getLibDir(classLoader));
	}

	public static String getRootDir(String libDir) {
		String rootDir = libDir;

		if (rootDir.endsWith("/WEB-INF/lib/")) {
			rootDir = rootDir.substring(0, rootDir.length() - 12);
		}

		return rootDir;
	}

	private static Log _log = LogFactoryUtil.getLog(WebDirDetector.class);

}