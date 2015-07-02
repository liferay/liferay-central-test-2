/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache.index;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheIndexer<I, K extends Serializable, V> {

	public PortalCacheIndexer(
		IndexAccessor<I, K> indexAccessor, PortalCache<K, V> portalCache) {

		_indexAccessor = indexAccessor;

		_portalCache = portalCache;

		_portalCache.registerCacheListener(new IndexerCacheListener());

		for (K indexedCacheKey : _portalCache.getKeys()) {
			_addIndexedCacheKey(indexedCacheKey);
		}
	}

	public Set<K> getIndexedCacheKeys(I index) {
		Set<K> indexedCacheKeys = _indexedCacheKeys.get(index);

		if (indexedCacheKeys == null) {
			return Collections.emptySet();
		}

		return new HashSet<>(indexedCacheKeys);
	}

	public void removeIndexedCacheKeys(I index) {
		Set<K> indexedCacheKeys = _indexedCacheKeys.remove(index);

		if (indexedCacheKeys == null) {
			return;
		}

		for (K indexedCacheKey : indexedCacheKeys) {
			_portalCache.remove(indexedCacheKey);
		}
	}

	private void _addIndexedCacheKey(K indexedCacheKey) {
		I index = _indexAccessor.getIndex(indexedCacheKey);

		Set<K> indexedCacheKeys = _indexedCacheKeys.get(index);

		if (indexedCacheKeys == null) {
			Set<K> newIndexedCacheKeys = new ConcurrentHashSet<>();

			newIndexedCacheKeys.add(indexedCacheKey);

			indexedCacheKeys = _indexedCacheKeys.putIfAbsent(
				index, newIndexedCacheKeys);

			if (indexedCacheKeys == null) {
				return;
			}
		}

		indexedCacheKeys.add(indexedCacheKey);
	}

	private void _removeIndexedCacheKey(K indexedCacheKey) {
		I index = _indexAccessor.getIndex(indexedCacheKey);

		Set<K> indexedCacheKeys = _indexedCacheKeys.get(index);

		if (indexedCacheKeys == null) {
			return;
		}

		indexedCacheKeys.remove(indexedCacheKey);

		if (indexedCacheKeys.isEmpty() &&
			_indexedCacheKeys.remove(index, indexedCacheKeys)) {

			for (K victimIndexedCacheKey : indexedCacheKeys) {
				_addIndexedCacheKey(victimIndexedCacheKey);
			}
		}
	}

	private final IndexAccessor<I, K> _indexAccessor;
	private final ConcurrentMap<I, Set<K>> _indexedCacheKeys =
		new ConcurrentHashMap<>();
	private final PortalCache<K, V> _portalCache;

	private class IndexerCacheListener implements CacheListener<K, V> {

		@Override
		public void dispose() {
			_indexedCacheKeys.clear();
		}

		@Override
		public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryPut(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_addIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {

			_removeIndexedCacheKey(indexedCacheKey);
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K indexedCacheKey, V value,
			int timeToLive) {
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache) {
			_indexedCacheKeys.clear();
		}

	}

}