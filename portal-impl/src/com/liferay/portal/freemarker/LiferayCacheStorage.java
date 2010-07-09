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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;

import freemarker.cache.ConcurrentCacheStorage;

/**
 * @author Mika Koivisto
 */
public class LiferayCacheStorage implements ConcurrentCacheStorage {

	public static final String CACHE_NAME = LiferayCacheStorage.class.getName();

	public void clear() {
		_cache.removeAll();
	}

	public Object get(Object key) {
		return _cache.get(key.toString());
	}

	public boolean isConcurrent() {
		return true;
	}

	public void put(Object key, Object value) {
		_cache.put(key.toString(), value);
	}

	public void remove(Object key) {
		_cache.remove(key.toString());
	}

	private static PortalCache _cache = SingleVMPoolUtil.getCache(CACHE_NAME);

}