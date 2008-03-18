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
 * <a href="AnnouncementFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementFlagLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementFlagLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementFlagLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementFlagLocalServiceFactory
 *
 */
public class AnnouncementFlagLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementFlag addAnnouncementFlag(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.addAnnouncementFlag(announcementFlag);
	}

	public static void deleteAnnouncementFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.deleteAnnouncementFlag(flagId);
	}

	public static void deleteAnnouncementFlag(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.deleteAnnouncementFlag(announcementFlag);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.dynamicQuery(queryInitializer,
			begin, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag updateAnnouncementFlag(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.updateAnnouncementFlag(announcementFlag);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementEntryPersistence getAnnouncementEntryPersistence() {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.getAnnouncementEntryPersistence();
	}

	public static void setAnnouncementEntryPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementEntryPersistence announcementEntryPersistence) {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.setAnnouncementEntryPersistence(announcementEntryPersistence);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinder getAnnouncementEntryFinder() {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.getAnnouncementEntryFinder();
	}

	public static void setAnnouncementEntryFinder(
		com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinder announcementEntryFinder) {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.setAnnouncementEntryFinder(announcementEntryFinder);
	}

	public static com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence getAnnouncementFlagPersistence() {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.getAnnouncementFlagPersistence();
	}

	public static void setAnnouncementFlagPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence announcementFlagPersistence) {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.setAnnouncementFlagPersistence(announcementFlagPersistence);
	}

	public static void afterPropertiesSet() {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag addAnnouncementFlag(
		long userId, long entryId, int flag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.addAnnouncementFlag(userId,
			entryId, flag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag getAnnouncementFlag(
		long userId, long entryId, int flag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		return announcementFlagLocalService.getAnnouncementFlag(userId,
			entryId, flag);
	}

	public static void deleteAnnouncementFlags(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementFlagLocalService announcementFlagLocalService = AnnouncementFlagLocalServiceFactory.getService();

		announcementFlagLocalService.deleteAnnouncementFlags(entryId);
	}
}