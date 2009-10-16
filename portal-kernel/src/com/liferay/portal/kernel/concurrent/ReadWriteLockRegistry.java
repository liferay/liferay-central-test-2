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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is map-like ReadWriteLock registry.
 *
 * When acquiring by a ReadWriteLockKey for the first time, the registry will
 * atomicly creating a ReadWriteLock, save it in a map, then return the
 * read/write lock according to ReadWriteLockKey.isWriteLock().
 *
 * So later, when acquiring by the same ReadWriteLockKey(defined by
 * ReadWriteLockKey.equals()), the registry will return the read/write lock from
 * the ReadWriteLock object created during last acquiring.
 *
 * When releasing a lock, depends on the lock type(according to
 * ReadWriteLockKey.isWriteLock()), the behavior is different. If it is a read
 * lock, the registry won't change its state, but for a write lock, the registry
 * will remove the ReadWriteLock.
 *
 * For the acquired Lock object, the caller is responsible to lock/unlock it,
 * the caller is also responsible to call the acquireLock()/releaseLock() in the
 * try-finally block to clean up the overdue ReadWriteLock, otherwise it may
 * cause memory leak.
 *
 * <a href="ReadWriteLockRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ReadWriteLockRegistry {

	public Lock acquireLock(ReadWriteLockKey<?> readWriteLockKey) {
		//fast path
		ReadWriteLock readWriteLock = _lockRegistry.get(readWriteLockKey);

		if (readWriteLock == null) {
			//slow path
			ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock();
			//atomic put, the worst case, the newReadWriteLock will become
			//garbage
			readWriteLock =
				_lockRegistry.putIfAbsent(readWriteLockKey, newReadWriteLock);

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
			_lockRegistry.remove(readWriteLockKey);
		}
	}

	private ConcurrentMap<ReadWriteLockKey<?>, ReadWriteLock> _lockRegistry =
		new ConcurrentHashMap<ReadWriteLockKey<?>, ReadWriteLock>();

}