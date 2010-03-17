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
 * <a href="PasswordTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordTrackerLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTrackerLocalService
 * @generated
 */
public class PasswordTrackerLocalServiceWrapper
	implements PasswordTrackerLocalService {
	public PasswordTrackerLocalServiceWrapper(
		PasswordTrackerLocalService passwordTrackerLocalService) {
		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	public com.liferay.portal.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.addPasswordTracker(passwordTracker);
	}

	public com.liferay.portal.model.PasswordTracker createPasswordTracker(
		long passwordTrackerId) {
		return _passwordTrackerLocalService.createPasswordTracker(passwordTrackerId);
	}

	public void deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTrackerId);
	}

	public void deletePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTracker);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.PasswordTracker getPasswordTracker(
		long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTracker(passwordTrackerId);
	}

	public java.util.List<com.liferay.portal.model.PasswordTracker> getPasswordTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackers(start, end);
	}

	public int getPasswordTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackersCount();
	}

	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker);
	}

	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker,
			merge);
	}

	public void deletePasswordTrackers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTrackers(userId);
	}

	public boolean isSameAsCurrentPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isSameAsCurrentPassword(userId,
			newClearTextPwd);
	}

	public boolean isValidPassword(long userId, java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isValidPassword(userId,
			newClearTextPwd);
	}

	public void trackPassword(long userId, java.lang.String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.trackPassword(userId, encPassword);
	}

	public PasswordTrackerLocalService getWrappedPasswordTrackerLocalService() {
		return _passwordTrackerLocalService;
	}

	private PasswordTrackerLocalService _passwordTrackerLocalService;
}