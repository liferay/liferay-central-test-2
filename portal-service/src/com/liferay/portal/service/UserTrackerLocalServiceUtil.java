/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

public class UserTrackerLocalServiceUtil {
	public static com.liferay.portal.model.UserTracker addUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.SystemException {
		return getService().addUserTracker(userTracker);
	}

	public static com.liferay.portal.model.UserTracker createUserTracker(
		long userTrackerId) {
		return getService().createUserTracker(userTrackerId);
	}

	public static void deleteUserTracker(long userTrackerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteUserTracker(userTrackerId);
	}

	public static void deleteUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.SystemException {
		getService().deleteUserTracker(userTracker);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.UserTracker getUserTracker(
		long userTrackerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserTracker(userTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getUserTrackers(start, end);
	}

	public static int getUserTrackersCount()
		throws com.liferay.portal.SystemException {
		return getService().getUserTrackersCount();
	}

	public static com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.SystemException {
		return getService().updateUserTracker(userTracker);
	}

	public static com.liferay.portal.model.UserTracker updateUserTracker(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateUserTracker(userTracker, merge);
	}

	public static com.liferay.portal.model.UserTracker addUserTracker(
		long companyId, long userId, java.util.Date modifiedDate,
		java.lang.String sessionId, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent,
		java.util.List<com.liferay.portal.model.UserTrackerPath> userTrackerPaths)
		throws com.liferay.portal.SystemException {
		return getService()
				   .addUserTracker(companyId, userId, modifiedDate, sessionId,
			remoteAddr, remoteHost, userAgent, userTrackerPaths);
	}

	public static java.util.List<com.liferay.portal.model.UserTracker> getUserTrackers(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getUserTrackers(companyId, start, end);
	}

	public static UserTrackerLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("UserTrackerLocalService is not set");
		}

		return _service;
	}

	public void setService(UserTrackerLocalService service) {
		_service = service;
	}

	private static UserTrackerLocalService _service;
}