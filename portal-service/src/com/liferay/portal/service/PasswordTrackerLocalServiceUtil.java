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
 * <a href="PasswordTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PasswordTrackerLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTrackerLocalService
 * @generated
 */
public class PasswordTrackerLocalServiceUtil {
	public static com.liferay.portal.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPasswordTracker(passwordTracker);
	}

	public static com.liferay.portal.model.PasswordTracker createPasswordTracker(
		long passwordTrackerId) {
		return getService().createPasswordTracker(passwordTrackerId);
	}

	public static void deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTracker(passwordTrackerId);
	}

	public static void deletePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTracker(passwordTracker);
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

	public static com.liferay.portal.model.PasswordTracker getPasswordTracker(
		long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTracker(passwordTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordTracker> getPasswordTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTrackers(start, end);
	}

	public static int getPasswordTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTrackersCount();
	}

	public static com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePasswordTracker(passwordTracker);
	}

	public static com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePasswordTracker(passwordTracker, merge);
	}

	public static void deletePasswordTrackers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTrackers(userId);
	}

	public static boolean isSameAsCurrentPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isSameAsCurrentPassword(userId, newClearTextPwd);
	}

	public static boolean isValidPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isValidPassword(userId, newClearTextPwd);
	}

	public static void trackPassword(long userId, java.lang.String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().trackPassword(userId, encPassword);
	}

	public static PasswordTrackerLocalService getService() {
		if (_service == null) {
			_service = (PasswordTrackerLocalService)PortalBeanLocatorUtil.locate(PasswordTrackerLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PasswordTrackerLocalService service) {
		_service = service;
	}

	private static PasswordTrackerLocalService _service;
}