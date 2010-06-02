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

import net.spy.memcached.MemcachedClientIF;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <a href="MemcachePortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MemcachePortalCache implements PortalCache {

	public MemcachePortalCache(
		MemcachedClientIF memcachedClient, int timeout,
		TimeUnit timeoutTimeUnit) {

		_memcachedClient = memcachedClient;
		_timeout = timeout;
		_timeoutTimeUnit = timeoutTimeUnit;
	}

	public Object get(String key) {
		Object cachedObject = null;

		Future<Object> future = _memcachedClient.asyncGet(key);

		try {
			cachedObject = future.get(_timeout, _timeoutTimeUnit);
		}
		catch (Throwable e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Memcache operation error", e);
			}
			future.cancel(true);
		}

		return cachedObject;
	}

	public void put(String key, Object obj) {
		put(key, obj, _timeToLive);
	}

	public void put(String key, Object obj, int timeToLive) {

		_memcachedClient.set(key, timeToLive, obj);
	}

	public void put(String key, Serializable obj) {
		put(key, obj, _timeToLive);

	}

	public void put(String key, Serializable obj, int timeToLive) {
		_memcachedClient.set(key, timeToLive, obj);
	}

	public void remove(String key) {
		_memcachedClient.delete(key);
	}

	public void removeAll() {
		_memcachedClient.flush();
	}

	public void setTimeToLive(int timeToLive) {
		_timeToLive = timeToLive;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MemcachePortalCache.class);

	private int _timeout;
	private TimeUnit _timeoutTimeUnit;
	private int _timeToLive;

	private MemcachedClientIF _memcachedClient;
}
