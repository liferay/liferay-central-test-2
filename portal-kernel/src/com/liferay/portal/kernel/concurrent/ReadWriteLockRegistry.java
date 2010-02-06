/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <a href="ReadWriteLockRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * Registry for {@link ReadWriteLock} objects with {@link ReadWriteLockKey} as
 * keys. The behavior of acquiring and releasing locks is provided by a {@link
 * ConcurrentHashMap}. This class is completely thread safe and ensures that
 * only one {@link ReadWriteLock} exists per key. <a
 * href="ReadWriteLockRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @see	   ReadWriteLock
 * @see	   ReadWriteLockKey
 */
public class ReadWriteLockRegistry {

	public Lock acquireLock(ReadWriteLockKey<?> readWriteLockKey) {
		ReadWriteLock readWriteLock = _readWriteLockMap.get(readWriteLockKey);

		if (readWriteLock == null) {
			ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock();

			readWriteLock = _readWriteLockMap.putIfAbsent(
				readWriteLockKey, newReadWriteLock);

			if (readWriteLock == null) {
				readWriteLock = newReadWriteLock;
			}
		}

		if (readWriteLockKey.isWriteLock()) {
			return readWriteLock.writeLock();
		}
		else {
			return readWriteLock.readLock();
		}
	}

	public void releaseLock(ReadWriteLockKey<?> readWriteLockKey) {
		if (readWriteLockKey.isWriteLock()) {
			_readWriteLockMap.remove(readWriteLockKey);
		}
	}

	private ConcurrentMap<ReadWriteLockKey<?>, ReadWriteLock>
		_readWriteLockMap = new ConcurrentHashMap
			<ReadWriteLockKey<?>, ReadWriteLock>();

}