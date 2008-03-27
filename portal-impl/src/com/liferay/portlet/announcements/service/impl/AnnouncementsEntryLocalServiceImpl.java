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
import com.liferay.portlet.announcements.EntryContentException;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.announcements.EntryExpirationDateException;
import com.liferay.portlet.announcements.EntryTitleException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="AnnouncementsEntryLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsEntryLocalServiceImpl
	extends AnnouncementsEntryLocalServiceBaseImpl {

	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int priority, boolean alert)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayMonth, displayDay, displayYear,
			new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationMonth, expirationDay, expirationYear,
			new EntryExpirationDateException());

		Date now = new Date();

		validate(title, content);

		long entryId = counterLocalService.increment();

		AnnouncementsEntry entry = announcementsEntryPersistence.create(
			entryId);

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

		announcementsEntryPersistence.update(entry, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AnnouncementsEntry.class.getName(), entry.getEntryId(), false,
			false, false);

		return entry;
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		// Flags

		announcementsFlagLocalService.deleteFlags(entryId);

		// Entry

		announcementsEntryPersistence.remove(entryId);
	}

	public AnnouncementsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return announcementsEntryPersistence.findByPrimaryKey(entryId);
	}

	public List<AnnouncementsEntry> getEntries(
			long classNameId, long classPK, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryPersistence.findByC_C_A(
			classNameId, classPK, alert, begin, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, boolean alert, int flagValue,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryFinder.findByScope(
			userId, classNameId, classPKs, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear,
			alert, flagValue, begin, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue, int begin, int end)
		throws PortalException, SystemException {

		return getEntries(
			userId, scopes, 0, 0, 0, 0, 0, 0, alert, flagValue, begin, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, boolean alert, int flagValue,
			int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryFinder.findByScopes(
			userId, scopes, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, alert, flagValue,
			begin, end);
	}

	public int getEntriesCount(long classNameId, long classPK, boolean alert)
		throws PortalException, SystemException {

		return announcementsEntryPersistence.countByC_C_A(
			classNameId, classPK, alert);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs, boolean alert,
			int flagValue)
		throws PortalException, SystemException {

		return getEntriesCount(
			userId, classNameId, classPKs, 0, 0, 0, 0, 0, 0, alert, flagValue);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, boolean alert, int flagValue)
		throws PortalException, SystemException {

		return announcementsEntryFinder.countByScope(
			userId, classNameId, classPKs, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear, alert,
			flagValue);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue)
		throws PortalException, SystemException {

		return getEntriesCount(
			userId, scopes, 0, 0, 0, 0, 0, 0, alert, flagValue);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long, long[]> scopes, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, boolean alert, int flagValue)
		throws PortalException, SystemException {

		return announcementsEntryFinder.countByScopes(
			userId, scopes, displayMonth, displayDay, displayYear,
			expirationMonth, expirationDay, expirationYear, alert, flagValue);
	}

	public List<AnnouncementsEntry> getUserEntries(
			long userId, int begin, int end)
		throws SystemException {

		return announcementsEntryPersistence.findByUserId(userId, begin, end);
	}

	public int getUserEntriesCount(long userId) throws SystemException {
		return announcementsEntryPersistence.countByUserId(userId);
	}

	public AnnouncementsEntry updateEntry(
			long entryId, String title, String content, String url, String type,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int priority)
		throws PortalException, SystemException {

		// Entry

		validate(title, content);

		Date displayDate = PortalUtil.getDate(
			displayMonth, displayDay, displayYear,
			new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationMonth, expirationDay, expirationYear,
			new EntryExpirationDateException());

		AnnouncementsEntry entry =
			announcementsEntryPersistence.findByPrimaryKey(entryId);

		entry.setModifiedDate(new Date());
		entry.setTitle(title);
		entry.setContent(content);
		entry.setUrl(url);
		entry.setType(type);
		entry.setDisplayDate(displayDate);
		entry.setExpirationDate(expirationDate);
		entry.setPriority(priority);

		announcementsEntryPersistence.update(entry, false);

		// Flags

		announcementsFlagLocalService.deleteFlags(entry.getEntryId());

		return entry;
	}

	protected void validate(String title, String content)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}

		if (Validator.isNull(content)) {
			throw new EntryContentException();
		}
	}

}