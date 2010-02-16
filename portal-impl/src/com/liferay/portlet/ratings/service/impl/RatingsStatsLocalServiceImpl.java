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

package com.liferay.portlet.ratings.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.base.RatingsStatsLocalServiceBaseImpl;

/**
 * <a href="RatingsStatsLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class RatingsStatsLocalServiceImpl
	extends RatingsStatsLocalServiceBaseImpl {

	public RatingsStats addStats(long classNameId, long classPK)
		throws SystemException {

		long statsId = counterLocalService.increment();

		RatingsStats stats = ratingsStatsPersistence.create(statsId);

		stats.setClassNameId(classNameId);
		stats.setClassPK(classPK);
		stats.setTotalEntries(0);
		stats.setTotalScore(0.0);
		stats.setAverageScore(0.0);

		try {
			ratingsStatsPersistence.update(stats, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {classNameId=" + classNameId +
						", classPK=" + classPK + "}");
			}

			stats = ratingsStatsPersistence.fetchByC_C(
				classNameId, classPK, false);

			if (stats == null) {
				throw se;
			}
		}

		return stats;
	}

	public void deleteStats(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		try {
			ratingsStatsPersistence.removeByC_C(classNameId, classPK);
		}
		catch (NoSuchStatsException nsse) {
			_log.warn(nsse);
		}

		ratingsEntryPersistence.removeByC_C(classNameId, classPK);
	}

	public RatingsStats getStats(long statsId)
		throws PortalException, SystemException {

		return ratingsStatsPersistence.findByPrimaryKey(statsId);
	}

	public RatingsStats getStats(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		RatingsStats stats = ratingsStatsPersistence.fetchByC_C(
			classNameId, classPK);

		if (stats == null) {
			stats = ratingsStatsLocalService.addStats(classNameId, classPK);
		}

		return stats;
	}

	private static Log _log = LogFactoryUtil.getLog(
		RatingsStatsLocalServiceImpl.class);

}