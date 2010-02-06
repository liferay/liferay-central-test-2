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

package com.liferay.portal.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="WebAppPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebAppPool {

	public static Object get(String webAppId, String key) {
		return _instance._get(webAppId, key);
	}

	public static void put(String webAppId, String key, Object obj) {
		_instance._put(webAppId, key, obj);
	}

	public static Object remove(String webAppId, String key) {
		return _instance._remove(webAppId, key);
	}

	private WebAppPool() {
		_webAppPool = new ConcurrentHashMap<String, Map<String, Object>>();
	}

	private Object _get(String webAppId, String key) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			return null;
		}
		else {
			return map.get(key);
		}
	}

	private void _put(String webAppId, String key, Object obj) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			map = new ConcurrentHashMap<String, Object>();

			_webAppPool.put(webAppId, map);
		}

		map.put(key, obj);
	}

	private Object _remove(String webAppId, String key) {
		Map<String, Object> map = _webAppPool.get(webAppId);

		if (map == null) {
			return null;
		}
		else {
			return map.remove(key);
		}
	}

	private static WebAppPool _instance = new WebAppPool();

	private Map<String, Map<String, Object>> _webAppPool;

}