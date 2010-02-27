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

import com.liferay.portal.model.UserTracker;

/**
 * <a href="UserTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPersistenceImpl
 * @see       UserTrackerUtil
 * @generated
 */
public interface UserTrackerPersistence extends BasePersistence<UserTracker> {
	public void cacheResult(com.liferay.portal.model.UserTracker userTracker);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserTracker> userTrackers);

	public com.liferay.portal.model.UserTracker create(long userTrackerId);

	public com.liferay.portal.model.UserTracker remove(long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker updateImpl(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findByPrimaryKey(
		long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker fetchByPrimaryKey(
		long userTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker[] findByCompanyId_PrevAndNext(
		long userTrackerId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker[] findByUserId_PrevAndNext(
		long userTrackerId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findBySessionId_First(
		java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker findBySessionId_Last(
		java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserTracker[] findBySessionId_PrevAndNext(
		long userTrackerId, java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserTracker> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeBySessionId(java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countBySessionId(java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}