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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTrackerLocalService.dynamicQueryCount(dynamicQuery);
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