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
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.listener.CacheListener;
import com.liferay.portal.kernel.cache.listener.CacheListenerScope;

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

	public void put(String key, Object obj) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, obj);

		notifyPutEvents(key, obj, updated);
	}

	public void put(String key, Object obj, int timeToLive) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, obj);

		notifyPutEvents(key, obj, updated);
	}

	public void put(String key, Serializable obj) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(key);

		_map.put(processedKey, obj);

		notifyPutEvents(key, obj, updated);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		String processedKey = processKey(key);

		boolean updated = _map.containsKey(processedKey);

		_map.put(processedKey, obj);

		notifyPutEvents(key, obj, updated);
	}

	public void registerCacheListener(CacheListener cacheListener)
			throws PortalCacheException {

		Lock writeLock = _cacheListenersRWLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.add(cacheListener);
		} finally {
			writeLock.unlock();
		}
	}

	public void registerCacheListener(
			CacheListener cacheListener, CacheListenerScope cacheListenerScope)
		throws PortalCacheException {

		registerCacheListener(cacheListener);
	}

	public void remove(String key) {
		String processedKey = processKey(key);

		Object data = _map.remove(processedKey);

		Lock readLock = _cacheListenersRWLock.readLock();

		try {
			readLock.lock();

			for (CacheListener cacheListener : _cacheListeners) {
				cacheListener.notifyEntryRemoved(this, key, data);
			}
		} finally {
			readLock.unlock();
		}
	}

	public void removeAll() {
		_map.clear();

		Lock readLock = _cacheListenersRWLock.readLock();

		try {
			readLock.lock();

			for (CacheListener cacheListener : _cacheListeners) {
				cacheListener.notifyRemoveAll(this);
			}
		} finally {
			readLock.unlock();
		}
	}

	public void unregisterAllCacheListeners() {
		Lock writeLock = _cacheListenersRWLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.clear();
		} finally {
			writeLock.unlock();
		}
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		Lock writeLock = _cacheListenersRWLock.writeLock();

		try {
			writeLock.lock();

			_cacheListeners.remove(cacheListener);
		} finally {
			writeLock.unlock();
		}
	}

	protected void notifyPutEvents(String key, Object obj, boolean updated) {
		Lock readLock = _cacheListenersRWLock.readLock();

		try {
			readLock.lock();

			if (updated) {
				for (CacheListener cacheListener : _cacheListeners) {
					cacheListener.notifyEntryUpdated(this, key, obj);
				}
			}
			else {
				for (CacheListener cacheListener : _cacheListeners) {
					cacheListener.notifyEntryPut(this, key, obj);
				}
			}
		} finally {
			readLock.unlock();
		}
	}

	private Set<CacheListener> _cacheListeners = new HashSet<CacheListener>();
	private ReadWriteLock _cacheListenersRWLock = new ReentrantReadWriteLock();
	private Map<String, Object> _map;

}