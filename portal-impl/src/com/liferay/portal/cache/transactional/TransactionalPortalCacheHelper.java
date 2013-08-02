/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheHelper {

	public static void begin() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		_pushPortalCacheMap();
	}

	public static void commit() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<PortalCache, UncommittedBuffer> portalCacheMap =
			_popPortalCacheMap();

		for (Map.Entry<PortalCache, UncommittedBuffer>
				portalCacheMapEntry : portalCacheMap.entrySet()) {

			PortalCache portalCache = portalCacheMapEntry.getKey();

			UncommittedBuffer uncommittedBuffer =
				portalCacheMapEntry.getValue();

			uncommittedBuffer.commitTo(portalCache);
		}

		portalCacheMap.clear();
	}

	public static boolean isEnabled() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return false;
		}

		List<Map<PortalCache, UncommittedBuffer>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return !portalCacheList.isEmpty();
	}

	public static void rollback() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<PortalCache, UncommittedBuffer> portalCacheMap =
			_popPortalCacheMap();

		portalCacheMap.clear();
	}

	protected static Object get(PortalCache portalCache, Serializable key) {
		Map<PortalCache, UncommittedBuffer> portalCacheMap =
			_peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			return null;
		}

		return uncommittedBuffer.get(key);
	}

	protected static void put(
		PortalCache portalCache, Serializable key, Object value) {

		Map<PortalCache, UncommittedBuffer> portalCacheMap =
			_peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.put(key, value);
	}

	protected static void removeAll(PortalCache portalCache) {
		Map<PortalCache, UncommittedBuffer> portalCacheMap =
			_peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.removeAll();
	}

	private static Map<PortalCache, UncommittedBuffer>
		_peekPortalCacheMap() {

		List<Map<PortalCache, UncommittedBuffer>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return portalCacheList.get(portalCacheList.size() - 1);
	}

	private static Map<PortalCache, UncommittedBuffer>
		_popPortalCacheMap() {

		List<Map<PortalCache, UncommittedBuffer>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return portalCacheList.remove(portalCacheList.size() - 1);
	}

	private static void _pushPortalCacheMap() {
		List<Map<PortalCache, UncommittedBuffer>> portalCacheList =
			_portalCacheListThreadLocal.get();

		portalCacheList.add(new HashMap<PortalCache, UncommittedBuffer>());
	}

	private static ThreadLocal<List<Map<PortalCache, UncommittedBuffer>>>
		_portalCacheListThreadLocal = new InitialThreadLocal
			<List<Map<PortalCache, UncommittedBuffer>>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._portalCacheListThreadLocal",
				new ArrayList<Map<PortalCache, UncommittedBuffer>>());

	private static class UncommittedBuffer {

		public void commitTo(PortalCache portalCache) {
			if (_removeAll) {
				portalCache.removeAll();
			}

			for (Map.Entry<? extends Serializable, ?> entry :
					_uncommittedMap.entrySet()) {

				Serializable key = entry.getKey();
				Object value = entry.getValue();

				if (value == TransactionalPortalCache.NULL_HOLDER) {
					portalCache.remove(key);
				}
				else {
					portalCache.put(entry.getKey(), entry.getValue());
				}
			}
		}

		public Object get(Serializable key) {
			Object value = _uncommittedMap.get(key);

			if ((value == null) && _removeAll) {
				value = TransactionalPortalCache.NULL_HOLDER;
			}

			return value;
		}

		public void put(Serializable key, Object value) {
			_uncommittedMap.put(key, value);
		}

		public void removeAll() {
			_uncommittedMap.clear();

			_removeAll = true;
		}

		private boolean _removeAll;
		private Map<Serializable, Object> _uncommittedMap =
			new HashMap<Serializable, Object>();

	}

}