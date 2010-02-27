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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="RatingsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link RatingsEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryLocalService
 * @generated
 */
public class RatingsEntryLocalServiceUtil {
	public static com.liferay.portlet.ratings.model.RatingsEntry addRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRatingsEntry(ratingsEntry);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry createRatingsEntry(
		long entryId) {
		return getService().createRatingsEntry(entryId);
	}

	public static void deleteRatingsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRatingsEntry(entryId);
	}

	public static void deleteRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRatingsEntry(ratingsEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry getRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getRatingsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsEntries(start, end);
	}

	public static int getRatingsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsEntriesCount();
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRatingsEntry(ratingsEntry);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRatingsEntry(ratingsEntry, merge);
	}

	public static void deleteEntry(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(userId, className, classPK);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry getEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(userId, className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateEntry(
		long userId, java.lang.String className, long classPK, double score)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEntry(userId, className, classPK, score);
	}

	public static RatingsEntryLocalService getService() {
		if (_service == null) {
			_service = (RatingsEntryLocalService)PortalBeanLocatorUtil.locate(RatingsEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(RatingsEntryLocalService service) {
		_service = service;
	}

	private static RatingsEntryLocalService _service;
}