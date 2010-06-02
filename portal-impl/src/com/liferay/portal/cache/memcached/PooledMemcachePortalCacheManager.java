/*
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

package com.liferay.portal.cache.memcached;

import com.liferay.portal.cache.memcached.factory.MemcachedClientFactory;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <a href="PooledMemcachePortalCacheManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PooledMemcachePortalCacheManager implements PortalCacheManager {

	public void afterPropertiesSet() {
		_cacheMap = new ConcurrentHashMap<String, PortalCache>();
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
			cache = new PooledMemcachePortalCache(
				_memcachedClientFactory, _timeout, _timeoutTimeUnit);

			_cacheMap.put(name, cache);
		}

		return cache;
	}

	public void setMemcachedClientPool(
		MemcachedClientFactory memcachedClientFactory) {
		_memcachedClientFactory = memcachedClientFactory;
	}

	public void setTimeout(int timeout) {
		_timeout = timeout;
	}

	public void setTimeoutTimeUnit(String timeoutTimeUnit) {
		_timeoutTimeUnit = TimeUnit.valueOf(timeoutTimeUnit);
	}

	private Map<String, PortalCache> _cacheMap;
	private MemcachedClientFactory _memcachedClientFactory;
	private int _timeout;
	private TimeUnit _timeoutTimeUnit;
}
