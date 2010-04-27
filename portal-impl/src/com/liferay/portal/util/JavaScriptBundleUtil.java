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

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.util.UniqueList;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="JavaScriptBundleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class JavaScriptBundleUtil {

	public static final String CACHE_NAME =
		JavaScriptBundleUtil.class.getName();

	public static void clearCache() {
		SingleVMPoolUtil.clear(CACHE_NAME);
	}

	public static String[] getFileNames(String bundleId) {
		String[] fileNames =
			(String[])SingleVMPoolUtil.get(CACHE_NAME, bundleId);

		if (fileNames == null) {
			fileNames = _instance._getFileNames(bundleId);

			SingleVMPoolUtil.put(CACHE_NAME, bundleId, fileNames);
		}

		return fileNames;
	}

	public static List<String> getRequires(String bundleId) {
		return _instance._getRequires(bundleId, new UniqueList<String>());
	}

	private JavaScriptBundleUtil() {
	}

	private String[] _getFileNames(String bundleId) {
		String[] result = {};

		List<String> requires = JavaScriptBundleUtil.getRequires(bundleId);

		Iterator<String> itr = requires.iterator();

		while (itr.hasNext()) {
			String id = itr.next();

			String[] fileNames = PropsUtil.getArray(id);

			result = ArrayUtil.append(result, fileNames);
		}

		return result;
	}

	private List<String> _getRequires(
		String bundleId, List<String> requires) {

		if (!ArrayUtil.contains(PropsValues.JAVASCRIPT_BUNDLE_IDS, bundleId)) {
			return requires;
		}

		String[] modules = PropsUtil.getArray(
			PropsKeys.JAVASCRIPT_BUNDLE_REQUIRES, new Filter(bundleId));

		for (String moduleName : modules) {
			String[] dependencies = PropsUtil.getArray(
				PropsKeys.JAVASCRIPT_BUNDLE_REQUIRES, new Filter(moduleName));

			if (!ArrayUtil.contains(dependencies, bundleId)) {
				_getRequires(moduleName, requires);
			}

			requires.add(moduleName);
		}

		requires.add(bundleId);

		return requires;
	}

	private static JavaScriptBundleUtil _instance = new JavaScriptBundleUtil();

}