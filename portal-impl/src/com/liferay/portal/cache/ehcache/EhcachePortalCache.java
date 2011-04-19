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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.listener.CacheListener;
import com.liferay.portal.kernel.cache.listener.CacheListenerScope;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class EhcachePortalCache extends BasePortalCache {

	public EhcachePortalCache(Ehcache cache) {
		_cache = cache;
	}

	public Object get(String key) {
		String processedKey = processKey(key);

		Element element = _cache.get(processedKey);

		if (element == null) {
			return null;
		}
		else {
			return element.getObjectValue();
		}
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public void put(String key, Object obj) {
		Element element = createElement(key, obj);

		_cache.put(element);
	}

	public void put(String key, Object obj, int timeToLive) {
		Element element = createElement(key, obj);

		element.setTimeToLive(timeToLive);

		_cache.put(element);
	}

	public void put(String key, Serializable obj) {
		Element element = createElement(key, obj);

		_cache.put(element);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		Element element = createElement(key, obj);

		element.setTimeToLive(timeToLive);

		_cache.put(element);
	}

	public void registerCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		registerCacheListener(cacheListener, CacheListenerScope.ALL);
	}

	public void registerCacheListener(
			CacheListener cacheListener, CacheListenerScope cacheListenerScope)
		throws PortalCacheException {

		if (_cacheListenersMap.containsKey(cacheListener)) {
			return;
		}

		CacheEventListener cacheEventListener =
			new WrappedCacheEventListener(cacheListener, this);

		_cacheListenersMap.put(cacheListener, cacheEventListener);

		NotificationScope notificationScope =
			convertCacheListenerScope(cacheListenerScope);

		RegisteredEventListeners registeredEventListeners =
			_cache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			cacheEventListener, notificationScope);
	}

	public void remove(String key) {
		String processedKey = processKey(key);

		_cache.remove(processedKey);
	}

	public void removeAll() {
		_cache.removeAll();
	}

	public void unregisterAllCacheListeners() {
		RegisteredEventListeners registeredEventListeners =
			_cache.getCacheEventNotificationService();

		for (CacheEventListener cacheEventListener :
				_cacheListenersMap.values()) {

			registeredEventListeners.unregisterListener(cacheEventListener);
		}

		_cacheListenersMap.clear();
	}

	public void unregisterCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		CacheEventListener cacheEventListener = _cacheListenersMap.get(
			cacheListener);

		if (cacheEventListener != null) {
			RegisteredEventListeners registeredEventListeners =
				_cache.getCacheEventNotificationService();

			registeredEventListeners.unregisterListener(cacheEventListener);
		}

		_cacheListenersMap.remove(cacheListener);
	}

	protected NotificationScope convertCacheListenerScope(
			CacheListenerScope scope) {

		if (scope == CacheListenerScope.ALL) {
			return NotificationScope.ALL;
		}
		else if (scope == CacheListenerScope.LOCAL) {
			return NotificationScope.LOCAL;
		}
		else {
			return NotificationScope.REMOTE;
		}
	}

	protected Element createElement(String key, Object obj) {
		String processedKey = processKey(key);

		Element element = new Element(processedKey, obj);

		return element;
	}

	private Ehcache _cache;
	private Map<CacheListener, CacheEventListener> _cacheListenersMap =
		new ConcurrentHashMap<CacheListener, CacheEventListener>();

}