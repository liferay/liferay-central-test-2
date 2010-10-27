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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.velocity.runtime.resource.Resource;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayResourceCacheUtil {

	public static final String CACHE_NAME =
		LiferayResourceCacheUtil.class.getName();

	public static void clear() {
		clearLocalCache();

		_portalCache.removeAll();
	}

	public static void clearLocalCache() {
		if (_localCacheAvailable) {
			_localCache.remove();
		}
	}

	public static Resource get(String key) {
		if (_localCacheAvailable) {
			Object result = null;

			Map<String, Object> localCache = null;

			localCache = _localCache.get();

			result = localCache.get(key);

			if ((result != null) && (result instanceof Resource)) {
				Resource resource = (Resource) result;

				Object lastModified = _portalCache.get(key);

				if (lastModified != null &&
					lastModified.equals(resource.getLastModified())) {

					return resource;
				}

				localCache.remove(key);
			}
		}

		return null;
	}

	public static void put(String key, Resource resource) {
		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			localCache.put(key, resource);
		}

		Long lastModified = resource.getLastModified();

		_portalCache.put(key, lastModified);
	}

	public static void remove(String key) {
		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			localCache.remove(key);
		}

		_portalCache.remove(key);
	}


	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;

	static {
		if (PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED &&
			PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_MAX_SIZE > 0) {

			_localCache = new AutoResetThreadLocal<LRUMap>(
				LiferayResourceCacheUtil.class + "._localCache",
				new LRUMap(
					PropsValues.
						VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}