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


/**
 * <a href="BrowserTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BrowserTrackerLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerLocalService
 * @generated
 */
public class BrowserTrackerLocalServiceWrapper
	implements BrowserTrackerLocalService {
	public BrowserTrackerLocalServiceWrapper(
		BrowserTrackerLocalService browserTrackerLocalService) {
		_browserTrackerLocalService = browserTrackerLocalService;
	}

	public com.liferay.portal.model.BrowserTracker addBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.addBrowserTracker(browserTracker);
	}

	public com.liferay.portal.model.BrowserTracker createBrowserTracker(
		long browserTrackerId) {
		return _browserTrackerLocalService.createBrowserTracker(browserTrackerId);
	}

	public void deleteBrowserTracker(long browserTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_browserTrackerLocalService.deleteBrowserTracker(browserTrackerId);
	}

	public void deleteBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		_browserTrackerLocalService.deleteBrowserTracker(browserTracker);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.BrowserTracker getBrowserTracker(
		long browserTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.getBrowserTracker(browserTrackerId);
	}

	public java.util.List<com.liferay.portal.model.BrowserTracker> getBrowserTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.getBrowserTrackers(start, end);
	}

	public int getBrowserTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.getBrowserTrackersCount();
	}

	public com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.updateBrowserTracker(browserTracker);
	}

	public com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.updateBrowserTracker(browserTracker,
			merge);
	}

	public void deleteUserBrowserTracker(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_browserTrackerLocalService.deleteUserBrowserTracker(userId);
	}

	public com.liferay.portal.model.BrowserTracker getBrowserTracker(
		long userId, long browserKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.getBrowserTracker(userId, browserKey);
	}

	public com.liferay.portal.model.BrowserTracker updateBrowserTracker(
		long userId, long browserKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.updateBrowserTracker(userId,
			browserKey);
	}

	public BrowserTrackerLocalService getWrappedBrowserTrackerLocalService() {
		return _browserTrackerLocalService;
	}

	private BrowserTrackerLocalService _browserTrackerLocalService;
}