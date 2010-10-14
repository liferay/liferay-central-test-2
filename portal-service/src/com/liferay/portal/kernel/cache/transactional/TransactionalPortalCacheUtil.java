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

package com.liferay.portal.kernel.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheUtil {

	public static void begin() {
		if (!_transactionalCacheEnabled) {
			return;
		}

		pushCache();

		int transactionLayerCount = _transactionLayerCountThreadLocal.get() + 1;
		_transactionLayerCountThreadLocal.set(transactionLayerCount);
	}

	public static void commit() {
		if (!_transactionalCacheEnabled) {
			return;
		}

		Map<PortalCache, Map<String, Object>> cache = popCache();

		for(Map.Entry<PortalCache, Map<String, Object>> entry
			: cache.entrySet()) {
			PortalCache portalCache = entry.getKey();
			Map<String, Object> tempCache = entry.getValue();
			for(Map.Entry<String, Object> tempEntry : tempCache.entrySet()) {
				portalCache.put(tempEntry.getKey(), tempEntry.getValue());
			}
		}

		int transactionLayerCount = _transactionLayerCountThreadLocal.get() - 1;
		_transactionLayerCountThreadLocal.set(transactionLayerCount);
	}

	public static boolean enabled() {
		if (_transactionalCacheEnabled &&
			_transactionLayerCountThreadLocal.get() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void rollback() {
		if (!_transactionalCacheEnabled) {
			return;
		}

		Map<PortalCache, Map<String, Object>> cache = popCache();
		cache.clear();

		int transactionLayerCount = _transactionLayerCountThreadLocal.get() - 1;
		_transactionLayerCountThreadLocal.set(transactionLayerCount);
	}

	protected static Map<PortalCache, Map<String, Object>> peekCache() {
		LinkedList<Map<PortalCache, Map<String, Object>>>
			transactionalPortalCacheStack =
				_transactionalPortalCacheStackThreadLocal.get();
		return transactionalPortalCacheStack.getLast();
	}

	protected static Map<PortalCache, Map<String, Object>> popCache() {
		LinkedList<Map<PortalCache, Map<String, Object>>>
			transactionalPortalCacheStack =
				_transactionalPortalCacheStackThreadLocal.get();
		return transactionalPortalCacheStack.removeLast();
	}

	protected static void pushCache() {
		LinkedList<Map<PortalCache, Map<String, Object>>>
			transactionalPortalCacheStack =
				_transactionalPortalCacheStackThreadLocal.get();
		transactionalPortalCacheStack.addLast(
			new HashMap<PortalCache, Map<String, Object>>());
	}

	protected static Object get(PortalCache portalCache, String key) {
		Map<PortalCache, Map<String, Object>> cache = peekCache();
		Map<String, Object> tempCache = cache.get(portalCache);
		if (tempCache == null) {
			return null;
		}
		return tempCache.get(key);
	}

	protected static void put(
		PortalCache portalCache, String key, Object value) {
		Map<PortalCache, Map<String, Object>> cache = peekCache();
		Map<String, Object> tempCache = cache.get(portalCache);
		if (tempCache == null) {
			tempCache = new HashMap<String, Object>();
			cache.put(portalCache, tempCache);
		}
		tempCache.put(key, value);
	}

	protected static void remove(PortalCache portalCache, String key) {
		Map<PortalCache, Map<String, Object>> cache = peekCache();
		Map<String, Object> tempCache = cache.get(portalCache);
		if (tempCache != null) {
			tempCache.remove(key);
		}
	}

	protected static void removeAll(PortalCache portalCache) {
		Map<PortalCache, Map<String, Object>> cache = peekCache();
		Map<String, Object> tempCache = cache.get(portalCache);
		if (tempCache != null) {
			tempCache.clear();
		}
	}

	private static ThreadLocal<Integer> _transactionLayerCountThreadLocal =
		new InitialThreadLocal<Integer>(
			TransactionalPortalCacheUtil.class.getName() +
				"._transactionLayerCountThreadLocal", 0);

	private static boolean _transactionalCacheEnabled =	GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED));

	private static ThreadLocal<LinkedList<Map<PortalCache, Map<String, Object>>>>
		_transactionalPortalCacheStackThreadLocal =
			new InitialThreadLocal<LinkedList<Map<PortalCache, Map<String, Object>>>>(
				TransactionalPortalCacheUtil.class.getName() +
					"._transactionalPortalCacheStackThreadLocal",
				new LinkedList<Map<PortalCache, Map<String, Object>>>());

}