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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class MultiVMKeyPoolCacheListener implements CacheListener {

	public MultiVMKeyPoolCacheListener(PortalCache localPortalCache) {
		_localPortalCache = localPortalCache;
	}

	public void notifyEntryEvicted(
		PortalCache portalCache, String key, Object value) {

		_localPortalCache.remove(String.valueOf(value));
	}

	public void notifyEntryExpired(
		PortalCache portalCache, String key, Object value) {

		_localPortalCache.remove(String.valueOf(value));
	}

	public void notifyEntryPut(
		PortalCache portalCache, String key, Object value) {

		_localPortalCache.remove(String.valueOf(value));
	}

	public void notifyEntryRemoved(
		PortalCache portalCache, String key, Object value) {

		_localPortalCache.remove(String.valueOf(value));
	}

	public void notifyEntryUpdated(
		PortalCache portalCache, String key, Object value) {

		_localPortalCache.remove(String.valueOf(value));
	}

	public void notifyRemoveAll(PortalCache portalCache) {
		_localPortalCache.removeAll();
	}

	private PortalCache _localPortalCache;

}