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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.listener.CacheListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Edward C. Han
 */
public class WrappedCacheEventListener implements CacheEventListener {
	public WrappedCacheEventListener(
		CacheListener cacheListener, PortalCache portalCache) {

		_cacheListener = cacheListener;
		_portalCache = portalCache;
	}

	public void notifyElementEvicted(Ehcache ehcache, Element element) {
		_cacheListener.notifyEntryEvicted(
			_portalCache, element.getKey().toString(),
			element.getObjectValue());

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyElementEvicted: "
				+ element.getKey());
		}
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		_cacheListener.notifyEntryExpired(
			_portalCache, element.getKey().toString(),
			element.getObjectValue());

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyElementExpired: "
				+ element.getKey());
		}
	}

	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		_cacheListener.notifyEntryPut(
			_portalCache, element.getKey().toString(),
			element.getObjectValue());

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyElementPut: "
				+ element.getKey());
		}
	}

	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		_cacheListener.notifyEntryRemoved(
			_portalCache, element.getKey().toString(),
			element.getObjectValue());

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyElementRemoved: "
				+ element.getKey());
		}
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		_cacheListener.notifyEntryUpdated(
			_portalCache, element.getKey().toString(),
			element.getObjectValue());

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyElementUpdated: "
				+ element.getKey());
		}
	}

	public void notifyRemoveAll(Ehcache ehcache) {
		_cacheListener.notifyRemoveAll(_portalCache);

		if (_log.isDebugEnabled()) {
			_log.debug(ehcache.getName() + " - notifyRemoveAll");
		}
	}

	public Object clone() throws CloneNotSupportedException {
		return new WrappedCacheEventListener(_cacheListener, _portalCache);
	}

	public void dispose() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WrappedCacheEventListener.class);

	private CacheListener _cacheListener;
	private PortalCache _portalCache;

}