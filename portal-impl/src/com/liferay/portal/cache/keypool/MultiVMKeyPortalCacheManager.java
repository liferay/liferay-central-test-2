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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.cache.listener.CacheListenerScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Edward Han
 */
public class MultiVMKeyPortalCacheManager implements PortalCacheManager {

	public void clearAll() throws PortalCacheException {
		for (MultiVMKeyPortalCache cache : _multiVMKeyPortalCaches.values()) {
			cache.removeAll();
		}
	}

	public PortalCache getCache(String name) throws PortalCacheException {
		return getCache(name, false);
	}


	public PortalCache getCache(String name, boolean blocking)
		throws PortalCacheException {

		MultiVMKeyPortalCache multiVMKeyPortalCache =
			_multiVMKeyPortalCaches.get(name);

		if (multiVMKeyPortalCache == null) {
			synchronized (_multiVMKeyPortalCaches) {
				PortalCache clusterPortalCache = _multiVMPool.getCache(
					name, blocking);

				PortalCache localPortalCache = _singleVMPool.getCache(
					name, blocking);

				multiVMKeyPortalCache = new MultiVMKeyPortalCache(
					clusterPortalCache, localPortalCache);

				multiVMKeyPortalCache.registerCacheListener(
					new MultiVMKeyExpirationCacheListener(localPortalCache),
					CacheListenerScope.REMOTE);

				_multiVMKeyPortalCaches.put(name, multiVMKeyPortalCache);
			}
		}

		return multiVMKeyPortalCache;
	}

	public void removeCache(String name) {
		synchronized (_multiVMKeyPortalCaches) {
			MultiVMKeyPortalCache cache = _multiVMKeyPortalCaches.get(name);

			if (cache != null) {
				_multiVMPool.removeCache(name);

				_singleVMPool.removeCache(name);

				_multiVMKeyPortalCaches.remove(name);
			}
		}
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_singleVMPool = singleVMPool;
	}

	private Map<String, MultiVMKeyPortalCache> _multiVMKeyPortalCaches =
		new ConcurrentHashMap<String, MultiVMKeyPortalCache>();
	private MultiVMPool _multiVMPool;
	private SingleVMPool _singleVMPool;

}