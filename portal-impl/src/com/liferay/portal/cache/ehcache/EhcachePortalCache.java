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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Brian Wing Shun Chan
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

	public void remove(String key) {
		String processedKey = processKey(key);

		_cache.remove(processedKey);
	}

	public void removeAll() {
		_cache.removeAll();
	}

	protected Element createElement(String key, Object obj) {
		String processedKey = processKey(key);

		Element element = new Element(processedKey, obj);

		return element;
	}

	private Ehcache _cache;

}