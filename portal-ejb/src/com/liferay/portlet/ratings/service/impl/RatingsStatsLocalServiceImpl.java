/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="RatingsStatsLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RatingsStatsLocalServiceImpl implements RatingsStatsLocalService {

	public void deleteStats(String className, String classPK)
		throws PortalException, SystemException {

		try {
			RatingsStatsUtil.removeByC_C(className, classPK);
		}
		catch (NoSuchStatsException nsse) {
			_log.warn(nsse);
		}

		RatingsEntryUtil.removeByC_C(className, classPK);
	}

	public RatingsStats getStats(long statsId)
		throws PortalException, SystemException {

		return RatingsStatsUtil.findByPrimaryKey(statsId);
	}

	public RatingsStats getStats(String className, String classPK)
		throws PortalException, SystemException {

		RatingsStats stats = null;

		try {
			stats = RatingsStatsUtil.findByC_C(className, classPK);
		}
		catch (NoSuchStatsException nsse) {
			long statsId = CounterLocalServiceUtil.increment(
				RatingsStats.class.getName());

			stats = RatingsStatsUtil.create(statsId);

			stats.setClassName(className);
			stats.setClassPK(classPK);
			stats.setTotalEntries(0);
			stats.setTotalScore(0.0);
			stats.setAverageScore(0.0);

			RatingsStatsUtil.update(stats);
		}

		return stats;
	}

	private static Log _log =
		LogFactory.getLog(RatingsStatsLocalServiceImpl.class);

}