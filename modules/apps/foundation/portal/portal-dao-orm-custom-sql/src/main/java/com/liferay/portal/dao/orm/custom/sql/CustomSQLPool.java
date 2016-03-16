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

package com.liferay.portal.dao.orm.custom.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.osgi.framework.BundleContext;

/**
 * @author Peter Fellwock
 */
public class CustomSQLPool {

	public CustomSQLPool() {
		_sqlPoolMaps = new WeakHashMap<>();
	}

	public void clear() {
		_sqlPoolMaps = null;

		_sqlPoolMaps = new WeakHashMap<>();
	}

	public String get(BundleContext bundleContext, String id) {
		Map<String, String> sqlPoolMap = _sqlPoolMaps.get(bundleContext);

		if (sqlPoolMap != null) {
			return sqlPoolMap.get(id);
		}

		return null;
	}

	public String get(Map<String, String> sqlPoolMap, String id) {
		return sqlPoolMap.get(id);
	}

	public String get(String id) {
		for (BundleContext bundleContext : _sqlPoolMaps.keySet()) {
			if (bundleContext == null) {
				continue;
			}

			Map<String, String> sqlPoolMap = _sqlPoolMaps.get(
				bundleContext);

			if (sqlPoolMap == null) {
				continue;
			}

			String content = sqlPoolMap.get(id);

			if (content != null) {
				return content;
			}
		}

		return null;
	}

	public boolean isBundleContextLoaded(BundleContext bundleContext) {
		Map<String, String> sqlPoolMap = _sqlPoolMaps.get(
			bundleContext);

		if (sqlPoolMap != null) {
			return true;
		}

		return false;
	}

	public void put(BundleContext bundleContext, String id, String content) {
		Map<String, String> sqlPoolMap = _sqlPoolMaps.get(bundleContext);

		if (sqlPoolMap == null) {
			sqlPoolMap = new HashMap<>();

			_sqlPoolMaps.put(bundleContext, sqlPoolMap);
		}

		sqlPoolMap.put(id, content);
	}

	private static Map<BundleContext, Map<String, String>> _sqlPoolMaps =
		new WeakHashMap<>();

}