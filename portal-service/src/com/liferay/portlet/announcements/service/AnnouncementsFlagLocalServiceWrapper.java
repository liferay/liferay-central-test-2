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


/**
 * <a href="AnnouncementsFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlagLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagLocalService
 * @generated
 */
public class AnnouncementsFlagLocalServiceWrapper
	implements AnnouncementsFlagLocalService {
	public AnnouncementsFlagLocalServiceWrapper(
		AnnouncementsFlagLocalService announcementsFlagLocalService) {
		_announcementsFlagLocalService = announcementsFlagLocalService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.addAnnouncementsFlag(announcementsFlag);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId) {
		return _announcementsFlagLocalService.createAnnouncementsFlag(flagId);
	}

	public void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagLocalService.deleteAnnouncementsFlag(flagId);
	}

	public void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagLocalService.deleteAnnouncementsFlag(announcementsFlag);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlag(flagId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlags(start, end);
	}

	public int getAnnouncementsFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlagsCount();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.updateAnnouncementsFlag(announcementsFlag);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.updateAnnouncementsFlag(announcementsFlag,
			merge);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.addFlag(userId, entryId, value);
	}

	public void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagLocalService.deleteFlag(flagId);
	}

	public void deleteFlags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagLocalService.deleteFlags(entryId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagLocalService.getFlag(userId, entryId, value);
	}

	public AnnouncementsFlagLocalService getWrappedAnnouncementsFlagLocalService() {
		return _announcementsFlagLocalService;
	}

	private AnnouncementsFlagLocalService _announcementsFlagLocalService;
}