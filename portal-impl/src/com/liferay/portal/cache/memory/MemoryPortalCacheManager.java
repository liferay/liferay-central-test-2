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

package com.liferay.portal.cache.memory;

import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.net.URL;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Brian Wing Shun Chan
 */
public class MemoryPortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	public void afterPropertiesSet() {
		_memoryPortalCaches =
			new ConcurrentHashMap<String, MemoryPortalCache<K, V>>(
				_cacheManagerInitialCapacity);

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.init();
		}
	}

	@Override
	public void clearAll() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.removeAll();
		}
	}

	@Override
	public void destroy() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.destroy();
		}

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.dispose();
		}
	}

	@Override
	public PortalCache<K, V> getCache(String name) {
		return getCache(name, false);
	}

	@Override
	public PortalCache<K, V> getCache(String name, boolean blocking) {
		MemoryPortalCache<K, V> portalCache = _memoryPortalCaches.get(name);

		if (portalCache == null) {
			portalCache = new MemoryPortalCache<K, V>(
				name, _cacheInitialCapacity);

			_memoryPortalCaches.put(name, portalCache);

			for (CacheManagerListener cacheManagerListener :
					_cacheManagerListeners) {

				cacheManagerListener.notifyCacheAdded(name);
			}
		}

		return portalCache;
	}

	@Override
	public Set<CacheManagerListener> getCacheManagerListeners() {
		return new HashSet<CacheManagerListener>(_cacheManagerListeners);
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
	}

	@Override
	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return _cacheManagerListeners.add(cacheManagerListener);
	}

	@Override
	public void removeCache(String name) {
		MemoryPortalCache<K, V> memoryPortalCache = _memoryPortalCaches.remove(
			name);

		memoryPortalCache.destroy();

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.notifyCacheRemoved(name);
		}
	}

	public void setCacheInitialCapacity(int cacheInitialCapacity) {
		_cacheInitialCapacity = cacheInitialCapacity;
	}

	public void setCacheManagerInitialCapacity(
		int cacheManagerInitialCapacity) {

		_cacheManagerInitialCapacity = cacheManagerInitialCapacity;
	}

	@Override
	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return _cacheManagerListeners.remove(cacheManagerListener);
	}

	@Override
	public void unregisterCacheManagerListeners() {
		_cacheManagerListeners.clear();
	}

	private int _cacheInitialCapacity = 10000;
	private int _cacheManagerInitialCapacity = 10000;
	private Set<CacheManagerListener> _cacheManagerListeners =
		new CopyOnWriteArraySet<CacheManagerListener>();
	private Map<String, MemoryPortalCache<K, V>> _memoryPortalCaches;

}