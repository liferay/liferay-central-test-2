/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author Charles May
 * @author Michael Young
 */
public class PermissionCacheUtil {

	public static final String CACHE_NAME = PermissionCacheUtil.class.getName();

	public static void clearCache() {
		clearLocalCache();

		_portalCache.removeAll();
	}

	public static void clearLocalCache() {
		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public static PermissionCheckerBag getBag(long userId, long groupId) {
		PermissionCheckerBag bag = null;

		String key = _encodeKey(userId, groupId);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			bag = (PermissionCheckerBag)localCache.get(key);
		}

		if (bag == null) {
			bag = (PermissionCheckerBag)_portalCache.get(key);
		}

		return bag;
	}

	public static Boolean getPermission(
		long userId, long groupId, String name, String primKey,
		String actionId) {

		Boolean value = null;

		String key = _encodeKey(userId, groupId, name, primKey, actionId);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			value = (Boolean)localCache.get(key);
		}

		if (value == null) {
			value = (Boolean)_portalCache.get(key);
		}

		return value;
	}

	public static PermissionCheckerBag putBag(
		long userId, long groupId, PermissionCheckerBag bag) {

		if (bag != null) {
			String key = _encodeKey(userId, groupId);

			if (_localCacheAvailable) {
				Map<String, Object> localCache = _localCache.get();

				localCache.put(key, bag);
			}

			_portalCache.put(key, bag);
		}

		return bag;
	}

	public static Boolean putPermission(
		long userId, long groupId, String name, String primKey, String actionId,
		Boolean value) {

		if (value != null) {
			String key = _encodeKey(userId, groupId, name, primKey, actionId);

			if (_localCacheAvailable) {
				Map<String, Object> localCache = _localCache.get();

				localCache.put(key, value);
			}

			_portalCache.put(key, value);
		}

		return value;
	}

	private static String _encodeKey(long userId, long groupId) {
		StringBundler sb = new StringBundler(5);

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

		StringBundler sb = new StringBundler(11);

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

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;
	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME, PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);

	static {
		if (PropsValues.PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new AutoResetThreadLocal<LRUMap>(
				PermissionCacheUtil.class + "._localCache",
				new LRUMap(
					PropsValues.PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

}