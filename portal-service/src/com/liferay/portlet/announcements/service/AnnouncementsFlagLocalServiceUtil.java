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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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
 * {@link AnnouncementsFlagLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagLocalService
 * @generated
 */
public class AnnouncementsFlagLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId) {
		return getService().createAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsFlag(flagId);
	}

	public static void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnnouncementsFlag(announcementsFlag);
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

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlag(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlags(start, end);
	}

	public static int getAnnouncementsFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAnnouncementsFlagsCount();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAnnouncementsFlag(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addFlag(userId, entryId, value);
	}

	public static void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlag(flagId);
	}

	public static void deleteFlags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlags(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFlag(userId, entryId, value);
	}

	public static AnnouncementsFlagLocalService getService() {
		if (_service == null) {
			_service = (AnnouncementsFlagLocalService)PortalBeanLocatorUtil.locate(AnnouncementsFlagLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AnnouncementsFlagLocalService service) {
		_service = service;
	}

	private static AnnouncementsFlagLocalService _service;
}