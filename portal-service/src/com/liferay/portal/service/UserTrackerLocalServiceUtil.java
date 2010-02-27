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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="UserTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserTrackerLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerLocalService
 * @generated
 */
public class UserTrackerLocalServiceUtil {
	public static com.liferay.portal.model.UserTracker addUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserTracker(userTracker);
	}

	public static com.liferay.portal.model.UserTracker createUserTracker(
		long userTrackerId) {
		return getService().createUserTracker(userTrackerId);
	}

	public static void deleteUserTracker(long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTracker(userTrackerId);
	}

	public static void deleteUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTracker(userTracker);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.UserTracker getUserTracker(
		long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTracker(userTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackers(start, end);
	}

	public static int getUserTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackersCount();
	}

	public static com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserTracker(userTracker);
	}

	public static com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserTracker(userTracker, merge);
	}

	public static com.liferay.portal.model.UserTracker addUserTracker(
		long companyId, long userId, java.util.Date modifiedDate,
		java.lang.String sessionId, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent,
		java.util.List<com.liferay.portal.model.UserTrackerPath> userTrackerPaths)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUserTracker(companyId, userId, modifiedDate, sessionId,
			remoteAddr, remoteHost, userAgent, userTrackerPaths);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackers(companyId, start, end);
	}

	public static UserTrackerLocalService getService() {
		if (_service == null) {
			_service = (UserTrackerLocalService)PortalBeanLocatorUtil.locate(UserTrackerLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserTrackerLocalService service) {
		_service = service;
	}

	private static UserTrackerLocalService _service;
}