/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CacheRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CacheRegistry {

	public static void clear() {
		for (Map.Entry<String, CacheRegistryItem> entry : _items.entrySet()) {
			CacheRegistryItem item = entry.getValue();

			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating " + item.getRegistryName());
			}

			item.invalidate();
		}
	}

	public static void clear(String name) {
		CacheRegistryItem item = _items.get(name);

		if (item != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating " + name);
			}

			item.invalidate();
		}
		else {
			_log.error("No cache registry found with name " + name);
		}
	}

	public static boolean isActive() {
		return _active;
	}

	public static void register(CacheRegistryItem item) {
		String name = item.getRegistryName();

		if (_log.isDebugEnabled()) {
			_log.debug("Registering " + name);
		}

		if (_items.containsKey(name)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Not registering duplicate " + name);
			}
		}
		else {
			_items.put(name, item);
		}
	}

	public static void setActive(boolean active) {
		_active = active;

		if (!active) {
			clear();
		}
	}

	public static void unregister(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("Unregistering " + name);
		}

		_items.remove(name);
	}

	private static Log _log = LogFactoryUtil.getLog(CacheRegistry.class);

	private static boolean _active = true;
	private static Map<String, CacheRegistryItem> _items =
		new ConcurrentHashMap<String, CacheRegistryItem>();

}