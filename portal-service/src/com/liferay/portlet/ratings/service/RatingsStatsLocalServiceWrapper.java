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

package com.liferay.portlet.ratings.service;


/**
 * <a href="RatingsStatsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RatingsStatsLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStatsLocalService
 * @generated
 */
public class RatingsStatsLocalServiceWrapper implements RatingsStatsLocalService {
	public RatingsStatsLocalServiceWrapper(
		RatingsStatsLocalService ratingsStatsLocalService) {
		_ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public com.liferay.portlet.ratings.model.RatingsStats addRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.addRatingsStats(ratingsStats);
	}

	public com.liferay.portlet.ratings.model.RatingsStats createRatingsStats(
		long statsId) {
		return _ratingsStatsLocalService.createRatingsStats(statsId);
	}

	public void deleteRatingsStats(long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteRatingsStats(statsId);
	}

	public void deleteRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteRatingsStats(ratingsStats);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.ratings.model.RatingsStats getRatingsStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStats(statsId);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getRatingsStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStatses(start, end);
	}

	public int getRatingsStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStatsesCount();
	}

	public com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats);
	}

	public com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats, merge);
	}

	public com.liferay.portlet.ratings.model.RatingsStats addStats(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.addStats(classNameId, classPK);
	}

	public void deleteStats(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteStats(className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsStats getStats(long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getStats(statsId);
	}

	public com.liferay.portlet.ratings.model.RatingsStats getStats(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getStats(className, classPK);
	}

	public RatingsStatsLocalService getWrappedRatingsStatsLocalService() {
		return _ratingsStatsLocalService;
	}

	private RatingsStatsLocalService _ratingsStatsLocalService;
}