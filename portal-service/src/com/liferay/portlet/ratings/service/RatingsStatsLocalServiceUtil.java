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

package com.liferay.portlet.ratings.service;

public class RatingsStatsLocalServiceUtil {
	public static com.liferay.portlet.ratings.model.RatingsStats addRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		return getService().addRatingsStats(ratingsStats);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats createRatingsStats(
		long statsId) {
		return getService().createRatingsStats(statsId);
	}

	public static void deleteRatingsStats(long statsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteRatingsStats(statsId);
	}

	public static void deleteRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		getService().deleteRatingsStats(ratingsStats);
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

	public static com.liferay.portlet.ratings.model.RatingsStats getRatingsStats(
		long statsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRatingsStats(statsId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getRatingsStatses(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getRatingsStatses(start, end);
	}

	public static int getRatingsStatsesCount()
		throws com.liferay.portal.SystemException {
		return getService().getRatingsStatsesCount();
	}

	public static com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		return getService().updateRatingsStats(ratingsStats);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateRatingsStats(ratingsStats, merge);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats addStats(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().addStats(classNameId, classPK);
	}

	public static void deleteStats(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteStats(className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats getStats(
		long statsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getStats(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats getStats(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getStats(className, classPK);
	}

	public static RatingsStatsLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("RatingsStatsLocalService is not set");
		}

		return _service;
	}

	public void setService(RatingsStatsLocalService service) {
		_service = service;
	}

	private static RatingsStatsLocalService _service;
}