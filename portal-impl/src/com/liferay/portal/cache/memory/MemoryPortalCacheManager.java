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
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="MemoryPortalCacheManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MemoryPortalCacheManager implements PortalCacheManager {

	public void afterPropertiesSet() {
		_cacheMap = new ConcurrentHashMap<String, PortalCache>(
			_cacheManagerInitialCapacity);
	}

	public void clearAll() {
		_cacheMap.clear();
	}

	public PortalCache getCache(String name) {
		return getCache(name, false);
	}

	public PortalCache getCache(String name, boolean blocking) {
		PortalCache cache = _cacheMap.get(name);

		if (cache == null) {
			cache = new MemoryPortalCache(_cacheInitialCapacity);

			_cacheMap.put(name, cache);
		}

		return cache;
	}

	public void setCacheInitialCapacity(int cacheInitialCapacity) {
		_cacheInitialCapacity = cacheInitialCapacity;
	}

	public void setCacheManagerInitialCapacity(
		int cacheManagerInitialCapacity) {

		_cacheManagerInitialCapacity = cacheManagerInitialCapacity;
	}

	private int _cacheInitialCapacity = 10000;
	private int _cacheManagerInitialCapacity = 10000;
	private Map<String, PortalCache> _cacheMap;

}