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

package com.liferay.portlet.ratings.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsEntryService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;

import java.util.List;

/**
 * <a href="RatingsStatsLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class RatingsStatsLocalServiceBaseImpl
	implements RatingsStatsLocalService {
	public RatingsStats addRatingsStats(RatingsStats ratingsStats)
		throws SystemException {
		ratingsStats.setNew(true);

		return ratingsStatsPersistence.update(ratingsStats, false);
	}

	public RatingsStats createRatingsStats(long statsId) {
		return ratingsStatsPersistence.create(statsId);
	}

	public void deleteRatingsStats(long statsId)
		throws PortalException, SystemException {
		ratingsStatsPersistence.remove(statsId);
	}

	public void deleteRatingsStats(RatingsStats ratingsStats)
		throws SystemException {
		ratingsStatsPersistence.remove(ratingsStats);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return ratingsStatsPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return ratingsStatsPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public RatingsStats getRatingsStats(long statsId)
		throws PortalException, SystemException {
		return ratingsStatsPersistence.findByPrimaryKey(statsId);
	}

	public List<RatingsStats> getRatingsStatses(int start, int end)
		throws SystemException {
		return ratingsStatsPersistence.findAll(start, end);
	}

	public int getRatingsStatsesCount() throws SystemException {
		return ratingsStatsPersistence.countAll();
	}

	public RatingsStats updateRatingsStats(RatingsStats ratingsStats)
		throws SystemException {
		ratingsStats.setNew(false);

		return ratingsStatsPersistence.update(ratingsStats, true);
	}

	public RatingsStats updateRatingsStats(RatingsStats ratingsStats,
		boolean merge) throws SystemException {
		ratingsStats.setNew(false);

		return ratingsStatsPersistence.update(ratingsStats, merge);
	}

	public RatingsEntryLocalService getRatingsEntryLocalService() {
		return ratingsEntryLocalService;
	}

	public void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		this.ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public RatingsEntryService getRatingsEntryService() {
		return ratingsEntryService;
	}

	public void setRatingsEntryService(RatingsEntryService ratingsEntryService) {
		this.ratingsEntryService = ratingsEntryService;
	}

	public RatingsEntryPersistence getRatingsEntryPersistence() {
		return ratingsEntryPersistence;
	}

	public void setRatingsEntryPersistence(
		RatingsEntryPersistence ratingsEntryPersistence) {
		this.ratingsEntryPersistence = ratingsEntryPersistence;
	}

	public RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	public void setRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			PortalUtil.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryLocalService.impl")
	protected RatingsEntryLocalService ratingsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryService.impl")
	protected RatingsEntryService ratingsEntryService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence.impl")
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsStatsLocalService.impl")
	protected RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
}