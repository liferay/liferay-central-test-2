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