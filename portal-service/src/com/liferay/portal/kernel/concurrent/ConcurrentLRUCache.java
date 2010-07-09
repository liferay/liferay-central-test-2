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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCache<K, V> {

	public ConcurrentLRUCache(int maxSize) {
		_maxSize = maxSize;

		_readLock = _readWriteLock.readLock();
		_writeLock = _readWriteLock.writeLock();

		_headEntry._nextEntry = _lastEntry;
		_lastEntry._previousEntry = _headEntry;
	}

	public long evictCount() {
		return _evictCount.get();
	}

	public V get(K key) {
		Entry<K, V> matchEntry = null;

		boolean requiresMove = false;

		_readLock.lock();

		try {
			matchEntry = _lastEntry._previousEntry;

			while (matchEntry != _headEntry) {
				if (matchEntry._key.equals(key)) {
					if (matchEntry._nextEntry != _lastEntry) {
						requiresMove = true;
					}

					_hitCount.getAndIncrement();

					return matchEntry._value;
				}

				matchEntry = matchEntry._previousEntry;
			}
		}
		finally {
			_readLock.unlock();

			if (requiresMove) {
				_writeLock.lock();

				try {
					if (matchEntry._key != null) {
						_detachEntry(matchEntry);

						_insertEntryBefore(_lastEntry, matchEntry);
					}
				}
				finally {
					_writeLock.unlock();
				}
			}
		}

		_missCount.getAndIncrement();

		return null;
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

		_putCount.getAndIncrement();
		_writeLock.lock();

		try {
			Entry<K, V> currentEntry = _lastEntry._previousEntry;

			while (currentEntry != _headEntry) {
				if (currentEntry._key.equals(key)) {
					currentEntry._value = value;

					if (currentEntry._nextEntry != _lastEntry) {
						_detachEntry(currentEntry);
						_insertEntryBefore(_lastEntry, currentEntry);
					}

					return;
				}

				currentEntry = currentEntry._previousEntry;
			}

			while (_size.get() >= _maxSize) {
				_removeHeadEntry();
			}

			_insertEntryBefore(_lastEntry, new Entry<K, V>(key, value));

			_size.getAndIncrement();
		}
		finally {
			_writeLock.unlock();
		}
	}

	public long putCount() {
		return _putCount.get();
	}

	public int size() {
		return _size.get();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{evictCount=");
		sb.append(_evictCount);
		sb.append(", hitCount=");
		sb.append(_hitCount);
		sb.append(", maxSize=");
		sb.append(_maxSize);
		sb.append(", missCount=");
		sb.append(_missCount);
		sb.append(", putCount=");
		sb.append(_putCount);
		sb.append(", size=");
		sb.append(_size);
		sb.append("}");

		return sb.toString();
	}

	private void _detachEntry(Entry<K, V> entry) {
		entry._previousEntry._nextEntry = entry._nextEntry;
		entry._nextEntry._previousEntry = entry._previousEntry;
		entry._nextEntry = entry._previousEntry = null;
	}

	private void _insertEntryBefore(
		Entry<K, V> referenceEntry, Entry<K, V> insertEntry) {

		insertEntry._previousEntry = referenceEntry._previousEntry;
		insertEntry._nextEntry = referenceEntry;

		referenceEntry._previousEntry._nextEntry = insertEntry;
		referenceEntry._previousEntry = insertEntry;
	}

	private void _removeHeadEntry() {
		Entry<K, V> entry = _headEntry._nextEntry;

		_detachEntry(entry);

		entry._key = null;
		entry._value = null;

		_size.getAndDecrement();
		_evictCount.getAndIncrement();
	}

	private final AtomicLong _evictCount = new AtomicLong(0);
	private final Entry<K, V> _headEntry = new Entry<K, V>(null, null);
	private final AtomicLong _hitCount = new AtomicLong(0);
	private final Entry<K, V> _lastEntry = new Entry<K, V>(null, null);
	private final int _maxSize;
	private final AtomicLong _missCount = new AtomicLong(0);
	private final AtomicLong _putCount = new AtomicLong(0);
	private final Lock _readLock;
	private final ReentrantReadWriteLock _readWriteLock =
		new ReentrantReadWriteLock();
	private final AtomicInteger _size = new AtomicInteger(0);
	private final Lock _writeLock;

	private static class Entry<K, V> {

		public Entry(K key, V value) {
			_key = key;
			_value = value;
		}

		private K _key;
		private Entry<K, V> _nextEntry;
		private Entry<K, V> _previousEntry;
		private V _value;

	}

}