/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.cache;

import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * <a href="SingleVMPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 *
 */
public class SingleVMPool {

	public static void clear() {
		_instance._cacheManager.clearAll();
	}

	public static void clear(String name) {
		Cache cache = getCache(name);

		cache.removeAll();
	}

	public static Object get(String name, String key) {
		Cache cache = getCache(name);

		return get(cache, key);
	}

	public static Object get(Cache cache, String key) {
		Element element = cache.get(key);

		if (element == null) {
			return null;
		}
		else {
			return element.getObjectValue();
		}
	}

	public static Cache getCache(String name) {
		Cache cache = _instance._cacheManager.getCache(name);

		if (cache == null) {
			_instance._cacheManager.addCache(name);

			cache = _instance._cacheManager.getCache(name);
		}
		return cache;
	}

	public static void put(String name, String key, Object object) {
		Cache cache = getCache(name);

		put(cache, key, object);
	}

	public static void put(Cache cache, String key, Object object) {
		Element element = new Element(key, object);

		cache.put(element);
	}

	public static void put(String name, String key, Serializable object) {
		Cache cache = getCache(name);

		put(cache, key, object);
	}

	public static void put(Cache cache, String key, Serializable object) {
		Element element = new Element(key, object);

		cache.put(element);
	}

	public static void remove(String name, String key) {
		Cache cache = getCache(name);

		remove(cache, key);
	}

	public static void remove(Cache cache, String key) {
		cache.remove(key);
	}

	private SingleVMPool() {
		String configLocation = PropsUtil.get(
			PropsUtil.EHCACHE_SINGLE_VM_CONFIG_LOCATION);

		URL url = getClass().getResource(configLocation);

		_cacheManager = new CacheManager(url);
	}

	private static SingleVMPool _instance = new SingleVMPool();

	private CacheManager _cacheManager;

}