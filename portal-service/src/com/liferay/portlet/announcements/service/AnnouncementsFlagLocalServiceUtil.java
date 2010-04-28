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

package com.liferay.portlet.announcements.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AnnouncementsFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AnnouncementsFlagLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagLocalService
 * @generated
 */
public class AnnouncementsFlagLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId) {
		return getService().createAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsFlag(announcementsFlag);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlag(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlags(start, end);
	}

	public static int getAnnouncementsFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlagsCount();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addFlag(userId, entryId, value);
	}

	public static void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlag(flagId);
	}

	public static void deleteFlags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlags(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFlag(userId, entryId, value);
	}

	public static AnnouncementsFlagLocalService getService() {
		if (_service == null) {
			_service = (AnnouncementsFlagLocalService)PortalBeanLocatorUtil.locate(AnnouncementsFlagLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AnnouncementsFlagLocalService service) {
		_service = service;
	}

	private static AnnouncementsFlagLocalService _service;
}