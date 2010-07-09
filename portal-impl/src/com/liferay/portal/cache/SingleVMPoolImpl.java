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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class SingleVMPoolImpl implements SingleVMPool {

	public void clear() {
		_portalCacheManager.clearAll();
	}

	public void clear(String name) {
		PortalCache portalCache = getCache(name);

		portalCache.removeAll();
	}

	public Object get(String name, String key) {
		PortalCache portalCache = getCache(name);

		return portalCache.get(key);
	}

	/**
	 * @deprecated
	 */
	public Object get(PortalCache portalCache, String key) {
		return portalCache.get(key);
	}

	public PortalCache getCache(String name) {
		return _portalCacheManager.getCache(name);
	}

	public PortalCache getCache(String name, boolean blocking) {
		return _portalCacheManager.getCache(name, blocking);
	}

	public void put(String name, String key, Object obj) {
		PortalCache portalCache = getCache(name);

		portalCache.put(key, obj);
	}

	/**
	 * @deprecated
	 */
	public void put(PortalCache portalCache, String key, Object obj) {
		portalCache.put(key, obj);
	}

	/**
	 * @deprecated
	 */
	public void put(
		PortalCache portalCache, String key, Object obj, int timeToLive) {

		portalCache.put(key, obj, timeToLive);
	}

	public void put(String name, String key, Serializable obj) {
		PortalCache portalCache = getCache(name);

		portalCache.put(key, obj);
	}

	/**
	 * @deprecated
	 */
	public void put(PortalCache portalCache, String key, Serializable obj) {
		portalCache.put(key, obj);
	}

	/**
	 * @deprecated
	 */
	public void put(
		PortalCache portalCache, String key, Serializable obj, int timeToLive) {

		portalCache.put(key, obj, timeToLive);
	}

	public void remove(String name, String key) {
		PortalCache portalCache = getCache(name);

		portalCache.remove(key);
	}

	/**
	 * @deprecated
	 */
	public void remove(PortalCache portalCache, String key) {
		portalCache.remove(key);
	}

	public void setPortalCacheManager(PortalCacheManager portalCacheManager) {
		_portalCacheManager = portalCacheManager;
	}

	private PortalCacheManager _portalCacheManager;

}