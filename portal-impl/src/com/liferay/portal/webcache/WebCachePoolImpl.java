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

package com.liferay.portal.webcache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePool;

/**
 * <a href="WebCachePoolImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolImpl implements WebCachePool {

	public static final String CACHE_NAME = WebCachePool.class.getName();

	public void afterPropertiesSet() {
		_cache = _singleVMPool.getCache(CACHE_NAME);
	}

	public void clear() {
		_cache.removeAll();
	}

	public Object get(String key, WebCacheItem wci) {
		Object obj = _cache.get(key);

		if (obj == null) {
			try {
				obj = wci.convert(key);

				int timeToLive = (int)(wci.getRefreshTime() / Time.SECOND);

				_cache.put(key, obj, timeToLive);
			}
			catch (WebCacheException wce) {
				if (_log.isWarnEnabled()) {
					Throwable cause = wce.getCause();

					if (cause != null) {
						_log.warn(cause, cause);
					}
					else {
						_log.warn(wce, wce);
					}
				}
			}
		}

		return obj;
	}

	public void remove(String key) {
		_cache.remove(key);
	}

	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_singleVMPool = singleVMPool;
	}

	private static Log _log = LogFactoryUtil.getLog(WebCachePoolImpl.class);

	private SingleVMPool _singleVMPool;
	private PortalCache _cache;

}