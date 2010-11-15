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
 * <p>
 * This class is a wrapper for {@link AnnouncementsEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntryService
 * @generated
 */
public class AnnouncementsEntryServiceWrapper
	implements AnnouncementsEntryService {
	public AnnouncementsEntryServiceWrapper(
		AnnouncementsEntryService announcementsEntryService) {
		_announcementsEntryService = announcementsEntryService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long plid, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntryService.addEntry(plid, classNameId, classPK,
			title, content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, priority, alert);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsEntryService.deleteEntry(entryId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntryService.updateEntry(entryId, title, content,
			url, type, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority);
	}

	public AnnouncementsEntryService getWrappedAnnouncementsEntryService() {
		return _announcementsEntryService;
	}

	public void setWrappedAnnouncementsEntryService(
		AnnouncementsEntryService announcementsEntryService) {
		_announcementsEntryService = announcementsEntryService;
	}

	private AnnouncementsEntryService _announcementsEntryService;
}