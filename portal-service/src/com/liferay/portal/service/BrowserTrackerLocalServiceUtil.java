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
 * <a href="BrowserTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link BrowserTrackerLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerLocalService
 * @generated
 */
public class BrowserTrackerLocalServiceUtil {
	public static com.liferay.portal.model.BrowserTracker addBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.SystemException {
		return getService().addBrowserTracker(browserTracker);
	}

	public static com.liferay.portal.model.BrowserTracker createBrowserTracker(
		long browserTrackerId) {
		return getService().createBrowserTracker(browserTrackerId);
	}

	public static void deleteBrowserTracker(long browserTrackerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteBrowserTracker(browserTrackerId);
	}

	public static void deleteBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.SystemException {
		getService().deleteBrowserTracker(browserTracker);
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

	public static com.liferay.portal.model.BrowserTracker getBrowserTracker(
		long browserTrackerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getBrowserTracker(browserTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> getBrowserTrackers(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getBrowserTrackers(start, end);
	}

	public static int getBrowserTrackersCount()
		throws com.liferay.portal.SystemException {
		return getService().getBrowserTrackersCount();
	}

	public static com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.SystemException {
		return getService().updateBrowserTracker(browserTracker);
	}

	public static com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateBrowserTracker(browserTracker, merge);
	}

	public static void deleteUserBrowserTracker(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserBrowserTracker(userId);
	}

	public static com.liferay.portal.model.BrowserTracker getBrowserTracker(
		long userId, long browserKey) throws com.liferay.portal.SystemException {
		return getService().getBrowserTracker(userId, browserKey);
	}

	public static com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		long userId, long browserKey) throws com.liferay.portal.SystemException {
		return getService().updateBrowserTracker(userId, browserKey);
	}

	public static BrowserTrackerLocalService getService() {
		if (_service == null) {
			_service = (BrowserTrackerLocalService)PortalBeanLocatorUtil.locate(BrowserTrackerLocalService.class.getName());
		}

		return _service;
	}

	public void setService(BrowserTrackerLocalService service) {
		_service = service;
	}

	private static BrowserTrackerLocalService _service;
}