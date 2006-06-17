/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.CachePolicy;
import com.liferay.util.ConverterException;
import com.liferay.util.ExtPropertiesLoader;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WebCachePool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebCachePool {

	static GeneralCacheAdministrator cache = null;

	static {
		cache = new GeneralCacheAdministrator(ExtPropertiesLoader.getInstance(
			PropsFiles.CACHE_SINGLE_VM).getProperties());
	}

	public static void clear() {
		cache.flushAll();
	}

	public static Object get(String key, WebCacheable wc) {
		Object obj = null;

		try {
			obj = cache.getFromCache(key);
		}
		catch (NeedsRefreshException nfe) {
			try {
				obj = wc.convert(key);

				cache.putInCache(
					key, obj, new CachePolicy(wc.getRefreshTime()));
			}
			catch (ConverterException ce) {
				_log.error(ce.getMessage());
			}
			finally {
				if (obj == null) {
					cache.cancelUpdate(key);
				}
			}
		}

		return obj;
	}

	public static Object remove(String key) {
		Object obj = null;

		try {
			obj = cache.getFromCache(key);

			cache.flushEntry(key);
		}
		catch (NeedsRefreshException nfe) {
		}
		finally {
			if (obj == null) {
				cache.cancelUpdate(key);
			}
		}

		return obj;
	}

	private static Log _log = LogFactory.getLog(WebCachePool.class);

}