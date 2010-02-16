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