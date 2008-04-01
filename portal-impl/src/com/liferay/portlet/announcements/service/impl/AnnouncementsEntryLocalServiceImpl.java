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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
			displayDateMinute, new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute,
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
			sendEmail(entry);
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
			long classNameId, long classPK, boolean alert, int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryPersistence.findByC_C_A(
			classNameId, classPK, alert, begin, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryFinder.findByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue, begin,
			end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue, int begin, int end)
		throws PortalException, SystemException {

		return getEntries(
			userId, scopes, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert, flagValue,
			begin, end);
	}

	public List<AnnouncementsEntry> getEntries(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int begin, int end)
		throws PortalException, SystemException {

		return announcementsEntryFinder.findByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, begin, end);
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
		throws PortalException, SystemException {

		return announcementsEntryFinder.countByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	public int getEntriesCount(
			long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
			int flagValue)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		return announcementsEntryFinder.countByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue);
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
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			int priority)
		throws PortalException, SystemException {

		// Entry

		validate(title, content);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, new EntryDisplayDateException());

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute,
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

	protected void sendEmail(AnnouncementsEntry entry)
		throws PortalException, SystemException {

		long classNameId = entry.getClassNameId();
		String className = StringPool.BLANK;

		if (classNameId > 0) {
			className = PortalUtil.getClassName(classNameId);
		}

		long classPK = entry.getClassPK();

		Company company = companyPersistence.findByPrimaryKey(
			entry.getCompanyId());

		String subject = ContentUtil.get(
			PropsValues.ANNOUNCEMENTS_EMAIL_SUBJECT);

		String body = ContentUtil.get(
			PropsValues.ANNOUNCEMENTS_EMAIL_BODY);

		String fromName = PropsValues.ANNOUNCEMENTS_EMAIL_FROM_NAME;
		String fromAddress = PropsValues.ANNOUNCEMENTS_EMAIL_FROM_ADDRESS;

		String toName = PropsValues.ANNOUNCEMENTS_EMAIL_TO_NAME;
		String toAddress = PropsValues.ANNOUNCEMENTS_EMAIL_TO_ADDRESS;

		List<User> users = new ArrayList<User>();

		try {
			if (classNameId == 0 && classPK == 0) {
				users = userPersistence.findByCompanyId(company.getCompanyId());
			}
			else if (className.equals(Group.class.getName()) &&
					classPK > 0) {

				Group group = groupPersistence.findByPrimaryKey(classPK);

				toName = group.getName();

				users = userLocalService.getGroupUsers(group.getGroupId());
			}
			else if (className.equals(Organization.class.getName()) &&
					classPK > 0) {

				Organization org = organizationPersistence.findByPrimaryKey(
					classPK);

				toName = org.getName();

				users = userLocalService.getOrganizationUsers(
					org.getOrganizationId());
			}
			else if (className.equals(Role.class.getName()) && classPK > 0) {
				Role role = rolePersistence.findByPrimaryKey(classPK);

				toName = role.getName();

				users = userLocalService.getRoleUsers(role.getRoleId());
			}
			else if (className.equals(User.class.getName()) && classPK > 0) {
				User user = userLocalService.getUserById(classPK);

				toName = user.getFullName();
				toAddress = user.getEmailAddress();

				users.add(user);
			}
			else if (className.equals(UserGroup.class.getName()) &&
					classPK > 0) {

				UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
					classPK);

				toName = userGroup.getName();

				users = userLocalService.getUserGroupUsers(
					userGroup.getUserGroupId());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Checking " + users.size() + " users");
			}

			List<InternetAddress> addresses =
				new ArrayList<InternetAddress>();

			long userClassNameId = PortalUtil.getClassNameId(
				User.class.getName());

			String emailColName =
				"announcements-delivery-by-email-type-" + entry.getType();

			String smsColName =
				"announcements-delivery-by-sms-type-" + entry.getType();

			/*ExpandoColumn emailColumn =
				expandoColumnLocalService.setColumn(
					userClassNameId, emailColName, ExpandoColumnImpl.BOOLEAN);

			ExpandoColumn smsColumn =
				expandoColumnLocalService.setColumn(
					userClassNameId, smsColName, ExpandoColumnImpl.BOOLEAN);

			boolean sendEmail, sendSMS;

			for (User user : users) {
				try {
					ExpandoValue emailValue =
						expandoValueLocalService.getValue(
							user.getUserId(), emailColumn.getColumnId());

					sendEmail = emailValue.getBoolean();
				}
				catch (NoSuchExpandoValueException nseve) {
					sendEmail = Boolean.FALSE;
				}

				if (sendEmail) {
					InternetAddress address = new InternetAddress(
						user.getEmailAddress(), user.getFullName());

					addresses.add(address);
				}

				try {
					ExpandoValue smsValue =
						expandoValueLocalService.getValue(
							user.getUserId(), smsColumn.getColumnId());

					sendSMS = smsValue.getBoolean();
				}
				catch (NoSuchExpandoValueException nseve) {
					sendSMS = Boolean.FALSE;
				}

				if (sendSMS) {
					Contact contact = contactPersistence.findByPrimaryKey(
						user.getContactId());

					if (Validator.isNotNull(contact.getSmsSn())) {
						InternetAddress address = new InternetAddress(
							contact.getSmsSn(), user.getFullName());

						addresses.add(address);
					}
				}
			}*/

			InternetAddress[] bulkAddresses = addresses.toArray(
				new InternetAddress[addresses.size()]);

			if (bulkAddresses.length > 0) {
				subject = StringUtil.replace(
					subject,
					new String[] {
						"[$ENTRY_ID$]",
						"[$ENTRY_TITLE$]",
						"[$ENTRY_URL$]",
						"[$ENTRY_CONTENT$]",
						"[$ENTRY_TYPE$]",
						"[$FROM_ADDRESS$]",
						"[$FROM_NAME$]",
						"[$PORTAL_URL$]",
						"[$PORTLET_NAME$]",
						"[$TO_ADDRESS$]",
						"[$TO_NAME$]"
					},
					new String[] {
						String.valueOf(entry.getEntryId()),
						entry.getTitle(),
						entry.getUrl(),
						entry.getContent(),
						LanguageUtil.get(
							company.getCompanyId(), company.getLocale(),
							entry.getType()),
						fromAddress,
						fromName,
						company.getVirtualHost(),
						(entry.isAlert()?"Alert":"Announcement"),
						toAddress,
						toName
					});

				body = StringUtil.replace(
					body,
					new String[] {
						"[$ENTRY_ID$]",
						"[$ENTRY_TITLE$]",
						"[$ENTRY_URL$]",
						"[$ENTRY_CONTENT$]",
						"[$ENTRY_TYPE$]",
						"[$FROM_ADDRESS$]",
						"[$FROM_NAME$]",
						"[$PORTAL_URL$]",
						"[$PORTLET_NAME$]",
						"[$TO_ADDRESS$]",
						"[$TO_NAME$]"
					},
					new String[] {
						String.valueOf(entry.getEntryId()),
						entry.getTitle(),
						entry.getUrl(),
						entry.getContent(),
						LanguageUtil.get(
							company.getCompanyId(), company.getLocale(),
							entry.getType()),
						fromAddress,
						fromName,
						company.getVirtualHost(),
						(entry.isAlert()?"Alert":"Announcement"),
						toAddress,
						toName
					});

				InternetAddress from = new InternetAddress(fromAddress, fromName);

				InternetAddress[] to = new InternetAddress[] {
					new InternetAddress(toAddress, toName)};

				MailMessage message = new MailMessage(
					from, to, null, null, bulkAddresses, subject, body, true);

				mailService.sendEmail(message);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortalException pe) {
			throw pe;
		}
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
		LogFactory.getLog(AnnouncementsEntryLocalServiceImpl.class);

}