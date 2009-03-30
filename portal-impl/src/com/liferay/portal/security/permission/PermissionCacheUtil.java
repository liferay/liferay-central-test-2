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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="PermissionCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Michael Young
 *
 */
public class PermissionCacheUtil {

	public static final String CACHE_NAME = PermissionCacheUtil.class.getName();

	public static void clearCache() {
		_cache.removeAll();
	}

	public static PermissionCheckerBag getBag(long userId, long groupId) {
		String key = _encodeKey(userId, groupId);

		return (PermissionCheckerBag)MultiVMPoolUtil.get(_cache, key);
	}

	public static Boolean getPermission(
		long userId, long groupId, String name, String primKey,
		String actionId) {

		String key = _encodeKey(userId, groupId, name, primKey, actionId);

		return (Boolean)MultiVMPoolUtil.get(_cache, key);
	}

	public static PermissionCheckerBag putBag(
		long userId, long groupId, PermissionCheckerBag bag) {

		if (bag != null) {
			String key = _encodeKey(userId, groupId);

			MultiVMPoolUtil.put(_cache, key, bag);
		}

		return bag;
	}

	public static Boolean putPermission(
		long userId, long groupId, String name, String primKey, String actionId,
		Boolean value) {

		if (value != null) {
			String key = _encodeKey(userId, groupId, name, primKey, actionId);

			MultiVMPoolUtil.put(_cache, key, value);
		}

		return value;
	}

	private static String _encodeKey(long userId, long groupId) {
		StringBuilder sb = new StringBuilder();

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(userId);
		sb.append(StringPool.POUND);
		sb.append(groupId);

		return sb.toString();
	}

	private static String _encodeKey(
		long userId, long groupId, String name, String primKey,
		String actionId) {

		StringBuilder sb = new StringBuilder();

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(userId);
		sb.append(StringPool.POUND);
		sb.append(groupId);
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(primKey);
		sb.append(StringPool.POUND);
		sb.append(actionId);

		return sb.toString();
	}

	private static PortalCache _cache = MultiVMPoolUtil.getCache(
		CACHE_NAME, true);

}