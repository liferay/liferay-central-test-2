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

import org.apache.velocity.runtime.resource.Resource;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayResourceCacheUtil {

	public static final String CACHE_NAME =
		LiferayResourceCacheUtil.class.getName();

	public static void clear() {
		_portalCache.removeAll();
	}

	public static Resource get(String key) {
		Object resource = _portalCache.get(key);

		if ((resource != null) && (resource instanceof Resource)) {
			return (Resource)resource;
		}

		return null;
	}

	public static void put(String key, Resource resource) {
		_portalCache.put(key, resource);
	}

	public static void remove(String key) {
		_portalCache.remove(key);
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}