/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.admin.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.service.spring.UserServiceUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AdminUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminUtil {

	public static String getEmailFromAddress(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailFromAddress = PropsUtil.get(
			PropsUtil.ADMIN_EMAIL_FROM_ADDRESS);

		return prefs.getValue("email-from-address", emailFromAddress);
	}

	public static String getEmailFromName(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailFromName = PropsUtil.get(PropsUtil.ADMIN_EMAIL_FROM_NAME);

		return prefs.getValue("email-from-name", emailFromName);
	}

	public static boolean getEmailPasswordSentEnabled(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailPasswordSentEnabled = prefs.getValue(
			"email-password-sent-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailPasswordSentEnabled)) {
			return GetterUtil.getBoolean(emailPasswordSentEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_ENABLED));
		}
	}

	public static String getEmailPasswordSentBody(String companyId)
		throws IOException, PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailPasswordSentBody = prefs.getValue(
			"email-password-sent-body", StringPool.BLANK);

		if (Validator.isNotNull(emailPasswordSentBody)) {
			return emailPasswordSentBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_BODY));
		}
	}

	public static String getEmailPasswordSentSubject(String companyId)
		throws IOException, PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailPasswordSentSubject = prefs.getValue(
			"email-password-sent-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailPasswordSentSubject)) {
			return emailPasswordSentSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT));
		}
	}

	public static boolean getEmailUserAddedEnabled(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailUserAddedEnabled = prefs.getValue(
			"email-user-added-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailUserAddedEnabled)) {
			return GetterUtil.getBoolean(emailUserAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_USER_ADDED_ENABLED));
		}
	}

	public static String getEmailUserAddedBody(String companyId)
		throws IOException, PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailUserAddedBody = prefs.getValue(
			"email-user-added-body", StringPool.BLANK);

		if (Validator.isNotNull(emailUserAddedBody)) {
			return emailUserAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_USER_ADDED_BODY));
		}
	}

	public static String getEmailUserAddedSubject(String companyId)
		throws IOException, PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		String emailUserAddedSubject = prefs.getValue(
			"email-user-added-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailUserAddedSubject)) {
			return emailUserAddedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.ADMIN_EMAIL_USER_ADDED_SUBJECT));
		}
	}

	public static String[] getDefaultGroupNames(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return StringUtil.split(prefs.getValue(
			"defaultGroupNames", StringPool.BLANK), "\n");
	}

	public static String[] getDefaultRoleNames(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return StringUtil.split(prefs.getValue(
			"defaultRoleNames", Role.POWER_USER + "\n" + Role.USER), "\n");
	}

	public static String[] getMailHostNames(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return StringUtil.split(prefs.getValue(
			"mailHostNames", StringPool.BLANK), "\n");
	}

	public static PortletPreferences getPreferences(String companyId)
		throws PortalException, SystemException {

		PortletPreferencesPK prefsPK = new PortletPreferencesPK(
			PortletKeys.PORTAL, PortletKeys.PREFS_LAYOUT_ID_SHARED,
			PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD + companyId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, prefsPK);
	}

	public static String[] getReservedEmailAddresses(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return StringUtil.split(prefs.getValue(
			"reservedEmailAddresses", StringPool.BLANK), "\n");
	}

	public static String[] getReservedUserIds(String companyId)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return StringUtil.split(prefs.getValue(
			"reservedUserIds", StringPool.BLANK), "\n");
	}

	public static String getUpdateUserPassword(
		HttpServletRequest req, String userId) {

		String password = PortalUtil.getUserPassword(req);

		if (!userId.equals(PortalUtil.getUserId(req))) {
			password = StringPool.BLANK;
		}

		return password;
	}

	public static String getUpdateUserPassword(
		ActionRequest req, String userId) {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUpdateUserPassword(reqImpl.getHttpServletRequest(), userId);
	}

	public static User updateUser(
			HttpServletRequest req, String userId, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String smsSn, String aimSn,
			String icqSn, String msnSn, String skypeSn, String ymSn)
		throws PortalException, SystemException {

		String password = getUpdateUserPassword(req, userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		Contact contact = user.getContact();

		Calendar birthdayCal = new GregorianCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DATE);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		return UserServiceUtil.updateUser(
			userId, password, emailAddress, languageId, timeZoneId, greeting,
			resolution, comments, contact.getFirstName(),
			contact.getMiddleName(), contact.getLastName(),
			contact.getNickName(), contact.getPrefixId(), contact.getSuffixId(),
			contact.isMale(), birthdayMonth, birthdayDay, birthdayYear, smsSn,
			aimSn, icqSn, msnSn, skypeSn, ymSn, contact.getJobTitle(),
			user.getOrganization().getOrganizationId(),
			user.getLocation().getOrganizationId());
	}

	public static User updateUser(
			ActionRequest req, String userId, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String smsSn, String aimSn,
			String icqSn, String msnSn, String skypeSn, String ymSn)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return updateUser(
			reqImpl.getHttpServletRequest(), userId, emailAddress, languageId,
			timeZoneId, greeting, resolution, comments, smsSn, aimSn, icqSn,
			msnSn, skypeSn, ymSn);
	}

	private static Log _log = LogFactory.getLog(AdminUtil.class);

}