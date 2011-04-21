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

package com.liferay.portal.cache.memory;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class MemoryPortalCache extends BasePortalCache {

	public MemoryPortalCache(int initialCapacity) {
		 _map = new ConcurrentHashMap<String, Object>(initialCapacity);
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public Object get(String key) {
		String processedKey = processKey(key);

		return _map.get(processedKey);
	}

	public void put(String key, Object value) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, value);

		notifyPutEvents(key, value, updated);
	}

	public void put(String key, Object value, int timeToLive) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, value);

		notifyPutEvents(key, value, updated);
	}

	public void put(String key, Serializable value) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, value);

		notifyPutEvents(key, value, updated);
	}

	public void put(String key, Serializable value, int timeToLive) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(processedKey);

		_map.put(processedKey, value);

		notifyPutEvents(key, value, updated);
	}

	public void registerCacheListener(CacheListener cacheListener) {
		Lock writeLock = _cacheListenersReadWriteLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.add(cacheListener);
		}
		finally {
			writeLock.unlock();
		}
	}

	public void registerCacheListener(
		CacheListener cacheListener, CacheListenerScope cacheListenerScope) {

		registerCacheListener(cacheListener);
	}

	public void remove(String key) {
		String processedKey = processKey(key);

		Object value = _map.remove(processedKey);

		Lock readLock = _cacheListenersReadWriteLock.readLock();

		try {
			readLock.lock();

			for (CacheListener cacheListener : _cacheListeners) {
				cacheListener.notifyEntryRemoved(this, key, value);
			}
		}
		finally {
			readLock.unlock();
		}
	}

	public void removeAll() {
		_map.clear();

		Lock readLock = _cacheListenersReadWriteLock.readLock();

		try {
			readLock.lock();

			for (CacheListener cacheListener : _cacheListeners) {
				cacheListener.notifyRemoveAll(this);
			}
		}
		finally {
			readLock.unlock();
		}
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		Lock writeLock = _cacheListenersReadWriteLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.remove(cacheListener);
		}
		finally {
			writeLock.unlock();
		}
	}

	public void unregisterCacheListeners() {
		Lock writeLock = _cacheListenersReadWriteLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.clear();
		}
		finally {
			writeLock.unlock();
		}
	}

	protected void notifyPutEvents(String key, Object value, boolean updated) {
		Lock readLock = _cacheListenersReadWriteLock.readLock();

		try {
			readLock.lock();

			if (updated) {
				for (CacheListener cacheListener : _cacheListeners) {
					cacheListener.notifyEntryUpdated(this, key, value);
				}
			}
			else {
				for (CacheListener cacheListener : _cacheListeners) {
					cacheListener.notifyEntryPut(this, key, value);
				}
			}
		}
		finally {
			readLock.unlock();
		}
	}

	private Set<CacheListener> _cacheListeners = new HashSet<CacheListener>();
	private ReadWriteLock _cacheListenersReadWriteLock =
		new ReentrantReadWriteLock();
	private Map<String, Object> _map;

}