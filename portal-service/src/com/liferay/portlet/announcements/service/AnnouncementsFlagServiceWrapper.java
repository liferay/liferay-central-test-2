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
 * This class is a wrapper for {@link AnnouncementsFlagService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagService
 * @generated
 */
public class AnnouncementsFlagServiceWrapper implements AnnouncementsFlagService {
	public AnnouncementsFlagServiceWrapper(
		AnnouncementsFlagService announcementsFlagService) {
		_announcementsFlagService = announcementsFlagService;
	}

	public void addFlag(long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagService.addFlag(entryId, value);
	}

	public void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlagService.deleteFlag(flagId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlagService.getFlag(entryId, value);
	}

	public AnnouncementsFlagService getWrappedAnnouncementsFlagService() {
		return _announcementsFlagService;
	}

	public void setWrappedAnnouncementsFlagService(
		AnnouncementsFlagService announcementsFlagService) {
		_announcementsFlagService = announcementsFlagService;
	}

	private AnnouncementsFlagService _announcementsFlagService;
}