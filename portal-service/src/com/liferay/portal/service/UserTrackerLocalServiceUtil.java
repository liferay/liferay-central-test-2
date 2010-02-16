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