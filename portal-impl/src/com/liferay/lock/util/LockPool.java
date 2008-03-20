/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.lock.model.impl.LockImpl;
import com.liferay.util.ConcurrentHashSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="LockPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LockPool {

	public static void clear() {
		_instance._clear();
	}

	public static Lock getLock(String className, Comparable<?> pk)
		throws ExpiredLockException, NoSuchLockException {

		return _instance._getLock(className, pk);
	}

	public static Set<Lock> getLocksByCompanyId(long companyId) {
		Set<Lock> locksByCompanyId = _instance._getLocksByCompanyId(companyId);

		Iterator<Lock> itr = locksByCompanyId.iterator();

		while (itr.hasNext()) {
			Lock lock = itr.next();

			if (lock.isExpired()) {
				itr.remove();

				_instance._getLocks(
					lock.getClassName(), lock.getPrimaryKey()).remove(lock);
				_instance._getLocksByUserId(lock.getUserId()).remove(lock);
			}
		}

		return locksByCompanyId;
	}

	public static Set<Lock> getLocksByUserId(long userId) {
		Set<Lock> locksByUserId = _instance._getLocksByUserId(userId);

		Iterator<Lock> itr = locksByUserId.iterator();

		while (itr.hasNext()) {
			Lock lock = itr.next();

			if (lock.isExpired()) {
				itr.remove();

				_instance._getLocks(
					lock.getClassName(), lock.getPrimaryKey()).remove(lock);
				_instance._getLocksByCompanyId(
					lock.getCompanyId()).remove(lock);
			}
		}

		return locksByUserId;
	}

	public static boolean hasLock(
		String className, Comparable<?> pk, long userId) {

		return _instance._hasLock(className, pk, userId);
	}

	public static boolean isLocked(String className, Comparable<?> pk) {
		return _instance._isLocked(className, pk);
	}

	public static void lock(
			String className, Comparable<?> pk, long companyId, long userId,
			long expirationTime)
		throws DuplicateLockException {

		_instance._lock(className, pk, companyId, userId, expirationTime);
	}

	public static void unlock(String className, Comparable<?> pk) {
		_instance._unlock(className, pk);
	}

	private LockPool() {
		_locksByClassName =
			new ConcurrentHashMap<String, Map<Comparable<?>, Lock>>();
		_locksByCompanyId = new ConcurrentHashMap<Long, Set<Lock>>();
		_locksByUserId = new ConcurrentHashMap<Long, Set<Lock>>();
	}

	private void _clear() {
		_locksByClassName.clear();
		_locksByCompanyId.clear();
		_locksByUserId.clear();
	}

	private Lock _getLock(String className, Comparable<?> pk)
		throws ExpiredLockException, NoSuchLockException {

		Map<Comparable<?>, Lock> locksByPK = _getLocks(className, pk);

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

	private Map<Comparable<?>, Lock> _getLocks(
		String className, Comparable<?> pk) {

		Map<Comparable<?>, Lock> locksByPK = _locksByClassName.get(className);

		if (locksByPK == null) {
			locksByPK =	new ConcurrentHashMap<Comparable<?>, Lock>();

			_locksByClassName.put(className, locksByPK);
		}

		return locksByPK;
	}

	private Set<Lock> _getLocksByCompanyId(long companyId) {
		Set<Lock> set = _locksByCompanyId.get(companyId);

		if (set == null) {
			set = new ConcurrentHashSet<Lock>();

			_locksByCompanyId.put(companyId, set);
		}

		return set;
	}

	private Set<Lock> _getLocksByUserId(long userId) {
		Set<Lock> set = _locksByUserId.get(userId);

		if (set == null) {
			set = new ConcurrentHashSet<Lock>();

			_locksByUserId.put(userId, set);
		}

		return set;
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

	private void _lock(
			String className, Comparable<?> pk, long companyId, long userId,
			long expirationTime)
		throws DuplicateLockException {

		Map<Comparable<?>, Lock> locksByPK = _getLocks(className, pk);

		Lock lock = locksByPK.get(pk);

		if (lock != null && lock.isExpired()) {
			_unlock(className, pk);

			lock = null;
		}
		else if ((lock != null) && (lock.getUserId() != userId)) {
			throw new DuplicateLockException(lock);
		}

		if (lock == null) {
			lock = new LockImpl(
				className, pk, companyId, userId, expirationTime);

			locksByPK.put(pk, lock);

			_getLocksByCompanyId(companyId).add(lock);
			_getLocksByUserId(userId).add(lock);
		}
		else {
			lock.setExpirationTime(expirationTime);
		}
	}

	private void _unlock(String className, Comparable<?> pk) {
		Map<Comparable<?>, Lock> locksByPK = _getLocks(className, pk);

		Lock lock = locksByPK.remove(pk);

		if (lock != null) {
			_getLocksByCompanyId(lock.getCompanyId()).remove(lock);
			_getLocksByUserId(lock.getUserId()).remove(lock);
		}
	}

	private static LockPool _instance = new LockPool();

	private Map<String, Map<Comparable<?>, Lock>> _locksByClassName;
	private Map<Long, Set<Lock>> _locksByCompanyId;
	private Map<Long, Set<Lock>> _locksByUserId;

}