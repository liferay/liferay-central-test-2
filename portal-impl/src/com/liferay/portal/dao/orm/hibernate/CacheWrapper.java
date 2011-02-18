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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.context.PortalContextLoaderLifecycleThreadLocal;

import java.util.Map;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated
 */
public class CacheWrapper implements Cache, CacheRegistryItem {

	public CacheWrapper(Cache cache) {
		_cache = cache;
		_registryName = cache.getRegionName();

		if (_log.isDebugEnabled()) {
			_log.debug("Creating cache for " + _registryName);
		}

		CacheRegistryUtil.register(this);
	}

	public void clear() throws CacheException {
		_cache.clear();
	}

	public void destroy() throws CacheException {
		if (PortalContextLoaderLifecycleThreadLocal.isDestroying()) {
			String regionName = _cache.getRegionName();

			if (regionName.startsWith("org.hibernate.cache")) {
				return;
			}
		}

		_cache.destroy();
	}

	public Object get(Object key) throws CacheException {
		return _cache.get(key);
	}

	public long getElementCountInMemory() {
		return _cache.getElementCountInMemory();
	}

	public long getElementCountOnDisk() {
		return _cache.getElementCountOnDisk();
	}

	public String getRegionName() {
		return _cache.getRegionName();
	}

	public String getRegistryName() {
		return _registryName;
	}

	public long getSizeInMemory() {
		return _cache.getSizeInMemory();
	}

	public int getTimeout() {
		return _cache.getTimeout();
	}

	public void lock(Object key) throws CacheException {
		_cache.lock(key);
	}

	public long nextTimestamp() {
		return _cache.nextTimestamp();
	}

	public void put(Object key, Object value) throws CacheException {
		if (CacheRegistryUtil.isActive()) {
			_cache.put(key, value);
		}
	}

	public Object read(Object key) throws CacheException {
		return _cache.read(key);
	}

	public void remove(Object key) throws CacheException {
		_cache.remove(key);
	}

	public Map<?, ?> toMap() {
		return _cache.toMap();
	}

	public void unlock(Object key) throws CacheException {
		_cache.unlock(key);
	}

	public void update(Object key, Object value) throws CacheException {
		if (CacheRegistryUtil.isActive()) {
			_cache.update(key, value);
		}
	}

	public void invalidate() {
		if (_log.isDebugEnabled()) {
			_log.debug("Invalidating cache for " + _registryName);
		}

		_cache.clear();
	}

	private static Log _log = LogFactoryUtil.getLog(CacheWrapper.class);

	private Cache _cache;
	private String _registryName;

}