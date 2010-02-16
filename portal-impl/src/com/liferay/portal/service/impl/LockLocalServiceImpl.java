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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.LockLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="LockLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LockLocalServiceImpl extends LockLocalServiceBaseImpl {

	public void clear() throws SystemException {
		lockPersistence.removeByExpirationDate(new Date());
	}

	public Lock getLock(String className, long key)
		throws PortalException, SystemException {

		return getLock(className, String.valueOf(key));
	}

	public Lock getLock(String className, String key)
		throws PortalException, SystemException {

		Lock lock = lockPersistence.findByC_K(className, key);

		if (lock.isExpired()) {
			unlock(className, key);

			throw new ExpiredLockException();
		}

		return lock;
	}

	public boolean hasLock(long userId, String className, long key)
		throws PortalException, SystemException {

		return hasLock(userId, className, String.valueOf(key));
	}

	public boolean hasLock(long userId, String className, String key)
		throws PortalException, SystemException {

		try {
			Lock lock = getLock(className, key);

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

	public boolean isLocked(String className, long key)
		throws PortalException, SystemException {

		return isLocked(className, String.valueOf(key));
	}

	public boolean isLocked(String className, String key)
		throws PortalException, SystemException {

		try {
			getLock(className, key);

			return true;
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return false;
	}

	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		return lock(
			userId, className, String.valueOf(key), owner, inheritable,
			expirationTime);
	}

	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		Lock lock = lockPersistence.fetchByC_K(className, key);

		if (lock != null) {
			if (lock.isExpired()) {
				unlock(className, key);

				lock = null;
			}
			else if (!lock.getOwner().equals(owner)) {
				throw new DuplicateLockException(lock);
			}
		}

		if (lock == null) {
			User user = userPersistence.findByPrimaryKey(userId);

			long lockId = counterLocalService.increment();

			lock = lockPersistence.create(lockId);

			lock.setCompanyId(user.getCompanyId());
			lock.setUserId(user.getUserId());
			lock.setUserName(user.getFullName());
			lock.setUserId(userId);
			lock.setClassName(className);
			lock.setKey(key);
			lock.setOwner(owner);
			lock.setInheritable(inheritable);
		}

		lock.setCreateDate(now);

		if (expirationTime == 0) {
			lock.setExpirationDate(new Date(expirationTime));
		}
		else {
			lock.setExpirationDate(new Date(now.getTime() + expirationTime));
		}

		lockPersistence.update(lock, false);

		return lock;
	}

	public Lock refresh(String uuid, long expirationTime)
		throws PortalException, SystemException {

		Date now = new Date();

		List<Lock> locks = lockPersistence.findByUuid(uuid);

		Lock lock = null;

		if (locks.size() > 0) {
			lock = locks.get(0);
		}
		else {
			throw new NoSuchLockException();
		}

		lock.setCreateDate(now);

		if (expirationTime == 0) {
			lock.setExpirationDate(new Date(Long.MAX_VALUE));
		}
		else {
			lock.setExpirationDate(new Date(now.getTime() + expirationTime));
		}

		lockPersistence.update(lock, false);

		return lock;
	}

	public void unlock(String className, long key) throws SystemException {
		unlock(className, String.valueOf(key));
	}

	public void unlock(String className, String key) throws SystemException {
		try {
			lockPersistence.removeByC_K(className, key);
		}
		catch (NoSuchLockException nsle) {
		}
	}

}