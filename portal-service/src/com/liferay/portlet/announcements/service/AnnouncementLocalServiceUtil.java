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
 * <a href="AnnouncementLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementLocalServiceFactory
 *
 */
public class AnnouncementLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.Announcement addAnnouncement(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.addAnnouncement(announcement);
	}

	public static void deleteAnnouncement(long announcementId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.deleteAnnouncement(announcementId);
	}

	public static void deleteAnnouncement(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.deleteAnnouncement(announcement);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.announcements.model.Announcement updateAnnouncement(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.updateAnnouncement(announcement);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementPersistence getAnnouncementPersistence() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementPersistence();
	}

	public static void setAnnouncementPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementPersistence announcementPersistence) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setAnnouncementPersistence(announcementPersistence);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementFinder getAnnouncementFinder() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementFinder();
	}

	public static void setAnnouncementFinder(
		com.liferay.portlet.announcements.service.persistence.AnnouncementFinder announcementFinder) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setAnnouncementFinder(announcementFinder);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence getAnnouncementFlagPersistence() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementFlagPersistence();
	}

	public static void setAnnouncementFlagPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence announcementFlagPersistence) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setAnnouncementFlagPersistence(announcementFlagPersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.setUserFinder(userFinder);
	}

	public static void afterPropertiesSet() {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		announcementLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.announcements.model.Announcement addAnnouncement(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.addAnnouncement(userId, classNameId,
			classPK, title, content, url, type, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear,
			priority, alert);
	}

	public static com.liferay.portlet.announcements.model.Announcement getAnnouncement(
		long announcementId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncement(announcementId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> getAnnouncements(
		long classNameId, long classPK, boolean alert, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncements(classNameId, classPK,
			alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> getAnnouncements(
		long userId, long classNameId, long[] classPKs, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncements(userId, classNameId,
			classPKs, displayMonth, displayDay, displayYear, expirationMonth,
			expirationDay, expirationYear, flag, alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> getAnnouncements(
		long userId, java.util.LinkedHashMap<Long, Long[]> announcementsParams,
		int flag, boolean alert, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncements(userId,
			announcementsParams, flag, alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> getAnnouncements(
		long userId, java.util.LinkedHashMap<Long, Long[]> announcementsParams,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncements(userId,
			announcementsParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert, begin,
			end);
	}

	public static int getAnnouncementsCount(long classNameId, long classPK,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementsCount(classNameId,
			classPK, alert);
	}

	public static int getAnnouncementsCount(long userId, long classNameId,
		long[] classPKs, int flag, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementsCount(userId,
			classNameId, classPKs, flag, alert);
	}

	public static int getAnnouncementsCount(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear, int flag,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementsCount(userId,
			classNameId, classPKs, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public static int getAnnouncementsCount(long userId,
		java.util.LinkedHashMap<Long, Long[]> announcementsParams, int flag,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementsCount(userId,
			announcementsParams, flag, alert);
	}

	public static int getAnnouncementsCount(long userId,
		java.util.LinkedHashMap<Long, Long[]> announcementsParams,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getAnnouncementsCount(userId,
			announcementsParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.Announcement> getUserAnnouncements(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getUserAnnouncements(userId, begin, end);
	}

	public static int getUserAnnouncementsCount(long userId)
		throws com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.getUserAnnouncementsCount(userId);
	}

	public static com.liferay.portlet.announcements.model.Announcement updateAnnouncement(
		long announcementId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementLocalService announcementLocalService = AnnouncementLocalServiceFactory.getService();

		return announcementLocalService.updateAnnouncement(announcementId,
			title, content, url, type, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, priority);
	}
}