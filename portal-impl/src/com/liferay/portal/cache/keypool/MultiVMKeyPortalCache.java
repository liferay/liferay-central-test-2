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
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.listener.CacheListener;
import com.liferay.portal.kernel.cache.listener.CacheListenerScope;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 */
public class MultiVMKeyPortalCache extends BasePortalCache {
	public MultiVMKeyPortalCache(
		PortalCache clusterCache, PortalCache localCache) {

		_clusterCache = clusterCache;
		_localCache = localCache;
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

		Object clusterKey = _clusterCache.get(key);
		Object value = _localCache.get(key);

		if (value == null) {
			if (key.equals(clusterKey)) {
				_localCache.put(key, _DUMMY_VALUE);

				if (_log.isDebugEnabled()) {
					_log.debug("Flagging [" + key + "] to store local only");
				}
			}
		}
		else {
			if (value.equals(_DUMMY_VALUE) || !key.equals(clusterKey)) {
				value = null;

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Key [" + key + "] already flagged or not in cluster");
				}
			}
		}

		return value;
	}

	public void put(String key, Object obj) {
		updateClusterKey(key);

		_localCache.put(key, obj);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local [" + key + "]");
		}
	}

	public void put(String key, Object obj, int timeToLive) {
		updateClusterKey(key, timeToLive);

		_localCache.put(key, obj, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local [" + key + "]");
		}
	}

	public void put(String key, Serializable obj) {
		updateClusterKey(key);

		_localCache.put(key, obj);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local [" + key + "]");
		}
	}

	public void put(String key, Serializable obj, int timeToLive) {
		updateClusterKey(key, timeToLive);

		_localCache.put(key, obj, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Storing local [" + key + "]");
		}
	}

	public void registerCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		_clusterCache.registerCacheListener(cacheListener);
	}

	public void registerCacheListener(
			CacheListener cacheListener, CacheListenerScope cacheListenerScope)
		throws PortalCacheException {

		_clusterCache.registerCacheListener(cacheListener, cacheListenerScope);
	}

	public void remove(String key) {
		_clusterCache.remove(key);
		_localCache.remove(key);
	}

	public void removeAll() {
		_clusterCache.removeAll();
		_localCache.removeAll();
	}

	public void unregisterAllCacheListeners() {
		_clusterCache.unregisterAllCacheListeners();
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		_clusterCache.unregisterCacheListener(cacheListener);
	}

	protected void updateClusterKey(String key) {
		if (!(_DUMMY_VALUE == _localCache.get(key)) ||
			!key.equals(_clusterCache.get(key))) {

			_clusterCache.put(key, key);

			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating cluster key [" + key + "]");
			}
		}
	}

	protected void updateClusterKey(String key, int timeToLive) {
		if (!(_DUMMY_VALUE == _localCache.get(key)) ||
			!key.equals(_clusterCache.get(key))) {

			_clusterCache.put(key, key, timeToLive);

			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating cluster key [" + key + "]");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultiVMKeyPortalCache.class);

	private static final Object _DUMMY_VALUE = new Object();

	private PortalCache _clusterCache;
	private PortalCache _localCache;
}