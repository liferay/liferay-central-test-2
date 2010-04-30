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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CacheRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CacheRegistry {

	public static void clear() {
		for (Map.Entry<String, CacheRegistryItem> entry : _items.entrySet()) {
			CacheRegistryItem item = entry.getValue();

			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating " + item.getRegistryName());
			}

			item.invalidate();
		}
	}

	public static void clear(String name) {
		CacheRegistryItem item = _items.get(name);

		if (item != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating " + name);
			}

			item.invalidate();
		}
		else {
			_log.error("No cache registry found with name " + name);
		}
	}

	public static boolean isActive() {
		return _active;
	}

	public static void register(CacheRegistryItem item) {
		String name = item.getRegistryName();

		if (_log.isDebugEnabled()) {
			_log.debug("Registering " + name);
		}

		if (_items.containsKey(name)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Not registering duplicate " + name);
			}
		}
		else {
			_items.put(name, item);
		}
	}

	public static void setActive(boolean active) {
		_active = active;

		if (!active) {
			clear();
		}
	}

	public static void unregister(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("Unregistering " + name);
		}

		_items.remove(name);
	}

	private static Log _log = LogFactoryUtil.getLog(CacheRegistry.class);

	private static boolean _active = true;
	private static Map<String, CacheRegistryItem> _items =
		new ConcurrentHashMap<String, CacheRegistryItem>();

}