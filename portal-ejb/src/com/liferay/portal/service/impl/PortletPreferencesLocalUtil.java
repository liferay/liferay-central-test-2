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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.ClusterPool;
import com.liferay.util.CollectionFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import java.util.Map;

/**
 * <a href="PortletPreferencesLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesLocalUtil {

	public static final String GROUP_NAME =
		PortletPreferencesLocalUtil.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[] {GROUP_NAME};

	protected static void clearPreferencesPool() {
		_cache.flushGroup(GROUP_NAME);
	}

	protected static void clearPreferencesPool(String key) {
		key = _encodeKey(key);

		_cache.flushEntry(key);
	}

	protected static Map getPreferencesPool(String key) {
		key = _encodeKey(key);

		Map prefsPool = null;

		try {
			prefsPool = (Map)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nfe) {
			prefsPool = CollectionFactory.getSyncHashMap();

			_cache.putInCache(key, prefsPool, GROUP_NAME_ARRAY);
		}
		finally {
			if (prefsPool == null) {
				_cache.cancelUpdate(key);
			}
		}

		return prefsPool;
	}

	private static String _encodeKey(String key) {
		return GROUP_NAME + StringPool.POUND + key;
	}

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}