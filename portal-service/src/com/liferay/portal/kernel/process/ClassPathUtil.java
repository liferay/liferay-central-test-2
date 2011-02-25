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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class ClassPathUtil {

	public static String getGlobalClassPath() {
		return _globalClassPath;
	}

	public static String getPortalClassPath() {
		return _portalClassPath;
	}

	public static void initializeClassPaths(ServletContext servletContext) {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (classLoader == null) {
			_log.error("Portal ClassLoader has not been initialized yet!");
			return;
		}

		_globalClassPath = _buildClassPath(
			classLoader, PortalException.class.getName());

		StringBundler sb = new StringBundler(5);

		sb.append(_globalClassPath);
		sb.append(StringPool.COLON);
		sb.append(_buildClassPath(
			classLoader, "com.liferay.portal.servlet.MainServlet"));
		sb.append(StringPool.COLON);
		sb.append(servletContext.getRealPath("").concat("/WEB-INF/classes"));

		_portalClassPath = sb.toString();
	}

	private static String _buildClassPath(
		ClassLoader classloader, String className) {
		String relativePath = className.replace(
			CharPool.PERIOD, CharPool.SLASH).concat(".class");
		String fullPath = classloader.getResource(relativePath).getPath();

		int index = -1;
		if (!fullPath.startsWith("file:") ||
			((index = fullPath.indexOf(CharPool.EXCLAMATION)) == -1)) {
			_log.error("Class " + className +
				" is not loaded from jar file.");
			return StringPool.BLANK;
		}

		index = fullPath.lastIndexOf(CharPool.SLASH, index);

		File dir = new File(fullPath.substring("file:".length(), index));

		if (!dir.isDirectory()) {
			_log.error(dir.toString() + " is not a directory.");
			return StringPool.BLANK;
		}

		File[] files = dir.listFiles();

		StringBundler sb = new StringBundler(files.length * 2);

		for (File file : files) {
			sb.append(file.getAbsolutePath());
			sb.append(StringPool.COLON);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(ClassPathUtil.class);

	private static String _globalClassPath;

	private static String _portalClassPath;

}