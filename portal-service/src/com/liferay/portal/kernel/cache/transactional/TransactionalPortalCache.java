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

package com.liferay.portal.kernel.cache.transactional;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCache extends BasePortalCache {

	public TransactionalPortalCache(PortalCache portalCache) {
		_portalCache = portalCache;
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public Object get(String key) {
		Object result = null;
		if (TransactionalPortalCacheUtil.enabled()) {
			result = TransactionalPortalCacheUtil.get(_portalCache, key);
		}
		if (result == null) {
			result = _portalCache.get(key);
		}

		return result;
	}

	public void put(String key, Object obj) {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.put(_portalCache, key, obj);
		}
		else {
			_portalCache.put(key, obj);
		}
	}

	public void put(String key, Object obj, int timeToLive) {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.put(_portalCache, key, obj);
		}
		else {
			_portalCache.put(key, obj, timeToLive);
		}
	}

	public void put(String key, Serializable obj) {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.put(_portalCache, key, obj);
		}
		else {
			_portalCache.put(key, obj);
		}
	}

	public void put(String key, Serializable obj, int timeToLive) {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.put(_portalCache, key, obj);
		}
		else {
			_portalCache.put(key, obj, timeToLive);
		}
	}

	public void remove(String key) {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.remove(_portalCache, key);
		}
		else {
			_portalCache.remove(key);
		}
	}

	public void removeAll() {
		if (TransactionalPortalCacheUtil.enabled()) {
			TransactionalPortalCacheUtil.removeAll(_portalCache);
		}
		else {
			_portalCache.removeAll();
		}
	}

	private PortalCache _portalCache;

}