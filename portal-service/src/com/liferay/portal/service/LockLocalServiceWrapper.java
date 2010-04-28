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

package com.liferay.portal.service;


/**
 * <a href="LockLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LockLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockLocalService
 * @generated
 */
public class LockLocalServiceWrapper implements LockLocalService {
	public LockLocalServiceWrapper(LockLocalService lockLocalService) {
		_lockLocalService = lockLocalService;
	}

	public com.liferay.portal.model.Lock addLock(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.addLock(lock);
	}

	public com.liferay.portal.model.Lock createLock(long lockId) {
		return _lockLocalService.createLock(lockId);
	}

	public void deleteLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_lockLocalService.deleteLock(lockId);
	}

	public void deleteLock(com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		_lockLocalService.deleteLock(lock);
	}

	public java.util.List<com.liferay.portal.model.Lock> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.Lock> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.Lock> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Lock getLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.getLock(lockId);
	}

	public java.util.List<com.liferay.portal.model.Lock> getLocks(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.getLocks(start, end);
	}

	public int getLocksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.getLocksCount();
	}

	public com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.updateLock(lock);
	}

	public com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.updateLock(lock, merge);
	}

	public void clear()
		throws com.liferay.portal.kernel.exception.SystemException {
		_lockLocalService.clear();
	}

	public com.liferay.portal.model.Lock getLock(java.lang.String className,
		long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.getLock(className, key);
	}

	public com.liferay.portal.model.Lock getLock(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.getLock(className, key);
	}

	public boolean hasLock(long userId, java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.hasLock(userId, className, key);
	}

	public boolean hasLock(long userId, java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.hasLock(userId, className, key);
	}

	public boolean isLocked(java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.isLocked(className, key);
	}

	public boolean isLocked(java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.isLocked(className, key);
	}

	public com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, long key, java.lang.String owner,
		boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.lock(userId, className, key, owner,
			inheritable, expirationTime);
	}

	public com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.lock(userId, className, key, owner,
			inheritable, expirationTime);
	}

	public com.liferay.portal.model.Lock refresh(java.lang.String uuid,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _lockLocalService.refresh(uuid, expirationTime);
	}

	public void unlock(java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.SystemException {
		_lockLocalService.unlock(className, key);
	}

	public void unlock(java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		_lockLocalService.unlock(className, key);
	}

	public LockLocalService getWrappedLockLocalService() {
		return _lockLocalService;
	}

	private LockLocalService _lockLocalService;
}