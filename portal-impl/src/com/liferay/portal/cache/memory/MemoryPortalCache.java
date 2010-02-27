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

package com.liferay.portal.cache.memory;

import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="MemoryPortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MemoryPortalCache implements PortalCache {

	public MemoryPortalCache(int initialCapacity) {
		 _map = new ConcurrentHashMap<String, Object>(initialCapacity);
	}

	public Object get(String key) {
		return _map.get(key);
	}

	public void put(String key, Object obj) {
		_map.put(key, obj);
	}

	public void put(String key, Object obj, int timeToLive) {
		_map.put(key, obj);
	}

	public void put(String key, Serializable obj) {
		_map.put(key, obj);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		_map.put(key, obj);
	}

	public void remove(String key) {
		_map.remove(key);
	}

	public void removeAll() {
		_map.clear();
	}

	private Map<String, Object> _map;

}