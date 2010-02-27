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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserTracker;

import java.util.List;

/**
 * <a href="UserTrackerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPersistence
 * @see       UserTrackerPersistenceImpl
 * @generated
 */
public class UserTrackerUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static UserTracker remove(UserTracker userTracker)
		throws SystemException {
		return getPersistence().remove(userTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserTracker update(UserTracker userTracker, boolean merge)
		throws SystemException {
		return getPersistence().update(userTracker, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.UserTracker userTracker) {
		getPersistence().cacheResult(userTracker);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserTracker> userTrackers) {
		getPersistence().cacheResult(userTrackers);
	}

	public static com.liferay.portal.model.UserTracker create(
		long userTrackerId) {
		return getPersistence().create(userTrackerId);
	}

	public static com.liferay.portal.model.UserTracker remove(
		long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userTrackerId);
	}

	public static com.liferay.portal.model.UserTracker updateImpl(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userTracker, merge);
	}

	public static com.liferay.portal.model.UserTracker findByPrimaryKey(
		long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userTrackerId);
	}

	public static com.liferay.portal.model.UserTracker fetchByPrimaryKey(
		long userTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.UserTracker findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.UserTracker findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.UserTracker[] findByCompanyId_PrevAndNext(
		long userTrackerId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userTrackerId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.UserTracker findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.UserTracker findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.UserTracker[] findByUserId_PrevAndNext(
		long userTrackerId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userTrackerId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySessionId(sessionId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySessionId(sessionId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySessionId(sessionId, start, end, obc);
	}

	public static com.liferay.portal.model.UserTracker findBySessionId_First(
		java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySessionId_First(sessionId, obc);
	}

	public static com.liferay.portal.model.UserTracker findBySessionId_Last(
		java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySessionId_Last(sessionId, obc);
	}

	public static com.liferay.portal.model.UserTracker[] findBySessionId_PrevAndNext(
		long userTrackerId, java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBySessionId_PrevAndNext(userTrackerId, sessionId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeBySessionId(java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBySessionId(sessionId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countBySessionId(java.lang.String sessionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySessionId(sessionId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserTrackerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserTrackerPersistence persistence) {
		_persistence = persistence;
	}

	private static UserTrackerPersistence _persistence;
}