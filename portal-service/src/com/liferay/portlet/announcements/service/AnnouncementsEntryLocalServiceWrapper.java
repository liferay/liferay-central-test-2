/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service;


/**
 * <a href="AnnouncementsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntryLocalService
 * @generated
 */
public class AnnouncementsEntryLocalServiceWrapper
	implements AnnouncementsEntryLocalService {
	public AnnouncementsEntryLocalServiceWrapper(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {
		_announcementsEntryLocalService = announcementsEntryLocalService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.addAnnouncementsEntry(announcementsEntry);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry createAnnouncementsEntry(
		long entryId) {
		return _announcementsEntryLocalService.createAnnouncementsEntry(entryId);
	}

	public void deleteAnnouncementsEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsEntryLocalService.deleteAnnouncementsEntry(entryId);
	}

	public void deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		_announcementsEntryLocalService.deleteAnnouncementsEntry(announcementsEntry);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getAnnouncementsEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getAnnouncementsEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getAnnouncementsEntries(start,
			end);
	}

	public int getAnnouncementsEntriesCount()
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getAnnouncementsEntriesCount();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.updateAnnouncementsEntry(announcementsEntry);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.updateAnnouncementsEntry(announcementsEntry,
			merge);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.addEntry(userId, classNameId,
			classPK, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	public void checkEntries()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsEntryLocalService.checkEntries();
	}

	public void deleteEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsEntryLocalService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		boolean alert, int flagValue, int start, int end)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntries(userId, scopes,
			alert, flagValue, start, end);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntries(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue, start, end);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntries(classNameId, classPK,
			alert, start, end);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntries(userId, classNameId,
			classPKs, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, start, end);
	}

	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntriesCount(userId, scopes,
			alert, flagValue);
	}

	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntriesCount(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue);
	}

	public int getEntriesCount(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntriesCount(classNameId,
			classPK, alert);
	}

	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		boolean alert, int flagValue) throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, alert, flagValue);
	}

	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getUserEntries(userId, start, end);
	}

	public int getUserEntriesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.getUserEntriesCount(userId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsEntryLocalService.updateEntry(userId, entryId,
			title, content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, priority);
	}

	public AnnouncementsEntryLocalService getWrappedAnnouncementsEntryLocalService() {
		return _announcementsEntryLocalService;
	}

	private AnnouncementsEntryLocalService _announcementsEntryLocalService;
}