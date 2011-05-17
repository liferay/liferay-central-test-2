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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public interface PortalCache {

	public void destroy();

	public Collection<Object> get(Collection<String> keys);

	public Object get(String key);

	public void put(String key, Object value);

	public void put(String key, Object value, int timeToLive);

	public void put(String key, Serializable value);

	public void put(String key, Serializable value, int timeToLive);

	public void registerCacheListener(CacheListener cacheListener);

	public void registerCacheListener(
		CacheListener cacheListener, CacheListenerScope cacheListenerScope);

	public void remove(String key);

	public void removeAll();

	public void setDebug(boolean debug);

	public void unregisterCacheListener(CacheListener cacheListener);

	public void unregisterCacheListeners();

}