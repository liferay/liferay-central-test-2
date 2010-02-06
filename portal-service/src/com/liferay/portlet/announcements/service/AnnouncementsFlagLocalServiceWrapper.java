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
 * <a href="AnnouncementsFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlagLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagLocalService
 * @generated
 */
public class AnnouncementsFlagLocalServiceWrapper
	implements AnnouncementsFlagLocalService {
	public AnnouncementsFlagLocalServiceWrapper(
		AnnouncementsFlagLocalService announcementsFlagLocalService) {
		_announcementsFlagLocalService = announcementsFlagLocalService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.addAnnouncementsFlag(announcementsFlag);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId) {
		return _announcementsFlagLocalService.createAnnouncementsFlag(flagId);
	}

	public void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsFlagLocalService.deleteAnnouncementsFlag(flagId);
	}

	public void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		_announcementsFlagLocalService.deleteAnnouncementsFlag(announcementsFlag);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlag(flagId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end) throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlags(start, end);
	}

	public int getAnnouncementsFlagsCount()
		throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.getAnnouncementsFlagsCount();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.updateAnnouncementsFlag(announcementsFlag);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.updateAnnouncementsFlag(announcementsFlag,
			merge);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.addFlag(userId, entryId, value);
	}

	public void deleteFlag(long flagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_announcementsFlagLocalService.deleteFlag(flagId);
	}

	public void deleteFlags(long entryId)
		throws com.liferay.portal.SystemException {
		_announcementsFlagLocalService.deleteFlags(entryId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _announcementsFlagLocalService.getFlag(userId, entryId, value);
	}

	public AnnouncementsFlagLocalService getWrappedAnnouncementsFlagLocalService() {
		return _announcementsFlagLocalService;
	}

	private AnnouncementsFlagLocalService _announcementsFlagLocalService;
}