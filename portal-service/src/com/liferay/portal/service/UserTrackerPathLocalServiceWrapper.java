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
 * <a href="UserTrackerPathLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserTrackerPathLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathLocalService
 * @generated
 */
public class UserTrackerPathLocalServiceWrapper
	implements UserTrackerPathLocalService {
	public UserTrackerPathLocalServiceWrapper(
		UserTrackerPathLocalService userTrackerPathLocalService) {
		_userTrackerPathLocalService = userTrackerPathLocalService;
	}

	public com.liferay.portal.model.UserTrackerPath addUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.addUserTrackerPath(userTrackerPath);
	}

	public com.liferay.portal.model.UserTrackerPath createUserTrackerPath(
		long userTrackerPathId) {
		return _userTrackerPathLocalService.createUserTrackerPath(userTrackerPathId);
	}

	public void deleteUserTrackerPath(long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userTrackerPathLocalService.deleteUserTrackerPath(userTrackerPathId);
	}

	public void deleteUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userTrackerPathLocalService.deleteUserTrackerPath(userTrackerPath);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.UserTrackerPath getUserTrackerPath(
		long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPath(userTrackerPathId);
	}

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPaths(start, end);
	}

	public int getUserTrackerPathsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPathsCount();
	}

	public com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.updateUserTrackerPath(userTrackerPath);
	}

	public com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.updateUserTrackerPath(userTrackerPath,
			merge);
	}

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		long userTrackerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPaths(userTrackerId,
			start, end);
	}

	public UserTrackerPathLocalService getWrappedUserTrackerPathLocalService() {
		return _userTrackerPathLocalService;
	}

	private UserTrackerPathLocalService _userTrackerPathLocalService;
}