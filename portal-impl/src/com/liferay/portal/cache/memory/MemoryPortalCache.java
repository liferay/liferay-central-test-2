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

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="MemoryPortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MemoryPortalCache extends BasePortalCache implements PortalCache {

	public MemoryPortalCache(int initialCapacity) {
		 _map = new ConcurrentHashMap<String, Object>(initialCapacity);
	}

	public Object get(String key) {
		String processedKey = processKey(key);

		return _map.get(processedKey);
	}

	public Collection<Object> get(Collection<String> keys) {

		List<Object> cachedResults = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			cachedResults.add(get(key));
		}

		return cachedResults;
	}
	
	public void put(String key, Object obj) {
		String processedKey = processKey(key);

		_map.put(processedKey, obj);
	}

	public void put(String key, Object obj, int timeToLive) {
		String processedKey = processKey(key);

		_map.put(processedKey, obj);
	}

	public void put(String key, Serializable obj) {
		String processedKey = processKey(key);

		_map.put(processedKey, obj);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		String processedKey = processKey(key);

		_map.put(processedKey, obj);
	}

	public void remove(String key) {
		String processedKey = processKey(key);

		_map.remove(processedKey);
	}

	public void removeAll() {
		_map.clear();
	}

	private Map<String, Object> _map;

}