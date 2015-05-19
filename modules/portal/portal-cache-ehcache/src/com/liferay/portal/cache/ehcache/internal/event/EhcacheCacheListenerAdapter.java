/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.ehcache.internal.EhcacheUnwrapUtil;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;

import java.io.Serializable;

import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Tina Tian
 */
public class EhcacheCacheListenerAdapter<K extends Serializable, V>
	implements CacheListener<K, V> {

	public EhcacheCacheListenerAdapter(CacheEventListener cacheEventListener) {
		this.cacheEventListener = cacheEventListener;
	}

	@Override
	public void dispose() {
		cacheEventListener.dispose();
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = new Element(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementEvicted(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = new Element(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementExpired(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = new Element(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementPut(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = new Element(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementRemoved(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = new Element(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementUpdated(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		cacheEventListener.notifyRemoveAll(
			EhcacheUnwrapUtil.getEhcache(portalCache));
	}

	protected final CacheEventListener cacheEventListener;

}