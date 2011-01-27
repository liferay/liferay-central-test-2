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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCache<K, V> {

	public ConcurrentLRUCache(int maxCacheSize) {
		this(maxCacheSize, 0.75F);
	}

	public ConcurrentLRUCache(int maxCacheSize, float expectLoadFactor) {
		if ((maxCacheSize <= 0) || (expectLoadFactor <= 0F) ||
			(expectLoadFactor >= 1)) {
			throw new IllegalArgumentException();
		}

		_maxSize = maxCacheSize;
		_expectSize = (int) (maxCacheSize * expectLoadFactor);
		if (_expectSize == 0) {
			throw new IllegalArgumentException(
				"maxCacheSize and expectLoadFactor are too small, the result "
				+ "expectSize is 0");
		}

		_readLock = _readWriteLock.readLock();
		_writeLock = _readWriteLock.writeLock();
	}

	public long evictCount() {
		return _evictCount.get();
	}

	public V get(K key) {
		_readLock.lock();
		try {
			ValueWrapper<V> valueWrapper = _cache.get(key);
			if (valueWrapper != null) {
				valueWrapper._hitCount.getAndIncrement();
				_hitCount.getAndIncrement();
				return valueWrapper._value;
			}
		}
		finally {
			_readLock.unlock();
		}

		_missCount.getAndIncrement();

		return null;
	}

	public int expectSize() {
		return _expectSize;
	}

	public long hitCount() {
		return _hitCount.get();
	}

	public int maxSize() {
		return _maxSize;
	}

	public long missCount() {
		return _missCount.get();
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		ValueWrapper<V> valueWrapper = new ValueWrapper<V>(value);

		_writeLock.lock();
		try {
			if (!_cache.containsKey(key) && (_cache.size() >= _maxSize)) {
				cleanUp();
			}

			_cache.put(key, valueWrapper);
		}
		finally {
			_writeLock.unlock();
		}

		_putCount.getAndIncrement();
	}

	public long putCount() {
		return _putCount.get();
	}

	public int size() {
		_readLock.lock();
		try {
			return _cache.size();
		}
		finally {
			_readLock.unlock();
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{evictCount=");
		sb.append(_evictCount.get());
		sb.append(", expectSize=");
		sb.append(_expectSize);
		sb.append(", hitCount=");
		sb.append(_hitCount.get());
		sb.append(", maxSize=");
		sb.append(_maxSize);
		sb.append(", missCount=");
		sb.append(_missCount.get());
		sb.append(", putCount=");
		sb.append(_putCount.get());
		sb.append(", size=");
		sb.append(size());
		sb.append("}");

		return sb.toString();
	}

	private void cleanUp() {
		List<Entry<K, ValueWrapper<V>>> entryList =
			new ArrayList<Entry<K, ValueWrapper<V>>>(_cache.entrySet());
		Collections.sort(entryList, _entryComparator);

		int freeUpCount = _cache.size() - _expectSize;
		_evictCount.getAndAdd(freeUpCount);
		Iterator<Entry<K, ValueWrapper<V>>> sortedIterator =
			entryList.iterator();
		while (freeUpCount-- > 0 && sortedIterator.hasNext()) {
			Entry<K, ValueWrapper<V>> entry = sortedIterator.next();
			_cache.remove(entry.getKey());
			sortedIterator.remove();
		}
	}

	private Map<K, ValueWrapper<V>> _cache = new HashMap<K, ValueWrapper<V>>();
	private final EntryComparator _entryComparator = new EntryComparator();
	private final AtomicLong _evictCount = new AtomicLong();
	private final int _expectSize;
	private final AtomicLong _hitCount = new AtomicLong();
	private final int _maxSize;
	private final AtomicLong _missCount = new AtomicLong();
	private final AtomicLong _putCount = new AtomicLong();
	private final Lock _readLock;
	private final ReentrantReadWriteLock _readWriteLock =
		new ReentrantReadWriteLock();
	private final Lock _writeLock;

	private class EntryComparator
		implements Comparator<Entry<K, ValueWrapper<V>>> {

		public int compare(
			Entry<K, ValueWrapper<V>> entry1,
			Entry<K, ValueWrapper<V>> entry2) {
			long hitCount1 = entry1.getValue()._hitCount.get();
			long hitCount2 = entry2.getValue()._hitCount.get();

			return (int) (hitCount1 - hitCount2);
		}
	}

	private class ValueWrapper<V> {

		public ValueWrapper(V v) {
			_value = v;
		}
		private final AtomicLong _hitCount = new AtomicLong();
		private final V _value;
	}

}