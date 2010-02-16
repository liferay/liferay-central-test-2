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