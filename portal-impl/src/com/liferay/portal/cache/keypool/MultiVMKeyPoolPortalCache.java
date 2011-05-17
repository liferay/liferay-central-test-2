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

package com.liferay.portal.cache.keypool;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class MultiVMKeyPoolPortalCache extends BasePortalCache {

	public MultiVMKeyPoolPortalCache(
		PortalCache clusterPortalCache, PortalCache localPortalCache) {

		_clusterPortalCache = clusterPortalCache;
		_localPortalCache = localPortalCache;
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public Object get(String key) {
		if (key == null) {
			return null;
		}

		Object localValue = _localPortalCache.get(key);

		if (localValue == null) {
			Object clusterValue = _clusterPortalCache.get(key);

			if (key.equals(clusterValue)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Flagging local key " + key);
				}

				_localPortalCache.put(key, _dummyValue);
			}
		}
		else {
			if (localValue.equals(_dummyValue)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Local key " + key + " has dummy value");
				}

				return null;
			}

			Object clusterValue = _clusterPortalCache.get(key);

			if (!key.equals(clusterValue)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Local key " + key + " is not in cluster");
				}

				return null;
			}

			return localValue;
		}

		return null;
	}

	public String getName() {
		return _clusterPortalCache.getName();
	}

	public void put(String key, Object obj) {
		updateClusterKey(key);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local key " + key);
		}

		_localPortalCache.put(key, obj);
	}

	public void put(String key, Object obj, int timeToLive) {
		updateClusterKey(key, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local key " + key);
		}

		_localPortalCache.put(key, obj, timeToLive);
	}

	public void put(String key, Serializable obj) {
		updateClusterKey(key);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local key " + key);
		}

		_localPortalCache.put(key, obj);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		updateClusterKey(key, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local key " + key);
		}

		_localPortalCache.put(key, obj, timeToLive);
	}

	public void registerCacheListener(CacheListener cacheListener) {
		_clusterPortalCache.registerCacheListener(cacheListener);
	}

	public void registerCacheListener(
		CacheListener cacheListener, CacheListenerScope cacheListenerScope) {

		_clusterPortalCache.registerCacheListener(
			cacheListener, cacheListenerScope);
	}

	public void remove(String key) {
		_clusterPortalCache.remove(key);
		_localPortalCache.remove(key);
	}

	public void removeAll() {
		_clusterPortalCache.removeAll();
		_localPortalCache.removeAll();
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		_clusterPortalCache.unregisterCacheListener(cacheListener);
	}

	public void unregisterCacheListeners() {
		_clusterPortalCache.unregisterCacheListeners();
	}

	protected void updateClusterKey(String key) {
		Object localValue = _localPortalCache.get(key);

		if (localValue == _dummyValue) {
			return;
		}

		Object clusterValue = _clusterPortalCache.get(key);

		if (!key.equals(clusterValue)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Invalidating cluster key " + key);
		}

		_clusterPortalCache.put(key, key);
	}

	protected void updateClusterKey(String key, int timeToLive) {
		Object localValue = _localPortalCache.get(key);

		if (localValue == _dummyValue) {
			return;
		}

		Object clusterValue = _clusterPortalCache.get(key);

		if (!key.equals(clusterValue)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Invalidating cluster key " + key);
		}

		_clusterPortalCache.put(key, key, timeToLive);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultiVMKeyPoolPortalCache.class);

	private static Object _dummyValue = new Object();

	private PortalCache _clusterPortalCache;
	private PortalCache _localPortalCache;

}