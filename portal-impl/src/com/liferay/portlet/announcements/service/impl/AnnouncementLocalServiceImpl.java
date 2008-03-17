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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.AnnouncementContentException;
import com.liferay.portlet.announcements.AnnouncementDisplayDateException;
import com.liferay.portlet.announcements.AnnouncementExpirationDateException;
import com.liferay.portlet.announcements.AnnouncementTitleException;
import com.liferay.portlet.announcements.model.Announcement;
import com.liferay.portlet.announcements.service.base.AnnouncementLocalServiceBaseImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="AnnouncementLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 * @author Raymond Augé
 *
 */
public class AnnouncementLocalServiceImpl extends
		AnnouncementLocalServiceBaseImpl {

	public Announcement addAnnouncement(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int priority, boolean alert)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		validate(title, content);

		Date displayDate =
			PortalUtil.getDate(displayMonth, displayDay, displayYear,
				new AnnouncementDisplayDateException());
		Date expirationDate =
			PortalUtil.getDate(expirationMonth, expirationDay, expirationYear,
				new AnnouncementExpirationDateException());

		long announcementId = counterLocalService.increment();

		Announcement announcement =
			announcementPersistence.create(announcementId);

		// audit fields
		announcement.setCompanyId(user.getCompanyId());
		announcement.setUserId(user.getUserId());
		announcement.setUserName(user.getFullName());
		announcement.setCreateDate(now);
		announcement.setModifiedDate(now);

		announcement.setClassNameId(classNameId);
		announcement.setClassPK(classPK);
		announcement.setTitle(title);
		announcement.setContent(content);
		announcement.setUrl(url);
		announcement.setType(type);
		announcement.setDisplayDate(displayDate);
		announcement.setExpirationDate(expirationDate);
		announcement.setPriority(priority);
		announcement.setAlert(alert);

		announcementPersistence.update(announcement);

		// Resource

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			Announcement.class.getName(), announcement.getAnnouncementId(),
			false, false, false);

		return announcement;
	}

	public void deleteAnnouncement(long announcementId)
		throws PortalException, SystemException {

		// AnnouncementFlags

		announcementFlagLocalService.deleteAnnouncementFlags(announcementId);

		// Announcement

		announcementPersistence.remove(announcementId);
	}

	public Announcement getAnnouncement(long announcementId)
		throws PortalException, SystemException {

		return announcementPersistence.findByPrimaryKey(announcementId);
	}

	public List<Announcement> getAnnouncements(
			long classNameId, long classPK, boolean alert,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementPersistence.findByC_C_A(
			classNameId, classPK, alert, begin, end);
	}

	public List<Announcement> getAnnouncements(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementFinder.findByC_C_F(
			userId, classNameId, classPKs, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear, flag,
			alert, begin, end);
	}

	public List<Announcement> getAnnouncements(
			long userId, LinkedHashMap<Long,Long[]> announcementsParams,
			int flag, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return getAnnouncements(
			userId, announcementsParams, 0, 0, 0, 0, 0, 0, flag, alert, begin,
			end);
	}

	public List<Announcement> getAnnouncements(
			long userId, LinkedHashMap<Long,Long[]> announcementsParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return announcementFinder.findByCNM_F(
			userId, announcementsParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert,
			begin, end);
	}

	public int getAnnouncementsCount(
			long classNameId, long classPK, boolean alert)
		throws PortalException, SystemException {

		return announcementPersistence.countByC_C_A(
			classNameId, classPK, alert);
	}

	public int getAnnouncementsCount(
			long userId, long classNameId, long[] classPKs, int flag,
			boolean alert)
		throws PortalException, SystemException {

		return getAnnouncementsCount(
			userId, classNameId, classPKs, 0, 0, 0, 0, 0, 0, flag, alert);
	}

	public int getAnnouncementsCount(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert)
		throws PortalException, SystemException {

		return announcementFinder.countByC_C_F(
			userId, classNameId, classPKs, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public int getAnnouncementsCount(
			long userId, LinkedHashMap<Long,Long[]> announcementsParams,
			int flag, boolean alert)
		throws PortalException, SystemException {

		return getAnnouncementsCount(
			userId, announcementsParams, 0, 0, 0, 0, 0, 0, flag, alert);
	}

	public int getAnnouncementsCount(
			long userId, LinkedHashMap<Long,Long[]> announcementsParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert)
		throws PortalException, SystemException {

		return announcementFinder.countByCNM_F(
			userId, announcementsParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public List<Announcement> getUserAnnouncements(
			long userId, int begin, int end)
		throws SystemException {

		return announcementPersistence.findByUserId(userId, begin, end);
	}

	public int getUserAnnouncementsCount(long userId) throws SystemException {

		return announcementPersistence.countByUserId(userId);
	}

	public Announcement updateAnnouncement(
			long announcementId, String title, String content, String url,
			String type, int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int priority)
		throws PortalException, SystemException {

		validate(title, content);

		Date displayDate =
			PortalUtil.getDate(displayMonth, displayDay, displayYear,
				new AnnouncementDisplayDateException());
		Date expirationDate =
			PortalUtil.getDate(expirationMonth, expirationDay, expirationYear,
				new AnnouncementExpirationDateException());

		Announcement announcement =
			announcementPersistence.findByPrimaryKey(announcementId);

		announcement.setModifiedDate(new Date());

		announcement.setTitle(title);
		announcement.setContent(content);
		announcement.setUrl(url);
		announcement.setType(type);
		announcement.setDisplayDate(displayDate);
		announcement.setExpirationDate(expirationDate);
		announcement.setPriority(priority);

		announcementPersistence.update(announcement);

		// We updated the Announcement, so remove any flags... so that users
		// notice the update.

		announcementFlagLocalService.deleteAnnouncementFlags(
			announcement.getAnnouncementId());

		return announcement;
	}

	protected void validate(String title, String content)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new AnnouncementTitleException();
		}

		if (Validator.isNull(content)) {
			throw new AnnouncementContentException();
		}

	}

}