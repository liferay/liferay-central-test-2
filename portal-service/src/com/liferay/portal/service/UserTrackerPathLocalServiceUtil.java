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
 * <a href="UserTrackerPathLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserTrackerPathLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathLocalService
 * @generated
 */
public class UserTrackerPathLocalServiceUtil {
	public static com.liferay.portal.model.UserTrackerPath addUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserTrackerPath(userTrackerPath);
	}

	public static com.liferay.portal.model.UserTrackerPath createUserTrackerPath(
		long userTrackerPathId) {
		return getService().createUserTrackerPath(userTrackerPathId);
	}

	public static void deleteUserTrackerPath(long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTrackerPath(userTrackerPathId);
	}

	public static void deleteUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTrackerPath(userTrackerPath);
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

	public static com.liferay.portal.model.UserTrackerPath getUserTrackerPath(
		long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackerPath(userTrackerPathId);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackerPaths(start, end);
	}

	public static int getUserTrackerPathsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackerPathsCount();
	}

	public static com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserTrackerPath(userTrackerPath);
	}

	public static com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserTrackerPath(userTrackerPath, merge);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		long userTrackerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTrackerPaths(userTrackerId, start, end);
	}

	public static UserTrackerPathLocalService getService() {
		if (_service == null) {
			_service = (UserTrackerPathLocalService)PortalBeanLocatorUtil.locate(UserTrackerPathLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserTrackerPathLocalService service) {
		_service = service;
	}

	private static UserTrackerPathLocalService _service;
}