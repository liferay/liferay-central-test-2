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
import com.liferay.portlet.announcements.AnnouncementEntryContentException;
import com.liferay.portlet.announcements.AnnouncementEntryDisplayDateException;
import com.liferay.portlet.announcements.AnnouncementEntryExpirationDateException;
import com.liferay.portlet.announcements.AnnouncementEntryTitleException;
import com.liferay.portlet.announcements.model.AnnouncementEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="AnnouncementEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryLocalServiceImpl
	extends AnnouncementEntryLocalServiceBaseImpl {

	public AnnouncementEntry addEntry(
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
				new AnnouncementEntryDisplayDateException());
		Date expirationDate =
			PortalUtil.getDate(expirationMonth, expirationDay, expirationYear,
				new AnnouncementEntryExpirationDateException());

		long entryId = counterLocalService.increment();

		AnnouncementEntry entry =
			announcementEntryPersistence.create(entryId);

		// audit fields
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);

		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setUrl(url);
		entry.setType(type);
		entry.setDisplayDate(displayDate);
		entry.setExpirationDate(expirationDate);
		entry.setPriority(priority);
		entry.setAlert(alert);

		announcementEntryPersistence.update(entry);

		// Resource

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AnnouncementEntry.class.getName(), entry.getEntryId(),
			false, false, false);

		return entry;
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		// AnnouncementFlags

		announcementFlagLocalService.deleteAnnouncementFlags(entryId);

		// Announcement

		announcementEntryPersistence.remove(entryId);
	}

	public AnnouncementEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return announcementEntryPersistence.findByPrimaryKey(entryId);
	}

	public List<AnnouncementEntry> getEntries(
			long classNameId, long classPK, boolean alert,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementEntryPersistence.findByC_C_A(
			classNameId, classPK, alert, begin, end);
	}

	public List<AnnouncementEntry> getEntries(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementEntryFinder.findByC_C_F(
			userId, classNameId, classPKs, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear, flag,
			alert, begin, end);
	}

	public List<AnnouncementEntry> getEntries(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int flag, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return getEntries(
			userId, entriesParams, 0, 0, 0, 0, 0, 0, flag, alert, begin, end);
	}

	public List<AnnouncementEntry> getEntries(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return announcementEntryFinder.findByCNM_F(
			userId, entriesParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert,
			begin, end);
	}

	public int getEntriesCount(
			long classNameId, long classPK, boolean alert)
		throws PortalException, SystemException {

		return announcementEntryPersistence.countByC_C_A(
			classNameId, classPK, alert);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs, int flag,
			boolean alert)
		throws PortalException, SystemException {

		return getEntriesCount(
			userId, classNameId, classPKs, 0, 0, 0, 0, 0, 0, flag, alert);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert)
		throws PortalException, SystemException {

		return announcementEntryFinder.countByC_C_F(
			userId, classNameId, classPKs, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int flag, boolean alert)
		throws PortalException, SystemException {

		return getEntriesCount(
			userId, entriesParams, 0, 0, 0, 0, 0, 0, flag, alert);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert)
		throws PortalException, SystemException {

		return announcementEntryFinder.countByCNM_F(
			userId, entriesParams, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, flag, alert);
	}

	public List<AnnouncementEntry> getUserEntries(
			long userId, int begin, int end)
		throws SystemException {

		return announcementEntryPersistence.findByUserId(userId, begin, end);
	}

	public int getUserEntriesCount(long userId) throws SystemException {

		return announcementEntryPersistence.countByUserId(userId);
	}

	public AnnouncementEntry updateEntry(
			long entryId, String title, String content, String url,
			String type, int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int priority)
		throws PortalException, SystemException {

		validate(title, content);

		Date displayDate =
			PortalUtil.getDate(displayMonth, displayDay, displayYear,
				new AnnouncementEntryDisplayDateException());
		Date expirationDate =
			PortalUtil.getDate(expirationMonth, expirationDay, expirationYear,
				new AnnouncementEntryExpirationDateException());

		AnnouncementEntry entry =
			announcementEntryPersistence.findByPrimaryKey(entryId);

		entry.setModifiedDate(new Date());

		entry.setTitle(title);
		entry.setContent(content);
		entry.setUrl(url);
		entry.setType(type);
		entry.setDisplayDate(displayDate);
		entry.setExpirationDate(expirationDate);
		entry.setPriority(priority);

		announcementEntryPersistence.update(entry);

		// We updated the Announcement, so remove any flags... so that users
		// notice the update.

		announcementFlagLocalService.deleteAnnouncementFlags(
				entry.getEntryId());

		return entry;
	}

	protected void validate(String title, String content)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new AnnouncementEntryTitleException();
		}

		if (Validator.isNull(content)) {
			throw new AnnouncementEntryContentException();
		}

	}

}