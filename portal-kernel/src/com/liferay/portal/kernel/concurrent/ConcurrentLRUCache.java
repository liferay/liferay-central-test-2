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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <a href="ConcurrentLRUCache.java.html"><b><i>View Source</i></b></a>
 *
 * Link based thread-safe in-memory LRU Cache. Suitable for high concurrent,
 * but small data size cache.
 *
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCache<K, V> {

	public ConcurrentLRUCache(int maxSize) {
		if (maxSize <= 0) {
			throw new IllegalArgumentException(
				"Maxsize has to be bigger than 0");
		}
		_maxSize = maxSize;
		_headEntry = new Entry<K, V>(null, null);
		_lastEntry = new Entry<K, V>(null, null);
		_headEntry._nextEntry = _lastEntry;
		_lastEntry._previousEntry = _headEntry;
	}

	public long evictCount() {
		return _evictCount.get();
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

	public long putCount() {
		return _putCount.get();
	}

	public int size() {
		return _size.get();
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("key is null");
		}

		_putCount.getAndIncrement();
		_writeLock.lock();
		try {
			// Try to overwrite exist value
			Entry<K, V> currentEntry = _lastEntry._previousEntry;
			while (currentEntry != _headEntry) {
				if (currentEntry._key.equals(key)) {
					currentEntry._value = value;
					if (currentEntry._nextEntry != _lastEntry) {
						detachEntry(currentEntry);
						insertEntryBefore(_lastEntry, currentEntry);
					}
					return;
				}
				currentEntry = currentEntry._previousEntry;
			}

			// Clean up room
			while (_size.get() >= _maxSize) {
				removeHeadEntry();
			}

			// Add new entry
			insertEntryBefore(_lastEntry, new Entry<K, V>(key, value));
			_size.getAndIncrement();
		}
		finally {
			_writeLock.unlock();
		}
	}

	public V get(K key) {
		Entry<K, V> matchEntry = null;
		boolean needMove = false;
		_readLock.lock();
		try {
			matchEntry = _lastEntry._previousEntry;
			while (matchEntry != _headEntry) {
				if (matchEntry._key.equals(key)) {
					if (matchEntry._nextEntry != _lastEntry) {
						// Mark to move matchEntry to last after release
						// read lock
						needMove = true;
					}
					_hitCount.getAndIncrement();
					return matchEntry._value;
				}
				matchEntry = matchEntry._previousEntry;
			}
		}
		finally {
			_readLock.unlock();
			if (needMove) {
				// There is a window time here, other thread may evict
				// matchEntry before current thread lock up the write lock,
				// so before move matchEntry to the last, have to make sure it
				// has not been evicted.
				_writeLock.lock();
				try {
					// Evicted entry should have a null key
					if (matchEntry._key != null) {
						detachEntry(matchEntry);
						insertEntryBefore(_lastEntry, matchEntry);
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

	public String toString() {
		StringBundler sb = new StringBundler();
		sb.append("ConcurrentLRUCache: MaxSize(");
		sb.append(_maxSize);
		sb.append("), Size(");
		sb.append(_size.toString());
		sb.append("), Hit(");
		sb.append(_hitCount.toString());
		sb.append("), Miss(");
		sb.append(_missCount.toString());
		sb.append("), Put(");
		sb.append(_putCount.toString());
		sb.append("), Evict(");
		sb.append(_evictCount.toString());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Must access with write lock
	 */
	private void detachEntry(Entry<K, V> entry) {
		entry._previousEntry._nextEntry = entry._nextEntry;
		entry._nextEntry._previousEntry = entry._previousEntry;
		entry._nextEntry = entry._previousEntry = null;
	}

	/**
	 * Must access with write lock
	 */
	private void insertEntryBefore(
		Entry<K, V> referenceEntry, Entry<K, V> insertEntry) {
		insertEntry._previousEntry = referenceEntry._previousEntry;
		insertEntry._nextEntry = referenceEntry;

		referenceEntry._previousEntry._nextEntry = insertEntry;
		referenceEntry._previousEntry = insertEntry;
	}

	/**
	 * Must access with write lock
	 */
	private void removeHeadEntry() {
		Entry<K, V> garbageEntry = _headEntry._nextEntry;
		detachEntry(garbageEntry);
		// Help GC
		garbageEntry._key = null;
		garbageEntry._value = null;
		_size.getAndDecrement();
		_evictCount.getAndIncrement();
	}

	private static class Entry<K, V> {

		public Entry(K key, V value) {
			_key = key;
			_value = value;
		}

		private K _key;
		private V _value;
		private Entry<K, V> _previousEntry;
		private Entry<K, V> _nextEntry;
	}

	private final int _maxSize;
	private final AtomicLong _evictCount = new AtomicLong(0);
	private final AtomicLong _hitCount = new AtomicLong(0);
	private final AtomicLong _missCount = new AtomicLong(0);
	private final AtomicLong _putCount = new AtomicLong(0);
	private final AtomicInteger _size = new AtomicInteger(0);
	private final Entry<K, V> _headEntry;
	private final Entry<K, V> _lastEntry;
	private final ReentrantReadWriteLock _readWriteLock =
		new ReentrantReadWriteLock();
	private final Lock _readLock = _readWriteLock.readLock();
	private final Lock _writeLock = _readWriteLock.writeLock();

}