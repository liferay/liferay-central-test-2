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
 * <a href="AnnouncementEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementEntryLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementEntryLocalServiceFactory
 *
 */
public class AnnouncementEntryLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementEntry addAnnouncementEntry(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry)
		throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.addAnnouncementEntry(announcementEntry);
	}

	public static void deleteAnnouncementEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.deleteAnnouncementEntry(entryId);
	}

	public static void deleteAnnouncementEntry(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.deleteAnnouncementEntry(announcementEntry);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.dynamicQuery(queryInitializer,
			begin, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry updateAnnouncementEntry(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry)
		throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.updateAnnouncementEntry(announcementEntry);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementEntryPersistence getAnnouncementEntryPersistence() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getAnnouncementEntryPersistence();
	}

	public static void setAnnouncementEntryPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementEntryPersistence announcementEntryPersistence) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setAnnouncementEntryPersistence(announcementEntryPersistence);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinder getAnnouncementEntryFinder() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getAnnouncementEntryFinder();
	}

	public static void setAnnouncementEntryFinder(
		com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinder announcementEntryFinder) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setAnnouncementEntryFinder(announcementEntryFinder);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence getAnnouncementFlagPersistence() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getAnnouncementFlagPersistence();
	}

	public static void setAnnouncementFlagPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence announcementFlagPersistence) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setAnnouncementFlagPersistence(announcementFlagPersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.setUserFinder(userFinder);
	}

	public static void afterPropertiesSet() {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.addEntry(userId, classNameId,
			classPK, title, content, url, type, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear,
			priority, alert);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		announcementEntryLocalService.deleteEntry(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> getEntries(
		long classNameId, long classPK, boolean alert, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntries(classNameId, classPK,
			alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntries(userId, classNameId,
			classPKs, displayMonth, displayDay, displayYear, expirationMonth,
			expirationDay, expirationYear, flag, alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, Long[]> entriesParams,
		int flag, boolean alert, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntries(userId, entriesParams,
			flag, alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, Long[]> entriesParams,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntries(userId, entriesParams,
			displayMonth, displayDay, displayYear, expirationMonth,
			expirationDay, expirationYear, flag, alert, begin, end);
	}

	public static int getEntriesCount(long classNameId, long classPK,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntriesCount(classNameId,
			classPK, alert);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, int flag, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, flag, alert);
	}

	public static int getEntriesCount(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear, int flag,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, Long[]> entriesParams, int flag,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntriesCount(userId,
			entriesParams, flag, alert);
	}

	public static int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, Long[]> entriesParams, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getEntriesCount(userId,
			entriesParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> getUserEntries(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getUserEntries(userId, begin, end);
	}

	public static int getUserEntriesCount(long userId)
		throws com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.getUserEntriesCount(userId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementEntryLocalService announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getService();

		return announcementEntryLocalService.updateEntry(entryId, title,
			content, url, type, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, priority);
	}
}