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

package com.liferay.portal.kernel.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentCharBufferPool {

	public static char[] borrowCharBuffer(int size) {
		if (isEnabled()) {
			_cleanUpDeadBuffers();
			// Build search key bases on the required size
			CharBufferHolder keyBufferHolder = new CharBufferHolder(size);
			int poolSize = -1;
			_modifyLock.lock();
			try {
				int index = Collections.binarySearch(_pooledCharBufferHolders,
					keyBufferHolder);
				if (index < 0) {
					// No buffer with exact required size exist, convert to
					// insert slot or next suitable buffer
					index = -(index + 1);
				}

				poolSize = _pooledCharBufferHolders.size();

				while (index < poolSize) {
					// Next suitable buffer exist
					CharBufferHolder charBufferHolder =
						_pooledCharBufferHolders.get(index);
					if (charBufferHolder._borrowed) {
						// Buffer has been borrowed, move on to next
						index++;
					}
					else {
						// Try to use this buffer
						char[] charBuffer = charBufferHolder.get();
						if (charBuffer != null) {
							// Buffer is valid, mark it as borrowed and record
							// it to borrowed list, return it.
							charBufferHolder._borrowed = true;
							_borrowedCharBufferHoldersThreadLocal.get().add(
								charBufferHolder);
							return charBuffer;
						}
						// This buffer has been GCed, remove it, continue scan.
						_pooledCharBufferHolders.remove(index);
					}
				}
			}
			finally {
				_modifyLock.unlock();
			}
			// No suitable buffer can be retrieved from pool, create a new one.
			// Add about 0.2% delta factor, this can increase reuse ratio
			// significantly with negligible memory overhead.
			char[] charBuffer = new char[size + (size >> 9)];
			if (poolSize < MAX_POOL_SIZE) {
				// Return this buffer only when the pool is not full yet.
				CharBufferHolder charBufferHolder = new CharBufferHolder(
					charBuffer);
				_borrowedCharBufferHoldersThreadLocal.get().add(
					charBufferHolder);
			}
			return charBuffer;
		} else {
			return new char[size];
		}
	}

	public static boolean isEnabled() {
		return _recordBorrowFlag.get();
	}

	public static void recordBorrow() {
		_recordBorrowFlag.set(Boolean.TRUE);
	}

	public static void returnCharBuffers() {
		List<CharBufferHolder> borrowedCharBufferHolders =
			_borrowedCharBufferHoldersThreadLocal.get();
		_modifyLock.lock();
		try {
			for(CharBufferHolder charBufferHolder : borrowedCharBufferHolders) {
				if (charBufferHolder._borrowed) {
					// Return buffers borrowed from pool by unmark them.
					charBufferHolder._borrowed = false;
				}
				else {
					// Return newly created buffers by inserting.
					int index = Collections.binarySearch(
						_pooledCharBufferHolders, charBufferHolder);
					if (index < 0) {
						index = -(index + 1);
					}
					_pooledCharBufferHolders.add(index, charBufferHolder);
				}
			}
		}
		finally {
			_modifyLock.unlock();
		}
		borrowedCharBufferHolders.clear();
		_cleanUpDeadBuffers();
	}

	private static void _cleanUpDeadBuffers() {
		// Peek before acquiring modify lock, this is crucial for concurrency,
		// since SoftReferences will only possibly be freed when full gc or CMS
		// Rescan happens. This means most of the time when a Thread runs here
		// ReferenceQueue is empty, it should return immediately without touch
		// the Lock. But once ReferenceQueue is not empty, most likely more than
		// one SoftReference have been freed by GC, so it is more efficient to
		// hold the Lock outside the while loop than acquiring individually for
		// each dead CharBufferHolder.
		CharBufferHolder charBufferHolder =
			(CharBufferHolder) _referenceQueue.poll();
		if (charBufferHolder != null) {
			_modifyLock.lock();
			try {
				do {
					_pooledCharBufferHolders.remove(charBufferHolder);
				}
				while ((charBufferHolder =
					(CharBufferHolder) _referenceQueue.poll()) != null);
			}
			finally {
				_modifyLock.unlock();
			}
		}
	}

	private static class CharBufferHolder extends SoftReference<char[]>
			implements Comparable<CharBufferHolder> {

		public CharBufferHolder(int length) {
			super(null);
			_length = length;
		}

		public CharBufferHolder(char[] charBuffer) {
			super(charBuffer, _referenceQueue);
			_length = charBuffer.length;
		}

		public int compareTo(CharBufferHolder o) {
			return _length - o._length;
		}

		private final int _length;
		private boolean _borrowed;

	}

	/**
	 * The intial slot number for char buffer, the number is measured from
	 * benchmark. It implies max possible concurrent processing request number.
	 * Set this number high enough to avoid runtime capacity expand.
	 * Making this constant rather than configurable, because this is system's
	 * inherent character, it is not tunable.
	 * The latest measured number from 6.0.x is 43.018, set the number a little
	 * bigger than that to prevent some random peaks.
	 */
	public static final int INITIAL_POOL_SIZE = 50;

	/**
	 * At runtime actual pool size should be around INITIAL_POOL_SIZE, to set a
	 * max pool size here is just to prevent random peaks causing high old gen
	 * usage. This is not a tunable number either.
	 */
	public static final int MAX_POOL_SIZE = INITIAL_POOL_SIZE * 2;

	private static final ThreadLocal<List<CharBufferHolder>>
		_borrowedCharBufferHoldersThreadLocal =
			new AutoResetThreadLocal<List<CharBufferHolder>>(
				ConcurrentCharBufferPool.class.getName()
					+ "._borrowedCharBufferHoldersThreadLocal",
				new ArrayList<CharBufferHolder>());

	private static final Lock _modifyLock = new ReentrantLock();

	private static List<CharBufferHolder> _pooledCharBufferHolders =
		new ArrayList<CharBufferHolder>(INITIAL_POOL_SIZE);

	private static ThreadLocal<Boolean> _recordBorrowFlag =
		new AutoResetThreadLocal<Boolean>(
			ConcurrentCharBufferPool.class.getName() + "._recordFlag", false);

	private static ReferenceQueue _referenceQueue = new ReferenceQueue();

}