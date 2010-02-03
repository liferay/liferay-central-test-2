/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="PortletPreferencesLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class PortletPreferencesLocalUtil {

	public static final String CACHE_NAME =
		PortletPreferencesLocalUtil.class.getName();

	protected static void clearPreferencesPool() {
		_cache.removeAll();
	}

	protected static void clearPreferencesPool(long ownerId, int ownerType) {
		String key = _encodeKey(ownerId, ownerType);

		_cache.remove(key);
	}

	protected static Map<String, PortletPreferencesImpl> getPreferencesPool(
			long ownerId, int ownerType) {
		String key = _encodeKey(ownerId, ownerType);

		Map<String, PortletPreferencesImpl> preferencesPool =
			(Map<String, PortletPreferencesImpl>)_cache.get(key);

		if (preferencesPool == null) {
			preferencesPool =
				new ConcurrentHashMap<String, PortletPreferencesImpl>();

			_cache.put(key, preferencesPool);
		}

		return preferencesPool;
	}

	private static String _encodeKey(long ownerId, int ownerType) {
		StringBundler sb = new StringBundler(5);

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(ownerId);
		sb.append(StringPool.POUND);
		sb.append(ownerType);

		return sb.toString();
	}

	private static PortalCache _cache = MultiVMPoolUtil.getCache(CACHE_NAME);

}