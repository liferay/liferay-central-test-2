/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.ratings.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.service.base.RatingsEntryLocalServiceBaseImpl;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import java.util.Date;

/**
 * <a href="RatingsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsEntryLocalServiceImpl
	extends RatingsEntryLocalServiceBaseImpl {

	public RatingsEntry getEntry(
			String userId, String className, String classPK)
		throws PortalException, SystemException {

		return RatingsEntryUtil.findByU_C_C(userId, className, classPK);
	}

	public RatingsEntry updateEntry(
			String userId, String className, String classPK, double score)
		throws PortalException, SystemException {

		Date now = new Date();

		RatingsEntry entry = null;

		try {
			entry = RatingsEntryUtil.findByU_C_C(userId, className, classPK);

			double oldScore = entry.getScore();

			entry.setModifiedDate(now);
			entry.setScore(score);

			RatingsEntryUtil.update(entry);

			// Stats

			RatingsStats stats = RatingsStatsLocalServiceUtil.getStats(
				className, classPK);

			stats.setTotalScore(stats.getTotalScore() - oldScore + score);
			stats.setAverageScore(
				stats.getTotalScore() / stats.getTotalEntries());

			RatingsStatsUtil.update(stats);
		}
		catch (NoSuchEntryException nsee) {
			User user = UserUtil.findByPrimaryKey(userId);

			long entryId = CounterLocalServiceUtil.increment(
				RatingsEntry.class.getName());

			entry = RatingsEntryUtil.create(entryId);

			entry.setCompanyId(user.getCompanyId());
			entry.setUserId(user.getUserId());
			entry.setUserName(user.getFullName());
			entry.setCreateDate(now);
			entry.setModifiedDate(now);
			entry.setClassName(className);
			entry.setClassPK(classPK);
			entry.setScore(score);

			RatingsEntryUtil.update(entry);

			// Stats

			RatingsStats stats = RatingsStatsLocalServiceUtil.getStats(
				className, classPK);

			stats.setTotalEntries(stats.getTotalEntries() + 1);
			stats.setTotalScore(stats.getTotalScore() + score);
			stats.setAverageScore(
				stats.getTotalScore() / stats.getTotalEntries());

			RatingsStatsUtil.update(stats);
		}

		return entry;
	}

}