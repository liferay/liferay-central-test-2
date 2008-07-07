/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementsEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementsEntryLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceFactory
 *
 */
public class AnnouncementsEntryLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.addAnnouncementsEntry(announcementsEntry);
	}

	public static void deleteAnnouncementsEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		announcementsEntryLocalService.deleteAnnouncementsEntry(entryId);
	}

	public static void deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		announcementsEntryLocalService.deleteAnnouncementsEntry(announcementsEntry);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getAnnouncementsEntry(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.updateAnnouncementsEntry(announcementsEntry);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.addEntry(userId, classNameId,
			classPK, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	public static void checkEntries()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		announcementsEntryLocalService.checkEntries();
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		announcementsEntryLocalService.deleteEntry(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntries(classNameId, classPK,
			alert, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntries(userId, classNameId,
			classPKs, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		boolean alert, int flagValue, int start, int end)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntries(userId, scopes, alert,
			flagValue, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntries(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue, start, end);
	}

	public static int getEntriesCount(long classNameId, long classPK,
		boolean alert) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntriesCount(classNameId,
			classPK, alert);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, alert, flagValue);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean alert,
		int flagValue) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue) throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntriesCount(userId, scopes,
			alert, flagValue);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getEntriesCount(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getUserEntries(userId, start, end);
	}

	public static int getUserEntriesCount(long userId)
		throws com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.getUserEntriesCount(userId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsEntryLocalService announcementsEntryLocalService = AnnouncementsEntryLocalServiceFactory.getService();

		return announcementsEntryLocalService.updateEntry(entryId, title,
			content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, priority);
	}
}