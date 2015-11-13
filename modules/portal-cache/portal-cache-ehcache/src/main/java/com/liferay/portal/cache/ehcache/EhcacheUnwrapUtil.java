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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.PortalCacheWrapper;
import com.liferay.portal.cache.ehcache.internal.EhcachePortalCache;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;

/**
 * @author Shuyang Zhou
 */
public class EhcacheUnwrapUtil {

	public static Ehcache getEhcache(PortalCache<?, ?> portalCache) {
		EhcachePortalCache<?, ?> ehcachePortalCache = getEhcachePortalCache(
			portalCache);

		return ehcachePortalCache.getEhcache();
	}

	public static <K extends Serializable, V> EhcachePortalCache<K, V>
		getEhcachePortalCache(PortalCache<K, V> portalCache) {

		PortalCache<K, V> currentPortalCache = portalCache;

		while (currentPortalCache instanceof PortalCacheWrapper) {
			PortalCacheWrapper<K, V> portalCacheWrapper =
				(PortalCacheWrapper<K, V>)currentPortalCache;

			currentPortalCache = portalCacheWrapper.getWrappedPortalCache();
		}

		if (currentPortalCache instanceof EhcachePortalCache) {
			return (EhcachePortalCache<K, V>)currentPortalCache;
		}

		throw new IllegalArgumentException(
			"Unable to locate Ehcache from " + portalCache);
	}

}