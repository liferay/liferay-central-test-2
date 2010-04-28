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
 * <a href="UserTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserTrackerLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerLocalService
 * @generated
 */
public class UserTrackerLocalServiceWrapper implements UserTrackerLocalService {
	public UserTrackerLocalServiceWrapper(
		UserTrackerLocalService userTrackerLocalService) {
		_userTrackerLocalService = userTrackerLocalService;
	}

	public com.liferay.portal.model.UserTracker addUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.addUserTracker(userTracker);
	}

	public com.liferay.portal.model.UserTracker createUserTracker(
		long userTrackerId) {
		return _userTrackerLocalService.createUserTracker(userTrackerId);
	}

	public void deleteUserTracker(long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userTrackerLocalService.deleteUserTracker(userTrackerId);
	}

	public void deleteUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userTrackerLocalService.deleteUserTracker(userTracker);
	}

	public java.util.List<com.liferay.portal.model.UserTracker> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.UserTracker> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.UserTracker> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.UserTracker getUserTracker(
		long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.getUserTracker(userTrackerId);
	}

	public java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.getUserTrackers(start, end);
	}

	public int getUserTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.getUserTrackersCount();
	}

	public com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.updateUserTracker(userTracker);
	}

	public com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.updateUserTracker(userTracker, merge);
	}

	public com.liferay.portal.model.UserTracker addUserTracker(long companyId,
		long userId, java.util.Date modifiedDate, java.lang.String sessionId,
		java.lang.String remoteAddr, java.lang.String remoteHost,
		java.lang.String userAgent,
		java.util.List<com.liferay.portal.model.UserTrackerPath> userTrackerPaths)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.addUserTracker(companyId, userId,
			modifiedDate, sessionId, remoteAddr, remoteHost, userAgent,
			userTrackerPaths);
	}

	public java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerLocalService.getUserTrackers(companyId, start, end);
	}

	public UserTrackerLocalService getWrappedUserTrackerLocalService() {
		return _userTrackerLocalService;
	}

	private UserTrackerLocalService _userTrackerLocalService;
}