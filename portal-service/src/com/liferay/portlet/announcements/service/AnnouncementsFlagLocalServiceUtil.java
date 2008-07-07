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
 * <a href="AnnouncementsFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementsFlagLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementsFlagLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceFactory
 *
 */
public class AnnouncementsFlagLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.addAnnouncementsFlag(announcementsFlag);
	}

	public static void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		announcementsFlagLocalService.deleteAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		announcementsFlagLocalService.deleteAnnouncementsFlag(announcementsFlag);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.getAnnouncementsFlag(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.updateAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.addFlag(userId, entryId, value);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		return announcementsFlagLocalService.getFlag(userId, entryId, value);
	}

	public static void deleteFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		announcementsFlagLocalService.deleteFlag(flagId);
	}

	public static void deleteFlags(long entryId)
		throws com.liferay.portal.SystemException {
		AnnouncementsFlagLocalService announcementsFlagLocalService = AnnouncementsFlagLocalServiceFactory.getService();

		announcementsFlagLocalService.deleteFlags(entryId);
	}
}