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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ThreadLocalCacheManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ThreadLocalCacheManager {

	public static void clearAll(Lifecycle lifecycle) {
		Map<String, ThreadLocalCache<?>> cacheGroup =
			_cacheContainerThreadLocal.get().get(lifecycle);
		if (cacheGroup != null) {
			cacheGroup.clear();
		}
	}

	public static void destroy() {
		_cacheContainerThreadLocal.remove();
	}

	public static <T> ThreadLocalCache<T> getCache(
		String name, Lifecycle lifecycle) {

		Map<Lifecycle, Map<String, ThreadLocalCache<?>>>
			cacheContainer = _cacheContainerThreadLocal.get();

		Map<String, ThreadLocalCache<?>> cacheGroup =
			cacheContainer.get(lifecycle);
		if (cacheGroup == null) {
			cacheGroup = new HashMap<String, ThreadLocalCache<?>>();
			cacheContainer.put(lifecycle, cacheGroup);
		}

		ThreadLocalCache<?> cache = cacheGroup.get(name);
		if (cache == null) {
			cache = new ThreadLocalCache(name, lifecycle);
			cacheGroup.put(name, cache);
		}

		return (ThreadLocalCache<T>) cache;
	}

	private static ThreadLocal<Map<Lifecycle, Map<String, ThreadLocalCache<?>>>>
		_cacheContainerThreadLocal =
			new InitialThreadLocal<
				Map<Lifecycle, Map<String, ThreadLocalCache<?>>>>(
					new EnumMap<Lifecycle, Map<String, ThreadLocalCache<?>>>(
						Lifecycle.class));

}