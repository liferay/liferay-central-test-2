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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.listener.CacheListener;

/**
 * @author Edward Han
 */
public class MultiVMKeyExpirationCacheListener implements CacheListener {
	public MultiVMKeyExpirationCacheListener(PortalCache localCache) {
		_localCache = localCache;
	}

	public void notifyEntryEvicted(
		PortalCache cache, String key, Object value) {

		_localCache.remove(value.toString());
	}

	public void notifyEntryExpired(
		PortalCache cache, String key, Object value) {

		_localCache.remove(value.toString());
	}

	public void notifyEntryPut(PortalCache cache, String key, Object value)
		throws PortalCacheException {

		_localCache.remove(value.toString());
	}

	public void notifyEntryRemoved(
			PortalCache cache, String key, Object value)
		throws PortalCacheException {

		_localCache.remove(value.toString());
	}

	public void notifyEntryUpdated(
			PortalCache cache, String key, Object value)
		throws PortalCacheException {

		_localCache.remove(value.toString());
	}

	public void notifyRemoveAll(PortalCache cache) {
		_localCache.removeAll();
	}

	private PortalCache _localCache;
}