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
 * <a href="RatingsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RatingsEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryLocalService
 * @generated
 */
public class RatingsEntryLocalServiceWrapper implements RatingsEntryLocalService {
	public RatingsEntryLocalServiceWrapper(
		RatingsEntryLocalService ratingsEntryLocalService) {
		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public com.liferay.portlet.ratings.model.RatingsEntry addRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.addRatingsEntry(ratingsEntry);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry createRatingsEntry(
		long entryId) {
		return _ratingsEntryLocalService.createRatingsEntry(entryId);
	}

	public void deleteRatingsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteRatingsEntry(entryId);
	}

	public void deleteRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteRatingsEntry(ratingsEntry);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry getRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getRatingsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntries(start, end);
	}

	public int getRatingsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntriesCount();
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry, merge);
	}

	public void deleteEntry(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteEntry(userId, className, classPK);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntries(className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry getEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntry(userId, className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateEntry(
		long userId, java.lang.String className, long classPK, double score,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateEntry(userId, className,
			classPK, score, serviceContext);
	}

	public RatingsEntryLocalService getWrappedRatingsEntryLocalService() {
		return _ratingsEntryLocalService;
	}

	private RatingsEntryLocalService _ratingsEntryLocalService;
}