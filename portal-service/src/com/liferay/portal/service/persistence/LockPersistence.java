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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Lock;

/**
 * <a href="LockPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockPersistenceImpl
 * @see       LockUtil
 * @generated
 */
public interface LockPersistence extends BasePersistence<Lock> {
	public void cacheResult(com.liferay.portal.model.Lock lock);

	public void cacheResult(java.util.List<com.liferay.portal.model.Lock> locks);

	public com.liferay.portal.model.Lock create(long lockId);

	public com.liferay.portal.model.Lock remove(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock updateImpl(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByPrimaryKey(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock fetchByPrimaryKey(long lockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock[] findByUuid_PrevAndNext(long lockId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock[] findByExpirationDate_PrevAndNext(
		long lockId, java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock findByC_K(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Lock> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_K(java.lang.String className, java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_K(java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}