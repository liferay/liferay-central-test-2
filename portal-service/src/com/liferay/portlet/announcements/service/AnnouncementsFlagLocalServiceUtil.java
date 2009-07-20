/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public class AnnouncementsFlagLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return getService().addAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId) {
		return getService().createAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		getService().deleteAnnouncementsFlag(announcementsFlag);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAnnouncementsFlag(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAnnouncementsFlags(start, end);
	}

	public static int getAnnouncementsFlagsCount()
		throws com.liferay.portal.SystemException {
		return getService().getAnnouncementsFlagsCount();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.SystemException {
		return getService().addFlag(userId, entryId, value);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFlag(userId, entryId, value);
	}

	public static void deleteFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFlag(flagId);
	}

	public static void deleteFlags(long entryId)
		throws com.liferay.portal.SystemException {
		getService().deleteFlags(entryId);
	}

	public static AnnouncementsFlagLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"AnnouncementsFlagLocalService is not set");
		}

		return _service;
	}

	public void setService(AnnouncementsFlagLocalService service) {
		_service = service;
	}

	private static AnnouncementsFlagLocalService _service;
}