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
 * <a href="AnnouncementsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AnnouncementsEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntryLocalService
 * @generated
 */
public class AnnouncementsEntryLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAnnouncementsEntry(announcementsEntry);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry createAnnouncementsEntry(
		long entryId) {
		return getService().createAnnouncementsEntry(entryId);
	}

	public static void deleteAnnouncementsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsEntry(entryId);
	}

	public static void deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsEntry(announcementsEntry);
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

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getAnnouncementsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsEntries(start, end);
	}

	public static int getAnnouncementsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsEntriesCount();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsEntry(announcementsEntry);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsEntry(announcementsEntry, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(userId, classNameId, classPK, title, content, url,
			type, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);
	}

	public static void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkEntries();
	}

	public static void deleteEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		boolean alert, int flagValue, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntries(userId, scopes, alert, flagValue, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntries(userId, scopes, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(classNameId, classPK, alert, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntries(userId, classNameId, classPKs, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue, start, end);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(userId, scopes, alert, flagValue);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntriesCount(userId, scopes, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue);
	}

	public static int getEntriesCount(long classNameId, long classPK,
		boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(classNameId, classPK, alert);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, boolean alert, int flagValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntriesCount(userId, classNameId, classPKs, alert,
			flagValue);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean alert,
		int flagValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntriesCount(userId, classNameId, classPKs,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserEntries(userId, start, end);
	}

	public static int getUserEntriesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserEntriesCount(userId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, entryId, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority);
	}

	public static AnnouncementsEntryLocalService getService() {
		if (_service == null) {
			_service = (AnnouncementsEntryLocalService)PortalBeanLocatorUtil.locate(AnnouncementsEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AnnouncementsEntryLocalService service) {
		_service = service;
	}

	private static AnnouncementsEntryLocalService _service;
}