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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * <a href="EhcachePortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EhcachePortalCache implements PortalCache {

	public EhcachePortalCache(Ehcache cache) {
		_cache = cache;
	}

	public Object get(String key) {
		Element element = _cache.get(key);

		if (element == null) {
			return null;
		}
		else {
			return element.getObjectValue();
		}
	}

	public void put(String key, Object obj) {
		Element element = new Element(key, obj);

		_cache.put(element);
	}

	public void put(String key, Object obj, int timeToLive) {
		Element element = new Element(key, obj);

		element.setTimeToLive(timeToLive);

		_cache.put(element);
	}

	public void put(String key, Serializable obj) {
		Element element = new Element(key, obj);

		_cache.put(element);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		Element element = new Element(key, obj);

		element.setTimeToLive(timeToLive);

		_cache.put(element);
	}

	public void remove(String key) {
		_cache.remove(key);
	}

	public void removeAll() {
		_cache.removeAll();
	}

	private Ehcache _cache;

}