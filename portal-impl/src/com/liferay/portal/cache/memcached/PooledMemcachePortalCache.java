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
import com.liferay.portal.cache.memcached.factory.MemcachedClientFactory;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <a href="PooledMemcachePortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PooledMemcachePortalCache implements PortalCache {

	public PooledMemcachePortalCache(
		MemcachedClientFactory memcachedClientFactory, int timeout,
		TimeUnit timeoutTimeUnit) {

		_memcachedClientFactory = memcachedClientFactory;
		_timeout = timeout;
		_timeoutTimeUnit = timeoutTimeUnit;
	}

	public Object get(String key) {
		MemcachedClientIF memcachedClient;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return null;
		}

		try {
			Object cachedObject = null;

			Future<Object> future = memcachedClient.asyncGet(key);

			try {
				cachedObject = future.get(_timeout, _timeoutTimeUnit);
			}
			catch (Exception e) {
				future.cancel(true);
			}

			return cachedObject;
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void put(String key, Object obj) {
		put(key, obj, _timeToLive);
	}

	public void put(String key, Object obj, int timeToLive) {
		MemcachedClientIF memcachedClient;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		try {
			memcachedClient.set(key, timeToLive, obj);
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void put(String key, Serializable obj) {
		put(key, obj, _timeToLive);

	}

	public void put(String key, Serializable obj, int timeToLive) {
		MemcachedClientIF memcachedClient;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		try {
			memcachedClient.set(key, timeToLive, obj);
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void remove(String key) {
		MemcachedClientIF memcachedClient;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		try {
			memcachedClient.delete(key);
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void removeAll() {
		MemcachedClientIF memcachedClient;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		try {
			memcachedClient.flush();
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void setTimeToLive(int timeToLive) {
		_timeToLive = timeToLive;
	}

	protected void cleanupClient(MemcachedClientIF memcachedClient) {
		try {
			_memcachedClientFactory.returnMemcachedObject(memcachedClient);
		}
		catch (Exception e) {
			//nothing to be done...
		}
	}


	private MemcachedClientFactory _memcachedClientFactory;
	private int _timeout;
	private TimeUnit _timeoutTimeUnit;
	private int _timeToLive;

}
