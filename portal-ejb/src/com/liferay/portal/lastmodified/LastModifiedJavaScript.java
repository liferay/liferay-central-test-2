/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lastmodified;

import com.liferay.portal.util.PropsFiles;
import com.liferay.util.ExtPropertiesLoader;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * <a href="LastModifiedJavaScript.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LastModifiedJavaScript {

	public static void clear() {
		_instance._clear();
	}

	public static String get(String key) {
		return _instance._get(key);
	}

	public static String put(String key, String obj) {
		return _instance._put(key, obj);
	}

	public static String remove(String key) {
		return _instance._remove(key);
	}

	private LastModifiedJavaScript() {
		_cache = new GeneralCacheAdministrator(ExtPropertiesLoader.getInstance(
			PropsFiles.CACHE_SINGLE_VM).getProperties());
	}

	private void _clear() {
		_cache.flushAll();
	}

	private String _get(String key) {
		String obj = null;

		try {
			obj = (String)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nfe) {
		}
		finally {
			if (obj == null) {
				_cache.cancelUpdate(key);
			}
		}

		return obj;
	}

	private String _put(String key, String obj) {
		if (Validator.isNotNull(key)) {
			_cache.flushEntry(key);
			_cache.putInCache(key, obj);
		}

		return obj;
	}

	private String _remove(String key) {
		String obj = null;

		try {
			obj = (String)_cache.getFromCache(key);

			_cache.flushEntry(key);
		}
		catch (NeedsRefreshException nfe) {
		}
		finally {
			if (obj == null) {
				_cache.cancelUpdate(key);
			}
		}

		return obj;
	}

	private static LastModifiedJavaScript _instance =
		new LastModifiedJavaScript();

	private GeneralCacheAdministrator _cache;

}