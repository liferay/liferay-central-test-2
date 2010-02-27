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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.model.RatingsStats;

/**
 * <a href="RatingsStatsPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStatsPersistenceImpl
 * @see       RatingsStatsUtil
 * @generated
 */
public interface RatingsStatsPersistence extends BasePersistence<RatingsStats> {
	public void cacheResult(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats);

	public void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsStats> ratingsStatses);

	public com.liferay.portlet.ratings.model.RatingsStats create(long statsId);

	public com.liferay.portlet.ratings.model.RatingsStats remove(long statsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	public com.liferay.portlet.ratings.model.RatingsStats updateImpl(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.ratings.model.RatingsStats findByPrimaryKey(
		long statsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	public com.liferay.portlet.ratings.model.RatingsStats fetchByPrimaryKey(
		long statsId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.ratings.model.RatingsStats findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	public com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}