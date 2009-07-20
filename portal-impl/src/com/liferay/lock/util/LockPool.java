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

package com.liferay.lock.util;

import com.liferay.lock.DuplicateLockException;
import com.liferay.lock.ExpiredLockException;
import com.liferay.lock.NoSuchLockException;
import com.liferay.lock.model.Lock;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockPool {

	public static void clear() {
		_instance._clear();
	}

	public static Lock getLock(String className, Comparable<?> pk)
		throws ExpiredLockException, NoSuchLockException {

		return _instance._getLock(className, pk);
	}

	public static boolean hasLock(
		String className, Comparable<?> pk, long userId) {

		return _instance._hasLock(className, pk, userId);
	}

	public static boolean isLocked(String className, Comparable<?> pk) {
		return _instance._isLocked(className, pk);
	}

	public static Lock lock(
			String className, Comparable<?> pk, long userId, String owner,
			boolean inheritable, long expirationTime)
		throws DuplicateLockException {

		return _instance._lock(
			className, pk, userId, owner, inheritable, expirationTime);
	}

	public static Lock refresh(String uuid, long expirationTime)
		throws NoSuchLockException {

		return _instance._refresh(uuid, expirationTime);
	}

	public static void unlock(String className, Comparable<?> pk) {
		_instance._unlock(className, pk);
	}

	private LockPool() {
		_locksByClassName =
			new ConcurrentHashMap<String, Map<Comparable<?>, Lock>>();
		_lockByUuid = new ConcurrentHashMap<String, Lock>();
	}

	private void _clear() {
		_locksByClassName.clear();
		_lockByUuid.clear();
	}

	private Lock _getLock(String className, Comparable<?> pk)
		throws ExpiredLockException, NoSuchLockException {

		Map<Comparable<?>, Lock> locksByPK = _getLocks(className);

		Lock lock = locksByPK.get(pk);

		if (lock == null) {
			throw new NoSuchLockException();
		}
		else if (lock.isExpired()) {
			_unlock(className, pk);

			throw new ExpiredLockException();
		}

		return lock;
	}

	private Map<Comparable<?>, Lock> _getLocks(String className) {
		Map<Comparable<?>, Lock> locksByPK = _locksByClassName.get(className);

		if (locksByPK == null) {
			locksByPK =	new ConcurrentHashMap<Comparable<?>, Lock>();

			_locksByClassName.put(className, locksByPK);
		}

		return locksByPK;
	}

	private boolean _hasLock(String className, Comparable<?> pk, long userId) {
		try {
			Lock lock = _getLock(className, pk);

			if (lock.getUserId() == userId) {
				return true;
			}
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return false;
	}

	private boolean _isLocked(String className, Comparable<?> pk) {
		try {
			_getLock(className, pk);

			return true;
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return false;
	}

	private Lock _lock(
			String className, Comparable<?> pk, long userId, String owner,
			boolean inheritable, long expirationTime)
		throws DuplicateLockException {

		Map<Comparable<?>, Lock> locksByPK = _getLocks(className);

		Lock lock = locksByPK.get(pk);

		if (lock != null) {
			if (lock.isExpired()) {
				_unlock(className, pk);

				lock = null;
			}
			else if (!lock.getOwner().equals(owner)) {
				throw new DuplicateLockException(lock);
			}
		}

		if (lock == null) {
			String uuid = PortalUUIDUtil.generate();

			lock = new Lock(
				uuid, className, pk, userId, owner, inheritable,
				expirationTime);

			locksByPK.put(pk, lock);

			_lockByUuid.put(uuid, lock);
		}
		else {
			lock.setExpirationTime(expirationTime);
		}

		return lock;
	}

	private Lock _refresh(String uuid, long expirationTime)
		throws NoSuchLockException {

		Lock lock = _lockByUuid.get(uuid);

		if (lock != null) {
			lock.setExpirationTime(expirationTime);

			return lock;
		}

		throw new NoSuchLockException();
	}

	private void _unlock(String className, Comparable<?> pk) {
		Map<Comparable<?>, Lock> locksByPK = _getLocks(className);

		Lock lock = locksByPK.remove(pk);

		if (lock != null) {
			_lockByUuid.remove(lock.getUuid());
		}
	}

	private static LockPool _instance = new LockPool();

	private Map<String, Map<Comparable<?>, Lock>> _locksByClassName;
	private Map<String, Lock> _lockByUuid;

}