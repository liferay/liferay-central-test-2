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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.announcements.EntryContentException;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.announcements.EntryExpirationDateException;
import com.liferay.portlet.announcements.EntryTitleException;
import com.liferay.portlet.announcements.job.CheckEntryJob;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * <a href="AnnouncementsEntryLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AnnouncementsEntryLocalServiceImpl
	extends AnnouncementsEntryLocalServiceBaseImpl {

	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, int priority,
			boolean alert)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, user.getTimeZone(),
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

	public void checkEntries() throws PortalException, SystemException {
		Date now = new Date();

		List<AnnouncementsEntry> entries =
			announcementsEntryFinder.findByDisplayDate(
				now, new Date(now.getTime() - CheckEntryJob.INTERVAL));

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + entries.size() + " entries");
		}

		for (AnnouncementsEntry entry : entries) {
			try {
				notifyUsers(entry);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
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
			long classNameId, long classPK, boolean alert, int start, int end)
		throws SystemException {

		return announcementsEntryPersistence.findByC_C_A(
			classNameId, classPK, alert, start, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int start, int end)
		throws SystemException {

		return announcementsEntryFinder.findByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue, start,
			end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue, int start, int end)
		throws SystemException {

		return getEntries(
			userId, scopes, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert, flagValue,
			start, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int start, int end)
		throws SystemException {

		return announcementsEntryFinder.findByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, start, end);
	}

	public int getEntriesCount(long classNameId, long classPK, boolean alert)
		throws SystemException {

		return announcementsEntryPersistence.countByC_C_A(
			classNameId, classPK, alert);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs, boolean alert,
			int flagValue)
		throws SystemException {

		return getEntriesCount(
			userId, classNameId, classPKs, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert,
			flagValue);
	}

	public int getEntriesCount(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		return announcementsEntryFinder.countByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue)
		throws SystemException {

		return getEntriesCount(
			userId, scopes, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert, flagValue);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		return announcementsEntryFinder.countByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue);
	}

	public List<AnnouncementsEntry> getUserEntries(
			long userId, int start, int end)
		throws SystemException {

		return announcementsEntryPersistence.findByUserId(userId, start, end);
	}

	public int getUserEntriesCount(long userId) throws SystemException {
		return announcementsEntryPersistence.countByUserId(userId);
	}

	public AnnouncementsEntry updateEntry(
			long userId, long entryId, String title, String content, String url,
			String type, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, int priority)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, user.getTimeZone(),
			new EntryExpirationDateException());

		validate(title, content);

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

	protected void notifyUsers(AnnouncementsEntry entry)
		throws IOException, PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(
			entry.getCompanyId());

		String className = entry.getClassName();
		long classPK = entry.getClassPK();

		String fromName = PropsValues.ANNOUNCEMENTS_EMAIL_FROM_NAME;
		String fromAddress = PropsValues.ANNOUNCEMENTS_EMAIL_FROM_ADDRESS;

		String toName = PropsValues.ANNOUNCEMENTS_EMAIL_TO_NAME;
		String toAddress = PropsValues.ANNOUNCEMENTS_EMAIL_TO_ADDRESS;

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("announcementsDeliveryEmailOrSms", entry.getType());

		if (classPK > 0) {
			if (className.equals(Group.class.getName())) {
				Group group = groupPersistence.findByPrimaryKey(classPK);

				toName = group.getName();

				params.put("usersGroups", classPK);
			}
			else if (className.equals(Organization.class.getName())) {
				Organization organization =
					organizationPersistence.findByPrimaryKey(classPK);

				toName = organization.getName();

				params.put("usersOrgs", classPK);
			}
			else if (className.equals(Role.class.getName())) {
				Role role = rolePersistence.findByPrimaryKey(classPK);

				toName = role.getName();

				params.put("usersRoles", classPK);
			}
			else if (className.equals(UserGroup.class.getName())) {
				UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
					classPK);

				toName = userGroup.getName();

				params.put("usersUserGroups", classPK);
			}
		}

		List<User> users = null;

		if (className.equals(User.class.getName())) {
			User user = userLocalService.getUserById(classPK);

			toName = user.getFullName();
			toAddress = user.getEmailAddress();

			users = new ArrayList<User>();

			if (Validator.isNotNull(toAddress)) {
				users.add(user);
			}
		}
		else {
			users = userLocalService.search(
				company.getCompanyId(), null, Boolean.TRUE, params,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Notifying " + users.size() + " users");
		}

		List<InternetAddress> bulkAddresses = new ArrayList<InternetAddress>();

		for (User user : users) {
			AnnouncementsDelivery announcementsDelivery =
				announcementsDeliveryLocalService.getUserDelivery(
					user.getUserId(), entry.getType());

			if (announcementsDelivery.isEmail()) {
				InternetAddress address = new InternetAddress(
					user.getEmailAddress(), user.getFullName());

				bulkAddresses.add(address);
			}

			if (announcementsDelivery.isSms()) {
				String smsSn = user.getContact().getSmsSn();

				InternetAddress address = new InternetAddress(
					smsSn, user.getFullName());

				bulkAddresses.add(address);
			}
		}

		if (bulkAddresses.size() == 0) {
			return;
		}

		String subject = ContentUtil.get(
			PropsValues.ANNOUNCEMENTS_EMAIL_SUBJECT);
		String body = ContentUtil.get(PropsValues.ANNOUNCEMENTS_EMAIL_BODY);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$ENTRY_CONTENT$]",
				"[$ENTRY_ID$]",
				"[$ENTRY_TITLE$]",
				"[$ENTRY_TYPE$]",
				"[$ENTRY_URL$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				entry.getContent(),
				String.valueOf(entry.getEntryId()),
				entry.getTitle(),
				LanguageUtil.get(company.getLocale(), entry.getType()),
				entry.getUrl(),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				LanguageUtil.get(
					company.getLocale(),
					(entry.isAlert() ? "alert" : "announcement")),
				toAddress,
				toName
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$ENTRY_CONTENT$]",
				"[$ENTRY_ID$]",
				"[$ENTRY_TITLE$]",
				"[$ENTRY_TYPE$]",
				"[$ENTRY_URL$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				entry.getContent(),
				String.valueOf(entry.getEntryId()),
				entry.getTitle(),
				LanguageUtil.get(company.getLocale(), entry.getType()),
				entry.getUrl(),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				LanguageUtil.get(
					company.getLocale(),
					(entry.isAlert() ? "alert" : "announcement")),
				toAddress,
				toName
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		InternetAddress[] bulkAddressesArray = bulkAddresses.toArray(
			new InternetAddress[bulkAddresses.size()]);

		MailMessage message = new MailMessage(
			from, to, subject, body, true);

		message.setBulkAddresses(bulkAddressesArray);

		mailService.sendEmail(message);
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

	private static Log _log =
		LogFactoryUtil.getLog(AnnouncementsEntryLocalServiceImpl.class);

}